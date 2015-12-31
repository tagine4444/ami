package ami.domain.model.amicase.events;

import ami.domain.model.amicase.amirequest.AmiRequest;

public class AmiRequestSavedAsDraftEvent {

	private final String id;
	private final AmiRequest amiRequestJson;
	private final String userName;
	private String hospitalName;
	private String hospitalId;
	private final String contract;
	private final String accountSize;
	

    public AmiRequestSavedAsDraftEvent(String id, AmiRequest amiRequestJson, String userName, 
    		String hospitalName,String hospitalId, String contract, String accountSize
    		) {
        this.id = id;
        this.amiRequestJson = amiRequestJson;
        this.userName = userName;
        this.hospitalName = hospitalName;
        this.hospitalId   = hospitalId;
        this.contract = contract;
        this.accountSize = accountSize;
  
    }
    
    public String getUserName() {
		return userName;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public String getId() {
		return id;
	}

	public AmiRequest getAmiRequestJson() {
		return amiRequestJson;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public String getContract() {
		return contract;
	}

	public String getAccountSize() {
		return accountSize;
	}

}
