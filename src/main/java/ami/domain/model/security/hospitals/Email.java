package ami.domain.model.security.hospitals;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Email {

	
	private String label;
	private String value;
	
	public Email() {
		// TODO Auto-generated constructor stub
	}
	public Email(String label, String value) {
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
