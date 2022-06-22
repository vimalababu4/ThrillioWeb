package authUsers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintStream;
import thrillio.Managers.UserManager;

@WebServlet(urlPatterns={"/auth", "/auth/logout"})
public class authUserController
  extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    System.out.println("servlet path: " + request.getServletPath());
    if (!request.getServletPath().contains("logout"))
    {
      String email = request.getParameter("email");
      String password = request.getParameter("password");
      long userId = UserManager.getInstance().authenticate(email, password);
      if (userId != -1L)
      {
        HttpSession session = request.getSession();
        session.setAttribute("userId", Long.valueOf(userId));
        
        request.getRequestDispatcher("bookmark/mybooks").forward(request, response);
      }
      else
      {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
      }
    }
    else
    {
      request.getSession().invalidate();
      request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    doGet(request, response);
  }
}
