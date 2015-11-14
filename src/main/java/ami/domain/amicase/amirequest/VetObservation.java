package ami.domain.amicase.amirequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class VetObservation {

	private boolean anesthetized;
	private boolean sedated;
	private boolean fasted;
	private boolean enema;
	private boolean painful;
	private boolean fractious;
	private boolean shocky;
	private boolean dyspneic;
	private boolean died;
	private boolean euthanized;
	private String exam;
	private String tentativeDiagnosis;

	 VetObservation() {
	}
	public VetObservation(boolean anesthetized,
			boolean sedated,
			boolean fasted,
			boolean enema,
			boolean painful,
			boolean fractious,
			boolean shocky,
			boolean dyspneic,
			boolean died,
			boolean euthanized,
			String exam,
			String tentativeDiagnosis) {
		
		this.anesthetized =  sedated;
		this.sedated =  sedated;
		this.fasted = fasted ;
		this.enema =  enema;
		this.painful = painful ;
		this.fractious = fractious ;
		this.shocky =  shocky;
		this.dyspneic = dyspneic ;
		this.died = died ;
		this.euthanized = euthanized ;
		this.exam =  exam;
		this.tentativeDiagnosis = tentativeDiagnosis ;
		
	}

	public boolean isAnesthetized() {
		return anesthetized;
	}

	public boolean isSedated() {
		return sedated;
	}

	public boolean isFasted() {
		return fasted;
	}

	public boolean isEnema() {
		return enema;
	}

	public boolean isPainful() {
		return painful;
	}

	public boolean isFractious() {
		return fractious;
	}

	public boolean isShocky() {
		return shocky;
	}

	public boolean isDyspneic() {
		return dyspneic;
	}

	public boolean isDied() {
		return died;
	}

	public boolean isEuthanized() {
		return euthanized;
	}

	public String getExam() {
		return exam;
	}

	public String getTentativeDiagnosis() {
		return tentativeDiagnosis;
	} 
	

}
