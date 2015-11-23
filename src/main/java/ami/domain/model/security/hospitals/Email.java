package ami.domain.model.security.hospitals;

public class Email {

	private String label;
	private String value;
	
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
