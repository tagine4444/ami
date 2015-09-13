package ami.domain.amirequest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AmiRequest {
	
	private String requestNumber;
	private HospitalAndClientInfo hospitalAndClientInfo;
	private PatientInfo patientInfo;
	private RequestedServices requestedServices;
	private VetObservation vetObservation;
	private ImagesAndDocuments imagesAndDocuments;
	
	
	public AmiRequest() {
	}
	
	public AmiRequest(String requestNumber,HospitalAndClientInfo hospitalAndClientInfo,
			PatientInfo patientInfo, RequestedServices requestedServices,        
			VetObservation vetObservation,ImagesAndDocuments imagesAndDocuments ) {
		
		this.requestNumber        =  requestNumber;                  
		this.hospitalAndClientInfo = hospitalAndClientInfo;
		this.patientInfo           = patientInfo;               
		this.requestedServices     = requestedServices;      
		this.vetObservation        = vetObservation;        
		this.imagesAndDocuments    = imagesAndDocuments;
	}
	
	public String getRequestNumber() {
		return requestNumber;
	}
	public HospitalAndClientInfo getHospitalAndClientInfo() {
		return hospitalAndClientInfo;
	}
	public PatientInfo getPatientInfo() {
		return patientInfo;
	}
	public RequestedServices getRequestedServices() {
		return requestedServices;
	}
	public VetObservation getVetObservation() {
		return vetObservation;
	}
	public ImagesAndDocuments getImagesAndDocuments() {
		return imagesAndDocuments;
	}
	
	public boolean hasRequestNumber(){
		return ! StringUtils.isEmpty(this.requestNumber);
		
	}

	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
		
	}

}
