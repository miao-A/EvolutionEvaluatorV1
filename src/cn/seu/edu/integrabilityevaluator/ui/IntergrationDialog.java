package cn.seu.edu.integrabilityevaluator.ui;

import java.io.IOException;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;


public class IntergrationDialog extends Dialog {

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
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public IntergrationDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 * @throws IOException 
	 */
	public Object open() throws IOException {
		createContents();
		shlCiet.open();
		shlCiet.layout();
		Display display = getParent().getDisplay();
		while (!shlCiet.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 * @throws IOException 
	 */
	private void createContents() throws IOException {
		shlCiet = new Shell(getParent(), SWT.DIALOG_TRIM);
		shlCiet.setSize(967, 641);
		shlCiet.setText("IntegrabilityEvaluator");
		shlCiet.setLayout(new FormLayout());	
		
		Composite composite = new Composite(shlCiet, SWT.NONE);
		composite.setLayout(new FormLayout());
		
		IntegrationComposite intergrationComposite = new IntegrationComposite(composite , SWT.NONE);
		/*intergrationComposite.setLayout(new FormLayout());
		*/
		/*Composite composite = new Composite(shlCiet, SWT.NONE);
		composite.setLayout(new FormLayout());
		
		Composite leftComposite = new Composite(composite, SWT.NONE);
		FormData fd_leftComposite = new FormData();
		fd_leftComposite.left = new FormAttachment(0, 5);
		fd_leftComposite.bottom = new FormAttachment(100, -108);
		fd_leftComposite.top = new FormAttachment(0, 5);
		leftComposite.setLayoutData(fd_leftComposite);
		leftComposite.setLayout(new FillLayout(SWT.VERTICAL));
		
		
		final Composite rightComposite = new Composite(composite, SWT.NONE);
		fd_leftComposite.right = new FormAttachment(100, -763);
		FormData fd_rightComposite = new FormData();
		fd_rightComposite.left = new FormAttachment(leftComposite, 22);
		fd_rightComposite.right = new FormAttachment(100, -10);
		fd_rightComposite.top = new FormAttachment(0, 10);
		fd_rightComposite.bottom = new FormAttachment(100, -10);
		rightComposite.setLayoutData(fd_rightComposite);
		rightComposite.setLayout(rightCompositeSL);
		
		Button projectInfoButton = new Button(leftComposite, SWT.NONE);
		
		projectInfoButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				rightCompositeSL.topControl = projectInfoComposite;
				Color color = new Color(null, 240, 0, 0); 
				projectInfoButton.setBackground(color);
				
				rightComposite.layout();
			}
		});
		projectInfoButton.setText("\u9879\u76EE\u4FE1\u606F");
		
		Button extensionButton = new Button(leftComposite, SWT.NONE);
		extensionButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				rightCompositeSL.topControl = extensibilityComposite;
				extensibilityComposite.reloadProject();
				rightComposite.layout();				
			}
		});
		extensionButton.setText("\u53EF\u6269\u5C55\u6027\u5EA6\u91CF");
		
		Button changeButton = new Button(leftComposite, SWT.NONE);
		changeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				rightCompositeSL.topControl = changeabilityComposite;
				changeabilityComposite.reloadProject();
				rightComposite.layout();
			}
		});
		changeButton.setText("\u53EF\u66FF\u6362\u6027\u5EA6\u91CF");
		
		Button compatibilityButton = new Button(leftComposite, SWT.NONE);
		compatibilityButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				rightCompositeSL.topControl = compatibilityComposite;
				rightComposite.layout();
			}
		});
		compatibilityButton.setText("\u53EF\u517C\u5BB9\u6027\u5EA6\u91CF");
		
		Button extensiMutiButton = new Button(leftComposite, SWT.NONE);
		extensiMutiButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				rightCompositeSL.topControl = extensiMutiVerionShowComposite;
				extensiMutiVerionShowComposite.reloadProject();
				//rightCompositeSL.topControl = diffComposite;
				rightComposite.layout();
			}
		});
		extensiMutiButton.setText("\u53EF\u6269\u5C55\u6027\u6F14\u5316\u8BC4\u4F30");		
		
		Button changeMutiButton = new Button(leftComposite, SWT.NONE);
		changeMutiButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				rightCompositeSL.topControl = changeMutiVersionShowComposite;
				changeMutiVersionShowComposite.reloadProject();
				rightComposite.layout();
			}
		});
		changeMutiButton.setText("\u53EF\u66FF\u6362\u6027\u6F14\u5316\u8BC4\u4F30");
		
		compatibilityComposite = new CompatibilityComposite(rightComposite, SWT.NONE);
		extensibilityComposite = new ExtensibilityComposite(rightComposite, SWT.NONE);
		changeabilityComposite = new SubstitutabilityComposite(rightComposite, SWT.NONE);
		projectInfoComposite = new ProjectInfoComposite(rightComposite, SWT.NONE);
		changeMutiVersionShowComposite = new SubstitutabilityMutiVersionShowComposite(rightComposite, SWT.NONE);
		extensiMutiVerionShowComposite = new ExtensiMutiVerionShowComposite(rightComposite, SWT.NONE);
		compatibilityMutiVersionComposite = new CompatibilityMutiVersionComposite(rightComposite, SWT.NONE);
		rightCompositeSL.topControl = projectInfoComposite;
		
		Button compatibilityMutibutton = new Button(composite, SWT.NONE);
		compatibilityMutibutton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				rightCompositeSL.topControl = compatibilityMutiVersionComposite;
				rightComposite.layout();
			}
		});
		compatibilityMutibutton.setText("\u53EF\u517C\u5BB9\u6027\u6F14\u5316\u8BC4\u4F30");
		FormData fd_compatibilityMutibutton = new FormData();
		fd_compatibilityMutibutton.bottom = new FormAttachment(leftComposite, 87, SWT.BOTTOM);
		fd_compatibilityMutibutton.right = new FormAttachment(leftComposite, 0, SWT.RIGHT);
		fd_compatibilityMutibutton.top = new FormAttachment(leftComposite, 1);
		fd_compatibilityMutibutton.left = new FormAttachment(leftComposite, 0, SWT.LEFT);
		compatibilityMutibutton.setLayoutData(fd_compatibilityMutibutton);*/

	}
	
	
	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			IntergrationDialog inst = new IntergrationDialog(shell, SWT.NULL);
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
