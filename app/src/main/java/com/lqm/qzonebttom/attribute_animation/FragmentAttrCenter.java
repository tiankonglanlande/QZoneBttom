package com.lqm.qzonebttom.attribute_animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import com.lqm.qzonebttom.R;
import com.lqm.qzonebttom.utils.ScreenUtils;

import java.util.ArrayList;

import static android.view.View.inflate;


/**
 * Created by @author 天空蓝蓝的 on 2017/4/2.
 */
public class FragmentAttrCenter extends Fragment implements ViewPager.OnPageChangeListener {

	private static final String TAG = "FragmentAttrCenter";
	private View rootView;
	private View layoutHide;
	private IBttomVisibleCallBack bttomVisibleCallBack;
	private View btFgCentenr;
	private View ivMid;
	private ViewPager pager;
	/**小圆点布局**/
	private LinearLayout pointLayout;

	/**item图片*/
	private int [] itemPicArr ={R.drawable.mid_center_item1,R.drawable.mid_center_item2,R.drawable.mid_center_item3,R.drawable.mid_center_item4,R.drawable.mid_center_item5,R.drawable.mid_center_item1,R.drawable.mid_center_item2,R.drawable.mid_center_item3};
	/**item文本*/
	private String []itemTextList={"说说","图片","视频","直播","动效相机","嘿嘿","哈哈","呵呵"};
	/**缓存ViewPager 的 adapter需要的View数据**/
	private ArrayList<View> views;
	/**缓存圆点集合*/
	private ArrayList<ImageView> ivPointList;
	/**当前选中项**/
	private int currentPosition;
	/**每页显示item数量---->默认显示8条*/
	private int pagerShowCount=8;
	/**缓存itemView*/
	private ArrayList<View> itemViewList;
	/**第一次弹出item的viewpager**/
	private boolean firstEntrance=true;

	@Override
	public View onCreateView(LayoutInflater inflater,
							 @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView=inflater.inflate(R.layout.fragment_attr_center, container, false);
		return rootView;
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setViews();
		initPagerView();
		setListeners();

	}

	private void setViews() {
		layoutHide=findView(rootView,R.id.layoutHide);
		btFgCentenr=findView(rootView,R.id.btFgCentenr);
		ivMid=findView(btFgCentenr,R.id.ivMid2);
	}

	private void setListeners() {
		layoutHide.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				walkOff();
			}
		});
		btFgCentenr.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				walkOff();
			}
		});
	}



	private void initPagerView() {
		pager=(ViewPager)findView(rootView,R.id.pager);
		pointLayout =(LinearLayout)findView(rootView,R.id.pointLayout);
		ivPointList=new ArrayList<>();

		views=new ArrayList<>();
		itemViewList=new ArrayList<>();
		//计算显示的页数（每页显示8个）
		int pageCount= itemPicArr.length%pagerShowCount==0? itemPicArr.length/pagerShowCount: itemPicArr.length/pagerShowCount+1;
		for (int i = 0; i <= pageCount; i++) {
			//获得每页布局
			GridLayout gridlayout= (GridLayout) inflate(getActivity(),R.layout.grid_layout,null);
			DisplayMetrics displayMetrics = ScreenUtils.getScreenDisplayMetrics(getActivity());
			int width=displayMetrics.widthPixels/(pagerShowCount/2);
			//解决gridLayout不能设置paddingTop，marginTop
			//添加pagerShowCount/2个空白view到容器中占位（添加一行）
			for (int a = 0; a < pagerShowCount/2; a++) {
				Space space=new Space(getActivity());
				RelativeLayout.LayoutParams spaceParms=new RelativeLayout.LayoutParams(width,100);
				space.setLayoutParams(spaceParms);
				gridlayout.addView(space);
 			}

			//添加item

			Log.e(TAG, "initPagerView: width="+width );
			for (int j = 0; j < itemPicArr.length; j++) {
				View itemView= inflate(getActivity(),R.layout.item,null);
				RelativeLayout.LayoutParams parms=new RelativeLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
				itemView.setLayoutParams(parms);
				ImageView ivItem=findView(itemView,R.id.ivPic);
				TextView tvItem=findView(itemView,R.id.tvName);
				ivItem.setImageResource(itemPicArr[j]);
				tvItem.setText(itemTextList[j]);
				gridlayout.addView(itemView);
				itemViewList.add(itemView);
			}
			views.add(gridlayout);
			pager.setCurrentItem(currentPosition);
		}

		//添加圆点
		pointLayout.removeAllViews();
		for (int i=0;i<=pageCount;i++){
			ImageView ivPoint=new ImageView(getActivity());
			LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(30,30);
			params.setMargins(5,0,0,0);
			ivPoint.setLayoutParams(params);
			if (currentPosition==0&&i==0){
				//设置默认第一个选中
				ivPoint.setImageResource(R.drawable.point_checked);
			}else {
				ivPoint.setImageResource(R.drawable.point_unchecked);
			}
			ivPointList.add(ivPoint);
			pointLayout.addView(ivPoint);
		}

		MyPagerAdapter adapter=new MyPagerAdapter(views);
		pager.setAdapter(adapter);
		pager.addOnPageChangeListener(this);
	}

	public void setBttomVisibleCallBack(IBttomVisibleCallBack bttomVisibleCallBack){
		this.bttomVisibleCallBack=bttomVisibleCallBack;
	}

	/**
	 * 入场操作
	 */
	public void entrance() {
		//设置进场动画，旋转中间按钮0-135度
		ObjectAnimator ivMidRatationAnimal=ObjectAnimator.ofFloat(ivMid,"rotation",0f,135f);
		ivMidRatationAnimal.setDuration(200).start();
		//添加item入场动画  底部弹出
		itemEntance();
		pagerEntance();

	}

	/**
	 * 给viewpager做动画避免第一次还没弹出item的时候就显示item的问题
	 */
	private void pagerEntance() {
		if (firstEntrance){
			firstEntrance=false;
			ObjectAnimator objectAni=ObjectAnimator.ofFloat(pager,"translationY",ScreenUtils.getScreenDisplayMetrics(getActivity()).heightPixels/2,0f);
			objectAni.setInterpolator(new AccelerateDecelerateInterpolator());
			objectAni.setDuration(200);
			objectAni.start();
		}
	}

	/**
	 * 添加item入场动画  底部弹出
	 */
	private void itemEntance() {
		if (itemViewList==null||itemViewList.size()<=0){
			return;
		}
		for (int i = 0; i < itemViewList.size(); i++) {
			final View itemView=itemViewList.get(i);

			ObjectAnimator objectAni=ObjectAnimator.ofFloat(itemView,"translationY",ScreenUtils.getScreenDisplayMetrics(getActivity()).heightPixels/2,0f);
			objectAni.setInterpolator(new AccelerateDecelerateInterpolator());
			objectAni.setDuration(200);
			objectAni.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					super.onAnimationEnd(animation);
          			//设置缓冲回弹动画
					springBack(itemView);
				}
			});

			AnimatorSet set = new AnimatorSet();
			//i%4=0
			//i%4=3
			if (i%(pagerShowCount/2)==0||i%(pagerShowCount/2)==3){
				set.setStartDelay(50);
				Log.e(TAG, "itemEntance: i="+i );
			}
			set.play(objectAni);
			set.start();
		}
	}

	private void springBack(View itemView) {
		ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(itemView,"translationY",-50,0);
		objectAnimator.setDuration(200).start();
	}

	/**
	 * 退场场操作
	 *
	 */
	public void walkOff() {
		//设置退场动画，旋转中间按钮135f-0度显示bttom底部导航
		ObjectAnimator ivMidRatationAnimal=ObjectAnimator.ofFloat(ivMid,"rotation",135f,0f);
		ivMidRatationAnimal.setDuration(200).start();
		ivMidRatationAnimal.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				if (bttomVisibleCallBack!=null){
					bttomVisibleCallBack.bttomVisibleCallBack(View.VISIBLE);
				}
			}
		});

		//添加item退场动画  收到底部退出
		itemWalkOff();
	}

	/**
	 * 添加item退场动画  收到底部退出
	 */
	private void itemWalkOff() {
		if (itemViewList==null||itemViewList.size()<=0){
			return;
		}
		for (int i = 0; i < itemViewList.size(); i++) {
			final View itemView=itemViewList.get(i);
			ObjectAnimator objectAni=ObjectAnimator.ofFloat(itemView,"translationY",0f,ScreenUtils.getScreenDisplayMetrics(getActivity()).heightPixels/2);
			objectAni.setInterpolator(new AccelerateDecelerateInterpolator());
			objectAni.setDuration(200);
			objectAni.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					super.onAnimationEnd(animation);
				}
			});
			AnimatorSet set = new AnimatorSet();
			//i%4=0
			//i%4=3
			if (i%(pagerShowCount/2)==0||i%(pagerShowCount/2)==3){
				set.setStartDelay(50);
				Log.e(TAG, "itemEntance: i="+i );
			}
			set.play(objectAni);
			set.start();
		}
	}

	private <T extends View> T findView(View rootView, int id) {
		return (T)rootView.findViewById(id);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	}

	@Override
	public void onPageSelected(int position) {
		Log.e(TAG, "onPageSelected: "+position );
		setCurrentPointSelect(position);
	}

	private void setCurrentPointSelect(int position) {
		for (int i=0;i<ivPointList.size();i++){
			ImageView iv=ivPointList.get(i);
			if (position==i){
				//设置选中的圆点图片
				iv.setImageResource(R.drawable.point_checked);
				Log.e(TAG, "onPageSelected: 替换背景" );
			}else{
				//重置其他图片
				iv.setImageResource(R.drawable.point_unchecked);
			}
		}
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	/**
	 * 显示或隐藏bttom接口回调
	 */
	public interface IBttomVisibleCallBack{
		/**
		 * @param visible View.GONE隐藏，View.VISIBLE显示
		 */
		public void bttomVisibleCallBack(int visible);
	}

}
