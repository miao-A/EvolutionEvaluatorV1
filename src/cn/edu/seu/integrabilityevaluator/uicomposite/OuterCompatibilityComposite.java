package cn.edu.seu.integrabilityevaluator.uicomposite;

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

import cn.edu.seu.integrabilityevaluator.model.JarClassModel;
import cn.edu.seu.integrabilityevaluator.model.UnCompatibilityMIModel;
import cn.edu.seu.integrabilityevaluator.parser.InnerCompatibility;
import cn.edu.seu.integrabilityevaluator.parser.OuterCompatibility;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

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
		setLayout(new FormLayout());
		
		Label lblProjectPath = new Label(this, SWT.NONE);
		FormData fd_lblProjectPath = new FormData();
		fd_lblProjectPath.left = new FormAttachment(0, 10);
		fd_lblProjectPath.top = new FormAttachment(0, 27);
		lblProjectPath.setLayoutData(fd_lblProjectPath);
		lblProjectPath.setText("Project path:");
		
		Label lbljar = new Label(this, SWT.NONE);
		FormData fd_lbljar = new FormData();
		fd_lbljar.right = new FormAttachment(lblProjectPath, 0, SWT.RIGHT);
		fd_lbljar.left = new FormAttachment(0, 10);
		fd_lbljar.top = new FormAttachment(0, 74);
		lbljar.setLayoutData(fd_lbljar);
		lbljar.setText("Jar file path:");
		
		pathOfProjectText = new Text(this, SWT.BORDER);
		fd_lblProjectPath.right = new FormAttachment(pathOfProjectText, -17);
		FormData fd_pathOfProjectText = new FormData();
		fd_pathOfProjectText.bottom = new FormAttachment(0, 46);
		fd_pathOfProjectText.top = new FormAttachment(0, 24);
		fd_pathOfProjectText.left = new FormAttachment(0, 218);
		pathOfProjectText.setLayoutData(fd_pathOfProjectText);
		
		
		
		Button btnNewButton = new Button(this, SWT.NONE);
		fd_pathOfProjectText.right = new FormAttachment(btnNewButton, -6);
		FormData fd_btnNewButton = new FormData();
		fd_btnNewButton.bottom = new FormAttachment(0, 47);
		fd_btnNewButton.top = new FormAttachment(0, 22);

		btnNewButton.setLayoutData(fd_btnNewButton);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				DirectoryDialog folderDialog = new DirectoryDialog(shell);
				
				folderDialog.setText("Please select project file");	
				folderDialog.setFilterPath("D:/ProjectOfHW/jEditor/jEditor0.2");//"D:/ProjectOfHW/junit/junit3.4"
				folderDialog.open();
				
				pathOfProjectText.setText(folderDialog.getFilterPath());				
			}			
		});
		btnNewButton.setText("Select...");
		
		jarPathText = new Text(this, SWT.BORDER);
		FormData fd_jarPathText = new FormData();
		fd_jarPathText.right = new FormAttachment(pathOfProjectText, 0, SWT.RIGHT);
		fd_jarPathText.left = new FormAttachment(0, 218);
		fd_jarPathText.bottom = new FormAttachment(0, 93);
		fd_jarPathText.top = new FormAttachment(0, 71);
		jarPathText.setLayoutData(fd_jarPathText);
		
		Button btnSelect = new Button(this, SWT.NONE);
		fd_btnNewButton.left = new FormAttachment(btnSelect, -84);
		fd_btnNewButton.right = new FormAttachment(btnSelect, 0, SWT.RIGHT);
		FormData fd_btnSelect = new FormData();
		btnSelect.setLayoutData(fd_btnSelect);
		btnSelect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
				fileDialog.setText("Select jar file");
				fileDialog.setFilterPath("D:\\test");
				fileDialog.setFileName("jfreechart-1.0.19.jar");
				fileDialog.open();
								
				jarPathText.setText(fileDialog.getFilterPath()+"\\"+fileDialog.getFileName());				
			}
		});
		btnSelect.setText("Select...");
		
		jaruncompatibilityTable = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		FormData fd_jaruncompatibilityTable = new FormData();
		fd_jaruncompatibilityTable.left = new FormAttachment(0, 10);
		fd_jaruncompatibilityTable.bottom = new FormAttachment(100, -10);
		fd_jaruncompatibilityTable.right = new FormAttachment(100, -10);
		fd_jaruncompatibilityTable.top = new FormAttachment(0, 162);
		jaruncompatibilityTable.setLayoutData(fd_jaruncompatibilityTable);
		jaruncompatibilityTable.setLinesVisible(true);
		jaruncompatibilityTable.setHeaderVisible(false);
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
		FormData fd_lbljar_1 = new FormData();
		fd_lbljar_1.right = new FormAttachment(lblProjectPath, 0, SWT.RIGHT);
		fd_lbljar_1.top = new FormAttachment(0, 99);
		fd_lbljar_1.left = new FormAttachment(0, 10);
		lbljar_1.setLayoutData(fd_lbljar_1);
		lbljar_1.setAlignment(SWT.LEFT);
		lbljar_1.setText("Jar file dependency path:");
		
		Label label_2 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		fd_btnSelect.top = new FormAttachment(label_2, 3);
		FormData fd_label_2 = new FormData();
		fd_label_2.bottom = new FormAttachment(lblProjectPath, 15, SWT.BOTTOM);
		fd_label_2.top = new FormAttachment(lblProjectPath, 6);
		fd_label_2.right = new FormAttachment(btnNewButton, 0, SWT.RIGHT);
		fd_label_2.left = new FormAttachment(0);
		label_2.setLayoutData(fd_label_2);
		
		Label label_3 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label_3 = new FormData();
		fd_label_3.right = new FormAttachment(btnNewButton, 0, SWT.RIGHT);
		fd_label_3.bottom = new FormAttachment(0, 147);
		fd_label_3.left = new FormAttachment(0);
		fd_label_3.top = new FormAttachment(0, 145);
		label_3.setLayoutData(fd_label_3);
		
		jarDependPathText = new Text(this, SWT.BORDER);
		FormData fd_jarDependPathText = new FormData();
		fd_jarDependPathText.right = new FormAttachment(pathOfProjectText, 0, SWT.RIGHT);
		fd_jarDependPathText.left = new FormAttachment(0, 218);
		fd_jarDependPathText.bottom = new FormAttachment(0, 121);
		fd_jarDependPathText.top = new FormAttachment(0, 99);
		jarDependPathText.setLayoutData(fd_jarDependPathText);
		
		Button compatibilityBtn = new Button(this, SWT.NONE);
		fd_btnSelect.left = new FormAttachment(compatibilityBtn, -111, SWT.LEFT);
		fd_btnSelect.right = new FormAttachment(compatibilityBtn, -27);
		FormData fd_CompatibilityBtn = new FormData();
		fd_CompatibilityBtn.left = new FormAttachment(jaruncompatibilityTable, -79);
		fd_CompatibilityBtn.bottom = new FormAttachment(jaruncompatibilityTable, -45);
		fd_CompatibilityBtn.top = new FormAttachment(lbljar, -26, SWT.TOP);
		fd_CompatibilityBtn.right = new FormAttachment(jaruncompatibilityTable, 0, SWT.RIGHT);
		compatibilityBtn.setLayoutData(fd_CompatibilityBtn);
		compatibilityBtn.addSelectionListener(new SelectionAdapter() {
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
						
						
						String[] tableHeader = {"Compatible        ",jarPath+"         "};							TableItem item = new TableItem(jaruncompatibilityTable,SWT.NONE);
						item.setText(tableHeader);
						
						//System.out.println("兼容"+jarPath);
					}else {
						//System.out.println("不兼容"+jarPath);
						String[] tableHeader = {"Incompatible        ",jarPath+"         "};						TableItem item = new TableItem(jaruncompatibilityTable,SWT.NONE);
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
							String[] strings= {"Incompatible classes",model.getFromClass(),};
							item.setText(strings);
						}
						
					}	
				}			
			}
		});
		compatibilityBtn.setText("Analysis");
		
		Button btnSelect_1 = new Button(this, SWT.NONE);
		FormData fd_btnSelect_1 = new FormData();
		fd_btnSelect_1.left = new FormAttachment(compatibilityBtn, -111, SWT.LEFT);
		fd_btnSelect_1.top = new FormAttachment(btnSelect, 5);
		fd_btnSelect_1.right = new FormAttachment(compatibilityBtn, -27);
		btnSelect_1.setLayoutData(fd_btnSelect_1);
		btnSelect_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				DirectoryDialog folderDialog = new DirectoryDialog(shell);
				
				folderDialog.setText("Please select project file");	
				folderDialog.setFilterPath("D:\\");
				folderDialog.open();				
				jarDependPathText.setText(folderDialog.getFilterPath());				
			}
		});
		btnSelect_1.setText("Select...");
		
	}
	
	public void setProjectPath(String string){
		pathOfProjectText.setText(string);
	}

	public String getProjectPath(){
		return pathOfProjectText.getText();
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
