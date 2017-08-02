package it.fivano.symusic.core.parser;

import it.fivano.symusic.SymusicUtility;
import it.fivano.symusic.core.parser.model.BaseReleaseParserModel;
import it.fivano.symusic.core.util.C;
import it.fivano.symusic.core.util.UserAgent;
import it.fivano.symusic.model.GenreModel;
import it.fivano.symusic.model.LinkModel;
import it.fivano.symusic.model.ReleaseModel;
import it.fivano.symusic.model.TrackModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

public class ReleaseDetailsParser extends BaseParser {


	public ReleaseDetailsParser(Properties props) {
		super();
		try {
			this.setLogger(getClass());
			this.setProps(props);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public boolean releaseDetails(BaseReleaseParserModel searchModel, ReleaseModel release) throws Exception {

		Document doc = null;
		try {

			if(searchModel==null)
				return true;

			// SE RELEASE ANCORA NON PRESENTE SI CREA L'OGGETTO
			if(release==null)
				throw new Exception("Bisogna inizializzare l'oggetto ReleaseModel");

			release = this.popolaRelease(release, searchModel);

			if(searchModel.getUrlReleaseDetails()!=null) {
				// INVOCARE DIRETTAMENTE LA URL ED ESTRARRE I DATI
				log.info(String.format("[%s] \t connecting to: %s ",site,searchModel.getUrlReleaseDetails()));
				String userAgent = UserAgent.randomUserAgent();
				doc = Jsoup.connect(searchModel.getUrlReleaseDetails()).userAgent(userAgent).ignoreHttpErrors(true).get();

				if(antiDDOS.isAntiDDOS(doc)) {
					doc = this.bypassAntiDDOS(doc, props.getProperty("base_url"), searchModel.getUrlReleaseDetails(), userAgent);
				}

			} else {
				String msg = "Nessuna URL release definita... impossibile recupere i dettagli! ";
				log.error(msg);
				throw new Exception(msg);
			}



			// genere
			if(props.getProperty("release_genre")!=null && release.qualitaDatiMigliore(C.GENRE, quality(C.GENRE))) {
					GenreModel genre = new GenreModel();
					genre.setName((String)this.runRole(doc, props.getProperty("release_genre")));
					release.setGenre(genre);
					release.getMappaQualitaDati().put(C.GENRE, quality(C.GENRE));
			}

			// tracks
			if(props.getProperty("release_loop_tracks")!=null) {
				Elements tracks = (Elements)this.runRole(doc, props.getProperty("release_loop_tracks"));

				List<TrackModel> tracksList = new ArrayList<TrackModel>();

				TrackModel currTrack = null;
				int numTr = 1;

				for(Element track : tracks) {
					currTrack = new TrackModel();
					// NUMERO DI TRACCIA
					currTrack.setTrackNumber(numTr);
					// NOME TRACCIA
					String text = (String)this.runRole(track, props.getProperty("release_track_name"));
					text = text.replaceFirst("\\d+\\.",""); // se c'e' il numero di track, lo elimina
					currTrack.setTrackName(text);

					tracksList.add(currTrack);
					log.info(String.format("[%s] \t TRACK: %s ",site,currTrack));
					numTr++;
				}

				release.getMappaQualitaDati().put(C.TRACKS, quality(C.TRACKS));

				if(release.qualitaDatiMigliore(C.TRACKS, quality(C.TRACKS))) {
					release.setTracks(tracksList);
				}
//				else if(release.qualitaDatiMigliore(C.TRACKS, quality(C.TRACKS))) {
//					// PRIORITA' ALLE TRACCE SCENELOG
//					release.setTracks(SymusicUtility.chooseTrack(tracksList, release.getTracks(), true));
//				}


			}

			Elements releaseDownloads = (Elements)this.runRole(doc, props.getProperty("release_loop_download"));
			LinkModel currLink = null;
			for(Element dl : releaseDownloads) {
				currLink = this.popolateLink(dl);
				release.addLink(currLink);
				log.info(String.format("[%s] \t LINK: %s ",site,currLink));
			}

			/**

			Element releaseDownloads = doc.getElementsByClass(conf.RELEASE_DOWNLOAD).get(0);

			Elements downloads = releaseDownloads.getElementsByTag("a");
			for(Element dl : downloads) {
				release.addLink(this.popolateLink(dl));
			}

			SymusicUtility.updateReleaseExtraction(release.getReleaseExtraction(),true,AreaExtraction.SCENELOG);
			countFailConnection = 0;
		} catch(Exception e) {
			log.error("Nessun risultato ottenuto oppure errore nel parsing. "+e.getMessage());
			countFailConnection++;
			SymusicUtility.updateReleaseExtraction(release.getReleaseExtraction(),false,AreaExtraction.SCENELOG);
//			throw new ParseReleaseException("Errore nel parsing",e);
		}
		*/

		} catch(Exception e) {
			throw e;
		}

		return true;

	}




	private ReleaseModel popolaRelease(ReleaseModel release, BaseReleaseParserModel scenelogModel) throws ParseException {
		if(scenelogModel.getReleaseName()!=null) {
			release.setNameWithUnderscore(scenelogModel.getReleaseName());
			release.setName(scenelogModel.getReleaseName().replace("_", " "));
		}
		if(scenelogModel.getReleaseDate()!=null && release.qualitaDatiMigliore(C.REL_DATE, quality(C.REL_DATE))) {
			release.setReleaseDate(SymusicUtility.getStandardDate(scenelogModel.getReleaseDate()));
			release.getMappaQualitaDati().put(C.REL_DATE, quality(C.REL_DATE));
		}

		if(scenelogModel.getGenre()!=null && release.qualitaDatiMigliore(C.GENRE, quality(C.GENRE))) {
			GenreModel genre = new GenreModel();
			genre.setName(scenelogModel.getGenre());
			release.setGenre(genre);
			release.getMappaQualitaDati().put(C.GENRE, quality(C.GENRE));
		}

		// AGGIUNGE ULTERIORI INFO DELLA RELEASE A PARTIRE DAL NOME
		// ES. CREW E ANNO RELEASE
		SymusicUtility.processReleaseName(release,qualityMap);

		return release;
	}


	public static void main(String[] args) {

		Element el = new Element(Tag.valueOf("span"), "");
		el.append("Ciao");
		System.out.println(el.text());
		System.out.println(el.html());
	}

}
