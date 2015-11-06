package mainui;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.eclipse.jdt.internal.compiler.batch.Main;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;


import cn.edu.seu.integrabilityevaluator.ui.IntegrationApp;
import cn.edu.seu.integrabilityevaluator.ui.IntergrationDialog;

public class WelcomeDialog extends Dialog {

	protected Object result;
	protected Shell shell;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public WelcomeDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.NONE);
		shell.setSize(632, 303);
		//shell.setText(getText());		
		
		shell.setLayout(new FillLayout()); 
		
		Composite ComplexityButton = new Composite(shell ,SWT.NONE); 
		ComplexityButton.setLayout(new GridLayout(1,false)); 
		Image welcome = new Image(Display.getDefault(), "images/welcome.jpg"); 
		ComplexityButton.setBackgroundImage(welcome);
		
		ComplexityButton.setBackgroundMode(SWT.INHERIT_DEFAULT);

		new Label(ComplexityButton, SWT.NONE);
		new Label(ComplexityButton, SWT.NONE);
		new Label(ComplexityButton, SWT.NONE);
		new Label(ComplexityButton, SWT.NONE);
		new Label(ComplexityButton, SWT.NONE);
		new Label(ComplexityButton, SWT.NONE);
		new Label(ComplexityButton, SWT.NONE);
		new Label(ComplexityButton, SWT.NONE);
		new Label(ComplexityButton, SWT.NONE);
		new Label(ComplexityButton, SWT.NONE);
		
		Composite composite = new Composite(ComplexityButton, SWT.NONE);
		GridData gd_composite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite.widthHint = 614;
		composite.setLayoutData(gd_composite);
		FillLayout fl_composite = new FillLayout(SWT.HORIZONTAL);
		fl_composite.spacing = 10;
		composite.setLayout(fl_composite);
		
		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.setText("New Button");
		
		Button btnNewButton_1 = new Button(composite, SWT.NONE);
		btnNewButton_1.setText("New Button");
		
		final Button complexityButton = new Button(composite, SWT.NONE);
		complexityButton.setText("Complexity Eval.");
		
		final Button integrationEvalButton = new Button(composite, SWT.NONE);
		integrationEvalButton.setText("Integration Eval.");
		
		final Button closeButton = new Button(composite, SWT.NONE);
		closeButton.setText("Close");
		new Label(ComplexityButton, SWT.NONE);
		
		Label lblNewLabel = new Label(ComplexityButton, SWT.RIGHT);
		GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel.widthHint = 614;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		lblNewLabel.setText("©Southeast University Institute of Software Engineering");
		
		
		Listener listener = new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				if(event.widget == closeButton){
					//shell.close();
					shell.dispose();
				}else if (event.widget == integrationEvalButton) {
					try {
						shell.setVisible(false);
						IntegrationApp window = new IntegrationApp();
						//IntergrationDialog window  = new IntergrationDialog(shell, SWT.NONE);
						window.open();
						shell.setVisible(true);						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if (event.widget == complexityButton) {
					try {
						shell.setVisible(false);
						MainUI window2 = new MainUI();
						//IntergrationDialog window  = new IntergrationDialog(shell, SWT.NONE);
						window2.open();
						shell.setVisible(true);						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				
			}
		};
		
		closeButton.addListener(SWT.Selection, listener);
		integrationEvalButton.addListener(SWT.Selection, listener);
		complexityButton.addListener(SWT.Selection, listener);
	}

}
