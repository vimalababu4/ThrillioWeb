package thrillio.a;

import java.util.List;

//import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import thrillio.constants.KidFriendlyStatus;
import thrillio.controllers.BookmarkController;
import thrillio.entities.Bookmark;
import thrillio.entities.User;

public class View {
	/*
	 * public static void bookmark(User user, Bookmark[][] bookmarks) {
	 
		System.out.println("\n" + user.getEmail() + "is bookmarking");
		for(int i=0;i<DataStore.USER_BOOKMARK_LIMIT;i++) {
			int typeOffset = (int)(Math.random() * DataStore.BOOKMARK_TYPES_COUNT);
			int bookmarkOffset =  (int)(Math.random() * DataStore.BOOKMARKCOUNT_PER_TYPE);
			Bookmark bookmark = bookmarks[typeOffset][bookmarkOffset];
			BookmarkController.getInstance().saveUserBookmark(user,bookmark);
			System.out.println(bookmark);
		}
	}
	*/
	public static void browse(User user, List<List<Bookmark>> bookmarks) {
		System.out.println("\n" + user.getEmail() + "is browsing items...");
		int bookmarkCount =0;
		for(List<Bookmark> bookmarkList : bookmarks) {
			for(Bookmark bookmarkeach : bookmarkList) {
				//if(bookmarkCount < DataStore.USER_BOOKMARK_LIMIT) {
					boolean isBookmarked = getBookmarkDecision(bookmarkeach);
					if(isBookmarked) {
						bookmarkCount++;
					//	BookmarkController.getInstance().saveUserBookmark(user, bookmarkeach);
						System.out.println("New Item Bookmarked "+ bookmarkeach);
						
					}
				//}
			/*	//mark as kidFriendly
				if(user.getUserType().equals(UserType.EDITOR)
						|| user.getUserType().equals(UserType.CHIEF_EDITOR)){
					if(bookmarkeach.isKidsFriendlyEligible() && bookmarkeach.getKidFriendlyStatus().equals(KidFriendlyStatus.UNKNOWN)) {
						KidFriendlyStatus kidFriendlyStatus =getKidFriendlyStatusDecision(bookmarkeach);
						if(!kidFriendlyStatus.equals(KidFriendlyStatus.UNKNOWN)) {
							BookmarkController.getInstance().setKidFriendlyStatus(user,kidFriendlyStatus,bookmarkeach);
							
						}
					}
					//sharing
					if(bookmarkeach.getKidFriendlyStatus().equals(KidFriendlyStatus.APPROVED) 
							&& bookmarkeach instanceof Shareable) {
						boolean isShared = getShareDecision();
						if(isShared) {
							BookmarkController.getInstance().share(user, bookmarkeach);
						}
						
					}
						}
				*/
			}
		}
		
	}
//below methods simulate user input, after IO, we take input via console
	private static boolean getShareDecision() {
		return Math.random() < 0.5 ? true : false;
		
	}

	private static KidFriendlyStatus getKidFriendlyStatusDecision(Bookmark bookmarkeach) {
		double randomVal = Math.random();
		//nested ternary operation
		return randomVal < 0.4 ? KidFriendlyStatus.APPROVED:  
			(randomVal >= 0.4 && randomVal < 0.8) ? KidFriendlyStatus.REJECTED : KidFriendlyStatus.UNKNOWN;
		
	}

	private static boolean getBookmarkDecision(Bookmark bookmark) {
		return Math.random() < 0.5 ? true :false;
		
		
	}


}
