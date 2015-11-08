package mainui;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.eclipse.jdt.internal.compiler.batch.Main;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
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

import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

public class WelcomeDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Image welcomeImage = null;

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
		shell = new Shell(getParent(), SWT.SHELL_TRIM);
		shell.setSize(647, 341);
		shell.setLayout(new FormLayout());
		
		final Composite welcomeComposite = new Composite(shell ,SWT.NONE); 
		FormData fd_welcomeComposite = new FormData();
		fd_welcomeComposite.bottom = new FormAttachment(100, 0);
		fd_welcomeComposite.right = new FormAttachment(100, 0);
		fd_welcomeComposite.top = new FormAttachment(0);
		fd_welcomeComposite.left = new FormAttachment(0);
		welcomeComposite.setLayoutData(fd_welcomeComposite);
		//Image welcome = new Image(Display.getDefault(), "images/welcome.jpg"); 
		welcomeImage = new Image(Display.getCurrent(),getClass().getResourceAsStream("/images/welcome3.jpg")); 
		welcomeComposite.setBackgroundImage(welcomeImage);
		
		welcomeComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		welcomeComposite.setLayout(new FormLayout());
		
		final Composite buttonComposite = new Composite(welcomeComposite, SWT.NONE);
		FormData fd_buttonComposite = new FormData();
		fd_buttonComposite.right = new FormAttachment(100,-10);
		fd_buttonComposite.top = new FormAttachment(0, 10);
		fd_buttonComposite.left = new FormAttachment(0, 10);
		buttonComposite.setLayoutData(fd_buttonComposite);
		FillLayout fl_buttonComposite = new FillLayout(SWT.HORIZONTAL);
		fl_buttonComposite.spacing = 10;
		buttonComposite.setLayout(fl_buttonComposite);
		
		Button btnNewButton = new Button(buttonComposite, SWT.NONE);
		btnNewButton.setText("New Button");
		
		Button btnNewButton_1 = new Button(buttonComposite, SWT.NONE);
		btnNewButton_1.setText("New Button");
		
		final Button complexityButton = new Button(buttonComposite, SWT.NONE);
		complexityButton.setText("Complexity Eval.");
		
		final Button integrationEvalButton = new Button(buttonComposite, SWT.NONE);
		integrationEvalButton.setText("Integration Eval.");
		
		final Button closeButton = new Button(buttonComposite, SWT.NONE);
		closeButton.setText("Close");
		
		/*final Label lblNewLabel = new Label(welcomeComposite, SWT.RIGHT);
		fd_buttonComposite.bottom = new FormAttachment(lblNewLabel, -24);
		fd_buttonComposite.top = new FormAttachment(lblNewLabel, -51, SWT.TOP);
		FormData fd_lblNewLabel = new FormData();
		fd_lblNewLabel.right = new FormAttachment(100, -10);
		fd_lblNewLabel.bottom = new FormAttachment(100, -10);
		fd_lblNewLabel.left = new FormAttachment(0, 5);
		lblNewLabel.setLayoutData(fd_lblNewLabel);
		lblNewLabel.setText("©Southeast University Institute of Software Engineering");*/
		
		
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
						//shell.setVisible(false);
						
						
						//shell.setVisible(true);						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				
			}
		};
		
		closeButton.addListener(SWT.Selection, listener);
		integrationEvalButton.addListener(SWT.Selection, listener);
		complexityButton.addListener(SWT.Selection, listener);
		
		welcomeComposite.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				// TODO Auto-generated method stub
				 if(welcomeImage != null)
			     {
			            int width = welcomeComposite.getSize().x;
			            int height = welcomeComposite.getSize().y;
			            int imageWidth = welcomeImage.getImageData().width;
			            int imageHeight = welcomeImage.getImageData().height;
			            
			            ImageData data = welcomeImage.getImageData().scaledTo(width, height);
			            e.gc.drawImage(new Image(e.display, data), 0, 0); 
			            //welcomeComposite.setBackgroundImage(new Image(e.display, data));
			            //welcomeComposite.setBackgroundMode(SWT.INHERIT_FORCE);
			     }
				
				 welcomeComposite.layout();
				 //buttonComposite.layout();
				 //lblNewLabel.redraw();
			}
		});		
		
	}

	
	
	
	
}
