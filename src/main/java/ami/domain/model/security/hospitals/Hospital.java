package ami.domain.model.security.hospitals;

import java.util.List;

public class Hospital {
	
	private String id;
	private String name;
	private String acronym;
	private List<Address> addresses;
	private List<Phone> phones;
	private List<Email> emails;// default emails where the reports are sent
	
	public Hospital(String id,
					String name,
					String acronym,
					List<Address> addresses,
					List<Phone> phones,
					List<Email>emails) {
		
		this.id       = id       ;
		this.name     = name     ;
		this.acronym  = acronym     ;
		this.addresses  = addresses  ;
		this.phones   = phones   ;
		this.emails   = emails;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public List<Email> getEmails() {
		return emails;
	}

	public String getAcronym() {
		return acronym;
	}

	public List<Address> getAddresses() {
		return addresses;
	}
	
}
