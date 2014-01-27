package org.leopardocs.autotips.core.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.leopardocs.autotips.core.ArgumentExtracter;
import org.leopardocs.autotips.core.AutoTipsParam;
import org.leopardocs.autotips.core.AutoTipsResult;
import org.leopardocs.autotips.core.analyzer.StandardAnalyzerSelector;
import org.leopardocs.autotips.core.config.Autotip;
import org.leopardocs.autotips.core.config.FieldItem;
import org.leopardocs.autotips.core.service.AutoTipsService;
import org.leopardocs.autotips.core.tools.AutoTipsTools;

public class AutoTipsSearchEngineImpl implements AutoTipsService {
	private static final Log logger = LogFactory
			.getLog(AutoTipsSearchEngineImpl.class);

	public AutoTipsResult processSearch(AutoTipsParam param) {
		AutoTipsTools.checkAutoTipsParam(param);

		AutoTipsResult result = new AutoTipsResult();
		result.setParam(param);

		Autotip autoTip = param.getAutoTip();

		Date start = new Date();

		ArgumentExtracter argumentExtracter = param.getArgumentExtracter();
		IndexSearcher searcher = null;
		try {
			try {
				searcher = new IndexSearcher(FSDirectory.open(new File(autoTip
						.getIndexPath())), true);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}

			Analyzer analyzer = StandardAnalyzerSelector.getAnalyzer(autoTip);

			QueryParser parser = new QueryParser(Version.LUCENE_29,
					autoTip.getDefaultFieldName(), analyzer);

			parser.setLowercaseExpandedTerms(false);

			StringBuilder queryString = new StringBuilder();
			List<FieldItem> fields = autoTip.getFields().getField();
			int fieldsSize = 0;
			String paramValue;
			if (fields != null) {
				fieldsSize = fields.size();
				paramValue = null;
				String defaultParamValue = "";
				String name = null;
				List queryStringItems = new ArrayList();

				for (FieldItem field : fields) {
					name = field.getName();

					if (argumentExtracter != null) {
						paramValue = (String) argumentExtracter.get(name,
								String.class);
						if ((paramValue != null)
								&& (paramValue.trim().length() > 0)) {
							if (!field.isCasesensitive()) {
								paramValue = paramValue.toLowerCase();
							}

							if (autoTip.getDefaultFieldName().equalsIgnoreCase(
									name))
								defaultParamValue = paramValue;
							else {
								queryStringItems.add(field.getName() + ":"
										+ paramValue.trim());
							}
						}
					}
				}

				int i = 0;
				int size = queryStringItems.size();
				for (int lastIndex = size - 1; i < size; i++) {
					if (i == lastIndex)
						queryString.append((String) queryStringItems.get(i)
								+ " ");
					else {
						queryString.append((String) queryStringItems.get(i)
								+ " AND ");
					}
				}

				if ((defaultParamValue != null)
						&& (defaultParamValue.trim().length() > 0)) {
					if (queryString.length() == 0)
						queryString.append(" " + defaultParamValue);
					else
						queryString.append(" AND " + defaultParamValue);
				}
			} else {
				return result;
			}

			if (logger.isDebugEnabled()) {
				logger.debug("queryString " + queryString);
			}

			if ((queryString == null) || (queryString.length() == 0)) {
				return result;
			}

			Query query = parser.parse(queryString.toString());

			TopDocs results = searcher.search(query,
					autoTip.getDefaultHitsPer());

			ScoreDoc[] hits = results.scoreDocs;
			int numTotalHits = results.totalHits;

			if (logger.isDebugEnabled()) {
				logger.debug(numTotalHits + " total matching documents");
			}

			int endIndex = Math.min(numTotalHits, autoTip.getDefaultHitsPer());

			String[][] values = new String[endIndex][fieldsSize];
			String[] columns = new String[fieldsSize];

			String fieldName = "";
			for (int i = 0; i < endIndex; i++) {
				Document doc = searcher.doc(hits[i].doc);
				for (int j = 0; j < fieldsSize; j++) {
					FieldItem fieldType = fields.get(j);
					fieldName = fieldType.getName();
					if (i == 0) {
						columns[j] = fieldName;
					}
					if (doc.getField(fieldName) != null)
						values[i][j] = doc.get(fieldName);
					else {
						values[i][j] = "";
					}
				}
			}
			Date end = new Date();
			long cost = end.getTime() - start.getTime();

			if (logger.isDebugEnabled()) {
				logger.debug("Time: " + cost + "ms");
			}

			result.setCost(cost);
			result.setValues(values);
			result.setColumns(columns);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new RuntimeException(e);
		} finally {
			if (searcher != null) {
				try {
					searcher.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}