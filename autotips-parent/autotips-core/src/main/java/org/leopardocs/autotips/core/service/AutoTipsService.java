package org.leopardocs.autotips.core.service;

import org.leopardocs.autotips.core.AutoTipsParam;
import org.leopardocs.autotips.core.AutoTipsResult;

public abstract interface AutoTipsService {
	public abstract AutoTipsResult processSearch(AutoTipsParam paramAutoTipsParam);
}