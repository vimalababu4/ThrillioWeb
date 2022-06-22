package thrillio.Managers;

import java.util.Collection;
import java.util.List;

import thrillio.constants.BookGenre;
import thrillio.constants.KidFriendlyStatus;
import thrillio.constants.MovieGenre;
import thrillio.dao.BookmarkDao;
import thrillio.entities.Book;
import thrillio.entities.Bookmark;
import thrillio.entities.Movie;
import thrillio.entities.User;
import thrillio.entities.UserBookmark;
import thrillio.entities.WebLink;

public class BookmarkManager {
	private static BookmarkManager instance = new BookmarkManager();
	private static BookmarkDao dao = new BookmarkDao();

	private BookmarkManager() {
	}

	public static BookmarkManager getInstance() {
		return instance;
	}

	public Movie createMovie(long id, String title,int releaseYear, String[] cast,
			String[] directors, MovieGenre horror, double imdbRating) {
		Movie movie = new Movie();
		movie.setId(id);
		movie.setTitle(title);
		movie.setReleaseYear(releaseYear);
		movie.setCast(cast);
		movie.setDirectors(directors);
		movie.setGenre(horror);
		movie.setImdbRating(imdbRating);
		return movie;

	}

	public Book createBook(long id, String title,String imageurl, int pblicationYear, long publisherId,
			String[] authors, BookGenre bookGenre, double amazonRating) {
		Book book = new Book();
		book.setId(id);
		book.setTitle(title);
		book.setImage_url(imageurl);
		book.setPublicationYear(pblicationYear);
		book.setPublisherId(publisherId);
		book.setAuthors(authors);
		book.setGenre(bookGenre);
		book.setAmazonRating(amazonRating);
		return book;
	}

	public WebLink createWebLink(long id, String title, String url, String host) {
		WebLink weblink = new WebLink();
		weblink.setId(id);
		weblink.setTitle(title);
		weblink.setUrl(url);
		weblink.setHost(host);
		return weblink;
	}

	public List<List<Bookmark>> getBookmarks() {
		return dao.getBookmarks();
	}

	public void saveUserBookmark(User user, Bookmark bookmark) {
		UserBookmark userBookmark = new UserBookmark(user,bookmark);
		//userBookmark.setUser((user));
		//userBookmark.setBookmark(bookmark);
	
		dao.saveUserBookmark(userBookmark);
	}

	public void setKidFriendlyStatus(User user, KidFriendlyStatus kidFriendlyStatus, Bookmark bookmarkeach) {
		bookmarkeach.setKidFriendlyStatus(kidFriendlyStatus);
		// System.out.println("kid-friendly status:" + kidFriendlyStatus + "," +
		// bookmarkeach);
		System.out.println("Kid-friendly status;" + kidFriendlyStatus + ", marked by" + user.getEmail() + bookmarkeach);

	}

	public void share(User user, Bookmark bookmarkeach) {
		bookmarkeach.setSharedBy(user);
		System.out.println("Data to be shared: ");
		if(bookmarkeach instanceof Book) {
			System.out.println(((Book)bookmarkeach).getItemData());
		}else if(bookmarkeach instanceof WebLink) {
			System.out.println(((WebLink)bookmarkeach).getItemData());
		 }
		
	}

	public Collection<Bookmark> getBooks(boolean isBookmarked, long id) {
		return dao.getBooks(isBookmarked,id);
		
	}

	public Bookmark getBook(long bookId) {
		// TODO Auto-generated method stub
		
		
		System.out.println("bookId in bookmanagerclass"+ bookId);
		return dao.getBook(bookId);
	}

}
