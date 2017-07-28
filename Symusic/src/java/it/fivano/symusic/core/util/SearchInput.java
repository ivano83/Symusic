package it.fivano.symusic.core.util;

import java.util.Date;

public class SearchInput {

	
	private Date dataDa;
	private Date dataA;
	private String genre;
	private String crew;
	private boolean excludeRadioRip;
	
	public Date getDataDa() {
		return dataDa;
	}
	public void setDataDa(Date dataDa) {
		this.dataDa = dataDa;
	}
	public Date getDataA() {
		return dataA;
	}
	public void setDataA(Date dataA) {
		this.dataA = dataA;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getCrew() {
		return crew;
	}
	public void setCrew(String crew) {
		this.crew = crew;
	}
	public boolean isExcludeRadioRip() {
		return excludeRadioRip;
	}
	public void setExcludeRadioRip(boolean excludeRadioRip) {
		this.excludeRadioRip = excludeRadioRip;
	}
	
	
}
