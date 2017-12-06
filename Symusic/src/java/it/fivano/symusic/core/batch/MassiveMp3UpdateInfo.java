package it.fivano.symusic.core.batch;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.fivano.symusic.MyLogger;
import it.fivano.symusic.core.mp3tag.Mp3FileModel;
import it.fivano.symusic.core.mp3tag.Mp3Utils;
import it.fivano.symusic.core.parser.GoogleParser;
import it.fivano.symusic.core.parser.MusixmatchParser;
import it.fivano.symusic.core.parser.model.SongInfo;
import it.ivano.utility.file.FileUtility;
import it.ivano.utility.filtri.AudioFileFilter;

public class MassiveMp3UpdateInfo {

	protected MyLogger log;

	public MassiveMp3UpdateInfo() {
		this.setLogger(getClass());
	}

	public SongInfo getInfoFromMusixmatch(String fullSongName) throws Exception {

		try {

			MusixmatchParser parser = new MusixmatchParser();

			SongInfo res = parser.searchSongInfo(fullSongName);
			log.info(res.toString());

			return res;

		}
		catch(Exception e) {
			throw e;
		}


	}

	public void elaborateFiles(String path) throws Exception {

		Mp3Utils mp3Utils = new Mp3Utils();
		List<File> fileList = FileUtility.caricaFile(path, new AudioFileFilter());
		SongInfo res = null;
		Mp3FileModel mp3Data = null;
		for(File f : fileList) {

			String relName = f.getName().substring(0,f.getName().lastIndexOf("."));
			res = this.getInfoFromMusixmatch(relName);

			String anno = new GoogleParser().searchReleaseYear(relName);
			if(anno!=null) {
				log.info("Recuperato l'anno dalla ricerca Google: "+anno);
				res.setYear(anno);
			}

			mp3Data = mp3Utils.extraiMetadati(f);
			log.info(mp3Data.toString());

			mp3Utils.updateMetadati(f,mp3Data,res);

			this.moveIntoYearFolder(path,f,mp3Data,res);

			//Thread.sleep(2000);
		}

	}


	private void moveIntoYearFolder(String path, File f, Mp3FileModel mp3Data, SongInfo res) {

		String year = res.getYear()!=null ? res.getYear() : mp3Data.getAnno();

		if(year!=null) {
			File yearFolder = new File(path+"\\"+year);
			if(!yearFolder.exists())
				yearFolder.mkdir();

			f.renameTo(new File(yearFolder,f.getName()));



		}

	}

	protected void setLogger(Class<?> classe) {
		log = new MyLogger(classe);
	}

	public static void main(String[] args) throws Exception {

		String path = "C:\\Users\\ivano\\ROOT\\IVANO\\ARCHIVIO_IVANO\\Mp3\\VA - Le 100 Canzoni Italianei di Oggi (2013)";

		MassiveMp3UpdateInfo m = new MassiveMp3UpdateInfo();
		m.elaborateFiles(path);
	}



}


