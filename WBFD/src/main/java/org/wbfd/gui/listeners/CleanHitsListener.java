package org.wbfd.gui.listeners;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.wbfd.Global;
import org.wbfd.enums.EXERCISE_TYPE;
import org.wbfd.gui.MsgBox;
import org.wbfd.service.impl.LocaleServiceImpl.TEXT;

public class CleanHitsListener implements Listener
{
    private EXERCISE_TYPE exerciseType;
    private Shell parent;

    public CleanHitsListener(Shell parent, EXERCISE_TYPE exerciseType)
    {
	this.exerciseType = exerciseType;
	this.parent = parent;
    }

    public void handleEvent(Event arg0)
    {
	if (MsgBox.show(parent, SWT.APPLICATION_MODAL | SWT.YES | SWT.NO, Global.getBeanFactory().getLocaleService().getText(TEXT.APP_TITLE), Global.getBeanFactory().getLocaleService().getText(TEXT.MAIN_HIT_CONFIRMATION)) == SWT.YES)
	{
	    TEXT title = TEXT.ERROR;
	    try
	    {
		switch (exerciseType)
		{
		case PHRASAL_VERB:
		    Global.getBeanFactory().getPhrasalVerbService().clearHits();
		    break;
		case WORD_FORMATION:
		    Global.getBeanFactory().getWordFormationService().clearHits();
		    break;
		case COLLOCATION:
		    Global.getBeanFactory().getCollocationService().clearHits();
		    break;
		}
	    }
	    catch (SQLException exc)
	    {
		MsgBox.show(parent, SWT.ICON_ERROR | SWT.OK, Global.getBeanFactory().getLocaleService().getText(title), "It was an error updating the hits\n" + exc.toString());
	    }
	}
    }

}
