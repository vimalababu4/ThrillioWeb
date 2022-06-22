<%@ page import ="thrillio.entities.Book, java.util.Collection" language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>BooksAloha</title>
</head>
<body style="font-family:Arial;font-size:20px;">
<div style="height:65px;align: center;background: #DB5227;font-family: Arial;color: white;"">
	<br><b>
	<a href="" style="font-family:garamond;font-size:34px;margin:0 0 0 10px;color:white;text-decoration: none;">Books<i>Aloha!</i></a></b>            	    	
</div>
<br><br>
<table>


<%
	Collection<Book> mybooks = (Collection<Book>)request.getAttribute("books");
	for(Book book: mybooks){
%>
  <tr>
    <td>
     <img src="<%= book.getImage_url()%>">
    </td>
    
    <td style="color:gray;">
     By <span style="color: #B13100;"><%= book.getAuthors() %></span>
     <br><br>
     Rating: <span style="color: #B13100;"><%= book.getAmazonRating() %></span>
     <br><br>
	Publication Year: <span style="color: #B13100;"><%= book.getPublicationYear()%></span>
	<br><br>

    </td>
  </tr>
  <tr>
     <td>&nbsp;</td>
  </tr>
<%
	}
%>
  
   
</table>
</body>
</html>


