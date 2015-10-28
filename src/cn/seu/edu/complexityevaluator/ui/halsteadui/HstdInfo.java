/* 
 * 日期 2015-6-1
 * 版本 3.0
 * 描述 系统复杂度的界面,这个一个主布局，里面包含三个层次的composite
 * 
 * */
package cn.seu.edu.complexityevaluator.ui.halsteadui;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.widgets.TabItem;

import cn.seu.edu.complexityevaluator.dao.QueryProjInfoDao;
import cn.seu.edu.complexityevaluator.dao.QueryProjInfoDaoImpl;
import cn.seu.edu.complexityevaluator.org.eclipse.wb.swt.SWTResourceManager;
import cn.seu.edu.complexityevaluator.ui.mccabeui.ClassMccInfo;
import cn.seu.edu.complexityevaluator.ui.mccabeui.MethodMccInfo;
import cn.seu.edu.complexityevaluator.util.QueryAndDeleteUtil;
import cn.seu.edu.complexityevaluator.util.utilofui.ClearComposite;
import cn.seu.edu.complexityevaluator.util.utilofui.InitialCombo;

public class HstdInfo extends Composite {
	private QueryProjInfoDao queryData = new QueryProjInfoDaoImpl();

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	private Label projName;// 项目名提示
	private Label projVersion;// 版本号提示
	private Combo combo_project;// proj下拉框
	private Combo combo_version;// version下拉框
	private TabFolder tabFolder;
	private TabItem tb_sys;// 系统层次
	private TabItem tb_class;// 类层级tab
	private TabItem tb_method;// 方法层
	private InitialCombo init = new InitialCombo();// 初始化下拉框类
	private Composite composite_sys;// tab里面的系统层容器
	private Composite composite_class;// tab里面的类层容器
	private Composite composite_method;// tab里面的方法层容器
	private ClearComposite clear = new ClearComposite();// 清空辅助类

	public HstdInfo(Composite parent, int style) {
		super(parent, style);
		// 项目名
		projName = new Label(this, SWT.NONE);
		projName.setBounds(21, 10, 67, 17);
		projName.setText("选择项目名:");

		// 版本号
		projVersion = new Label(this, SWT.NONE);
		projVersion.setBounds(248, 10, 72, 17);
		projVersion.setText("选择版本号：");
		// proj下拉框
		combo_project = new Combo(this, SWT.DROP_DOWN);
		combo_project.setBounds(95, 7, 88, 25);
		// version下拉框
		combo_version = new Combo(this, SWT.DROP_DOWN);
		combo_version.setBounds(326, 7, 88, 20);
		// tabfolder容器
		tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setBounds(0, 47, 737, 478);

		tb_sys = new TabItem(tabFolder, SWT.NONE);
		tb_sys.setText("系统的Halstead信息");

		tb_class = new TabItem(tabFolder, SWT.NONE);
		tb_class.setText("类的Halstead信息");

		tb_method = new TabItem(tabFolder, SWT.NONE);
		tb_method.setText("方法的Halstead信息");

		// 初始化项目下拉条
		init.initProj(combo_project);
		// 初始化版本下拉条
		String active_projectName = combo_project.getText();
		init.initVersion(active_projectName, combo_version);
		// 系统层容器
//		composite_sys = new SysHalsteadInfo(tabFolder, SWT.NONE);
		composite_sys = new SysHstdInfo(tabFolder, SWT.NONE);
		tb_sys.setControl(composite_sys);
		composite_class = new ClassHstdInfo(tabFolder, SWT.NONE);
		tb_class.setControl(composite_class);
		composite_method = new MethodHstdInfo(tabFolder, SWT.NONE);
		tb_method.setControl(composite_method);
		// 添加 项目名称 选中监听器，如果切换怎么处理
		combo_project.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// 事件处理
				// 先清空原来的版本数据
				combo_version.removeAll();
				// 再获取当前选中项目名查询对应版本号
				String changed_projectName = combo_project.getText();
				init.initialVersion(changed_projectName, combo_version);
				// 清空系统级原来表格数据
                clear.clearHsdTable();
			}
		});
		// 添加 项目版本 选中监听器，如果切换怎么处理
		combo_version.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// 清空系统级别表格数据
				clear.clearHsdTable();
				// 拿到当前选中的版本号
				String changed_projectName = combo_project.getText();
				String changed_version = combo_version.getText();
				// 设置系统级别表中数据
				ResultSet rs_halstead = queryData.queryExactSysHsdProjVersion(changed_projectName, changed_version);
				try {
//					while (rs_halstead.next()) {
//						String uiqueOperator = rs_halstead.getInt(4) + "";
//						String uiqueOperand = rs_halstead.getInt(5) + "";
//						String Operator = rs_halstead.getInt(6) + "";
//						String Operand = rs_halstead.getInt(7) + "";
//						String length = rs_halstead.getInt(8) + "";
//						String volume = rs_halstead.getFloat(9) + "";
//						String bugs = rs_halstead.getFloat(10) + "";
//						String effort = rs_halstead.getFloat(11) + "";
//						String diffculty = rs_halstead.getFloat(12) + "";
//						TableItem item = new TableItem(SysHalsteadInfo.getSystemHstd_table(),
//								SWT.NONE);
//						item.setText(new String[] { uiqueOperator,
//								uiqueOperand, Operator, Operand, length,
//								volume, bugs, effort, diffculty});
					while (rs_halstead.next()) {
						String uiqueOperator = rs_halstead.getInt(4) + "";
						TableItem item_uiqueOperator = new TableItem(SysHstdInfo.getSystemHstd_table(), SWT.NONE);
						item_uiqueOperator.setText("唯一操作符数目："+uiqueOperator);
						String uiqueOperand = rs_halstead.getInt(5) + "";
						TableItem item_uiqueOperand = new TableItem(SysHstdInfo.getSystemHstd_table(), SWT.NONE);
						item_uiqueOperand.setText("唯一操作数数目："+uiqueOperand);
						String Operator = rs_halstead.getInt(6) + "";
						TableItem item_Operator = new TableItem(SysHstdInfo.getSystemHstd_table(), SWT.NONE);
						item_Operator.setText("总操作符数目："+Operator);
						String Operand = rs_halstead.getInt(7) + "";
						TableItem item_Operand = new TableItem(SysHstdInfo.getSystemHstd_table(), SWT.NONE);
						item_Operand.setText("总操作数数目："+Operand);
						String length = rs_halstead.getInt(8) + "";
						TableItem item_length = new TableItem(SysHstdInfo.getSystemHstd_table(), SWT.NONE);
						item_length.setText("程序长度："+length);
						String volume = rs_halstead.getFloat(9) + "";
						TableItem item_volume = new TableItem(SysHstdInfo.getSystemHstd_table(), SWT.NONE);
						item_volume.setText("程序容量："+volume);
						String bugs = rs_halstead.getFloat(10) + "";
						TableItem item_bugs = new TableItem(SysHstdInfo.getSystemHstd_table(), SWT.NONE);
						item_bugs.setText("预测bug数目："+bugs);
						String effort = rs_halstead.getFloat(11) + "";
						TableItem item_effort = new TableItem(SysHstdInfo.getSystemHstd_table(), SWT.NONE);
						item_effort.setText("工作量："+effort);
						String diffculty = rs_halstead.getFloat(12) + "";
						TableItem item_diffculty = new TableItem(SysHstdInfo.getSystemHstd_table(), SWT.NONE);
						item_diffculty.setText("程序难度："+diffculty);
					}
				} catch (SQLException d) {
					// TODO Auto-generated catch block
					d.printStackTrace();
				}
				// 设置类级别Halstead表格数据
				ResultSet rs_classhsd = queryData.queryExactClassHsdProjVersion(changed_projectName, changed_version);
				try {
					while (rs_classhsd.next()) {
						String packageName = rs_classhsd.getString(5);
						String className = rs_classhsd.getString(6);
						String length = rs_classhsd.getInt(11) + "";
						String volume = rs_classhsd.getFloat(12) + "";
						String bugs = rs_classhsd.getFloat(13) + "";
						String effort = rs_classhsd.getFloat(14) + "";
						TableItem item = new TableItem(ClassHstdInfo.getClassHsd_table(), SWT.NONE);
						item.setText(new String[] { packageName, className,
								length, volume, bugs, effort });
					}
				} catch (SQLException h) {
					// TODO Auto-generated catch block
					h.printStackTrace();
				}

				// 设置方法级别hasltead表格数据
				ResultSet rs_methodhsd = queryData.queryExactMethodHsdProjVersion(changed_projectName, changed_version);
				try {
					while (rs_methodhsd.next()) {
						String packageName = rs_methodhsd.getString(5);
						String className = rs_methodhsd.getString(6);
						String methodName = rs_methodhsd.getString(7);
						String length = rs_methodhsd.getInt(12) + "";
						String volume = rs_methodhsd.getFloat(13) + "";
						String bugs = rs_methodhsd.getFloat(14) + "";
						String effort = rs_methodhsd.getFloat(15) + "";
						TableItem item = new TableItem(MethodHstdInfo
								.getMethodHsd_table(), SWT.NONE);
						item.setText(new String[] { packageName, className,
								methodName, length, volume, bugs, effort });
					}
				} catch (SQLException i) {
					i.printStackTrace();
				}
			}
		});
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
