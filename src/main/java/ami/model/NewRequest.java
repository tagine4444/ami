package ami.model;

public class NewRequest {
	
	private PatientInfo patientInfo;
	private HospitalAndClientInfo hospitalAndClientInfo;
	private RequestedServices requestedServices;
	private ImagesAndDocument imagesAndDocs; 
	private VeterinarianObservation vetObs;
	
	public NewRequest(  PatientInfo patientInfo,
						HospitalAndClientInfo hospitalAndClientInfo, 
						RequestedServices requestedSvc, 
						ImagesAndDocument imagesAndDocs, 
						VeterinarianObservation vetObs)
	{
	
		this.patientInfo = patientInfo;
		this.hospitalAndClientInfo = hospitalAndClientInfo;
		this.requestedServices = requestedSvc;
		this.imagesAndDocs = imagesAndDocs;
		this.vetObs = vetObs;
	}

	public PatientInfo getPatientInfo() {
		return patientInfo;
	}

	public HospitalAndClientInfo getHospitalAndClientInfo() {
		return hospitalAndClientInfo;
	}

	public RequestedServices getRequestedServices() {
		return requestedServices;
	}

	public ImagesAndDocument getImagesAndDocs() {
		return imagesAndDocs;
	}

	public VeterinarianObservation getVetObs() {
		return vetObs;
	}

}
