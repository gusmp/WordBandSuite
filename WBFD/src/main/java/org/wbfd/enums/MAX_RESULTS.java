package org.wbfd.enums;

public enum MAX_RESULTS
{

    MAX_RESULTS_PV(10), MAX_RESULTS_WF(10), MAX_RESULTS_CO(10);

    private Integer value;

    MAX_RESULTS(Integer value)
    {
	this.value = value;
    }

    public Integer getValue()
    {
	return this.value;
    }
}
