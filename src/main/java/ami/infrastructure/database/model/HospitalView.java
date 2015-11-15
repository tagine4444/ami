package ami.infrastructure.database.model;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.eventhandling.annotation.Timestamp;
import org.joda.time.DateTime;

import ami.domain.model.security.amiusers.AmiUser;
import ami.domain.model.security.hospitals.Hospital;

public class HospitalView {

	private Hospital hospital;
	
	private  DateTime hospitalActivationDate;
	private  DateTime hospitalDeactivationDate;
	private  String hospitalDeactivatedBy;
	private  String hospitalDeactivationReason;
	private List<AmiUser> amiUsers;

	
	public HospitalView(Hospital hospital,
			@Timestamp DateTime hospitalActivationDate,
			String hospitalDeactivatedBy,
			@Timestamp DateTime hospitalDeactivationDate,
			String hospitalDeactivationReason) {		
		this.hospital    = hospital;   
		this.hospitalActivationDate = hospitalActivationDate;
		
		this.hospitalDeactivatedBy = hospitalDeactivatedBy;
		this.hospitalDeactivationDate = hospitalDeactivationDate;
		this.hospitalDeactivationReason = hospitalDeactivationReason;
		this.amiUsers = new ArrayList<AmiUser>();
	}


	public Hospital getHospital() {
		return hospital;
	}


	public DateTime getHospitalActivationDate() {
		return hospitalActivationDate;
	}


	public DateTime getHospitalDeactivationDate() {
		return hospitalDeactivationDate;
	}


	public String getHospitalDeactivatedBy() {
		return hospitalDeactivatedBy;
	}


	public String getHospitalDeactivationReason() {
		return hospitalDeactivationReason;
	}
	
	public void addUser(AmiUser amiUser){
		this.amiUsers.add(amiUser);
	}


	public List<AmiUser> getAmiUsers() {
		return amiUsers;
	}
	
}
