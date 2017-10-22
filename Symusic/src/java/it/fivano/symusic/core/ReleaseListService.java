package it.fivano.symusic.core;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import it.fivano.symusic.SymusicUtility;
import it.fivano.symusic.backend.service.ReleaseService;
import it.fivano.symusic.core.parser.ReleaseDetailsParser;
import it.fivano.symusic.core.parser.ReleaseListParser;
import it.fivano.symusic.core.parser.YoutubeParser;
import it.fivano.symusic.core.parser.model.BaseReleaseParserModel;
import it.fivano.symusic.core.thread.SupportObject;
import it.fivano.symusic.core.util.C.SearchType;
import it.fivano.symusic.core.util.SearchInput;
import it.fivano.symusic.exception.BackEndException;
import it.fivano.symusic.exception.ParseReleaseException;
import it.fivano.symusic.model.ReleaseModel;
import it.fivano.symusic.model.VideoModel;

public class ReleaseListService extends ReleaseSiteService {

	protected boolean enableBeatportService;
	protected boolean enableScenelogService;
	protected boolean enableYoutubeService;

	private List<ReleaseModel> listRelease;

	public ReleaseListService(Long idUser) throws IOException {
		super();
		this.idUser = idUser;
		enableBeatportService = true;
		enableScenelogService = true;
		enableYoutubeService = true;
		this.setLogger(getClass());
	}


	//public List<ReleaseModel> loadReleaseList(String genereOrCrew, Date da, Date a, SearchType searchType) throws BackEndException, ParseReleaseException {
	public List<ReleaseModel> loadReleaseList(SearchInput searchInput) throws BackEndException, ParseReleaseException {

//		this.genre = genereOrCrew;
		listRelease = new ArrayList<ReleaseModel>();
		try {

			// fase ricerca titoli release in base ai criteri scelti (genere, crew e range data)
			Properties currProps = null;
			List<BaseReleaseParserModel> resultList = null;
			for(String currSite : generalConf.RELEASE_LIST_SITE) {
				currProps = generalConf.loadProperties(currSite.trim());
				ReleaseListParser listParser = new ReleaseListParser(currProps);

				if(searchInput.getSearchType().equals(SearchType.SEARCH_BY_CREW)) {
					resultList = listParser.searchByCrew(searchInput.getDataDa(), searchInput.getDataA(), searchInput.getCrew(), searchInput.isExcludeRadioRip());
				} else {
					resultList = listParser.searchByGenre(searchInput.getDataDa(), searchInput.getDataA(), searchInput.getGenre(), searchInput.isExcludeRadioRip());
				}

				if(!resultList.isEmpty())
					break;
			}


			// fase recupero dettagli della release
			YoutubeParser youtube = new YoutubeParser();
			GoogleService google = new GoogleService();
			currProps = null;
			int count = 0;
			ReleaseModel currRelease = null;
			for(BaseReleaseParserModel parserModel : resultList) {

				count++;
				currRelease = new ReleaseModel();
				log.info("********* "+parserModel.getReleaseName()+" *********");


				if(searchInput.isExcludeRadioRip() && this.isRadioRipRelease(parserModel.getReleaseName().replace(" ", "-"))) {
					log.info(parserModel.getReleaseName()+" ignorata poichè è un RIP");
					continue;
				}

				if(searchInput.isExcludeRadioRip() && this.isVARelease(parserModel.getReleaseName())) {
					log.info(parserModel.getReleaseName()+" ignorata poichè è una VA");
					continue;
				}

				// CONTROLLA SE LA RELEASE E' GIA' PRESENTE
				boolean isRecuperato = false;
				ReleaseService relServ = new ReleaseService();
				ReleaseModel relDb = relServ.getReleaseFull(parserModel.getReleaseName(), idUser);
				if(relDb!=null) {
					log.info(parserModel.getReleaseName()+" e' gia' presente nel database con id = "+relDb.getId());

					isRecuperato = true;
					currRelease = relDb; // SOSTITUISCE I DATI FINO AD ORA ESTRATTI CON QUELLI DEL DB
				}

				// YOUTUBE VIDEO
				if(enableYoutubeService && (currRelease.getVideos()==null || currRelease.getVideos().isEmpty()) ) {
					List<VideoModel> youtubeVideos = youtube.searchYoutubeVideos(parserModel.getReleaseName());
					currRelease.setVideos(youtubeVideos);

				}

				// SE SUL DB CI SONO QUASI TUTTI I DATI PROSEGUE CON LA SUCCESSIVA RELEASE
				if(isRecuperato && currRelease.getDetailsPercent()>=70) {
					log.info("Ci sono abbastanza dettagli per la release "+currRelease.getNameWithUnderscore());
				}
				else {

					for(String currSite : generalConf.RELEASE_DETAIL_SITE) {
						currProps = generalConf.loadProperties(currSite);
						ReleaseDetailsParser detailsParser = new ReleaseDetailsParser(currProps);

						parserModel.setUrlReleaseDetails(null); // reset url
						detailsParser.releaseDetails(parserModel, currRelease);

						if(currRelease.getDetailsPercent()>=80) {
							log.info("Ci sono abbastanza dettagli per la release "+currRelease.getNameWithUnderscore());
							break;
						}
					}


					if(!this.verificaAnnoRelease(currRelease,searchInput.getAnnoMin(),searchInput.getAnnoMax())) {
						log.info(parserModel.getReleaseName()+" ignorata poichè l'anno non è all'interno del range.");
						continue;
					}
				}

				listRelease.add(currRelease);

				// AGGIORNAMENTI DEI DATI SUL DB
				this.saveOrUpdateRelease(currRelease, isRecuperato);

				google.addManualSearchLink(currRelease);
				youtube.addManualSearchLink(currRelease); // link a youtube per la ricerca manuale

				this.addSimilarRelease(currRelease);

				log.info("********* Processate "+count+" release su "+resultList.size()+"*********");

			}

			// INIT OGGETTO DI SUPPORTO UNICO PER TUTTI I THREAD
			SupportObject supp = new SupportObject();
			supp.setEnableBeatportService(enableBeatportService);
			supp.setEnableScenelogService(enableScenelogService);
			supp.setEnableYoutubeService(enableYoutubeService);

		} catch (Exception e) {
			throw new ParseReleaseException("Errore nel parsing delle pagine",e);
		}

		return listRelease;

	}

	private ReleaseModel arricchisciRelease(BaseReleaseParserModel sc, ReleaseModel release) throws ParseException {
		release.setCrew(sc.getCrew());
		release.setGenre(SymusicUtility.creaGenere(sc.getGenre()));
		if(release.getReleaseDate()==null)
			release.setReleaseDate(SymusicUtility.getStandardDate(sc.getReleaseDate()));
		return release;
	}



	private boolean verificaAnnoRelease(ReleaseModel release, Integer annoDa, Integer annoAl) {
		if(release.getYear()!=null) {

			try {
				Integer annoRel = Integer.parseInt(release.getYear());

				if(annoRel<=annoAl && annoRel>=annoDa) {
					return true;
				}
			} catch(Exception e) {
				log.error("Errore nella verifica dell'anno release. "+e.getMessage());
				return true; // anno non recuperato... per defualt la release è considerata
			}

			return false;
		}
		else
			return true; // anno non recuperato... per defualt la release è considerata


	}




	private ReleaseModel verificaPresenzaInLista(ReleaseModel release) {
		for(ReleaseModel r : listRelease) {
			if(r.equals(release)) {
				log.info("Release "+release+" e' gia' presente in lista, si effettuera' la fusione");
				return r;
			}
		}
		return null;
	}


	private String genericFilter(String text) {
		if(text!=null) {
			text = text.replaceAll("[()]", "").trim();
			if(!String.valueOf(text.toCharArray()[0]).matches("[a-zA-Z0-9]")) {
				return text.substring(1);
			}
		}
		return null;
	}


	private boolean downloadReleaseDay(Date dateInDate, Date da, Date a) {
		boolean result = (dateInDate.compareTo(da)>=0) && (dateInDate.compareTo(a)<=0);
		return result;
	}


	public static void main(String[] args) throws IOException, ParseException, BackEndException, ParseReleaseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date da = sdf.parse("20171004");
		Date a = sdf.parse("20171004");

		SearchInput searchInput = new SearchInput();
		searchInput.setDataDa(da);
		searchInput.setDataA(a);
		searchInput.setGenre("Dance");
		searchInput.setAnnoMin(2017);
		searchInput.setAnnoMax(2017);
		searchInput.setExcludeRadioRip(true);
		searchInput.setExcludeVa(true);
		searchInput.setSearchType(SearchType.SEARCH_BY_GENRE);

		ReleaseListService s = new ReleaseListService(1L);
		List<ReleaseModel> res = s.loadReleaseList(searchInput);
//		List<ReleaseModel> res = s.parseMusicDLRelease("trance",da,a);
		for(ReleaseModel r : res)
			System.out.println(r);
//		System.out.println(s.genericFilter("fhfh( dewdef) fef"));
//		System.out.println(s.genericFilter("fhfh( dewdef) fef"));
//		System.out.println("fhfh( dewdef) fef".replaceAll("[()]", ""));
	}



	@Override
	protected String applyFilterSearch(String result) {
		return result;
	}



}
