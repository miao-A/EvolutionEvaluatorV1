/* 作者 何磊
 * 日期 2015-6-22
 * 版本 1.0
 * 描述：这个容器是容纳了类层次的Tab页面
 * */
package cn.seu.edu.complexityevaluator.ui.seriesui.seriesclassui.cc;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;

public class ClassCc_Composite extends Composite {

	private TabFolder class_tabFolder;
	public ClassCc_Composite(Composite parent, int style) {
		super(parent, style);
		class_tabFolder = new ClassCc_Tab(this, SWT.NONE);
		class_tabFolder.setBounds(0, 0, 715, 430);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
