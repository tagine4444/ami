package ami.application.commands.security;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.joda.time.DateTime;

import ami.domain.hospitals.Hospital;

public class DeactivateHospitalCmd {

	@TargetAggregateIdentifier
    private final String   hospitalId;
	private Hospital hospital;
	private final DateTime hospitalDeactivationDate;
	private final String   hospitalDeactivatedBy;
	private final String   hospitalDeactivationReason;
	
	public DeactivateHospitalCmd(String hospitalId,
			Hospital hospital,
			DateTime hospitalDeactivationDate,
			String hospitalDeactivatedBy,
			String hospitalDeactivationReason) {
		 this.hospitalId                      =  hospitalId;
		 this.hospital 				  = hospital;
	     this.hospitalDeactivationDate = hospitalDeactivationDate ;
         this.hospitalDeactivatedBy    =  hospitalDeactivatedBy;
         this.hospitalDeactivationReason=  hospitalDeactivationReason;
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

	public Hospital getHospital() {
		return hospital;
	}



	public String getHospitalId() {
		return hospitalId;
	}
}