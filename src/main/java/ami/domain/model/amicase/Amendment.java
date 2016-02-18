package ami.domain.model.amicase;

import org.joda.time.DateTime;

public class Amendment {

	private int id;
	private String newAmendment;
	private DateTime creationDate ;
//	private String creationDateString ;
	
	private String   userName;
	private String   firstName ;
	private String   lastName ;
	
	private String occupation;
	private String hospitalName;
	private String hospitalId;
	private boolean customerAmendment; // customerAmendment is the one from the customer vs the one from admin (like AMI).
	
	public Amendment(int id, String newAmendment, DateTime creationDate, String userName,  String firstName,  String lastName,
			String occupation,String hospitalName, String hospitalId, boolean customerAmendment) {
		this.id = id;
		this.newAmendment     = newAmendment;
		this.creationDate 	  = creationDate;    
		this.userName         = userName; 
		this.firstName        = firstName; 
		this.lastName         = lastName; 
		this.occupation		  = occupation;  
		this.hospitalName	  = hospitalName;
		this.hospitalId		  = hospitalId;  
		this.customerAmendment = customerAmendment;
		
//		 if(creationDate!=null){
//			 this.creationDateString = creationDate.toString();
//		 }
	}

	public DateTime getCreationDate() {
		return creationDate;
	}

//	public String getCreationDateString() {
//		return creationDateString;
//	}

	public String getUserName() {
		return userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getNewAmendment() {
		return newAmendment;
	}

	public String getOccupation() {
		return occupation;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public boolean isCustomerAmendment() {
		return customerAmendment;
	}

	public int getId() {
		return id;
	}
}
