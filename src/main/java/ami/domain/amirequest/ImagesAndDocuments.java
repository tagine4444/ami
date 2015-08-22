package ami.domain.amirequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ImagesAndDocuments {

	private boolean hasDocumentDeliveredByUpload;
	private boolean hasDocumentDeliveredByCarrier;
	private boolean hasDocumentDeliveredByEmail;
	private String notes;
	
     ImagesAndDocuments() {
	}
	
	public ImagesAndDocuments( boolean hasDocumentDeliveredByUpload,
			boolean hasDocumentDeliveredByCarrier,
			boolean hasDocumentDeliveredByEmail,
			String notes) {
		
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
	
}
