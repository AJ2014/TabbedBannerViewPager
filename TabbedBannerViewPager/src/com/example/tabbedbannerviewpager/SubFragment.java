package com.example.tabbedbannerviewpager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Administrator on 2014/6/26.
 */
public class SubFragment extends Fragment {

	/**
	 * fragment's index
	 */
	int mFIndex;
	/**
	 * cached content view
	 */
    View mContentView;
    /**
     * cached header view
     */
    View mHeader;
    /**
     * cached listView
     */
    CustomedListView mListView;
    BaseAdapter mAdapter;
    AbsListView.OnScrollListener mOnScrollListener;
    
    public SubFragment(int index, View header, IOnScrollListener listener) {
    	mFIndex = index;
        mHeader = header;
        setOnScrollListener(listener);
    }

    public void setOnScrollListener(IOnScrollListener listener) {
        mOnScrollListener = listener;
        if (null != mListView) {
        	mListView.setOnScrollListener(listener);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == mContentView) {
            mContentView = inflater.inflate(R.layout.layout_sub_fragment, container, false);
            mListView = (CustomedListView) mContentView.findViewById(R.id.list_view);
            /**
             * 如果ListView数据不够，或者为空，
             * 此时需要使用Footer来使得其可滑动
             * Footer的高度也应该是可变的
             */
            mAdapter = new CustomListAdapter(getActivity().getApplicationContext(), new String[]{
                    "#000000","#0000FF", "#000FF0", "#00FF00"
                    ,"#000000"
                    ,"#0000FF", "#000FF0", "#00FF00",
                    "#000000","#0000FF", "#000FF0", "#00FF00","#000000","#0000FF", "#000FF0", "#00FF00"
            });
            if (null == mHeader) {
                mHeader = inflater.inflate(R.layout.layout_list_header, null);
            }
            mListView.addHeaderView(mHeader);
            mListView.setAdapter(mAdapter);
            /**
             * identify contained listView
             */
            mListView.setFIndex(mFIndex);
            mAdapter.notifyDataSetChanged();
            mListView.setOnScrollListener(mOnScrollListener);
        } else {
            ViewGroup parent = (ViewGroup)mContentView.getParent();
            if (parent != null) {
                parent.removeView(mContentView);
            }
        }
        
        return mContentView;
    }
    
    /**
     * auto scroll with default duration
     * @param scrollY
     */
    public void scrollListBy(final int scrollY) {
    	if (null != mListView) {
    		mListView.post(new Runnable() {
				@Override
				public void run() {
					mListView.smoothScrollBy(scrollY, 1000);
				}
			});
    	}
    }
    
    /**
     * auto scroll with input duration
     * @param scrollY
     * @param duration
     */
    public void scrollListBy(final int scrollY, final int duration) {
    	if (null != mListView) {
    		mListView.post(new Runnable() {
				@Override
				public void run() {
					mListView.smoothScrollBy(scrollY, duration);
				}
			});
    	}
    }
    
    /**
     * request to reset the listView's position, except it had
     * a negative scrollY
     * @param firstIndex
     * @param top
     * @return
     */
    public boolean requestAdjust(int firstIndex, int top) {
    	if (null != mListView) {
    		final int curFirstPos = mListView.getFirstVisiblePosition();
    		if (curFirstPos <= firstIndex) {
    			setSelectionFromTop(firstIndex, top);
    			return true;
    		}
    	}
    	return false;
    }
    
    public void setSelectionFromTop(final int firstIndex, final int top) {
    	if (null != mListView) {
    		mListView.setSelectionFromTop(firstIndex, top);
    	}
    }

}
