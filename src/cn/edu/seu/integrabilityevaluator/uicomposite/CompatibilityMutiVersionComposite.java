package cn.edu.seu.integrabilityevaluator.uicomposite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

public class CompatibilityMutiVersionComposite extends Composite {

	private InnerCompatibilityMutiVersionComposite innerCompatibilityMutiVersionComposite;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CompatibilityMutiVersionComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());
		
		CTabFolder tabFolder = new CTabFolder(this, SWT.BORDER);
		FormData fd_tabFolder = new FormData();
		fd_tabFolder.bottom = new FormAttachment(100);
		fd_tabFolder.right = new FormAttachment(100);
		fd_tabFolder.top = new FormAttachment(0);
		fd_tabFolder.left = new FormAttachment(0);
		tabFolder.setLayoutData(fd_tabFolder);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		CTabItem tbtmEvaluationOfInner = new CTabItem(tabFolder, SWT.NONE);
		tbtmEvaluationOfInner.setText(" Inner compatibility under evoluation  ");
		
		
		
		final Composite composite = new InnerCompatibilityMutiVersionComposite(tabFolder, SWT.NONE);
		tbtmEvaluationOfInner.setControl(composite);
		
		CTabItem tbtmEvaluationOfOuter = new CTabItem(tabFolder, SWT.NONE);
		tbtmEvaluationOfOuter.setText("Outer compatibility under evoluation  ");
		
		final Composite composite_2 = new OuterCompatibilityMutiVersionComposite(tabFolder, SWT.NONE);
		tbtmEvaluationOfOuter.setControl(composite_2);
		
		CTabItem tbtmEvaluationOfVersion = new CTabItem(tabFolder, SWT.NONE);
		tbtmEvaluationOfVersion.setText("Version compatibility under evoluation");
		
		final Composite composite_1 = new VersionCompatibilityComposite(tabFolder, SWT.NONE);
		tbtmEvaluationOfVersion.setControl(composite_1);
		
		tabFolder.setSelection(tbtmEvaluationOfInner);
		
		tabFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(((InnerCompatibilityMutiVersionComposite)composite).getProjectPath1().equals("")||((InnerCompatibilityMutiVersionComposite)composite).getProjectPath2().equals("")){
					//
				}else {
					((OuterCompatibilityMutiVersionComposite) composite_2).setProjectPath1(((InnerCompatibilityMutiVersionComposite)composite).getProjectPath1());
					((OuterCompatibilityMutiVersionComposite) composite_2).setProjectPath2(((InnerCompatibilityMutiVersionComposite)composite).getProjectPath2());
				
					((VersionCompatibilityComposite) composite_1).setProjectPath1(((InnerCompatibilityMutiVersionComposite)composite).getProjectPath1());
					((VersionCompatibilityComposite) composite_1).setProjectPath2(((InnerCompatibilityMutiVersionComposite)composite).getProjectPath2());
				
				}
			}
		});

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
