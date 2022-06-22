package thrillio.constants;

public enum KidFriendlyStatus {
	
	APPROVED("approved"),
	REJECTED("rejected"),
	UNKNOWN("unknown");
	
	private KidFriendlyStatus(String status) {
		this.status = status;
	}  // need not be instantiated
	private String status;
	public String getStatus() {
		return status;
	}
}
