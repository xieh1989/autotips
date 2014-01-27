package org.leopardocs.autotips.web;

import static org.leopardocs.autotips.web.AutotipsWebCST.PARAM_CONFIG_DB;
import static org.leopardocs.autotips.web.AutotipsWebCST.PARAM_CONFIG_DEFAULT;
import static org.leopardocs.autotips.web.AutotipsWebCST.PARAM_INDEXER_CHECKONSTART;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.leopardocs.autotips.core.config.Autotip;
import org.leopardocs.autotips.core.config.Autotips;
import org.leopardocs.autotips.core.indexer.CommonIndexer;
import org.leopardocs.autotips.core.mode.ModeConfig;
import org.leopardocs.autotips.core.tools.AutoTipsTools;

public class AutoTipsStartupListener implements ServletContextListener {
	private final Log logger = LogFactory.getLog(getClass());

	private ServletContext servletContext;

	public void contextInitialized(ServletContextEvent sce) {
		this.logger.info("AutoTips Start Listener start process...");
		try {
			this.logger.info("AutoTips config in file " + ModeConfig.getDefault().getConfigFile() + " .");

			this.servletContext = sce.getServletContext();

			String defaultConfig = this.servletContext.getInitParameter(PARAM_CONFIG_DEFAULT);

			if ((defaultConfig != null) && (defaultConfig.trim().length() >= 0)) {
				ModeConfig.getDefault().setConfigFile(defaultConfig);
			}

			String dbConfig = this.servletContext.getInitParameter(PARAM_CONFIG_DB);

			if ((dbConfig != null) && (dbConfig.trim().length() >= 0)) {
				ModeConfig.getDBMODE().setConfigFile(dbConfig);
			}

			if (this.logger.isDebugEnabled()) {
				this.logger.debug("AutoTip Default Config " + ModeConfig.getDefault().getConfigFile());
				this.logger.debug("AutoTip DB Config " + ModeConfig.getDBMODE().getConfigFile());
			}

			Autotips autoTipsConfig = WebConfigurator.parseConfig(this.servletContext);
			if (autoTipsConfig == null) {
				throw new IllegalArgumentException("AutoTips should not null.");
			}
			AutoTipsServlet.setAutoTipsConfig(autoTipsConfig);
			processCheckOnStart(autoTipsConfig);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e);
			throw new RuntimeException(e);
		} finally {
			this.logger.info("AutoTips Start Listener start process end.");
		}
	}

	private void processCheckOnStart(Autotips autoTips) {
		String checkOnStart = this.servletContext.getInitParameter(PARAM_INDEXER_CHECKONSTART);

		if (checkOnStart == null || checkOnStart.trim().equals("")) {
			checkOnStart = "true";
		}

		if (Boolean.valueOf(checkOnStart).booleanValue()) {
			this.logger.info("AutoTips check start...");
			List<Autotip> autotips = autoTips.getAutotip();
			for (Autotip autotip : autotips) {
				if (this.logger.isDebugEnabled()) {
					this.logger.debug("AutoTip " + autotip.getId() + "'s index path is " + autotip.getIndexPath());
				}

				File file = new File(autotip.getIndexPath());
				if (!file.exists()) {
					this.logger.info("AutoTip " + autotip.getId() + "'s index path " + autotip.getIndexPath()
							+ " not exists, create it...");

					file.mkdirs();
					this.logger.info("AutoTip " + autotip.getId() + "'s index path " + autotip.getIndexPath()
							+ " create end.");
				}

				if (AutoTipsTools.sizeOf(file) == 0L) {
					this.logger.info("AutoTip " + autotip.getId() + "'s index path " + autotip.getIndexPath()
							+ " is empty, init it...");
					try {
						CommonIndexer.process(autotip);
					} catch (Exception e) {
						e.printStackTrace();
						this.logger.error(e);
						throw new RuntimeException(e);
					} finally {
					}
					this.logger.info("AutoTip " + autotip.getId() + "'s index path " + autotip.getIndexPath()
							+ " init end.");
				} else {
					this.logger.info("AutoTip " + autotip.getId() + "'s index path have indexer, donot need to init.");
				}
			}

			this.logger.info("AutoTips check end.");
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}
}