package org.leopardocs.autotips.core.mode;


public abstract class ModeConfig {
	private static ModeConfig _DEFAULT = new NormalModeConfig();

	private static ModeConfig _DBMODE = new DBModeConfig();

	public static ModeConfig getDefault() {
		return _DEFAULT;
	}

	public abstract void setConfigFile(String paramString);

	public abstract String getConfigFile();

	public static ModeConfig getDBMODE() {
		return _DBMODE;
	}

}