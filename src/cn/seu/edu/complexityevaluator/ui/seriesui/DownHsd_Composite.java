/* 作者 何磊
 * 日期 2015-6-22
 * 版本 1.0
 * 描述：这个界面是容纳下面多个tab页面的容器，包括系统层界面，类层界面和方法层界面
 * 
 * */
package cn.seu.edu.complexityevaluator.ui.seriesui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import cn.seu.edu.complexityevaluator.ui.seriesui.seriesclassui.halstead.ClassHsd_Composite;
import cn.seu.edu.complexityevaluator.ui.seriesui.seriesmethodui.halstead.MethodHsd_Composite;
import cn.seu.edu.complexityevaluator.ui.seriesui.seriessysui.halstead.SysHsd_Composite;

public class DownHsd_Composite extends Composite {
	private SysHsd_Composite composite_sys;// 系统层界面
	private ClassHsd_Composite composite_class;// 类层界面
	private MethodHsd_Composite composite_method;// 方法层界面
	public SysHsd_Composite getComposite_sys() {
		return composite_sys;
	}

	public ClassHsd_Composite getComposite_class() {
		return composite_class;
	}

	public MethodHsd_Composite getComposite_method() {
		return composite_method;
	}

//	private StackLayout sl = new StackLayout();// 设置堆栈格式
	public DownHsd_Composite(Composite parent, int style) {
		super(parent, style);
		// 系统层界面
		composite_sys = new SysHsd_Composite(this, SWT.NONE);
		// 类层界面,这个里面会包含三个界面，一个是类变更信息，一个是圈复杂度变更图，还有一个halstead变更图
		composite_class = new ClassHsd_Composite(this, SWT.NONE);
		// 方法层界面
		composite_method = new MethodHsd_Composite(this, SWT.NONE);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
