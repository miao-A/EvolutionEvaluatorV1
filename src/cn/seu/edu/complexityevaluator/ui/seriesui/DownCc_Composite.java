﻿/* 作者 何磊
 * 日期 2015-6-22
 * 版本 1.0
 * 描述：这个界面是容纳下面多个tab页面的容器，包括系统层界面，类层界面和方法层界面
 * 
 * */
package cn.seu.edu.complexityevaluator.ui.seriesui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import cn.seu.edu.complexityevaluator.ui.seriesui.seriesclassui.cc.ClassCc_Composite;
import cn.seu.edu.complexityevaluator.ui.seriesui.seriesmethodui.cc.MethodCc_Composite;
import cn.seu.edu.complexityevaluator.ui.seriesui.seriessysui.cc.SysCc_Composite;

public class DownCc_Composite extends Composite {
	private SysCc_Composite composite_sys;// 系统层界面
	private ClassCc_Composite composite_class;// 类层界面
	private MethodCc_Composite composite_method;// 方法层界面
	public SysCc_Composite getComposite_sys() {
		return composite_sys;
	}

	public ClassCc_Composite getComposite_class() {
		return composite_class;
	}

	public MethodCc_Composite getComposite_method() {
		return composite_method;
	}

	public DownCc_Composite(Composite parent, int style) {
		super(parent, style);
		// 系统层界面
		composite_sys = new SysCc_Composite(this, SWT.NONE);
		// 类层界面
		composite_class = new ClassCc_Composite(this, SWT.NONE);
		// 方法层界面
		composite_method = new MethodCc_Composite(this, SWT.NONE);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
