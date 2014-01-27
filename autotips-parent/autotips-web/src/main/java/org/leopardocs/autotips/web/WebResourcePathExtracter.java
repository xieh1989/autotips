package org.leopardocs.autotips.web;

import javax.servlet.ServletContext;

import org.leopardocs.autotips.core.StandardResourcePathExtracter;

public class WebResourcePathExtracter extends StandardResourcePathExtracter {
	private ServletContext context;
	private static final String PREFIX_CONTEXT = "context:";

	public WebResourcePathExtracter(ServletContext context) {
		this.context = context;
	}

	public String getAbsPath(String path) {
		String forReturn = null;
		if ((path != null) && (path.trim().length() > 0)) {
			if (path.startsWith("context:")) {
				if (this.context == null) {
					throw new IllegalArgumentException(
							"context must have value when use Context Path.");
				}

				path = path.substring("context:".length());
				forReturn = this.context.getRealPath(path);
			}

			if (forReturn == null) {
				forReturn = super.getAbsPath(path);
			}
		}
		return forReturn;
	}
}