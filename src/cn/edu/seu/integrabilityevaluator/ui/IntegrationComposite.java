package cn.edu.seu.integrabilityevaluator.ui;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
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

public class IntegrationComposite extends Composite {
	
	protected Object result;
	protected Shell shlCiet;
	
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
		IntegrationComposite inst = new IntegrationComposite(shell, SWT.NULL);
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

	public IntegrationComposite(Composite parent, int style) {
		super(parent, style);
	
		try {
			/*FormLayout thisLayout = new FormLayout();
			this.setLayout(thisLayout);
			this.layout();			
		
			Composite composite = new Composite(parent, SWT.NONE);
			composite.setLayout(new FormLayout());
							
			Composite leftComposite = new Composite(composite, SWT.NONE);
			FormData fd_leftComposite = new FormData();
			fd_leftComposite.left = new FormAttachment(0, 5);
			fd_leftComposite.bottom = new FormAttachment(100, -108);
			fd_leftComposite.top = new FormAttachment(0, 5);
			leftComposite.setLayoutData(fd_leftComposite);
			leftComposite.setLayout(new FillLayout(SWT.VERTICAL));*/
			
			
			setLayout(new GridLayout(2, false));					
			Composite leftComposite = new Composite(this, SWT.WRAP);
			FillLayout fl_leftComposite = new FillLayout(SWT.VERTICAL);
			fl_leftComposite.marginWidth = 5;
			fl_leftComposite.spacing = 10;
			fl_leftComposite.marginHeight = 10;
			leftComposite.setLayout(fl_leftComposite);
			GridData gd_leftComposite = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
			gd_leftComposite.widthHint = 185;
			gd_leftComposite.heightHint = 597;
			leftComposite.setLayoutData(gd_leftComposite);
			
			final Composite rightComposite = new Composite(this, SWT.NONE);
			rightComposite.setLayout(rightCompositeSL);
			GridData gd_rightComposite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_rightComposite.widthHint = 760;
			rightComposite.setLayoutData(gd_rightComposite);
			
			Composite composite = new Composite(parent, SWT.NONE);
			composite.setLayout(new GridLayout());
			
				
			/*final Composite rightComposite = new Composite(composite, SWT.NONE);
			fd_leftComposite.right = new FormAttachment(100, -734);
			FormData fd_rightComposite = new FormData();
			fd_rightComposite.left = new FormAttachment(leftComposite, 30);
			fd_rightComposite.right = new FormAttachment(100);
			fd_rightComposite.top = new FormAttachment(0, 10);
			fd_rightComposite.bottom = new FormAttachment(100, -28);
			rightComposite.setLayoutData(fd_rightComposite);
			rightComposite.setLayout(rightCompositeSL);*/
			
			Button projectInfoButton = new Button(leftComposite, SWT.NONE);
			
			projectInfoButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					rightCompositeSL.topControl = projectInfoComposite;
					/*Color color = new Color(null, 240, 0, 0); 
					projectInfoButton.setBackground(color);*/
					
					rightComposite.layout();
				}
			});
			//projectInfoButton.setText("\u9879\u76EE\u4FE1\u606F");
			projectInfoButton.setText("Project information");
			
			Button extensionButton = new Button(leftComposite, SWT.NONE);
			extensionButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					rightCompositeSL.topControl = extensibilityComposite;
					extensibilityComposite.reloadProject();
					rightComposite.layout();				
				}
			});
			//extensionButton.setText("\u53EF\u6269\u5C55\u6027\u5EA6\u91CF");
			extensionButton.setText("Extensibility measurement");
			
			Button changeButton = new Button(leftComposite, SWT.NONE);
			changeButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					rightCompositeSL.topControl = changeabilityComposite;
					changeabilityComposite.reloadProject();
					rightComposite.layout();
				}
			});
			changeButton.setText("Substitutability measurement");
			
			Button compatibilityButton = new Button(leftComposite, SWT.NONE);
			compatibilityButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					rightCompositeSL.topControl = compatibilityComposite;
					rightComposite.layout();
				}
			});
			compatibilityButton.setText("Compatibility measurement");
			
			Button extensiMutiButton = new Button(leftComposite, SWT.FLAT | SWT.WRAP
					| SWT.CENTER);
			extensiMutiButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					rightCompositeSL.topControl = extensiMutiVerionShowComposite;
					extensiMutiVerionShowComposite.reloadProject();
					//rightCompositeSL.topControl = diffComposite;
					rightComposite.layout();
				}
			});
			extensiMutiButton.setText("Evaluation of extensibility \r\nunder evoluation");		
			
			Button changeMutiButton = new Button(leftComposite, SWT.WRAP);
			changeMutiButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					rightCompositeSL.topControl = changeMutiVersionShowComposite;
					changeMutiVersionShowComposite.reloadProject();
					rightComposite.layout();
				}
			});
			changeMutiButton.setText("Evaluation of substitutability \r\nunder evoluation");
			
			Button compatibilityMutibutton = new Button(leftComposite, SWT.WRAP);
			compatibilityMutibutton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					rightCompositeSL.topControl = compatibilityMutiVersionComposite;
					rightComposite.layout();
				}
			});
			compatibilityMutibutton.setText("Evaluation of compatibility \r\nunder evoluation");
				
			compatibilityComposite = new CompatibilityComposite(rightComposite, SWT.NONE);
			extensibilityComposite = new ExtensibilityComposite(rightComposite, SWT.NONE);
			changeabilityComposite = new SubstitutabilityComposite(rightComposite, SWT.NONE);
			projectInfoComposite = new ProjectInfoComposite(rightComposite, SWT.NONE);
			changeMutiVersionShowComposite = new SubstitutabilityMutiVersionShowComposite(rightComposite, SWT.NONE);
			extensiMutiVerionShowComposite = new ExtensiMutiVerionShowComposite(rightComposite, SWT.NONE);
			compatibilityMutiVersionComposite = new CompatibilityMutiVersionComposite(rightComposite, SWT.NONE);
			rightCompositeSL.topControl = projectInfoComposite;
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
