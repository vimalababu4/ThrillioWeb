package thrillio.entities;

import java.util.Arrays;

import thrillio.constants.MovieGenre;

public class Movie extends Bookmark {
	private int releaseYear;
	private String[] cast;
	private String[] directors;
	private MovieGenre genre;
	private double imdbRating;

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public String[] getCast() {
		return cast;
	}

	public void setCast(String[] cast) {
		this.cast = cast;
	}

	public String[] getDirectors() {
		return directors;
	}

	public void setDirectors(String[] directors) {
		this.directors = directors;
	}

	public MovieGenre getGenre() {
		return genre;
	}

	public void setGenre(MovieGenre horror) {
		this.genre = horror;
	}

	public double getImdbRating() {
		return imdbRating;
	}

	public void setImdbRating(double imdbRating) {
		this.imdbRating = imdbRating;
	}

	@Override
	public String toString() {
		return "Movie [releaseYear=" + releaseYear + ", cast=" + Arrays.toString(cast) + ", directors="
				+ Arrays.toString(directors) + ", genre=" + genre + ", imdbRating=" + imdbRating + "]";
	}

	@Override   //stub
	public boolean isKidsFriendlyEligible() {
		// TODO Auto-generated method stub
		if(getGenre().equals(MovieGenre.HORROR) || getReleaseYear()==1942 || getCast()[0].contains("Orizon")) {
			return false;
		}
		return true;
	}
	

}
