package it.fivano.symusic.core.util;

import java.util.Date;

import it.fivano.symusic.core.util.C.SearchType;

public class SearchInput {


	private Date dataDa;
	private Date dataA;
	private String genre;
	private String crew;
	private String name;
	private boolean excludeRadioRip;
	private boolean excludeVa;
	private SearchType searchType;
	private int annoMin;
	private int annoMax;

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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public SearchType getSearchType() {
		return searchType;
	}
	public void setSearchType(SearchType searchType) {
		this.searchType = searchType;
	}
	public boolean isExcludeVa() {
		return excludeVa;
	}
	public void setExcludeVa(boolean excludeVa) {
		this.excludeVa = excludeVa;
	}
	public int getAnnoMin() {
		return annoMin;
	}
	public void setAnnoMin(int annoMin) {
		this.annoMin = annoMin;
	}
	public int getAnnoMax() {
		return annoMax;
	}
	public void setAnnoMax(int annoMax) {
		this.annoMax = annoMax;
	}

}
