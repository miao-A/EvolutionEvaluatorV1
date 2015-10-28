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

public class OuterCompatibilityComposite extends Composite {
	private Text pathOfProjectText;
	private Text jarPathText;
	private TableEditor editor = null;
	
	String strings = new String();
	private Text jarDependPathText;
	private Table jaruncompatibilityTable;
	private Table table;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public OuterCompatibilityComposite(Composite parent, int style) {
		super(parent, style);
		
		Label lblProjectPath = new Label(this, SWT.NONE);
		lblProjectPath.setAlignment(SWT.RIGHT);
		lblProjectPath.setBounds(27, 27, 103, 17);
		lblProjectPath.setText("Project path:");
		
		Label lbljar = new Label(this, SWT.NONE);
		lbljar.setAlignment(SWT.RIGHT);
		lbljar.setText("Path of jar file:");
		lbljar.setBounds(27, 74, 142, 17);
		
		pathOfProjectText = new Text(this, SWT.BORDER);
		pathOfProjectText.setBounds(136, 24, 379, 22);
		
		
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				DirectoryDialog folderDialog = new DirectoryDialog(shell);
				
				//folderDialog.setText("请选择项目文件");	
				folderDialog.setText("Please select project file");
				folderDialog.setFilterPath("D:/ProjectOfHW/jEditor/jEditor0.2");//"D:/ProjectOfHW/junit/junit3.4"
				folderDialog.open();
				
				pathOfProjectText.setText(folderDialog.getFilterPath());				
			}			
		});
		btnNewButton.setBounds(488, 24, 53, 22);
		btnNewButton.setText("Path...");
		
		jarPathText = new Text(this, SWT.BORDER);
		jarPathText.setBounds(175, 71, 307, 22);
		
		Button btnPath = new Button(this, SWT.NONE);
		btnPath.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
				//fileDialog.setText("选择jar文件");
				fileDialog.setText("Select jar file");
				fileDialog.setFilterPath("D:\\test");
				fileDialog.setFileName("jfreechart-1.0.19.jar");
				fileDialog.open();
								
				jarPathText.setText(fileDialog.getFilterPath()+"\\"+fileDialog.getFileName());				
			}
		});
		btnPath.setBounds(488, 71, 53, 22);
		btnPath.setText("Path...");
		
		jaruncompatibilityTable = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		jaruncompatibilityTable.setLinesVisible(true);
		jaruncompatibilityTable.setHeaderVisible(false);
		jaruncompatibilityTable.setBounds(10, 162, 711, 352);
		//String[] comboItems = {"1.7","1.6","1.5","1.4"};
		
		String[] tableHeader = {"---------------------------","---------------------------------------------------------------------------"};	
		/*for (int i = 0; i < tableHeader.length; i++)  
	    {  					
			TableColumn tableColumn = new TableColumn(jdkuncompatibilityTable, SWT.NONE);
			tableColumn.setText(tableHeader[i]);  
			// 设置表头可移动，默认为false  
			tableColumn.setMoveable(true); 
	    	tableColumn.pack();
	    	
	    }*/
		
		for (int i = 0; i < tableHeader.length; i++)  
	    {  					
			TableColumn tableColumn = new TableColumn(jaruncompatibilityTable, SWT.NONE);
			tableColumn.setText(tableHeader[i]);  
			// 设置表头可移动，默认为false  
			tableColumn.setMoveable(true); 
	    	tableColumn.pack();
	    	
	    }
		
		Label lbljar_1 = new Label(this, SWT.NONE);
		lbljar_1.setAlignment(SWT.RIGHT);
		lbljar_1.setText("Path of jar file dependency:");
		lbljar_1.setBounds(2, 99, 167, 17);
		
		Label label_2 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_2.setBounds(0, 63, 577, 2);
		
		Label label_3 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_3.setBounds(0, 145, 577, 2);
		
		jarDependPathText = new Text(this, SWT.BORDER);
		jarDependPathText.setBounds(175, 99, 307, 22);
		
		Button CompatibilityBtn = new Button(this, SWT.NONE);		
		CompatibilityBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
//				jdkuncompatibilityTable.removeAll();
				jaruncompatibilityTable.removeAll();
				
						
				String pathOfProject = pathOfProjectText.getText(); // "D:/ProjectOfHW/jEditor/jEditor0.4.1/src/org/jeditor/gui";
				String jarPath = jarPathText.getText(); //"D:/ProjectOfHW/jEditor/jEditor0.4.2/src/org/jeditor/gui";
				String jarDependPath = jarDependPathText.getText();	
				
//				int jdkIndex = jdkVersionCombo.getSelectionIndex();
				
				OuterCompatibility outerCompatibility = new OuterCompatibility(pathOfProject, null);
/*				if (outerCompatibility.jdkCompatibility(jdkVersionCombo.getItem(jdkIndex))) {
					
					String[] tableHeader = {"        兼容        ","        "+jdkVersionCombo.getItem(jdkIndex)+"         "};	
					TableItem item = new TableItem(jdkuncompatibilityTable,SWT.NONE);
					item.setText(tableHeader);						
	
				}else {
					TableItem item =null;
					System.out.println("不兼容"+jdkVersionCombo.getItem(jdkIndex));
					
					String[] tableHeader = {"        不兼容        ","        "+jdkVersionCombo.getItem(jdkIndex)+"         "};
					item = new TableItem(jdkuncompatibilityTable,SWT.NONE);
					item.setText(tableHeader);
					
					
					List<String> lists = outerCompatibility.getuncompatibilityfileList();					
					for (String string : lists) {
						item = new TableItem(jdkuncompatibilityTable,SWT.NONE);
						String[] strings= {"位置",string};
						item.setText(strings);				
						
					}
				}*/
				
				
				if (jarPath != "") {
					if (outerCompatibility.jarCompatibility(jarPath, jarDependPath)) {
						
						
						String[] tableHeader = {"Compatible        ",jarPath+"         "};	
						TableItem item = new TableItem(jaruncompatibilityTable,SWT.NONE);
						item.setText(tableHeader);
						
						System.out.println("Compatible"+jarPath);
					}else {
						System.out.println("Incompatible"+jarPath);
						String[] tableHeader = {"Incompatible        ",jarPath+"         "};	
						TableItem item = new TableItem(jaruncompatibilityTable,SWT.NONE);
						item.setText(tableHeader);
						/*TableItem item = new TableItem(jaruncompatibilityTable,SWT.NONE);
						item.setText(tableHeader);*/
						/*for (int i = 0; i < tableHeader.length; i++)  
					    {  					
							TableColumn tableColumn = new TableColumn(jaruncompatibilityTable, SWT.NONE);
							tableColumn.setText(tableHeader[i]);  
							// 设置表头可移动，默认为false  
							tableColumn.setMoveable(false); 
					    	tableColumn.pack();
					    	
					    }*/
						
						List<JarClassModel> lists = outerCompatibility.getUncompatibilityClassModels();

						for (JarClassModel model : lists) {
							item = new TableItem(jaruncompatibilityTable,SWT.NONE);
							String[] strings= {"Incompatible classes       ",model.getFromClass(),};
							item.setText(strings);
						}
						
					}	
				}			
			}
		});
		
		CompatibilityBtn.setBounds(617, 48, 79, 69);
		CompatibilityBtn.setText("Analysis");
		
		Button btnPath_1 = new Button(this, SWT.NONE);
		btnPath_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				DirectoryDialog folderDialog = new DirectoryDialog(shell);
				
				//folderDialog.setText("请选择项目文件");	
				folderDialog.setText("Please select project file");
				folderDialog.setFilterPath("D:\\test\\TestJar");
				folderDialog.open();				
				jarDependPathText.setText(folderDialog.getFilterPath());				
			}
		});
		btnPath_1.setText("Path...");
		btnPath_1.setBounds(488, 99, 53, 22);
		
		
		
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
