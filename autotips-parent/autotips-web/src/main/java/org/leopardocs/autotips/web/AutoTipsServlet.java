package org.leopardocs.autotips.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.leopardocs.autotips.core.AutoTipsParam;
import org.leopardocs.autotips.core.AutoTipsResult;
import org.leopardocs.autotips.core.config.Autotip;
import org.leopardocs.autotips.core.config.Autotips;
import org.leopardocs.autotips.core.format.AutoTipsOutput;
import org.leopardocs.autotips.core.format.JSONOutput;
import org.leopardocs.autotips.core.service.AutoTipsService;
import org.leopardocs.autotips.core.service.impl.AutoTipsSearchEngineImpl;
import org.leopardocs.autotips.web.param.HttpArgumentExtracter;

public class AutoTipsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Log logger = LogFactory.getLog(AutoTipsServlet.class);
	private static String PARAM_DEFAULT_AUTOTIP_ID_NAME = "autoTipId";
	private static String ATT_AUTOTIP_CONFIG_KEY = "_AUTOTIP_AUTOTIPCONFIG";
	private static Autotips AUTOTIPS_CONFIG;

	private static AutoTipsService autoTipsSearchEngineImpl = new AutoTipsSearchEngineImpl();
	private static AutoTipsOutput autoTipsOutput = new JSONOutput();

	public static void setAutoTipsConfig(Autotips autoTipsConfig) {
		setAutoTipsConfig(autoTipsConfig, false);
	}

	public static void setAutoTipsConfig(Autotips autoTipsConfig, boolean force) {
		if (force) {
			AUTOTIPS_CONFIG = autoTipsConfig;
		}
		if (AUTOTIPS_CONFIG == null)
			AUTOTIPS_CONFIG = autoTipsConfig;
	}

	public static Autotips getAutoTipsConfig() {
		return AUTOTIPS_CONFIG;
	}

	private AutoTipsResult processAutoTip(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Autotip autoTip = (Autotip) request
				.getAttribute(ATT_AUTOTIP_CONFIG_KEY);

		AutoTipsParam param = new AutoTipsParam();
		param.setAutoTip(autoTip);
		param.setArgumentExtracter(new HttpArgumentExtracter(request));
		AutoTipsResult result = autoTipsSearchEngineImpl.processSearch(param);
		return result;
	}

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType(autoTipsOutput.getContentType());
		request.setCharacterEncoding(autoTipsOutput.getCharacterEncoding());
		String autoTipId = request.getParameter(PARAM_DEFAULT_AUTOTIP_ID_NAME);
		Autotip autoTip = WebConfigurator
				.getAutotip(AUTOTIPS_CONFIG, autoTipId);

		if (autoTip == null) {
			throw new IllegalArgumentException("autoTipId " + autoTipId
					+ " not found in configuration.");
		}

		request.setAttribute(ATT_AUTOTIP_CONFIG_KEY, autoTip);

		if (logger.isDebugEnabled()) {
			logger.debug("Receive autoTipId = " + autoTipId + " autotip.");
		}

		PrintWriter out = response.getWriter();
		AutoTipsResult result = processAutoTip(request, response);
		StringBuilder processOutput = autoTipsOutput.processOutput(result);
		if (logger.isDebugEnabled()) {
			logger.debug("Output : " + processOutput.toString());
		}
		out.print(processOutput.toString());
		out.flush();
		out.close();
	}
}