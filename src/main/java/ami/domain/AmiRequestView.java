package ami.domain;

import org.axonframework.eventhandling.annotation.Timestamp;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonRawValue;

public class AmiRequestView {
	
	@JsonRawValue
	private String amiRequest;
	private String userName;
	private String hospitalName;
	private DateTime time ;
	
	public AmiRequestView( String amiRequest, String userName,
			String hospitalName, @Timestamp DateTime time ) {
		
		this.amiRequest    = amiRequest;   
		this.userName      = userName;     
		this.hospitalName  = hospitalName; 
		this.time = time;
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

	public DateTime getTime() {
		return time;
	}

}
