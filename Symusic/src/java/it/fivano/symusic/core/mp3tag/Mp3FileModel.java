package it.fivano.symusic.core.mp3tag;

public class Mp3FileModel {

	public static enum ID3TAG{
		ID3_V2, ID3_V1;
	}

	private ID3TAG tipoIdtag;
	private String artista;
	private String traccia;
	private String album;
	private String genere;
	private String anno;
	private String numeroTraccia;

	public String getArtista() {
		return artista;
	}
	public void setArtista(String artista) {
		this.artista = artista;
	}
	public String getTraccia() {
		return traccia;
	}
	public void setTraccia(String traccia) {
		this.traccia = traccia;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getGenere() {
		return genere;
	}
	public void setGenere(String genere) {
		this.genere = genere;
	}
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}
	public String getNumeroTraccia() {
		return numeroTraccia;
	}
	public void setNumeroTraccia(String numeroTraccia) {
		this.numeroTraccia = numeroTraccia;
	}
	public ID3TAG getTipoIdtag() {
		return tipoIdtag;
	}
	public void setTipoIdtag(ID3TAG tipoIdtag) {
		this.tipoIdtag = tipoIdtag;
	}

	@Override
	public String toString() {
		return String.format("%s |Artista: %s|Traccia: %s|Album: %s|Anno: %s|Genere: %s|",
				tipoIdtag, artista, traccia, album, anno, genere);
	}

}
