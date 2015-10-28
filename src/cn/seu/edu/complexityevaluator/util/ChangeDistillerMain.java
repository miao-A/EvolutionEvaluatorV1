﻿package cn.seu.edu.complexityevaluator.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import cn.seu.edu.complexityevaluator.chart.classChart.mccChart.SeriesClassMccChart;
import cn.seu.edu.complexityevaluator.chart.methodChart.hsdChart.SeriesMethodVolumeChart;
import cn.seu.edu.complexityevaluator.dao.QueryProjInfoDao;
import cn.seu.edu.complexityevaluator.dao.QueryProjInfoDaoImpl;
import cn.seu.edu.complexityevaluator.ui.seriesui.SeriesHsdComplextyInfo;
import cn.seu.edu.complexityevaluator.ui.seriesui.SeriesMccComplexityInfo;
import ch.uzh.ifi.seal.changedistiller.ChangeDistiller;
import ch.uzh.ifi.seal.changedistiller.ChangeDistiller.Language;
import ch.uzh.ifi.seal.changedistiller.distilling.FileDistiller;
import ch.uzh.ifi.seal.changedistiller.model.entities.SourceCodeChange;

public class ChangeDistillerMain {
	private QueryProjInfoDao query = new QueryProjInfoDaoImpl();
	private String leftFilePath;// 上一个版本的.java文件的全路径
	private String rightFilePath;// 下一个版本的.java文件的全路径

	public void MccChangeData(String projName, String leftVersion,
			String rightVersion) {
		List<SourceCodeChange> changes = new ArrayList<SourceCodeChange>();// 存储代码变更的结果信息
		// 最终需要的两个文件全路径
		// 根据以上的信息，从数据库中查询对应的项目路径
		String leftProjPath = query.queryProjPath(projName, leftVersion);
		String rightProjPath = query.queryProjPath(projName, rightVersion);
		// 根据项目路径遍历到每个.java文件的全路径
		String[] LeftSourceFilePaths = FilePathUtil.getAllFiles(leftProjPath,
				".java");
		String[] RightSourceFilePaths = FilePathUtil.getAllFiles(rightProjPath,
				".java");
		// 再根据方法的圈复杂度的halstad基本度量值的变更表中拿到发生变更的文件路径,并进行去重
		Set<String> fileSet = new HashSet<String>();// 用来去重
		Map<Integer, String> classMccMapping = SeriesClassMccChart
				.getClassMccMapping();
		Iterator it = classMccMapping.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, String> entry = (Entry<Integer, String>) it.next();
			// 拿到路径
			String path = entry.getValue();
			String fileName = path.split("\\|")[0];
			String packName = path.split("\\|")[1];
			String temp = packName.replace(".", "\\") + "\\" + fileName;// 改变格式
			fileSet.add(temp);
		}
		// 从fileSet中遍历，将这个路径去LeftSourceFilePaths和RightSourceFilePaths这两个数组中去匹配，找到完全匹配的两个路径，再分别将这两个路径传入分析工具中
		for (String s : fileSet) {
			for (String t : LeftSourceFilePaths) {
				if (t.indexOf(s) != -1) {
					leftFilePath = t;
				}
			}
			for (String t : RightSourceFilePaths) {
				if (t.indexOf(s) != -1) {
					rightFilePath = t;
				}
			}
			File left = new File(leftFilePath.replace("\\", "\\\\"));
			File right = new File(rightFilePath.replace("\\", "\\\\"));
			FileDistiller distiller = ChangeDistiller
					.createFileDistiller(Language.JAVA);
			try {
				distiller.extractClassifiedSourceCodeChanges(left, leftVersion,
						right, rightVersion);
			} catch (Exception e) {
				System.err.println("Warning: error while change distilling. "
						+ e.getMessage());
			}
			// 提取需要的变更的类型
			for (int i = 0; i < distiller.getSourceCodeChanges().size(); i++) {
				String changeType = distiller.getSourceCodeChanges().get(i)
						.toString();
				if (changeType.equals("ADDITIONAL_CLASS")
						|| changeType.equals("REMOVED_CLASS")
						|| changeType.equals("ADDITIONAL_FUNCTIONALITY")
						|| changeType.equals("REMOVED_FUNCTIONALITY")
						|| changeType.equals("ALTERNATIVE_PART_DELETE")
						|| changeType.equals("ALTERNATIVE_PART_INSERT ")
						|| changeType.equals("CONDITION_EXPRESSION_CHANGE")
						|| changeType.equals("STATEMENT_DELETE")
						|| changeType.equals("STATEMENT_INSERT")
						|| changeType.equals("STATEMENT_UPDATE")) {
					changes.add(distiller.getSourceCodeChanges().get(i));
				}
			}
		}
		printClassLevelComplexity(changes,
				"C:/test/ClassMccComplexityEvolution.csv");
	}

	public void HstdChangeData(String projName, String leftVersion,
			String rightVersion) {
		List<SourceCodeChange> changes = new ArrayList<SourceCodeChange>();// 存储代码变更的结果信息

		// 最终需要的两个文件全路径
		// 根据以上的信息，从数据库中查询对应的项目路径
		String leftProjPath = query.queryProjPath(projName, leftVersion);

		String rightProjPath = query.queryProjPath(projName, rightVersion);
		// 根据项目路径遍历到每个.java文件的全路径
		String[] LeftSourceFilePaths = FilePathUtil.getAllFiles(leftProjPath,
				".java");
		String[] RightSourceFilePaths = FilePathUtil.getAllFiles(rightProjPath,
				".java");
		// 再根据方法的圈复杂度的halstad基本度量值的变更表中拿到发生变更的文件路径,并进行去重
		Set<String> fileSet = new HashSet<String>();// 用来去重
		Map<Integer, String> methodVolumeMapping = SeriesMethodVolumeChart
				.getMethodVolumeMapping();
		Iterator it = methodVolumeMapping.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, String> entry = (Entry<Integer, String>) it.next();
			// 拿到路径
			String path = entry.getValue();
			String fileName = path.split("\\|")[0];
			String packName = path.split("\\|")[1];
			String temp = packName.replace(".", "\\") + "\\" + fileName;// 改变格式
			fileSet.add(temp);
		}
		// 从fileSet中遍历，将这个路径去LeftSourceFilePaths和RightSourceFilePaths这两个数组中去匹配，找到完全匹配的两个路径，再分别将这两个路径传入分析工具中
		for (String s : fileSet) {
			for (String t : LeftSourceFilePaths) {
				if (t.indexOf(s) != -1) {
					leftFilePath = t;
				}
			}
			for (String t : RightSourceFilePaths) {
				if (t.indexOf(s) != -1) {
					rightFilePath = t;
				}
			}
			File left = new File(leftFilePath.replace("\\", "\\\\"));
			File right = new File(rightFilePath.replace("\\", "\\\\"));

			FileDistiller distiller = ChangeDistiller
					.createFileDistiller(Language.JAVA);
			try {
				distiller.extractClassifiedSourceCodeChanges(left, leftVersion,
						right, rightVersion);
			} catch (Exception e) {
				System.err.println("Warning: error while change distilling. "
						+ e.getMessage());
			}

			// 提取需要的变更的类型
			for (int i = 0; i < distiller.getSourceCodeChanges().size(); i++) {
				for (ChangeTypes c : ChangeTypes.values()) {
					if (c.toString().equals(
							distiller.getSourceCodeChanges().get(i)
									.getChangeType().toString())) {
						changes.add(distiller.getSourceCodeChanges().get(i));
					}
				}
			}
		}
		printClassLevelComplexity(changes,
				"C:/test/ClassHalsteadComplexityEvolution.csv");
	}

	public void printClassLevelComplexity(List<SourceCodeChange> changes,
			String outputAddress) {
		DataWriter writer = new DataWriter(outputAddress);
		String[] head = { "RootEntity", "Change Type", "Change Details" };
		writer.writeToCsv(head);

		for (SourceCodeChange change : changes) {
			String changeType = change.getChangeType().toString();
//			if (changeType.equals("DOC_DELETE")
//					|| changeType.equals("DOC_INSERT")
//					|| changeType.equals("DOC_UPDATE")
//					|| changeType.equals("COMMENT_DELETE")
//					|| changeType.equals("COMMENT_INSERT")
//					|| changeType.equals("COMMENT_MOVE")
//					|| changeType.equals("COMMENT_UPDATE")) {
//
//			}

			String fullyClassName = change.getRootEntity().toString();
			String changeEntity = change.getChangedEntity().toString();
			String[] info = { fullyClassName, changeType, changeEntity };
			writer.writeToCsv(info);
		}
		writer.close();
		// this.classChangeCluster();
	}
}