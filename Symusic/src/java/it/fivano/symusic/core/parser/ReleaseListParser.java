package it.fivano.symusic.core.parser;

import it.fivano.symusic.AntiDdosUtility;
import it.fivano.symusic.MyLogger;
import it.fivano.symusic.SymusicUtility;
import it.fivano.symusic.conf.SymusicConf;
import it.fivano.symusic.core.parser.model.BaseReleaseParserModel;
import it.fivano.symusic.core.util.SearchInput;
import it.fivano.symusic.core.util.UserAgent;
import it.fivano.symusic.exception.ParseReleaseException;
import it.fivano.symusic.model.ReleaseModel;

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

public class ReleaseListParser extends BaseParser {

	
	protected SearchInput searchInput;
	
	public ReleaseListParser() {
		super();
		try {
			this.setLogger(getClass());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<BaseReleaseParserModel> searchByGenre(Properties props, Date da, Date a, String genre, boolean excludeRadioRip) throws ParseReleaseException {
		
		this.setProps(props);
		searchInput = new SearchInput();
		searchInput.setDataDa(da);
		searchInput.setDataA(a);
		searchInput.setGenre(genre==null?"":genre);
		searchInput.setExcludeRadioRip(excludeRadioRip);
		
		String urlPage = props.getProperty("base_url")+props.getProperty("search_byGenre").replace("{0}", searchInput.getGenre());
		
		
		return search(urlPage);
		
	}
	
	public List<BaseReleaseParserModel> search(String url) throws ParseReleaseException {
		
		String urlPage = url;
		
		List<BaseReleaseParserModel> listaRelease = new ArrayList<BaseReleaseParserModel>();
		
		int pageGap = props.getProperty("page_gap")!=null ? Integer.parseInt(props.getProperty("page_gap")) : 1;
		int pagina = 1;
		while(parseFullPage(listaRelease, urlPage)) {
			
			urlPage = url + props.getProperty("params_page").replace("{0}", (pageGap*pagina)+"");
			
			pagina++;
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

			Elements releaseGroup = doc.getElementsByClass(props.getProperty("loop_list_item"));
			if(releaseGroup.size()>0) {
				BaseReleaseParserModel release = null;
				log.info("####################################");
				for(Element tmp : releaseGroup) {

					release = this.popolaDatiRelease(tmp);

					if(searchInput.getDataDa().after(release.getReleaseDate()))
						continuaPaginaSuccessiva = false;
					
					if((searchInput.isExcludeRadioRip() && release.isRadioRip()) || !continuaPaginaSuccessiva) {
						// e' un radio rip o un fuori range
					}
					else {
						listaRelease.add(release);
						log.info("|"+release+"| acquisita");
					}
					
				}
				
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
		release.setUrlReleaseDetails((String)this.runRole(tmp,props.getProperty("item_url")));

		// RELEASE DATE
		release.setReleaseDate(dateInDate);
		
		// GENERE
		String genre = searchInput.getGenre().length()==0 ? (String)this.runRole(tmp,props.getProperty("item_genre")) : searchInput.getGenre();
		release.setGenre(StringUtils.capitalize(genre));

		// RANGE DATA, SOLO LE RELEASE COMPRESE DA - A
		if(searchInput.getDataDa()!=null && searchInput.getDataA()!=null) {
			release.setDateInRange(this.isInRange(dateInDate, searchInput.getDataDa(), searchInput.getDataA()));
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

	
	
	public static void main(String[] args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date da = sdf.parse("13-07-2017");
		Date a = sdf.parse("13-07-2017");
		Properties props = new SymusicConf().loadProperties("scnlog");
		ReleaseListParser p = new ReleaseListParser();
		System.setProperty("https.proxyHost", "10.55.32.23");
		System.setProperty("https.proxyPort", "80");
		
		List<BaseReleaseParserModel> m = p.searchByGenre(props,da,a,"trance",true);
		
		ReleaseModel release = new ReleaseModel();
		ReleaseDetailsParser re = new ReleaseDetailsParser();
		re.parseReleaseDetails(props, m.get(0), release);

		System.out.println(release);
	}
	
	
}
