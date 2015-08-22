package ami.domain.hospitals;

import java.util.List;

public class Hospital {
	
	private String id;
	private String name;
	private String address;
	private String mainPhone;
	private String mainFax;
	private String fax2;
	private List<Phone> phones;
	
	public Hospital(String id,
					String name,
					String address,
					String mainPhone,
					String mainFax,
					String fax2,
					List<Phone> phones) {
		
		this.id       = id       ;
		this.name     = name     ;
		this.address  = address  ;
		this.mainPhone= mainPhone;
		this.mainFax  = mainFax  ;
		this.fax2     = fax2     ;
		this.phones   = phones   ;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getMainPhone() {
		return mainPhone;
	}

	public String getMainFax() {
		return mainFax;
	}

	public String getFax2() {
		return fax2;
	}

	public List<Phone> getPhones() {
		return phones;
	}
	
}
