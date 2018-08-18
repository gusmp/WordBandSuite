package org.wbfd.gui.listeners;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.wbfd.Global;
import org.wbfd.enums.LEVEL;
import org.wbfd.gui.AboutWindow;
import org.wbfd.gui.CollocationWindow;
import org.wbfd.gui.MsgBox;
import org.wbfd.gui.PhrasalVerbWindow;
import org.wbfd.gui.WordFormationWindow;
import org.wbfd.service.impl.LocaleServiceImpl.TEXT;

public class OpenWindowListener implements Listener
{
    public enum WINDOW_TYPE
    {
	PHRASAL_VERB_WINDOW, WORD_FORMATION_WINDOW, COLLOCATION_WINDOW, ABOUT_WINDOW
    };

    private Shell parent;
    private WINDOW_TYPE windowType;
    private LEVEL level;

    public OpenWindowListener(Shell parent, WINDOW_TYPE windowType, LEVEL level)
    {
	this.parent = parent;
	this.windowType = windowType;
	this.level = level;
    }

    public void handleEvent(Event event)
    {
	TEXT title = TEXT.ERROR;
	LEVEL realLevel = level;
	try
	{
	    if (realLevel == LEVEL.CPE)
	    {
		if (Global.getHellModeOption() == true)
		{
		    realLevel = LEVEL.CPE_HELL_MODE;
		}
	    }

	    if (windowType == WINDOW_TYPE.PHRASAL_VERB_WINDOW)
	    {
		title = TEXT.PV_SCREEN_TITLE;
		PhrasalVerbWindow pvWindow = new PhrasalVerbWindow(parent, 33556576 | SWT.APPLICATION_MODAL, realLevel);
		pvWindow.open();
	    }
	    else if (windowType == WINDOW_TYPE.WORD_FORMATION_WINDOW)
	    {
		title = TEXT.WF_SCREEN_TITLE;
		WordFormationWindow wfWindow = new WordFormationWindow(parent, 33556576 | SWT.APPLICATION_MODAL, realLevel);
		wfWindow.open();
	    }
	    else if (windowType == WINDOW_TYPE.COLLOCATION_WINDOW)
	    {
		title = TEXT.CO_SCREEN_TITLE;
		CollocationWindow coWindow = new CollocationWindow(parent, 33556576 | SWT.APPLICATION_MODAL, realLevel);
		coWindow.open();
	    }
	    else if (windowType == WINDOW_TYPE.ABOUT_WINDOW)
	    {
		title = TEXT.ABOUT_SCREEN_TITLE;
		AboutWindow aboutWindow = new AboutWindow(parent, 33556576 | SWT.APPLICATION_MODAL);
		aboutWindow.open();
	    }
	}
	catch (Exception exc)
	{
	    MsgBox.show(parent, SWT.ICON_ERROR | SWT.OK, Global.getBeanFactory().getLocaleService().getText(title), "It was an error opening the exercice.\nMaybe there are not exercises loaded?\n" + exc.toString());
	}
    }
}
