package org.wbfd.enums;

public enum EXERCISE_HEADER
{
    PV("PHRASAL VERBS"), WF("WORD FORMATION"), CO("COLLOCATION");

    private String header;

    EXERCISE_HEADER(String header)
    {
	this.header = header;
    }

    public String getHeader()
    {
	return this.header;
    }
};
