package cn.seu.edu.integrabilityevaluator.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.custom.CTabItem;

public class CompatibilityMutiVersionComposite extends Composite {

	private InnerCompatibilityMutiVersionComposite innerCompatibilityMutiVersionComposite;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CompatibilityMutiVersionComposite(Composite parent, int style) {
		super(parent, style);
		
		CTabFolder tabFolder = new CTabFolder(this, SWT.BORDER);
		tabFolder.setBounds(0, 0, 809, 614);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		CTabItem tbtmEvaluationOfInner = new CTabItem(tabFolder, SWT.NONE);
		tbtmEvaluationOfInner.setText("Evaluation of inner compatibility \r\nunder evoluation");
		
		
		
		Composite composite = new InnerCompatibilityMutiVersionComposite(tabFolder, SWT.NONE);
		tbtmEvaluationOfInner.setControl(composite);
		
		CTabItem tbtmEvaluationOfOuter = new CTabItem(tabFolder, SWT.NONE);
		tbtmEvaluationOfOuter.setText("Evaluation of outer compatibility under evoluation");
		
		Composite composite_2 = new OuterCompatibilityMutiVersionComposite(tabFolder, SWT.NONE);
		tbtmEvaluationOfOuter.setControl(composite_2);
		
		CTabItem tbtmEvaluationOfVersion = new CTabItem(tabFolder, SWT.NONE);
		tbtmEvaluationOfVersion.setText("Evaluation of version compatibility under evoluation");
		
		Composite composite_1 = new VersionCompatibilityComposite(tabFolder, SWT.NONE);
		tbtmEvaluationOfVersion.setControl(composite_1);
		
		tabFolder.setSelection(tbtmEvaluationOfInner);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
