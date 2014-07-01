package com.example.tabbedbannerviewpager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2014/6/26.
 */
public class CustomedListView extends ListView {

    private static final String TAG = CustomedListView.class.getSimpleName();
    /**
     * fragment index
     */
    private int mFIndex;
    /**
     * added header view
     */
    private View mHeader;
    /**
     * custom adapter
     */
    private CustomListAdapter mAdapter;

    public CustomedListView(Context context) {
        super(context);
        init(context);
    }

    public CustomedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomedListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
    	/**
    	 * disable fast scroll, for the over scroll condition
    	 */
        setFastScrollEnabled(false);
    }
    
    @Override
    public void setAdapter(ListAdapter adapter) {
    	mAdapter = (CustomListAdapter) adapter;
    	if (null != mHeader) {
    		initAdaptersDistanceEntries(mHeader);
    	}
    	super.setAdapter(adapter);
    }

    @Override
    public void addHeaderView(View v) {
        final int hCount = getHeaderViewsCount();
        if (0 != hCount) {
            removeViews(0, hCount);
        }
        mHeader = v;
        initAdaptersDistanceEntries(v);
        super.addHeaderView(v);
    }
    
    /**
     * initial the distance entries with first head row
     * @param header
     */
    private void initAdaptersDistanceEntries(View header) {
    	if (null != mAdapter) {
        	mAdapter.addViewObserverCallback(header, 
        			mAdapter.new CustomedOnGlobalLayoutListener(header, -1));
        }
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        /**
         * empty implement, for over scroll condition solving 
         */
    }

	public int getFIndex() {
		return mFIndex;
	}

	public void setFIndex(int mFIndex) {
		this.mFIndex = mFIndex;
	}
    
	/**
	 * get the distance between listView's top and the item's top at the 'position'
	 * @param position
	 * @return
	 */
	public int getDistanceFromTop(int position) {
		if (null == mAdapter
				|| position < 0 
				|| position > mAdapter.getCount()) {
			return 0;
		}
		return mAdapter.getItemDistanceFromTopAtPosition(position);
	}
	
	/**
	 * deal with the motion event dispatch
	 * when the viewPager contains listView in the sub fragment
	 */
	
	float lastMotionX = 0;
	float lastMotionY = 0;
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		final int action = ev.getAction();
    	float distanceX = ev.getX() - lastMotionX;
    	float distanceY = ev.getY() - lastMotionY;
    	lastMotionX = ev.getX();
    	lastMotionY = ev.getY();
    	switch(action) {
	    	case MotionEvent.ACTION_MOVE: {
	    		if (Math.abs(distanceY) > Math.abs(distanceX)) {
	    			/**
	    			 * intercept the motion
	    			 */
	    			requestDisallowInterceptTouchEvent(true);
	    		} else {
	    			/**
	    			 * dispatch the motion to it's parent
	    			 */
	    			return false;
	    		}
	    		
	    	}
    	}
		return super.dispatchTouchEvent(ev);
	}

}
