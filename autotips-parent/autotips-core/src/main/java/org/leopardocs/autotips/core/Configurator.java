package org.leopardocs.autotips.core;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.leopardocs.autotips.core.config.Autotip;
import org.leopardocs.autotips.core.config.Autotips;
import org.leopardocs.autotips.core.tools.XmlBindingTools;

public class Configurator {
	private static Log logger = LogFactory.getLog(Configurator.class);
	protected static Map<String, Autotips> AUTOTIPS_MAP = Collections
			.synchronizedMap(new HashMap());

	public static Autotips parseConfig(String configAbsPath)
			throws FileNotFoundException, UnsupportedEncodingException,
			JAXBException {
		if ((configAbsPath == null) || (configAbsPath.trim().length() == 0)) {
			throw new RuntimeException(
					"Argument configAbsPath must have value.");
		}
		FileReader reader = null;
		Autotips autoTips = (Autotips) AUTOTIPS_MAP.get(configAbsPath);
		if (autoTips == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("unfound config file " + configAbsPath
						+ " in cache, parse it.");
			}
			reader = new FileReader(configAbsPath);
			autoTips = (Autotips) XmlBindingTools.parseXML(reader,
					Autotips.class);
			AUTOTIPS_MAP.put(configAbsPath, autoTips);
		} else if (logger.isDebugEnabled()) {
			logger.debug("read config file " + configAbsPath + " from cache .");
		}

		return autoTips;
	}

	public static Autotip getAutotip(Autotips autoTips, String id) {
		if (autoTips == null) {
			throw new IllegalArgumentException(
					"Argument autoTips should not null.");
		}

		if ((id == null) || (id.trim().length() <= 0)) {
			throw new IllegalArgumentException("Argument id should not null.");
		}
		List<Autotip> autotips = autoTips.getAutotip();
		Autotip target = null;
		for (Autotip item : autotips) {
			if (logger.isDebugEnabled()) {
				logger.debug("Find " + item.getId() + " config.");
			}
			if ((item != null) && (item.getId().equals(id))) {
				target = item;
				break;
			}
		}

		return target;
	}
}