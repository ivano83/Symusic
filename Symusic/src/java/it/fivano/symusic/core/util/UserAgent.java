package it.fivano.symusic.core.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class UserAgent {

	private static List<String> lista = new ArrayList<String>();
	private static Random random = new Random();
	
	static {
		lista.add("Mozilla/5.0 (Windows; U; MSIE 9.0; Windows NT 9.0; en-US)");
		lista.add("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0");
		lista.add("Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; GTB7.4; InfoPath.2; SV1; .NET CLR 3.3.69573; WOW64; en-US)");
		lista.add("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)");
		lista.add("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:24.0) Gecko/20100101 Firefox/24.0");
		lista.add("Opera/12.80 (Windows NT 5.1; U; en) Presto/2.10.289 Version/12.02");
		lista.add("Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; fr) Presto/2.9.168 Version/11.52");

	}

	public static String randomUserAgent() {

		return lista.get(random.nextInt(lista.size()-1));
	}
}
