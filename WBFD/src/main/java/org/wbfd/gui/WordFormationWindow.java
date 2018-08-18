package org.wbfd.gui;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.wbfd.Global;
import org.wbfd.entity.WordFormation;
import org.wbfd.enums.LEVEL;
import org.wbfd.service.WordFormationService;
import org.wbfd.service.impl.LocaleServiceImpl.TEXT;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;

public class WordFormationWindow extends BaseWindow
{

    protected Object result;
    protected Shell wordFormationDialog;
    private WordFormationService wordFormationService;

    private WordFormation currentWordFormation;

    private Label lbWordRoot;

    private Label lbSentence1;
    private Text txSentenceKey1;
    private Label lbSentenceKey1;

    private Label lbSentence2;
    private Text txSentenceKey2;
    private Label lbSentenceKey2;

    private Label lbSentence3;
    private Text txSentenceKey3;
    private Label lbSentenceKey3;

    private Button btNext;
    private Button btVerify;
    private Button btAnswer;

    public WordFormationWindow(Shell parent, int style, LEVEL level)
    {
	super(parent, style);
	this.level = level;
	this.wordFormationService = Global.getBeanFactory().getWordFormationService();
    }

    public Object open()
    {
	createContents();
	nextWordFormation();
	wordFormationDialog.open();
	wordFormationDialog.layout();
	Display display = getParent().getDisplay();
	while (!wordFormationDialog.isDisposed())
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
	wordFormationDialog = new Shell(getParent(), getStyle());

	wordFormationDialog.setSize(686, 287);
	wordFormationDialog.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.WF_SCREEN_TITLE));
	GuiUtils.centerWindow(getParent(), wordFormationDialog);

	Group group = new Group(wordFormationDialog, SWT.NONE);
	group.setBounds(10, 10, 662, 213);

	lbWordRoot = new Label(group, SWT.NONE);
	lbWordRoot.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	lbWordRoot.setBounds(10, 10, 406, 12);
	lbWordRoot.setText("WordRoot");

	lbSentence1 = new Label(group, SWT.WRAP);
	lbSentence1.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	lbSentence1.setBounds(10, 28, 642, 30);
	lbSentence1.setText("Sentence1");

	txSentenceKey1 = new Text(group, SWT.BORDER);
	txSentenceKey1.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	txSentenceKey1.setBounds(10, 64, 150, 18);

	lbSentenceKey1 = new Label(group, SWT.NONE);
	lbSentenceKey1.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	lbSentenceKey1.setBounds(166, 70, 486, 14);
	lbSentenceKey1.setText("SentenceKey1");

	lbSentence2 = new Label(group, SWT.WRAP);
	lbSentence2.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	lbSentence2.setBounds(10, 88, 642, 30);
	lbSentence2.setText("Sentence2");

	txSentenceKey2 = new Text(group, SWT.BORDER);
	txSentenceKey2.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	txSentenceKey2.setBounds(10, 124, 150, 18);

	lbSentenceKey2 = new Label(group, SWT.NONE);
	lbSentenceKey2.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	lbSentenceKey2.setBounds(166, 130, 486, 14);
	lbSentenceKey2.setText("SentenceKey2");

	lbSentence3 = new Label(group, SWT.WRAP);
	lbSentence3.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	lbSentence3.setBounds(10, 148, 642, 30);
	lbSentence3.setText("Sentence3");

	txSentenceKey3 = new Text(group, SWT.BORDER);
	txSentenceKey3.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	txSentenceKey3.setBounds(10, 184, 150, 18);

	lbSentenceKey3 = new Label(group, SWT.NONE);
	lbSentenceKey3.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	lbSentenceKey3.setBounds(166, 190, 488, 14);
	lbSentenceKey3.setText("SentenceKey3");

	btNext = new Button(wordFormationDialog, SWT.NONE);
	btNext.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	btNext.setBounds(599, 229, 73, 21);
	btNext.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.BT_NEXT));
	btNext.addSelectionListener(new SelectionAdapter()
	{
	    public void widgetSelected(SelectionEvent e)
	    {
		nextWordFormation();
	    }
	});

	btVerify = new Button(wordFormationDialog, SWT.NONE);
	btVerify.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	btVerify.setBounds(518, 229, 73, 21);
	btVerify.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.BT_VERIFY));
	btVerify.addSelectionListener(new SelectionAdapter()
	{
	    public void widgetSelected(SelectionEvent e)
	    {
		Boolean validation1 = validateAnswer(txSentenceKey1.getText(), currentWordFormation.getSentenceKey1(), lbSentenceKey1);
		Boolean validation2 = validateAnswer(txSentenceKey2.getText(), currentWordFormation.getSentenceKey2(), lbSentenceKey2);
		Boolean validation3 = validateAnswer(txSentenceKey3.getText(), currentWordFormation.getSentenceKey3(), lbSentenceKey3);

		if ((validation1 == true) && (validation2 == true) && (validation3 == true))
		{
		    btVerify.setEnabled(false);
		    currentWordFormation.setHits(currentWordFormation.getHits() + 1);
		    try
		    {
			wordFormationService.save(currentWordFormation);
		    }
		    catch (SQLException exc)
		    {
		    }
		}
	    }
	});

	btAnswer = new Button(wordFormationDialog, SWT.NONE);
	btAnswer.setFont(SWTResourceManager.getFont("Arial", 9, SWT.NORMAL));
	btAnswer.setBounds(10, 229, 155, 21);
	btAnswer.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.BT_GIVE_ANSWER));
	btAnswer.addSelectionListener(new SelectionAdapter()
	{
	    public void widgetSelected(SelectionEvent e)
	    {
		lbSentenceKey1.setForeground(wordFormationDialog.getDisplay().getSystemColor(SWT.COLOR_RED));
		lbSentenceKey1.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MSG_ANSWER_IS) + " " + currentWordFormation.getSentenceKey1());

		lbSentenceKey2.setForeground(wordFormationDialog.getDisplay().getSystemColor(SWT.COLOR_RED));
		lbSentenceKey2.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MSG_ANSWER_IS) + " " + currentWordFormation.getSentenceKey2());

		lbSentenceKey3.setForeground(wordFormationDialog.getDisplay().getSystemColor(SWT.COLOR_RED));
		lbSentenceKey3.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MSG_ANSWER_IS) + " " + currentWordFormation.getSentenceKey3());

		btVerify.setEnabled(false);
	    }
	});
    }

    private Boolean validateAnswer(String answer, String key, Label lbSentenceKey)
    {
	Boolean ret;

	if (answer.equalsIgnoreCase(key) == true)
	{
	    lbSentenceKey.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MSG_OK));
	    lbSentenceKey.setForeground(wordFormationDialog.getDisplay().getSystemColor(SWT.COLOR_DARK_GREEN));

	    ret = true;
	}
	else
	{
	    lbSentenceKey.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MSG_KO));
	    lbSentenceKey.setForeground(wordFormationDialog.getDisplay().getSystemColor(SWT.COLOR_RED));
	    ret = false;
	}

	return (ret);
    }

    private void nextWordFormation()
    {
	try
	{
	    currentWordFormation = wordFormationService.getNextWordFormation(level);

	    lbWordRoot.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.WF_SCREEN_WORD_ROOT) + " " + currentWordFormation.getWordRoot().toUpperCase());

	    lbSentence1.setText(currentWordFormation.getSentence1());
	    txSentenceKey1.setText("");
	    lbSentenceKey1.setText("");
	    lbSentenceKey1.setForeground(wordFormationDialog.getDisplay().getSystemColor(SWT.COLOR_BLACK));

	    lbSentence2.setText(currentWordFormation.getSentence2());
	    txSentenceKey2.setText("");
	    lbSentenceKey2.setText("");
	    lbSentenceKey2.setForeground(wordFormationDialog.getDisplay().getSystemColor(SWT.COLOR_BLACK));

	    lbSentence3.setText(currentWordFormation.getSentence3());
	    txSentenceKey3.setText("");
	    lbSentenceKey3.setText("");
	    lbSentenceKey3.setForeground(wordFormationDialog.getDisplay().getSystemColor(SWT.COLOR_BLACK));

	    btVerify.setEnabled(true);

	}
	catch (SQLException exc)
	{
	    MsgBox.show(getParent(), SWT.ICON_ERROR | SWT.OK, Global.getBeanFactory().getLocaleService().getText(TEXT.WF_SCREEN_TITLE), "Error getting next word formation\n" + exc.getMessage());
	}
    }
}
