package org.wbfd.gui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.wbfd.Global;
import org.wbfd.service.impl.LocaleServiceImpl.TEXT;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class AboutWindow extends Dialog
{

    protected Object result;
    protected Shell aboutWindow;

    public AboutWindow(Shell parent, int style)
    {
	super(parent, style);
	setText("SWT Dialog");
    }

    public Object open()
    {
	createContents();
	aboutWindow.open();
	aboutWindow.layout();
	Display display = getParent().getDisplay();
	while (!aboutWindow.isDisposed())
	{
	    if (!display.readAndDispatch())
	    {
		display.sleep();
	    }
	}
	return result;
    }

    private void createContents()
    {
	aboutWindow = new Shell(getParent(), getStyle());
	aboutWindow.setSize(450, 180);
	aboutWindow.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.ABOUT_SCREEN_TITLE));
	GuiUtils.centerWindow(getParent(), aboutWindow);

	Group group = new Group(aboutWindow, SWT.NONE);
	group.setBounds(10, 10, 426, 134);

	Label lbTitle = new Label(group, SWT.NONE);
	lbTitle.setAlignment(SWT.CENTER);
	lbTitle.setFont(SWTResourceManager.getFont("Arial Black", 21, SWT.BOLD));
	lbTitle.setBounds(10, 10, 406, 43);
	lbTitle.setText("Word Bank for Desktop");

	Label lbVersion = new Label(group, SWT.NONE);
	lbVersion.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	lbVersion.setBounds(20, 59, 396, 16);
	lbVersion.setText("Version: " + Global.getBeanFactory().getLocaleService().getText(TEXT.APP_VERSION));

	Label lbDBRelease = new Label(group, SWT.NONE);
	lbDBRelease.setBounds(20, 93, 396, 16);
	lbDBRelease.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.ABOUT_SCREEN_DB_UPDATE));
	lbDBRelease.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));

    }
}
