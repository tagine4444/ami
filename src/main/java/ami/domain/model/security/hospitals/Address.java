package ami.domain.model.security.hospitals;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Address {
	
	private String label;
	private String value;
	
	public Address() {
		// TODO Auto-generated constructor stub
	}
	public Address(String label, String value) {
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public String getValue() {
		return value;
	}
	
	
}
