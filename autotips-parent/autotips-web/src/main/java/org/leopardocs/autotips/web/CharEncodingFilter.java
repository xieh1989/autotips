package org.leopardocs.autotips.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharEncodingFilter implements Filter {
	private String encoding = AutotipsWebCST.DEFAULT_ENCODING;

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(this.encoding);
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		String enc = filterConfig.getInitParameter("encoding");
		if (enc != null)
			this.encoding = enc;
	}
}