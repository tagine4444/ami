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
import ami.application.events.security.AmiUserCreatedEvent;
import ami.application.events.security.AmiUserDeactivatedEvent;
import ami.application.events.security.HospitaDeactivatedEvent;
import ami.application.events.security.HospitalCreatedEvent;
import ami.domain.model.security.amiusers.AmiUser;
import ami.domain.model.security.hospitals.Hospital;

public class SecurityAggregate extends AbstractAnnotatedAggregateRoot {

		@AggregateIdentifier
		private String hospitalId;
		
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
			
			
			if(!this.hasMasterUser && !amiUser.isMasterUser()){
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
				throw new IllegalArgumentException("Cannot deactivate User '"+command.getHospitalId() +"' because he doesn't work at hospital '"+this.hospital.getId()+"' " );
			}
			
			apply(new AmiUserDeactivatedEvent(
					command.getHospitalId(),
					command.getAmiUserName(),
					command.getDeactivationDate() , 
					command.getDeactivatedBy() , 
					command.getDeactivationReason()) );
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
			this.hospitalId = event.getHospital().getId();
			this.hospital = event.getHospital();
			this.hospitalActivationDate = event.getHospitalActivationDate();
			
		}
		
		@EventSourcingHandler
		public void on(HospitaDeactivatedEvent event) {
			this.hospitalId = event.getHospitalId();
			this.hospitalDeactivatedBy = event.getHospitalDeactivatedBy();
			this.hospitalDeactivationDate = event.getHospitalDeactivationDate();
			this.hospitalDeactivationReason = event.getHospitalDeactivationReason();
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
		
}
