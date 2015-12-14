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
	private String notes;
	
	// only for object mapper to turn json into Object
	public Hospital(){}
	
	public Hospital(String id,
					String name,
					String acronym,
					List<Address> addresses,
					List<Phone> phones,
					List<Email>emails,
					String notes) {
		
		this.id       = id       ;
		this.name     = name     ;
		this.acronym  = acronym     ;
		this.addresses  = addresses  ;
		this.phones   = phones   ;
		this.emails   = emails;
		this.notes = notes;
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

	public String getNotes() {
		return notes;
	}
	
	public void replacePhoneList(List<Phone> phoneList){
		
		this.phones = phoneList;
		
	}

	public void replaceEmailsList(List<Email> newEmailList) {
		this.emails = newEmailList;
		
	}

	public void replaceAddressList(List<Address> newAddressList) {
		this.addresses = newAddressList;
		
	}

	public void replaceAcronym(String newAcronym) {
		this.acronym = newAcronym;
	}

	public void replaceNotes(String newNotes) {
		this.notes = newNotes;
	}
	
}
