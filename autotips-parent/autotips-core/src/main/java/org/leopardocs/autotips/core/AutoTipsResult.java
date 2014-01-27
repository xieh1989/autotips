package org.leopardocs.autotips.core;

public class AutoTipsResult {
	private AutoTipsParam param;
	private boolean returnCost;
	private long cost;
	private String[][] values;
	private String[] columns;

	public String[][] getValues() {
		return this.values;
	}

	public void setValues(String[][] values) {
		this.values = values;
	}

	public String[] getColumns() {
		return this.columns;
	}

	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	public boolean isReturnCost() {
		return this.returnCost;
	}

	public long getCost() {
		return this.cost;
	}

	public void setCost(long cost) {
		this.cost = cost;
	}

	public void setReturnCost(boolean returnCost) {
		this.returnCost = returnCost;
	}

	public AutoTipsParam getParam() {
		return this.param;
	}

	public void setParam(AutoTipsParam param) {
		this.param = param;
	}
}