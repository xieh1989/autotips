package org.leopardocs.autotips.core.analyzer.impl;

import java.io.IOException;
import java.io.Reader;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.Version;
import org.leopardocs.autotips.core.config.Autotip;
import org.leopardocs.autotips.core.config.FieldItem;
import org.leopardocs.autotips.core.tools.AutoTipsTools;

public class StandardAnalyzer36 extends Analyzer {
	private Set stopSet;
	private Autotip se = null;

	private boolean replaceInvalidAcronym = defaultReplaceInvalidAcronym;
	private static boolean defaultReplaceInvalidAcronym;
	private boolean enableStopPositionIncrements;
	private boolean useDefaultStopPositionIncrements;

	public static final Set<?> STOP_WORDS = StopAnalyzer.ENGLISH_STOP_WORDS_SET;

	public static final Set STOP_WORDS_SET = StopAnalyzer.ENGLISH_STOP_WORDS_SET;
	public static final int DEFAULT_MAX_TOKEN_LENGTH = 255;
	private int maxTokenLength = 255;

	/** @deprecated */
	public static boolean getDefaultReplaceInvalidAcronym() {
		return defaultReplaceInvalidAcronym;
	}

	/** @deprecated */
	public static void setDefaultReplaceInvalidAcronym(
			boolean replaceInvalidAcronym) {
		defaultReplaceInvalidAcronym = replaceInvalidAcronym;
	}

	public StandardAnalyzer36(Autotip Autotip) {
		this(Version.LUCENE_36, STOP_WORDS_SET, Autotip);
	}

	public StandardAnalyzer36(Version matchVersion, Set stopWords,
			Autotip Autotip) {
		this.se = Autotip;
		this.stopSet = stopWords;
		init(matchVersion);
	}

	private final void init(Version matchVersion) {
		if (matchVersion.onOrAfter(Version.LUCENE_29))
			this.enableStopPositionIncrements = true;
		else {
			this.useDefaultStopPositionIncrements = true;
		}
		if (matchVersion.onOrAfter(Version.LUCENE_24))
			this.replaceInvalidAcronym = defaultReplaceInvalidAcronym;
		else
			this.replaceInvalidAcronym = false;
	}

	public TokenStream tokenStream(String fieldName, Reader reader) {
		StandardTokenizer tokenStream = new StandardTokenizer(
				Version.LUCENE_36, reader);
		tokenStream.setReplaceInvalidAcronym(this.replaceInvalidAcronym);
		tokenStream.setMaxTokenLength(this.maxTokenLength);
		TokenStream result = new StandardFilter(tokenStream);
		FieldItem seField = AutoTipsTools.getField(this.se, fieldName);

		if (!seField.isCasesensitive()) {
			result = new LowerCaseFilter(result);
		}

		if (this.useDefaultStopPositionIncrements)
			result = new StopFilter(true, result, this.stopSet);
		else {
			result = new StopFilter(this.enableStopPositionIncrements, result,
					this.stopSet);
		}

		return result;
	}

	public void setMaxTokenLength(int length) {
		this.maxTokenLength = length;
	}

	public int getMaxTokenLength() {
		return this.maxTokenLength;
	}

	/** @deprecated */
	public TokenStream reusableTokenStream(String fieldName, Reader reader)
			throws IOException {
		SavedStreams streams = (SavedStreams) getPreviousTokenStream();
		if (streams == null) {
			streams = new SavedStreams();
			setPreviousTokenStream(streams);
			streams.tokenStream = new StandardTokenizer(Version.LUCENE_36,
					reader);
			streams.filteredTokenStream = new StandardFilter(
					streams.tokenStream);
			FieldItem seField = AutoTipsTools.getField(this.se, fieldName);

			if (!seField.isCasesensitive()) {
				streams.filteredTokenStream = new LowerCaseFilter(
						streams.filteredTokenStream);
			}

			if (this.useDefaultStopPositionIncrements) {
				streams.filteredTokenStream = new StopFilter(true,
						streams.filteredTokenStream, this.stopSet);
			} else {
				streams.filteredTokenStream = new StopFilter(
						this.enableStopPositionIncrements,
						streams.filteredTokenStream, this.stopSet);
			}
		} else {
			streams.tokenStream.reset(reader);
		}
		streams.tokenStream.setMaxTokenLength(this.maxTokenLength);

		streams.tokenStream
				.setReplaceInvalidAcronym(this.replaceInvalidAcronym);

		return streams.filteredTokenStream;
	}

	/** @deprecated */
	public boolean isReplaceInvalidAcronym() {
		return this.replaceInvalidAcronym;
	}

	/** @deprecated */
	public void setReplaceInvalidAcronym(boolean replaceInvalidAcronym) {
		this.replaceInvalidAcronym = replaceInvalidAcronym;
	}

	static {
		String v = System
				.getProperty("org.apache.lucene.analysis.standard.StandardAnalyzer.replaceInvalidAcronym");

		if ((v == null) || (v.equals("true")))
			defaultReplaceInvalidAcronym = true;
		else
			defaultReplaceInvalidAcronym = false;
	}

	private static final class SavedStreams {
		StandardTokenizer tokenStream;
		TokenStream filteredTokenStream;
	}
}