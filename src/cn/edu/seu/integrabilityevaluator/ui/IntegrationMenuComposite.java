package cn.edu.seu.integrabilityevaluator.ui;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.SWT;

import cn.edu.seu.integrabilityevaluator.uicomposite.CompatibilityComposite;
import cn.edu.seu.integrabilityevaluator.uicomposite.CompatibilityMutiVersionComposite;
import cn.edu.seu.integrabilityevaluator.uicomposite.ExtensiMutiVerionShowComposite;
import cn.edu.seu.integrabilityevaluator.uicomposite.ExtensibilityComposite;
import cn.edu.seu.integrabilityevaluator.uicomposite.ProjectInfoComposite;
import cn.edu.seu.integrabilityevaluator.uicomposite.SubstitutabilityComposite;
import cn.edu.seu.integrabilityevaluator.uicomposite.SubstitutabilityMutiVersionShowComposite;

import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.custom.CBanner;
import org.eclipse.wb.swt.SWTResourceManager;

public class IntegrationMenuComposite extends Composite {
	
	
	private StackLayout rightCompositeSL = new StackLayout();
	private SubstitutabilityComposite changeabilityComposite;
	private ExtensibilityComposite extensibilityComposite;
	private CompatibilityComposite compatibilityComposite;
	private ProjectInfoComposite projectInfoComposite;
	private ExtensiMutiVerionShowComposite extensiMutiVerionShowComposite;
	private SubstitutabilityMutiVersionShowComposite changeMutiVersionShowComposite;
	private CompatibilityMutiVersionComposite compatibilityMutiVersionComposite;
	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) {
		showGUI();
	}
	
	/**
	* Overriding checkSubclass allows this class to extend org.eclipse.swt.widgets.Composite
	*/	
	protected void checkSubclass() {
	}
	
	/**
	* Auto-generated method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		
		IntegrationMenuComposite inst = new IntegrationMenuComposite(shell, SWT.NULL);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if(size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
		} else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	public IntegrationMenuComposite(Composite parent, int style) {
		super(parent, style);
		setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
	
		try {
					
			setLayout(new GridLayout(1, false));					
			Composite composite = new Composite(parent, SWT.NONE);
			composite.setLayout(new GridLayout());
			
			
			Composite RadioComposite = new Composite(this, SWT.NONE);
			RadioComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
			GridLayout gl_RadioComposite = new GridLayout(4, false);
			RadioComposite.setLayout(gl_RadioComposite);
			GridData gd_RadioComposite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_RadioComposite.heightHint = 53;
			gd_RadioComposite.widthHint = 848;
			RadioComposite.setLayoutData(gd_RadioComposite);
			
			final Button projectImportButton = new Button(RadioComposite, SWT.RADIO);
			GridData gd_projectImportButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_projectImportButton.widthHint = 200;
			projectImportButton.setLayoutData(gd_projectImportButton);
			projectImportButton.setAlignment(SWT.CENTER);
			projectImportButton.setSelection(true);
			projectImportButton.setText("Project Import");
			//GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(projectImportButton);
			
			final Button btnRadioButton_1 = new Button(RadioComposite, SWT.RADIO);
			GridData gd_btnRadioButton_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_btnRadioButton_1.widthHint = 200;
			btnRadioButton_1.setLayoutData(gd_btnRadioButton_1);
			btnRadioButton_1.setAlignment(SWT.CENTER);
			btnRadioButton_1.setText("Measurement of Extensibility");
			
			final Button btnRadioButton_2 = new Button(RadioComposite, SWT.RADIO);
			GridData gd_btnRadioButton_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_btnRadioButton_2.widthHint = 200;
			btnRadioButton_2.setLayoutData(gd_btnRadioButton_2);
			btnRadioButton_2.setAlignment(SWT.CENTER);
			btnRadioButton_2.setText("Measurement of Substitutability");
			
			final Button btnRadioButton_3 = new Button(RadioComposite, SWT.RADIO);
			btnRadioButton_3.setText("Measurement of Substitutability");
			
			
			final Button btnRadioButton_5 = new Button(RadioComposite, SWT.RADIO | SWT.CENTER | SWT.WRAP);
			GridData gd_btnRadioButton_5 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_btnRadioButton_5.widthHint = 200;
			gd_btnRadioButton_5.heightHint = 20;
			btnRadioButton_5.setLayoutData(gd_btnRadioButton_5);
			//btnRadioButton_5.setText("Evaluation of substitutability \r\nunder evoluation");
			btnRadioButton_5.setText("Evaluation of substitutability");
			
			final Button btnRadioButton_6 = new Button(RadioComposite, SWT.RADIO  | SWT.CENTER | SWT.WRAP );
			GridData gd_btnRadioButton_6 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_btnRadioButton_6.widthHint = 200;
			gd_btnRadioButton_6.heightHint = 20;
			btnRadioButton_6.setLayoutData(gd_btnRadioButton_6);
			//btnRadioButton_6.setText("Evaluation of compatibility \r\nunder evoluation");
			btnRadioButton_6.setText("Evaluation of compatibility");

			
			final Button btnRadioButton_4 = new Button(RadioComposite, SWT.RADIO | SWT.CENTER | SWT.WRAP);
			GridData gd_btnRadioButton_4 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_btnRadioButton_4.widthHint = 200;
			gd_btnRadioButton_4.heightHint = 20;
			btnRadioButton_4.setLayoutData(gd_btnRadioButton_4);
			//btnRadioButton_4.setText("Evaluation of extensibility under evoluation");
			btnRadioButton_4.setText("Evaluation of extensibility");

			new Label(RadioComposite, SWT.NONE);
			//new Label(RadioComposite, SWT.NONE);
			
			

			
			
			final Composite rightComposite = new Composite(this, SWT.NONE);
			rightComposite.setLayout(rightCompositeSL);
			GridData gd_rightComposite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_rightComposite.widthHint = 847;
			gd_rightComposite.heightHint = 628;
			rightComposite.setLayoutData(gd_rightComposite);
			
						
						
						
						
						
			compatibilityComposite = new CompatibilityComposite(rightComposite, SWT.NONE);
			extensibilityComposite = new ExtensibilityComposite(rightComposite, SWT.NONE);
			changeabilityComposite = new SubstitutabilityComposite(rightComposite, SWT.NONE);
			projectInfoComposite = new ProjectInfoComposite(rightComposite, SWT.NONE);
			changeMutiVersionShowComposite = new SubstitutabilityMutiVersionShowComposite(rightComposite, SWT.NONE);
			extensiMutiVerionShowComposite = new ExtensiMutiVerionShowComposite(rightComposite, SWT.NONE);
			compatibilityMutiVersionComposite = new CompatibilityMutiVersionComposite(rightComposite, SWT.NONE);
			rightCompositeSL.topControl = projectInfoComposite;
			
			
			Listener listener =new Listener() {
				
				@Override
					public void handleEvent(Event event){
					
		
						if(event.widget == projectImportButton){
							rightCompositeSL.topControl = projectInfoComposite;
						}else if (event.widget == btnRadioButton_1) {	
							rightCompositeSL.topControl = extensibilityComposite;
						}else if (event.widget == btnRadioButton_2) {	
							rightCompositeSL.topControl = changeabilityComposite;
						}else if (event.widget == btnRadioButton_3) {	
							rightCompositeSL.topControl = compatibilityComposite;
						}else if (event.widget == btnRadioButton_4) {	
							rightCompositeSL.topControl = extensiMutiVerionShowComposite;
						}else if (event.widget == btnRadioButton_5) {	
							rightCompositeSL.topControl = changeMutiVersionShowComposite;
						}else if (event.widget == btnRadioButton_6) {	
							rightCompositeSL.topControl = compatibilityMutiVersionComposite;
						}						
						rightComposite.layout();
					}
			
			
				};

			
			projectImportButton.addListener(SWT.Selection, listener);
			btnRadioButton_1.addListener(SWT.Selection, listener);
			btnRadioButton_2.addListener(SWT.Selection, listener);
			btnRadioButton_3.addListener(SWT.Selection, listener);
			btnRadioButton_4.addListener(SWT.Selection, listener);
			btnRadioButton_5.addListener(SWT.Selection, listener);
			btnRadioButton_6.addListener(SWT.Selection, listener);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
