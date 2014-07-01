package com.example.tabbedbannerviewpager;

import android.content.Context;
import android.graphics.Color;
import android.test.ApplicationTestCase;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2014/6/30.
 */
public class CustomListAdapter extends BaseAdapter {

    Context context;
    String[] items;
    int mFIndex;
    /**
     * 子视图高度索引
     */
    SparseIntArray mHeightEntries;
    /**
     * 子视图距顶部距离索引
     */
    SparseIntArray mDistanceEntries;

    public CustomListAdapter(Context context, String[] items) {
        this.context = context;
        this.items = items;
        mHeightEntries = new SparseIntArray();
        mDistanceEntries = new SparseIntArray();
        mDistanceEntries.put(0, 0);
    }

    @Override
    public int getCount() {
        return null == items ? 0 : items.length;
    }

    @Override
    public Object getItem(int i) {
        return null == items ? null : items[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String curItem = (String) getItem(i);
        TextView tView = null;
        if (null == view) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_list_item, null);
        }
        tView = (TextView) view.findViewById(R.id.title);
        tView.setText("Item-" + i);
        tView.setBackgroundColor(Color.parseColor(curItem));
        addViewObserverCallback(view, new CustomedOnGlobalLayoutListener(view, i));
        return view;
    }
    
    public class CustomedOnGlobalLayoutListener implements OnGlobalLayoutListener {
    	int position;
    	View relativeView;
    	public CustomedOnGlobalLayoutListener(View view, int position) {
    		this.position = position;
    		relativeView = view;
    	}
    	
		@Override
		public void onGlobalLayout() {
			removeViewObserverCallback(relativeView, CustomedOnGlobalLayoutListener.this);
			final ListView parent = (ListView)relativeView.getParent();
			if (null == parent) {
				return;
			}
			final int height = relativeView.getHeight();
			mHeightEntries.put(position + 1, height);
			// 上一个item距顶部的距离
			final int preDist = mDistanceEntries.get(position + 1);
			final int curDist = preDist + parent.getDividerHeight() + height; 
			mDistanceEntries.put(position + 2, curDist);
//			Log.i("junjiang2", String.format("height[%d-%d] distance[%d-%d]", 
//					position + 1, height, position + 2, curDist));
		}
    	
    }
    
    public void addViewObserverCallback(View view, OnGlobalLayoutListener listener) {
    	if (null != view) {
    		view.getViewTreeObserver().addOnGlobalLayoutListener(listener);
    	}
    }
    
    private void removeViewObserverCallback(View view, OnGlobalLayoutListener victim) {
    	if (null != view) {
    		view.getViewTreeObserver().removeGlobalOnLayoutListener(victim);
    	}
    }
    
    /**
     * @param pos item's index
     * @return distance between item's top and container' top
     */
    public int getItemDistanceFromTopAtPosition(int pos) {
    	return mDistanceEntries.get(pos);
    }

}
