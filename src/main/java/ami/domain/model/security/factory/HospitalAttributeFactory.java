package ami.domain.model.security.factory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ami.domain.model.security.hospitals.Address;
import ami.domain.model.security.hospitals.Email;
import ami.domain.model.security.hospitals.Phone;

public class HospitalAttributeFactory {
	
	public static List<Phone> getPhonesFromMap(List<Map<String, String>> updatedPhoneListOfMaps) {
		
		List<Phone> list = new ArrayList<Phone>();
		if(updatedPhoneListOfMaps==null || updatedPhoneListOfMaps.isEmpty()){
			return list;
		}
		
		Iterator<Map<String, String>> it = updatedPhoneListOfMaps.iterator();
		while(it.hasNext()){
			Map<String, String> aMap = it.next();
			String label = aMap.get("label");
			String value =aMap.get("value");
			
			list.add(new Phone(label, value));
			
		}
		
		return list;
	}
	
	public static List<Email> getEmailsFromMap(List<Map<String, String>> updatedPhoneListOfMaps) {
		
		List<Email> list = new ArrayList<Email>();
		if(updatedPhoneListOfMaps==null || updatedPhoneListOfMaps.isEmpty()){
			return list;
		}
		
		Iterator<Map<String, String>> it = updatedPhoneListOfMaps.iterator();
		while(it.hasNext()){
			Map<String, String> aMap = it.next();
			String label = aMap.get("label");
			String value =aMap.get("value");
			
			list.add(new Email(label, value));
		}
		
		return list;
	}

	public static List<Address> getAddressesFromMap(
			List<Map<String, String>> updatedAddressesListOfMaps) {
		
		List<Address> list = new ArrayList<Address>();
		if(updatedAddressesListOfMaps==null || updatedAddressesListOfMaps.isEmpty()){
			return list;
		}
		
		Iterator<Map<String, String>> it = updatedAddressesListOfMaps.iterator();
		while(it.hasNext()){
			Map<String, String> aMap = it.next();
			String label = aMap.get("label");
			String value =aMap.get("value");
			
			list.add(new Address(label, value));
		}
		
		return list;
	}
	
	

}
