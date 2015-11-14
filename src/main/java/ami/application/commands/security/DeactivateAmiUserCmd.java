package ami.application.commands.security;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.joda.time.DateTime;

import ami.domain.model.security.hospitals.Hospital;

public class DeactivateAmiUserCmd {
	
	@TargetAggregateIdentifier
    private final String   hospitalId;
	private final String amiUserName;
	private final DateTime deactivationDate;
	private final String deactivatedBy;
	private final String deactivationReason;

	
	public DeactivateAmiUserCmd(String id,String amiUserName,DateTime deactivationDate,String deactivatedBy,String deactivationReason) {
		this.hospitalId                  =  id;
		this.amiUserName 		= amiUserName;
		this.deactivationDate   =  deactivationDate;
		this.deactivatedBy      =  deactivatedBy;
		this.deactivationReason =  deactivationReason;
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


	public String getHospitalId() {
		return hospitalId;
	}




	public String getAmiUserName() {
		return amiUserName;
	}
}
