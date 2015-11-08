package cn.edu.seu.integrabilityevaluator.uicomposite;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import cn.edu.seu.integrabilityevaluator.dbconnect.ProjectInfoConnector;
import cn.edu.seu.integrabilityevaluator.parser.ProjectParser;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

public class ProjectInfoComposite extends Composite {
	private Text projectPathText;
	private Text projectNameText;
	private Text versionText;
	private Text versionInfoText;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ProjectInfoComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());
		
		Label lblProjectInformation = new Label(this, SWT.NONE);
		FormData fd_lblProjectInformation = new FormData();
		fd_lblProjectInformation.top = new FormAttachment(0, 47);
		fd_lblProjectInformation.left = new FormAttachment(0, 83);
		fd_lblProjectInformation.right = new FormAttachment(0, 240);
		lblProjectInformation.setLayoutData(fd_lblProjectInformation);
		lblProjectInformation.setText("Project path:");
		//label.setText("\u9879\u76EE\u8DEF\u5F84\uFF1A");
		
		projectPathText = new Text(this, SWT.BORDER);
		FormData fd_projectPathText = new FormData();
		fd_projectPathText.left = new FormAttachment(0, 245);
		fd_projectPathText.top = new FormAttachment(0, 44);
		projectPathText.setLayoutData(fd_projectPathText);
		
		Button fileReadBtn = new Button(this, SWT.NONE);
		FormData fd_fileReadBtn = new FormData();
		fd_fileReadBtn.left = new FormAttachment(100, -265);
		fd_fileReadBtn.top = new FormAttachment(lblProjectInformation, -5, SWT.TOP);
		fd_fileReadBtn.right = new FormAttachment(100, -191);
		fileReadBtn.setLayoutData(fd_fileReadBtn);
		fileReadBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				DirectoryDialog folderDialog = new DirectoryDialog(shell);
				
				//folderDialog.setText("请选择项目文件");
				folderDialog.setText("Select project file");
				folderDialog.setFilterPath("D:/ProjectEOfHW/jEditor/jEditor0.2");
				folderDialog.open();
				
				projectPathText.setText(folderDialog.getFilterPath());
				
			}
		});
		//fileReadBtn.setText("\u9879\u76EE...");
		fileReadBtn.setText("Select...");
		
		Label label_1 = new Label(this, SWT.NONE);
		FormData fd_label_1 = new FormData();
		fd_label_1.right = new FormAttachment(lblProjectInformation, 0, SWT.RIGHT);
		fd_label_1.top = new FormAttachment(0, 77);
		fd_label_1.left = new FormAttachment(0, 83);
		label_1.setLayoutData(fd_label_1);
		//label_1.setText("\u9879\u76EE\u540D\u79F0\uFF1A");
		label_1.setText("Project name:");
		
		projectNameText = new Text(this, SWT.BORDER);
		fd_projectPathText.right = new FormAttachment(projectNameText, 0, SWT.RIGHT);
		fd_projectPathText.bottom = new FormAttachment(projectNameText, -7);
		FormData fd_projectNameText = new FormData();
		fd_projectNameText.right = new FormAttachment(100, -314);
		fd_projectNameText.left = new FormAttachment(label_1, 5);
		fd_projectNameText.top = new FormAttachment(0, 74);
		projectNameText.setLayoutData(fd_projectNameText);
		
		Label label_2 = new Label(this, SWT.NONE);
		FormData fd_label_2 = new FormData();
		fd_label_2.right = new FormAttachment(lblProjectInformation, 0, SWT.RIGHT);
		fd_label_2.top = new FormAttachment(0, 105);
		fd_label_2.left = new FormAttachment(0, 83);
		label_2.setLayoutData(fd_label_2);
		//label_2.setText("\u9879\u76EE\u7248\u672C\uFF1A");
		label_2.setText("Project version:");
		
		versionText = new Text(this, SWT.BORDER);
		fd_projectNameText.bottom = new FormAttachment(versionText, -5);
		FormData fd_versionText = new FormData();
		fd_versionText.right = new FormAttachment(100, -314);
		fd_versionText.left = new FormAttachment(label_2, 5);
		versionText.setLayoutData(fd_versionText);
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		FormData fd_lblNewLabel = new FormData();
		
		fd_lblNewLabel.left = new FormAttachment(0, 84);
		fd_lblNewLabel.right = new FormAttachment(lblProjectInformation, 0, SWT.RIGHT);
		lblNewLabel.setLayoutData(fd_lblNewLabel);
		//lblNewLabel.setText("\u7248\u672C\u4FE1\u606F\uFF1A");
		lblNewLabel.setText("Project description:");
		
		versionInfoText = new Text(this, SWT.BORDER);
		fd_versionText.top = new FormAttachment(versionInfoText, -52, SWT.TOP);
		fd_versionText.bottom = new FormAttachment(versionInfoText, -29);
		fd_lblNewLabel.top = new FormAttachment(versionInfoText, 3, SWT.TOP);
		FormData fd_versionInfoText = new FormData();
		fd_versionInfoText.top = new FormAttachment(0, 154);
		fd_versionInfoText.right = new FormAttachment(100, -314);
		fd_versionInfoText.left = new FormAttachment(0, 245);
		versionInfoText.setLayoutData(fd_versionInfoText);
		
		Button btnSubmit = new Button(this, SWT.NONE);
		fd_versionInfoText.bottom = new FormAttachment(100, -160);
		FormData fd_btnSubmit = new FormData();
		fd_btnSubmit.top = new FormAttachment(versionInfoText, 42);
		fd_btnSubmit.left = new FormAttachment(0, 382);
		fd_btnSubmit.right = new FormAttachment(100, -451);
		btnSubmit.setLayoutData(fd_btnSubmit);
		btnSubmit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (projectPathText.getText().isEmpty()) {
					MessageDialog dialog = new MessageDialog(Display.getCurrent().getActiveShell(),//shell窗口
							"WARNNING",
							null,
							//"请输入项目路径信息",
							"Please input project path information",
							MessageDialog.WARNING,
							new String[]{"OK"},
							1);
					dialog.open();
				}else if (projectNameText.getText().isEmpty()||versionText.getText().isEmpty()) {
					MessageDialog dialog = new MessageDialog(Display.getCurrent().getActiveShell(),//shell窗口
							"WARNNING",
							null,
							"Please input project path and version information ",
							//"请输入项目名称与版本信息",
							MessageDialog.WARNING,
							new String[]{"OK"},
							1);
					dialog.open();					
				}else{
					
					String projectPath = projectPathText.getText();
					String projectName = projectNameText.getText();
					String version = versionText.getText();
					String versionInfo = versionInfoText.getText();
					
					
					System.out.println("put project information!");
					ProjectInfoConnector piConnector = new ProjectInfoConnector(projectName, version,versionInfo);		
					
					
					ProjectParser projectParser = new ProjectParser(projectPath,projectName,version);
//					projectParser.parser();
					projectParser.runDectors();
				}			
			}
		});
		//button.setText("\u5206\u6790");
		btnSubmit.setText("Submit");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
