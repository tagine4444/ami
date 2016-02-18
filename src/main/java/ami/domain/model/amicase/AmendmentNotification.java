package ami.domain.model.amicase;

import org.joda.time.DateTime;


public class AmendmentNotification {

	private int amendmentId;
	private String  caseNumber;
	private Amendment amendment;
	private boolean adminAware;
	private DateTime creationDate ;
//	private String creationDateString ;
	
	public AmendmentNotification(String caseNumber, Amendment amendment) {
		
		this.amendmentId   = amendment.getId();
		this.caseNumber    =caseNumber;
		this.amendment     = amendment;
		//if amendment is from customer, then admin (chuck) is NOT aware.
		this.adminAware    = !amendment.isCustomerAmendment();
		// if amendment is from customer, then customer IS aware
		// customer is always aware as an email is sent.
		this.creationDate  = new DateTime();
//		this.creationDateString = creationDate.toString();
		
	}
	
	public Amendment getAmendment() {
		return amendment;
	}

	public boolean isAdminAware() {
		return adminAware;
	}


	public DateTime getCreationDate() {
		return creationDate;
	}

//	public String getCreationDateString() {
//		return creationDateString;
//	}

	public int getAmendmentId() {
		return amendmentId;
	}

	public String getCaseNumber() {
		return caseNumber;
	}

}