﻿package cn.edu.seu.integrabilityevaluator.uicomposite;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

public class InnerCompatibilityComposite extends Composite {
	private Text pathOfProjectText;
	private Text componentOfProjectText;
	private Table uncompatibilityTable;
	private TableEditor editor = null;
	
	String strings = new String();
	private Text ResultText;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public InnerCompatibilityComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());
		
		Label lblProjectPath = new Label(this, SWT.NONE);
		FormData fd_lblProjectPath = new FormData();
		fd_lblProjectPath.right = new FormAttachment(0, 174);
		fd_lblProjectPath.top = new FormAttachment(0, 13);
		fd_lblProjectPath.left = new FormAttachment(0, 10);
		lblProjectPath.setLayoutData(fd_lblProjectPath);
		lblProjectPath.setText("Project path:");
		
		Label lblPackagePathOf = new Label(this, SWT.NONE);
		FormData fd_lblPackagePathOf = new FormData();
		fd_lblPackagePathOf.right = new FormAttachment(0, 174);
		fd_lblPackagePathOf.top = new FormAttachment(0, 62);
		fd_lblPackagePathOf.left = new FormAttachment(0, 10);
		lblPackagePathOf.setLayoutData(fd_lblPackagePathOf);
		lblPackagePathOf.setText("Package path of project:");
		
		pathOfProjectText = new Text(this, SWT.BORDER);
		FormData fd_pathOfProjectText = new FormData();
		fd_pathOfProjectText.top = new FormAttachment(0, 10);
		fd_pathOfProjectText.left = new FormAttachment(0, 180);
		pathOfProjectText.setLayoutData(fd_pathOfProjectText);
		
		Button btnNewButton = new Button(this, SWT.NONE);
		FormData fd_btnNewButton = new FormData();
		fd_btnNewButton.top = new FormAttachment(0, 10);
		fd_btnNewButton.left = new FormAttachment(pathOfProjectText, 27);
		btnNewButton.setLayoutData(fd_btnNewButton);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				DirectoryDialog folderDialog = new DirectoryDialog(shell);
				
				folderDialog.setText("Please select project");	
				folderDialog.setFilterPath("D:/ProjectOfHW/jEditor/jEditor0.2");//"D:/ProjectOfHW/junit/junit3.4"
				folderDialog.open();
				
				pathOfProjectText.setText(folderDialog.getFilterPath());
				
			}
			
			
		});
		btnNewButton.setText("Select...");
		
		componentOfProjectText = new Text(this, SWT.BORDER);
		fd_pathOfProjectText.bottom = new FormAttachment(componentOfProjectText, -22);
		fd_pathOfProjectText.right = new FormAttachment(componentOfProjectText, 0, SWT.RIGHT);
		FormData fd_componentOfProjectText = new FormData();
		fd_componentOfProjectText.bottom = new FormAttachment(0, 79);
		fd_componentOfProjectText.right = new FormAttachment(0, 656);
		fd_componentOfProjectText.top = new FormAttachment(0, 57);
		fd_componentOfProjectText.left = new FormAttachment(0, 180);
		componentOfProjectText.setLayoutData(fd_componentOfProjectText);
		
		Button btnPath = new Button(this, SWT.NONE);
		fd_btnNewButton.bottom = new FormAttachment(btnPath, -22);
		FormData fd_btnPath = new FormData();
		fd_btnPath.top = new FormAttachment(lblPackagePathOf, -5, SWT.TOP);
		fd_btnPath.left = new FormAttachment(btnNewButton, 0, SWT.LEFT);
		btnPath.setLayoutData(fd_btnPath);
		btnPath.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				DirectoryDialog folderDialog = new DirectoryDialog(shell);
				
				folderDialog.setText("Please select package in project");	
				folderDialog.setFilterPath("D:/ProjectOfHW/jEditor/jEditor0.2/src/org/jeditor/app");
				folderDialog.open();
				
				componentOfProjectText.setText(folderDialog.getFilterPath());
				
			}
		});
		btnPath.setText("Select...");
		
		uncompatibilityTable = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		FormData fd_uncompatibilityTable = new FormData();
		fd_uncompatibilityTable.bottom = new FormAttachment(100, -10);
		fd_uncompatibilityTable.right = new FormAttachment(100, -10);
		fd_uncompatibilityTable.top = new FormAttachment(0, 120);
		fd_uncompatibilityTable.left = new FormAttachment(0, 10);
		uncompatibilityTable.setLayoutData(fd_uncompatibilityTable);
		uncompatibilityTable.setHeaderVisible(true);
		uncompatibilityTable.setLinesVisible(true);

		
		editor = new TableEditor(uncompatibilityTable);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		
		String[] tableHeader = {"      Qualified class name       ", "     Incompatible class      ","      Incompatible method      ","    required signature    ","      Actual signature      "};	
		for (int i = 0; i < tableHeader.length; i++)  
	    {  					
			TableColumn tableColumn = new TableColumn(uncompatibilityTable, SWT.NONE);
			tableColumn.setText(tableHeader[i]);  
			// 设置表头可移动，默认为false  
			tableColumn.setMoveable(false); 
	    	tableColumn.pack();
	    	
	    }
		
		Button CompatibilityBtn = new Button(this, SWT.NONE);
		fd_btnNewButton.right = new FormAttachment(CompatibilityBtn, -48);
		fd_btnPath.right = new FormAttachment(CompatibilityBtn, -48);
		FormData fd_CompatibilityBtn = new FormData();
		fd_CompatibilityBtn.bottom = new FormAttachment(0, 82);
		fd_CompatibilityBtn.right = new FormAttachment(0, 863);
		fd_CompatibilityBtn.top = new FormAttachment(0, 13);
		fd_CompatibilityBtn.left = new FormAttachment(0, 784);
		CompatibilityBtn.setLayoutData(fd_CompatibilityBtn);
		CompatibilityBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {				
				uncompatibilityTable.removeAll();				
				
				String pathOfProject = pathOfProjectText.getText(); // "D:/ProjectOfHW/jEditor/jEditor0.4.1/src/org/jeditor/gui";
				String componentOfProject = componentOfProjectText.getText(); //"D:/ProjectOfHW/jEditor/jEditor0.4.2/src/org/jeditor/gui";
								
				InnerCompatibility innerCompatibility = new InnerCompatibility(pathOfProject, componentOfProject);
				List<UnCompatibilityMIModel> unCompatibilityMIModels = innerCompatibility.getunCompatibilityMIModels();					

				/*if (unCompatibilityMIModels.size() == 0) {
					System.out.println("该包在项目中兼容");
				}
				*/
				
				
				for (UnCompatibilityMIModel unCompatibilityMIModel : unCompatibilityMIModels) {
					//unCompatibilityMIModel.getMessage();
					
				    final TableItem item = new TableItem(uncompatibilityTable, SWT.NONE);
				    String string[] = {unCompatibilityMIModel.getInPackageName()+"."+unCompatibilityMIModel.getFromClassName(),
				    		unCompatibilityMIModel.getFromPackageName(),unCompatibilityMIModel.getMethodName(),unCompatibilityMIModel.getDefaultArguments(),
				    		unCompatibilityMIModel.getRealArguments()};
				    item.setText(string);
				}				
					

				if (unCompatibilityMIModels.size()>0) {
					/*TableItem lastItem = new TableItem(uncompatibilityTable, SWT.NONE);
					lastItem.setText(new String[] {"不兼容的接口个数:",String.valueOf(unCompatibilityMIModels.size())});*/
					ResultText.setText("No. of incompatible methods:"+String.valueOf(unCompatibilityMIModels.size()));
				}else {
/*					TableItem lastItem = new TableItem(uncompatibilityTable, SWT.NONE);
					lastItem.setText(new String[] {"该包在项目中兼容"});*/
					ResultText.setText("Compatible");
				}
				
			}
		});
		CompatibilityBtn.setText("Analysis");
		
		ResultText = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		FormData fd_ResultText = new FormData();
		fd_ResultText.right = new FormAttachment(0, 228);
		fd_ResultText.top = new FormAttachment(0, 91);
		fd_ResultText.left = new FormAttachment(0, 10);
		ResultText.setLayoutData(fd_ResultText);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}