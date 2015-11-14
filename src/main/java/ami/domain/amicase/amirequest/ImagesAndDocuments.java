package ami.domain.amicase.amirequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ImagesAndDocuments {

	private String	labAccount; 
	private String	labs; 
	private boolean hasDocumentDeliveredByUpload;
	private boolean hasDocumentDeliveredByCarrier;
	private boolean hasDocumentDeliveredByEmail;
	private String notes;
	
     ImagesAndDocuments() {
	}
	
	public ImagesAndDocuments( 
			String	labAccount,
			String	labs,
			boolean hasDocumentDeliveredByUpload,
			boolean hasDocumentDeliveredByCarrier,
			boolean hasDocumentDeliveredByEmail,
			String notes) {
		
		  this.labAccount = labAccount;
		  this.labs = labs;
		  this.hasDocumentDeliveredByUpload = hasDocumentDeliveredByUpload;
		  this.hasDocumentDeliveredByCarrier = hasDocumentDeliveredByCarrier;
		  this.hasDocumentDeliveredByEmail = hasDocumentDeliveredByEmail;
		  this.notes = notes ;
	}


	public boolean isHasDocumentDeliveredByUpload() {
		return hasDocumentDeliveredByUpload;
	}


	public boolean isHasDocumentDeliveredByCarrier() {
		return hasDocumentDeliveredByCarrier;
	}


	public boolean isHasDocumentDeliveredByEmail() {
		return hasDocumentDeliveredByEmail;
	}


	public String getNotes() {
		return notes;
	}

	public String getLabAccount() {
		return labAccount;
	}

	public String getLabs() {
		return labs;
	}
	
}
