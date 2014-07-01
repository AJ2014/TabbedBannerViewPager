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

	int mFIndex;
    View mContentView;
    View mHeader;
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
            mListView.setFIndex(mFIndex);
            mAdapter.notifyDataSetChanged();
            Log.i("junjiang2", "onCreateView " + mFIndex);
            mListView.setOnScrollListener(mOnScrollListener);
        } else {
            ViewGroup parent = (ViewGroup)mContentView.getParent();
            if (parent != null) {
                parent.removeView(mContentView);
            }
        }
        
        return mContentView;
    }
    
    @Override
    public void onAttach(Activity activity) {
    	Log.i("junjiang2", mFIndex + "-onAttach");
    	super.onAttach(activity);
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.i("junjiang2", mFIndex + "-onCreate");
    	super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onStart() {
    	Log.i("junjiang2", mFIndex + "-onStart");
    	super.onStart();
    }
    
    @Override
    public void onResume() {
    	Log.i("junjiang2", mFIndex + "-onResume");
    	super.onResume();
//    	Log.i("junjiang2", String.format("[%d]margin[min%d max%d cur%d]", 
//    			mFIndex, MainActivity.minMargin, MainActivity.maxMargin, MainActivity.curMargin));
    	if (MainActivity.curMargin == MainActivity.maxMargin) {
        	
        } else {
        	if (MainActivity.curMargin == MainActivity.minMargin) {
        		requestAdjust(0, MainActivity.minMargin);
        	} else {
        		requestAdjust(0, MainActivity.curMargin);
        		scrollListBy(-(MainActivity.minMargin - MainActivity.curMargin));
        	}
        	mListView.invalidate();
        }
    }
    
    @Override
    public void onPause() {
    	Log.i("junjiang2", mFIndex + "-onPause");
    	super.onPause();
    }
    
    @Override
    public void onStop() {
    	Log.i("junjiang2", mFIndex + "-onStop");
    	super.onStop();
    }
    
    @Override
    public void onDestroyView() {
    	Log.i("junjiang2", mFIndex + "-onDestroyView");
    	super.onDestroyView();
    }
    
    @Override
    public void onDestroy() {
    	Log.i("junjiang2", mFIndex + "-onDestroy");
    	super.onDestroy();
    }
    
    @Override
    public void onDetach() {
    	Log.i("junjiang2", mFIndex + "-onDetach");
    	super.onDetach();
    }

    public BaseAdapter getListAdapter() {
        return mAdapter;
    }
    
    /**
     * auto scroll with default duration
     * @param scrollY
     */
    public void scrollListBy(final int scrollY) {
    	if (null != mListView) {
    		Log.i("junjiang2", mListView.getFIndex() + " scrollListTo " + scrollY);
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
    
    public boolean canAdjust(int firstIndex) {
    	final int curFirstPos = mListView.getFirstVisiblePosition();
		return curFirstPos <= firstIndex;
    }
    
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
