package org.wbfd.gui;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.wbfd.Global;
import org.wbfd.entity.Collocation;
import org.wbfd.enums.LEVEL;
import org.wbfd.service.CollocationService;
import org.wbfd.service.impl.LocaleServiceImpl.TEXT;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class CollocationWindow extends BaseWindow
{

    protected Object result;
    protected Shell collocationDialog;
    private CollocationService collocationService;

    private Collocation currentCollocation;
    private Text txAnswer;

    private Label lbSentence1Phrase;
    private Label lbSentence2Example;
    private Label lblSentence3;
    private Label lbComments;

    private Button btNext;
    private Button btVerify;
    private Button btAnswer;

    public CollocationWindow(Shell parent, int style, LEVEL level)
    {
	super(parent, style);
	this.level = level;
	this.collocationService = Global.getBeanFactory().getCollocationService();
    }

    public Object open()
    {
	createContents();
	nextCollocation();
	collocationDialog.open();
	collocationDialog.layout();
	Display display = getParent().getDisplay();
	while (!collocationDialog.isDisposed())
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
	collocationDialog = new Shell(getParent(), getStyle());
	collocationDialog.setSize(617, 220);
	collocationDialog.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.CO_SCREEN_TITLE));
	GuiUtils.centerWindow(getParent(), collocationDialog);

	Group group = new Group(collocationDialog, SWT.NONE);
	group.setBounds(10, 10, 593, 148);

	lbSentence1Phrase = new Label(group, SWT.WRAP);
	lbSentence1Phrase.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	lbSentence1Phrase.setBounds(10, 10, 573, 30);
	lbSentence1Phrase.setText("Sentence1Phrase");

	lbSentence2Example = new Label(group, SWT.WRAP);
	lbSentence2Example.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	lbSentence2Example.setBounds(10, 46, 573, 30);
	lbSentence2Example.setText("Sentence2Example");

	lblSentence3 = new Label(group, SWT.WRAP);
	lblSentence3.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	lblSentence3.setBounds(10, 82, 573, 30);
	lblSentence3.setText("Sentence3");

	txAnswer = new Text(group, SWT.BORDER);
	txAnswer.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	txAnswer.setBounds(10, 118, 179, 18);

	lbComments = new Label(group, SWT.NONE);
	lbComments.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	lbComments.setBounds(195, 124, 388, 12);
	lbComments.setText("Comment");

	btNext = new Button(collocationDialog, SWT.NONE);
	btNext.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	btNext.setBounds(530, 164, 73, 21);
	btNext.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.BT_NEXT));
	btNext.addSelectionListener(new SelectionAdapter()
	{
	    public void widgetSelected(SelectionEvent e)
	    {
		nextCollocation();
	    }
	});

	btAnswer = new Button(collocationDialog, SWT.NONE);
	btAnswer.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	btAnswer.setBounds(10, 164, 133, 21);
	btAnswer.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.BT_GIVE_ANSWER));
	btAnswer.addSelectionListener(new SelectionAdapter()
	{
	    public void widgetSelected(SelectionEvent e)
	    {
		btVerify.setEnabled(false);
		lbComments.setForeground(collocationDialog.getDisplay().getSystemColor(SWT.COLOR_RED));
		lbComments.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MSG_ANSWER_IS) + currentCollocation.getKey());
	    }
	});

	btVerify = new Button(collocationDialog, SWT.NONE);
	btVerify.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	btVerify.setBounds(451, 164, 73, 21);
	btVerify.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.BT_VERIFY));
	btVerify.addSelectionListener(new SelectionAdapter()
	{
	    public void widgetSelected(SelectionEvent e)
	    {
		if (txAnswer.getText().equalsIgnoreCase(currentCollocation.getKey()) == true)
		{
		    currentCollocation.setHits(currentCollocation.getHits() + 1);
		    try
		    {
			collocationService.save(currentCollocation);
		    }
		    catch (SQLException exc)
		    {
		    }
		    lbComments.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MSG_OK));
		    lbComments.setForeground(collocationDialog.getDisplay().getSystemColor(SWT.COLOR_DARK_GREEN));

		}
		else
		{
		    lbComments.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MSG_KO));
		    lbComments.setForeground(collocationDialog.getDisplay().getSystemColor(SWT.COLOR_RED));
		}
	    }
	});
    }

    private void nextCollocation()
    {
	try
	{
	    currentCollocation = collocationService.getNextCollocation(level);
	    btVerify.setEnabled(true);
	    lbComments.setText("");
	    txAnswer.setText("");
	    lbComments.setForeground(collocationDialog.getDisplay().getSystemColor(SWT.COLOR_BLACK));

	    if (currentCollocation.getLevel() == LEVEL.CAE)
	    {
		nextCAECollocation();
	    }
	    else
	    {
		nextCPECollocation();
	    }
	}
	catch (SQLException exc)
	{
	    MsgBox.show(getParent(), SWT.ICON_ERROR | SWT.OK, Global.getBeanFactory().getLocaleService().getText(TEXT.CO_SCREEN_TITLE), "Error getting next collocation\n" + exc.getMessage());
	}
    }

    private void nextCAECollocation()
    {
	lbSentence1Phrase.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.CO_SCREEN_PHRASE) + " " + currentCollocation.getPhrase());
	lbSentence2Example.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.CO_SCREEN_EXAMPLE) + " " + currentCollocation.getExample());
	lblSentence3.setText("");
    }

    private void nextCPECollocation()
    {
	lbSentence1Phrase.setText(currentCollocation.getSentence1());
	lbSentence2Example.setText(currentCollocation.getSentence2());
	lblSentence3.setText(currentCollocation.getSentence3());
    }
}
