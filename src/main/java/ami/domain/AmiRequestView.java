package ami.domain;

import com.fasterxml.jackson.annotation.JsonRawValue;

public class AmiRequestView {
	
	@JsonRawValue
	private String amiRequest;
	private String userName;
	private String hospitalName;
	
	public AmiRequestView( String amiRequest, String userName,
			String hospitalName ) {
		
		this.amiRequest    = amiRequest;   
		this.userName      = userName;     
		this.hospitalName  = hospitalName; 
	}

//	@JsonValue
//    @JsonRawValue
	public String getAmiRequest() {
		return amiRequest;
	}
	
//	@JsonProperty
	public String getUserName() {
		return userName;
	}
//	@JsonProperty
	public String getHospitalName() {
		return hospitalName;
	}

}
