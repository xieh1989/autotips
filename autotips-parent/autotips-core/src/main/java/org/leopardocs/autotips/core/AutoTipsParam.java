package org.leopardocs.autotips.core;

import org.leopardocs.autotips.core.config.Autotip;

public class AutoTipsParam {
	protected Autotip autoTip;
	protected ArgumentExtracter argumentExtracter;

	public Autotip getAutoTip() {
		return this.autoTip;
	}

	public void setAutoTip(Autotip autoTip) {
		this.autoTip = autoTip;
	}

	public ArgumentExtracter getArgumentExtracter() {
		return this.argumentExtracter;
	}

	public void setArgumentExtracter(ArgumentExtracter argumentExtracter) {
		this.argumentExtracter = argumentExtracter;
	}
}