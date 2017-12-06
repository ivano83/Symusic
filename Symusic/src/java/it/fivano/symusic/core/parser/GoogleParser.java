package it.fivano.symusic.core.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import it.fivano.symusic.SymusicUtility;
import it.fivano.symusic.SymusicUtility.LevelSimilarity;
import it.fivano.symusic.backend.service.VideoService;
import it.fivano.symusic.conf.YoutubeConf;
import it.fivano.symusic.exception.BackEndException;
import it.fivano.symusic.exception.ParseReleaseException;
import it.fivano.symusic.model.ReleaseModel;
import it.fivano.symusic.model.VideoModel;

public class GoogleParser extends GenericParser {

	private YoutubeConf conf;

	public GoogleParser() throws IOException {
		this.conf = new YoutubeConf();
		this.setLogger(getClass());
	}

	public String searchReleaseYear(String nomeCompleto) throws ParseReleaseException {

		try {

			nomeCompleto = (nomeCompleto+" data di uscita").replace(" ","+");

			Elements videoGroup = null;
			Document doc = null;
			String urlConn = null;

			// pagina di inizio
			urlConn = "https://www.google.it/search?q="+nomeCompleto;
			log.info("Connessione in corso --> "+urlConn);
			String userAgent = this.randomUserAgent();
			doc = Jsoup.connect(urlConn).timeout(TIMEOUT).userAgent(userAgent).get();

			videoGroup = doc.getElementsByClass("_XWk");
			if(!videoGroup.isEmpty()) {

				String anno = videoGroup.get(0).text();
				if(anno.trim().matches("\\d\\d\\d\\d"))
					return anno;

			}



		} catch(Exception e) {
			log.error("Errore nel parsing pagina google", e);
//			throw new ParseReleaseException("Errore nel parsing",e);
		}

		return null;

	}


	@Override
	protected String applyFilterSearch(String t) {
		t = t.replaceAll("[-,.!?&']", " ").replace(" feat ", " ").replace(" ft ", " ")
				.replace("  ", " ").replace(" and ", " ").replace(" ", "+");
		return t;
	}

	public static void main(String[] args) throws Exception {
		GoogleParser p = new GoogleParser();
//		List<VideoModel> v = p.searchYoutubeVideos("Crystal_Lake_Feat_Barbie_G_-_Darkness-(HUMF001)-WEB-2013-FMC");
//		System.out.println(v);
		System.out.println(p.searchReleaseYear("Raf - Le Ragioni Del Cuore"));

	}

}
