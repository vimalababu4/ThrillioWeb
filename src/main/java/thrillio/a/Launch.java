package thrillio.a;


import java.util.List;

import thrillio.Managers.BookmarkManager;
import thrillio.Managers.UserManager;
import thrillio.bgJobs.WebpageDownloaderTask;
import thrillio.entities.Bookmark;
import thrillio.entities.User;

public class Launch {
	private static List<User> users;
	private static List<List<Bookmark>> bookmarks;

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		loadData();
		start();
		//background jobs
		runDownloaderJob();	

	}
	private static void runDownloaderJob() {
		WebpageDownloaderTask task = new WebpageDownloaderTask(true);
		(new Thread(task)).start();
	}

	private static void start() {
		//System.out.println("\n2. Browsing...");
		for(User user : users) {
			View.browse(user, bookmarks);
		}
		
	}

	private static void loadData() {
		System.out.println("1. Loading data ..");
		DataStore.loadData();
		users = UserManager.getInstance().getUsers();
		bookmarks = BookmarkManager.getInstance().getBookmarks();
		//System.out.println("printing data...");
		//printUserData();
		//printBookmarkData();
		
		
	}
	
	private static void printBookmarkData() {
		// TODO Auto-generated method stub
		for(List<Bookmark> bookmarkList : bookmarks) {
			for(Bookmark bookmark : bookmarkList) {
			System.out.println(bookmark);
		}
		}
		
	}

	private static void printUserData() {
		// TODO Auto-generated method stub
		for(User user : users) {
			System.out.println(user);
		}
		
	}

}
