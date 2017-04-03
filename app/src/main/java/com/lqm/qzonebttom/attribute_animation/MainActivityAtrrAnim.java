package com.lqm.qzonebttom.attribute_animation;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lqm.qzonebttom.Fragment1;
import com.lqm.qzonebttom.Fragment2;
import com.lqm.qzonebttom.Fragment3;
import com.lqm.qzonebttom.Fragment4;
import com.lqm.qzonebttom.R;

import java.util.ArrayList;


/**
 * Created by @author 天空蓝蓝的 on 2017/4/2.
 */
public class MainActivityAtrrAnim extends AppCompatActivity implements FragmentAttrCenter.IBttomVisibleCallBack{

    private int []bttomIds={R.id.bttom1,R.id.bttom2,R.id.bttom3,R.id.bttom4};
    /**默认未选中的图片*/
    private int []ivDefaultResIdList={R.drawable.icon_1,R.drawable.icon_1,R.drawable.icon_1,R.drawable.icon_1};
    /**选中的图片*/
    private int []ivSelectResIdList={R.drawable.icon_1_click,R.drawable.icon_1_click,R.drawable.icon_1_click,R.drawable.icon_1_click};
    /**底部字符串*/
    private String []bttomNameList={"动态","与我相关","我的空间","发现"};
    /**底部布局View集合*/
    private ArrayList<View> bttomLayoutList=new ArrayList<>();
    /**当前选中的position*/
    int curentPosition =0;
    private LinearLayout layoutBttomContainer;
    private int tvSelectColor=R.color.yellow;
    private int tvDefaultColor=R.color.black;
    private FrameLayout itemBttomCenter;//中间的按钮布局
    private Fragment1 f1;
    private Fragment2 f2;
    private Fragment3 f3;
    private Fragment4 f4;
    private FragmentAttrCenter fCenter;
    /**中间fCenter2是否显示按钮*/
    private boolean isCenterShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_attr_animal);

        initViews();
        intFragment();
        initListeners();

    }

    private void intFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tr = manager.beginTransaction();

        fCenter= (FragmentAttrCenter) manager.findFragmentByTag("fragmentCenter");
        if (fCenter==null){
            fCenter=new FragmentAttrCenter();
            tr.add(R.id.content,fCenter,"fragmentCenter");
        }
        //设置回调监听
        fCenter.setBttomVisibleCallBack(this);

        f1= (Fragment1) manager.findFragmentByTag("fragment1");
        if (f1==null){
            f1=new Fragment1();
            tr.add(R.id.content,f1,"fragment1");
        }

        f2= (Fragment2) manager.findFragmentByTag("fragment2");
        if (f2==null){
            f2=new Fragment2();
            tr.add(R.id.content,f2,"fragment2");
        }

        f3= (Fragment3) manager.findFragmentByTag("fragment3");
        if (f3==null){
            f3=new Fragment3();
            tr.add(R.id.content,f3,"fragment3");
        }

        f4= (Fragment4) manager.findFragmentByTag("fragment4");
        if (f4==null){
            f4=new Fragment4();
            tr.add(R.id.content,f4,"fragment4");
        }
        tr.commit();

        //设置默认第一个fragment显示
        setCurrentFragment(curentPosition);

    }

    /**
     * 根据curentPosition设置当前的framgent的显示
     * 隐藏中间fCenter
     * @param curentPosition
     */
    private void setCurrentFragment(int curentPosition) {
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction tr=manager.beginTransaction();
        switch (curentPosition){
            case 0:
                tr.show(f1).hide(f2).hide(f3).hide(f4);
                break;
            case 1:
                tr.show(f2).hide(f1).hide(f3).hide(f4);
                break;
            case 2:
                tr.show(f3).hide(f2).hide(f1).hide(f4);
                break;
            case 3:
                tr.show(f4).hide(f2).hide(f3).hide(f1);
                break;
        }
        isCenterShow=false;
        tr.hide(fCenter);
        layoutBttomContainer.setVisibility(View.VISIBLE);
        tr.commit();
    }

    /**
     * 初始化中间按钮将布局保存到成员变量
     * 初始化其他按钮将布局添加到集合
     */
    private void initViews() {
        layoutBttomContainer=(LinearLayout)findView(R.id.layoutBttomContainer);
        layoutBttomContainer.removeAllViews();
        for (int i = 0; i < bttomIds.length; i++) {
            //添加中间按钮start
            if (i == (bttomIds.length)/2){
                itemBttomCenter= (FrameLayout) View.inflate(this,R.layout.item_attr_center,null);
                layoutBttomContainer.addView(itemBttomCenter);
            }
            //添加中间按钮end

            LinearLayout itemBttomView= (LinearLayout) View.inflate(this,R.layout.item_attr_bttom,null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT);
            params.weight = 1.0f;
            itemBttomView.setOrientation(LinearLayout.VERTICAL);
            itemBttomView.setLayoutParams(params);

            layoutBttomContainer.addView(itemBttomView);

            bttomLayoutList.add(itemBttomView);
            bttomLayoutList.get(i).setOnClickListener(bttomClick(i));
        }
        selectCurrent(0);//默认选中第一个
    }
    private void initListeners() {
        for (int i = 0; i < bttomLayoutList.size(); i++) {
            bttomLayoutList.get(i).setOnClickListener(bttomClick(i));
        }
        //设置中间点击监听
        itemBttomCenter.setOnClickListener(itemCenterClick());
    }

    private View.OnClickListener itemCenterClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              showCenterFragment();
            }
        };
    }


  private void showCenterFragment() {
    FragmentManager manage= getSupportFragmentManager();
    FragmentTransaction tr = manage.beginTransaction();
      if (!isCenterShow) {
          isCenterShow=true;
          tr.hide(f1).hide(f2).hide(f3).hide(f4).show(fCenter);
          layoutBttomContainer.setVisibility(View.GONE);
          fCenter.entrance();
      }else{
          isCenterShow=false;
          setCurrentFragment(curentPosition);
      }
    tr.commit();
  }
    private View.OnClickListener bttomClick(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curentPosition == position){
                    return;
                }
                //重置样式
                resetAllBttom();
                //添加当前选中的样式
                selectCurrent(position);
                //切换到选中的fragment
                setCurrentFragment(curentPosition);

            }
        };
    }

    private void selectCurrent(int position) {
        curentPosition =position;
        for (int i = 0; i < bttomLayoutList.size(); i++) {
            View v=bttomLayoutList.get(i);
            ImageView iv=(ImageView)findView(v,R.id.iv_bttom);
            TextView tv=(TextView)findView(v,R.id.tv_bttom);
            if (position==i){
                //设置选中图片
                iv.setImageResource(ivSelectResIdList[i]);
                //设置选中文字颜色
                tv.setTextColor(getResources().getColor(tvSelectColor));
            }
            //设置选中文字
            tv.setText(bttomNameList[i]);
        }
    }

    public void resetAllBttom(){
        for (int i = 0; i < bttomLayoutList.size(); i++) {
            View v=bttomLayoutList.get(i);
            ImageView iv=findView(v,R.id.iv_bttom);
            iv.setImageResource(ivDefaultResIdList[i]);
            TextView tv=findView(v,R.id.tv_bttom);
            tv.setText(bttomNameList[i]);
            tv.setTextColor(getResources().getColor(tvDefaultColor));
        }
    }
    @Override
    public void bttomVisibleCallBack(int visible) {
        layoutBttomContainer.setVisibility(visible);
        showCenterFragment();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(isCenterShow){
            fCenter.walkOff();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 获取控件的方法
     * @param id
     * @param <T>
     * @return
     */
    public  <T extends View> T findView(int id){
        return (T) super.findViewById(id);
    }
    /**
     * 获取控件的方法
     * @param rootView
     * @param id
     * @param <T>
     * @return
     */
    public  <T extends View> T findView(View rootView,int id){
        return (T) rootView.findViewById(id);
    }


}

