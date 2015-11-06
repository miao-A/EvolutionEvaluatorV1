package mainui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class RunSystem {
	
	protected Object result;
	protected static Shell shlCiet;
	
	
	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);			
			WelcomeDialog welcomeDialog = new WelcomeDialog(shell, SWT.NULL);
			welcomeDialog.open();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
