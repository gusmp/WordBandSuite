package org.wbfd.gui;

import java.sql.SQLException;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.wbfd.Global;
import org.wbfd.entity.PhrasalVerb;
import org.wbfd.enums.LEVEL;
import org.wbfd.service.PhrasalVerbService;
import org.wbfd.service.impl.LocaleServiceImpl.TEXT;
import org.eclipse.wb.swt.SWTResourceManager;

public class PhrasalVerbWindow extends BaseWindow
{
    protected Object result;
    protected Shell phrasalVerbsDialog;
    private PhrasalVerbService phrasalVerbService;

    private Label lbMeaning;
    private Label lbExample;
    private Label lbComments;
    private Label lbNumClues;

    private Text txAnswer;

    private Button btNext;
    private Button btVerify;
    private Button btAnswer;
    private Button btClue;

    private final Integer MAX_CLUES_PER_HIT = 2;

    private PhrasalVerb currentPhrasalVerb;

    public PhrasalVerbWindow(Shell parent, int style, LEVEL level)
    {
	super(parent, style);
	this.level = level;
	this.phrasalVerbService = Global.getBeanFactory().getPhrasalVerbService();
    }

    public Object open()
    {
	createContents();
	nextPhrasalVerb();
	phrasalVerbsDialog.open();
	phrasalVerbsDialog.layout();
	Display display = getParent().getDisplay();
	while (!phrasalVerbsDialog.isDisposed())
	{
	    if (!display.readAndDispatch())
	    {
		display.sleep();
	    }
	}
	return result;
    }

    private void nextPhrasalVerb()
    {
	try
	{
	    currentPhrasalVerb = phrasalVerbService.getNextPhrasalVerb(level);

	    lbMeaning.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.PV_SCREEN_MEANING) + currentPhrasalVerb.getMeaning().toLowerCase());
	    lbExample.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.PV_SCREEN_EXAMPLE) + currentPhrasalVerb.getExample().substring(0, 1).toUpperCase() + currentPhrasalVerb.getExample().substring(1, currentPhrasalVerb.getExample().length()).toLowerCase());

	    lbComments.setText("");
	    lbComments.setForeground(phrasalVerbsDialog.getDisplay().getSystemColor(SWT.COLOR_BLACK));

	    txAnswer.setText("");
	    btAnswer.setEnabled(true);
	    btVerify.setEnabled(true);
	    btNext.setEnabled(true);
	    btClue.setEnabled(true);
	    numberClues = 0;
	    lbNumClues.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MSG_NUMBER_CLUES) + numberClues + "/" + MAX_CLUES_PER_HIT);
	    lbNumClues.setForeground(phrasalVerbsDialog.getDisplay().getSystemColor(SWT.COLOR_BLACK));
	}
	catch (SQLException exc)
	{
	    MsgBox.show(getParent(), SWT.ICON_ERROR | SWT.OK, Global.getBeanFactory().getLocaleService().getText(TEXT.PV_SCREEN_TITLE), "Error getting next phrasal verb\n" + exc.getMessage());
	}
    }

    private void createContents()
    {
	phrasalVerbsDialog = new Shell(getParent(), getStyle());

	phrasalVerbsDialog.setSize(640, 186);
	phrasalVerbsDialog.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.PV_SCREEN_TITLE));
	GuiUtils.centerWindow(getParent(), phrasalVerbsDialog);

	Group group = new Group(phrasalVerbsDialog, SWT.NONE);
	group.setBounds(10, 10, 616, 112);

	lbMeaning = new Label(group, SWT.NONE);
	lbMeaning.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	lbMeaning.setBounds(10, 10, 596, 15);
	lbMeaning.setText("Meaning");

	lbExample = new Label(group, SWT.WRAP);
	lbExample.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	lbExample.setBounds(10, 29, 596, 30);
	lbExample.setText("Example");

	txAnswer = new Text(group, SWT.NONE);
	txAnswer.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	txAnswer.setBounds(10, 65, 179, 18);
	txAnswer.setText("");

	lbComments = new Label(group, SWT.NONE);
	lbComments.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	lbComments.setBounds(195, 65, 411, 18);
	lbComments.setText("Comments");

	lbNumClues = new Label(group, SWT.NONE);
	lbNumClues.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	lbNumClues.setBounds(10, 89, 596, 13);
	lbNumClues.setText("Number of clues");

	btNext = new Button(phrasalVerbsDialog, SWT.NONE);
	btNext.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	btNext.setBounds(553, 128, 73, 21);
	btNext.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.BT_NEXT));
	btNext.addSelectionListener(new SelectionAdapter()
	{
	    public void widgetSelected(SelectionEvent e)
	    {
		nextPhrasalVerb();
	    }
	});

	btVerify = new Button(phrasalVerbsDialog, SWT.NONE);
	btVerify.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	btVerify.setSelection(true);
	btVerify.setBounds(474, 128, 73, 21);
	btVerify.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.BT_VERIFY));
	btVerify.addSelectionListener(new SelectionAdapter()
	{
	    public void widgetSelected(SelectionEvent e)
	    {
		btClue.setEnabled(false);

		if (txAnswer.getText().equalsIgnoreCase(currentPhrasalVerb.getKey()) == true)
		{
		    btVerify.setEnabled(false);
		    if (numberClues <= MAX_CLUES_PER_HIT)
		    {
			currentPhrasalVerb.setHits(currentPhrasalVerb.getHits() + 1);
			try
			{
			    phrasalVerbService.save(currentPhrasalVerb);
			}
			catch (SQLException exc)
			{
			}
		    }
		    lbComments.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MSG_OK));
		    lbComments.setForeground(phrasalVerbsDialog.getDisplay().getSystemColor(SWT.COLOR_DARK_GREEN));
		}
		else
		{
		    lbComments.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MSG_KO));
		    lbComments.setForeground(phrasalVerbsDialog.getDisplay().getSystemColor(SWT.COLOR_RED));
		}
	    }
	});

	btAnswer = new Button(phrasalVerbsDialog, SWT.NONE);
	btAnswer.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	btAnswer.setBounds(10, 128, 127, 21);
	btAnswer.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.BT_GIVE_ANSWER));
	btAnswer.addSelectionListener(new SelectionAdapter()
	{
	    public void widgetSelected(SelectionEvent e)
	    {
		numberClues = Integer.MAX_VALUE;
		lbComments.setForeground(phrasalVerbsDialog.getDisplay().getSystemColor(SWT.COLOR_RED));
		lbComments.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MSG_ANSWER_IS) + currentPhrasalVerb.getKey());
		btClue.setEnabled(false);
		btVerify.setEnabled(false);
	    }
	});

	btClue = new Button(phrasalVerbsDialog, SWT.NONE);
	btClue.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	btClue.setBounds(141, 128, 108, 21);
	btClue.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.BT_GIVE_CLUE));
	btClue.addSelectionListener(new SelectionAdapter()
	{
	    public void widgetSelected(SelectionEvent e)
	    {
		getClue(txAnswer, currentPhrasalVerb.getKey(), lbNumClues, MAX_CLUES_PER_HIT);
	    }
	});
    }
}
