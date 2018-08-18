package org.wbfd.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.wbfd.Global;
import org.wbfd.enums.LEVEL;
import org.wbfd.service.impl.LocaleServiceImpl.TEXT;

public abstract class BaseWindow extends Dialog
{
    protected LEVEL level;
    protected Integer numberClues;

    public BaseWindow(Shell parent, int style)
    {
	super(parent, style);
    }

    public void getClue(Text txAnswer, String key, Label lbNumClues, Integer MAX_CLUES_PER_HIT)
    {
	Integer lastCorrectLetter = 0;
	for (int i = 0; i < txAnswer.getText().length(); i++)
	{
	    String rightLetter = key.substring(i, i + 1);
	    String candidateLetter = txAnswer.getText().substring(i, i + 1);
	    if (rightLetter.equalsIgnoreCase(candidateLetter) == false)
	    {
		break;
	    }
	    lastCorrectLetter = i + 1;
	}

	if (lastCorrectLetter < key.length())
	{
	    txAnswer.setText(key.substring(0, lastCorrectLetter + 1));
	    numberClues++;
	}

	if (numberClues == MAX_CLUES_PER_HIT)
	{
	    lbNumClues.setForeground(getParent().getDisplay().getSystemColor(SWT.COLOR_RED));
	    lbNumClues.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MSG_NUMBER_CLUES) + numberClues + "/" + MAX_CLUES_PER_HIT + " " + Global.getBeanFactory().getLocaleService().getText(TEXT.MSG_NUMBER_CLUES_WARNING));
	}
	else if (numberClues > MAX_CLUES_PER_HIT)
	{
	    lbNumClues.setForeground(getParent().getDisplay().getSystemColor(SWT.COLOR_RED));
	    lbNumClues.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MSG_NUMBER_CLUES) + numberClues + "/" + MAX_CLUES_PER_HIT + " " + Global.getBeanFactory().getLocaleService().getText(TEXT.MSG_NUMBER_CLUES_NO_HIT));
	}
	else
	{
	    lbNumClues.setText(Global.getBeanFactory().getLocaleService().getText(TEXT.MSG_NUMBER_CLUES) + numberClues + "/" + MAX_CLUES_PER_HIT);
	}
    }

}
