package cn.seu.edu.integrabilityevaluator.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.custom.CTabItem;

public class CompatibilityComposite extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CompatibilityComposite(Composite parent, int style) {
		super(parent, style);
		
		CTabFolder tabFolder = new CTabFolder(this, SWT.BORDER);
		tabFolder.setBounds(0, 0, 809, 614);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
		tabItem.setText("\u5185\u90E8\u517C\u5BB9\u6027");
		
		Composite composite = new InnerCompatibilityComposite(tabFolder, SWT.NONE);
		tabItem.setControl(composite);
		
		CTabItem tabItem_2 = new CTabItem(tabFolder, SWT.NONE);
		tabItem_2.setText("\u5916\u90E8\u517C\u5BB9\u6027");
		
		Composite composite_2 = new OuterCompatibilityComposite(tabFolder, SWT.NONE);
		tabItem_2.setControl(composite_2);
		
/*		CTabItem tabItem_1 = new CTabItem(tabFolder, SWT.NONE);
		tabItem_1.setText("\u7248\u672C\u517C\u5BB9\u6027");*/
		
/*		Composite composite_1 = new VersionCompatibilityComposite(tabFolder, SWT.NONE);
		tabItem_1.setControl(composite_1);*/
		
		tabFolder.setSelection(tabItem);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
