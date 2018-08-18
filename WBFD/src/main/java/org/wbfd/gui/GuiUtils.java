package org.wbfd.gui;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

public class GuiUtils
{
    public static void centerWindow(Shell parent, Shell window)
    {
	Monitor primary = parent.getDisplay().getPrimaryMonitor();
	Rectangle bounds = primary.getBounds();
	Rectangle rect = window.getBounds();
	int x = (bounds.x + (bounds.width - rect.width)) / 2;
	int y = (bounds.y + (bounds.height - rect.height)) / 2;
	window.setLocation(x, y);
    }
}
