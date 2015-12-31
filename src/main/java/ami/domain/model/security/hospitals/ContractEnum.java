package ami.domain.model.security.hospitals;

public enum ContractEnum {

	CONTRACT("Contract"),
	NOT_CONTRACT("Not Contract");
	
	private final String name;
	
	private ContractEnum(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
}
