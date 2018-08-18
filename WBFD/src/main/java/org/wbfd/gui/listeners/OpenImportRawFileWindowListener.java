package org.wbfd.gui.listeners;

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.wbfd.Global;
import org.wbfd.gui.MsgBox;
import org.wbfd.service.impl.LocaleServiceImpl.TEXT;

public class OpenImportRawFileWindowListener implements Listener
{

    private Shell parent;

    public OpenImportRawFileWindowListener(Shell parent)
    {
	this.parent = parent;
    }

    public void handleEvent(Event event)
    {
	TEXT title = TEXT.IMPORT_SCREEN_TITLE;
	FileDialog fileDlg = new FileDialog(parent, SWT.OPEN);
	fileDlg.setFilterExtensions((String[]) Arrays.asList("*.txt").toArray());

	String fileSelected = fileDlg.open();

	// cancel button
	if (fileSelected == null)
	{
	    return;
	}

	try
	{
	    Global.getBeanFactory().getImportRawFormatService().importDataBase(fileSelected);

	    MsgBox.show(parent, SWT.ICON_WORKING | SWT.OK, Global.getBeanFactory().getLocaleService().getText(title), Global.getBeanFactory().getLocaleService().getText(TEXT.IMPORT_OK));
	}
	catch (Exception exc)
	{
	    MsgBox.show(parent, SWT.ICON_ERROR | SWT.OK, Global.getBeanFactory().getLocaleService().getText(title), "Some errors were found during the import process\n" + exc.getMessage());
	}
    }
}
