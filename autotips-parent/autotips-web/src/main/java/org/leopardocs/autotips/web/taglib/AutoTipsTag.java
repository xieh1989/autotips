package org.leopardocs.autotips.web.taglib;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class AutoTipsTag extends SimpleTagSupport implements DynamicAttributes {
	protected static final String OTHER_PARAM_PREFIX = "param_";
	protected static final String OTHER_EVAL_PARAM_PREFIX = "paramEval_";
	private String id;
	private String autoTipId;
	private String name;
	private String value = "";
	private String searchParamName;
	private String resultFieldName;
	private boolean forceSelect = false;
	private String onSelect;
	private String onSearch;
	private int delay = 100;
	private int minLength = 2;

	private Map<String, String> dynamicAttributes = new LinkedHashMap<String, String>();

	public void doTag() throws JspException, IOException {
		if ((this.id == null) && (this.name == null)) {
			throw new JspException(
					"autotip tag, id and name must not be all null");
		}

		Map<String, String> paramsMap = new LinkedHashMap<String, String>();
		Map<String, String> evalParamsMap = new LinkedHashMap<String, String>();
		Map<String, String> otherAttributes = new LinkedHashMap<String, String>();

		for (Map.Entry<String, String> entry : this.dynamicAttributes
				.entrySet()) {
			String key = entry.getKey();
			if (key.startsWith("param_"))
				paramsMap.put(key.substring("param_".length()),
						entry.getValue());
			else if (key.startsWith("paramEval_"))
				evalParamsMap.put(key.substring("paramEval_".length()),
						entry.getValue());
			else {
				otherAttributes.put(key, entry.getValue());
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append("<input type=\"text\" ");
		if (this.id != null) {
			sb.append("id=\"").append(this.id).append("\" ");
		}
		if (this.name != null) {
			sb.append("name=\"").append(this.name).append("\" ");
		}
		sb.append("value=\"").append(this.value).append("\" ");

		for (Map.Entry<String, String> entry : otherAttributes.entrySet()) {
			sb.append((String) entry.getKey()).append("=\"")
					.append((String) entry.getValue()).append("\" ");
		}
		sb.append("/>");

		sb.append("<script type=\"text/javascript\">jQuery(document).ready(function(){");
		sb.append("var config = new Object();");
		if ((this.id != null) && (this.id.trim().length() > 0))
			sb.append("config.inputId = \"").append(this.id).append("\";");
		else {
			sb.append("config.inputName = \"").append(this.name).append("\";");
		}
		sb.append("config.searchParamName = \"").append(this.searchParamName)
				.append("\";");
		sb.append("config.otherParams = new Object();");
		sb.append("config.otherParams.autoTipId = \"").append(this.autoTipId)
				.append("\";");
		for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
			sb.append("config.otherParams[\"").append((String) entry.getKey())
					.append("\"] = \"").append((String) entry.getValue())
					.append("\";");
		}
		sb.append("config.otherEvalParams = new Object();");
		for (Map.Entry<String, String> entry : evalParamsMap.entrySet()) {
			sb.append("config.otherEvalParams[\"")
					.append((String) entry.getKey()).append("\"] = \"")
					.append((String) entry.getValue()).append("\";");
		}
		sb.append("config.resultFieldName = \"").append(this.resultFieldName)
				.append("\";");
		sb.append("config.forceSelect = ").append(this.forceSelect).append(";");
		sb.append("config.delay = ").append(this.delay).append(";");
		sb.append("config.minLength = ").append(this.minLength).append(";");
		if (this.onSelect != null) {
			sb.append("config.onSelect = \"").append(this.onSelect)
					.append("\";");
		}
		if (this.onSearch != null) {
			sb.append("config.onSearch = \"").append(this.onSearch)
					.append("\";");
		}
		sb.append("autotips(config);");
		sb.append("});</script>");

		getJspContext().getOut().print(sb);
	}

	protected String getNextId() {
		Integer newId = (Integer) getJspContext().getAttribute(
				"_autotips_tag_last_id");
		Integer localInteger1;
		if (newId == null) {
			newId = Integer.valueOf(0);
		} else {
			localInteger1 = newId;
			Integer localInteger2 = newId = Integer
					.valueOf(newId.intValue() + 1);
		}
		getJspContext().setAttribute("_autotips_tag_last_id", newId);
		return "_autotips_tag_id_" + newId;
	}

	public void setDynamicAttribute(String url, String localName, Object value)
			throws JspException {
		this.dynamicAttributes.put(localName, value.toString());
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAutoTipId() {
		return this.autoTipId;
	}

	public void setAutoTipId(String autoTipId) {
		this.autoTipId = autoTipId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSearchParamName() {
		return this.searchParamName;
	}

	public void setSearchParamName(String searchParamName) {
		this.searchParamName = searchParamName;
	}

	public String getResultFieldName() {
		return this.resultFieldName;
	}

	public void setResultFieldName(String resultFieldName) {
		this.resultFieldName = resultFieldName;
	}

	public boolean isForceSelect() {
		return this.forceSelect;
	}

	public void setForceSelect(boolean forceSelect) {
		this.forceSelect = forceSelect;
	}

	public String getOnSelect() {
		return this.onSelect;
	}

	public void setOnSelect(String onSelect) {
		this.onSelect = onSelect;
	}

	public String getOnSearch() {
		return this.onSearch;
	}

	public void setOnSearch(String onSearch) {
		this.onSearch = onSearch;
	}

	public int getDelay() {
		return this.delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public int getMinLength() {
		return this.minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}
}