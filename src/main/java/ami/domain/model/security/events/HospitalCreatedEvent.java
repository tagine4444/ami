package ami.domain.model.security.events;

import org.joda.time.DateTime;

import ami.domain.model.security.hospitals.Hospital;

public class HospitalCreatedEvent {
	
	
    private final Hospital hospital;
    private final DateTime hospitalActivationDate;

    public HospitalCreatedEvent( Hospital hospital,  DateTime hospitalActivationDate) {
        this.hospital = hospital;
        this.hospitalActivationDate = hospitalActivationDate;
    }

	public Hospital getHospital() {
		return hospital;
	}

	public DateTime getHospitalActivationDate() {
		return hospitalActivationDate;
	}

}
