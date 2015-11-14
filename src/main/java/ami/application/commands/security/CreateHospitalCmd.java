package ami.application.commands.security;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.joda.time.DateTime;

import ami.domain.model.security.hospitals.Hospital;

public class CreateHospitalCmd {


	@TargetAggregateIdentifier
    private final String hospitalId;
    private Hospital hospital;
    private final DateTime hospitalActivationDate;

    public CreateHospitalCmd( Hospital hospital,DateTime hospitalActivationDate) {
        this.hospitalId = hospital.getId();
        this.hospital = hospital;
        this.hospitalActivationDate = hospitalActivationDate;
    }

	


	public Hospital getHospital() {
		return hospital;
	}

	public DateTime getHospitalActivationDate() {
		return hospitalActivationDate;
	}


	public String getHospitalId() {
		return hospitalId;
	}
}
