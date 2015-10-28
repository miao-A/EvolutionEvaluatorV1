﻿/* 
 * 日期： 2015-4-21
 * 描述： 主要是弹出分析完成对话框
 * 问题：位置没有居中显示
 * */
package cn.seu.edu.complexityevaluator.ui.inputprojui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

public class CompleteAnalyseDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	public CompleteAnalyseDialog(Shell parent, int style) {
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
		shell.setSize(359, 157);
		shell.setText(getText());
		//居中
		Rectangle parentBounds = shell.getParent().getBounds();  
		Rectangle shellBounds = shell.getBounds();  
		shell.setLocation(parentBounds.x + (parentBounds.width - shellBounds.width)/2, parentBounds.y + (parentBounds.height - shellBounds.height)/2);  
		// 分析完成提示语
		Label label = new Label(shell, SWT.NONE);
		label.setBounds(140, 52, 61, 17);
		label.setText("分析完成!!");

	}
}
