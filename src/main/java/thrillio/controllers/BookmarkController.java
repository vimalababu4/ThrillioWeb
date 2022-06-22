package thrillio.controllers;

import java.io.IOException;
import java.util.Collection;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import thrillio.Managers.BookmarkManager;
import thrillio.Managers.UserManager;
import thrillio.constants.KidFriendlyStatus;
import thrillio.entities.Book;
import thrillio.entities.Bookmark;
import thrillio.entities.User;

@WebServlet(urlPatterns={"/bookmark","/bookmark/save","/bookmark/mybooks"})
public class BookmarkController extends HttpServlet {
	/*
	private static BookmarkController instance = new BookmarkController();
	private BookmarkController() {}
	public static BookmarkController getInstance() {
		return instance;
	}*/
	
	 public BookmarkController() {
	        // TODO Auto-generated constructor stub
	    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	RequestDispatcher dispatcher = null;
    	System.out.println("Servlet path: "+request.getServletPath());
    	if(request.getSession().getAttribute("userId") != null) {
			long userId = (long) request.getSession().getAttribute("userId");
			if(request.getServletPath().contains("save")){
	    		dispatcher = request.getRequestDispatcher("/mybooks.jsp");
	    		String bid=request.getParameter("bid");
	    		User user = UserManager.getInstance().getUser(userId);
	    		Bookmark book =BookmarkManager.getInstance().getBook(Long.parseLong(bid));
	    		BookmarkManager.getInstance().saveUserBookmark(user, book);
	    		Collection<Bookmark> list=BookmarkManager.getInstance().getBooks(true,userId);
	        	request.setAttribute("books", list);
	    	}else if(request.getServletPath().contains("mybooks")) {
	    		dispatcher = request.getRequestDispatcher("/mybooks.jsp");
	    		Collection<Bookmark> list=BookmarkManager.getInstance().getBooks(true,userId);
	        	request.setAttribute("books", list);
	    	}else {
	    		dispatcher = request.getRequestDispatcher("/browseBooks.jsp");
	    		Collection<Bookmark> list=BookmarkManager.getInstance().getBooks(false,userId);
	        	request.setAttribute("books", list);
	    	}
		}else {
			dispatcher = request.getRequestDispatcher("/login.jsp");
		}
    
    	
    	dispatcher.forward(request, response);
    }
	public void saveUserBookmark(User user, Bookmark bookmark) {
		BookmarkManager.getInstance().saveUserBookmark(user,bookmark);
		
	}
	public void setKidFriendlyStatus(User user, KidFriendlyStatus kidFriendlyStatus, Bookmark bookmarkeach) {
		BookmarkManager.getInstance().setKidFriendlyStatus(user,kidFriendlyStatus, bookmarkeach);
			
		
		
	}
	public void share(User user, Bookmark bookmarkeach) {
		BookmarkManager.getInstance().share(user, bookmarkeach);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
