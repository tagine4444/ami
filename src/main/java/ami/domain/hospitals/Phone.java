package ami.domain.hospitals;

public class Phone {

	private String name;
	private String number;
	
	public Phone(String name, String number) {
		this.name = name;
		this.number = number;
	}
	
	public String getName() {
		return name;
	}
	public String getNumber() {
		return number;
	}
}
