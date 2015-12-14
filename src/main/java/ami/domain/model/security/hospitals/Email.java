package ami.domain.model.security.hospitals;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Email implements HospitalAttribute {

	
	private String label;
	private String value;
	
	public Email() {
		// TODO Auto-generated constructor stub
	}
	public Email(String label, String value) {
		this.label = label;
		this.value = value;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public String getValue() {
		return value;
	}
}
