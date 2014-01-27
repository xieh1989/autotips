package org.leopardocs.autotips.core.mode;


public class DBModeConfig extends ModeConfig {
	public static final String DEFAULT_CONFIGURATION_FILE_BACKUP = "classpath:/autotips_backup.xml";
	public String configFileForUse = "classpath:/autotips_backup.xml";

	public void setConfigFile(String forUse) {
		if (forUse != null)
			this.configFileForUse = forUse;
	}

	public String getConfigFile() {
		return this.configFileForUse;
	}
}