package ami.domain.model.security.hospitals;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Phone implements HospitalAttribute {

	private String label;
	private String value;
	
	public Phone(){}
	
	public Phone(String label, String value) {
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
