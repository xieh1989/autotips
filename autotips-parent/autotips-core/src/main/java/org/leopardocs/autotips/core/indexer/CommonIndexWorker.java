package org.leopardocs.autotips.core.indexer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.leopardocs.autotips.core.config.Autotip;
import org.leopardocs.autotips.core.config.FieldItem;
import org.leopardocs.autotips.core.config.Source;
import org.leopardocs.autotips.core.config.SourceType;
import org.leopardocs.autotips.core.tools.AutoTipsTools;

public class CommonIndexWorker implements IndexWorker {
	private static final Log logger = LogFactory
			.getLog(CommonIndexWorker.class);

	private static int MAX_PER_PROCESS = 1000;

	public void doIndex(IndexWriter writer, Autotip Autotip) throws Exception {
		writer.deleteAll();
		List<Source> sources = Autotip.getSources().getSource();

		for (Source source : sources)
			if (SourceType.DB.equals(source.getType()))
				processDBSource(writer, Autotip, source);
	}

	public void processDBSource(IndexWriter writer, Autotip autoTip,
			Source source) {
		Connection conn = null;
		try {
			conn = AutoTipsTools.getSourceTypeConnection(source);

			String oSQL = source.getSql();

			String countSQL = AutoTipsTools.getCountSQL(oSQL);

			PreparedStatement ps = conn.prepareStatement(countSQL);
			ResultSet result = ps.executeQuery();

			int count = 0;
			if (result.next()) {
				count = result.getInt(1);
				if (result != null) {
					try {
						result.close();
					} catch (Exception e) {
						e.printStackTrace();
						logger.error(e);
						throw new RuntimeException(e);
					}
				}
				logger.info("total count is " + count + ".");
				try {
					ps.close();
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e);
					throw new RuntimeException(e);
				}

				if (count > MAX_PER_PROCESS) {
					int totalPage = AutoTipsTools.getTotalPage(count,
							MAX_PER_PROCESS);
					logger.info("total page is " + totalPage + ".");
					FieldItem t = AutoTipsTools.getDefaultField(autoTip);
					if (t != null) {
						ps = conn.prepareStatement(
								AutoTipsTools.getPagingSQL(oSQL,
										t.getRefField()), 1003, 1007);
						ps.setMaxRows(MAX_PER_PROCESS);
						int startIndex = 1;
						int endIndex = 1;

						Date start = new Date();
						Date end = null;
						for (int pageNo = 1; pageNo <= totalPage; pageNo++) {
							startIndex = (pageNo - 1) * MAX_PER_PROCESS + 1;
							endIndex = pageNo * MAX_PER_PROCESS;
							ps.setInt(1, endIndex);
							ps.setInt(2, startIndex);

							logger.debug("start process from " + startIndex
									+ " to " + endIndex + "...");
							result = ps.executeQuery();
							processDBSourceCurrentPage(result, writer, autoTip);
							if (result != null) {
								result.close();
							}
							end = new Date();
							long cost = end.getTime() - start.getTime();
							logger.debug("end process from " + startIndex
									+ " to " + endIndex + ", and cost " + cost
									+ "ms.");
							start = end;
						}
					}
				} else if (count > 0) {
					ps = conn.prepareStatement(oSQL);
					result = ps.executeQuery();
					processDBSourceCurrentPage(result, writer, autoTip);
					if (result != null)
						result.close();
				}
			}
		} catch (CorruptIndexException e) {
			e.printStackTrace();
			logger.error(e);
			throw new RuntimeException(e);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
			throw new RuntimeException(e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new RuntimeException(e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error(e);
					throw new RuntimeException(e);
				}
		}
	}

	private void processDBSourceCurrentPage(ResultSet result,
			IndexWriter writer, Autotip autoTip) throws SQLException,
			CorruptIndexException, IOException {
		List<FieldItem> fields = autoTip.getFields().getField();
		while (result.next()) {
			Document doc = new Document();
			for (FieldItem field : fields) {
				String value = result.getString(field.getRefField());

				if ((value != null) && (value.trim().length() > 0)) {
					doc.add(new Field(field.getName(), value, Field.Store.YES,
							AutoTipsTools.getFieldIndexProperty(field)));
				}
			}
			writer.addDocument(doc);
		}
	}
}