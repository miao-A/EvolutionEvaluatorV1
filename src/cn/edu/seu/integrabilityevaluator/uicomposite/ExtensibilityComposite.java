package cn.edu.seu.integrabilityevaluator.uicomposite;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Combo;

import cn.edu.seu.integrabilityevaluator.dbconnect.ExtensibilityConnector;
import cn.edu.seu.integrabilityevaluator.dbconnect.ProjectConnector;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ExtensibilityComposite extends Composite {
	private Table extensibilityTable;
	private ProjectConnector pcConnector = new ProjectConnector();
	private ArrayList<String> rStrings;
	private Combo projectSelectCombo = new Combo(this, SWT.NONE);
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ExtensibilityComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());
		
		extensibilityTable = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		FormData fd_extensibilityTable = new FormData();
		fd_extensibilityTable.top = new FormAttachment(projectSelectCombo, 24);
		fd_extensibilityTable.bottom = new FormAttachment(100, -10);
		fd_extensibilityTable.left = new FormAttachment(0, 10);
		fd_extensibilityTable.right = new FormAttachment(100, -10);
		extensibilityTable.setLayoutData(fd_extensibilityTable);
		extensibilityTable.setBounds(81, 77, 774, 405);
		extensibilityTable.setHeaderVisible(true);
		extensibilityTable.setLinesVisible(true);
		
		String[] tableHeader = {"Package name","Concerete classes", "Abstract classes","Interface classes"," Total classes","Ratio"};
		
		for (int i = 0; i < tableHeader.length; i++)  
	    {  
			TableColumn tableColumn = new TableColumn(extensibilityTable, SWT.NONE);  
			tableColumn.setText(tableHeader[i]);  
			// 设置表头可移动，默认为false  
			tableColumn.setMoveable(false); 
	    	tableColumn.pack();
	    }  
		
		
		final Combo versionCombo = new Combo(this, SWT.NONE);
		FormData fd_versionCombo = new FormData();
		fd_versionCombo.top = new FormAttachment(projectSelectCombo, 0, SWT.TOP);
		versionCombo.setLayoutData(fd_versionCombo);
		FormData fd_projectSelectCombo = new FormData();
		fd_projectSelectCombo.top = new FormAttachment(0, 23);
		projectSelectCombo.setLayoutData(fd_projectSelectCombo);
		projectSelectCombo.setBounds(111, 0, 98, 25);
		versionCombo.setBounds(340, 0, 88, 25);			
		
		
		projectSelectCombo.addSelectionListener(new SelectionAdapter() {
		
			
			@Override
			public void widgetSelected(SelectionEvent e) {				
				
				int index = projectSelectCombo.getSelectionIndex();
				
				ArrayList<String> verList = pcConnector.getVersion(projectSelectCombo.getItem(index));
				versionCombo.removeAll();
				for (String string : verList) {
					versionCombo.add(string);
				}
				
				versionCombo.layout();
			}
		});
		
		
		versionCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index1 = projectSelectCombo.getSelectionIndex();
				int index2 = versionCombo.getSelectionIndex();
				
				ExtensibilityConnector dbConnector = new ExtensibilityConnector(projectSelectCombo.getItem(index1),versionCombo.getItem(index2));
				ArrayList<String> packageNameList= dbConnector.getpackageName();
					// 添加三行数据  		        
			    extensibilityTable.removeAll();    
			    for (String string : packageNameList) {
			       	TableItem item = new TableItem(extensibilityTable, SWT.NONE);
			       	ArrayList<String> al = dbConnector.packageExtensibilityRatio(string);
			       	item.setText((String[])al.toArray(new String[al.size()]));
				}
			    TableItem item = new TableItem(extensibilityTable, SWT.NONE);
			    ArrayList<String> al = dbConnector.projectExtensibilityRatio();
		        item.setText((String[])al.toArray(new String[al.size()]));		       
			}
		});

		
		Label lblProject = new Label(this, SWT.NONE);
		fd_projectSelectCombo.right = new FormAttachment(lblProject, 122, SWT.RIGHT);
		fd_projectSelectCombo.left = new FormAttachment(lblProject, 34);
		FormData fd_lblProject = new FormData();
		fd_lblProject.top = new FormAttachment(0, 26);
		fd_lblProject.right = new FormAttachment(extensibilityTable, 148);
		fd_lblProject.left = new FormAttachment(extensibilityTable, 0, SWT.LEFT);
		lblProject.setLayoutData(fd_lblProject);
		lblProject.setBounds(47, 8, 61, 17);
		lblProject.setText("Select project:");
		
		Label lblVersion = new Label(this, SWT.NONE);
		fd_versionCombo.right = new FormAttachment(lblVersion, 114, SWT.RIGHT);
		fd_versionCombo.left = new FormAttachment(lblVersion, 26);
		FormData fd_lblVersion = new FormData();
		fd_lblVersion.right = new FormAttachment(projectSelectCombo, 216, SWT.RIGHT);
		fd_lblVersion.left = new FormAttachment(projectSelectCombo, 70);
		fd_lblVersion.top = new FormAttachment(0, 26);
		lblVersion.setLayoutData(fd_lblVersion);
		lblVersion.setBounds(260, 8, 61, 17);
		lblVersion.setText("Project version:");		
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
