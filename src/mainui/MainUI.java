package mainui;


import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;

//import cn.seu.edu.complexityevaluator.mainui.ComplexityCompsite;







import cn.edu.seu.integrabilityevaluator.ui.IntegrationComposite;
import cn.edu.seu.integrabilityevaluator.ui.IntegrationMenuComposite;




public class MainUI  {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	private StackLayout sl = new StackLayout();
	private Composite composite;
	protected Shell shell;
	public static void main(String[] args) {
		try {
			/*WelcomeInterface welcomeInterface = new WelcomeInterface();
			Display display = Display.getDefault();
			welcomeInterface.open(display);
			new Thread().sleep(2000);
			welcomeInterface.welcomeClose();*/
			
			MainUI window = new MainUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void open()  {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	protected void createContents(){
		
	
		shell = new Shell(SWT.CLOSE | SWT.MIN);
		shell.setSize(915, 700);
		shell.setText("EvoluationEvaluator");
		shell.setLayout(new GridLayout(1, false));
		
		/*shell.setLocation(Display.getCurrent().getClientArea().width / 2 - shell.getShell().getSize().x/2, Display.getCurrent()  
                .getClientArea().height / 2 - shell.getSize().y/2); */
		
		
		
		Menu menubar1 = new Menu(shell, SWT.BAR);
		
		

		MenuItem menubar2 = new MenuItem(menubar1,SWT.BAR);
		menubar2.setText("test");
		shell.setMenuBar(menubar1);
		
		MenuItem architecture = new MenuItem(menubar1, SWT.CASCADE);
		architecture.setText("\u67B6\u6784\u8BC4\u4F30");
		
		Menu tempMenu_arch = new Menu(shell, SWT.DROP_DOWN);  
		architecture.setMenu(tempMenu_arch);
		
		final MenuItem evolution = new MenuItem(tempMenu_arch, SWT.CASCADE);  
		evolution.setText("\u67B6\u6784\u6210\u719F\u5EA6\u8BC4\u4F30");
		
		
		MenuItem code = new MenuItem(menubar1, SWT.CASCADE);
		code.setText("Code evaluator");
		Menu temp_code = new Menu(shell, SWT.DROP_DOWN);  
		code.setMenu(temp_code);
		
		final MenuItem intergrationMenu = new MenuItem(temp_code, SWT.NONE);
		intergrationMenu.setText("Integration evalutor");
		
		final MenuItem codeComplexityItem = new MenuItem(temp_code, SWT.NONE);
		codeComplexityItem.setText("\u4EE3\u7801\u590D\u6742\u5EA6\u8BC4\u4F30");
		
		MenuItem mntmNewItem = new MenuItem(menubar1, SWT.NONE);
		mntmNewItem.setText("New Item");
		
		
		composite = new Composite(shell, SWT.NONE);
		GridData gd_composite = new GridData(SWT.RIGHT, SWT.FILL, false, false, 1, 1);
		gd_composite.heightHint = 660;
		gd_composite.widthHint = 900;
		composite.setLayoutData(gd_composite);
		composite.setLayout(sl);
 
		//sl.topControl = new Test(composite, SWT.NONE);
		Display display = Display.getDefault();
		

		Listener listener =new Listener() {
			
			@Override
			public void handleEvent(Event event){
			

				if(event.widget == intergrationMenu){
					sl.topControl = new IntegrationMenuComposite(composite, SWT.NONE);
				}else if (event.widget == codeComplexityItem) {	
					
				//	sl.topControl = new ComplexityCompsite(composite, SWT.NONE);
				}
				
				composite.layout();
			}
		
		
		};
		intergrationMenu.addListener(SWT.Selection, listener);
		codeComplexityItem.addListener(SWT.Selection, listener);
		
	}
}
