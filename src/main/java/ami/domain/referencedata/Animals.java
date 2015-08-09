package ami.domain.referencedata;

import java.util.List;

public class Animals {

	private String id;
	private List<String> breeds;
	
	public Animals(String id, List<String> breeds) {
		this.id = id;
		this.breeds = breeds;
	}

	public String getId() {
		return id;
	}

	public List<String> getBreeds() {
		return breeds;
	}
}
