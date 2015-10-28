﻿package cn.seu.edu.complexityevaluator.ui.halsteadui;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import cn.seu.edu.complexityevaluator.dao.QueryProjInfoDao;
import cn.seu.edu.complexityevaluator.dao.QueryProjInfoDaoImpl;
import cn.seu.edu.complexityevaluator.util.utilofui.LastProjAndVer;

public class SysHstdInfo extends Composite {
	private QueryProjInfoDao queryData = new QueryProjInfoDaoImpl();
	private LastProjAndVer lastProjAndVer = new LastProjAndVer();// 获取最新的project和version
	private static Table systemHstd_table;//系统表格
    public static Table getSystemHstd_table() {
		return systemHstd_table;
	}

    private Composite low_composite;//容器
	public SysHstdInfo(Composite parent, int style) {
		super(parent, style);
		low_composite = new Composite(this, SWT.NONE);
		
		low_composite.setBounds(10, 10, 659, 393);

		systemHstd_table = new Table(low_composite, SWT.BORDER);
		systemHstd_table.setLocation(0, 0);
		systemHstd_table.setSize(659, 393);
		systemHstd_table.setLinesVisible(true);

		// 查询数据库中最新的系统halstead信息
		ResultSet rs_halstead = queryData.queryExactSysHsdProjVersion(lastProjAndVer.last()[0],lastProjAndVer.last()[1]);
		try {
			while (rs_halstead.next()) {
				String uiqueOperator = rs_halstead.getInt(4) + "";
				TableItem item_uiqueOperator = new TableItem(systemHstd_table, SWT.NONE);
				item_uiqueOperator.setText("唯一操作符数目："+uiqueOperator);
				String uiqueOperand = rs_halstead.getInt(5) + "";
				TableItem item_uiqueOperand = new TableItem(systemHstd_table, SWT.NONE);
				item_uiqueOperand.setText("唯一操作数数目："+uiqueOperand);
				String Operator = rs_halstead.getInt(6) + "";
				TableItem item_Operator = new TableItem(systemHstd_table, SWT.NONE);
				item_Operator.setText("总操作符数目："+Operator);
				String Operand = rs_halstead.getInt(7) + "";
				TableItem item_Operand = new TableItem(systemHstd_table, SWT.NONE);
				item_Operand.setText("总操作数数目："+Operand);
				String length = rs_halstead.getInt(8) + "";
				TableItem item_length = new TableItem(systemHstd_table, SWT.NONE);
				item_length.setText("程序长度："+length);
				String volume = rs_halstead.getFloat(9) + "";
				TableItem item_volume = new TableItem(systemHstd_table, SWT.NONE);
				item_volume.setText("程序容量："+volume);
				String bugs = rs_halstead.getFloat(10) + "";
				TableItem item_bugs = new TableItem(systemHstd_table, SWT.NONE);
				item_bugs.setText("预测bug数目："+bugs);
				String effort = rs_halstead.getFloat(11) + "";
				TableItem item_effort = new TableItem(systemHstd_table, SWT.NONE);
				item_effort.setText("工作量："+effort);
				String diffculty = rs_halstead.getFloat(12) + "";
				TableItem item_diffculty = new TableItem(systemHstd_table, SWT.NONE);
				item_diffculty.setText("程序难度："+diffculty);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
