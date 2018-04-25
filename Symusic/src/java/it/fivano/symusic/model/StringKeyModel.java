package it.fivano.symusic.model;

import java.util.Map;
import java.util.TreeMap;

public class StringKeyModel {

	private Map<String,Integer> stringKeys;

	public StringKeyModel() {
		stringKeys = new TreeMap<String,Integer>();
	}

	public void addStringKey(String key, Integer count) {
		stringKeys.put(key, count);
	}

	public boolean checkKey(String key) {
		return stringKeys.containsKey(key);
	}

	public int getKeyCount(String key) {
		if(checkKey(key))
			return stringKeys.get(key);
		return 0;
	}
}
