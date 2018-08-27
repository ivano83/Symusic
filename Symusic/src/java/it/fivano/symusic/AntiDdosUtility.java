package it.fivano.symusic;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class AntiDdosUtility {

	private MyLogger log;

	public AntiDdosUtility() {
		log = new MyLogger(getClass());
	}

	/**
	 * Example:
	 * <script type="text/javascript">
  //<![CDATA[
  (function(){
    var a = function() {try{return !!window.addEventListener} catch(e) {return !1} },
    b = function(b, c) {a() ? document.addEventListener("DOMContentLoaded", b, c) : document.attachEvent("onreadystatechange", b)};
    b(function(){
      var a = document.getElementById('cf-content');a.style.display = 'block';
      setTimeout(function(){
        var t,r,a,f, sFFWaQb={"nvbotxImQWzc":+((!+[]+!![]+!![]+!![]+[])+(!+[]+!![]+!![]+!![]+!![]+!![]))};
        t = document.createElement('div');
        t.innerHTML="<a href='/'>x</a>";
        t = t.firstChild.href;r = t.match(/https?:\/\//)[0];
        t = t.substr(r.length); t = t.substr(0,t.length-1);
        a = document.getElementById('jschl-answer');
        f = document.getElementById('challenge-form');
        ;sFFWaQb.nvbotxImQWzc+=+((!+[]+!![]+!![]+[])+(!+[]+!![]+!![]));sFFWaQb.nvbotxImQWzc+=!+[]+!![]+!![]+!![]+!![]+!![]+!![];sFFWaQb.nvbotxImQWzc+=+((!+[]+!![]+[])+(!+[]+!![]+!![]+!![]));sFFWaQb.nvbotxImQWzc*=+((+!![]+[])+(!+[]+!![]+!![]+!![]+!![]+!![]+!![]));sFFWaQb.nvbotxImQWzc*=+((!+[]+!![]+!![]+!![]+[])+(!+[]+!![]+!![]+!![]+!![]+!![]));sFFWaQb.nvbotxImQWzc-=+((+!![]+[])+(!+[]+!![]+!![]+!![]+!![]));sFFWaQb.nvbotxImQWzc+=+((!+[]+!![]+!![]+[])+(+!![]));sFFWaQb.nvbotxImQWzc*=+((!+[]+!![]+!![]+[])+(!+[]+!![]));a.value = parseInt(sFFWaQb.nvbotxImQWzc, 10) + t.length;
        f.submit();
      }, 5850);
    }, false);
  })();
  //]]> </script>
   *
   * http://0daymp3.com/cdn-cgi/l/chk_jschl?jschl_vc=1e6bbb2fc7b3371a3e12408718d0e13d&jschl_answer=2753163
	 * @param script
	 * @return
	 * @throws Exception
	 */

	public long calcolateAnswer(String script) throws Exception {

		long tot = 0;
//		log.info(script);
		String[] lines = script.split("\n");
		String varName = "";
		for(String scriptLine : lines) {

			if(scriptLine.contains("var t,r,a,f,")) {
				varName = scriptLine.replace("var t,r,a,f,", "").replaceAll("[{};\"]", "");

				String[] tmp = varName.split(":");
				varName = tmp[0].replace("=", ".").trim();
//				System.out.println(varName);
				tot = parseCalc(tot, "+", tmp[1]);

			}
			else if(scriptLine.contains(varName) && varName.length()>0) {

				String[] tmp = scriptLine.split(";");
				for(String x : tmp) {
					if(!x.contains("parseInt") && x.trim().length()>0) {
//						log.info(x);
						x = x.replace(varName, "");
						String operatore = x.split("=")[0];
						tot = parseCalc(tot, operatore, x.split("=")[1]);
					}
				}

			}


		}

//		tot += 11;
		tot += 9;

//		log.info("ANTI_DDOS TOT = "+tot);
		return tot;
	}

	/**
	 * It's simple, just look at site html source:
	  https://github.com/Karmel0x/cloudflare-js-bypass/issues/1

	    - Get input values from html
	    - Get var values from js
		    a) get first var value cause its different than next ones
		    b) combine them
	    - Calculate them LIKE JAVASCRIPT WOULD DO IT
	    	a) count them cause in JS (1+1+[])+(1+0+[]) is 21 not 3
	    	= replace some characters to single character so we can easly do it
	    	b) calculate single brackets /+ + and vars *= -= += /=
	    - Cut the float value to 10 digits
	    - Add domain name lenght
	    - Send values as GET request

	(function(){
	    var a = function() {try{return !!window.addEventListener} catch(e) {return !1} },
	    b = function(b, c) {a() ? document.addEventListener("DOMContentLoaded", b, c) : document.attachEvent("onreadystatechange", b)};
	    b(function(){
	      var a = document.getElementById('cf-content');a.style.display = 'block';
	      setTimeout(function(){
	        var s,t,o,p,b,r,e,a,k,i,n,g,f, WbhPikH={"OnWEUOcfRJhX":+((!+[]+!![]+!![]+!![]+!![]+!![]+!![]+!![]+[])+(!+[]+!![]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![]+!![]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![]+!![]+!![]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![]+!![]+!![]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![]+!![])+(+[])+(!+[]+!![]+!![]))/+((!+[]+!![]+!![]+[])+(!+[]+!![]+!![]+!![]+!![]+!![])+(+!![])+(!+[]+!![]+!![]+!![]+!![]+!![]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![]+!![]))};
	        t = document.createElement('div');
	        t.innerHTML="<a href='/'>x</a>";
	        t = t.firstChild.href;r = t.match(/https?:\/\//)[0];
	        t = t.substr(r.length); t = t.substr(0,t.length-1);
	        a = document.getElementById('jschl-answer');
	        f = document.getElementById('challenge-form');
	        ;WbhPikH.OnWEUOcfRJhX*=+((!+[]+!![]+!![]+!![]+!![]+!![]+!![]+!![]+!![]+[])+(!+[]+!![])+(+[])+(!+[]+!![]+!![]+!![])+(+!![])+(!+[]+!![]+!![]+!![]+!![]+!![]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![]+!![]+!![]+!![]+!![])+(!+[]+!![])+(!+[]+!![]+!![]))/+((!+[]+!![]+!![]+!![]+!![]+[])+(!+[]+!![])+(!+[]+!![]+!![]+!![]+!![]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![]+!![]+!![]));a.value = +WbhPikH.OnWEUOcfRJhX.toFixed(10) + t.length; '; 121'
	        f.action += location.hash;
	        f.submit();
	      }, 4000);
	    }, false);
	  })();

	  	jschl_answer	13.1313520913
		jschl_vc	5a1ce97f506802930e21af7206e63699
		pass	1534884903.809-ElcKZ8sIAH

	 * @param script
	 * @return
	 */

	public double calcolateAnswer2018(String script) {

		BigDecimal tot = new BigDecimal(0);
//		log.info(script);
		String[] lines = script.split("\n");
		String varName = "";
		for(String scriptLine : lines) {

			if(scriptLine.contains("var s,t,o,p,b,r,e,a,k,i,n,g,f,")) {
				varName = scriptLine.replace("var s,t,o,p,b,r,e,a,k,i,n,g,f,", "").replaceAll("[{};\"]", "");

				String[] tmp = varName.split(":");
				varName = tmp[0].replace("=", ".").trim();
//				System.out.println(varName);
				tot = parseCalc2018(tot, "+", tmp[1]);

			}
			else if(scriptLine.contains(varName) && varName.length()>0) {
				String[] tmp = scriptLine.split(";");
				for(String x : tmp) {
					if(!x.contains("toFixed") && x.trim().length()>0) {
//						log.info(x);
						x = x.replace(varName, "");
						String operatore = x.split("=")[0];
						tot = parseCalc2018(tot, operatore, x.split("=")[1]);
					} else if(x.contains("toFixed")) {
						break;
					}
				}

			}


		}

		return tot.setScale(10,BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	private final static Pattern OPERATION_PATTERN = Pattern.compile("setTimeout\\(function\\(\\)\\{\\s+(var t,r,a,f.+?\\r?\\n[\\s\\S]+?a\\.value =.+?)\\r?\\n");
	private final static Pattern OPERATION_PATTERN_2 = Pattern.compile("setTimeout\\(function\\(\\)\\{\\s+(var s,t,o,p,b,r,e,a,k,i,n,g,f.+?\\r?\\n[\\s\\S]+?a\\.value =.+?)\\r?\\n");
    private final static Pattern PASS_PATTERN = Pattern.compile("name=\"pass\" value=\"(.+?)\"");
    private final static Pattern CHALLENGE_PATTERN = Pattern.compile("name=\"jschl_vc\" value=\"(\\w+)\"");

    public String cloudFlareSolve(String responseString, String baseUrl, String userAgent) throws InterruptedException, IOException {
        Context rhino = Context.enter();
        try {

        	String domain = baseUrl.replace("http://", "").replace("https://", "");
    		if(domain.endsWith("/"))
    			domain = domain.substring(0,domain.indexOf("/"));
//    		System.out.println(domain);

            Thread.sleep(5000);

            //System.out.println(responseString);
            Matcher operationSearch = OPERATION_PATTERN.matcher(responseString);
            String manualOperationSearch = this.manualMatcher(responseString);
            Matcher challengeSearch = CHALLENGE_PATTERN.matcher(responseString);
            Matcher passSearch = PASS_PATTERN.matcher(responseString);
            if(!(operationSearch.find() || manualOperationSearch!=null) || !passSearch.find() || !challengeSearch.find())
                return null;

            String rawOperation = manualOperationSearch!=null ? manualOperationSearch : operationSearch.group(1);
            String challengePass = passSearch.group(1);
            String challenge = challengeSearch.group(1);


            String operation = rawOperation
                    .replaceAll("a\\.value =(.+?) \\+ .+?;", "$1")
                    .replaceAll("\\s{3,}[a-z](?: = |\\.).+", "");
            String js = operation.replace("\n", "");

            rhino.setOptimizationLevel(-1);
            Scriptable scope = rhino.initStandardObjects();

            // either do or die trying
            int result = ((Double) rhino.evaluateString(scope, js, "CloudFlare JS Challenge", 1, null)).intValue();
            String answer = String.valueOf(result + domain.length());

            List<NameValuePair> params = new ArrayList<NameValuePair>(3);
            params.add(new BasicNameValuePair("jschl_vc", challenge));
            params.add(new BasicNameValuePair("pass", challengePass));
            params.add(new BasicNameValuePair("jschl_answer", answer));

            String url = baseUrl.replace("https://", "http://") + "/cdn-cgi/l/chk_jschl?" + URLEncodedUtils.format(params, "windows-1251");
//            log.info(url);
//            Document doc = Jsoup.connect(url).timeout(6000).header("Referer", urlToRedirect)
//        			.userAgent(userAgent)
//        			.ignoreHttpErrors(true).get();

            return url;
        } finally {
            Context.exit();
        }
    }

    public String cloudFlareSolve2018(String responseString, String baseUrl, String userAgent) throws InterruptedException, IOException {
        try {

        	String domain = baseUrl.replace("http://", "").replace("https://", "");
    		if(domain.endsWith("/"))
    			domain = domain.substring(0,domain.indexOf("/"));

            Thread.sleep(5000);

            String operationSearch = this.manualMatcher(responseString);
            Matcher challengeSearch = CHALLENGE_PATTERN.matcher(responseString);
            Matcher passSearch = PASS_PATTERN.matcher(responseString);
            if( !passSearch.find() || !challengeSearch.find())
                return null;

            String challengePass = passSearch.group(1);
            String challenge = challengeSearch.group(1);

            double result = this.calcolateAnswer2018(operationSearch);

//            BigDecimal resToFixed = new BigDecimal(result).setScale(2,BigDecimal.ROUND_HALF_UP);
//            System.out.println("resToFixed: "+resToFixed.floatValue());

            // either do or die trying
            String answer = String.valueOf(result + domain.length());
//            System.out.println("answer: "+answer);

            List<NameValuePair> params = new ArrayList<NameValuePair>(3);
            params.add(new BasicNameValuePair("jschl_vc", challenge));
            params.add(new BasicNameValuePair("pass", challengePass));
            params.add(new BasicNameValuePair("jschl_answer", answer));

            String url = baseUrl.replace("https://", "http://") + "/cdn-cgi/l/chk_jschl?" + URLEncodedUtils.format(params, "windows-1251");
//            log.info(url);
//            Document doc = Jsoup.connect(url).timeout(6000).header("Referer", urlToRedirect)
//        			.userAgent(userAgent)
//        			.ignoreHttpErrors(true).get();

            return url;
        } finally {

        }
    }

	private String manualMatcher(String responseString) {
		int initPoint = responseString.lastIndexOf("setTimeout(function(){");
		int endPoint = responseString.lastIndexOf("a.value");
		if(initPoint>0 && endPoint>0) {
			while(responseString.charAt(endPoint)!='\n') {
				endPoint++;
			}
			String subStr = responseString.substring(initPoint+23, endPoint-7);
			return subStr;
		}
		return null;
	}

	private long parseCalc(long tot, String operatore, String value) {

		String totale = "";
		String[] tmp = value.split("\\)\\+\\(");
		for(String x : tmp) {
			int res = StringUtils.countMatches(x, "!!") + StringUtils.countMatches(x, "!+");
//			System.out.println(res+" --> "+x);
			totale += res+"";
		}
//		log.info(totale+" --> "+value);
//		System.out.println(totale+" --> "+value);
		long totInt = Long.parseLong(totale);
		if(operatore.equals("+"))
			return tot+totInt;
		if(operatore.equals("-"))
			return tot-totInt;
		if(operatore.equals("*"))
			return tot*totInt;
		if(operatore.equals("/"))
			return tot/totInt;

		return tot;
	}

	private BigDecimal parseCalc2018(BigDecimal tot, String operatore, String value) {

		// ( (cifra_x1)+(cifra_x2)+...(cifra_xn)  ) / ( (cifra_y1)+(cifra_y2)+...(cifra_yn) )
		String[] tmp1 = value.split("\\/\\+");

		long subResult[] = new long[tmp1.length];
		for(int i=0;i<tmp1.length;i++) {
			String totale = "";
//			System.out.println(tmp1[i]);
			String[] tmp = tmp1[i].split("\\)\\+\\(");
			for(String x : tmp) {
				int res = StringUtils.countMatches(x, "!!") + StringUtils.countMatches(x, "!+");
				totale += res+"";
			}
			subResult[i] = Long.parseLong(totale);
		}

		// dovrebbero essere sempre due i risultati da dividere
		BigDecimal totInt = new BigDecimal(0);
		if(subResult.length==1) {
			totInt = new BigDecimal(subResult[0]);
		} else if(subResult.length>1) {
			totInt = new BigDecimal(subResult[0]);
			for(int i=1;i<subResult.length;i++) {
				totInt = totInt.divide(new BigDecimal(subResult[i]),10,BigDecimal.ROUND_HALF_UP);
			}
		}
//		System.out.println("result = "+totInt);


//		log.info(totale+" --> "+value);
//		System.out.println(totale+" --> "+value);
		if(operatore.equals("+"))
			return tot.add(totInt);
		if(operatore.equals("-"))
			return tot.subtract(totInt);
		if(operatore.equals("*"))
			return tot.multiply(totInt);
		if(operatore.equals("/"))
			return tot.divide(totInt,10,BigDecimal.ROUND_HALF_UP);

		return tot;
	}
	public boolean isAntiDDOS(Document doc) {
		Elements res = doc.getElementsByClass("cf-browser-verification");
		if(res.size()!=0)
			log.info("DDOS protection: true");
		return res.size()==0 ? false : true;
	}

	public static void main(String[] args) throws Exception {
		String script = "var a = function() {try{return !!window.addEventListener} catch(e) {return !1} },\n"+
    "b = function(b, c) {a() ? document.addEventListener(\"DOMContentLoaded\", b, c) : document.attachEvent(\"onreadystatechange\", b)};\n"+
    "b(function(){\n"+
      "var a = document.getElementById('cf-content');a.style.display = 'block';\n"+
      "setTimeout(function(){\n"+
        "var t,r,a,f, ZrxcFYU={\"bODbAk\":+((+!![]+[])+(!+[]+!![]+!![]+!![]+!![]+!![]))};\n"+
        "t = document.createElement('div');\n"+
        "t.innerHTML=\"<a href='/'>x</a>\";\n"+
        "t = t.firstChild.href;r = t.match(/https?:\\/\\//)[0];\n"+
        "t = t.substr(r.length); t = t.substr(0,t.length-1);\n"+
        "a = document.getElementById('jschl-answer');\n"+
        "f = document.getElementById('challenge-form');\n"+
        ";ZrxcFYU.bODbAk+=+((+!![]+[])+(+[]));a.value = parseInt(ZrxcFYU.bODbAk, 10) + t.length;\n"+
        "f.submit();\n"+
      "}, 8000);\n"+
    "}, false);\n";
	// GET http://scnlog.eu/cdn-cgi/l/chk_jschl?jschl_vc=3aa3bf2aae0861f7f08447a9a6649b58&pass=1429731087.261-ugSb%2FE6DdZ&jschl_answer=35

		System.out.println(new AntiDdosUtility().calcolateAnswer(script));

		script = "b(function(){\n"+
      "var a = document.getElementById('cf-content');a.style.display = 'block';\n"+
      "setTimeout(function(){\n"+
        "var s,t,o,p,b,r,e,a,k,i,n,g,f, WbhPikH={\"OnWEUOcfRJhX\":+((!+[]+!![]+!![]+!![]+!![]+!![]+!![]+!![]+[])+(!+[]+!![]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![]+!![]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![]+!![]+!![]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![]+!![]+!![]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![]+!![])+(+[])+(!+[]+!![]+!![]))/+((!+[]+!![]+!![]+[])+(!+[]+!![]+!![]+!![]+!![]+!![])+(+!![])+(!+[]+!![]+!![]+!![]+!![]+!![]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![]+!![]))};\n"+
        "t = document.createElement('div');\n"+
        "t.innerHTML=\"<a href='/'>x</a>\";\n"+
        "t = t.firstChild.href;r = t.match(/https?:\\/\\//)[0];\n"+
        "t = t.substr(r.length); t = t.substr(0,t.length-1);\n"+
        "a = document.getElementById('jschl-answer');\n"+
        "f = document.getElementById('challenge-form');\n"+
        ";WbhPikH.OnWEUOcfRJhX*=+((!+[]+!![]+!![]+!![]+!![]+!![]+!![]+!![]+!![]+[])+(!+[]+!![])+(+[])+(!+[]+!![]+!![]+!![])+(+!![])+(!+[]+!![]+!![]+!![]+!![]+!![]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![]+!![]+!![]+!![]+!![])+(!+[]+!![])+(!+[]+!![]+!![]))/+((!+[]+!![]+!![]+!![]+!![]+[])+(!+[]+!![])+(!+[]+!![]+!![]+!![]+!![]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![]+!![]+!![]+!![])+(!+[]+!![]+!![]+!![]+!![]+!![]));a.value = +WbhPikH.OnWEUOcfRJhX.toFixed(10) + t.length; '; 121'\n"+
        "f.action += location.hash;\n"+
        "f.submit();\n"+
      "}, 4000);\n"+
    "}, false);";
		System.out.println(new AntiDdosUtility().calcolateAnswer2018(script));

		//System.out.println(new AntiDdosUtility().manualMatcher(script));
	}

}
