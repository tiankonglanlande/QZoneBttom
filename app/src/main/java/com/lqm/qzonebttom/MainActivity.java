package com.lqm.qzonebttom;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
/**
 * 
 * @author 天空蓝蓝的
 *
 */
public class MainActivity extends FragmentActivity implements OnClickListener {

    private FrameLayout content;
	private FrameLayout bttomShow;
	private LinearLayout layoutBttomShow;
	private FragmentMidShow midShowFragment;
	private Fragment1 f1;
	private Fragment2 f2;
	private Fragment3 f3;
	private Fragment4 f4;
	private int position;//标记显示的fragment
	private FrameLayout bttom1;
	private FrameLayout bttom2;
	private FrameLayout bttom3;
	private FrameLayout bttom4;
	private ImageView ivBttom1;
	private ImageView ivBttom2;
	private ImageView ivBttom3;
	private ImageView ivBttom4;
	private boolean isMidBttomShow;
	private ImageView mid2Click;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFragment();
        setViews();
        setListeners();
    }


	private void setListeners() {
		bttomShow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				int tag=(Integer) bttomShow.getTag();
				if (tag==1) {
					showMidFragment();
				}else if(tag==2){
					hideMidFragment();
				}
			}
		});
		
		bttom1.setOnClickListener(this);
		bttom2.setOnClickListener(this);
		bttom3.setOnClickListener(this);
		bttom4.setOnClickListener(this);
	}

	protected void addFragment() {
		FragmentManager manager=getSupportFragmentManager();
		FragmentTransaction tr=manager.beginTransaction();
		midShowFragment=new FragmentMidShow();
		f1=new Fragment1();
		f2=new Fragment2();
		f3=new Fragment3();
		f4=new Fragment4();
		
		
		tr.add(R.id.content, midShowFragment, "midShowFragment");
		tr.add(R.id.content, f1, "f1");
		tr.add(R.id.content, f2, "f2");
		tr.add(R.id.content, f3, "f3");
		tr.add(R.id.content, f4, "f4");
		tr.hide(midShowFragment);
		tr.show(f1);
		tr.hide(f2);
		tr.hide(f3);
		tr.hide(f4);
		tr.commit();
		
		position=0;//默认第一个fragment显示
		
	}
	@SuppressLint("NewApi") 
	public void showMidFragment(){
		isMidBttomShow=true;
		layoutBttomShow.setAlpha(1);
		layoutBttomShow.setVisibility(View.VISIBLE);
		FragmentManager manager=getSupportFragmentManager();
		FragmentTransaction tr=manager.beginTransaction();
		FragmentMidShow f=(FragmentMidShow) manager.findFragmentByTag("midShowFragment");
		f.resetView();
		tr.show(f);
		tr.hide(f1);
		tr.hide(f2);
		tr.hide(f3);
		tr.hide(f4);
		tr.commit();
		bttomShow.setTag(2);
	}
	@SuppressLint("NewApi") 
	public void hideMidFragment(){
		isMidBttomShow=false;
		layoutBttomShow.setAlpha(0);
		//layoutBttomShow.setVisibility(View.GONE);
		FragmentManager manager=getSupportFragmentManager();
		FragmentTransaction tr=manager.beginTransaction();
		FragmentMidShow f=(FragmentMidShow) manager.findFragmentByTag("midShowFragment");
		Fragment curentFg=null;
		switch (position) {
		case 0:
			curentFg=manager.findFragmentByTag("f1");
			break;
		case 1:
			curentFg=manager.findFragmentByTag("f2");
			break;
		case 2:
			curentFg=manager.findFragmentByTag("f3");
			
			break;
		case 3:
			curentFg=manager.findFragmentByTag("f4");
			break;
		}
		tr.hide(f);
		tr.show(curentFg);
		tr.commit();
		bttomShow.setTag(1);
	}

	private void setViews() {
		content=(FrameLayout) findViewById(R.id.content);
		bttomShow=(FrameLayout) findViewById(R.id.bttomShow);
		bttomShow.setTag(1);
		layoutBttomShow=(LinearLayout) findViewById(R.id.layoutBttomShow);
		bttom1=(FrameLayout)findViewById(R.id.bttom1);
		bttom2=(FrameLayout)findViewById(R.id.bttom2);
		bttom3=(FrameLayout)findViewById(R.id.bttom3);
		bttom4=(FrameLayout)findViewById(R.id.bttom4);
		ivBttom1=(ImageView)findViewById(R.id.iv_bttom1);
		ivBttom2=(ImageView)findViewById(R.id.iv_bttom2);
		ivBttom3=(ImageView)findViewById(R.id.iv_bttom3);
		ivBttom4=(ImageView)findViewById(R.id.iv_bttom4);
		ivBttom1.setSelected(true);
		
		mid2Click=(ImageView)findViewById(R.id.mid2Click);
	}


	@Override
	public void onClick(View view) {
		if (isMidBttomShow) {
			return;
		}
		switch (view.getId()) {
		case R.id.bttom1:
			if (position!=0) {
				position=0;
				//设置相应UI和fragment
				showCommFragment(position);
			}
			break;
		case R.id.bttom2:
			if (position!=1) {
				position=1;
				//设置相应UI和fragment
				showCommFragment(position);
			}
			break;
		case R.id.bttom3:
			if (position!=2) {
				position=2;
				//设置相应UI和fragment
				showCommFragment(position);
			}
			break;
		case R.id.bttom4:
			if (position!=3) {
				position=3;
				//设置相应UI和fragment
				showCommFragment(position);
			}
			break;

		default:
			break;
		}	}

	private void showCommFragment(int position) {
		FragmentManager manager=getSupportFragmentManager();
	    FragmentTransaction tr = manager.beginTransaction();
	    Fragment1 f1=(Fragment1) manager.findFragmentByTag("f1");
	    Fragment2 f2=(Fragment2) manager.findFragmentByTag("f2");
	    Fragment3 f3=(Fragment3) manager.findFragmentByTag("f3");
	    Fragment4 f4=(Fragment4) manager.findFragmentByTag("f4");
		switch (position) {
		case 0:
			ivBttom1.setSelected(true);
			ivBttom2.setSelected(false);
			ivBttom3.setSelected(false);
			ivBttom4.setSelected(false);
			tr.show(f1).hide(f2).hide(f3).hide(f4);
			break;
		case 1:
			ivBttom1.setSelected(false);
			ivBttom2.setSelected(true);
			ivBttom3.setSelected(false);
			ivBttom4.setSelected(false);
			tr.hide(f1).show(f2).hide(f3).hide(f4);
			
			break;
		case 2:
			ivBttom1.setSelected(false);
			ivBttom2.setSelected(false);
			ivBttom3.setSelected(true);
			ivBttom4.setSelected(false);
			tr.hide(f1).hide(f2).show(f3).hide(f4);
			
			break;
		case 3:
			ivBttom1.setSelected(false);
			ivBttom2.setSelected(false);
			ivBttom3.setSelected(false);
			ivBttom4.setSelected(true);
			tr.hide(f1).hide(f2).hide(f3).show(f4);
			break;

		default:
			break;
		}
		tr.commit();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (isMidBttomShow) {
			//旋转动画旋转+动画结束时候隐藏fragment
			
			AnimationSet rotate = (AnimationSet) AnimationUtils.loadAnimation(MainActivity.this, R.anim.center);
			rotate.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation arg0) {
					FragmentManager manager = getSupportFragmentManager();
					FragmentMidShow f=(FragmentMidShow) manager.findFragmentByTag("midShowFragment");
					f.hideFragment();
				}
				
				@Override
				public void onAnimationRepeat(Animation arg0) {
				}
				
				@Override
				public void onAnimationEnd(Animation arg0) {
					
				}
			});
			mid2Click.startAnimation(rotate);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
