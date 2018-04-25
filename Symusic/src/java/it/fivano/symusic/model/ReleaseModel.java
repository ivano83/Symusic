package it.fivano.symusic.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

public class ReleaseModel {

	private Long id;
	private String name;
	private String nameWithUnderscore;
	private String year;
	private String crew;
	private String artist;
	private String song;
	private String releaseDate;
	private String recordLabel;
	private GenreModel genre;
	private ReleaseExtractionModel releaseExtraction;
	private ReleaseFlagModel releaseFlag;

	private Integer voteValue;
	private Double voteAverage;
	private boolean voted;

	private Map<String,Integer> mappaQualitaDati;

	private List<LinkModel> links;
	private List<TrackModel> tracks;
	private List<VideoModel> videos;

	private List<String> similarRelease;

	private int popularLevel;

	public ReleaseModel() {
		voteAverage = 0.;
		voteValue = 0;
		setMappaQualitaDati(new HashMap<String,Integer>());
	}


	public String getName() {
		if(name==null && nameWithUnderscore!=null) {
			name = nameWithUnderscore.replace("_", " ");
		}
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getCrew() {
		return crew;
	}
	public void setCrew(String crew) {
		this.crew = crew;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getSong() {
		return song;
	}
	public void setSong(String song) {
		this.song = song;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getRecordLabel() {
		return recordLabel;
	}
	public void setRecordLabel(String recordLabel) {
		this.recordLabel = recordLabel;
	}
	public String getNameWithUnderscore() {
		return nameWithUnderscore;
	}
	public void setNameWithUnderscore(String nameWithUnderscore) {
		this.nameWithUnderscore = nameWithUnderscore;
	}
	public List<LinkModel> getLinks() {
		return links;
	}
	public void setLinks(List<LinkModel> links) {
		this.links = links;
	}
	public void addLink(LinkModel link) {
		if(this.links==null)
			links = new ArrayList<LinkModel>();

		if(!links.contains(link))
			links.add(link);
	}

	@Override
	public String toString() {
		return this.getName() + ((releaseDate!=null)?" ["+releaseDate+"]":"");
	}

	public int getDetailsPercent() {
		int percent = 0;
		if(releaseDate!=null) percent += 10;
		if(genre!=null) percent += 10;
		if(crew!=null) percent += 10;
		if(artist!=null) percent += 5;
		if(song!=null) percent += 5;
		if(links!=null && links.size()==1) percent += 10;
		if(links!=null && links.size()>1 && links.size()<3) percent += 20;
		else if(links!=null && links.size()>3 && links.size()<5) percent += 30;
		else if(links!=null && links.size()>5) percent += 40;
		if(videos.size()>1) percent += 20;

		return percent;
	}
	public List<TrackModel> getTracks() {
		if(this.tracks==null)
			tracks = new ArrayList<TrackModel>();
		return tracks;
	}
	public void setTracks(List<TrackModel> tracks) {
		this.tracks = tracks;
	}
	public void addTrack(TrackModel tr) {
		if(this.tracks==null)
			tracks = new ArrayList<TrackModel>();

		if(!tracks.contains(tr))
			tracks.add(tr);
	}
	public List<VideoModel> getVideos() {
		if(this.videos==null)
			videos = new ArrayList<VideoModel>();
		return videos;
	}
	public void setVideos(List<VideoModel> videos) {
		this.videos = videos;
	}
	public void addVideo(VideoModel tr) {
		if(this.videos==null)
			videos = new ArrayList<VideoModel>();

		if(!videos.contains(tr))
			videos.add(tr);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getVoteValue() {
		return voteValue;
	}
	public void setVoteValue(Integer voteValue) {
		this.voteValue = voteValue;
	}
	public boolean isVoted() {
		return voted;
	}
	public void setVoted(boolean voted) {
		this.voted = voted;
	}
	public Double getVoteAverage() {
		return voteAverage;
	}
	public void setVoteAverage(Double voteAverage) {
		this.voteAverage = voteAverage;
	}


	public GenreModel getGenre() {
		return genre;
	}


	public void setGenre(GenreModel genre) {
		this.genre = genre;
	}


	public ReleaseExtractionModel getReleaseExtraction() {
		if(releaseExtraction==null)
			releaseExtraction = new ReleaseExtractionModel();
		return releaseExtraction;
	}


	public void setReleaseExtraction(ReleaseExtractionModel releaseExtraction) {
		this.releaseExtraction = releaseExtraction;
	}

	@Override
	public boolean equals(Object obj) {
		if(this.nameWithUnderscore!=null) {
			ReleaseModel rel = (ReleaseModel)obj;
			return this.nameWithUnderscore.equalsIgnoreCase(rel.getNameWithUnderscore());
		}
		else if(name!=null) {
			ReleaseModel rel = (ReleaseModel)obj;
			return this.name.equalsIgnoreCase(rel.getName());
		}
		return super.equals(obj);
	}


	public ReleaseFlagModel getReleaseFlag() {
		if(releaseFlag==null)
			releaseFlag = new ReleaseFlagModel();
		return releaseFlag;
	}


	public void setReleaseFlag(ReleaseFlagModel releaseFlag) {
		this.releaseFlag = releaseFlag;
	}

	public List<String> getSimilarRelease() {
		return similarRelease;
	}
	public void setSimilarRelease(List<String> similarRelease) {
		this.similarRelease = similarRelease;
	}
	public void addSimilarRelease(String tr) {
		if(this.similarRelease==null)
			similarRelease = new ArrayList<String>();

		if(!similarRelease.contains(tr))
			similarRelease.add(tr);
	}


	public Map<String,Integer> getMappaQualitaDati() {
		return mappaQualitaDati;
	}


	public void setMappaQualitaDati(Map<String,Integer> mappaQualitaDati) {
		this.mappaQualitaDati = mappaQualitaDati;
	}

	public boolean qualitaDatiMigliore(String nome, Integer rate) {
		if(mappaQualitaDati.containsKey(nome)) {
			return rate.intValue() > mappaQualitaDati.get(nome).intValue();
		}
		return true;
	}

	public List<String> getSeparateArtist() {
		List<String> res = new ArrayList<String>();
		if(artist!=null) {
			String s = artist.toLowerCase()
					.replace(",", "|").replace(" feat ", "|").replace(" feat. ", "|").replace(" ft ", "|").replace(" ft. ", "|").replace(" featuring ", "|")
					.replace(" presents ", "|").replace(" pres ", "|").replace(" pres. ", "|").replace(" with ", "|").replace(" vs ", "|").replace(" vs. ", "|")
					.replace("  ", " ").replace(" and ", "|").trim();

			for(String currArtist : s.split("\\|"))
				res.add(WordUtils.capitalizeFully(currArtist.trim()));
		}

		return res;
	}


	public int getPopularLevel() {
		return popularLevel;
	}


	public void setPopularLevel(int popularLevel) {
		this.popularLevel = popularLevel;
	}

}
