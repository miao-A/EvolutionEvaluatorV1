package cn.seu.edu.integrabilityevaluator.ui;

import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import cn.seu.edu.integrabilityevaluator.model.JarClassModel;
import cn.seu.edu.integrabilityevaluator.model.UnCompatibilityMIModel;
import cn.seu.edu.integrabilityevaluator.parser.InnerCompatibility;
import cn.seu.edu.integrabilityevaluator.parser.OuterCompatibility;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.TableViewer;

public class OuterCompatibilityMutiVersionComposite extends Composite {
	private Text pathOfOldProjectText;
	private Text jarPathText;
	private TableEditor editor = null;
	
	String strings = new String();
	private Text jarDependPathText;
	private Table jaruncompatibilityTable1;
	private Table table;
	private Text pathOfNewProjectText;
	private Text resultOneText;
	private Text resultTwoText;
	private Table jaruncompatibilityTable2;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public OuterCompatibilityMutiVersionComposite(Composite parent, int style) {
		super(parent, style);
		
		Label label = new Label(this, SWT.NONE);
		label.setAlignment(SWT.RIGHT);
		label.setBounds(25, 13, 103, 17);
		label.setText("\u9879\u76EE\u8DEF\u5F841\uFF1A");
		
		Label lbljar = new Label(this, SWT.NONE);
		lbljar.setAlignment(SWT.RIGHT);
		lbljar.setText("\u5916\u90E8jar\u5305\u8DEF\u5F84\uFF1A");
		lbljar.setBounds(25, 89, 103, 17);
		
		pathOfOldProjectText = new Text(this, SWT.BORDER);
		pathOfOldProjectText.setBounds(134, 13, 346, 22);
		
		
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				DirectoryDialog folderDialog = new DirectoryDialog(shell);
				
				folderDialog.setText("请选择项目文件");	
				folderDialog.setFilterPath("D:/ProjectOfHW/jEditor/jEditor0.2");//"D:/ProjectOfHW/junit/junit3.4"
				folderDialog.open();
				
				pathOfOldProjectText.setText(folderDialog.getFilterPath());				
			}			
		});
		btnNewButton.setBounds(486, 10, 53, 22);
		btnNewButton.setText("\u8DEF\u5F84...");
		
		jarPathText = new Text(this, SWT.BORDER);
		jarPathText.setBounds(134, 86, 346, 22);
		
		Button button = new Button(this, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
				fileDialog.setText("选择jar文件");
				fileDialog.setFilterPath("D:\\test");
				fileDialog.setFileName("jfreechart-1.0.19.jar");
				fileDialog.open();
								
				jarPathText.setText(fileDialog.getFilterPath()+"\\"+fileDialog.getFileName());				
			}
		});
		button.setBounds(486, 83, 53, 22);
		button.setText("\u8DEF\u5F84...");
		
		jaruncompatibilityTable1 = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		jaruncompatibilityTable1.setLinesVisible(true);
		jaruncompatibilityTable1.setHeaderVisible(true);
		jaruncompatibilityTable1.setBounds(10, 183, 711, 159);
		
		
		
		jaruncompatibilityTable2 = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		jaruncompatibilityTable2.setLinesVisible(true);
		jaruncompatibilityTable2.setHeaderVisible(true);
		jaruncompatibilityTable2.setBounds(10, 385, 711, 159);
		/*String[] tableHeader = {"---------------------------","---------------------------------------------------------------------------"};	
		
		for (int i = 0; i < tableHeader.length; i++)  
	    {  					
			TableColumn tableColumn = new TableColumn(jaruncompatibilityTable1, SWT.NONE);
			tableColumn.setText(tableHeader[i]);  
			// 设置表头可移动，默认为false  
			tableColumn.setMoveable(true); 
	    	tableColumn.pack();
	    	
	    }*/
		
		Label lbljar_1 = new Label(this, SWT.NONE);
		lbljar_1.setAlignment(SWT.RIGHT);
		lbljar_1.setText("\u5916\u90E8jar\u5305\u4F9D\u8D56\u5305\u8DEF\u5F84\uFF1A");
		lbljar_1.setBounds(0, 119, 128, 17);
		
		Label label_2 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_2.setBounds(10, 64, 577, 2);
		
		Label label_3 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_3.setBounds(10, 142, 577, 2);
		
		jarDependPathText = new Text(this, SWT.BORDER);
		jarDependPathText.setBounds(134, 114, 346, 22);
		
		Button CompatibilityBtn = new Button(this, SWT.NONE);		
		
		String[] tableHeader = {"        包名.类名        ", "       不兼容的jar包类        "};
		
		for (int i = 0; i < tableHeader.length; i++)  
	    {  					
			TableColumn tableColumn = new TableColumn(jaruncompatibilityTable1, SWT.NONE);
			tableColumn.setText(tableHeader[i]);  
			// 设置表头可移动，默认为false  
			tableColumn.setMoveable(false); 
	    	tableColumn.pack();
	    	
	    }
		
		
		for (int i = 0; i < tableHeader.length; i++)  
	    {  					
			TableColumn tableColumn = new TableColumn(jaruncompatibilityTable2, SWT.NONE);
			tableColumn.setText(tableHeader[i]);  
			// 设置表头可移动，默认为false  
			tableColumn.setMoveable(false); 
	    	tableColumn.pack();
	    	
	    }
		CompatibilityBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				jaruncompatibilityTable1.removeAll();	
				jaruncompatibilityTable2.removeAll();
						
				String pathOfOldProject = pathOfOldProjectText.getText(); // "D:/ProjectOfHW/jEditor/jEditor0.4.1/src/org/jeditor/gui";
				String pathOfNewProject = pathOfNewProjectText.getText();
				String jarPath = jarPathText.getText(); //"D:/ProjectOfHW/jEditor/jEditor0.4.2/src/org/jeditor/gui";
				String jarDependPath = jarDependPathText.getText();	
				
				
				OuterCompatibility oldOuterCompatibility = new OuterCompatibility(pathOfOldProject, null);
				OuterCompatibility newOuterCompatibility = new OuterCompatibility(pathOfNewProject, null);
				
				resultOneText.setText("");
				resultTwoText.setText("");
				
				
				if (jarPath != "") {
					if (oldOuterCompatibility.jarCompatibility(jarPath, jarDependPath)) {						
						
						/*String[] tableHeader = {"       版本1兼容        ","        "+jarPath+"         "};	
						TableItem item = new TableItem(jaruncompatibilityTable,SWT.NONE);
						item.setText(tableHeader);*/
						resultOneText.setText("版本1兼容:"+jarPath);
						//System.out.println("兼容"+jarPath);
					}else {
						//System.out.println("不兼容"+jarPath);
						
						/*String[] tableHeader = {"        包名.类名        ", "       不兼容的jar包类        "};
						
						for (int i = 0; i < tableHeader.length; i++)  
					    {  					
							TableColumn tableColumn = new TableColumn(jaruncompatibilityTable1, SWT.NONE);
							tableColumn.setText(tableHeader[i]);  
							// 设置表头可移动，默认为false  
							tableColumn.setMoveable(false); 
					    	tableColumn.pack();
					    	
					    }*/
						
						/*String[] tableHeader1 = {"        版本1不兼容        ","        "+jarPath+"         "};	
						TableItem item = new TableItem(jaruncompatibilityTable1,SWT.NONE);
						item.setText(tableHeader1);*/					
						resultOneText.setText("版本1不兼容:"+jarPath);						
						List<JarClassModel> lists = oldOuterCompatibility.getUncompatibilityClassModels();

						for (JarClassModel model : lists) {
							TableItem item = new TableItem(jaruncompatibilityTable1,SWT.NONE);
							String[] strings= {model.getFromClass() ,"        "+model.getClassName()};
							item.setText(strings);
						} 
						
					}
					
					if (newOuterCompatibility.jarCompatibility(jarPath, jarDependPath)) {						
						
						/*String[] tableHeader = {"        版本2兼容        ","        "+jarPath+"         "};	
						TableItem item = new TableItem(jaruncompatibilityTable,SWT.NONE);
						item.setText(tableHeader);*/
						resultTwoText.setText("版本2兼容:"+jarPath);
						//System.out.println("兼容"+jarPath);
					}else {
						//System.out.println("不兼容"+jarPath);
						/*String[] tableHeader = {"        包名.类名        ", "       不兼容的jar包类        "};		
						for (int i = 0; i < tableHeader.length; i++)  
					    {  					
							TableColumn tableColumn = new TableColumn(jaruncompatibilityTable2, SWT.NONE);
							tableColumn.setText(tableHeader[i]);  
							// 设置表头可移动，默认为false  
							tableColumn.setMoveable(false); 
					    	tableColumn.pack();
					    	
					    }*/
						/*String[] tableHeader = {"        版本2不兼容        ","        "+jarPath+"         "};	
						TableItem item = new TableItem(jaruncompatibilityTable2,SWT.NONE);
						item.setText(tableHeader);*/		
						//TableItem item = new TableItem(jaruncompatibilityTable2,SWT.NONE);
						resultTwoText.setText("版本2不兼容:"+jarPath);
						List<JarClassModel> lists = newOuterCompatibility.getUncompatibilityClassModels();

						for (JarClassModel model : lists) {
							TableItem item = new TableItem(jaruncompatibilityTable2,SWT.NONE);
							String[] strings= {model.getFromClass() ,"        "+model.getClassName()};
							item.setText(strings);
						}
						
					}
				}			
			}
		});
		
		CompatibilityBtn.setBounds(599, 27, 79, 69);
		CompatibilityBtn.setText("\u5206\u6790");
		
		Button button_1 = new Button(this, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				DirectoryDialog folderDialog = new DirectoryDialog(shell);
				
				folderDialog.setText("请选择项目文件");	
				folderDialog.setFilterPath("D:\\test\\TestJar");
				folderDialog.open();				
				jarDependPathText.setText(folderDialog.getFilterPath());				
			}
		});
		button_1.setText("\u8DEF\u5F84...");
		button_1.setBounds(486, 111, 53, 22);
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setText("\u9879\u76EE\u8DEF\u5F842\uFF1A");
		label_1.setAlignment(SWT.RIGHT);
		label_1.setBounds(25, 41, 103, 17);
		
		pathOfNewProjectText = new Text(this, SWT.BORDER);
		pathOfNewProjectText.setBounds(134, 41, 346, 22);
		
		Button button_2 = new Button(this, SWT.NONE);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				DirectoryDialog folderDialog = new DirectoryDialog(shell);
				
				folderDialog.setText("请选择项目文件");	
				folderDialog.setFilterPath("D:/ProjectOfHW/jEditor/jEditor0.3");//"D:/ProjectOfHW/junit/junit3.4"
				folderDialog.open();
				
				pathOfNewProjectText.setText(folderDialog.getFilterPath());			
			}
		});
		button_2.setText("\u8DEF\u5F84...");
		button_2.setBounds(486, 36, 53, 22);
		
		resultOneText = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		resultOneText.setBounds(10, 150, 334, 23);
		
		resultTwoText = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		resultTwoText.setBounds(10, 356, 334, 23);

		
		
		
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
