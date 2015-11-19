package cn.edu.seu.integrabilityevaluator.uicomposite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class CompatibilityComposite extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CompatibilityComposite(Composite parent, int style) {
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
		
		CTabItem tbtmInnerCompatibility = new CTabItem(tabFolder, SWT.NONE);
		//tabItem.setText("\u5185\u90E8\u517C\u5BB9\u6027");
		tbtmInnerCompatibility.setText(" Inner compatibility  ");
		
		final Composite composite = new InnerCompatibilityComposite(tabFolder, SWT.NONE);
		tbtmInnerCompatibility.setControl(composite);
		
		final CTabItem tabItem_2 = new CTabItem(tabFolder, SWT.NONE);
		
		tabItem_2.setText("Outer compatibility");
		
		final Composite composite_2 = new OuterCompatibilityComposite(tabFolder, SWT.NONE);
		


		tabItem_2.setControl(composite_2);
		
/*		CTabItem tabItem_1 = new CTabItem(tabFolder, SWT.NONE);
		tabItem_1.setText("\u7248\u672C\u517C\u5BB9\u6027");*/
		
/*		Composite composite_1 = new VersionCompatibilityComposite(tabFolder, SWT.NONE);
		tabItem_1.setControl(composite_1);*/	
		
		tabFolder.setSelection(tbtmInnerCompatibility);

		tabFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!((InnerCompatibilityComposite) composite).getProjectPath().equals("")){
					((OuterCompatibilityComposite) composite_2).setProjectPath(((InnerCompatibilityComposite) composite).getProjectPath());
				}
			}
		});
		
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
