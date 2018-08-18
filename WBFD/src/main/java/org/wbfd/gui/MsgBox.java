package org.wbfd.gui;

import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class MsgBox
{
    public static int show(Shell parent, int buttons, String title, String msg)
    {
	// SWT.ICON_ERROR | SWT.OK
	MessageBox messageBox = new MessageBox(parent, buttons);
	messageBox.setText(title);
	messageBox.setMessage(msg);
	return (messageBox.open());
    }
}
