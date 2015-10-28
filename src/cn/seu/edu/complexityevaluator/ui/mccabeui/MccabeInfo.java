/* 
 * 日期 2015-6-1
 * 版本 3.0
 * 描述 系统复杂度的主界面
 * 
 * */
package cn.seu.edu.complexityevaluator.ui.mccabeui;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.TabItem;
import org.jfree.chart.JFreeChart;

import cn.seu.edu.complexityevaluator.chart.classChart.mccChart.ClassMccPieChart;
import cn.seu.edu.complexityevaluator.chart.methodChart.mccChart.MethodMccPieChart;
import cn.seu.edu.complexityevaluator.dao.QueryProjInfoDao;
import cn.seu.edu.complexityevaluator.dao.QueryProjInfoDaoImpl;
import cn.seu.edu.complexityevaluator.org.eclipse.wb.swt.SWTResourceManager;
import cn.seu.edu.complexityevaluator.util.utilofui.ClearComposite;
import cn.seu.edu.complexityevaluator.util.utilofui.InitialCombo;

public class MccabeInfo extends Composite {

	private QueryProjInfoDao queryData = new QueryProjInfoDaoImpl();
	private Label projName;// 项目名提示
	private Label projVersion;// 版本号提示
	private Label sysMcc;//系统圈复杂度
	private Label sysMccNum;//系统圈复杂度
	private Combo combo_project;// proj下拉框
	private Combo combo_version;// version下拉框
	private TabFolder tabFolder;//分页tab
	private TabItem tb_class;// 类层级tab
	private TabItem tb_method;// 方法层
	private InitialCombo init = new InitialCombo();// 初始化下拉框类
	private Composite composite_class;// tab里面的类层容器
	private Composite composite_method;// tab里面的方法层容器
    private ClearComposite clear=new ClearComposite();//清空辅助类
	public MccabeInfo(Composite parent, int style) {
		super(parent, style);
		// 项目名
		projName = new Label(this, SWT.NONE);
		projName.setBounds(21, 10, 67, 17);
		projName.setText("选择项目名:");

		// 版本号
		projVersion = new Label(this, SWT.NONE);
		projVersion.setBounds(248, 10, 72, 17);
		projVersion.setText("选择版本号：");
		
		//系统圈复杂度标签
		sysMcc=new Label(this, SWT.NONE);
		sysMcc.setBounds(490, 10, 88, 17);
		sysMcc.setText("系统的圈复杂度：");
		
		//系统圈复杂度数值
		sysMccNum=new Label(this, SWT.NONE);
		sysMccNum.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		sysMccNum.setBounds(578, 10, 72, 22);
		
		// proj下拉框
		combo_project = new Combo(this, SWT.DROP_DOWN);
		combo_project.setBounds(95, 7, 88, 25);
		// version下拉框
		combo_version = new Combo(this, SWT.DROP_DOWN);
		combo_version.setBounds(326, 7, 88, 20);
		// tabfolder容器
		tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setBounds(0, 47, 737, 478);

		tb_class = new TabItem(tabFolder, SWT.NONE);
		tb_class.setText("类的圈复杂度");

		tb_method = new TabItem(tabFolder, SWT.NONE);
		tb_method.setText("方法的圈复杂度");

		// 初始化项目下拉条
		init.initProj(combo_project);
		// 初始化版本下拉条
		String active_projectName = combo_project.getText();
		init.initVersion(active_projectName, combo_version);
		//初始化系统层圈复杂度值
		String active_projectVersion=combo_version.getText();
		int sysMcc = queryData.queryExactSysMcc(active_projectName, active_projectVersion);
		sysMccNum.setText(sysMcc+"");
		// 系统层容器
		composite_class = new ClassMccInfo(tabFolder, SWT.NONE);
		tb_class.setControl(composite_class);
		composite_method = new MethodMccInfo(tabFolder, SWT.NONE);
		tb_method.setControl(composite_method);
		
		
		// 添加 项目名称 选中监听器，如果切换怎么处理
		combo_project.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// 事件处理
				// 先清空原来下拉条的版本数据
				combo_version.removeAll();
				// 再获取当前选中项目名查询对应版本号,重新初始化
				String changed_projectName = combo_project.getText();
				init.initialVersion(changed_projectName, combo_version);
				// 清空系统级原来表格数据
				clear.clearMccTable();
				sysMccNum.setText(null);
			}
		});
		
		// 添加 项目版本 选中监听器，如果切换怎么处理
		combo_version.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// 清空圈复杂度数据
				clear.clearMccTable();
				MethodMccInfo.getFrame().setChart(null);
				// 拿到当前选中的版本号
				String changed_projectName = combo_project.getText();
				String changed_version = combo_version.getText();
				//设置系统级圈复杂度数据
				int sysMcc = queryData.queryExactSysMcc(changed_projectName, changed_version);
				sysMccNum.setText(sysMcc+"");
				// 设置类级别圈复杂数据
				ResultSet rs_classmcc = queryData.queryExactClassMccProjVersion(changed_projectName, changed_version);
				try {
					while (rs_classmcc.next()) {
						String packageName = rs_classmcc.getString(5);
						String className = rs_classmcc.getString(6);
						String mccabe = rs_classmcc.getInt(7) + "";
						TableItem i = new TableItem(ClassMccInfo.getClassMcc_table(), SWT.NONE);
						i.setText(new String[] { packageName, className,
								mccabe });
					}
				} catch (SQLException g) {
					// TODO Auto-generated catch block
					g.printStackTrace();
				}	
				// 设置方法级别圈复杂数据
				ResultSet rs_methodmcc = queryData.queryExactMethodMccProjVersion(changed_projectName, changed_version);
				try {
					while (rs_methodmcc.next()) {
						String packageName = rs_methodmcc.getString(5);
						String className = rs_methodmcc.getString(6);
						String methodName = rs_methodmcc.getString(7);
						String mccabe = rs_methodmcc.getInt(8) + "";
						TableItem i = new TableItem(MethodMccInfo.getMaccabe_table(), SWT.NONE);
						i.setText(new String[] { packageName, className,
								methodName, mccabe });
					}
				} catch (SQLException h) {
					h.printStackTrace();
				}
				//刷新类层圈复杂度分布表
				JFreeChart classchart=ClassMccPieChart.createChart(ClassMccPieChart.createDataset(combo_project.getText(),combo_version.getText()));
				ClassMccInfo.getClassframe().setChart(classchart);
				ClassMccInfo.getClassframe().layout();
				//刷新方法层图形
				JFreeChart methodchart = MethodMccPieChart.createChart(MethodMccPieChart
						.createDataset(combo_project.getText(),combo_version.getText()));
				MethodMccInfo.getFrame().setChart(methodchart);
				MethodMccInfo.getFrame().layout();
				
			}
		});
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
