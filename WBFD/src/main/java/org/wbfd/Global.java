package org.wbfd;

import org.wbfd.di.BeanFactory;

public class Global
{
    private static BeanFactory bf;

    public static BeanFactory getBeanFactory()
    {
	return bf;
    }

    static
    {
	try
	{
	    bf = new BeanFactory();
	}
	catch (Exception exc)
	{
	}
    }

    private static Boolean hellModeOption = false;

    public static Boolean getHellModeOption()
    {
	return hellModeOption;
    }

    public static void setHellModeOption(Boolean hellModeOption)
    {
	Global.hellModeOption = hellModeOption;
    }
}
