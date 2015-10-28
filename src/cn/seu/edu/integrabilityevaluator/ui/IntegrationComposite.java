package cn.seu.edu.integrabilityevaluator.ui;

import java.io.IOException;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

public class IntegrationComposite extends Composite {
	
	
	private StackLayout rightCompositeSL = new StackLayout();
	private SubstitutabilityComposite changeabilityComposite;
	private ExtensibilityComposite extensibilityComposite;
	private CompatibilityComposite compatibilityComposite;
	private ProjectInfoComposite projectInfoComposite;
	private ExtensiMutiVerionShowComposite extensiMutiVerionShowComposite;
	private SubstitutabilityMutiVersionShowComposite changeMutiVersionShowComposite;
	private CompatibilityMutiVersionComposite compatibilityMutiVersionComposite;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public IntegrationComposite(Composite parent, int style) {
		super(parent, style);
		
		try {
			setLayout(new GridLayout(2, false));					
			Composite leftComposite = new Composite(this, SWT.NONE);
			FillLayout fl_leftComposite = new FillLayout(SWT.VERTICAL);
			fl_leftComposite.marginWidth = 5;
			fl_leftComposite.spacing = 10;
			fl_leftComposite.marginHeight = 10;
			leftComposite.setLayout(fl_leftComposite);
			GridData gd_leftComposite = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
			gd_leftComposite.widthHint = 152;
			gd_leftComposite.heightHint = 597;
			leftComposite.setLayoutData(gd_leftComposite);
			
			final Composite rightComposite = new Composite(this, SWT.NONE);
			rightComposite.setLayout(rightCompositeSL);
			GridData gd_rightComposite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_rightComposite.widthHint = 760;
			rightComposite.setLayoutData(gd_rightComposite);
			
			Composite composite = new Composite(parent, SWT.NONE);
			composite.setLayout(new GridLayout());							
						
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
			
			Button extensiMutiButton = new Button(leftComposite, SWT.WRAP);
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
		
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}

		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
