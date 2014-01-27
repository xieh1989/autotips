package org.leopardocs.autotips.web;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.leopardocs.autotips.core.Configurator;
import org.leopardocs.autotips.core.ResourcePathExtracter;
import org.leopardocs.autotips.core.config.Autotip;
import org.leopardocs.autotips.core.config.Autotips;
import org.leopardocs.autotips.core.mode.ModeConfig;

public class WebConfigurator extends Configurator {
	private static Log logger = LogFactory.getLog(WebConfigurator.class);

	public static Autotips parseConfig() throws FileNotFoundException,
			UnsupportedEncodingException, JAXBException {
		return parseConfig(ModeConfig.getDefault());
	}

	public static Autotips parseConfig(ServletContext servletContext,
			ModeConfig modeConfig) throws FileNotFoundException,
			UnsupportedEncodingException, JAXBException {
		Autotips autoTips = null;
		if (modeConfig == null) {
			throw new IllegalArgumentException(
					"Argument modeConfig must have value.");
		}

		ResourcePathExtracter resourcePathExtracter = new WebResourcePathExtracter(
				servletContext);

		autoTips = parseConfig(resourcePathExtracter.getAbsPath(modeConfig
				.getConfigFile()));
		setPreDefineProperties(servletContext, autoTips);
		return autoTips;
	}

	public static Autotips parseConfig(ModeConfig modeConfig)
			throws FileNotFoundException, UnsupportedEncodingException,
			JAXBException {
		return parseConfig(null, modeConfig);
	}

	public static Autotips parseConfig(ServletContext servletContext)
			throws FileNotFoundException, UnsupportedEncodingException,
			JAXBException {
		return parseConfig(servletContext, ModeConfig.getDefault());
	}

	private static void setPreDefineProperties(ServletContext servletContext,
			Autotips autotips) {
		if (autotips != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("begin translate index path to absolute path start...");
			}
			List<Autotip> autotipList = autotips.getAutotip();
			ResourcePathExtracter resourcePathExtracter = new WebResourcePathExtracter(
					servletContext);

			for (Autotip autotip : autotipList) {
				if (logger.isDebugEnabled()) {
					logger.debug("Autotip" + autotip.getId()
							+ "'s index path config is "
							+ autotip.getIndexPath());
				}

				autotip.setIndexPath(resourcePathExtracter.getAbsPath(autotip
						.getIndexPath()));

				if (logger.isDebugEnabled()) {
					logger.debug("Autotip" + autotip.getId()
							+ "'s index path after translate is "
							+ autotip.getIndexPath());
				}

			}

			if (logger.isDebugEnabled())
				logger.debug("begin translate index path to absolute path end.");
		}
	}
}