package com.skillstorm.service;

public class UrlParserService {
	
	public static int getId(String url) {
		if (url == null || url == "") {
			return 0;
		}
		String[] splits = url.split("/");
		if (splits.length < 2) {
			return 0;
		}
		try {
			return Integer.parseInt(splits[1]);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	
	public static int[] getIds(String url) {
		if (url == null || url == "") {
			return null;
		}
		String[] splits = url.split("/");
		if (splits.length < 2) {
			return null;
		}
		try {
			int [] ids = new int[splits.length-1];
			for (int i=0; i<splits.length-1; i++) {
				ids[i] = Integer.parseInt(splits[i+1]);
			}
			return ids;
		} catch (NumberFormatException e) {
			return null;
		}
	}

}
