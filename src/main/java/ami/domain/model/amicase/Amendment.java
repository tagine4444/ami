package ami.domain.model.amicase;

import org.joda.time.DateTime;

public class Amendment {

	private DateTime creationDate ;
	private String creationDateString ;
	
	private String   userName;
	private String   firstName ;
	private String   lastName ;
	
	public Amendment(DateTime creationDate, String userName,  String firstName,  String lastName) {
		this.creationDate 		= creationDate;    
		this.userName         = userName; 
		this.firstName        = firstName; 
		this.lastName         =lastName; 
		
		 if(creationDate!=null){
			 this.creationDateString = creationDate.toString();
		 }
	}

	public DateTime getCreationDate() {
		return creationDate;
	}

	public String getCreationDateString() {
		return creationDateString;
	}

	public String getUserName() {
		return userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
}
