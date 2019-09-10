package com.example.acceptance.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.example.acceptance.R;
import com.example.acceptance.adapter.TitleAdapter;
import com.example.acceptance.base.BaseActivity;
import com.example.acceptance.bean.TitleBean;
import com.example.acceptance.fragment.main.ParticularsFragment;
import com.example.acceptance.fragment.setup.ConclusionsFragment;
import com.example.acceptance.fragment.setup.DutyFragment;
import com.example.acceptance.fragment.setup.FileFragment;
import com.example.acceptance.fragment.setup.PacketFragment;
import com.example.acceptance.fragment.setup.ProductFragment;
import com.example.acceptance.fragment.setup.ResultFragment;
import com.example.acceptance.fragment.setup.SketchFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetupActivity extends BaseActivity {


    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_step)
    ListView lvStep;
    @BindView(R.id.fl_step)
    FrameLayout flStep;

    public static Intent openIntent(Context context) {
        Intent intent = new Intent(context, SetupActivity.class);
        return intent;
    }
    private TitleAdapter titleAdapter;
    private List<TitleBean> list = new ArrayList<>();

    private FragmentTransaction transaction;
    private PacketFragment packetFragment;//数据包类型
    private DutyFragment dutyFragment;//责任单位
    private ProductFragment productFragment;//产品类别
    private FileFragment fileFragment;//文件类别
    private ResultFragment resultFragment;//检查结果
    private ConclusionsFragment conclusionsFragment;//检查结论
    private SketchFragment sketchFragment;//问题简述
    @Override
    protected void initView() {
        ivLeft.setOnClickListener(view -> finish());
        list.add(new TitleBean("数据包类型"));
        list.add(new TitleBean("责任单位"));
        list.add(new TitleBean("产品类别"));
        list.add(new TitleBean("文件类别"));
        list.add(new TitleBean("检查结果"));
        list.add(new TitleBean("检查结论"));
        list.add(new TitleBean("问题简述"));
        list.get(0).setCheck(true);
        titleAdapter = new TitleAdapter(this, list);
        lvStep.setAdapter(titleAdapter);

        packetFragment=new PacketFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_step, packetFragment);
        transaction.commit();

        lvStep.setOnItemClickListener((adapterView, view, position, l) -> {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setCheck(false);
            }
            list.get(position).setCheck(true);
            titleAdapter.notifyDataSetChanged();
            transaction = getSupportFragmentManager().beginTransaction();
            switch (position) {
                case 0://数据包类型
                    if (packetFragment==null){
                        packetFragment=new PacketFragment();
                    }
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fl_step, packetFragment);

                    break;
                case 1://责任单位
                    if (dutyFragment==null){
                        dutyFragment=new DutyFragment();
                    }
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fl_step, dutyFragment);
                    break;
                case 2://产品类别
                    if (productFragment==null){
                        productFragment=new ProductFragment();
                    }
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fl_step, productFragment);
                    break;
                case 3://文件类别
                    if (fileFragment==null){
                        fileFragment=new FileFragment();
                    }
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fl_step, fileFragment);
                    break;
                case 4://检查结果
                    if (resultFragment==null){
                        resultFragment=new ResultFragment();
                    }
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fl_step, resultFragment);
                    break;
                case 5://检查结论
                    if (conclusionsFragment==null){
                        conclusionsFragment=new ConclusionsFragment();
                    }
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fl_step, conclusionsFragment);
                    break;
                case 6://问题简述
                    if (sketchFragment==null){
                        sketchFragment=new SketchFragment();
                    }
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fl_step, sketchFragment);
                    break;
            }
            transaction.commit();
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setup;
    }

    @Override
    protected void initDataAndEvent() {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        hideInputWhenTouchOtherView(this, ev, null);
        return super.dispatchTouchEvent(ev);
    }


    public boolean isTouchView(View view, MotionEvent event) {
        if (view == null || event == null) {
            return false;
        }
        int[] leftTop = {0, 0};
        view.getLocationInWindow(leftTop);
        int left = leftTop[0];
        int top = leftTop[1];
        int bottom = top + view.getHeight();
        int right = left + view.getWidth();
        if (event.getRawX() > left && event.getRawX() < right
                && event.getRawY() > top && event.getRawY() < bottom) {
            return true;
        }
        return false;
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            return !isTouchView(v, event);
        }
        return false;
    }

    public void hideInputWhenTouchOtherView(Activity activity, MotionEvent ev, List<View> excludeViews) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (excludeViews != null && !excludeViews.isEmpty()) {
                for (int i = 0; i < excludeViews.size(); i++) {
                    if (isTouchView(excludeViews.get(i), ev)) {
                        return;
                    }
                }
            }
            View v = activity.getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager inputMethodManager = (InputMethodManager)
                        activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }

        }
    }

}
