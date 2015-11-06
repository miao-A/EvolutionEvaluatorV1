package mainui;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

public class GroupTest {
    
    public static void main(String args[])
       {
           final Display display = new Display();
           final Shell shell = new Shell(display, SWT.MIN|SWT.MAX|SWT.CLOSE);
           shell.setSize(240, 300);
           shell.setText("分组框");
           shell.setToolTipText("Shell容器");
            
           GridLayout shellLayout = new GridLayout(1,false);
           shellLayout.marginHeight = 15;
           shellLayout.marginWidth = 15;
           shell.setLayout(shellLayout);
            
           final Group group1 = new Group(shell,SWT.NONE);
           GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(group1);
            
           GridLayout group1Layout = new GridLayout(1,false);
           group1Layout.marginHeight = 15;
           group1Layout.marginWidth = 15;
           group1.setLayout(group1Layout);            
           group1.setText("请选择");
            
           Button bt1 = new Button(group1,SWT.RADIO);
           bt1.setText("Group实例");
           GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(bt1);
           Button bt2 = new Button(group1,SWT.RADIO);
           GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(bt2);
           bt2.setText("Button实例");
            
           final Group group2 = new Group(shell,SWT.NONE);
           GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(group2);
            
           GridLayout group2Layout = new GridLayout(1,false);
           group2Layout.marginHeight = 15;
           group2Layout.marginWidth = 15;
           group2.setLayout(group2Layout);            
           group2.setText("Group2");
            
           Button bt3 = new Button(group2,SWT.NORMAL);       
           GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(bt3);
           bt3.setText("Normal");
           Button bt4 = new Button(group2,SWT.CHECK);
           GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(bt4);
           bt4.setText("Check");            
           Button bt5 = new Button(group2,SWT.TOGGLE);
           GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(bt5);
           bt5.setText("TOGGLE");
            

           shell.open();
           while(!shell.isDisposed())
           {
               if(!display.readAndDispatch())
                   display.sleep();
           }
           display.dispose();
            
            
       }


}