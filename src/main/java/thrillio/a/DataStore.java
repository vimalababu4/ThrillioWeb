package thrillio.a;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import thrillio.Managers.BookmarkManager;
import thrillio.Managers.UserManager;
import thrillio.constants.BookGenre;
import thrillio.constants.Gender;
import thrillio.constants.MovieGenre;
import thrillio.entities.Bookmark;
import thrillio.entities.User;
import thrillio.entities.UserBookmark;

public class DataStore {
	
	/*public static final int USER_BOOKMARK_LIMIT = 5;
	public static final int BOOKMARKCOUNT_PER_TYPE = 5;
	public static final int BOOKMARK_TYPES_COUNT = 3;
	public static final int TOTAL_USER_COUNT = 5;*/
	public static List<User> users = new ArrayList<>();
	public static List<User> getUsers() {
		return users;
	}
	//private static Bookmark[][] bookmarks = new Bookmark[BOOKMARK_TYPES_COUNT][BOOKMARKCOUNT_PER_TYPE]; //one for type, other for actual bookmark, each type can have 5 bookmarks
	private static List<List<Bookmark>>  bookmarks = new  ArrayList<>();
	public static List<List<Bookmark>> getBookmarks() {
		return bookmarks;
	}
	private static List<UserBookmark> userBookmark = new ArrayList<>();//[TOTAL_USER_COUNT * USER_BOOKMARK_LIMIT];//one to hold user bookmark
	//private static int bookmarkIndex=0;
	//LOADING DATA
	public static void loadData() {
		/*loadUsers();
		LoadWebLinks();
		loadMovies();
		loadBooks();*/
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			//new com.mysql.jdbc.Driver();
			//System.serProperty("jdbc.drivers","com.mysql.jdbc.Driver");
			//DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		//try with resources ==> conn & stmt will be closed
		//Connection string: < protocol>:>sub-procol>:<data.source details>
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_test?useSSL=false","root", "");
				Statement stmt = conn.createStatement();){
			loadUsers(stmt);
			LoadWebLinks(stmt);
			loadMovies(stmt);
			loadBooks(stmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private static void loadBooks(Statement stmt) throws SQLException {
		/*bookmarks[2][0] =BookmarkManager.getInstance().createBook(4000,"Walden","",1854,"Wilder Publications",new String[] {"Henry David Thoreau"},BookGenre.PHILOSOPHY,4.3);
		bookmarks[2][1] =BookmarkManager.getInstance().createBook(4001,"Self-Reliance and Other Essays","",1993,"Dover Publications",new String[] {"Ralph Waldo Emerson"},BookGenre.PHILOSOPHY,4.5);
		bookmarks[2][2] =BookmarkManager.getInstance().createBook(4002,"Light From Many Lamps","",1988,"Touchstone",new String[] {"Lillian Eichler Watson"},BookGenre.PHILOSOPHY,5.0);
		bookmarks[2][3] =BookmarkManager.getInstance().createBook(4003,"Head First Design Patterns","",2004, "O'Reilly Media", new String[]{"Eric Freeman","Bert Bates","Kathy Sierra","Elisabeth Robson"},BookGenre.TECHNICAL,4.5);
		bookmarks[2][4] =BookmarkManager.getInstance().createBook(4004,	"Effective Java Programming Language Guide","",2007,"Prentice Hall", new String[] {"Joshua Bloch"},BookGenre.TECHNICAL,	4.9);
		*/
		//String[] data = new String[BOOKMARKCOUNT_PER_TYPE];
		String query="select b.id,title,image_url,publication_year,GROUP_CONCAT(a.name SEPARATOR ',') AS authors,book_genre_id,amazon_rating, created_date from book b, publisher p, Author a,book_author ba where b.publisher_id=p.id and b.id = ba.book_id and ba.author_id= a.id group by b.id";
		//List<String> data = new ArrayList<>();
		List<Bookmark> bookmarkList = new ArrayList<>();
		ResultSet rs= stmt.executeQuery(query);
    	//IOUtil.read(data, "Book");
    	//int colNum = 0;
    	//for (String row : data) {
		while(rs.next()) {
    		/*String[] values = row.split("\t");
    		String[] authors = values[4].split(",");
    		*/
    		long id= rs.getLong("id");
    		String title= rs.getString("title");
    		String imageUrl= rs.getString("image_url");
    		int publicationYear= rs.getInt("publication_year");
    		long publisherId= rs.getLong(4);
    		String[] authors= rs.getString("authors").split(",");
    		int genre_id= rs.getInt("book_genre_id");
    		BookGenre genre= BookGenre.values()[genre_id];
    		double amazonRating= rs.getDouble("amazon_rating");
    		Date createdDate = rs.getDate("created_date");
    		System.out.println("createdDate"+ createdDate);
    		Timestamp timeStamp = rs.getTimestamp(8);
    		System.out.println("timeStamp"+timeStamp);
    		System.out.println(timeStamp.toLocalDateTime());
    		Bookmark bookmark = BookmarkManager.getInstance().createBook(id, title,imageUrl, publicationYear,publisherId,authors,genre,amazonRating/*, values[7]*/);
    		bookmarkList.add(bookmark);
    	}
    	bookmarks.add(bookmarkList);
	}
	private static void loadMovies(Statement stmt) throws SQLException {
		/*bookmarks[1][0]=BookmarkManager.getInstance().createMovie(3000,"Citizen Kane","",1941,new String[] {"Orson Welles","Joseph Cotten"},new String[] {"Orson Welles"},MovieGenre.CLASSICS,8.5);
		bookmarks[1][1]=BookmarkManager.getInstance().createMovie(3001,"The Grapes of Wrath","",1940,new String[] {"Henry Fonda","Jane Darwell"},new String[] {"John Ford"},MovieGenre.CLASSICS,8.2);
		bookmarks[1][2]=BookmarkManager.getInstance().createMovie(3002,"A Touch of Greatness","",2004, new String[]{"Albert Cullum"},new String[] {"Leslie Sullivan"},MovieGenre.DOCUMENTARIES,7.3);
		bookmarks[1][3]=BookmarkManager.getInstance().createMovie(3003,"The Big Bang Theory","",2007,new String[]{"Kaley Cuoco","Jim Parsons"}, new String[]{"Chuck Lorre","Bill Prady"},MovieGenre.TV_SHOWS,8.7);
		bookmarks[1][4]=BookmarkManager.getInstance().createMovie(3004,"Ikiru","",1952,new String[] {"Takashi Shimura","Minoru Chiaki"},new String[] {"Akira Kurosawa"},"Foreign Movies",8.4);
	*/
		//String[] data = new String[BOOKMARKCOUNT_PER_TYPE];
		String query="select m.id,title,release_year,  GROUP_CONCAT(a.name SEPARATOR ',') AS cast,GROUP_CONCAT(d.name SEPARATOR ',') as directors,movie_genre_id,imdb_rating from movie m, actor a, movie_actor ma,director d,movie_director md where m.id = ma.movie_id and ma.actor_id = a.id and m.id=md.movie_id and md.director_id = m.id group by m.id";
		ResultSet rs = stmt.executeQuery(query);
		//List<String> data = new ArrayList<>();
		List<Bookmark> bookmarkList = new ArrayList<>();
    	//IOUtil.read(data, "Movie");
    	//int colNum = 0;
    	//for (String row : data) {
		while(rs.next()) {
			long id = rs.getLong("id");
			String title = rs.getString("title");
			int releaseYear = rs.getInt("release_year");
			String[] cast= rs.getString("cast").split(",");
			String[] directors = rs.getString("directors").split(",");
			int genre_id = rs.getInt("movie_genre_id");
			MovieGenre genre = MovieGenre.values()[genre_id];
			double imdbRating= rs.getDouble("imdb_rating");
			
    		/*String[] values = row.split("\t");
    		String[] cast = values[3].split(",");
    		String[] directors = values[4].split(",");*/
			
    		Bookmark bookmark = BookmarkManager.getInstance().createMovie(id,title,releaseYear, cast, directors, genre, imdbRating/*, values[7]*/);
    		bookmarkList.add(bookmark);
    	}
    	bookmarks.add(bookmarkList);
	}
	private static void LoadWebLinks(Statement stmt) throws SQLException  {
		/*bookmarks[0][0] = BookmarkManager.getInstance().createWebLink(2000,"Taming Tiger, Part 2","http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-2.html","http://www.javaworld.com");
		bookmarks[0][1] = BookmarkManager.getInstance().createWebLink(2001,"How do I import a pre-existing Java project into Eclipse and get up and running?","http://stackoverflow.com/questions/142863/how-do-i-import-a-pre-existing-java-project-into-eclipse-and-get-up-and-running","http://www.stackoverflow.com");
		bookmarks[0][2] = BookmarkManager.getInstance().createWebLink(2002,"Interface vs Abstract Class","http://mindprod.com/jgloss/interfacevsabstract.html","http://mindprod.com");
		bookmarks[0][3] = BookmarkManager.getInstance().createWebLink(2003,"NIO tutorial by Greg Travis","http://cs.brown.edu/courses/cs161/papers/j-nio-ltr.pdf","http://cs.brown.edu");
		bookmarks[0][4] = BookmarkManager.getInstance().createWebLink(2004,"Virtual Hosting and Tomcat","http://tomcat.apache.org/tomcat-6.0-doc/virtual-hosting-howto.html","http://tomcat.apache.org");
	*/
		
		//List<String> data = new ArrayList<>();
	
    	//IOUtil.read(data, "WebLink");
		String query= "select * from weblink";
		
		ResultSet rs= stmt.executeQuery(query);
	
		
    	List<Bookmark> bookmarkList = new ArrayList<>();
    	while (rs.next()) {
    		//String[] values = row.split("\t");
    		long id= rs.getLong("id");
    		String title= rs.getString("title");
    		String url  = rs.getString("url");
    		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++"+url);
    		String host= rs.getString("host");
    		
    		Bookmark bookmark = BookmarkManager.getInstance().createWebLink(id,title, url,host/*, values[4]*/);
    		bookmarkList.add(bookmark);
    	}
    	bookmarks.add(bookmarkList);
	}
	/*private static void loadUsers() {
		users[0]=UserManager.getInstance().createUser(1000,"user0@semanticsquare.com","test","John","M",Gender.MALE,UserType.User);
		users[1]=UserManager.getInstance().createUser(1001,"user1@semanticsquare.com","test","Sam","M",Gender.MALE,UserType.User);
		users[2]=UserManager.getInstance().createUser(1002,"user2@semanticsquare.com","test","Anita","M",Gender.MALE,UserType.EDITOR);
		users[3]=UserManager.getInstance().createUser(1003,"user3@semanticsquare.com","test","Sara","M",Gender.FEMALE,UserType.EDITOR);
		users[4]=UserManager.getInstance().createUser(1004,"user4@semanticsquare.com","test","Dheeru","M",Gender.MALE,UserType.CHIEF_EDITOR);
		
		
	}*/
	private static void loadUsers(Statement stmt) throws SQLException {
		
		//List<String> data = new ArrayList<>();
		//IOUtil.read(data, "User");
		
		String query ="Select * from user";
	
		ResultSet rs =stmt.executeQuery(query);
		
		//List<String> data = new ArrayList<>();
		while(rs.next()) {
			long id= rs.getLong("id");
			String email= rs.getString("email");
			String password=rs.getString("password");
			String firstName=rs.getString("first_name");
			String lastName=rs.getString("last_name");
			int gender_id=rs.getInt("gender_id");//rs.getGender("gender");
			Gender gender= Gender.values()[gender_id];
			String userType=rs.getString("user_type_id");
			Date createdDate = rs.getDate("created_date");
			Timestamp timeStamp = rs.getTimestamp(8);
			System.out.println(timeStamp.toLocalDateTime());
			/*String[] values =row.split("\t");
			Gender gender = Gender.MALE;
			if(values[5].equals("f")) {
				gender = Gender.FEMALE;
			}else if( values[5].equals("t")) {
				gender = Gender.TRANSGENDER;
			}*/
			User user = UserManager.getInstance().createUser(id, email,password, firstName, lastName, gender, userType);
			users.add(user);
		}
		
	}
	
	public static void add(UserBookmark userBookmark2) {
		userBookmark.add(userBookmark2);
		//bookmarkIndex++;
		
	}

}
