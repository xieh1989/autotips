package org.leopardocs.autotips.core.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.util.Version;
import org.leopardocs.autotips.core.analyzer.impl.StandardAnalyzer33;
import org.leopardocs.autotips.core.analyzer.impl.StandardAnalyzer34;
import org.leopardocs.autotips.core.analyzer.impl.StandardAnalyzer35;
import org.leopardocs.autotips.core.analyzer.impl.StandardAnalyzer36;
import org.leopardocs.autotips.core.config.Autotip;

public class StandardAnalyzerSelector {

	@SuppressWarnings("deprecation")
	public static Analyzer getAnalyzer(Autotip Autotip) {
		if (Version.LUCENE_CURRENT == Version.LUCENE_33) {
			return new StandardAnalyzer33(Autotip);
		} else if (Version.LUCENE_CURRENT == Version.LUCENE_34) {
			return new StandardAnalyzer34(Autotip);
		} else if (Version.LUCENE_CURRENT == Version.LUCENE_35) {
			return new StandardAnalyzer35(Autotip);
		} else if (Version.LUCENE_CURRENT == Version.LUCENE_36) {
			return new StandardAnalyzer36(Autotip);
		}
		return null;
	}
}
