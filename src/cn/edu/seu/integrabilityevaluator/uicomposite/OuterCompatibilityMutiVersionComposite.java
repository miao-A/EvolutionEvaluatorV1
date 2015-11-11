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
		setLayout(new FormLayout());
		
		Label lblProjectPath = new Label(this, SWT.NONE);
		FormData fd_lblProjectPath = new FormData();
		fd_lblProjectPath.left = new FormAttachment(0, 10);
		fd_lblProjectPath.top = new FormAttachment(0, 16);
		lblProjectPath.setLayoutData(fd_lblProjectPath);
		lblProjectPath.setText("Project version1:");
		
		Label lbljar = new Label(this, SWT.NONE);
		FormData fd_lbljar = new FormData();
		fd_lbljar.right = new FormAttachment(lblProjectPath, 0, SWT.RIGHT);
		fd_lbljar.top = new FormAttachment(0, 89);
		fd_lbljar.left = new FormAttachment(0, 10);
		lbljar.setLayoutData(fd_lbljar);
		lbljar.setText("Jar file path:");
		
		pathOfOldProjectText = new Text(this, SWT.BORDER);
		fd_lblProjectPath.right = new FormAttachment(pathOfOldProjectText, -24);
		FormData fd_pathOfOldProjectText = new FormData();
		fd_pathOfOldProjectText.bottom = new FormAttachment(0, 35);
		fd_pathOfOldProjectText.top = new FormAttachment(0, 13);
		fd_pathOfOldProjectText.left = new FormAttachment(0, 200);
		pathOfOldProjectText.setLayoutData(fd_pathOfOldProjectText);
		
		
		
		Button btnNewButton = new Button(this, SWT.NONE);
		fd_pathOfOldProjectText.right = new FormAttachment(btnNewButton, -6);
		FormData fd_btnNewButton = new FormData();
		fd_btnNewButton.bottom = new FormAttachment(0, 35);
		fd_btnNewButton.top = new FormAttachment(0, 13);
		btnNewButton.setLayoutData(fd_btnNewButton);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				DirectoryDialog folderDialog = new DirectoryDialog(shell);
				
				//folderDialog.setText("请选择项目文件");
				folderDialog.setText("Plesse select project file");
				folderDialog.setFilterPath("D:/ProjectOfHW/jEditor/jEditor0.2");//"D:/ProjectOfHW/junit/junit3.4"
				folderDialog.open();
				
				pathOfOldProjectText.setText(folderDialog.getFilterPath());				
			}			
		});
		btnNewButton.setText("Select...");
		
		jarPathText = new Text(this, SWT.BORDER);
		FormData fd_jarPathText = new FormData();
		fd_jarPathText.left = new FormAttachment(lbljar, 24);
		fd_jarPathText.bottom = new FormAttachment(0, 108);
		fd_jarPathText.top = new FormAttachment(0, 86);
		jarPathText.setLayoutData(fd_jarPathText);
		
		Button btnPath_1 = new Button(this, SWT.NONE);
		fd_jarPathText.right = new FormAttachment(btnPath_1, -6);
		fd_btnNewButton.left = new FormAttachment(btnPath_1, -76);
		fd_btnNewButton.right = new FormAttachment(btnPath_1, 0, SWT.RIGHT);
		FormData fd_btnPath_1 = new FormData();
		fd_btnPath_1.bottom = new FormAttachment(0, 108);
		fd_btnPath_1.top = new FormAttachment(0, 86);
		btnPath_1.setLayoutData(fd_btnPath_1);
		btnPath_1.addSelectionListener(new SelectionAdapter() {
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
		btnPath_1.setText("Select...");
		
		jaruncompatibilityTable1 = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		FormData fd_jaruncompatibilityTable1 = new FormData();
		fd_jaruncompatibilityTable1.right = new FormAttachment(100, -10);
		fd_jaruncompatibilityTable1.left = new FormAttachment(0, 10);
		jaruncompatibilityTable1.setLayoutData(fd_jaruncompatibilityTable1);
		jaruncompatibilityTable1.setLinesVisible(true);
		jaruncompatibilityTable1.setHeaderVisible(true);
		
		
		
		jaruncompatibilityTable2 = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		FormData fd_jaruncompatibilityTable2 = new FormData();
		fd_jaruncompatibilityTable2.bottom = new FormAttachment(100, -10);
		fd_jaruncompatibilityTable2.right = new FormAttachment(100, -10);
		fd_jaruncompatibilityTable2.top = new FormAttachment(0, 437);
		fd_jaruncompatibilityTable2.left = new FormAttachment(0, 10);
		jaruncompatibilityTable2.setLayoutData(fd_jaruncompatibilityTable2);
		jaruncompatibilityTable2.setLinesVisible(true);
		jaruncompatibilityTable2.setHeaderVisible(true);
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
		FormData fd_lbljar_1 = new FormData();
		fd_lbljar_1.top = new FormAttachment(0, 119);
		fd_lbljar_1.left = new FormAttachment(0, 10);
		lbljar_1.setLayoutData(fd_lbljar_1);
		lbljar_1.setText("Jar file dependency path:");
		
		Label label_2 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label_2 = new FormData();
		fd_label_2.left = new FormAttachment(0, 10);
		fd_label_2.bottom = new FormAttachment(0, 67);
		fd_label_2.top = new FormAttachment(0, 65);
		label_2.setLayoutData(fd_label_2);
		
		Label label_3 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label_3 = new FormData();
		fd_label_3.top = new FormAttachment(lbljar_1, 6);
		fd_label_3.right = new FormAttachment(100, -95);
		fd_label_3.left = new FormAttachment(0, 10);
		label_3.setLayoutData(fd_label_3);
		
		jarDependPathText = new Text(this, SWT.BORDER);
		fd_lbljar_1.right = new FormAttachment(jarDependPathText, -6);
		FormData fd_jarDependPathText = new FormData();
		fd_jarDependPathText.left = new FormAttachment(0, 200);
		fd_jarDependPathText.bottom = new FormAttachment(0, 136);
		fd_jarDependPathText.top = new FormAttachment(0, 114);
		jarDependPathText.setLayoutData(fd_jarDependPathText);
		
		Button CompatibilityBtn = new Button(this, SWT.NONE);
		fd_btnPath_1.left = new FormAttachment(CompatibilityBtn, -82, SWT.LEFT);
		fd_label_2.right = new FormAttachment(CompatibilityBtn, -6);
		fd_btnPath_1.right = new FormAttachment(CompatibilityBtn, -6);
		FormData fd_CompatibilityBtn = new FormData();
		fd_CompatibilityBtn.left = new FormAttachment(jaruncompatibilityTable1, -79);
		fd_CompatibilityBtn.right = new FormAttachment(jaruncompatibilityTable1, 0, SWT.RIGHT);
		fd_CompatibilityBtn.bottom = new FormAttachment(0, 106);
		fd_CompatibilityBtn.top = new FormAttachment(0, 37);
		CompatibilityBtn.setLayoutData(fd_CompatibilityBtn);
		
		//String[] tableHeader = {"        包名.类名        ", "       不兼容的jar包类        "};
		String[] tableHeader = {"        Qualified class name        ", "       Incompatible classes in jar file        "};
		
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
						resultOneText.setText("Version1 compatible:"+jarPath);
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
						resultOneText.setText("Version1 incompatible:"+jarPath);						
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
						resultTwoText.setText("Version2 compatible::"+jarPath);
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
						resultTwoText.setText("Version2 incompatible::"+jarPath);
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
		CompatibilityBtn.setText("Analysis");
		
		Button btnPath_2 = new Button(this, SWT.NONE);
		fd_jarDependPathText.right = new FormAttachment(btnPath_2, -6);
		FormData fd_btnPath_2 = new FormData();
		fd_btnPath_2.right = new FormAttachment(btnPath_1, 0, SWT.RIGHT);
		fd_btnPath_2.left = new FormAttachment(btnPath_1, -76);
		
		fd_btnPath_2.bottom = new FormAttachment(0, 136);
		fd_btnPath_2.top = new FormAttachment(0, 114);
		btnPath_2.setLayoutData(fd_btnPath_2);
		btnPath_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				DirectoryDialog folderDialog = new DirectoryDialog(shell);
				
				//folderDialog.setText("请选择项目文件");	
				folderDialog.setText("Plesse select project file");
				folderDialog.setFilterPath("D:\\test\\TestJar");
				folderDialog.open();				
				jarDependPathText.setText(folderDialog.getFilterPath());				
			}
		});
		btnPath_2.setText("Select...");
		
		Label lblProjcetPath = new Label(this, SWT.NONE);
		FormData fd_lblProjcetPath = new FormData();
		fd_lblProjcetPath.right = new FormAttachment(lblProjectPath, 0, SWT.RIGHT);
		fd_lblProjcetPath.top = new FormAttachment(0, 41);
		fd_lblProjcetPath.left = new FormAttachment(0, 10);
		lblProjcetPath.setLayoutData(fd_lblProjcetPath);
		lblProjcetPath.setText("Projcet version2:");
		
		pathOfNewProjectText = new Text(this, SWT.BORDER);
		FormData fd_pathOfNewProjectText = new FormData();
		fd_pathOfNewProjectText.left = new FormAttachment(lblProjcetPath, 24);
		fd_pathOfNewProjectText.bottom = new FormAttachment(0, 63);
		fd_pathOfNewProjectText.top = new FormAttachment(0, 41);
		pathOfNewProjectText.setLayoutData(fd_pathOfNewProjectText);
		
		Button btnPath = new Button(this, SWT.NONE);
		fd_pathOfNewProjectText.right = new FormAttachment(btnPath, -6);
		FormData fd_btnPath = new FormData();
		fd_btnPath.left = new FormAttachment(CompatibilityBtn, -82, SWT.LEFT);
		fd_btnPath.right = new FormAttachment(CompatibilityBtn, -6);
		fd_btnPath.bottom = new FormAttachment(0, 62);
		fd_btnPath.top = new FormAttachment(0, 40);
		btnPath.setLayoutData(fd_btnPath);
		btnPath.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				DirectoryDialog folderDialog = new DirectoryDialog(shell);
				
				//folderDialog.setText("请选择项目文件");	
				folderDialog.setText("Plesse select project file");
				folderDialog.setFilterPath("D:/ProjectOfHW/jEditor/jEditor0.3");//"D:/ProjectOfHW/junit/junit3.4"
				folderDialog.open();
				
				pathOfNewProjectText.setText(folderDialog.getFilterPath());			
			}
		});
		btnPath.setText("Select...");
		
		resultOneText = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		fd_jaruncompatibilityTable1.top = new FormAttachment(resultOneText, 10);
		fd_label_3.bottom = new FormAttachment(resultOneText, -6);
		FormData fd_resultOneText = new FormData();
		fd_resultOneText.right = new FormAttachment(0, 474);
		fd_resultOneText.top = new FormAttachment(0, 150);
		fd_resultOneText.left = new FormAttachment(0, 10);
		resultOneText.setLayoutData(fd_resultOneText);
		
		resultTwoText = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		fd_jaruncompatibilityTable1.bottom = new FormAttachment(resultTwoText, -6);
		FormData fd_resultTwoText = new FormData();
		fd_resultTwoText.right = new FormAttachment(resultOneText, 0, SWT.RIGHT);
		fd_resultTwoText.left = new FormAttachment(0, 10);
		fd_resultTwoText.bottom = new FormAttachment(jaruncompatibilityTable2, -6);
		resultTwoText.setLayoutData(fd_resultTwoText);

		
		
		
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
