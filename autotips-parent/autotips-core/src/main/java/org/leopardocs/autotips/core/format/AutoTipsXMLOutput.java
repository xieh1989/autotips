package org.leopardocs.autotips.core.format;

import org.leopardocs.autotips.core.AutoTipsResult;

public class AutoTipsXMLOutput implements AutoTipsOutput {
	public StringBuilder processOutput(AutoTipsResult result) {
		StringBuilder builder = new StringBuilder();
		if ((result != null) && (result.getValues() != null)) {
			builder.append("<r>");

			String[][] values = result.getValues();
			String[] columns = result.getColumns();
			int endIndex = values.length;
			int columnSize = columns.length;
			String column = "";
			String itemValue = "";
			for (int i = 0; i < endIndex; i++) {
				builder.append("<i ");
				for (int j = 0; j < columnSize; j++) {
					column = columns[j];
					itemValue = values[i][j];
					builder.append(column);
					builder.append("=\"");
					builder.append(itemValue);
					builder.append("\" ");
				}
				builder.append(" />");
			}
			builder.append("<c v=\"");
			builder.append(result.getCost());
			builder.append("ms");
			builder.append("\" />");
			builder.append("</r>");
		}
		return builder;
	}

	public String getContentType() {
		return "text/xml;charset=utf-8";
	}

	public String getCharacterEncoding() {
		return "utf-8";
	}
}