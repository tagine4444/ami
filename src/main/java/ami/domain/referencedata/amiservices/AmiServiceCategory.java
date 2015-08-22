package ami.domain.referencedata.amiservices;

public enum AmiServiceCategory {

	
	CONTRAST_RADIOGRAPHY("CONTRAST_RADIOGRAPHY","Contrast Radiography"),
	COMPUTED_TOMOGRAPHY("COMPUTED_TOMOGRAPHY","Computed Tomograhpy"),
	RADIOGRAPHY_FLUOROSCOPY("RADIOGRAPHY_FLUOROSCOPY","Radiography Fluoroscopy"),
	ULTRASOUND("ULTRASOUND","Ultrasound");
	
	private final String code;
	private final String name;
	
	AmiServiceCategory(String code, String name){
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	
	
	
}
