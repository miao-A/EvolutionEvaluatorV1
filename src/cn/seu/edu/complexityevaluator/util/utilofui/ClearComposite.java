/* 作者 何磊
 * 日期 2015-6-1
 * 描述 用来清空几个tab下容器中的table数据
 * 版本 1.0
 * */
package cn.seu.edu.complexityevaluator.util.utilofui;
import cn.seu.edu.complexityevaluator.ui.halsteadui.ClassHstdInfo;
import cn.seu.edu.complexityevaluator.ui.halsteadui.MethodHstdInfo;
import cn.seu.edu.complexityevaluator.ui.halsteadui.SysHstdInfo;
import cn.seu.edu.complexityevaluator.ui.mccabeui.ClassMccInfo;
import cn.seu.edu.complexityevaluator.ui.mccabeui.MethodMccInfo;

public class ClearComposite {
     public void clearMccTable(){
    	 ClassMccInfo.getClassMcc_table().removeAll();
    	 MethodMccInfo.getMaccabe_table().removeAll();
     }
     public void clearHsdTable(){
    	 SysHstdInfo.getSystemHstd_table().removeAll();
    	 ClassHstdInfo.getClassHsd_table().removeAll();
    	 MethodHstdInfo.getMethodHsd_table().removeAll();
     }
}
