package ami.domain.amicase;

import java.util.List;

public class Animals {

	private String id;
	private String name;
	private List<String> breeds;
	
	public Animals(String id, String name, List<String> breeds) {
		this.id = id;
		this.name = name;
		this.breeds = breeds;
	}

	public String getId() {
		return id;
	}

	public List<String> getBreeds() {
		return breeds;
	}

	public String getName() {
		return name;
	}
}
