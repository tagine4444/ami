package ami.domain.model.security.hospitals;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Hospital {
	
	private String id;
	private String name;
	private String acronym;
	private List<Address> addresses;
	private List<Phone> phones;
	private List<Email> emails;// default emails where the reports are sent
	
	// only for object mapper to turn json into Object
	public Hospital(){}
	
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

	public void init(String id){
		this.id = id;
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
