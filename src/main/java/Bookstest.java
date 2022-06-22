import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import groovy.util.logging.Log4j;
import thrillio.Managers.BookmarkManager;
import thrillio.Managers.UserManager;
import thrillio.a.DataStore;
import thrillio.bgJobs.WebpageDownloaderTask;
import thrillio.constants.KidFriendlyStatus;
import thrillio.dao.BookmarkDao;
import thrillio.dao.UserDao;
import thrillio.entities.Book;
import thrillio.entities.Bookmark;
import thrillio.entities.User;
import thrillio.entities.UserBookmark;
import thrillio.entities.WebLink;
import thrillio.util.IOUtil;
import thrillio.util.StringUtil;

public class Bookstest {
	
	public static void main(String[] args) {
		//JMS jms
		//Log4j logger = new
	/*
		User user = UserManager.getInstance().getUser(5);
	//	System.out.println("id of User "+user.getId());
		Bookmark book =BookmarkManager.getInstance().getBook(4);
	//	System.out.println("id of Book "+book.getId());
		//System.out.println(book);
		BookmarkDao dao= new BookmarkDao();
		UserBookmark ub=new UserBookmark(user,book);
		
		dao.saveUserBookmark(ub);
		DataStore d= new DataStore();
		//System.out.println(ub.getUser().getId());
	//	System.out.println("id of Book "+ub.getBookmark().getId());
		//System.out.println(d.getBookmarks());
		
		Collection<Bookmark> books=dao.getBooks(true, 5);
		System.out.println("\n saved books " +books);
		StringUtil util = new StringUtil();
		String s=util.encodePassword("test");
		System.out.println(s);
		
	
		long ll=UserManager.getInstance().authenticate("user0@semanticsquare.com", "test");
		System.out.println("ll  "+ll);
		
		UserDao dao= new UserDao();
		long userId=dao.authenticate("user0@semanticsquare.com", "a94a8fe5ccb19ba61c4c0873d391e987982fbbd3 ");
		
		System.out.println("userid: "+userId);*/
		//List<String> data = new ArrayList<>();
		//String file = "C:\\Users\\HP\\Desktop\\WebLink.txt";
		//IOUtil.read(data, file);
		//WebpageDownloaderTask webpagDownload = new WebpageDownloaderTask(true);
	//	(new Thread(task)).start();
		/*List<WebLink> list2= new ArrayList<WebLink>();
		WebLink link = new WebLink();
			for(String s: data) {
				String[] line= s.split("\t");
				link.setId(Long.parseLong(line[0]));
				link.setTitle(line[1]);
				link.setUrl(line[2]);
				link.setHost(line[3]);
				list2.add(link);
				String webPage=String.valueOf(link.getId());
				if(webPage != null) {
					IOUtil.write(webPage,link.getId());
			}
		
			}*/
		
		boolean isBookmarked =  Math.random() < 0.5 ? true :false;
		System.out.println(isBookmarked);
	
		//System.out.println(list2);
		//webpagDownload.download(list2);// you have shutdown collable also be careful
		
	}
}