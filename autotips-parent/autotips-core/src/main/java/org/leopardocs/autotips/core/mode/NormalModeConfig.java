package org.leopardocs.autotips.core.mode;


public class NormalModeConfig extends ModeConfig {
	public static final String DEFAULT_CONFIGURATION_FILE = "/autotips.xml";
	public String configFileForUse = "/autotips.xml";

	public void setConfigFile(String forUse) {
		if (forUse != null)
			this.configFileForUse = forUse;
	}

	public String getConfigFile() {
		return this.configFileForUse;
	}
}