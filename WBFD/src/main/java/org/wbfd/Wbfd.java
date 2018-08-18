package org.wbfd;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.wbfd.gui.MainWindow;

public class Wbfd
{

    public static void main(String[] args) throws SQLException, ClassNotFoundException
    {
	Display display = new Display();
	Shell shell = new Shell(display);

	MainWindow mainWindow = new MainWindow(shell, SWT.APPLICATION_MODAL);
	mainWindow.open();
	while (!shell.isDisposed())
	{
	    if (!display.readAndDispatch())
		display.sleep();
	}
	display.dispose();
    }
}
