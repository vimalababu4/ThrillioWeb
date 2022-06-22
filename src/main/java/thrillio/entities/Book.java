package thrillio.entities;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;


import thrillio.constants.BookGenre;
import thrillio.partner.Shareable;

public class Book extends Bookmark implements Shareable{
	
	private String image_url;
	private int publicationYear;
	private long publisherId;
	private String[] authors;
	private BookGenre genre;
	private double amazonRating;

	public Book() {}
	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public int getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}

	public long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(long publisherId) {
		this.publisherId = publisherId;
	}

	public String[] getAuthors() {
		return authors;
	}

	public void setAuthors(String[] authors) {
		this.authors = authors;
	}

	public BookGenre getGenre() {
		return genre;
	}

	public void setGenre(BookGenre genre) {
		this.genre = genre;
	}

	public double getAmazonRating() {
		return amazonRating;
	}

	public void setAmazonRating(double amazonRating) {
		this.amazonRating = amazonRating;
	}

	@Override
	public String toString() {
		return "Book [image_url=" + image_url + ", publicationYear=" + publicationYear + ", publisherId=" + publisherId
				+ ", authors=" + Arrays.toString(authors) + ", genre=" + genre + ", amazonRating=" + amazonRating + "]";
	}

	@Override
	public boolean isKidsFriendlyEligible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getItemData() {
		//xml -extensible mark up language
		//human readable, and machine level 
		StringBuilder builder = new StringBuilder();
		builder.append("<item>");
			builder.append("<type>Book</type>");
			builder.append("<title>").append(getTitle()).append("</title>");
			builder.append("<publisher>").append(StringUtils.join(authors,",")).append("</title>");
			builder.append("<publicationYear>").append(publicationYear).append("</publicationYear>");
			builder.append("<genre>").append(genre).append("</genre>");
			builder.append("<amazonRating>").append(amazonRating).append("</amazonRating>");
		builder.append("</item");
		return builder.toString();
	}
	

}
