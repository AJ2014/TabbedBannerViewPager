package com.example.tabbedbannerviewpager;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;

/**
 * Created by Administrator on 2014/6/26.
 */
public class ToolFunctions {

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
    
    public static void requestMeasureView(Context context, View view) {
    	if (null == context || null == view) {
    		return;
    	}
//    	DisplayMetrics dm = context.getResources().getDisplayMetrics();
    	view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), 
    			MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
    }
    
    public static void requestMeasureView(View view, int width, int height) {
    	if (null == view) {
    		return;
    	}
    	view.measure(width, height);
    }

}
