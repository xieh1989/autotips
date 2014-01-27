package org.leopardocs.autotips.core;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class StandardResourcePathExtracter implements ResourcePathExtracter {
	private static final String PREFIX_FILE = "file:";
	private static final String PREFIX_CLASSPATH = "classpath:";

	public String getAbsPath(String path) {
		String forReturn = null;
		if ((path != null) && (path.trim().length() > 0)) {
			if (path.startsWith("file:")) {
				path = path.substring("file:".length());
				forReturn = path;
			} else if (path.startsWith("classpath:")) {
				path = path.substring("classpath:".length());
				try {
					forReturn = URLDecoder.decode(Configurator.class.getResource(path).getPath(), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} finally {
					forReturn = null;
				}
			}
		}
		return forReturn;
	}
}