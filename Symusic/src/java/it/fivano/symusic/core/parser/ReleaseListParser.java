package it.fivano.symusic.core.parser;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import it.fivano.symusic.SymusicUtility;
import it.fivano.symusic.conf.SymusicConf;
import it.fivano.symusic.core.parser.model.BaseReleaseParserModel;
import it.fivano.symusic.core.util.C;
import it.fivano.symusic.core.util.C.SearchType;
import it.fivano.symusic.core.util.SearchInput;
import it.fivano.symusic.core.util.UserAgent;
import it.fivano.symusic.exception.ParseReleaseException;
import it.fivano.symusic.model.ReleaseModel;

public class ReleaseListParser extends BaseParser {


	protected SearchInput searchInput;


	public ReleaseListParser(Properties props) {
		super();
		try {
			this.setLogger(getClass());
			this.setProps(props);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<BaseReleaseParserModel> searchByGenre(Date da, Date a, String genre, boolean excludeRadioRip) throws Exception {

		searchInput = new SearchInput();
		searchInput.setDataDa(da);
		searchInput.setDataA(a);
		searchInput.setGenre(genre==null?"":genre);
		searchInput.setExcludeRadioRip(excludeRadioRip);
		searchInput.setSearchType(SearchType.SEARCH_BY_GENRE);

		return search();

	}

	public List<BaseReleaseParserModel> searchByCrew(Date da, Date a, String crew, boolean excludeRadioRip) throws Exception {

		searchInput = new SearchInput();
		searchInput.setDataDa(da);
		searchInput.setDataA(a);
		searchInput.setGenre("");
		searchInput.setCrew(crew);
		searchInput.setExcludeRadioRip(excludeRadioRip);
		searchInput.setSearchType(SearchType.SEARCH_BY_CREW);

		return search();

	}

	public List<BaseReleaseParserModel> searchByName(String name, boolean excludeRadioRip, int maxItem) throws Exception {

		searchInput = new SearchInput();
		searchInput.setGenre("");
		searchInput.setName(this.applyFilterSearch(name));
		searchInput.setExcludeRadioRip(excludeRadioRip);
		searchInput.setSearchType(SearchType.SEARCH_BY_NAME);
		searchInput.setMaxItem(maxItem);

		return search();

	}

	public List<BaseReleaseParserModel> searchByReleaseName(String name, boolean excludeRadioRip) throws Exception {

		searchInput = new SearchInput();
		searchInput.setGenre("");
		searchInput.setName(name);
		searchInput.setExcludeRadioRip(excludeRadioRip);
		searchInput.setSearchType(SearchType.SEARCH_BY_RELEASE_NAME);

		return search();

	}

	public List<BaseReleaseParserModel> search() throws Exception {


		List<BaseReleaseParserModel> listaRelease = new ArrayList<BaseReleaseParserModel>();

		int pageGap = props.getProperty("page_gap")!=null ? Integer.parseInt(props.getProperty("page_gap")) : 1;
		int pagina = props.getProperty("page_start")!=null ? Integer.parseInt(props.getProperty("page_start")) : 1;
		boolean isSearchByName = false;
		String urlPage = null;
		String urlPattern = null;
		if(SearchType.SEARCH_BY_NAME.equals(searchInput.getSearchType()) && props.getProperty("search_byName")!=null) {
			urlPattern = props.getProperty("search_byName").replace("{0}", searchInput.getName());
			isSearchByName = true;
		} else if(SearchType.SEARCH_BY_CREW.equals(searchInput.getSearchType()) && props.getProperty("search_byCrew")!=null) {
			urlPattern = props.getProperty("search_byCrew").replace("{0}", searchInput.getCrew());
		} else if(SearchType.SEARCH_BY_GENRE.equals(searchInput.getSearchType()) && props.getProperty("search_byGenre")!=null) {
			urlPattern = props.getProperty("search_byGenre").replace("{0}", searchInput.getGenre());
		} else if(SearchType.SEARCH_BY_RELEASE_NAME.equals(searchInput.getSearchType()) && props.getProperty("search_byReleaseName")!=null) {
			urlPattern = props.getProperty("search_byReleaseName").replace("{0}", searchInput.getName());
		} else {
			String msg = "La ricerca per '"+searchInput.getSearchType()+"' non è gestita!";
			log.error(msg);
			throw new Exception(msg);
		}
		urlPage = this.risolviUrl(urlPattern, (pageGap*pagina)+"");

		while(parseFullPage(listaRelease, urlPage)) {
			pagina++;

			if(isSearchByName && pagina>10)
				break; // basta cercare ulteriori pagine

			urlPage = this.risolviUrl(urlPattern, (pageGap*pagina)+"");

		}

		return listaRelease;

	}



	private boolean parseFullPage(List<BaseReleaseParserModel> listaRelease, String urlPage) throws ParseReleaseException {

		if(urlPage == null)
			return false;

		boolean continuaPaginaSuccessiva = true;

		try {

			String baseUrl = props.getProperty("base_url");
			// CONNESSIONE ALLA PAGINA
			String userAgent = UserAgent.randomUserAgent();
			log.info("Connessione in corso --> "+urlPage);
			Document doc = Jsoup.connect(urlPage).timeout(TIMEOUT).userAgent(userAgent).ignoreHttpErrors(true).get();

			if(antiDDOS.isAntiDDOS(doc)) {
				doc = this.bypassAntiDDOS(doc, baseUrl, urlPage, userAgent);
			}
			else {
				int tent = 0;
				while(tent < 5 &&
						(doc.text().contains("wait 4 seconds then reload page") || doc.text().contains(" preparing http") || doc.text().contains("you click too fast"))) {
					log.info("Protezione bot attivo: tentativo n. "+tent);
					Thread.sleep(4100+(500*tent));
					doc = Jsoup.connect(urlPage).timeout(TIMEOUT).userAgent(userAgent).ignoreHttpErrors(true).get();
					tent++;
				}
			}

			boolean isSearchByName = SearchType.SEARCH_BY_NAME.toString().equals(searchInput.getSearchType().toString());

//			Elements releaseGroup = doc.getElementsByClass(props.getProperty("loop_list_item"));
			Elements releaseGroup = (Elements)this.runRole(doc,props.getProperty("loop_list_item"));
			if(releaseGroup.size()>0) {
				BaseReleaseParserModel release = null;
				log.info("####################################");
				for(Element tmp : releaseGroup) {

					release = this.popolaDatiRelease(tmp);


					if(isSearchByName) {
						if(listaRelease.size()>=searchInput.getMaxItem())
							continuaPaginaSuccessiva = false; // superato il max item richiesto
					} else if(searchInput.getDataDa()!=null && searchInput.getDataDa().after(release.getReleaseDate()))
						continuaPaginaSuccessiva = false; // superato il range richiesto

					if((searchInput.isExcludeRadioRip() && release.isRadioRip()) || !continuaPaginaSuccessiva) {
						// e' un radio rip o un fuori range
					}
					else if(release.isDateInRange()){
						listaRelease.add(release);
						log.info("|"+release+"| acquisita");
					}

				}

				if(!isSearchByName && searchInput.getDataDa()==null && searchInput.getDataA()==null)
					continuaPaginaSuccessiva = false; // date non specificate: basta la prima pagina

			} else {
				continuaPaginaSuccessiva = false;
			}
		} catch (IOException e) {
			log.error("Errore IO", e);
			throw new ParseReleaseException("Errore IO",e);
		} catch (ParseException e) {
			log.error("Errore nel parsing", e);
			throw new ParseReleaseException("Errore nel parsing",e);
		} catch (Exception e) {
			log.error("Errore generico", e);
			throw new ParseReleaseException("Errore generico",e);
		}


		return continuaPaginaSuccessiva;

	}

	private BaseReleaseParserModel popolaDatiRelease(Element tmp) throws Exception {

		BaseReleaseParserModel release = new BaseReleaseParserModel();

		String releaseName = (String)this.runRole(tmp,props.getProperty("item_release_name"));

		String dateIn = this.getStandardDateFormat((String)this.runRole(tmp,props.getProperty("item_release_date")));
		Date dateInDate = SymusicUtility.getStandardDate(dateIn);

		// RELEASE NAME
		release.setReleaseName(releaseName);

		// URL
		if(props.getProperty("item_url")!=null)
			release.setUrlReleaseDetails((String)this.runRole(tmp,props.getProperty("item_url")));

		// RELEASE DATE
		release.setReleaseDate(dateInDate);

		// GENERE
		String genre = searchInput.getGenre().length()==0 ? (String)this.runRole(tmp,props.getProperty("item_genre")) : searchInput.getGenre();
		release.setGenre(StringUtils.capitalize(genre));

		// RANGE DATA, SOLO LE RELEASE COMPRESE DA - A
		if(searchInput.getDataDa()!=null && searchInput.getDataA()!=null) {
			release.setDateInRange(this.isInRange(dateInDate, searchInput.getDataDa(), searchInput.getDataA()));
		} else {
			release.setDateInRange(true);
		}

		// CONTROLLA SE E' UN RADIO/SAT RIP
		release.setRadioRip(this.isRadioRipRelease(releaseName));

		return release;
	}



	private String getStandardDateFormat(String dateIn) throws ParseException {

		String dateFormat = props.getProperty("date_format");

		dateIn = dateIn.replace("th,", "").replace("rd,", "").replace("st,", "").replace("nd,", "");

		return SymusicUtility.getStandardDateFormat(dateIn, dateFormat);

	}

	protected String applyFilterSearch(String t) {
		t = t.toLowerCase().replaceAll("[-,!?&']", " ").replace(" feat ", " ").replace(" feat. ", " ").replace(" ft ", " ").replace(" featuring ", " ")
				.replace(" presents ", " ").replace(" pres ", " ").replace(" pres. ", " ")
				.replace("  ", " ").replace(" and ", " ").trim();
		if(t.indexOf("(")!=-1) {
			t = t.substring(0,t.indexOf("("));
		}
		if(props.getProperty("search_byName_format")!=null) {
			t = this.formatSearchValue(t, props.getProperty("search_byName_format"));
		}
		return t.trim();
	}

	protected boolean isInRange(Date dateInDate, Date da, Date a) {
		boolean result = (dateInDate.compareTo(da)>=0) && (dateInDate.compareTo(a)<=0);
		return result;
	}

	protected boolean isRadioRipRelease(String releaseName) throws IOException {
		for(String rip : new SymusicConf().RELEASE_EXCLUSION) {
			if(releaseName.contains(rip))
				return true;
		}
		return false;
	}

	public SearchInput getSearchInput() {
		return searchInput;
	}

	public static void main(String[] args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date da = sdf.parse("29-09-2017");
		Date a = sdf.parse("29-09-2017");
		Properties props = new SymusicConf().loadProperties("prescene");
		ReleaseListParser p = new ReleaseListParser(props);
//		System.setProperty("https.proxyHost", "10.55.32.23");
//		System.setProperty("https.proxyPort", "80");

		List<BaseReleaseParserModel> m = p.searchByGenre(da,a,"Trance",true);
//
//		ReleaseModel release = new ReleaseModel();
//		ReleaseDetailsParser re = new ReleaseDetailsParser();
//		re.parseReleaseDetails(props, m.get(0), release);
//
//		System.out.println(release);
		System.out.println(m);

//		List<BaseReleaseParserModel> m = p.searchByName("bone man", true);
//		System.out.println(m);

		/*
		List<BaseReleaseParserModel> m = p.searchByReleaseName("Morker-Thoughts-(BLR001)-WEB-2017-CBR", true);
		System.out.println(m);

		ReleaseModel release = new ReleaseModel();
		ReleaseDetailsParser re = new ReleaseDetailsParser(props);
		re.releaseDetails(m.get(0), release);
		System.out.println(release);


		props = new SymusicConf().loadProperties("bestelectronicmusic");
		re = new ReleaseDetailsParser(props);
		re.searchByName(release.getNameWithUnderscore(), release);

		System.out.println(release);
		*/
	}


}
