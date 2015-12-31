package ami.infrastructure.services;

import java.util.List;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ami.application.commands.amirequest.SwitchCaseToInProgressCmd;
import ami.application.commands.security.CreateHospitalCmd;
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
import ami.domain.model.security.hospitals.Address;
import ami.domain.model.security.hospitals.Email;
import ami.domain.model.security.hospitals.Hospital;
import ami.domain.model.security.hospitals.Phone;

import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class AmiServicesImpl implements AmiServices  {
	
	@Autowired
	private CommandGateway commandGateway;
	
	
	@Override
	public void createHospital(Hospital hospital ,DateTime hospitalActivationDate) throws JsonProcessingException {
		
		commandGateway.sendAndWait(new CreateHospitalCmd(hospital,hospitalActivationDate));
	}
	
	@Override
	public void updateMasterUserPwd(String hospitalId ,String userName, String newPwd) {
		commandGateway.sendAndWait(new UpdateMasterUserPwdCmd( hospitalId , userName, newPwd));
	}
	@Override
	public void updateMasterUserEmail(String hospitalId, String userName,
			String newEmail)  {
		commandGateway.sendAndWait(new UpdateMasterUserEmailCmd( hospitalId , userName, newEmail));
		
	}
	@Override
	public void updateMasterUserFirstName(String hospitalId, String userName,
			String newValue) {
		commandGateway.sendAndWait(new UpdateMasterUserFirstNameCmd( hospitalId , userName, newValue));
		
	}
	@Override
	public void updateMasterUserLastName(String hospitalId, String userName, String newValue) {
		commandGateway.sendAndWait(new UpdateMasterUserLastNameCmd( hospitalId , userName, newValue));
		
	}
	
	@Override
	public void updateMasterUserOccupation(String hospitalId, String userName, String newValue) {
		commandGateway.sendAndWait(new UpdateMasterUserOccupationCmd( hospitalId , userName, newValue));
		
	}
	
	@Override
	public void switchMasterUser(String hospitalId, String newMasterUser) {
		commandGateway.sendAndWait(new SwitchMasterUserCmd( hospitalId , newMasterUser));
	}

	@Override
	public void updateHospitalPhones(String hospitalId, String userName,
			List<Phone> updatedPhoneList) {
		
		commandGateway.sendAndWait(new UpdateHospitalPhonesCmd( hospitalId , userName, updatedPhoneList));
	}

	@Override
	public void updateHospitalEmails(String hospitalId, String userName,
			List<Email> updatedEmailList) {
		
		commandGateway.sendAndWait(new UpdateHospitalEmailsCmd( hospitalId , userName, updatedEmailList));
	}

	@Override
	public void updateHospitalAddresses(String hospitalId, String userName,
			List<Address> updatedAddressList) {
		
		commandGateway.sendAndWait(new UpdateHospitalAddressesCmd( hospitalId , userName, updatedAddressList));
		
	}

	@Override
	public void updateHospitalAcronym(String hospitalId, String userName,
			String newAcronym) {
		commandGateway.sendAndWait(new UpdateHospitalAcronymCmd( hospitalId , userName, newAcronym));
		
	}

	@Override
	public void updateHospitalNotes(String hospitalId, String userName,
			String newNotes) {
		commandGateway.sendAndWait(new UpdateHospitalNotesCmd( hospitalId , userName, newNotes));
		
	}
	
	@Override
	public void switchCaseToInProgress(String caseNumber, String userName, DateTime dateTime) throws JsonProcessingException {
		
		commandGateway.sendAndWait(new SwitchCaseToInProgressCmd(  caseNumber,  userName,  dateTime));
		
	}

	@Override
	public void updateHospitalContract(String hospitalId, String userName,
			String newContract) {
		commandGateway.sendAndWait(new UpdateHospitalContractCmd( hospitalId , userName, newContract));
		
	}

	@Override
	public void updateHospitalAccountSize(String hospitalId, String userName,
			String newAccountSize) {
		commandGateway.sendAndWait(new UpdateHospitalAccountSizeCmd( hospitalId , userName, newAccountSize));
		
	}

}
