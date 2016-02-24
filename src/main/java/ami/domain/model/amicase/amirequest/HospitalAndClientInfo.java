package ami.domain.model.amicase.amirequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class HospitalAndClientInfo {
	
//	private String	labAccount; 
//	private String	labs; 
	private String	vet; 
	
	private String	clientFirstName; 
	private String	clientLastName; 
	private String	clientId; 
	private boolean	isEmployee;
	
	HospitalAndClientInfo(){
		
	}
	
	public HospitalAndClientInfo(
//			String	labs,           
			String	vet,            
//			String	labAccount,     
			String	clientFirstName,
			String	clientLastName, 
			String	clientId,       
			boolean	isEmployee) {
		
//		this.labs = labs;           
//		this.labAccount = labAccount;     
		this.vet = vet;            
		this.clientFirstName = clientFirstName;
		this.clientLastName = clientLastName; 
		this.clientId = clientId;       
		this.isEmployee = isEmployee;
	}
	
//	public String getLabs() {
//		return labs;
//	}
	public String getVet() {
		return vet;
	}
//	public String getLabAccount() {
//		return labAccount;
//	}
	public String getClientFirstName() {
		return clientFirstName;
	}
	public String getClientLastName() {
		return clientLastName;
	}
	public String getClientId() {
		return clientId;
	}
	public boolean getIsEmployee() {
		return isEmployee;
	}

	
}
