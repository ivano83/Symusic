package it.fivano.symusic.core.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import it.fivano.symusic.AntiDdosUtility;
import it.fivano.symusic.MyLogger;
import it.fivano.symusic.model.LinkModel;

public class BaseParser {

	protected MyLogger log;

	protected static final int TIMEOUT = 9000;
	protected AntiDdosUtility antiDDOS;

	protected Properties props;
	protected String site;
	protected Map<String,Integer> qualityMap;

	public BaseParser() {
		try {
			antiDDOS = new AntiDdosUtility();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected Object runRole(Element inputElement, String routingReleaseName) throws Exception {

		if(routingReleaseName==null)
			return null;

		String[] regole = routingReleaseName.split("\\|\\|");

		Elements tmp = new Elements(inputElement);
		String regola = null;
		Object valore = null;
		for(int i=0; i<regole.length; i++) {
			if(tmp==null)
				throw new Exception("La regola "+regola+" non ha prodotto alcun risultato");

			regola = regole[i].trim();
			if(regola.contains("get=")) {
				tmp = new Elements(tmp.get(Integer.parseInt(regola.replace("get=", "").trim())));
			} else if(regola.contains("attr=")) {
				valore = tmp.get(0).attr(regola.replace("attr=", "").trim());
			} else if(regola.contains("text()")) {
				valore = tmp.get(0).text();
			} else if(regola.contains("html()")) {
				valore = tmp.get(0).html();
			} else if(regola.contains("split=")) {
				String[] regSplit = regola.replace("split=", "").split("\\(");
				if(regSplit.length==2)
					valore = ((String)valore).split(regSplit[0])[Integer.parseInt(regSplit[1].replace(")", ""))];
				else {
					valore = ((String)valore).split(regSplit[0]);
				}
			} else if(regola.contains("loop=")) {
				String[] regSplit = ((String)valore).split(regola.replace("loop=", "").trim());
				Elements elems = new Elements();
				Element currEl = null;
				for(String s : regSplit) {
					currEl = new Element(Tag.valueOf("span"), "");
					currEl.append(s);
					elems.add(currEl);
				}
				valore = elems;
			} else if(regola.contains("class=")) {
				tmp = tmp.get(0).getElementsByClass(regola.replace("class=", "").trim());
			} else {
				tmp = tmp.select(regola);
			}


		}

		// senza il recupero di un valore, viene presa la lista degli elementi correnti
		if(tmp!=null && !tmp.isEmpty() && valore==null)
			valore = tmp;


		return valore;
	}

	protected String formatSearchValue(String val, String flow) {
		if(flow==null)
			return val;

		String[] regole = flow.split("\\|\\|");

		String regola = null;
		String valore = val;
		for(int i=0; i<regole.length; i++) {
			regola = regole[i].trim();

			if(regola.contains("replaceAll=")) {
				String[] regSplit = regola.replace("replaceAll=", "").split("\\(");
				valore = valore.replaceAll(regSplit[0],regSplit[1].replace(")", ""));

			} else if(regola.contains("replace=")) {
				String[] regSplit = regola.replace("replace=", "").split("\\(");
				valore = valore.replace(regSplit[0],regSplit[1].replace(")", ""));

			}
		}

		return valore;

	}

	protected LinkModel popolateLink(Element dl) {
		LinkModel currLink = new LinkModel();
		currLink.setLink(dl.attr("href"));
		currLink.setName((dl.attr("href").length()>70)? dl.attr("href").substring(0,70)+"..." : dl.attr("href"));

		return currLink;
	}

	protected Document bypassAntiDDOS(Document doc, String baseUrl, String urlToRedirect, String userAgent) throws Exception {

//		String url = this.getBypassUrlOld(doc,baseUrl);
//		log.info(doc.text());
		String url = antiDDOS.cloudFlareSolve(doc.html(), baseUrl, userAgent);

//		String userAgent = this.randomUserAgent();
//		log.info(url);
//		doc = Jsoup.connect(url).timeout(TIMEOUT).userAgent(userAgent).ignoreHttpErrors(true).get();
		doc = Jsoup.connect(url).timeout(6000).header("Referer", urlToRedirect)
    			.userAgent(userAgent)
    			.ignoreHttpErrors(true).get();

//		log.info(doc.text());

		return doc;
	}

	protected void setLogger(Class<?> classe) {
		log = new MyLogger(classe);
	}

	protected void setProps(Properties props2) {
		this.props = props2;
		this.site = props.getProperty("site");
		this.qualityMap = new HashMap<String, Integer>();
		for(String tmp : this.props.getProperty("quality").split("\\|")) {
			qualityMap.put(tmp.split("=")[0].trim(), Integer.parseInt(tmp.split("=")[1].trim()));
		}
	}


	protected Integer quality(String name) {
		if(qualityMap.containsKey(name))
			return qualityMap.get(name);
		else
			return 5; // defualt
	}


}
