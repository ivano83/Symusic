package it.fivano.symusic.core.parser;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import it.fivano.symusic.core.parser.model.SongInfo;
import it.fivano.symusic.exception.ParseReleaseException;

public class MusixmatchParser extends GenericParser {


	public MusixmatchParser() throws IOException {
		this.setLogger(getClass());
	}

	public SongInfo searchSongInfo(String fullSongName) throws ParseReleaseException {

		SongInfo result = new SongInfo();
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
		try {
			int tentativi = 0;

			String trackSearch = applyFilterSearch(fullSongName);

			Elements trackResGroup = null;
			Element firstRes = null;
			Document doc = null;
			String urlConn = null;
			String baseUrl = "https://www.musixmatch.com";
			try {

				// pagina di inizio
				urlConn = String.format(baseUrl+"/it/search/%s/tracks", trackSearch);
				log.info("Connessione in corso --> "+urlConn);
				String userAgent = this.randomUserAgent();
				doc = Jsoup.connect(urlConn).timeout(TIMEOUT).userAgent(userAgent).get();

				trackResGroup = doc.getElementsByClass("box-content");
				if(trackResGroup.isEmpty()) {
					int count = 0;
					while(trackResGroup.isEmpty() && count<5) {
						log.info("Wait 20 second... "+urlConn);
						Thread.sleep(20000);
						doc = Jsoup.connect(urlConn).timeout(TIMEOUT).userAgent(userAgent).get();
						trackResGroup = doc.getElementsByClass("box-content");
						count++;


					}
				}
				if(trackResGroup.get(0).getElementsByClass("empty").isEmpty()) {

					boolean trovato = false;
					int i = 0;
					trackResGroup = trackResGroup.get(0).getElementsByClass("media-card-text");
					while(i<trackResGroup.size() && !trovato ) {
						firstRes = trackResGroup.get(i);

						String urlDettaglio = firstRes.select("h2 > a").attr("href");
						String trackName = firstRes.select("h2 > a").text();
						String artistName = firstRes.select("h3").text();

						result.setUrlDetails(urlDettaglio);
						if(result.getArtistName()==null)
							result.setArtistName(artistName);
						if(result.getSongName()==null)
							result.setSongName(trackName);

						urlConn = baseUrl + urlDettaglio;
						log.info("\t\t"+urlConn);
						doc = Jsoup.connect(urlConn).timeout(TIMEOUT).userAgent(userAgent).get();

						if(!doc.getElementsByClass("mui-cell__text").isEmpty()) {

							String albumName = null;
							String releaseDate = null;
							try {
								firstRes = doc.getElementsByClass("mui-cell__text").get(0);
								albumName = firstRes.select("h2").text();
								releaseDate = firstRes.select("h3").text().replace("º", "");

								result.setAlbumName(albumName);
								result.setReleaseDate(sdf.parse(releaseDate));

								trovato = true;

							} catch (ParseException e) {
								log.warn("Impossibile fare il parsing della data: "+releaseDate);
							} catch (Exception e) {
								log.warn("Impossibile recuperare i dati anni e album ");
							}

						}


						i++;
					}

				}



			} catch (Exception e1) {
				log.error("Errore nel recupero dei dati",e1);
				tentativi++;
			}


		} catch(Exception e) {
			log.error("Errore nel parsing", e);
//			throw new ParseReleaseException("Errore nel parsing",e);
		}

		return result;

	}


	@Override
	protected String applyFilterSearch(String t) {
		t = t.toLowerCase()
				.replace("à", "a").replace("è", "e").replace("é", "e").replace("ì", "ì").replace("ò", "o").replace("ù", "u")
				.replaceAll("[^a-zA-Z0-9]+"," ").replace(" feat ", " ").replace(" ft ", " ")
				.replace(" and ", " ").replace(" rmx ", " ").replace(" remix ", " ").replace("  ", " ").replace(" ", "%20").trim();
		return t;
	}

	public static void main(String[] args) throws Exception {
		MusixmatchParser p = new MusixmatchParser();
//		List<VideoModel> v = p.searchYoutubeVideos("Crystal_Lake_Feat_Barbie_G_-_Darkness-(HUMF001)-WEB-2013-FMC");
//		System.out.println(v);

		System.out.println(p.searchSongInfo("AlunaGeorge - You Know You Like It"));


	}




}