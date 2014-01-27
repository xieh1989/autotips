package org.leopardocs.autotips.core.format;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.leopardocs.autotips.core.AutoTipsResult;

public class JSONOutput implements AutoTipsOutput {
	private static final Log logger = LogFactory.getLog(JSONOutput.class);

	public StringBuilder processOutput(AutoTipsResult result) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		if ((result != null) && (result.getValues() != null)) {
			String[][] values = result.getValues();
			String[] columns = result.getColumns();
			int endIndex = values.length;
			int columnSize = columns.length;
			String column = "";
			String itemValue = "";
			for (int i = 0; i < endIndex; i++) {
				if (values[i][0] != null) {
					builder.append("{");
					for (int j = 0; j < columnSize; j++) {
						column = columns[j];
						itemValue = values[i][j];
						builder.append("\"");
						builder.append(column);
						builder.append("\":\"");
						builder.append(itemValue);
						if (j == columnSize - 1)
							builder.append("\"");
						else {
							builder.append("\",");
						}
					}
					builder.append(" }, ");
				}

			}

			if (!builder.toString().equals("[")) {
				builder.delete(builder.length() - 2, builder.length());
			}
		}

		builder.append("]");

		if (logger.isDebugEnabled()) {
			logger.debug("JSON Output: " + builder.toString());
		}
		return builder;
	}

	public String getContentType() {
		return "application/json;charset=UTF-8";
	}

	public String getCharacterEncoding() {
		return "utf-8";
	}
}