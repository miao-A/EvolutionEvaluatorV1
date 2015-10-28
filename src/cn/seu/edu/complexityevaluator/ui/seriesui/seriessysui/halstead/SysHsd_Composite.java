﻿/* 作者 何磊
 * 日期 2015-6-22
 * 版本1.0
 * 描述 这个容器主要是容纳了系统层Tab页面
 * 
 * */
package cn.seu.edu.complexityevaluator.ui.seriesui.seriessysui.halstead;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;

public class SysHsd_Composite extends Composite {

	private TabFolder sys_tabFolder;
	public SysHsd_Composite(Composite parent, int style) {
		super(parent, style);
		sys_tabFolder = new SysHsd_Tab(this, SWT.NONE);
		sys_tabFolder.setBounds(0, 0, 715, 430);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
