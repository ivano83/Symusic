package it.fivano.symusic.conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class SymusicConf {

	public String MAX_ACTIVE_THREAD;
	public List<String> RELEASE_EXCLUSION;
	public List<String> RELEASE_VA;

	public SymusicConf() throws IOException {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("symusic.properties");
		Properties props = new Properties();
		props.load(in);
		
		MAX_ACTIVE_THREAD = props.getProperty("maxActiveThread");
		RELEASE_EXCLUSION = Arrays.asList(props.getProperty("releaseExclusion").split(","));
		RELEASE_VA = Arrays.asList(props.getProperty("releaseVa").split(","));

	}
	
	public Properties loadProperties(String siteName) throws IOException {
		InputStream in = null;
		try {
			in = this.getClass().getClassLoader().getResourceAsStream(siteName.toLowerCase()+".properties");
			Properties props = new Properties();
			props.load(in);
			
			return props;
		
		} finally {
			try { if(in!=null) in.close(); } catch (Exception e) { }
		}
	}
	
}
