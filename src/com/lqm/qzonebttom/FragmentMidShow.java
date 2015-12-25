package com.lqm.qzonebttom;

import com.example.qzonebttom.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.LinearLayout;

public class FragmentMidShow extends Fragment {

	private View rootView;
	private MainActivity ac;
	private LinearLayout layoutHide;
	private GridView gd;
	private View item1;
	private View item2;
	private View item3;
	private View item4;
	private View item5;
	private View item6;
	private View item7;
	private View item8;
	private LinearLayout container;
	private int height;
	private int count;
	protected boolean isExise;
	private long oldTime;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView=inflater.inflate(R.layout.fragment_midshow, container, false);
		return rootView;
	}
	@Override
	public void onAttach(Activity activity) {
		this.ac=(MainActivity) activity;
		super.onAttach(activity);
	}
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setViews();
		setListeners();

	}

	private void setViews() {
		container=(LinearLayout)rootView.findViewById(R.id.container);
		layoutHide=(LinearLayout)rootView.findViewById(R.id.layoutHide);
		item1=rootView.findViewById(R.id.item1);
		item2=rootView.findViewById(R.id.item2);
		item3=rootView.findViewById(R.id.item3);
		item4=rootView.findViewById(R.id.item4);
		item5=rootView.findViewById(R.id.item5);
		item6=rootView.findViewById(R.id.item6);
		item7=rootView.findViewById(R.id.item7);
		item8=rootView.findViewById(R.id.item8);
		height=400;
	}
	private void setListeners() {
		layoutHide.setOnClickListener(new OnClickListener() {


			@Override
			public void onClick(View arg0) {
				if (System.currentTimeMillis()-oldTime<800) {
					oldTime=System.currentTimeMillis();
					return;
				}
				hideFragment();
			}
			
		});
	}

	/**
	 * 重置视图
	 */
	public void resetView() {
		    count=0;
			MarginLayoutParams maParams=(MarginLayoutParams) item2.getLayoutParams();
			maParams.topMargin=0;
			item2.requestLayout();
			MarginLayoutParams maParams3=(MarginLayoutParams) item3.getLayoutParams();
			maParams3.topMargin=0;
			item3.requestLayout();
			
			MarginLayoutParams maParams6=(MarginLayoutParams) item6.getLayoutParams();
			maParams6.topMargin=0;
			item6.requestLayout();
			MarginLayoutParams maParams7=(MarginLayoutParams) item7.getLayoutParams();
			maParams7.topMargin=0;
			item7.requestLayout();
			
			MarginLayoutParams ma=(MarginLayoutParams) container.getLayoutParams();
    		ma.setMargins(0, count, 0, 0);
    		container.setLayoutParams(ma);
	}
	public void hideFragment() {
		isExise=false;
	    MarginLayoutParams maParams=(MarginLayoutParams) item2.getLayoutParams();
	    maParams.topMargin=25;
	    item2.requestLayout();
	    MarginLayoutParams maParams3=(MarginLayoutParams) item3.getLayoutParams();
	    maParams3.topMargin=25;
	    item3.requestLayout();
	    
	    MarginLayoutParams maParams6=(MarginLayoutParams) item6.getLayoutParams();
	    maParams6.topMargin=25;
	    item6.requestLayout();
	    MarginLayoutParams maParams7=(MarginLayoutParams) item7.getLayoutParams();
	    maParams7.topMargin=25;
	    item7.requestLayout();
	    
	    final Handler han=new Handler(){
	    	@Override
	    	public void handleMessage(Message msg) {
	    		super.handleMessage(msg);
	    		if (count<=height) {
					/*container.setPadding(0, count, 0, 0);
					container.requestLayout();*/
					MarginLayoutParams ma=(MarginLayoutParams) container.getLayoutParams();
		    		ma.setMargins(0, count, 0, 0);
		    		container.setLayoutParams(ma);
				}
				if (count>=height) {
					ac.hideMidFragment();
				    isExise=true;
				}
	    	}
	    };
	    new Thread(){

			public void run() {
	    		try {
	    			while (count<=height&&isExise==false) {
	    				count+=20;
						Thread.sleep(10);
						if (isExise==false) {
							han.sendEmptyMessage(100);
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    	};
	    }.start();
	}
}
