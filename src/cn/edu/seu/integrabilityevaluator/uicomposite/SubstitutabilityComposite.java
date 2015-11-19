package cn.edu.seu.integrabilityevaluator.uicomposite;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.SWT;






import cn.edu.seu.integrabilityevaluator.dbconnect.ProjectConnector;
import cn.edu.seu.integrabilityevaluator.dbconnect.SubstitutabilityConnector;

import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;


public class SubstitutabilityComposite extends Composite {

	private ProjectConnector pcConnector = new ProjectConnector();
	private ArrayList<String> rStrings;
	private Combo projectSelectCombo = new Combo(this, SWT.NONE);
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public SubstitutabilityComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());
		
		final Combo versionCombo = new Combo(this, SWT.NONE);
		FormData fd_versionCombo = new FormData();
		fd_versionCombo.top = new FormAttachment(projectSelectCombo, 0, SWT.TOP);
		versionCombo.setLayoutData(fd_versionCombo);
		FormData fd_projectSelectCombo = new FormData();
		fd_projectSelectCombo.top = new FormAttachment(0, 20);
		projectSelectCombo.setLayoutData(fd_projectSelectCombo);
		projectSelectCombo.setBounds(77, 10, 98, 25);
		versionCombo.setBounds(310, 10, 88, 25);	
						
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
		
		Label lblProject = new Label(this, SWT.NONE);
		fd_projectSelectCombo.left = new FormAttachment(lblProject, 27);
		FormData fd_lblProject = new FormData();
		fd_lblProject.left = new FormAttachment(0, 10);
		fd_lblProject.right = new FormAttachment(0, 76);
		fd_lblProject.top = new FormAttachment(0, 23);
		lblProject.setLayoutData(fd_lblProject);
		lblProject.setBounds(10, 18, 61, 17);
		lblProject.setText("Project:");
		
		Label lblVersion = new Label(this, SWT.NONE);
		fd_versionCombo.left = new FormAttachment(lblVersion, 22);
		FormData fd_lblVersion = new FormData();
		fd_lblVersion.right = new FormAttachment(projectSelectCombo, 125, SWT.RIGHT);
		fd_lblVersion.left = new FormAttachment(projectSelectCombo, 44);
		fd_lblVersion.top = new FormAttachment(projectSelectCombo, 3, SWT.TOP);
		lblVersion.setLayoutData(fd_lblVersion);
		lblVersion.setBounds(243, 13, 61, 17);
		lblVersion.setText("Version:");
		
		TabFolder tabFolder = new TabFolder(this, SWT.NONE);
		FormData fd_tabFolder = new FormData();
		fd_tabFolder.bottom = new FormAttachment(100, -30);
		fd_tabFolder.top = new FormAttachment(projectSelectCombo, 26);
		fd_tabFolder.right = new FormAttachment(100, -20);
		fd_tabFolder.left = new FormAttachment(0, 10);
		tabFolder.setLayoutData(fd_tabFolder);
		tabFolder.setBounds(10, 51, 880, 552);
		
		TabItem tbtmTablePackageCoupling = new TabItem(tabFolder, SWT.NONE);
		tbtmTablePackageCoupling.setText("Package coupling relationships table");
		
		final Tree packageCouplingTree = new Tree(tabFolder, SWT.BORDER);
		tbtmTablePackageCoupling.setControl(packageCouplingTree);
		packageCouplingTree.setLinesVisible(true);
		
		TabItem tbtmClassCouplingRelationships = new TabItem(tabFolder, SWT.NONE);
		tbtmClassCouplingRelationships.setText("Class coupling relationships table");
		
		final Tree classCouplingTree = new Tree(tabFolder, SWT.BORDER);
		classCouplingTree.setLinesVisible(true);
		tbtmClassCouplingRelationships.setControl(classCouplingTree);	
		
		
		versionCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index1 = projectSelectCombo.getSelectionIndex();
				int index2 = versionCombo.getSelectionIndex();				
				
				///树的层级显示
				packageCouplingTree.removeAll();
				classCouplingTree.removeAll();
				//ClassChangeabilityConnector dbConnector = new ClassChangeabilityConnector(projectSelectCombo.getItem(index1),versionCombo.getItem(index2));
				SubstitutabilityConnector dbConnector = new SubstitutabilityConnector(projectSelectCombo.getItem(index1),versionCombo.getItem(index2));
				final ArrayList<CouplingNode> packageNodeList = new ArrayList<CouplingNode>();
				final ArrayList<CouplingNode> classNodeList = new ArrayList<CouplingNode>();
				ArrayList<String> packageList = dbConnector.getpackageName();
						//dbConnector.getpackageName();
				for (String string : packageList) {
					CouplingNode packageNode = new CouplingNode(string);
					packageNode.setAfferents(dbConnector.packageAffernetCouplingslist(string));
					packageNode.setEfferents(dbConnector.packageEffernetCouplingslist(string));			
					packageNodeList.add(packageNode);		
				}		
				
				
				for (CouplingNode node : packageNodeList) {
					TreeItem item = new TreeItem(packageCouplingTree, SWT.NONE);
					DecimalFormat df = new DecimalFormat("0.00");
					item.setText(node.getName()+" AC: " + node.getAfferents().size() + " EC: " + node.getEfferents().size()
							+ " C: " + df.format(node.getChangeabilityRatio()));
					ArrayList<String> list = new ArrayList<>(node.getEfferents());
					list.addAll(node.getAfferents());
					for (String string : list) {
						TreeItem treeItem = new TreeItem(item, SWT.NONE);				
						treeItem.setText(string);
						if (packageNodeList.contains(new CouplingNode(string))) {
							int index = packageNodeList.indexOf(new CouplingNode(string));
							CouplingNode pNode = packageNodeList.get(index);
							ArrayList<String> nextList = new ArrayList<>(pNode.getEfferents());
							nextList.addAll(pNode.getAfferents());
							for (String string2 : nextList) {
								TreeItem nextItem = new TreeItem(treeItem, SWT.NONE);				
								nextItem.setText(string2);
							}
							
						}
					}			
				}	
				
				ArrayList<String> classList = dbConnector.getClassName();
				//dbConnector.getpackageName();
				for (String string : classList) {
					
					CouplingNode packageNode = new CouplingNode(string.replace('#', '.'));
					packageNode.setAfferents(dbConnector.classAffernetCouplingslist(string));
					packageNode.setEfferents(dbConnector.classEffernetCouplingslist(string));			
					classNodeList.add(packageNode);		
				}
				
				
				for (CouplingNode node : classNodeList) {
					TreeItem item = new TreeItem(classCouplingTree, SWT.NONE);
					DecimalFormat df = new DecimalFormat("0.00");
					item.setText(node.getName()+" AC: " + node.getAfferents().size() + " EC: " + node.getEfferents().size()
							+ " C: " + df.format(node.getChangeabilityRatio()));
					ArrayList<String> list = new ArrayList<>(node.getEfferents());
					list.addAll(node.getAfferents());
					for (String string : list) {
						TreeItem treeItem = new TreeItem(item, SWT.NONE);				
						treeItem.setText(string);
						if (classNodeList.contains(new CouplingNode(string))) {
							int index = classNodeList.indexOf(new CouplingNode(string));
							CouplingNode cNode = classNodeList.get(index);
							ArrayList<String> nextList = new ArrayList<>(cNode.getEfferents());
							nextList.addAll(cNode.getAfferents());
							for (String string2 : nextList) {
								TreeItem nextItem = new TreeItem(treeItem, SWT.NONE);				
								nextItem.setText(string2);
							}
							
						}
					}			
				}	
				
				packageCouplingTree.addListener(SWT.Expand, new Listener() {					
					@Override
					public void handleEvent(Event event) {
						// TODO Auto-generated method stub
						TreeItem selectItem =  (TreeItem) event.item;
						System.out.println("selectitem:" + selectItem.getText());			
						if (packageNodeList.contains(new CouplingNode(selectItem.getText()))) {
							int index = packageNodeList.indexOf(new CouplingNode(selectItem.getText()));
							CouplingNode pNode = packageNodeList.get(index);
							ArrayList<String> nextList = new ArrayList<>(pNode.getEfferents());
							nextList.addAll(pNode.getAfferents());
							selectItem.removeAll();
							for (String string2 : nextList) {
								TreeItem nextItem = new TreeItem(selectItem, SWT.NONE);				
								nextItem.setText(string2);
								if (packageNodeList.contains(new CouplingNode(string2))) {
									int index2 = packageNodeList.indexOf(new CouplingNode(string2));
									CouplingNode nextpNode = packageNodeList.get(index2);
									ArrayList<String> nextnextList = new ArrayList<>(nextpNode.getEfferents());
									for (String string3 : nextnextList) {
										TreeItem nextnextItem = new TreeItem(nextItem, SWT.NONE);				
										nextnextItem.setText(string3);
									}						
								}
							}
						}							
					}
				});
				
				classCouplingTree.addListener(SWT.Expand, new Listener() {					
					@Override
					public void handleEvent(Event event) {
						// TODO Auto-generated method stub
						TreeItem selectItem =  (TreeItem) event.item;
						System.out.println("selectitem:" + selectItem.getText());			
						if (classNodeList.contains(new CouplingNode(selectItem.getText()))) {
							int index = classNodeList.indexOf(new CouplingNode(selectItem.getText()));
							CouplingNode pNode = classNodeList.get(index);
							ArrayList<String> nextList = new ArrayList<>(pNode.getEfferents());
							nextList.addAll(pNode.getAfferents());
							selectItem.removeAll();
							for (String string2 : nextList) {
								TreeItem nextItem = new TreeItem(selectItem, SWT.NONE);				
								nextItem.setText(string2);
								if (classNodeList.contains(new CouplingNode(string2))) {
									int index2 = classNodeList.indexOf(new CouplingNode(string2));
									CouplingNode nextpNode = classNodeList.get(index2);
									ArrayList<String> nextnextList = new ArrayList<>(nextpNode.getEfferents());
									for (String string3 : nextnextList) {
										TreeItem nextnextItem = new TreeItem(nextItem, SWT.NONE);				
										nextnextItem.setText(string3);
									}						
								}
							}
						}							
					}
				});

				packageCouplingTree.layout();
				classCouplingTree.layout();
			}
		});
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
