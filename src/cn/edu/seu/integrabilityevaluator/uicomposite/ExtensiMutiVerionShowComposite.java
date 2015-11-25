package cn.edu.seu.integrabilityevaluator.uicomposite;

import java.awt.Frame;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jdt.core.Flags;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.SWT;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.jfree.chart.ChartPanel;
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
import cn.edu.seu.integrabilityevaluator.chart.ExtensibilityOfProjectLineChart;
import cn.edu.seu.integrabilityevaluator.chart.LineChart;
import cn.edu.seu.integrabilityevaluator.dbconnect.ProjectConnector;
import cn.edu.seu.integrabilityevaluator.dbconnect.ProjectInfoConnector;
import cn.edu.seu.integrabilityevaluator.parser.ExtensibilityDiff;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.wb.swt.SWTResourceManager;

public class ExtensiMutiVerionShowComposite extends Composite {

	private ProjectConnector pcConnector = new ProjectConnector();
	private ArrayList<String> rStrings;
	private Combo projectSelectCombo = new Combo(this, SWT.NONE);
	private LineChart extensibilityChart = null;
	private ChartComposite chartComposite = null;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @throws IOException 
	 */
	public ExtensiMutiVerionShowComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());
		
		Label lblNull = new Label(this, SWT.NONE);
		FormData fd_lblNull = new FormData();
		fd_lblNull.top = new FormAttachment(0, 26);
		fd_lblNull.left = new FormAttachment(0, 10);
		fd_lblNull.right = new FormAttachment(0, 148);
		lblNull.setLayoutData(fd_lblNull);
		lblNull.setBounds(28, 10, 61, 17);
		lblNull.setText("Select project:");
		
		
		FormData fd_projectSelectCombo = new FormData();
		fd_projectSelectCombo.top = new FormAttachment(lblNull, -3, SWT.TOP);
		fd_projectSelectCombo.left = new FormAttachment(lblNull, 29);
		projectSelectCombo.setLayoutData(fd_projectSelectCombo);
	
		projectSelectCombo.setBounds(117, 2, 98, 25);
			
		
		final CTabFolder tabFolder = new CTabFolder(this, SWT.BORDER);
		
		tabFolder.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		FormData fd_tabFolder = new FormData();
		fd_tabFolder.right = new FormAttachment(100, -27);
		fd_tabFolder.left = new FormAttachment(0, 10);
		fd_tabFolder.top = new FormAttachment(0, 98);
		fd_tabFolder.bottom = new FormAttachment(100, -21);
		tabFolder.setLayoutData(fd_tabFolder);
		tabFolder.setLocation(10, 48);
		//tabFolder.setSize(888, 545);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		
		final CTabItem extenTabItem = new CTabItem(tabFolder, SWT.NONE);
		//extenTabItem.setText("\u53EF\u6269\u5C55\u6027\u8D8B\u52BF\u56FE");
		extenTabItem.setText("Tendency chart of extensibility");
		final CTabItem extenDiffTabItem = new CTabItem(tabFolder, SWT.NONE);
		//extenDiffTabItem.setText("\u7248\u672C\u53D8\u66F4\u8BE6\u7EC6\u4FE1\u606F");
		extenDiffTabItem.setText("Version changes information");
		
		tabFolder.setSelection(extenTabItem);
		
		final StyledText styledText = new StyledText(tabFolder, SWT.BORDER|SWT.H_SCROLL|SWT.V_SCROLL);
		extenDiffTabItem.setControl(styledText);
		final Combo version1Combo = new Combo(this, SWT.NONE);
		FormData fd_version1Combo = new FormData();
		fd_version1Combo.top = new FormAttachment(projectSelectCombo, 0, SWT.TOP);
		version1Combo.setLayoutData(fd_version1Combo);
		version1Combo.setBounds(332, 2, 88, 25);
		
		final Combo version2Combo = new Combo(this, SWT.NONE);
		FormData fd_version2Combo = new FormData();
		fd_version2Combo.top = new FormAttachment(projectSelectCombo, 0, SWT.TOP);
		version2Combo.setLayoutData(fd_version2Combo);
		version2Combo.setBounds(537, 2, 88, 25);
		
		final Label label = new Label(this, SWT.NONE);
		fd_version1Combo.right = new FormAttachment(label, 134, SWT.RIGHT);
		fd_version1Combo.left = new FormAttachment(label, 32);
		FormData fd_label = new FormData();
		fd_label.right = new FormAttachment(projectSelectCombo, 177, SWT.RIGHT);
		fd_label.left = new FormAttachment(projectSelectCombo, 20);
		fd_label.top = new FormAttachment(projectSelectCombo, 3, SWT.TOP);
		label.setLayoutData(fd_label);
		label.setBounds(243, 10, 61, 17);
		//label.setText("\u7248\u672C1\uFF1A");
		label.setText("Source version:");
		
		final Label label_1 = new Label(this, SWT.NONE);
		fd_version2Combo.right = new FormAttachment(label_1, 138, SWT.RIGHT);
		fd_version2Combo.left = new FormAttachment(label_1, 25);
		FormData fd_label_1 = new FormData();
		fd_label_1.right = new FormAttachment(version1Combo, 132, SWT.RIGHT);
		fd_label_1.left = new FormAttachment(version1Combo, 6);
		fd_label_1.top = new FormAttachment(projectSelectCombo, 3, SWT.TOP);
		label_1.setLayoutData(fd_label_1);
		//label_1.setText("\u7248\u672C2\uFF1A");
		label_1.setText("Target version:");
		label_1.setBounds(448, 10, 61, 17);
		
		
		
		
		final Button projRadioButton = new Button(this, SWT.RADIO);
		FormData fd_projRadioButton = new FormData();
		fd_projRadioButton.right = new FormAttachment(projectSelectCombo, 0, SWT.RIGHT);
		fd_projRadioButton.top = new FormAttachment(lblNull, 17);
		fd_projRadioButton.left = new FormAttachment(lblNull, 0, SWT.LEFT);
		projRadioButton.setLayoutData(fd_projRadioButton);
		projRadioButton.setText("System Level");
		projRadioButton.setSelection(false);
		projRadioButton.setBounds(653, 10, 98, 17);
		//projRadioButton.setText("\u7CFB\u7EDF\u5C42");
		
		final Button pkgRadioButton = new Button(this, SWT.RADIO);
		fd_tabFolder.right = new FormAttachment(pkgRadioButton, 0, SWT.RIGHT);
		fd_tabFolder.right = new FormAttachment(pkgRadioButton, -17, SWT.RIGHT);
		FormData fd_pkgRadioButton = new FormData();
		fd_pkgRadioButton.left = new FormAttachment(projRadioButton, 20);
		fd_pkgRadioButton.right = new FormAttachment(100, -10);
		fd_pkgRadioButton.top = new FormAttachment(projRadioButton, 0, SWT.TOP);
		pkgRadioButton.setLayoutData(fd_pkgRadioButton);
		
		pkgRadioButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//projRadioButton.setSelection(false);
				int index = projectSelectCombo.getSelectionIndex();
				if (index >= 0) {
					String projName = projectSelectCombo.getItem(index);
					extensibilityChart = new ExtensibilityLineChart("",projName);
					
					JFreeChart chart = null;										
					
					extensibilityChart.creatDataSet();		
					
					try {
						extensibilityChart.createChart();
						chart = extensibilityChart.getChart();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					chartComposite = new ChartComposite(tabFolder, SWT.NONE, chart, true);
					extenTabItem.setControl(chartComposite);
					
					chartComposite.setVisible(true);
					
					//chartComposite.redraw();
					
				}
				
			}
		});
		pkgRadioButton.setBounds(779, 10, 98, 17);
		pkgRadioButton.setText("Package Level");
		
		projRadioButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//pkgRadioButton.setSelection(false);
				int index = projectSelectCombo.getSelectionIndex();
				if (index>=0) {
					String projName = projectSelectCombo.getItem(index);
					extensibilityChart = new ExtensibilityOfProjectLineChart("",projName);
					
					JFreeChart chart = null;										
					
					extensibilityChart.creatDataSet();		
					
					try {
						extensibilityChart.createChart();
						chart = extensibilityChart.getChart();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					chartComposite = new ChartComposite(tabFolder, SWT.NONE, chart, true);
					extenTabItem.setControl(chartComposite);
					chartComposite.setVisible(true);					

				}
				
			}
		});
		
		
			
		projectSelectCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = projectSelectCombo.getSelectionIndex();
				String projName = projectSelectCombo.getItem(index);
				projRadioButton.setSelection(true);
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
				
				projRadioButton.setSelection(true);
				Event event = new Event();
				projRadioButton.notifyListeners(0, event);
				pkgRadioButton.setSelection(false);
				{
					
					//ExtensibilityLineChart extensibilityChart = new ExtensibilityLineChart("",projName);	
					//ExtensibilityOfProjectLineChart extensibilityChart = new ExtensibilityOfProjectLineChart("", projName);
					if (projRadioButton.getSelection()) {
						extensibilityChart = new ExtensibilityOfProjectLineChart("", projName);
					}else {
						extensibilityChart = new ExtensibilityLineChart("",projName);	
					}
					JFreeChart chart = null;										
					
					extensibilityChart.creatDataSet();		
					
					try {
						extensibilityChart.createChart();
						chart = extensibilityChart.getChart();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					
					chartComposite = new ChartComposite(tabFolder, SWT.NONE, chart, true);
					extenTabItem.setControl(chartComposite);
					chartComposite.setVisible(true);
					
					/*Composite chartComposite = new Composite(tabFolder, SWT.EMBEDDED);
					extenTabItem.setControl(chartComposite);
					Frame frame1 = SWT_AWT.new_Frame(chartComposite);
					frame1.add(new ChartPanel(chart));*/
					
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
				//diffComposite = setCompose(tabFolder,extenDiffTabItem,projName,version1,version2);

				styledText.setText(getDiffText(projName,version1,version2));
				textAddColor(styledText);
				//diffComposite = getCompose(tabFolder,projName,version1,version2);
				//extenTabItem.setControl(diffComposite);
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
				styledText.setText(getDiffText(projName,version1,version2));
				textAddColor(styledText);
				//setCompose(tabFolder,extenDiffTabItem,projName,version1,version2);
				//diffComposite = getCompose(tabFolder,projName,version1,version2);
				//extenTabItem.setControl(diffComposite);
			}
		});
		
		//版本菜单的可见性设置
		/*label.setVisible(false);
		label_1.setVisible(false);
		version1Combo.setVisible(false);
		version2Combo.setVisible(false);*/
		tabFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				if (event.item == extenTabItem) {
					label.setVisible(false);
					label_1.setVisible(false);
					version1Combo.setVisible(false);
					version2Combo.setVisible(false);
				}else if(event.item == extenDiffTabItem){
					label.setVisible(true);
					label_1.setVisible(true);
					version1Combo.setVisible(true);
					version2Combo.setVisible(true);
				}
			}
		});
	}
	
	private void setCompose(Composite tabFolder,CTabItem extenDiffTabItem,String projName,String version1,String version2) {
	
		
			Composite composite = new Composite(tabFolder, SWT.NONE);
			extenDiffTabItem.setControl(composite);
			
			final StyledText eDiffText = new StyledText(tabFolder, SWT.BORDER|SWT.V_SCROLL);
			//eDiffText.setBounds(0, 0, 600, 441);
			
			eDiffText.setText("");
			eDiffText.setEditable(false);
			
			FormData fd_eDiffText = new FormData();
			fd_eDiffText.right = new FormAttachment(100);
			fd_eDiffText.top = new FormAttachment(0);
			fd_eDiffText.left = new FormAttachment(0);
			fd_eDiffText.bottom = new FormAttachment(100);
			eDiffText.setLayoutData(fd_eDiffText);
			
			
			
			ProjectInfoConnector projectInfoConnector = new ProjectInfoConnector();
			ArrayList<String> list = projectInfoConnector.getVersion(projName);
			final ExtensibilityDiff extensibilityDiff = new ExtensibilityDiff(projName);
			String textString = version1 + " compare with " + version2;
							
			HashMap<String, HashMap<String,List<String>>> diffmap = extensibilityDiff.diffInProject(version1, version2);
			boolean noEffect = true;
			for (String pkgName : diffmap.keySet()) {
				HashMap<String, List<String>> map = diffmap.get(pkgName);
				if (map.containsKey("interface")||map.containsKey("+interface")||map.containsKey("-interface")) {
					textString += "\npackage: " + pkgName;
					if (map.containsKey("+interface")) {									
						textString += "\n+interface\t"+map.get("+interface").size();
						for (String string : map.get("+interface")) {
							textString += "\nName:\t"+string;
						}
					}
								
					if (map.containsKey("+abstract")) {
						textString += "\n+abstract\t"+map.get("+abstract").size();
						for (String string : map.get("+abstract")) {
							textString += "\nName:\t"+string;
						}
					}
								
					if (map.containsKey("+concrete")) {
						textString += "\n+concrete\t"+map.get("+concrete").size();
						for (String string : map.get("+concrete")) {
							textString += "\nName:\t"+string;
						}
					}
							
					if (map.containsKey("-interface")) {
						textString += "\n-interface\t"+map.get("-interface").size();
						for (String string : map.get("-interface")) {
							textString += "\nName:\t"+string;
						}
					}
								
					if (map.containsKey("-abstract")) {
						textString += "\n-abstract\t"+map.get("-abstract").size();
						for (String string : map.get("-abstract")) {
							textString += "\nName:\t"+string;
						}
					}
								
					if (map.containsKey("-concrete")) {
						textString += "\n-concrete\t"+map.get("-concrete").size();
						for (String string : map.get("-concrete")) {
							textString += "\nName:\t"+string;
						}
					}
					
					noEffect = false;
				}						
			}
						
			if (diffmap.size()==0||noEffect) {
				textString += "\nNo effect!";
			}
			eDiffText.append(textString+"\n\n");
						
			textAddColor(eDiffText);	
			System.out.println("diff print");
					
	}
	
	private String getDiffText(String projName,String version1,String version2) {
		
		
		ProjectInfoConnector projectInfoConnector = new ProjectInfoConnector();
		ArrayList<String> list = projectInfoConnector.getVersion(projName);
		final ExtensibilityDiff extensibilityDiff = new ExtensibilityDiff(projName);
		String textString = version1 + " compare with " + version2;
						
		HashMap<String, HashMap<String,List<String>>> diffmap = extensibilityDiff.diffInProject(version1, version2);
		boolean noEffect = true;
		for (String pkgName : diffmap.keySet()) {
			HashMap<String, List<String>> map = diffmap.get(pkgName);
			if (map.containsKey("interface")||map.containsKey("+interface")||map.containsKey("-interface")) {
				textString += "\npackage: " + pkgName;
				if (map.containsKey("+interface")) {									
					textString += "\n+interface\t"+map.get("+interface").size();
					for (String string : map.get("+interface")) {
						textString += "\nName:\t"+string;
					}
				}
							
				if (map.containsKey("+abstract")) {
					textString += "\n+abstract\t"+map.get("+abstract").size();
					for (String string : map.get("+abstract")) {
						textString += "\nName:\t"+string;
					}
				}
							
				if (map.containsKey("+concrete")) {
					textString += "\n+concrete\t"+map.get("+concrete").size();
					for (String string : map.get("+concrete")) {
						textString += "\nName:\t"+string;
					}
				}
						
				if (map.containsKey("-interface")) {
					textString += "\n-interface\t"+map.get("-interface").size();
					for (String string : map.get("-interface")) {
						textString += "\nName:\t"+string;
					}
				}
							
				if (map.containsKey("-abstract")) {
					textString += "\n-abstract\t"+map.get("-abstract").size();
					for (String string : map.get("-abstract")) {
						textString += "\nName:\t"+string;
					}
				}
							
				if (map.containsKey("-concrete")) {
					textString += "\n-concrete\t"+map.get("-concrete").size();
					for (String string : map.get("-concrete")) {
						textString += "\nName:\t"+string;
					}
				}
				
				noEffect = false;
			}						
		}
					
		if (diffmap.size()==0||noEffect) {
			textString += "\nNo effect!";
		}
		textString += "\n\n";
					
		
		return textString;
				
	}

		
	public void reloadProject(){
		projectSelectCombo.removeAll();
		rStrings = pcConnector.getProject();	
		for (String string : rStrings) {
			projectSelectCombo.add(string);
		}
	}
	
	private void textAddColor(StyledText styledText){
		String text = styledText.getText();
		String[] strings = text.split("\n");
		int index = 0;
		for (int i = 0; i < strings.length; i++) {
		
			Color red = Display.getDefault().getSystemColor(SWT.COLOR_RED);
			Color blue = Display.getDefault().getSystemColor(SWT.COLOR_BLUE);
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
			
			index += strings[i].length()+1;
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
