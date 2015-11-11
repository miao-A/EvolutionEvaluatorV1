package cn.edu.seu.integrabilityevaluator.ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.MenuItem;

import cn.edu.seu.integrabilityevaluator.uicomposite.CompatibilityComposite;
import cn.edu.seu.integrabilityevaluator.uicomposite.CompatibilityMutiVersionComposite;
import cn.edu.seu.integrabilityevaluator.uicomposite.ExtensiMutiVerionShowComposite;
import cn.edu.seu.integrabilityevaluator.uicomposite.ExtensibilityComposite;
import cn.edu.seu.integrabilityevaluator.uicomposite.ProjectInfoComposite;
import cn.edu.seu.integrabilityevaluator.uicomposite.SubstitutabilityComposite;
import cn.edu.seu.integrabilityevaluator.uicomposite.SubstitutabilityMutiVersionShowComposite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.GridLayout;

public class IntegrationApp {
	
	private Shell shell; 
	private StackLayout compositeSL = new StackLayout();
	private SubstitutabilityComposite changeabilityComposite;
	private ExtensibilityComposite extensibilityComposite;
	private CompatibilityComposite compatibilityComposite;
	private ProjectInfoComposite projectInfoComposite;
	private ExtensiMutiVerionShowComposite extensiMutiVerionShowComposite;
	private SubstitutabilityMutiVersionShowComposite changeMutiVersionShowComposite;
	private CompatibilityMutiVersionComposite compatibilityMutiVersionComposite;
	
	
	
	public static void main(String[] args){
		try {
			IntegrationApp window = new IntegrationApp();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/*public IntegrationApp(){
		try {
			IntegrationApp window = new IntegrationApp();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	*/

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		shell = new Shell(SWT.SHELL_TRIM | SWT.PRIMARY_MODAL);
		shell.setSize(931, 658);
		shell.setText("Integration Evaluator");
		shell.setLayout(new FormLayout());
		//shell.setLayout(new StackLayout());		
	
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		final MenuItem projImportItem = new MenuItem(menu, SWT.NONE);
		projImportItem.setText("Project Import");
		
		new MenuItem(menu, SWT.SEPARATOR);
		
		MenuItem mntmMeasurement = new MenuItem(menu, SWT.CASCADE);
		mntmMeasurement.setText("Measurement");
		
		Menu menu_1 = new Menu(mntmMeasurement);
		mntmMeasurement.setMenu(menu_1);
		
		final MenuItem extensibilityItem = new MenuItem(menu_1, SWT.NONE);
		extensibilityItem.setText("Measurement of Extensibility");
		
		final MenuItem substitutabilityItem = new MenuItem(menu_1, SWT.NONE);
		substitutabilityItem.setText("Measurement of Substitutability");
		
		final MenuItem compatibilityItem = new MenuItem(menu_1, SWT.NONE);
		compatibilityItem.setText("Measurement of Compatibility");

		
		new MenuItem(menu, SWT.SEPARATOR);
		
		
		MenuItem mntmEvaluation = new MenuItem(menu, SWT.CASCADE);
		mntmEvaluation.setText("Evaluation");
		
		Menu menu_2 = new Menu(mntmEvaluation);
		mntmEvaluation.setMenu(menu_2);
		
		final MenuItem extensibilityMutiItem = new MenuItem(menu_2, SWT.NONE);
		extensibilityMutiItem.setText("Evaluation of Extensibility under Evoluation");
		
		final MenuItem substitutabilityMutiItem = new MenuItem(menu_2, SWT.NONE);
		substitutabilityMutiItem.setText("Evaluation of Substitutability under Evoluation");
		
		final MenuItem compatibilityMutiItem = new MenuItem(menu_2, SWT.NONE);
		compatibilityMutiItem.setText("Evaluation of Compatibility under Evoluation");
		
		
		final Composite composite = new Composite(shell, SWT.NONE);
		FormData fd_composite = new FormData();
		fd_composite.bottom = new FormAttachment(100);
		fd_composite.right = new FormAttachment(100);
		fd_composite.top = new FormAttachment(0);
		fd_composite.left = new FormAttachment(0);
		composite.setLayoutData(fd_composite);
		composite.setLayout(compositeSL);		
		
		projectInfoComposite = new ProjectInfoComposite(composite, SWT.NONE);
		//compatibilityComposite = new CompatibilityComposite(composite, SWT.NONE);
		//extensibilityComposite = new ExtensibilityComposite(composite, SWT.NONE);
		//changeabilityComposite = new SubstitutabilityComposite(composite, SWT.NONE);		
		//changeMutiVersionShowComposite = new SubstitutabilityMutiVersionShowComposite(composite, SWT.NONE);
		//extensiMutiVerionShowComposite = new ExtensiMutiVerionShowComposite(composite, SWT.NONE);
		//compatibilityMutiVersionComposite = new CompatibilityMutiVersionComposite(composite, SWT.NONE);
		
		compositeSL.topControl = projectInfoComposite;
		composite.layout();
		
		
		Listener listener = new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				if(event.widget == projImportItem){
					compositeSL.topControl = projectInfoComposite;
					setShellTitle("");
				}else if (event.widget == extensibilityItem) {	
					extensibilityComposite = new ExtensibilityComposite(composite, SWT.NONE);
					compositeSL.topControl = extensibilityComposite;
					extensibilityComposite.reloadProject();
					setShellTitle(" - Measurement of Extensibility");
				}else if (event.widget == substitutabilityItem) {
					changeabilityComposite = new SubstitutabilityComposite(composite, SWT.NONE);
					compositeSL.topControl = changeabilityComposite;
					changeabilityComposite.reloadProject();
					setShellTitle(" - Measurement of Substitutability");
				}else if (event.widget == compatibilityItem) {
					compatibilityComposite = new CompatibilityComposite(composite, SWT.NONE);
					compositeSL.topControl = compatibilityComposite;
					setShellTitle(" - Measurement of Compatibility");
				}else if (event.widget == extensibilityMutiItem) {	
					extensiMutiVerionShowComposite = new ExtensiMutiVerionShowComposite(composite, SWT.NONE);
					compositeSL.topControl = extensiMutiVerionShowComposite;
					extensiMutiVerionShowComposite.reloadProject();
					setShellTitle(" - Evaluation of Extensibility under Evoluation");
				}else if (event.widget == substitutabilityMutiItem) {
					changeMutiVersionShowComposite = new SubstitutabilityMutiVersionShowComposite(composite, SWT.NONE);
					compositeSL.topControl = changeMutiVersionShowComposite;
					changeMutiVersionShowComposite.reloadProject();
					setShellTitle(" - Evaluation of Substitutability under Evoluation");
				}else if (event.widget == compatibilityMutiItem) {
					compatibilityMutiVersionComposite = new CompatibilityMutiVersionComposite(composite, SWT.NONE);
					compositeSL.topControl = compatibilityMutiVersionComposite;
					setShellTitle(" - Evaluation of Compatibility under Evoluation");
				}						
				composite.layout();
			}
			
		};
		
		compatibilityItem.addListener(SWT.Selection, listener);
		projImportItem.addListener(SWT.Selection, listener);
		substitutabilityItem.addListener(SWT.Selection, listener);
		extensibilityItem.addListener(SWT.Selection, listener);
		compatibilityMutiItem.addListener(SWT.Selection, listener);
		substitutabilityMutiItem.addListener(SWT.Selection, listener);
		extensibilityMutiItem.addListener(SWT.Selection, listener);
		
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
	}
	
	public void setShellTitle(String string){
		shell.setText("Integration Evaluator"+string);
	}
}
