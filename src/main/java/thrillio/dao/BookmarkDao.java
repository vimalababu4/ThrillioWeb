package thrillio.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import thrillio.Managers.BookmarkManager;
import thrillio.a.DataStore;
import thrillio.constants.BookGenre;
import thrillio.entities.Book;
import thrillio.entities.Bookmark;
import thrillio.entities.Movie;
import thrillio.entities.UserBookmark;
import thrillio.entities.WebLink;

public class BookmarkDao {
	public List<List<Bookmark>> getBookmarks() {
		return DataStore.getBookmarks();
	}

	public void saveUserBookmark(UserBookmark userBookmark) {
		//DataStore.add(userBookmark);
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_test?useSSL=false","root", "password");
				Statement stmt = conn.createStatement();){
			if(userBookmark.getBookmark() instanceof Book) {
				saveUserBook(userBookmark,stmt);
			}else if(userBookmark.getBookmark() instanceof Movie) {
				saveUserMovie(userBookmark,stmt);
			}else {
				saveUserWeblink(userBookmark,stmt);
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void saveUserWeblink(UserBookmark userBookmark, Statement stmt) throws SQLException {
		String query= "insert into User_Weblink(user_id,weblink_id) values("+userBookmark.getUser().getId() +" ,"+ userBookmark.getBookmark().getId()+")";
		System.out.println("Query  "+query);
		stmt.executeUpdate(query);
		
	}

	private void saveUserMovie(UserBookmark userBookmark, Statement stmt) throws SQLException {
		String query= "insert into User_Movie(user_id,movie_id) values("+userBookmark.getUser().getId() +" ,"+ userBookmark.getBookmark().getId()+")";
		System.out.println("Query  "+query);
		stmt.executeUpdate(query);
		
	}

	private void saveUserBook(UserBookmark userBookmark, Statement stmt) throws SQLException {
		String query= "insert into User_Book(user_id,book_id) values("+userBookmark.getUser().getId() +" ,"+ userBookmark.getBookmark().getId()+")";
		System.out.println("Query  "+query);
		stmt.executeUpdate(query);
		
	}

	//in real applications, we would have sql or hibernate queries	
	public List<WebLink> getAllWebLinks(){
		List<WebLink> result= new ArrayList<>();
		List<List<Bookmark>> bookmarks = DataStore.getBookmarks();
		List<Bookmark> allWebLinks = bookmarks.get(0);
		for(Bookmark bookmark : allWebLinks) {
			result.add((WebLink)bookmark);
		}
		return result;
	}
	public List<WebLink> getWebLinks(WebLink.DownloadStatus downloadStatus){
		List<WebLink> result= new ArrayList<>();
		List<WebLink> allWebLinks = getAllWebLinks();
		for(WebLink webLink : allWebLinks) {
			if(webLink.getDownloadStatus().equals(downloadStatus)) {
				result.add(webLink);
		}
		}
		return result;
	}

public Collection<Bookmark> getBooks(boolean isBookmarked, long userId) {
		
		Collection<Bookmark> result = new ArrayList<>();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			//new com.mysql.jdbc.Driver(); 
			            // OR
			//System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
		
		                // OR java.sql.DriverManager
		    //DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_test?useSSL=false", "root", "abhIruchI*123$");
				Statement stmt = conn.createStatement();) {			
			
			String query = "";
			if (!isBookmarked) {
				query = "Select b.id, title, image_url, publication_year,publisher_id, GROUP_CONCAT(a.name SEPARATOR ',') AS authors, book_genre_id, " +
									"amazon_rating from Book b, Author a, Book_Author ba where b.id = ba.book_id and ba.author_id = a.id and " + 
									"b.id NOT IN (select ub.book_id from User u, User_Book ub where u.id = " + userId +
									" and u.id = ub.user_id) group by b.id";			
				System.out.println("\ngetBooks query "+ query);
			} else {
				query = "Select b.id, title, image_url, publication_year, publisher_id, GROUP_CONCAT(a.name SEPARATOR ',') AS authors, book_genre_id, " +
						"amazon_rating from Book b, Author a, Book_Author ba where b.id = ba.book_id and ba.author_id = a.id and " + 
						"b.id IN (select ub.book_id from User u, User_Book ub where u.id = " + userId +
						" and u.id = ub.user_id) group by b.id";
				System.out.println("\ngetBooks query "+ query);
			}
			
			ResultSet rs = stmt.executeQuery(query);				
			
	    	while (rs.next()) {
	    		long id = rs.getLong("id");
				String title = rs.getString("title");
				String imageUrl = rs.getString("image_url");
				int publicationYear = rs.getInt("publication_year");
				int publisherId = rs.getInt("publisher_Id");
				String[] authors = rs.getString("authors").split(",");			
				int genre_id = rs.getInt("book_genre_id");
				BookGenre genre = BookGenre.values()[genre_id];
				double amazonRating = rs.getDouble("amazon_rating");
				
				//System.out.println("id: " + id + ", title: " + title + ", publication year: " + publicationYear + ", authors: " + String.join(", ", authors) + ", genre: " + genre + ", amazonRating: " + amazonRating);
	    		
	    		Bookmark bookmark = BookmarkManager.getInstance().createBook(id, title, imageUrl, publicationYear, publisherId, authors, genre, amazonRating);
	    		result.add(bookmark); 
	    	}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

public Bookmark getBook(long bookId) {
	Book book = null;
	System.out.println("bookId in bookmark Dao class "+ bookId);
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
	
	try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_test?useSSL=false", "root", "abhIruchI*123$");
			Statement stmt = conn.createStatement();) {
		String query = "Select b.id, title, image_url, publication_year, p.id, GROUP_CONCAT(a.name SEPARATOR ',') AS authors, book_genre_id, amazon_rating, created_date"
				+ " from Book b, Publisher p, Author a, Book_Author ba "
				+ "where b.id = " + bookId + "  and b.publisher_id = p.id and b.id = ba.book_id and ba.author_id = a.id group by b.id";
    	ResultSet rs = stmt.executeQuery(query);
		
    	while (rs.next()) {
    		long id = rs.getLong("b.id");
			String title = rs.getString("title");
			String imageUrl = rs.getString("image_url");
			int publicationYear = rs.getInt("publication_year");
			long publisher = rs.getLong("p.id");		
			String[] authors = rs.getString("authors").split(",");			
			int genre_id = rs.getInt("book_genre_id");
			BookGenre genre = BookGenre.values()[genre_id];
			double amazonRating = rs.getDouble("amazon_rating");
			
			System.out.println("id: " + id + ", title: " + title + ", publication year: " + publicationYear + ", publisher: " + publisher + ", authors: " + String.join(", ", authors) + ", genre: " + genre + ", amazonRating: " + amazonRating);
    		
    		book = BookmarkManager.getInstance().createBook(id, title, imageUrl, publicationYear, publisher, authors, genre, amazonRating/*, values[7]*/);
    	}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return book;
}
	

}
