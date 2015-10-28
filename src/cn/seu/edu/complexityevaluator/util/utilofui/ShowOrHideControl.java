/* 作者 何磊
 * 日期 2015-6-8
 * 描述 这个只是用来简化连续版本复杂度界面的隐藏控件和展示控件的效果的封装，以便代码的简洁
 * 版本1.0
 * 问题：这个工具类，一般针对性比较强，很难说去写的比较抽象了
 * */
package cn.seu.edu.complexityevaluator.util.utilofui;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;

public class ShowOrHideControl {
    public void  show(Label label_1,Label label_2,Combo combo_sversion,Combo combo_eversion){
    	//起始版本标签可见
    	label_1.setVisible(true);
		// 起始版本号下拉条可见
		combo_sversion.setVisible(true);
		// 结束版本号标签可见
		label_2.setVisible(true);
		// 结束版本号下拉条可见
		combo_eversion.setVisible(true);
    }
    public void hide(Label label_1,Label label_2,Combo combo_sversion,Combo combo_eversion){
    	//起始版本标签可见
		label_1.setVisible(false);
		// 起始版本号下拉条可见
		combo_sversion.setVisible(false);
		// 结束版本号标签可见
		label_2.setVisible(false);
		// 结束版本号下拉条可见
		combo_eversion.setVisible(false);
    }
}
