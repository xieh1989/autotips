package org.leopardocs.autotips.web.param;

import javax.servlet.http.HttpServletRequest;

import org.leopardocs.autotips.core.ArgumentExtracter;

public class HttpArgumentExtracter implements ArgumentExtracter {
	private HttpServletRequest request;

	public HttpArgumentExtracter(HttpServletRequest request) {
		this.request = request;
	}

	public <E> E get(String key, Class<E> returnClass) {
    if (returnClass.equals(String.class)) {
      return (E)this.request.getParameter(key);
    }
    if (returnClass.equals(String[].class)) {
      return (E)this.request.getParameterValues(key);
    }
    return null;
  }
}