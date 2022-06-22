import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import thrillio.util.IOUtil;

public class UrIToUrl {

	public static void main(String[] args) throws URISyntaxException, IOException {
		   String aURIString = "http://courses.baeldung.com";
		    URI uri = new URI(aURIString);
		    URL url = new URL(aURIString);

		    URL toURL = uri.toURL();
		    URI toURI = url.toURI();
		 
		    System.out.println("toURL "+toURL);
		    System.out.println("toURI "+toURI);
		
		    //URI urI = new URI("somescheme://someauthority/path?thequery");  //is correct

		   // URL urL = urI.toURL(); //not correct because protocol is wrong
		    
		   // System.out.println("\n new url "+urL);
		
		    //opening connection to remote resource
		    URL uRL = new URL("https://stackoverflow.com/questions/142863/how-do-i-import-a-pre-existing-java-project-into-eclipse-and-get-up-and-running");
		    //String contents = IOUtils.toString(uRL.openStream());
		// ---------------------------------------OR----------------------------------------------------------------------------------------------------
		   
		    HttpURLConnection contents = (HttpURLConnection)uRL.openConnection();
		   // System.out.println("contents "+contents);
		    
		   int responseCode = contents.getResponseCode();
		   System.out.println(responseCode);
		    String str = IOUtil.read(contents.getInputStream());
		   System.out.println("str "+str);
	}

}
