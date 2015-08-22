package ami.domain.amirequest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RequestedServices {

	private boolean isInterpretationOnly;
	private boolean isStat;
	private List<String> selectedServices;
	
    RequestedServices() {
	}
	public RequestedServices(boolean isInterpretationOnly,boolean isStat, List<String> selectedServices) {
		this.isInterpretationOnly = isInterpretationOnly;
		this.isStat = isStat;
		this.selectedServices = selectedServices;
	}


	public boolean getIsInterpretationOnly() {
		return isInterpretationOnly;
	}


	public boolean getIsStat() {
		return isStat;
	}


	public List<String> getSelectedServices() {
		return selectedServices;
	}


}
