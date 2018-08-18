package org.wbfd.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.wbfd.Global;
import org.wbfd.enums.EXERCISE_TYPE;
import org.wbfd.enums.LEVEL;
import org.wbfd.gui.listeners.CleanHitsListener;
import org.wbfd.gui.listeners.OpenImportFileWindowListener;
import org.wbfd.gui.listeners.OpenImportRawFileWindowListener;
import org.wbfd.gui.listeners.OpenWindowListener;
import org.wbfd.gui.listeners.OpenWindowListener.WINDOW_TYPE;
import org.wbfd.service.impl.LocaleServiceImpl.TEXT;

public class MainWindow extends Dialog
{
    protected Object result;
    protected Shell wordBankDialog;

    public MainWindow(Shell parent, int style)
    {
	super(parent, style);
	wordBankDialog = parent;
    }

    public Object open()
    {
	wordBankDialog.setSize(450, 300);
	GuiUtils.centerWindow(getParent(), getParent());

	createContents();
	wordBankDialog.open();
	wordBankDialog.layout();
	Display display = getParent().getDisplay();
	while (!wordBankDialog.isDisposed())
	{
	    if (!display.readAndDispatch())
	    {
		display.sleep();
	    }
	}
	return result;
    }

    /**
     * Create contents of the dialog.
     */
    private void createContents()
    {
	wordBankDialog.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.APP_TITLE));

	Menu bar = new Menu(wordBankDialog, SWT.BAR);

	buildCAEMenu(bar);
	buildCPEMenu(bar);
	buildImportMenu(bar);
	buildHitMenu(bar);
	buildAboutMenu(bar);

	wordBankDialog.setMenuBar(bar);

	wordBankDialog.addListener(SWT.Close, new Listener()
	{
	    public void handleEvent(Event event)
	    {
		if (MsgBox.show(wordBankDialog, SWT.APPLICATION_MODAL | SWT.YES | SWT.NO, Global.getBeanFactory().getLocaleService().getText(TEXT.APP_TITLE), Global.getBeanFactory().getLocaleService().getText(TEXT.MAIN_CONFIRM_CLOSE)) == SWT.YES)
		{
		    Global.getBeanFactory().getDbf().closeConnection();
		    event.doit = true;
		}
		else
		{
		    event.doit = false;
		}
	    }
	});
    }

    private void buildCAEMenu(Menu bar)
    {
	MenuItem caeMenu = new MenuItem(bar, SWT.CASCADE);
	caeMenu.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MAIN_CAE_MENU));

	Menu caePhrasalVerbMenu = new Menu(wordBankDialog, SWT.DROP_DOWN);
	caeMenu.setMenu(caePhrasalVerbMenu);

	MenuItem caePhrasalVerbItem = new MenuItem(caePhrasalVerbMenu, SWT.PUSH);
	caePhrasalVerbItem.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MAIN_CPE_CAE_PV));
	caePhrasalVerbItem.addListener(SWT.Selection, new OpenWindowListener(getParent(), WINDOW_TYPE.PHRASAL_VERB_WINDOW, LEVEL.CAE));

	MenuItem caeWordFormationItem = new MenuItem(caePhrasalVerbMenu, SWT.PUSH);
	caeWordFormationItem.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MAIN_CPE_CAE_WF));
	caeWordFormationItem.addListener(SWT.Selection, new OpenWindowListener(getParent(), WINDOW_TYPE.WORD_FORMATION_WINDOW, LEVEL.CAE));

	MenuItem caeCollocationItem = new MenuItem(caePhrasalVerbMenu, SWT.PUSH);
	caeCollocationItem.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MAIN_CPE_CAE_CO));
	caeCollocationItem.addListener(SWT.Selection, new OpenWindowListener(getParent(), WINDOW_TYPE.COLLOCATION_WINDOW, LEVEL.CAE));
    }

    private void buildCPEMenu(Menu bar)
    {
	MenuItem cpeMenu = new MenuItem(bar, SWT.CASCADE);
	cpeMenu.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MAIN_CPE_MENU));

	Menu cpePhrasalVerbMenu = new Menu(wordBankDialog, SWT.DROP_DOWN);
	cpeMenu.setMenu(cpePhrasalVerbMenu);

	MenuItem cpePhrasalVerbItem = new MenuItem(cpePhrasalVerbMenu, SWT.PUSH);
	cpePhrasalVerbItem.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MAIN_CPE_CAE_PV));
	cpePhrasalVerbItem.addListener(SWT.Selection, new OpenWindowListener(getParent(), WINDOW_TYPE.PHRASAL_VERB_WINDOW, LEVEL.CPE));

	MenuItem cpeWordFormationItem = new MenuItem(cpePhrasalVerbMenu, SWT.PUSH);
	cpeWordFormationItem.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MAIN_CPE_CAE_WF));
	cpeWordFormationItem.addListener(SWT.Selection, new OpenWindowListener(getParent(), WINDOW_TYPE.WORD_FORMATION_WINDOW, LEVEL.CPE));

	MenuItem cpeCollocationItem = new MenuItem(cpePhrasalVerbMenu, SWT.PUSH);
	cpeCollocationItem.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MAIN_CPE_CAE_CO));
	cpeCollocationItem.addListener(SWT.Selection, new OpenWindowListener(getParent(), WINDOW_TYPE.COLLOCATION_WINDOW, LEVEL.CPE));

	MenuItem cpeHellModeItem = new MenuItem(cpePhrasalVerbMenu, SWT.CHECK);
	cpeHellModeItem.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MAIN_CPE_HELL_MODE));
	cpeHellModeItem.addListener(SWT.Selection, new Listener()
	{

	    public void handleEvent(Event arg0)
	    {
		Global.setHellModeOption(((MenuItem) arg0.widget).getSelection());
	    }
	});
    }

    private void buildImportMenu(Menu bar)
    {
	MenuItem importMenu = new MenuItem(bar, SWT.CASCADE);
	importMenu.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MAIN_IMPORT_MENU));

	Menu importImportMenu = new Menu(wordBankDialog, SWT.DROP_DOWN);
	importMenu.setMenu(importImportMenu);

	MenuItem importPhrasalVerbItem = new MenuItem(importImportMenu, SWT.PUSH);
	importPhrasalVerbItem.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MAIN_IMPORT_MENU_PV));
	importPhrasalVerbItem.addListener(SWT.Selection, new OpenImportFileWindowListener(getParent(), EXERCISE_TYPE.PHRASAL_VERB));

	MenuItem importWordFormationItem = new MenuItem(importImportMenu, SWT.PUSH);
	importWordFormationItem.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MAIN_IMPORT_MENU_WF));
	importWordFormationItem.addListener(SWT.Selection, new OpenImportFileWindowListener(getParent(), EXERCISE_TYPE.WORD_FORMATION));

	MenuItem importCollocationItem = new MenuItem(importImportMenu, SWT.PUSH);
	importCollocationItem.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MAIN_IMPORT_MENU_CO));
	importCollocationItem.addListener(SWT.Selection, new OpenImportFileWindowListener(getParent(), EXERCISE_TYPE.COLLOCATION));

	MenuItem mergeDBItem = new MenuItem(importImportMenu, SWT.PUSH);
	mergeDBItem.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MAIN_IMPORT_MERGE_DB));
	mergeDBItem.addListener(SWT.Selection, new OpenImportFileWindowListener(getParent(), null));

	MenuItem importRawFormat = new MenuItem(importImportMenu, SWT.PUSH);
	importRawFormat.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MAIN_IMPORT_RAW_FORMAT));
	importRawFormat.addListener(SWT.Selection, new OpenImportRawFileWindowListener(getParent()));
    }

    private void buildHitMenu(Menu bar)
    {
	MenuItem hitMenu = new MenuItem(bar, SWT.CASCADE);
	hitMenu.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MAIN_HIT_MENU));

	Menu cleanHitsMenu = new Menu(wordBankDialog, SWT.DROP_DOWN);
	hitMenu.setMenu(cleanHitsMenu);

	MenuItem cleanHitsPhrasalVerbItem = new MenuItem(cleanHitsMenu, SWT.PUSH);
	cleanHitsPhrasalVerbItem.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MAIN_HIT_CLEAN_PV));
	cleanHitsPhrasalVerbItem.addListener(SWT.Selection, new CleanHitsListener(getParent(), EXERCISE_TYPE.PHRASAL_VERB));

	MenuItem cleanHitsWFItem = new MenuItem(cleanHitsMenu, SWT.PUSH);
	cleanHitsWFItem.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MAIN_HIT_CLEAN_WF));
	cleanHitsWFItem.addListener(SWT.Selection, new CleanHitsListener(getParent(), EXERCISE_TYPE.WORD_FORMATION));

	MenuItem cleanHitsCOItem = new MenuItem(cleanHitsMenu, SWT.PUSH);
	cleanHitsCOItem.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MAIN_HIT_CLEAN_CO));
	cleanHitsCOItem.addListener(SWT.Selection, new CleanHitsListener(getParent(), EXERCISE_TYPE.COLLOCATION));
    }

    private void buildAboutMenu(Menu bar)
    {
	MenuItem aboutMenuItem = new MenuItem(bar, SWT.CASCADE);
	aboutMenuItem.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MAIN_ABOUT_MENU));

	Menu aboutMenu = new Menu(wordBankDialog, SWT.DROP_DOWN);
	aboutMenuItem.setMenu(aboutMenu);

	MenuItem showAbout = new MenuItem(aboutMenu, SWT.PUSH);
	showAbout.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MAIN_ABOUT_MENU));
	showAbout.addListener(SWT.Selection, new OpenWindowListener(getParent(), WINDOW_TYPE.ABOUT_WINDOW, null));
    }

}
