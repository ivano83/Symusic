package it.fivano.symusic.core.util;

public class C {

	public static String TRACKS = "Tracks";
	public static String GENRE = "Genre";
	public static String CREW = "Crew";
	public static String YEAR = "Year";
	public static String REL_DATE = "ReleaseDate";
	public static String ARTIST = "Artist";
	public static String SONG = "Song";

	public static String SEARCH_BY_NAME = "Name";
	public static String SEARCH_BY_CREW = "Crew";
	public static String SEARCH_BY_GENRE = "Genre";
	public static String SEARCH_BY_RELEASE_NAME = "ReleaseName";

	public static enum SearchType {
		SEARCH_BY_NAME,
		SEARCH_BY_CREW,
		SEARCH_BY_GENRE,
		SEARCH_BY_RELEASE_NAME;
	}
}
