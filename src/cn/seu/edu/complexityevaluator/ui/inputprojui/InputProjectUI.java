/*
 * 日期 2015-4-20
 * 描述：项目导入界面内容 
 * 版本：2.0
 * 问题：1.0：后面要可能要添加，输入框内容的校验。现在只是校验了项目文件夹路径/已解决
 *     
 * 更新：将构造方法中的实例对象，抽出到属性中去，简化了监听器部分代码。
 * 
 * */
package cn.seu.edu.complexityevaluator.ui.inputprojui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;

import cn.seu.edu.complexityevaluator.dao.QueryProjInfoDao;
import cn.seu.edu.complexityevaluator.dao.QueryProjInfoDaoImpl;
import cn.seu.edu.complexityevaluator.mainui.CalculateMetric;
import cn.seu.edu.complexityevaluator.org.eclipse.wb.swt.SWTResourceManager;
import cn.seu.edu.complexityevaluator.ui.inputprojui.CompleteAnalyseDialog;
import cn.seu.edu.complexityevaluator.ui.inputprojui.InputErrorDialog;

public class InputProjectUI extends Composite {

	private Label label_tip;// 输入相关信息提示语
	private Label projName_tip;// 项目名lable
	private Label projIntroduce_tip;// 项目简介label
	private Label projVersion_tip;// 版本号label
	private Label projPath_tip;// 项目路径label
	private Text text_dir;// 文件夹路径框
	private String selectedDir;// 文件夹路径
	private CalculateMetric calculateMetric = new CalculateMetric();
	private Text projName_text;// 项目名称内容
	private Text projVersion_text;// 项目版本内容
	private Text projIntroduce_text;// 项目简介内容
	private Button buttonSelectDir;// 选择项目路径按钮
	private Button btnNewButton_analyse;// 分析按钮
	private CompleteAnalyseDialog ca=new CompleteAnalyseDialog(new Shell(), SWT.None);//分析完成对话框
	private QueryProjInfoDao query=new QueryProjInfoDaoImpl();
	public InputProjectUI(Composite composite_input, int style) {
		super(composite_input, style);
		//设置背景色为白色
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		// 输入相关信息提示语
		label_tip = new Label(this, SWT.NONE);
		label_tip.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_tip.setBounds(104, 42, 206, 17);
		label_tip.setText("请输入你需要分析的项目相关信息：");
		// 项目名称
		projName_tip = new Label(this, SWT.NONE);
		projName_tip.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));//设置背景色为白色
		projName_tip.setBounds(104, 94, 61, 17);
		projName_tip.setText("项目名称：");
		// 项目名称内容
		projName_text = new Text(this, SWT.BORDER);
		projName_text.setBounds(192, 91, 181, 23);
		// 项目简介
		projIntroduce_tip = new Label(this, SWT.NONE);
		projIntroduce_tip.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));//设置背景色为白色
		projIntroduce_tip.setBounds(104, 171, 61, 17);
		projIntroduce_tip.setText("项目简介：");
		// 项目简介内容
		projIntroduce_text = new Text(this, SWT.BORDER);
		projIntroduce_text.setBounds(192, 168, 181, 76);
		// 项目版本号提示
		projVersion_tip = new Label(this, SWT.NONE);
		projVersion_tip.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));//设置背景色为白色
		projVersion_tip.setBounds(104, 136, 81, 17);
		projVersion_tip.setText("项目版本号：");
		// 项目版本号内容
		projVersion_text = new Text(this, SWT.BORDER);
		projVersion_text.setBounds(192, 133, 182, 23);
		// 项目路径提示语
		projPath_tip = new Label(this, SWT.NONE);
		projPath_tip.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));//设置背景色为白色
		projPath_tip.setBounds(104, 269, 61, 17);
		projPath_tip.setText("项目路径：");
		// 项目路径的文本框
		text_dir = new Text(this, SWT.BORDER);
		text_dir.setBounds(192, 266, 182, 23);
		// 选择项目路径按钮
		buttonSelectDir = new Button(this, SWT.NONE);
		buttonSelectDir.setBounds(380, 264, 104, 27);
		buttonSelectDir.setText("选择项目文件夹");

		// 添加触发事件，以及打开文件夹选择监听器
		buttonSelectDir.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {

				DirectoryDialog directoryDialog = new DirectoryDialog(
						getShell(), SWT.OPEN);
				directoryDialog.setFilterPath(selectedDir);
				directoryDialog.setMessage("选择需要分析的项目文件夹");

				String dir = directoryDialog.open();
				if (dir != null) {
					text_dir.setText(dir);
					selectedDir = dir;
				}
			}
		});

		// 分析按钮
		btnNewButton_analyse = new Button(this, SWT.NONE);
		btnNewButton_analyse.setBounds(224, 318, 124, 46);
		btnNewButton_analyse.setText("开始分析项目");
		// 添加监听器
		btnNewButton_analyse.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (event.widget == btnNewButton_analyse) { // //第一个按钮，项目导入
					final String projpath = text_dir.getText();// 拿到输入的项目路径
					final String projName = projName_text.getText();// 拿到项目的名称
					final String projVersion = projVersion_text.getText();// 拿到项目的版本号
					// 做一些简单的判断
					if (projpath != null && projpath != "" && projName != ""
							&& projVersion != ""&&!query.isExist(projName, projVersion)) {
						//根基项目名和版本号判断是否已经分析过
						
						//设置分析中状态
						btnNewButton_analyse.setText("分析中");
						btnNewButton_analyse.setEnabled(false);
						//任务时间处理较长，加入新的线程
						new Thread() {
							public void run() {
								calculateMetric.caculateMetric(projpath,projName, projVersion);
								Display.getDefault().syncExec(new Runnable() {
									@Override
									public void run() {
										// TODO Auto-generated method stub
										//将按钮重置状态
										btnNewButton_analyse.setText("开始分析项目");
										btnNewButton_analyse.setEnabled(true);
										ca.open();//弹出分析完对话框	
									}

								});

							}
						}.start();

					} else {
						InputErrorDialog inputError = new InputErrorDialog(new Shell(),
								SWT.NONE);
						inputError.open();
					}
				}
			}
		});
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
