﻿/* 作者 何磊
 * 日期 2015/6/22
 * 版本 2.0
 * 描述：连续版本界面;系统层是默认显示最新导入的项目的所有版本的复杂度信息，其他的需要选择比较项目版本号
 * 问题:这个版本号的选择的图形切换还没有做，下个版本加进来
 * 更新：将这个界面进行拆分成多个容器，方便代码管理
 * */
package cn.seu.edu.complexityevaluator.ui.seriesui;

import java.util.Map;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.jfree.chart.JFreeChart;

import cn.seu.edu.complexityevaluator.chart.classChart.hsdChart.SeriesClassBugChart;
import cn.seu.edu.complexityevaluator.chart.classChart.hsdChart.SeriesClassDifChart;
import cn.seu.edu.complexityevaluator.chart.classChart.hsdChart.SeriesClassEffortChart;
import cn.seu.edu.complexityevaluator.chart.classChart.hsdChart.SeriesClassLengthChart;
import cn.seu.edu.complexityevaluator.chart.classChart.hsdChart.SeriesClassVolumeChart;
import cn.seu.edu.complexityevaluator.chart.methodChart.hsdChart.SeriesMethodBugChart;
import cn.seu.edu.complexityevaluator.chart.methodChart.hsdChart.SeriesMethodDifChart;
import cn.seu.edu.complexityevaluator.chart.methodChart.hsdChart.SeriesMethodEffortChart;
import cn.seu.edu.complexityevaluator.chart.methodChart.hsdChart.SeriesMethodLengthChart;
import cn.seu.edu.complexityevaluator.chart.methodChart.hsdChart.SeriesMethodVolumeChart;
import cn.seu.edu.complexityevaluator.ui.seriesui.seriesclassui.halstead.ClassHsdTable;
import cn.seu.edu.complexityevaluator.ui.seriesui.seriesclassui.halstead.ClassHsd_Tab;
import cn.seu.edu.complexityevaluator.ui.seriesui.seriesmethodui.halstead.MethodHsdTable;
import cn.seu.edu.complexityevaluator.ui.seriesui.seriesmethodui.halstead.MethodHsd_Tab;
import cn.seu.edu.complexityevaluator.util.ChangeDistillerMain;
import cn.seu.edu.complexityevaluator.util.Mapping;
import cn.seu.edu.complexityevaluator.util.utilofui.InitialCombo;
import cn.seu.edu.complexityevaluator.util.utilofui.ShowOrHideControl;

public class SeriesHsdComplextyInfo extends Composite {
	private StackLayout sl = new StackLayout();// 设置堆栈格式
	private InitialCombo init = new InitialCombo();// 初始化下拉条
	private Label label;// 选择项目标签
	private Label label_1;// 起始版本号
	private Label label_2;// 结束版本号
	public static Combo combo_proj;// 项目下拉条
	public static Combo combo_sversion;// 起始版本号下拉条
	public static Combo combo_eversion;// 结束版本号下拉条
	private Button button_sys;// 系统层按钮
	private Button button_class;// 类层按钮
	private Button button_method;// 方法层按钮
	private Button hstdchangeInfo;// 变更原因按钮
	private DownHsd_Composite composite;// 下面的大容器
	private ShowOrHideControl showorhide;
	private Mapping mapped = new Mapping();
    private ChangeDistillerMain changer=new ChangeDistillerMain();
	public SeriesHsdComplextyInfo(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());
		// 系统层单选按钮
		button_sys = new Button(this, SWT.RADIO);
		FormData fd_button = new FormData();
		button_sys.setLayoutData(fd_button);
		button_sys.setText("系统层");
		button_sys.setSelection(true);// 默认选中
		// 类层单选按钮
		button_class = new Button(this, SWT.RADIO);
		fd_button.top = new FormAttachment(button_class, 0, SWT.TOP);
		fd_button.right = new FormAttachment(button_class, -6);
		FormData fd_button_1 = new FormData();
		fd_button_1.top = new FormAttachment(0, 24);
		button_class.setLayoutData(fd_button_1);
		button_class.setText("类层");
		// 方法层单选按钮
		button_method = new Button(this, SWT.RADIO);
		fd_button_1.right = new FormAttachment(button_method, -6);
		FormData fd_button_2 = new FormData();
		fd_button_2.top = new FormAttachment(0, 24);
		fd_button_2.right = new FormAttachment(100, -10);
		button_method.setLayoutData(fd_button_2);
		button_method.setText("方法层");
		// 选择项目标签
		label = new Label(this, SWT.NONE);
		FormData fd_label = new FormData();
		fd_label.top = new FormAttachment(0, 24);
		label.setLayoutData(fd_label);
		label.setText("选择项目");
		// 项目名下拉条
		combo_proj = new Combo(this, SWT.NONE);
		fd_label.right = new FormAttachment(combo_proj, -12);
		FormData fd_combo = new FormData();
		fd_combo.left = new FormAttachment(0, 82);
		fd_combo.top = new FormAttachment(label, -3, SWT.TOP);
		combo_proj.setLayoutData(fd_combo);
		init.initProj(combo_proj);// 默认选中最新的项目名

		// 起始版本号
		label_1 = new Label(this, SWT.NONE);
		fd_combo.right = new FormAttachment(label_1, -5);
		FormData fd_label_1 = new FormData();
		fd_label_1.left = new FormAttachment(0, 164);
		fd_label_1.top = new FormAttachment(label, 0, SWT.TOP);
		label_1.setLayoutData(fd_label_1);
		label_1.setText("起始版本号");
		label_1.setVisible(false);
		// 起始版本号下拉条
		combo_sversion = new Combo(this, SWT.NONE);
		FormData fd_combo_1 = new FormData();
		fd_combo_1.left = new FormAttachment(label_1, 6);
		fd_combo_1.top = new FormAttachment(label, -3, SWT.TOP);
		combo_sversion.setLayoutData(fd_combo_1);
		combo_sversion.setVisible(false);
		// 结束版本号标签
		label_2 = new Label(this, SWT.NONE);
		fd_combo_1.right = new FormAttachment(label_2, -6);
		FormData fd_lblNewLabel = new FormData();
		fd_lblNewLabel.top = new FormAttachment(label, 0, SWT.TOP);
		label_2.setLayoutData(fd_lblNewLabel);
		label_2.setText("结束版本号");
		label_2.setVisible(false);
		// 结束版本号下拉条
		combo_eversion = new Combo(this, SWT.NONE);
		fd_lblNewLabel.right = new FormAttachment(100, -337);
		FormData fd_combo_2 = new FormData();
		fd_combo_2.top = new FormAttachment(label, -3, SWT.TOP);
		fd_combo_2.left = new FormAttachment(label_2, 8);
		combo_eversion.setLayoutData(fd_combo_2);
		combo_eversion.setVisible(false);
		// 变更原因按钮
		hstdchangeInfo = new Button(this, SWT.NONE);
		FormData fd_btnNewButton_11 = new FormData();
		fd_btnNewButton_11.right = new FormAttachment(100, -70);
		hstdchangeInfo.setLayoutData(fd_btnNewButton_11);
		hstdchangeInfo.setText("变更原因");
		hstdchangeInfo.setVisible(false);
		// 添加变更原因按钮 选中监听器，如果切换怎么处理
		hstdchangeInfo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String projName = SeriesHsdComplextyInfo.combo_proj.getText();
				String leftVersion = SeriesHsdComplextyInfo.combo_sversion.getText();
				String rightVersion = SeriesHsdComplextyInfo.combo_eversion.getText();
				if(projName!=null&&projName!=""&&leftVersion!=null&&leftVersion!=""&&rightVersion!=null&&rightVersion!=""){
				 changer.HstdChangeData(projName,leftVersion,rightVersion);
				 new OutputSuccessDialog(new Shell(), SWT.None).open("C:/test/ClassHalsteadComplexityEvolution.csv");
				}else{
				 new ProjInfoErrorDialog(new Shell(), SWT.None).open();
				}
			}
		});

		// 下面的主容器，用来存放其他容器，设置成堆栈式布局
		composite = new DownHsd_Composite(this, SWT.NONE);
		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(combo_proj, 17);
		fd_composite.bottom = new FormAttachment(100, -10);
		fd_composite.left = new FormAttachment(0, 10);
		fd_composite.right = new FormAttachment(0, 725);
		composite.setLayoutData(fd_composite);
		composite.setLayout(sl);
		// 默认显示系统层界面
		sl.topControl = composite.getComposite_sys();
		// 添加 项目名称 选中监听器，如果切换怎么处理
		combo_proj.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// 事件处理
				// 先清空原来的版本数据
				combo_sversion.removeAll();
				combo_eversion.removeAll();
				// 再获取当前选中项目名查询对应版本号
				init.initialVersion(combo_proj.getText(), combo_sversion);
			}
		});
		// 起始版本按钮选择处理
		combo_sversion.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// 先清空原来的版本数据
				combo_eversion.removeAll();
				// 清空图形数据 包括类层次和方法层级
				ClassHsd_Tab.getClassbug_frame().setChart(null);
				ClassHsd_Tab.getClassdif_frame().setChart(null);
				ClassHsd_Tab.getClasshsd_frame().setChart(null);
				ClassHsd_Tab.getClasseffort_frame().setChart(null);
				ClassHsd_Tab.getClasslength_frame().setChart(null);
				ClassHsdTable.getTable().removeAll();
				// 再获取当前选中项目名，对应的去除选中起始版本号的比版本号数据大的版本号
				init.initialEversion(combo_proj, combo_sversion, combo_eversion);
			}
		});

		// 结束版本按钮选择处理 只有这个按钮有选中才会去执行图形
		combo_eversion.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// 添加图形 类层和方法层圈复杂度柱状图以及映射表
				// 1.方法层
				JFreeChart classhsdchanged_chart = SeriesClassVolumeChart// volume图
				.createChart(SeriesClassVolumeChart.createDataset(
						SeriesHsdComplextyInfo.combo_proj.getText(),
						SeriesHsdComplextyInfo.combo_sversion.getText(),
						SeriesHsdComplextyInfo.combo_eversion.getText()));
				ClassHsd_Tab.getClasshsd_frame()
						.setChart(classhsdchanged_chart);
				ClassHsd_Tab.getClasshsd_frame().layout();
				JFreeChart classlengthchanged_chart = SeriesClassLengthChart// length图
				.createChart(SeriesClassLengthChart.createDataset(
						SeriesHsdComplextyInfo.combo_proj.getText(),
						SeriesHsdComplextyInfo.combo_sversion.getText(),
						SeriesHsdComplextyInfo.combo_eversion.getText()));
				ClassHsd_Tab.getClasslength_frame().setChart(
						classlengthchanged_chart);
				ClassHsd_Tab.getClasslength_frame().layout();
				JFreeChart classeffortchanged_chart = SeriesClassEffortChart// effort
				.createChart(SeriesClassEffortChart.createDataset(
						SeriesHsdComplextyInfo.combo_proj.getText(),
						SeriesHsdComplextyInfo.combo_sversion.getText(),
						SeriesHsdComplextyInfo.combo_eversion.getText()));
				ClassHsd_Tab.getClasseffort_frame().setChart(
						classeffortchanged_chart);
				ClassHsd_Tab.getClasseffort_frame().layout();
				JFreeChart classdifchanged_chart = SeriesClassDifChart// dif
				.createChart(SeriesClassDifChart.createDataset(
						SeriesHsdComplextyInfo.combo_proj.getText(),
						SeriesHsdComplextyInfo.combo_sversion.getText(),
						SeriesHsdComplextyInfo.combo_eversion.getText()));
				ClassHsd_Tab.getClassdif_frame()
						.setChart(classdifchanged_chart);
				ClassHsd_Tab.getClassdif_frame().layout();
				JFreeChart classbugchanged_chart = SeriesClassBugChart// bug
				.createChart(SeriesClassBugChart.createDataset(
						SeriesHsdComplextyInfo.combo_proj.getText(),
						SeriesHsdComplextyInfo.combo_sversion.getText(),
						SeriesHsdComplextyInfo.combo_eversion.getText()));
				ClassHsd_Tab.getClassbug_frame()
						.setChart(classbugchanged_chart);
				ClassHsd_Tab.getClassbug_frame().layout();
				Map<Integer, String> classVolumeMapping = SeriesClassVolumeChart
						.getClassVolumeMapping();
				mapped.classMapped(classVolumeMapping, "Volume变更图",
						ClassHsdTable.getTable());
				// SeriesClassLength编号映射
				Map<Integer, String> classLengthMapping = SeriesClassLengthChart
						.getClassLengthtMapping();
				mapped.classMapped(classLengthMapping, "Length变更图",
						ClassHsdTable.getTable());
				// SeriesClassEffort编号映射
				Map<Integer, String> classEffortMapping = SeriesClassEffortChart
						.getClassEffortMapping();
				mapped.classMapped(classEffortMapping, "Effort变更图",
						ClassHsdTable.getTable());
				// SeriesClassDifChart编号映射
				Map<Integer, String> classDifMapping = SeriesClassDifChart
						.getClassDifMapping();
				mapped.classMapped(classDifMapping, "Difficulty变更图",
						ClassHsdTable.getTable());
				// SeriesClassBugChart编号映射
				Map<Integer, String> classBugMapping = SeriesClassBugChart
						.getClassBugMapping();
				mapped.classMapped(classBugMapping, "bug变更图",
						ClassHsdTable.getTable());

				// 2.方法层填充
				JFreeChart methodHsdChanged_chart = SeriesMethodVolumeChart// volume
				.createChart(SeriesMethodVolumeChart.createDataset(
						SeriesHsdComplextyInfo.combo_proj.getText(),
						SeriesHsdComplextyInfo.combo_sversion.getText(),
						SeriesHsdComplextyInfo.combo_eversion.getText()));
				MethodHsd_Tab.getMethodHsd_frame().setChart(
						methodHsdChanged_chart);
				MethodHsd_Tab.getMethodHsd_frame().layout();
				JFreeChart methodLengthChanged_chart = SeriesMethodLengthChart// length
				.createChart(SeriesMethodLengthChart.createDataset(
						SeriesHsdComplextyInfo.combo_proj.getText(),
						SeriesHsdComplextyInfo.combo_sversion.getText(),
						SeriesHsdComplextyInfo.combo_eversion.getText()));
				MethodHsd_Tab.getMethodLength_frame().setChart(
						methodLengthChanged_chart);
				MethodHsd_Tab.getMethodLength_frame().layout();
				JFreeChart methodEffortChanged_chart = SeriesMethodEffortChart// effort
				.createChart(SeriesMethodEffortChart.createDataset(
						SeriesHsdComplextyInfo.combo_proj.getText(),
						SeriesHsdComplextyInfo.combo_sversion.getText(),
						SeriesHsdComplextyInfo.combo_eversion.getText()));
				MethodHsd_Tab.getMethodEffort_frame().setChart(
						methodEffortChanged_chart);
				MethodHsd_Tab.getMethodEffort_frame().layout();
				JFreeChart methodBugChanged_chart = SeriesMethodBugChart// bug
				.createChart(SeriesMethodBugChart.createDataset(
						SeriesHsdComplextyInfo.combo_proj.getText(),
						SeriesHsdComplextyInfo.combo_sversion.getText(),
						SeriesHsdComplextyInfo.combo_eversion.getText()));
				MethodHsd_Tab.getMethodBug_frame().setChart(
						methodBugChanged_chart);
				MethodHsd_Tab.getMethodBug_frame().layout();
				JFreeChart methodDifChanged_chart = SeriesMethodDifChart// dif
				.createChart(SeriesMethodDifChart.createDataset(
						SeriesHsdComplextyInfo.combo_proj.getText(),
						SeriesHsdComplextyInfo.combo_sversion.getText(),
						SeriesHsdComplextyInfo.combo_eversion.getText()));
				MethodHsd_Tab.getMethodDif_frame().setChart(
						methodDifChanged_chart);
				MethodHsd_Tab.getMethodDif_frame().layout();
				// SeriesmethodVolume编号映射
				Map<Integer, String> methodVolumeMapping = SeriesMethodVolumeChart
						.getMethodVolumeMapping();
				mapped.methodMapped(methodVolumeMapping, "Volume变更图",
						MethodHsdTable.getTable());

				// SeriesmethodLength编号映射
				Map<Integer, String> methodLengthMapping = SeriesMethodLengthChart
						.getMethodLengthMapping();
				mapped.methodMapped(methodLengthMapping, "Length变更图",
						MethodHsdTable.getTable());

				// SeriesmethodEffort编号映射
				Map<Integer, String> methodEffortMapping = SeriesMethodEffortChart
						.getMethodEffortMapping();
				mapped.methodMapped(methodEffortMapping, "Effort变更图",
						MethodHsdTable.getTable());

				// SeriesmethodDifChart编号映射
				Map<Integer, String> methodDifMapping = SeriesMethodDifChart
						.getMethodDifMapping();
				mapped.methodMapped(methodDifMapping, "Difficulty变更图",
						MethodHsdTable.getTable());

				// SeriesmethodBugChart编号映射
				Map<Integer, String> methodBugMapping = SeriesMethodBugChart
						.getMethodBugMapping();
				mapped.methodMapped(methodBugMapping, "bug变更图",
						MethodHsdTable.getTable());
			}
		});

		// 为上面这些单选按钮添加监听器
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				showorhide = new ShowOrHideControl();
				if (event.widget == button_sys) { // //第一个按钮，系统层
					sl.topControl = composite.getComposite_sys();
					// 隐藏部分选择框
					showorhide.hide(label_1, label_2, combo_sversion,
							combo_eversion);
					hstdchangeInfo.setVisible(false);
					// 项目名下拉框初始化
					combo_proj.removeAll();
					init.initProj(combo_proj);
				} else if (event.widget == button_class) {// 第二个按钮，类层
					sl.topControl = composite.getComposite_class();
					showorhide.show(label_1, label_2, combo_sversion,
							combo_eversion);
					hstdchangeInfo.setVisible(true);
					combo_eversion.removeAll();
					combo_sversion.removeAll();
					init.initialVersion(combo_proj.getText(), combo_sversion);
				} else if (event.widget == button_method) { // 第三个按钮，方法层
					sl.topControl = composite.getComposite_method();
					showorhide.show(label_1, label_2, combo_sversion,
							combo_eversion);
					hstdchangeInfo.setVisible(false);
					combo_eversion.removeAll();
					combo_sversion.removeAll();
					init.initialVersion(combo_proj.getText(), combo_sversion);
				}
				composite.layout();
			}
		};
		button_sys.addListener(SWT.Selection, listener);
		button_class.addListener(SWT.Selection, listener);
		button_method.addListener(SWT.Selection, listener);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
