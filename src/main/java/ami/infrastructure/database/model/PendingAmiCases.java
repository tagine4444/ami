package ami.infrastructure.database.model;

import java.util.List;

public class PendingAmiCases {

	private List<AmiRequestView> amiRequests;
	private List<AmiRequestView> statsAmiRequests;
	
	PendingAmiCases() {
		// TODO Auto-generated constructor stub
	}
	public PendingAmiCases(List<AmiRequestView> amiRequests,List<AmiRequestView> statsAmiRequests) {
		this.amiRequests = amiRequests;
		this.statsAmiRequests = statsAmiRequests;
	}
	
	public List<AmiRequestView> getAmiRequests() {
		return amiRequests;
	}
	public List<AmiRequestView> getStatsAmiRequests() {
		return statsAmiRequests;
	}
	
	
}
