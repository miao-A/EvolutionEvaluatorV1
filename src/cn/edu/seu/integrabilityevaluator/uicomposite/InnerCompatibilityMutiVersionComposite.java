package cn.edu.seu.integrabilityevaluator.uicomposite;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import cn.edu.seu.integrabilityevaluator.model.AbstractClassModel;
import cn.edu.seu.integrabilityevaluator.model.ConstructorMethodModel;
import cn.edu.seu.integrabilityevaluator.model.MethodModel;
import cn.edu.seu.integrabilityevaluator.model.UnCompatibilityMIModel;
import cn.edu.seu.integrabilityevaluator.modelcompatibilityrecoder.ChangeStatus;
import cn.edu.seu.integrabilityevaluator.modelcompatibilityrecoder.ClassCompatibilityRecoder;
import cn.edu.seu.integrabilityevaluator.modelcompatibilityrecoder.ConstructorMethodRecoder;
import cn.edu.seu.integrabilityevaluator.modelcompatibilityrecoder.MethodRecoder;
import cn.edu.seu.integrabilityevaluator.parser.Compatibility;
import cn.edu.seu.integrabilityevaluator.parser.InnerCompatibility;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

public class InnerCompatibilityMutiVersionComposite extends Composite {
	private Text pathOfOldProjectText;
	private Table uncompatibilityTableOne;

	private TableEditor editor = null;
	
	String strings = new String();
	private Text pathOfNewProjectText;
	private Table uncompatibilityTableTwo;
	private Text ResultOneText;
	private Text ResultTwoText;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public InnerCompatibilityMutiVersionComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());
		
		Label lblProjectPath = new Label(this, SWT.NONE);
		FormData fd_lblProjectPath = new FormData();
		fd_lblProjectPath.left = new FormAttachment(0, 10);
		fd_lblProjectPath.top = new FormAttachment(0, 18);
		lblProjectPath.setLayoutData(fd_lblProjectPath);
		lblProjectPath.setText("Project version1:");
		
		pathOfOldProjectText = new Text(this, SWT.BORDER);
		fd_lblProjectPath.right = new FormAttachment(pathOfOldProjectText, -6);
		FormData fd_pathOfOldProjectText = new FormData();
		fd_pathOfOldProjectText.bottom = new FormAttachment(0, 35);
		fd_pathOfOldProjectText.top = new FormAttachment(0, 13);
		fd_pathOfOldProjectText.left = new FormAttachment(0, 143);
		pathOfOldProjectText.setLayoutData(fd_pathOfOldProjectText);
		
		Button btnNewButton = new Button(this, SWT.NONE);
		fd_pathOfOldProjectText.right = new FormAttachment(btnNewButton, -6);
		FormData fd_btnNewButton = new FormData();
		fd_btnNewButton.top = new FormAttachment(0, 13);
		btnNewButton.setLayoutData(fd_btnNewButton);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				DirectoryDialog folderDialog = new DirectoryDialog(shell);
				
				folderDialog.setText("Please select project file");	
				folderDialog.setFilterPath("D:/ProjectOfHW/junit/junit3.4");//"D:/ProjectOfHW/junit/junit3.4"
				folderDialog.open();
				
				pathOfOldProjectText.setText(folderDialog.getFilterPath());
				
			}
			
			
		});
		btnNewButton.setText("Select...");
		
		uncompatibilityTableOne = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		FormData fd_uncompatibilityTableOne = new FormData();
		fd_uncompatibilityTableOne.right = new FormAttachment(100, -10);
		fd_uncompatibilityTableOne.left = new FormAttachment(0, 10);
		uncompatibilityTableOne.setLayoutData(fd_uncompatibilityTableOne);
		uncompatibilityTableOne.setHeaderVisible(true);
		uncompatibilityTableOne.setLinesVisible(true);
	
//		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(uncompatibilityTableOne);

		uncompatibilityTableTwo = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		FormData fd_uncompatibilityTableTwo = new FormData();
		fd_uncompatibilityTableTwo.bottom = new FormAttachment(100, -10);
		fd_uncompatibilityTableTwo.right = new FormAttachment(100, -10);
		fd_uncompatibilityTableTwo.left = new FormAttachment(0, 10);
		uncompatibilityTableTwo.setLayoutData(fd_uncompatibilityTableTwo);
		uncompatibilityTableTwo.setLinesVisible(true);
		uncompatibilityTableTwo.setHeaderVisible(true);
		
		
		editor = new TableEditor(uncompatibilityTableOne);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		
		String[] tableHeader = {"    Qualified class name    ", "    Incompatible classes    ","    Incompatible methods    ","    required signature    ","    Actual signature    "};	
		for (int i = 0; i < tableHeader.length; i++)  
	    {  					
			TableColumn tableColumn = new TableColumn(uncompatibilityTableOne, SWT.NONE);
			tableColumn.setText(tableHeader[i]);  
			// 设置表头可移动，默认为false  
			tableColumn.setMoveable(false); 
	    	tableColumn.pack();
	    	
	    }
		for (int i = 0; i < tableHeader.length; i++)  
	    {  					
			TableColumn tableColumn = new TableColumn(uncompatibilityTableTwo, SWT.NONE);
			tableColumn.setText(tableHeader[i]);  
			// 设置表头可移动，默认为false  
			tableColumn.setMoveable(false); 
	    	tableColumn.pack();
	    	
	    }
		
		Button CompatibilityBtn = new Button(this, SWT.NONE);
		fd_btnNewButton.left = new FormAttachment(CompatibilityBtn, -71, SWT.LEFT);
		fd_btnNewButton.right = new FormAttachment(CompatibilityBtn, -6);
		FormData fd_CompatibilityBtn = new FormData();
		fd_CompatibilityBtn.right = new FormAttachment(100, -10);
		//fd_CompatibilityBtn.right = new FormAttachment(100, -10);
		fd_CompatibilityBtn.top = new FormAttachment(0, 11);
		CompatibilityBtn.setLayoutData(fd_CompatibilityBtn);
		CompatibilityBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {				
				uncompatibilityTableOne.removeAll();
				uncompatibilityTableTwo.removeAll();
				
				String pathOfOldProject = pathOfOldProjectText.getText(); // "D:/ProjectOfHW/jEditor/jEditor0.4.1/src/org/jeditor/gui";
				String pathOfNewProject = pathOfNewProjectText.getText();
								
				InnerCompatibility oldInnerCompatibility = new InnerCompatibility(pathOfOldProject, pathOfOldProject);
				InnerCompatibility newInnerCompatibility = new InnerCompatibility(pathOfNewProject, pathOfNewProject);
				List<UnCompatibilityMIModel> oldUnCompatibilityMIModels = oldInnerCompatibility.getunCompatibilityMIModels();
				List<UnCompatibilityMIModel> newUnCompatibilityMIModels = newInnerCompatibility.getunCompatibilityMIModels();

				if (oldUnCompatibilityMIModels.size() == 0) {
					//System.out.println("版本1内部兼容性良好");
					ResultOneText.setText("Version1 has good inner compatibility");
				}
				
				if (newUnCompatibilityMIModels.size() == 0) {
					//System.out.println("版本2内部兼容性良好");
					ResultTwoText.setText("Version2 has good inner compatibility");
				}
				
				for (UnCompatibilityMIModel unCompatibilityMIModel : oldUnCompatibilityMIModels) {
					//unCompatibilityMIModel.getMessage();
					
				    final TableItem item = new TableItem(uncompatibilityTableOne, SWT.NONE);
				    String string[] = {unCompatibilityMIModel.getInPackageName()+"."+unCompatibilityMIModel.getFromClassName(),unCompatibilityMIModel.getFromPackageName(),
				    		unCompatibilityMIModel.getMethodName(),unCompatibilityMIModel.getDefaultArguments(),
				    		unCompatibilityMIModel.getRealArguments()};
				    item.setText(string);
				}				
					

				if (oldUnCompatibilityMIModels.size()>0) {
					//TableItem lastItem = new TableItem(uncompatibilityTableOne, SWT.NONE);
					//lastItem.setText(new String[] {"版本1不兼容的接口个数:",String.valueOf(oldUnCompatibilityMIModels.size())});
					ResultOneText.setText("Number of incompatible methods in version1:"+String.valueOf(oldUnCompatibilityMIModels.size()));
				}else {
					//TableItem lastItem = new TableItem(uncompatibilityTableOne, SWT.NONE);
					//lastItem.setText(new String[] {"版本1程序内部兼容性良好"});
					ResultOneText.setText("Version1 has good inner compatibility");
				}
				
				for (UnCompatibilityMIModel unCompatibilityMIModel : newUnCompatibilityMIModels) {
					//unCompatibilityMIModel.getMessage();
					
				    final TableItem item = new TableItem(uncompatibilityTableTwo, SWT.NONE);
				    String string[] = {unCompatibilityMIModel.getInPackageName(),unCompatibilityMIModel.getFromPackageName(),
				    		unCompatibilityMIModel.getMethodName(),unCompatibilityMIModel.getDefaultArguments(),
				    		unCompatibilityMIModel.getRealArguments()};
				    item.setText(string);
				}	
				
				if (newUnCompatibilityMIModels.size()>0) {
					//TableItem lastItem = new TableItem(uncompatibilityTableOne, SWT.NONE);
					//lastItem.setText(new String[] {"版本2不兼容的接口个数:",String.valueOf(newUnCompatibilityMIModels.size())});
					ResultTwoText.setText("Number of incompatible methods in version2:"+String.valueOf(newUnCompatibilityMIModels.size()));
				}else {
					//TableItem lastItem = new TableItem(uncompatibilityTableOne, SWT.NONE);
					//lastItem.setText(new String[] {"版本2程序内部兼容性良好"});
					ResultTwoText.setText("Version2 has good inner compatibility");
				}
				
			}
		});
		CompatibilityBtn.setText("Analysis");
		
		Label lblProjectPath_1 = new Label(this, SWT.NONE);
		fd_CompatibilityBtn.bottom = new FormAttachment(lblProjectPath_1, 0, SWT.BOTTOM);
		FormData fd_lblProjectPath_1 = new FormData();
		fd_lblProjectPath_1.right = new FormAttachment(lblProjectPath, 0, SWT.RIGHT);
		fd_lblProjectPath_1.top = new FormAttachment(0, 53);
		fd_lblProjectPath_1.left = new FormAttachment(0, 10);
		lblProjectPath_1.setLayoutData(fd_lblProjectPath_1);
		lblProjectPath_1.setText("Project version2:");
		
		pathOfNewProjectText = new Text(this, SWT.BORDER);
		FormData fd_pathOfNewProjectText = new FormData();
		fd_pathOfNewProjectText.right = new FormAttachment(pathOfOldProjectText, 0, SWT.RIGHT);
		fd_pathOfNewProjectText.bottom = new FormAttachment(0, 70);
		fd_pathOfNewProjectText.top = new FormAttachment(0, 48);
		fd_pathOfNewProjectText.left = new FormAttachment(0, 143);
		pathOfNewProjectText.setLayoutData(fd_pathOfNewProjectText);
		
		Button btnPath = new Button(this, SWT.NONE);
		fd_btnNewButton.bottom = new FormAttachment(btnPath, -13);
		FormData fd_btnPath = new FormData();
		fd_btnPath.left = new FormAttachment(pathOfNewProjectText, 6);
		fd_btnPath.right = new FormAttachment(CompatibilityBtn, -6);
		fd_btnPath.bottom = new FormAttachment(CompatibilityBtn, 0, SWT.BOTTOM);
		fd_btnPath.top = new FormAttachment(0, 48);
		btnPath.setLayoutData(fd_btnPath);
		btnPath.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				DirectoryDialog folderDialog = new DirectoryDialog(shell);
				
				folderDialog.setText("Please select project file");	
				folderDialog.setFilterPath("D:/ProjectOfHW/jEditor/jEditor0.3");//"D:/ProjectOfHW/junit/junit3.4"
				folderDialog.open();
				
				pathOfNewProjectText.setText(folderDialog.getFilterPath());
			}
		});
		btnPath.setText("Select...");
		
		
		
		ResultOneText = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		fd_uncompatibilityTableOne.top = new FormAttachment(ResultOneText, 6);
		FormData fd_ResultOneText = new FormData();
		fd_ResultOneText.right = new FormAttachment(0, 375);
		fd_ResultOneText.top = new FormAttachment(0, 104);
		fd_ResultOneText.left = new FormAttachment(0, 10);
		ResultOneText.setLayoutData(fd_ResultOneText);
		
		ResultTwoText = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		fd_uncompatibilityTableOne.bottom = new FormAttachment(ResultTwoText, -27);
		fd_uncompatibilityTableTwo.top = new FormAttachment(0, 396);
		FormData fd_ResultTwoText = new FormData();
		fd_ResultTwoText.right = new FormAttachment(ResultOneText, 0, SWT.RIGHT);
		fd_ResultTwoText.left = new FormAttachment(0, 10);
		fd_ResultTwoText.bottom = new FormAttachment(uncompatibilityTableTwo, -6);
		fd_ResultTwoText.top = new FormAttachment(0, 368);
		ResultTwoText.setLayoutData(fd_ResultTwoText);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
