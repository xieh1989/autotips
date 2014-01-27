package org.leopardocs.autotips.core.tools;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Field;
import org.leopardocs.autotips.core.AutoTipsParam;
import org.leopardocs.autotips.core.config.Autotip;
import org.leopardocs.autotips.core.config.FieldItem;
import org.leopardocs.autotips.core.config.Source;

public class AutoTipsTools {
	private static final Log logger = LogFactory.getLog(AutoTipsTools.class);

	public static boolean checkAutoTipsParam(AutoTipsParam param) {
		return true;
	}

	public static java.sql.Connection getSourceTypeConnection(Source source)
			throws Exception {
		java.sql.Connection conn = null;
		if (source != null) {
			if (source.getJndiName() != null)
				try {
					Context ctx = new InitialContext();
					conn = ((DataSource) ctx.lookup(source.getJndiName()))
							.getConnection();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					throw e;
				}
			else {
				try {
					org.leopardocs.autotips.core.config.Connection connection = source
							.getConnection();
					Class.forName(connection.getDriver());
					conn = DriverManager.getConnection(connection.getUrl(),
							connection.getUname(), connection.getUpwd());
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					throw e;
				}
			}
		}
		return conn;
	}

	public static String getCountSQL(String oSQL) {
		String sql = "SELECT COUNT(*) VNUM FROM (" + oSQL + ") V$T";
		if (logger.isDebugEnabled()) {
			logger.debug("oSQL = " + oSQL);
			logger.debug("CountSQL = " + sql);
		}
		return sql;
	}

	public static String getPagingSQL(String oSQL, String orderField) {
		String orderSQL = oSQL + " ORDER BY " + orderField;
		String pagingSQL = "SELECT * FROM (SELECT V$T.*, ROWNUM RN FROM ("
				+ orderSQL + ") V$T WHERE ROWNUM <= ? ) WHERE RN >=  ?";
		if (logger.isDebugEnabled()) {
			logger.debug("oSQL = " + oSQL);
			logger.debug("orderSQL = " + orderSQL);
			logger.debug("pagingSQL = " + pagingSQL);
		}
		return pagingSQL;
	}

	public static String getDBImplSQL(String oSQL, String orderField,
			List<String> queryStrings) {
		String querySql = oSQL + " ";
		for (int i = 0; i < queryStrings.size(); i++) {
			if (i == queryStrings.size() - 1)
				querySql = querySql + (String) queryStrings.get(i) + " ";
			else {
				querySql = querySql + (String) queryStrings.get(i) + " AND ";
			}
		}
		querySql = querySql + " ORDER BY " + orderField;

		if (logger.isDebugEnabled()) {
			logger.debug("oSQL = " + oSQL);
			logger.debug("querySql = " + querySql);
		}
		return querySql;
	}

	public static FieldItem getDefaultField(Autotip autoTip) {
		return getField(autoTip, autoTip.getDefaultFieldName());
	}

	public static FieldItem getField(Autotip autoTip, String fieldName) {
		List<FieldItem> fields = autoTip.getFields().getField();
		for (FieldItem field : fields) {
			if (field.getName().equalsIgnoreCase(fieldName)) {
				return field;
			}
		}
		return null;
	}

	public static Field.Index getFieldIndexProperty(FieldItem field) {
		if (field.isAnalyzed()) {
			return Field.Index.ANALYZED;
		}
		return Field.Index.NO;
	}

	public static String getFullStackTrace(Throwable throwable) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		throwable.printStackTrace(pw);
		return sw.getBuffer().toString();
	}

	public static int getTotalPage(int count, int perPage) {
		return count % perPage != 0 ? count / perPage + 1 : count / perPage;
	}

	public static long sizeOf(File file) {
		if (!file.exists()) {
			return 0L;
		}

		if (file.isDirectory()) {
			return sizeOfDirectory(file);
		}
		return file.length();
	}

	public static String getCurrentTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS")
				.format(new Date());
	}

	public static long sizeOfDirectory(File directory) {
		if (!directory.exists()) {
			return 0L;
		}

		if (!directory.isDirectory()) {
			return 0L;
		}

		long size = 0L;

		File[] files = directory.listFiles();
		if (files == null) {
			return 0L;
		}
		for (File file : files) {
			size += sizeOf(file);
		}

		return size;
	}
}