package cn.seu.edu.integrabilityevaluator.ui;

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

import cn.seu.edu.integrabilityevaluator.chart.ExtensibilityLineChart;
import cn.seu.edu.integrabilityevaluator.chart.ExtensibilityOfProjectLineChart;
import cn.seu.edu.integrabilityevaluator.chart.LineChart;
import cn.seu.edu.integrabilityevaluator.dbconnect.ProjectConnector;
import cn.seu.edu.integrabilityevaluator.dbconnect.ProjectInfoConnector;
import cn.seu.edu.integrabilityevaluator.parser.ExtensibilityDiff;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Button;

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
	public ExtensiMutiVerionShowComposite(Composite parent, int style) throws IOException {
		super(parent, style);
		
		Label lblNull = new Label(this, SWT.NONE);
		lblNull.setBounds(21, 10, 61, 17);
		lblNull.setText("Project:");
	
		projectSelectCombo.setBounds(102, 7, 98, 25);
			
		
		final CTabFolder tabFolder = new CTabFolder(this, SWT.BORDER);
		tabFolder.setLocation(13, 53);
		tabFolder.setSize(655, 480);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		final CTabItem extenTabItem = new CTabItem(tabFolder, SWT.NONE);
		extenTabItem.setText("Extensibility tendency chart");
		
		final CTabItem extenDiffTabItem = new CTabItem(tabFolder, SWT.NONE);
		extenDiffTabItem.setText("Detailed information about version changes");
		
		final Combo version1Combo = new Combo(this, SWT.NONE);
		version1Combo.setBounds(278, 7, 88, 25);
		
		final Combo version2Combo = new Combo(this, SWT.NONE);
		version2Combo.setBounds(431, 7, 88, 25);
		
		Label lblVersion = new Label(this, SWT.NONE);
		lblVersion.setBounds(215, 15, 61, 17);
		lblVersion.setText("Version1:");
		
		Label lblVersion_1 = new Label(this, SWT.NONE);
		lblVersion_1.setText("Version2:");
		lblVersion_1.setBounds(368, 15, 61, 17);
		
		
		
		
		final Button projRadioButton = new Button(this, SWT.RADIO);
		projRadioButton.setSelection(false);
		projRadioButton.setBounds(554, 10, 98, 17);
		projRadioButton.setText("System level");
		
		final Button pkgRadioButton = new Button(this, SWT.RADIO);
		
		pkgRadioButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//projRadioButton.setSelection(false);
				int index = projectSelectCombo.getSelectionIndex();
				if (index > 0) {
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
		pkgRadioButton.setBounds(554, 30, 98, 17);
		pkgRadioButton.setText("Package level");
		
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
				setCompose(tabFolder,extenDiffTabItem,projName,version1,version2);
				
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
				setCompose(tabFolder,extenDiffTabItem,projName,version1,version2);
				
			}
		});
	}
	
	private void setCompose(Composite tabFolder,CTabItem extenDiffTabItem,String projName,String version1,String version2) {
	
		
			Composite composite = new Composite(tabFolder, SWT.NONE);
			extenDiffTabItem.setControl(composite);
			
			final StyledText eDiffText = new StyledText(composite, SWT.BORDER|SWT.V_SCROLL);
			eDiffText.setBounds(0, 0, 649, 441);
			
			eDiffText.setText("");
			eDiffText.setEditable(false);
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
