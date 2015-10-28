﻿/* 作者 何磊
 * 日期 2015-4-20
 * 描述 ： 这个对话框是针对单击“开始分析项目“这个按钮，来做的错误提示对话框
 * 版本： 1.0
 * 存在问题：目前对话框弹出的位置并不是在正中间。后面需要调整
 * */
package cn.seu.edu.complexityevaluator.ui.inputprojui;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;

public class InputErrorDialog extends Dialog {

	protected Object result;
	protected Shell shell;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public InputErrorDialog(Shell parent, int style) {
		super(parent, style);
		setText("");
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.CLOSE);
		shell.setSize(341, 111);
		shell.setText("错误！");
		//居中
		Rectangle parentBounds = this.getParent().getBounds();  
		Rectangle shellBounds = shell.getBounds();  
		shell.setLocation(parentBounds.x + (parentBounds.width - shellBounds.width)/2, parentBounds.y + (parentBounds.height - shellBounds.height)/2);  
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(100, 34, 144, 17);
		lblNewLabel.setText("项目信息不完整或已经分析过！");

	}
}
