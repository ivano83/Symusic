package it.fivano.symusic.core.mp3tag;

import java.io.File;
import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;

import org.blinkenlights.jid3.ID3Exception;
import org.blinkenlights.jid3.ID3Tag;
import org.blinkenlights.jid3.MP3File;
import org.blinkenlights.jid3.MediaFile;
import org.blinkenlights.jid3.v1.ID3V1Tag;
import org.blinkenlights.jid3.v2.ID3V2Tag;
import org.blinkenlights.jid3.v2.ID3V2_3_0Tag;

import it.fivano.symusic.MyLogger;
import it.fivano.symusic.core.mp3tag.Mp3FileModel.ID3TAG;
import it.fivano.symusic.core.parser.model.SongInfo;

public class Mp3Utils {

	protected MyLogger log;

	public Mp3Utils() {
		log = new MyLogger(getClass());
	}

	public Mp3FileModel extraiMetadati(File file) throws Exception {

		Mp3FileModel res = new Mp3FileModel();
		try {
			if(file==null) {
				throw new Exception("Il file e' nullo e non puo' essere processato");
			}

			// create an MP3File object representing our chosen file
			MediaFile oMediaFile = new MP3File(file);

			ID3V2Tag tag = oMediaFile.getID3V2Tag();
			ID3V1Tag tag2 = oMediaFile.getID3V1Tag();

			if(tag!=null) {
				res.setTipoIdtag(ID3TAG.ID3_V2);
				res.setAlbum(tag.getAlbum());
				try { res.setAnno(tag.getYear()+""); } catch (Exception e) { }
				res.setArtista(tag.getArtist());
				try { res.setNumeroTraccia(tag.getTrackNumber()+""); } catch (Exception e) { }
				res.setGenere(tag.getGenre());
				res.setTraccia(tag.getTitle());
			} else if(tag2!=null) {
				res.setTipoIdtag(ID3TAG.ID3_V1);
				res.setAlbum(tag2.getAlbum());
				res.setAnno(tag2.getYear());
				res.setArtista(tag2.getArtist());
				res.setGenere(tag2.getGenre().toString());
				res.setTraccia(tag2.getTitle());
			} else {

			}

		} catch (ID3Exception e) {
			throw new Exception("Errore nel recupero dei dati per il file mp3 '"+file.getName()+"'",e);
		} catch (Exception e) {
			throw new Exception("Errore nel recupero dei dati per il file '"+file.getName()+"'", e);
		}
		return res;

	}

	public void updateMetadati(File file, Mp3FileModel mp3Data, SongInfo res) throws Exception {

		try {
			if(file==null) {
				throw new Exception("Il file e' nullo e non puo' essere processato");
			}
			if(res==null)
				return;

			// create an MP3File object representing our chosen file
			MP3File oMediaFile = new MP3File(file);

			ID3V2Tag tag = oMediaFile.getID3V2Tag();
			ID3V2_3_0Tag tagNew = new ID3V2_3_0Tag();

			if(tag!=null && tag.getArtist()==null && res.getArtistName()!=null) {
				tag.setArtist(res.getArtistName());
			} else if(res.getArtistName()!=null)
				tagNew.setArtist(res.getArtistName());

			if(tag!=null && tag.getTitle()==null && res.getSongName()!=null) {
				tag.setTitle(res.getSongName());
			} else if(res.getSongName()!=null)
				tagNew.setTitle(res.getSongName());

			if(tag!=null && tag.getAlbum()==null && res.getAlbumName()!=null) {
				tag.setAlbum(res.getAlbumName());
			} else if(res.getAlbumName()!=null)
				tagNew.setAlbum(res.getAlbumName());


			int year = 0;
			try { year = tag.getYear(); } catch (Exception e) { }
//			if(year==0 && res.getReleaseDate()!=null) {
			if(res.getReleaseDate()!=null) {
				if(tag!=null) tag.setYear(Integer.parseInt(res.getYear())) ;
				else tagNew.setYear(Integer.parseInt(res.getYear()));
			}
			if(year!=0 && res.getReleaseDate()!=null && year!=Integer.parseInt(res.getYear())) {
				log.warn("L'anno nei tag non coicide con quello recuperato: "+mp3Data+" --> dal web = "+res.getYear());
			}

			oMediaFile.removeID3V2Tag();
			oMediaFile.setID3Tag(tag!=null?tag:tagNew);
			oMediaFile.sync();

		} catch (ID3Exception e) {
			throw new Exception("Errore nel recupero dei dati per il file mp3 '"+file.getName()+"'",e);
		} catch (Exception e) {
			throw new Exception("Errore nel recupero dei dati per il file '"+file.getName()+"'", e);
		}

	}

	public static void main(String[] args) throws Exception {

		Mp3Utils u = new Mp3Utils();
		File f = new File("C:\\Users\\ivano\\Downloads\\US Billboard Hot 100 Singles\\Alessia Cara - Scars To Your Beautiful.mp3");
		Mp3FileModel res = u.extraiMetadati(f);
		System.out.println(res.getAlbum());
	}


}
