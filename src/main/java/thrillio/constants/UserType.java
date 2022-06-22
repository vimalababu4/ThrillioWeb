package thrillio.constants;

public enum UserType {
	
	User("user"),
	EDITOR("editot"),
	CHIEF_EDITOR("chiefeditor");
	private UserType(String type) {
		this.type =type;
	}
	private String type;
	public String getType() {
		return type;
	}

}
