package org.leopardocs.autotips.core.format;

import org.leopardocs.autotips.core.AutoTipsResult;

public abstract interface AutoTipsOutput {
	public static final String CHARACTER_ENCODING_UTF8 = "utf-8";
	public static final String CONTENT_TYPE_XML = "text/xml;charset=utf-8";
	public static final String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";

	public abstract StringBuilder processOutput(AutoTipsResult paramAutoTipsResult);

	public abstract String getContentType();

	public abstract String getCharacterEncoding();
}