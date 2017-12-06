package it.fivano.symusic.core.batch;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import it.fivano.symusic.MyLogger;

public class EpriceBlackHour {

	protected MyLogger log;

	public EpriceBlackHour() {
		this.setLogger(getClass());
	}

	public boolean checkBlackHour() throws Exception {

		try {

			Element firstRes = null;
			Document doc = null;
			String urlConn = "https://www.eprice.it/black-hour?metb=telone-hp-black-hour-beko";

			// pagina di inizio
			log.info("Connessione in corso --> "+urlConn);
			String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:24.0) Gecko/20100101 Firefox/24.0";
			doc = Jsoup.connect(urlConn).timeout(6000).userAgent(userAgent).get();

			firstRes = doc.getElementById("bf_boxActive");
			if(firstRes!=null) {
				log.info("OFFERTA IN CORSO --> "+firstRes.text());
			}

			firstRes = doc.getElementById("bf_boxEnd");
			if(firstRes!=null) {
				log.info("OFFERTA TERMINATA --> "+firstRes.text());

			}
			else {
				firstRes = doc.getElementById("bf_boxActive");
				if(firstRes!=null) {
					log.info("OFFERTA IN CORSO --> "+firstRes.text());
					playSound();
					Thread.sleep(18000);
					return true;
				} else {
					firstRes = doc.getElementById("bf_boxInit");
					if(firstRes!=null) {
						log.info("OFFERTA DEVE INIZIARE --> "+firstRes.text());
					}
				}
			}



		} catch(Exception e) {
			log.error("Errore nel parsing", e);
//			throw new ParseReleaseException("Errore nel parsing",e);
		}
		return false;

	}

	public void playSound() {
		    try {
		        Clip clip = AudioSystem.getClip();
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(
		        EpriceBlackHour.class.getResourceAsStream("classic_ring_phone.wav"));
		        clip.open(inputStream);
		        clip.start();
		      } catch (Exception e) {
		    	  log.error(e.getMessage(),e);
		      }
		}

	protected void setLogger(Class<?> classe) {
		log = new MyLogger(classe);
	}

	public static void main(String[] args) throws Exception {

		EpriceBlackHour m = new EpriceBlackHour();
		int cicloInCorso = 0;
		while(cicloInCorso < 5) {
			if(m.checkBlackHour())
				cicloInCorso++;
			Thread.sleep(60000);
		}
	}



}


