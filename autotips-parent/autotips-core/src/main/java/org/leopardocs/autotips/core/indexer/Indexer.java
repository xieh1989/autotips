package org.leopardocs.autotips.core.indexer;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.leopardocs.autotips.core.analyzer.StandardAnalyzerSelector;
import org.leopardocs.autotips.core.config.Autotip;

public class Indexer {
	private static final Log logger = LogFactory.getLog(Indexer.class);

	public static void initIndex(IndexWorker worker, Autotip Autotip)
			throws Exception {
		initIndex(worker, Autotip, false);
	}

	private static long printMemoryInfo() {
		int mb = 1048576;
		Runtime runtime = Runtime.getRuntime();
		logger.info(" AutoTips check memory info start...");
		logger.info(" Heap utilization statistics [MB] ");
		logger.info(" Used Memory:"
				+ (runtime.totalMemory() - runtime.freeMemory()) / mb);

		long free = runtime.freeMemory() / mb;
		logger.info(" Free Memory:" + free);
		logger.info(" Total Memory:" + runtime.totalMemory() / mb);
		logger.info(" Max Memory:" + runtime.maxMemory() / mb);
		logger.info(" AutoTips check memory info end...");
		return free;
	}

	public static void initIndex(IndexWorker worker, Autotip Autotip,
			boolean forceReIndex) throws Exception {
		if (Autotip == null) {
			throw new IllegalArgumentException(
					"Argument autoTips should not null.");
		}

		Date start = new Date();
		try {
			logger.info("Process " + Autotip.getId() + " start in path: "
					+ Autotip.getIndexPath());
			Directory dir = FSDirectory.open(new File(Autotip.getIndexPath()));
			Analyzer analyzer = StandardAnalyzerSelector.getAnalyzer(Autotip);

			long free = printMemoryInfo();
			IndexWriter writer = new IndexWriter(dir, analyzer,
					IndexWriter.MaxFieldLength.UNLIMITED);

			if (free >= 1024L)
				writer.setRAMBufferSizeMB(256.0D);
			else {
				writer.setRAMBufferSizeMB(128.0D);
			}
			worker.doIndex(writer, Autotip);
			writer.optimize();
			writer.close();
			Date end = new Date();
			logger.info("Process " + Autotip.getId() + " end, cost = "
					+ (end.getTime() - start.getTime()) + " total milliseconds");
		} catch (IOException e) {
			logger.error(
					"Process " + Autotip.getId() + " caught a " + e.getClass()
							+ "\n with message: " + e.getMessage(), e);

			throw e;
		}
	}
}