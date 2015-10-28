/* 作者 何磊
 * 日期 2015-6-22
 * 版本 1.0
 * 描述：这个容器是容纳了方法层Tab页面
 * */
package cn.seu.edu.complexityevaluator.ui.seriesui.seriesmethodui.cc;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;

public class MethodCc_Composite extends Composite {

	private TabFolder methodTabFolder;
	public MethodCc_Composite(Composite parent, int style) {
		super(parent, style);
		methodTabFolder = new MethodCc_Tab(this, SWT.NONE);
		methodTabFolder.setBounds(0, 0, 715, 430);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
