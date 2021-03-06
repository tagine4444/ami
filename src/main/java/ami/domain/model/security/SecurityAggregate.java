package ami.domain.model.security;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;
import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;

import ami.application.commands.security.CreateAmiUserCmd;
import ami.application.commands.security.CreateHospitalCmd;
import ami.application.commands.security.DeactivateAmiUserCmd;
import ami.application.commands.security.DeactivateHospitalCmd;
import ami.application.commands.security.SwitchMasterUserCmd;
import ami.application.commands.security.UpdateHospitalAccountSizeCmd;
import ami.application.commands.security.UpdateHospitalAcronymCmd;
import ami.application.commands.security.UpdateHospitalAddressesCmd;
import ami.application.commands.security.UpdateHospitalContractCmd;
import ami.application.commands.security.UpdateHospitalEmailsCmd;
import ami.application.commands.security.UpdateHospitalNotesCmd;
import ami.application.commands.security.UpdateHospitalPhonesCmd;
import ami.application.commands.security.UpdateMasterUserEmailCmd;
import ami.application.commands.security.UpdateMasterUserFirstNameCmd;
import ami.application.commands.security.UpdateMasterUserLastNameCmd;
import ami.application.commands.security.UpdateMasterUserOccupationCmd;
import ami.application.commands.security.UpdateMasterUserPwdCmd;
import ami.domain.model.security.amiusers.AmiUser;
import ami.domain.model.security.events.AmiUserCreatedEvent;
import ami.domain.model.security.events.AmiUserDeactivatedEvent;
import ami.domain.model.security.events.HospitaDeactivatedEvent;
import ami.domain.model.security.events.HospitalAccountSizeUpdatedEvent;
import ami.domain.model.security.events.HospitalAcronymUpdatedEvent;
import ami.domain.model.security.events.HospitalAddressesUpdatedEvent;
import ami.domain.model.security.events.HospitalContractUpdatedEvent;
import ami.domain.model.security.events.HospitalCreatedEvent;
import ami.domain.model.security.events.HospitalEmailsUpdatedEvent;
import ami.domain.model.security.events.HospitalNotesUpdatedEvent;
import ami.domain.model.security.events.HospitalPhonesUpdatedEvent;
import ami.domain.model.security.events.MasterUserEmailUpdatedEvent;
import ami.domain.model.security.events.MasterUserFirstNameUpdatedEvent;
import ami.domain.model.security.events.MasterUserLastNameUpdatedEvent;
import ami.domain.model.security.events.MasterUserOccupationUpdatedEvent;
import ami.domain.model.security.events.MasterUserPwdUpdatedEvent;
import ami.domain.model.security.events.MasterUserSwitchedAbortedCauseAlreadyMasterUserEvent;
import ami.domain.model.security.events.MasterUserSwitchedEvent;
import ami.domain.model.security.hospitals.Hospital;

public class SecurityAggregate extends AbstractAnnotatedAggregateRoot {

		@AggregateIdentifier
		private String id;
		
		private Hospital hospital;
		private List<AmiUser> amiUsers = new ArrayList<AmiUser>();
//		private List<AmiUser> masterUsers = new ArrayList<AmiUser>();
		private boolean hasMasterUser;
		private DateTime hospitalActivationDate;
		private DateTime hospitalDeactivationDate;
		private String hospitalDeactivatedBy;
		private String hospitalDeactivationReason;

	
		// No-arg constructor, required by Axon
		public SecurityAggregate() {
		}

		@CommandHandler
		public SecurityAggregate(CreateHospitalCmd command) {
			
			apply(new HospitalCreatedEvent(command.getHospital(), 
										   command.getHospitalActivationDate()));
		}
		
		

		@CommandHandler
		public void createUser(CreateAmiUserCmd command) {
			if( !this.hospital.getId().equals(command.getHospitalId()) ){
				throw new IllegalArgumentException("Cannot Create User '"+command.getAmiUser().getUser()+"' because he doesn't work at hospital '"+this.hospital.getId()+"' " );
			}
			
			final AmiUser amiUser = command.getAmiUser();
			
			
			if(!amiUser.isMasterUser() && !this.hasMasterUser && !amiUser.isMasterUser()){
				throw new IllegalArgumentException("Cannot Create User '"+command.getAmiUser().getUser()+"' No Master Users exist for this hospital: '"+this.hospital.getId()+"' " );
			}
			
			boolean userAlreadyExists = userAlreadyExists(command.getAmiUser());
			
			if(userAlreadyExists){
				throw new IllegalArgumentException("Cannot Create User '"+command.getAmiUser().getUser() +"' because he doesn't work at hospital '"+this.hospital.getId()+"' " );
			}
				
			apply(new AmiUserCreatedEvent(command.getHospitalId(),
					command.getHospitalName(),
					command.getAmiUser() ) );
		}
		
		@CommandHandler
		public void deactivateHospital(DeactivateHospitalCmd command) {
			
			apply(new HospitaDeactivatedEvent(
					command.getHospitalId(),
					command.getHospitalDeactivationDate() , 
					command.getHospitalDeactivatedBy() , 
					command.getHospitalDeactivationReason()) );
		}
		
		@CommandHandler
		public void deactivateAmiUser(DeactivateAmiUserCmd command) {
			if(!this.hospital.getId().equals(command.getHospitalId())){
				throw new IllegalArgumentException("Cannot deactivate User '"+command.getAmiUserName() +"' because he doesn't work at hospital '"+this.hospital.getId()+"' " );
			}
			
			apply(new AmiUserDeactivatedEvent(
					command.getHospitalId(),
					command.getAmiUserName(),
					command.getDeactivationDate() , 
					command.getDeactivatedBy() , 
					command.getDeactivationReason()) );
		}
		
		@CommandHandler
		public void updateMasterUserCmd(UpdateMasterUserPwdCmd command) {
			
			if(!this.hospital.getId().equals(command.getHospitalId())){
				throw new IllegalArgumentException("Cannot update pwd for  '"+command.getUserName() +"' because he doesn't work at hospital '"+this.hospital.getId()+"' " );
			}
			
//			if(! isMasterUser(command.getUserName()) ){
//				throw new IllegalArgumentException("Cannot update pwd for  '"+command.getUserName() +"' because he is not a master user for hospital "+this.hospital.getId()+"' " );
//			}
			apply(new MasterUserPwdUpdatedEvent(
					command.getHospitalId(),
					command.getUserName(),
					command.getNewPwd() ) );
		}
		
		
		@CommandHandler
		public void updateMasterUserCmd(UpdateMasterUserEmailCmd command) {
			
			if(!this.hospital.getId().equals(command.getHospitalId())){
				throw new IllegalArgumentException("Cannot update email for  '"+command.getUserName() +"' because he doesn't work at hospital '"+this.hospital.getId()+"' " );
			}
			
//			if(! isMasterUser(command.getUserName()) ){
//				throw new IllegalArgumentException("Cannot update email for  '"+command.getUserName() +"' because he is not a master user for hospital "+this.hospital.getId()+"' " );
//			}
			
			apply(new MasterUserEmailUpdatedEvent(
					command.getHospitalId(),
					command.getUserName(),
					command.getNewEmail() ) );
		}
		
		@CommandHandler
		public void updateMasterUserCmd(UpdateMasterUserFirstNameCmd command) {
			
			if(!this.hospital.getId().equals(command.getHospitalId())){
				throw new IllegalArgumentException("Cannot update first name for  '"+command.getUserName() +"' because he doesn't work at hospital '"+this.hospital.getId()+"' " );
			}
			
//			if(! isMasterUser(command.getUserName()) ){
//				throw new IllegalArgumentException("Cannot update first name for  '"+command.getUserName() +"' because he is not a master user for hospital "+this.hospital.getId()+"' " );
//			}
			
			apply(new MasterUserFirstNameUpdatedEvent(
					command.getHospitalId(),
					command.getUserName(),
					command.getNewFirstName() ) );
		}
		
		
		@CommandHandler
		public void updateMasterUserCmd(UpdateMasterUserLastNameCmd command) {
			
			if(!this.hospital.getId().equals(command.getHospitalId())){
				throw new IllegalArgumentException("Cannot update last name for  '"+command.getUserName() +"' because he doesn't work at hospital '"+this.hospital.getId()+"' " );
			}
			
//			if(! isMasterUser(command.getUserName()) ){
//				throw new IllegalArgumentException("Cannot update last name for  '"+command.getUserName() +"' because he is not a master user for hospital "+this.hospital.getId()+"' " );
//			}
			
			apply(new MasterUserLastNameUpdatedEvent(
					command.getHospitalId(),
					command.getUserName(),
					command.getNewLastName() ) );
		}
		
		@CommandHandler
		public void updateMasterUserCmd(UpdateMasterUserOccupationCmd command) {
			
			if(!this.hospital.getId().equals(command.getHospitalId())){
				throw new IllegalArgumentException("Cannot update occupation for  '"+command.getUserName() +"' because he doesn't work at hospital '"+this.hospital.getId()+"' " );
			}
			
//			if(! isMasterUser(command.getUserName()) ){
//				throw new IllegalArgumentException("Cannot update occupation for  '"+command.getUserName() +"' because he is not a master user for hospital "+this.hospital.getId()+"' " );
//			}
			
			apply(new MasterUserOccupationUpdatedEvent(
					command.getHospitalId(),
					command.getUserName(),
					command.getNewOccupation() ) );
		}
		
		@CommandHandler
		public void switchMasterUserCmd(SwitchMasterUserCmd command) {
			
			if(!this.hospital.getId().equals(command.getHospitalId())){
				throw new IllegalArgumentException("Cannot switch master user to '"+command.getNewMasterUser()+"' because he doesn't work at hospital '"+this.hospital.getId()+"' " );
			}
			
			if( isMasterUser(command.getNewMasterUser()) ){
				
				apply(new MasterUserSwitchedAbortedCauseAlreadyMasterUserEvent(
						command.getHospitalId(),
						command.getNewMasterUser() ) );
			}
			
			apply(new MasterUserSwitchedEvent(
					command.getHospitalId(),
					command.getNewMasterUser() ) );
		}
		
		

		@CommandHandler
		public void updateHospitalPhones(UpdateHospitalPhonesCmd command) {
			
			apply(new HospitalPhonesUpdatedEvent(
					command.getHospitalId(),
					command.getUserName(),
					command.getNewPhoneList() ) );
		}
		
		@CommandHandler
		public void updateHospitalEmails(UpdateHospitalEmailsCmd command) {
			
			apply(new HospitalEmailsUpdatedEvent(
					command.getHospitalId(),
					command.getUserName(),
					command.getNewEmailList() ) );
		}
		
		@CommandHandler
		public void updateHospitalAddresses(UpdateHospitalAddressesCmd command) {
			
			apply(new HospitalAddressesUpdatedEvent(
					command.getHospitalId(),
					command.getUserName(),
					command.getNewAddressList() ) );
		}
		
		@CommandHandler
		public void updateHospitalAcronym(UpdateHospitalAcronymCmd command) {
			
			apply(new HospitalAcronymUpdatedEvent(
					command.getHospitalId(),
					command.getUserName(),
					command.getNewAcronym() ) );
		}
		
		
		@CommandHandler
		public void updateHospitalNotes(UpdateHospitalNotesCmd command) {
			
			apply(new HospitalNotesUpdatedEvent(
					command.getHospitalId(),
					command.getUserName(),
					command.getNewNotes() ) );
		}
		
		
		@CommandHandler
		public void updateHospitalAcronym(UpdateHospitalContractCmd command) {
			
			apply(new HospitalContractUpdatedEvent(
					command.getHospitalId(),
					command.getUserName(),
					command.getNewContract()) );
		}
		
		@CommandHandler
		public void updateHospitalAcronym(UpdateHospitalAccountSizeCmd command) {
			
			apply(new HospitalAccountSizeUpdatedEvent(
					command.getHospitalId(),
					command.getUserName(),
					command.getNewAccountSize()) );
		}
		
		
		@EventSourcingHandler
		public void on(AmiUserCreatedEvent event) {
			this.amiUsers.add(event.getAmiUser());
			this.hasMasterUser  = hasMasterUser();
		}
		

		@EventSourcingHandler
		public void on(AmiUserDeactivatedEvent event) {
			Iterator<AmiUser> iterator = this.amiUsers.iterator();
			
			while (iterator.hasNext()) {
				AmiUser amiUser = (AmiUser) iterator.next();
				if(event.getHospitalId().equals(amiUser.getHospitalId())){
					iterator.remove();
				}
			}
		}
		
		@EventSourcingHandler
		public void on(HospitalCreatedEvent event) {
			this.id = event.getHospital().getId();
			this.hospital = event.getHospital();
			this.hospitalActivationDate = event.getHospitalActivationDate();
		}
		
		@EventSourcingHandler
		public void on(MasterUserPwdUpdatedEvent event) {
			this.id = event.getHospitalId();
		}
		
		@EventSourcingHandler
		public void on(MasterUserEmailUpdatedEvent event) {
			this.id = event.getHospitalId();
		}
		
		@EventSourcingHandler
		public void on(MasterUserFirstNameUpdatedEvent event) {
			this.id = event.getHospitalId();
		}
		
		@EventSourcingHandler
		public void on(MasterUserLastNameUpdatedEvent event) {
			this.id = event.getHospitalId();
		}
		
		@EventSourcingHandler
		public void on(MasterUserOccupationUpdatedEvent event) {
			this.id = event.getHospitalId();
		}
		@EventSourcingHandler
		public void on(MasterUserSwitchedEvent event) {
			this.id = event.getHospitalId();
		}
		
		@EventSourcingHandler
		public void on(HospitaDeactivatedEvent event) {
			this.id = event.getHospitalId();
			this.hospitalDeactivatedBy = event.getHospitalDeactivatedBy();
			this.hospitalDeactivationDate = event.getHospitalDeactivationDate();
			this.hospitalDeactivationReason = event.getHospitalDeactivationReason();
		}
		
		@EventSourcingHandler
		public void on(HospitalPhonesUpdatedEvent event) {
			this.hospital.replacePhoneList(event.getNewPhoneList());
		}
		
		@EventSourcingHandler
		public void on(HospitalEmailsUpdatedEvent event) {
			this.hospital.replaceEmailsList(event.getNewEmailList());
		}
		
		@EventSourcingHandler
		public void on(HospitalAddressesUpdatedEvent event) {
			this.hospital.replaceAddressList(event.getNewAddressList());
		}
		
		@EventSourcingHandler
		public void on(HospitalAcronymUpdatedEvent event) {
			this.hospital.replaceAcronym(event.getNewAcronym());
		}
		
		@EventSourcingHandler
		public void on(HospitalNotesUpdatedEvent event) {
			this.hospital.replaceNotes(event.getNewNotes());
		}
		
		
		@EventSourcingHandler
		public void on(HospitalContractUpdatedEvent event) {
			this.hospital.replaceContract(event.getNewContract());
		}
		
		@EventSourcingHandler
		public void on(HospitalAccountSizeUpdatedEvent event) {
			this.hospital.replaceAccountSize(event.getNewAccountSize());
		}
		private boolean userAlreadyExists(AmiUser newAmiUser) {
			
			Iterator<AmiUser> iterator = this.amiUsers.iterator();
			
			while (iterator.hasNext()) {
				AmiUser amiUser = (AmiUser) iterator.next();
				if(newAmiUser.getUser().equals(amiUser.getUser())){
					return true;
				}
			}
			return false;
		}
		
		public boolean hasMasterUser(){
			
			boolean foundMasterRole =false;
			for (AmiUser anAmiUser : this.amiUsers) {
				
				List<? extends GrantedAuthority> role = anAmiUser.getRole();
				for (GrantedAuthority grantedAuthority : role) {
					if( AmiAuthtorities.AMI_MASTER_USER.toString().equals( grantedAuthority.getAuthority() )){
						foundMasterRole = true;
						break;
					}
				}
				
				if( foundMasterRole){
					break;
				}
			}
			
			return foundMasterRole;
		}
		
		private boolean isMasterUser(String userName) {
			
			Iterator<AmiUser> iterator = this.amiUsers.iterator();
			
			while (iterator.hasNext()) {
				AmiUser amiUser = (AmiUser) iterator.next();
				if(userName.equals(amiUser.getUser())){
					return amiUser.isMasterUser();
				}
			}
			
			return false;
		}
		
}
