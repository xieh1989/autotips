package org.leopardocs.autotips.core.indexer;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.leopardocs.autotips.core.config.Autotip;
import org.leopardocs.autotips.core.config.Autotips;

public class CommonIndexer {
	private static final Log logger = LogFactory.getLog(CommonIndexer.class);

	public static void processAll(Autotips autoTips) throws Exception {
		if (autoTips != null) {
			logger.info("start process all index...");

			List<Autotip> autotips = autoTips.getAutotip();
			CommonIndexWorker worker = new CommonIndexWorker();
			for (Autotip autoTip : autotips) {
				Indexer.initIndex(worker, autoTip);
			}
			logger.info("end process all index.");
		}
	}

	public static void process(Autotip autoTip) throws Exception {
		if (autoTip != null) {
			logger.info("start process autotip " + autoTip.getId() + "...");
			CommonIndexWorker worker = new CommonIndexWorker();
			Indexer.initIndex(worker, autoTip);
			logger.info("end process autotip " + autoTip.getId() + ".");
		}
	}
}