package cn.seu.edu.complexityevaluator.mainui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.custom.StackLayout;

import cn.seu.edu.complexityevaluator.ui.halsteadui.HstdInfo;
import cn.seu.edu.complexityevaluator.ui.inputprojui.InputProjectUI;
import cn.seu.edu.complexityevaluator.ui.mccabeui.MccabeInfo;
import cn.seu.edu.complexityevaluator.ui.seriesui.SeriesHsdComplextyInfo;
import cn.seu.edu.complexityevaluator.ui.seriesui.SeriesMccComplexityInfo;
import org.eclipse.swt.layout.FillLayout;
public class ComplexityCompsite extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	private StackLayout sl = new StackLayout();
	private Composite left_composite;
	private Composite right_composite;
	private Composite composite_input;
	private Button input_Buttom;// 第一个按钮，项目导入
	private Button mccInfo_Button;//第二个按钮，圈复杂度度量与评估按钮
	private Button hsdInfo_Button;//第三个按钮，halstead度量与评估按钮
	private Button changedCc_button;//第四个按钮，圈复杂度演化评估
	private Button changedHsd_button;//第五个按钮，Halstead演化评估
	public ComplexityCompsite(Composite parent, int style) {
		super(parent, style);
		this.setSize(999, 641);
		this.setLayout(new GridLayout(2, false));
		// 左边的容器
		left_composite = new Composite(this, SWT.NONE);
		FillLayout fl_left_composite = new FillLayout(SWT.VERTICAL);
		fl_left_composite.spacing = 10;
		fl_left_composite.marginWidth = 5;
		fl_left_composite.marginHeight = 10;
		left_composite.setLayout(fl_left_composite);
		GridData gd_leftComposit = new GridData(SWT.LEFT, SWT.FILL, false,
				false, 1, 1);
		gd_leftComposit.widthHint = 148;
		gd_leftComposit.heightHint = 518;
		left_composite.setLayoutData(gd_leftComposit);
		// 第一个按钮，项目导入
		input_Buttom = new Button(left_composite, SWT.NONE);
		input_Buttom.setText("项目信息导入");

		// 第二个按钮，圈复杂度评估
		mccInfo_Button = new Button(left_composite,
				SWT.NONE);
		mccInfo_Button.setText("圈复杂度度量与评估");
		
		// 第三个按钮，halstead评估
		hsdInfo_Button = new Button(left_composite, SWT.NONE);
		hsdInfo_Button.setText("Halstead度量与评估");
		
		// 第四个个按钮，连续版本圈复杂度评估
		changedCc_button = new Button(left_composite, SWT.NONE);
		changedCc_button.setText("圈复杂度演化评估");
		
		//第五个按钮，连续版本halstead按钮
		changedHsd_button = new Button(left_composite, SWT.NONE);
		changedHsd_button.setText("Halstead演化评估");
		// 右边的控件
		right_composite = new Composite(this, SWT.NONE);
		GridData gd_rightComposite = new GridData(SWT.LEFT, SWT.FILL, false,
				false, 1, 1);
		gd_rightComposite.heightHint = 481;
		gd_rightComposite.widthHint = 740;
		right_composite.setLayoutData(gd_rightComposite);
		right_composite.setLayout(sl);
		
		//对默认显示的第一个界面进行实例化
		composite_input = new InputProjectUI(right_composite, SWT.NONE);
		sl.topControl = composite_input;// 默认显示项目导入界面
		// 界面切换监听器
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				if (event.widget == input_Buttom) { // //第一个按钮，项目导入
					sl.topControl = composite_input;
				} else if (event.widget == mccInfo_Button) { // 第二个按钮，圈复杂度评估
					sl.topControl = new MccabeInfo(right_composite, SWT.NONE);
				} else if (event.widget == hsdInfo_Button) { // 第三个按钮，hasltead评估
					sl.topControl = new HstdInfo(right_composite, SWT.NONE);
				} else if (event.widget == changedCc_button) { // 第四个按钮,连续版本圈复杂度
					sl.topControl = new SeriesMccComplexityInfo(right_composite,SWT.NONE);
				} else if(event.widget == changedHsd_button){ //第五个按钮Halstead
					sl.topControl =  new SeriesHsdComplextyInfo(right_composite,SWT.NONE);
				}
				right_composite.layout();
			}
		};
		// 为每个按钮添加监听器
		input_Buttom.addListener(SWT.Selection, listener);
		mccInfo_Button.addListener(SWT.Selection, listener);
		changedCc_button.addListener(SWT.Selection, listener);
		changedHsd_button.addListener(SWT.Selection, listener);
		hsdInfo_Button.addListener(SWT.Selection, listener);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
