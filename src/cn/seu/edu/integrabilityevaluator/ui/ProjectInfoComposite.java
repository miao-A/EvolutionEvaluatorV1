package cn.seu.edu.integrabilityevaluator.ui;

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

import cn.seu.edu.integrabilityevaluator.dbconnect.ProjectInfoConnector;
import cn.seu.edu.integrabilityevaluator.parser.ProjectParser;


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
		
		Label label = new Label(this, SWT.NONE);
		label.setBounds(105, 37, 61, 17);
		label.setText("\u9879\u76EE\u8DEF\u5F84\uFF1A");
		
		projectPathText = new Text(this, SWT.BORDER);
		projectPathText.setBounds(172, 34, 277, 23);
		
		Button fileReadBtn = new Button(this, SWT.NONE);
		fileReadBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				DirectoryDialog folderDialog = new DirectoryDialog(shell);
				
				folderDialog.setText("请选择项目文件");	
				folderDialog.setFilterPath("D:/ProjectEOfHW/jEditor/jEditor0.2");
				folderDialog.open();
				
				projectPathText.setText(folderDialog.getFilterPath());
				
			}
		});
		fileReadBtn.setBounds(455, 32, 80, 27);
		fileReadBtn.setText("\u9879\u76EE...");
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setBounds(105, 90, 61, 17);
		label_1.setText("\u9879\u76EE\u540D\u79F0\uFF1A");
		
		projectNameText = new Text(this, SWT.BORDER);
		projectNameText.setBounds(172, 87, 153, 23);
		
		versionText = new Text(this, SWT.BORDER);
		versionText.setBounds(172, 140, 153, 23);
		
		Label label_2 = new Label(this, SWT.NONE);
		label_2.setBounds(105, 143, 61, 17);
		label_2.setText("\u9879\u76EE\u7248\u672C\uFF1A");
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setBounds(105, 196, 61, 17);
		lblNewLabel.setText("\u7248\u672C\u4FE1\u606F\uFF1A");
		
		versionInfoText = new Text(this, SWT.BORDER);
		versionInfoText.setBounds(172, 193, 277, 49);
		
		Button button = new Button(this, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (projectPathText.getText().isEmpty()) {
					MessageDialog dialog = new MessageDialog(Display.getCurrent().getActiveShell(),//shell窗口
							"WARNNING",
							null,
							"请输入项目路径信息",
							MessageDialog.WARNING,
							new String[]{"OK"},
							1);
					dialog.open();
				}else if (projectNameText.getText().isEmpty()||versionText.getText().isEmpty()) {
					MessageDialog dialog = new MessageDialog(Display.getCurrent().getActiveShell(),//shell窗口
							"WARNNING",
							null,
							"请输入项目名称与版本信息",
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
		button.setBounds(263, 257, 80, 27);
		button.setText("\u5206\u6790");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
