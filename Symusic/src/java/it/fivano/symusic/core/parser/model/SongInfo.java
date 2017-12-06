package it.fivano.symusic.core.parser.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SongInfo {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

	private String songName;
	private String artistName;
	private String albumName;
	private Date releaseDate;
	private String urlDetails;

	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	public String getAlbumName() {
		return albumName;
	}
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public String getYear() {
		if(releaseDate!=null)
			return sdf.format(releaseDate);
		return null;
	}
	public void setYear(String anno) throws ParseException {
		releaseDate = sdf.parse(anno);
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getUrlDetails() {
		return urlDetails;
	}
	public void setUrlDetails(String urlDetails) {
		this.urlDetails = urlDetails;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return (artistName!=null?artistName+" - ":"" ) + (songName!=null?songName+" ":"" )
				+ (albumName!=null?"["+albumName+"] ":"" ) + (releaseDate!=null?"("+new SimpleDateFormat("yyyy").format(releaseDate)+") ":"" );
	}

}
