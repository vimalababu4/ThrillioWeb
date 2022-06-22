package thrillio.entities;

public class UserBookmark {
	private User user;
	private Bookmark bookmark;
	public UserBookmark(User user, Bookmark bookmark){
		this.user=user;
		this.bookmark=bookmark;
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Bookmark getBookmark() {
		return bookmark;
	}

	public void setBookmark(Bookmark bookmark) {
		this.bookmark = bookmark;
	}

	
	

}
