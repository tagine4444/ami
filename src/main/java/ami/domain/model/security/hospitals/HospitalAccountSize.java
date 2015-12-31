package ami.domain.model.security.hospitals;

public enum HospitalAccountSize {

	SMALL("Small"),
	MEDIUM("Medium"),
	LARGE("Large"),
	XLARGE("Extra Large");
	
	private final String name;
	
	private HospitalAccountSize(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
}
