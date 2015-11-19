package cn.edu.seu.integrabilityevaluator.uicomposite;

import java.awt.Frame;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;

import java.awt.Panel;
import java.awt.BorderLayout;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormLayout;

import cn.edu.seu.integrabilityevaluator.chart.ExtensibilityLineChart;
import cn.edu.seu.integrabilityevaluator.chart.LineChart;
import cn.edu.seu.integrabilityevaluator.chart.SubstitutabilityLineChart;
import cn.edu.seu.integrabilityevaluator.dbconnect.ProjectConnector;
import cn.edu.seu.integrabilityevaluator.dbconnect.ProjectInfoConnector;
import cn.edu.seu.integrabilityevaluator.parser.ExtensibilityDiff;
import cn.edu.seu.integrabilityevaluator.parser.SubstitutabilityDiff;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.wb.swt.SWTResourceManager;

public class SubstitutabilityMutiVersionShowComposite extends Composite {

	private ProjectConnector pcConnector = new ProjectConnector();
	private ArrayList<String> rStrings;
	private Combo projectSelectCombo = new Combo(this, SWT.NONE);
//	private JFreeChart chart = null;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @throws IOException 
	 */
	public SubstitutabilityMutiVersionShowComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());
		
		Label lblNull = new Label(this, SWT.NONE);
		FormData fd_lblNull = new FormData();
		fd_lblNull.top = new FormAttachment(0, 24);
		lblNull.setLayoutData(fd_lblNull);
		lblNull.setBounds(13, 15, 61, 17);
		lblNull.setText("Project:");
		FormData fd_projectSelectCombo = new FormData();
		fd_projectSelectCombo.top = new FormAttachment(0, 21);
		fd_projectSelectCombo.left = new FormAttachment(lblNull, 19);
		projectSelectCombo.setLayoutData(fd_projectSelectCombo);
	
		projectSelectCombo.setBounds(90, 12, 98, 25);
		
		
		final CTabFolder tabFolder = new CTabFolder(this, SWT.BORDER);
		tabFolder.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		fd_lblNull.right = new FormAttachment(tabFolder, 71);
		fd_lblNull.left = new FormAttachment(tabFolder, 0, SWT.LEFT);
		FormData fd_tabFolder = new FormData();
		fd_tabFolder.top = new FormAttachment(projectSelectCombo, 21);
		fd_tabFolder.bottom = new FormAttachment(100, -10);
		fd_tabFolder.right = new FormAttachment(100, -10);
		fd_tabFolder.left = new FormAttachment(0, 10);
		tabFolder.setLayoutData(fd_tabFolder);
		tabFolder.setLocation(13, 53);
		tabFolder.setSize(885, 527);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
				
		final CTabItem changeTabItem = new CTabItem(tabFolder, SWT.NONE);
		changeTabItem.setText("Tendency chart of substitutability");
		
		/*final CTabItem changeDifftabItem = new CTabItem(tabFolder, SWT.NONE);
		changeDifftabItem.setText("\u7248\u672C\u53D8\u66F4");*/
		
		final CTabItem classChangeDiffTabItem = new CTabItem(tabFolder, SWT.NONE);
		classChangeDiffTabItem.setText("Version changes information");
		
		final StyledText styledText = new StyledText(tabFolder, SWT.BORDER|SWT.H_SCROLL|SWT.V_SCROLL);
		classChangeDiffTabItem.setControl(styledText);
		
		final Combo version1Combo = new Combo(this, SWT.NONE);
		FormData fd_version1Combo = new FormData();
		fd_version1Combo.top = new FormAttachment(projectSelectCombo, 0, SWT.TOP);
		version1Combo.setLayoutData(fd_version1Combo);
		version1Combo.setBounds(294, 12, 88, 25);
		
		final Combo version2Combo = new Combo(this, SWT.NONE);
		FormData fd_version2Combo = new FormData();
		fd_version2Combo.top = new FormAttachment(projectSelectCombo, 0, SWT.TOP);
		version2Combo.setLayoutData(fd_version2Combo);
		version2Combo.setBounds(467, 12, 88, 25);
		
		Label label = new Label(this, SWT.NONE);
		FormData fd_label = new FormData();
		fd_label.left = new FormAttachment(projectSelectCombo, 21);
		fd_label.right = new FormAttachment(version1Combo, -16);
		fd_label.top = new FormAttachment(projectSelectCombo, 3, SWT.TOP);
		label.setLayoutData(fd_label);
		label.setBounds(217, 15, 61, 17);
		label.setText("Version1:");
		
		Label label_1 = new Label(this, SWT.NONE);
		fd_version2Combo.left = new FormAttachment(0, 529);
		fd_version1Combo.right = new FormAttachment(label_1, -29);
		FormData fd_label_1 = new FormData();
		fd_label_1.right = new FormAttachment(version2Combo, -6);
		fd_label_1.left = new FormAttachment(0, 446);
		fd_label_1.top = new FormAttachment(projectSelectCombo, 3, SWT.TOP);
		label_1.setLayoutData(fd_label_1);
		label_1.setText("Version2:");
		label_1.setBounds(400, 15, 61, 17);
			
		projectSelectCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = projectSelectCombo.getSelectionIndex();
				String projName = projectSelectCombo.getItem(index);
				
				ArrayList<String> verList = pcConnector.getVersion(projectSelectCombo.getItem(index));
				version1Combo.removeAll();
				for (String string : verList) {
					version1Combo.add(string);
				}
				version1Combo.layout();
				
				version2Combo.removeAll();
				for (String string : verList) {
					version2Combo.add(string);
				}
				version2Combo.layout();
				
				
				{				
					//System.out.println("可替换性指示图");
					//BarChart changeabilityChart = new ChangeabilityBarChart("可替换性指示图");
					SubstitutabilityLineChart changeabilityChart = new SubstitutabilityLineChart("",projName);				
										
					changeabilityChart.creatDataSet();		
					JFreeChart chart = null;
					try {
						changeabilityChart.createChart();
						chart  = changeabilityChart.getChart();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					ChartComposite chartComposite = new ChartComposite(tabFolder, SWT.NONE, chart, true);
					changeTabItem.setControl(chartComposite);
					chartComposite.setVisible(true);				
				
					System.out.println("chart print");
					
				}			
				
			}			
		});
	
		version1Combo.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (projectSelectCombo.getText().equals("")) {
					return ;					
				}
				if (version2Combo.getText().equals("")) {
					return ;					
				}
				String version1 = version1Combo.getText();
				String version2 = version2Combo.getText();
				String projName = projectSelectCombo.getText();
				//setCompose(tabFolder,classChangeDiffTabItem,projName,version1,version2);
				styledText.setText(getDiffText(projName,version1,version2));
				textAddColor(styledText);
				
			}
		});
		
		
		version2Combo.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if (projectSelectCombo.getText().equals("")) {
					return ;					
				}
				
				if (version1Combo.getText().equals("")) {
					return ;					
				}
				String version1 = version1Combo.getText();
				String version2 = version2Combo.getText();
				String projName = projectSelectCombo.getText();
				//setCompose(tabFolder,classChangeDiffTabItem,projName,version1,version2);
				styledText.setText(getDiffText(projName,version1,version2));
				textAddColor(styledText);
			}
		});
		
	}
	
	
	private void setCompose(Composite tabFolder,CTabItem classChangeDiffTabItem,String projName,String version1,String version2) {
		
		{
			Composite composite = new Composite(tabFolder, SWT.NONE);			
			//changeDifftabItem.setControl(composite);
			
			final StyledText cDiffText = new StyledText(composite, SWT.BORDER|SWT.V_SCROLL);
			cDiffText.setBounds(0, 0, 649, 441);
			
			cDiffText.setText("");
			cDiffText.setEditable(false);
			final SubstitutabilityDiff	changeabilityDiff = new SubstitutabilityDiff(projName);
			
			String textString = version1+" compare with " + version2;
			
			HashMap<String, HashMap<String,List<String>>> diffInfluenceMap = changeabilityDiff.diffInProject(version1, version2);
			
			for (String pkgName : diffInfluenceMap.keySet()) {
				
				HashMap<String, List<String>> map = diffInfluenceMap.get(pkgName);
				if (map.containsKey("+import")||map.containsKey("+export")||map.containsKey("-import")||map.containsKey("-export")) {
					textString += "\npackage: " + pkgName;
					
					if (map.containsKey("+import")) {									
						textString += "\n+import \t"+map.get("+import").size();
						for (String string : map.get("+import")) {
							textString += "\nName:\t"+string;
						}
					}
					
					if (map.containsKey("-import")) {									
						textString += "\n-import \t"+map.get("-import").size();
						for (String string : map.get("-import")) {
							textString += "\nName:\t"+string;
						}
					}
					
					if (map.containsKey("+export")) {									
						textString += "\n+export \t"+map.get("+export").size();
						for (String string : map.get("+export")) {
							textString += "\nName:\t"+string;
						}
					}
					
					if (map.containsKey("-export")) {									
						textString += "\n-export \t"+map.get("-export").size();
						for (String string : map.get("-export")) {
							textString += "\nName:\t"+string;
						}
					}
					
					
				}else {
					textString += "\nNo effect";
				}							
			}
			
			if (diffInfluenceMap.size()==0) {
				textString += "\nNo effect";
			}
			
			cDiffText.append(textString+"\n\n");								
			textAddColor(cDiffText);	
			System.out.println("diff print");
		}		
		
		{
			Composite composite = new Composite(tabFolder, SWT.NONE);			
			classChangeDiffTabItem.setControl(composite);
			
			final StyledText mcDiffText = new StyledText(composite, SWT.BORDER|SWT.V_SCROLL);
			mcDiffText.setBounds(0, 0, 649, 441);
			
			mcDiffText.setText("");
			mcDiffText.setEditable(false);
			ProjectInfoConnector projectInfoConnector = new ProjectInfoConnector();
			ArrayList<String> list = projectInfoConnector.getVersion(projName);
			final SubstitutabilityDiff	changeabilityDiff = new SubstitutabilityDiff(projName);
			String textString = version1+" compare with " + version2;
			
			HashMap<String, HashMap<String,List<String>>> diffInfluenceMap = changeabilityDiff.diffInProject(version1, version2);
			HashMap<String, HashMap<String,List<String>>> diffmap = changeabilityDiff.moreDiffInProject(version1, version2);
			
			boolean noEffect = true;

			for (String pkgName : diffmap.keySet()) {
				
				HashMap<String, List<String>> influenceMap = new HashMap<>();
				if (diffInfluenceMap.containsKey(pkgName)) {
					influenceMap = diffInfluenceMap.get(pkgName);
				}
				 
				
				HashMap<String, List<String>> map = diffmap.get(pkgName);
				if (map.containsKey("+import")||map.containsKey("+export")||map.containsKey("-import")||map.containsKey("-export")) {
					textString += "\npackage: " + pkgName;
					
					if (map.containsKey("+import")) {									
						textString += "\n+import \t"+map.get("+import").size();
						for (String string : map.get("+import")) {
							
							
							if (influenceMap.containsKey("+import")) {
								for (String influString : influenceMap.get("+import")) {
									if (string.startsWith(influString)) {
										string = "*"+string;
									}
								}
							}
							textString += "\nName:\t"+string;
						}
					}
					
					if (map.containsKey("-import")) {									
						textString += "\n-import \t"+map.get("-import").size();
						for (String string : map.get("-import")) {
							
							if (influenceMap.containsKey("-import")) {
								for (String influString : influenceMap.get("-import")) {
									if (string.startsWith(influString)) {
										string = "*"+string;
									}
								}
							}
							textString += "\nName:\t"+string;
						}
					}
					
					if (map.containsKey("+export")) {									
						textString += "\n+export \t"+map.get("+export").size();
						for (String string : map.get("+export")) {
							
							if (influenceMap.containsKey("+export")) {
								for (String influString : influenceMap.get("+export")) {
									if (string.startsWith(influString)) {
										string = "*"+string;
									}
								}
							}
							
							textString += "\nName:\t"+string;
						}
					}
					
					if (map.containsKey("-export")) {									
						textString += "\n-export \t"+map.get("-export").size();
						for (String string : map.get("-export")) {
							
							if (influenceMap.containsKey("-export")) {
								for (String influString : influenceMap.get("-export")) {
									if (string.startsWith(influString)) {
										string = "*"+string;
									}
								}
							}
							textString += "\nName:\t"+string;
						}
					}
					noEffect = false;
				}							
			}
			
			if (diffmap.size()==0||noEffect) {
				textString += "\nNo effect";
			}
			
			mcDiffText.append(textString+"\n\n");								
			textAddColor(mcDiffText);	
			System.out.println("diff print");
					
		}		
	}
	
private String getDiffText(String projName,String version1,String version2) {		
		{	
			
			
			final SubstitutabilityDiff	changeabilityDiff = new SubstitutabilityDiff(projName);
			
			String textString = version1+" compare with " + version2;
			
			HashMap<String, HashMap<String,List<String>>> diffInfluenceMap = changeabilityDiff.diffInProject(version1, version2);
			
			for (String pkgName : diffInfluenceMap.keySet()) {
				
				HashMap<String, List<String>> map = diffInfluenceMap.get(pkgName);
				if (map.containsKey("+import")||map.containsKey("+export")||map.containsKey("-import")||map.containsKey("-export")) {
					textString += "\npackage: " + pkgName;
					
					if (map.containsKey("+import")) {									
						textString += "\n+import \t"+map.get("+import").size();
						for (String string : map.get("+import")) {
							textString += "\nName:\t"+string;
						}
					}
					
					if (map.containsKey("-import")) {									
						textString += "\n-import \t"+map.get("-import").size();
						for (String string : map.get("-import")) {
							textString += "\nName:\t"+string;
						}
					}
					
					if (map.containsKey("+export")) {									
						textString += "\n+export \t"+map.get("+export").size();
						for (String string : map.get("+export")) {
							textString += "\nName:\t"+string;
						}
					}
					
					if (map.containsKey("-export")) {									
						textString += "\n-export \t"+map.get("-export").size();
						for (String string : map.get("-export")) {
							textString += "\nName:\t"+string;
						}
					}
					
					
				}else {
					textString += "\nNo effect";
				}							
			}
			
			if (diffInfluenceMap.size()==0) {
				textString += "\nNo effect";
			}
			textString +="\n\n";								
			
		}		
		
		{
			
			
			
			ProjectInfoConnector projectInfoConnector = new ProjectInfoConnector();
			ArrayList<String> list = projectInfoConnector.getVersion(projName);
			final SubstitutabilityDiff	changeabilityDiff = new SubstitutabilityDiff(projName);
			String textString2 = version1+" compare with " + version2;
			
			HashMap<String, HashMap<String,List<String>>> diffInfluenceMap = changeabilityDiff.diffInProject(version1, version2);
			HashMap<String, HashMap<String,List<String>>> diffmap = changeabilityDiff.moreDiffInProject(version1, version2);
			
			boolean noEffect = true;

			for (String pkgName : diffmap.keySet()) {
				
				HashMap<String, List<String>> influenceMap = new HashMap<>();
				if (diffInfluenceMap.containsKey(pkgName)) {
					influenceMap = diffInfluenceMap.get(pkgName);
				}
				 
				
				HashMap<String, List<String>> map = diffmap.get(pkgName);
				if (map.containsKey("+import")||map.containsKey("+export")||map.containsKey("-import")||map.containsKey("-export")) {
					textString2 += "\npackage: " + pkgName;
					
					if (map.containsKey("+import")) {									
						textString2 += "\n+import \t"+map.get("+import").size();
						for (String string : map.get("+import")) {
							
							
							if (influenceMap.containsKey("+import")) {
								for (String influString : influenceMap.get("+import")) {
									if (string.startsWith(influString)) {
										string = "*"+string;
									}
								}
							}
							textString2 += "\nName:\t"+string;
						}
					}
					
					if (map.containsKey("-import")) {									
						textString2 += "\n-import \t"+map.get("-import").size();
						for (String string : map.get("-import")) {
							
							if (influenceMap.containsKey("-import")) {
								for (String influString : influenceMap.get("-import")) {
									if (string.startsWith(influString)) {
										string = "*"+string;
									}
								}
							}
							textString2 += "\nName:\t"+string;
						}
					}
					
					if (map.containsKey("+export")) {									
						textString2 += "\n+export \t"+map.get("+export").size();
						for (String string : map.get("+export")) {
							
							if (influenceMap.containsKey("+export")) {
								for (String influString : influenceMap.get("+export")) {
									if (string.startsWith(influString)) {
										string = "*"+string;
									}
								}
							}
							
							textString2 += "\nName:\t"+string;
						}
					}
					
					if (map.containsKey("-export")) {									
						textString2 += "\n-export \t"+map.get("-export").size();
						for (String string : map.get("-export")) {
							
							if (influenceMap.containsKey("-export")) {
								for (String influString : influenceMap.get("-export")) {
									if (string.startsWith(influString)) {
										string = "*"+string;
									}
								}
							}
							textString2 += "\nName:\t"+string;
						}
					}
					noEffect = false;
				}							
			}
			
			if (diffmap.size()==0||noEffect) {
				textString2 += "\nNo effect";
			}
			
			textString2 += "\n\n";	
			
			return textString2;			
					
		}		
	}
	

	//设置字体颜色，抬头设为红色，包名设为蓝色，有影响的变更设置为黄色
	private void textAddColor(StyledText styledText){
		String text = styledText.getText();
		String[] strings = text.split("\n");
		int index = 0;
		for (int i = 0; i < strings.length; i++) {
		
			Color red = Display.getDefault().getSystemColor(SWT.COLOR_RED);
			Color blue = Display.getDefault().getSystemColor(SWT.COLOR_BLUE);
			Color green = Display.getDefault().getSystemColor(SWT.COLOR_GREEN);
		    /*StyleRange sr = new StyleRange(i, strings[i].length(), red, null);
		    styledText.setStyleRange(sr);*/
			
			StyleRange range = styledText.getStyleRangeAtOffset(index);
			if (strings[i].contains("compare with")) {
				if (range == null) {
					range = new StyleRange();
				}
				range.fontStyle = SWT.BOLD;
				range.foreground = red;
				range.start = index;
				range.length = strings[i].length();
				styledText.setStyleRange(range);
			}
			if (strings[i].startsWith("package: ")) {
				if (range == null) {
					range = new StyleRange();
				}
				range.fontStyle = SWT.BOLD;
				range.foreground = blue;
				range.start = index;
				range.length = strings[i].length();
				styledText.setStyleRange(range);
			}
			
			if (strings[i].startsWith("Name:\t*")) {
				if (range == null) {
					range = new StyleRange();
				}
				range.fontStyle = SWT.BOLD;
				range.foreground = green;
				range.start = index;
				range.length = strings[i].length();
				styledText.setStyleRange(range);
			}
			
			index += strings[i].length()+1;
		}
	}

	public void reloadProject(){
		projectSelectCombo.removeAll();
		rStrings = pcConnector.getProject();	
		for (String string : rStrings) {
			projectSelectCombo.add(string);
		}
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
