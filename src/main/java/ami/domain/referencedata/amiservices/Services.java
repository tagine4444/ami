package ami.domain.referencedata.amiservices;

public class Services {
	
	private String categoryCode;
	private String categoryName;
	private String name;
	
	public Services(String categoryCode,String categoryName, String name) {
		this.categoryCode = categoryCode;
		this.categoryName = categoryName;
		this.name = name;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public String getName() {
		return name;
	}

}
