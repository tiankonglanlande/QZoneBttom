package com.lqm.qzonebttom.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by @author 天空蓝蓝的 on 2017/4/2.
 */

public class ScreenUtils{
    public static DisplayMetrics getScreenDisplayMetrics(Activity activity){
        DisplayMetrics outMetrics=new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }
}