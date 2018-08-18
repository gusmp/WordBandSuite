package org.wbfd.gui.listeners;

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.wbfd.Global;
import org.wbfd.enums.EXERCISE_TYPE;
import org.wbfd.gui.MsgBox;
import org.wbfd.service.impl.LocaleServiceImpl.TEXT;

public class OpenImportFileWindowListener implements Listener
{

    private Shell parent;
    private EXERCISE_TYPE exerciseType;

    public OpenImportFileWindowListener(Shell parent, EXERCISE_TYPE exerciseType)
    {
	this.parent = parent;
	this.exerciseType = exerciseType;
    }

    public void handleEvent(Event event)
    {
	TEXT title = TEXT.IMPORT_SCREEN_TITLE;
	FileDialog fileDlg = new FileDialog(parent, SWT.OPEN);
	if (exerciseType != null)
	{
	    fileDlg.setFilterExtensions((String[]) Arrays.asList("*.csv").toArray());
	}
	String fileSelected = fileDlg.open();

	// cancel button
	if (fileSelected == null)
	{
	    return;
	}

	try
	{
	    if (exerciseType == EXERCISE_TYPE.PHRASAL_VERB)
	    {
		Global.getBeanFactory().getImportPhrasalVerbService().importFile(fileSelected);
	    }
	    else if (exerciseType == EXERCISE_TYPE.WORD_FORMATION)
	    {
		Global.getBeanFactory().getImportWordFormationService().importFile(fileSelected);
	    }
	    else if (exerciseType == EXERCISE_TYPE.COLLOCATION)
	    {
		Global.getBeanFactory().getImportCollocationService().importFile(fileSelected);
	    }
	    else if (exerciseType == null)
	    {
		title = TEXT.MERGE_SCREEN_TITLE;
		Global.getBeanFactory().getImportPhrasalVerbService().importDataBase(fileSelected);
		Global.getBeanFactory().getImportWordFormationService().importDataBase(fileSelected);
		Global.getBeanFactory().getImportCollocationService().importDataBase(fileSelected);
	    }

	    MsgBox.show(parent, SWT.ICON_WORKING | SWT.OK, Global.getBeanFactory().getLocaleService().getText(title), Global.getBeanFactory().getLocaleService().getText(TEXT.IMPORT_OK));
	}
	catch (Exception exc)
	{
	    MsgBox.show(parent, SWT.ICON_ERROR | SWT.OK, Global.getBeanFactory().getLocaleService().getText(title), "Some errors were found during the import process\n" + exc.getMessage());
	}
    }
}
