package ami.application.events.security;

import org.joda.time.DateTime;

public class HospitaDeactivatedEvent {
	
	  	private final String   hospitalId;
		private final DateTime hospitalDeactivationDate;
		private final String   hospitalDeactivatedBy;
		private final String   hospitalDeactivationReason;
		
		public HospitaDeactivatedEvent(String hospitalId,
				DateTime hospitalDeactivationDate,
				String hospitalDeactivatedBy,
				String hospitalDeactivationReason) {
			
			 this.hospitalId                      =  hospitalId;
		     this.hospitalDeactivationDate = hospitalDeactivationDate ;
	         this.hospitalDeactivatedBy    =  hospitalDeactivatedBy;
	         this.hospitalDeactivationReason=  hospitalDeactivationReason;
	    }
		

		public String getHospitalId() {
			return hospitalId;
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

}
