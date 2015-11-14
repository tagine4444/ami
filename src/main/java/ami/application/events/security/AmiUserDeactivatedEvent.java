package ami.application.events.security;

import org.joda.time.DateTime;

import ami.domain.model.security.hospitals.Hospital;

public class AmiUserDeactivatedEvent {

	private final String   hospitalId;
	private final String amiUserName;
	private final DateTime deactivationDate;
	private final String deactivatedBy;
	private final String deactivationReason;

	
	public AmiUserDeactivatedEvent(String hospitalId,
									String amiUserName,
								    DateTime deactivationDate,
									String deactivatedBy,
									String deactivationReason) {
		this.hospitalId                 =  hospitalId;
		this.amiUserName 			= amiUserName;
		this.deactivationDate   =  deactivationDate;
		this.deactivatedBy      =  deactivatedBy;
		this.deactivationReason =  deactivationReason;
	}

	public String getHospitalId() {
		return hospitalId;
	}


	public DateTime getDeactivationDate() {
		return deactivationDate;
	}


	public String getDeactivatedBy() {
		return deactivatedBy;
	}


	public String getDeactivationReason() {
		return deactivationReason;
	}

	public String getAmiUserName() {
		return amiUserName;
	}

	

}
