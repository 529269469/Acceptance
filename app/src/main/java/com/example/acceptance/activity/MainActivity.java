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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.example.acceptance.R;
import com.example.acceptance.adapter.Title2Adapter;
import com.example.acceptance.adapter.TitleAdapter;
import com.example.acceptance.base.BaseActivity;
import com.example.acceptance.bean.TitleBean;
import com.example.acceptance.fragment.main.ApplyForFragment;
import com.example.acceptance.fragment.main.ParticularsFragment;
import com.example.acceptance.fragment.main.kitting.KittingFileFragment;
import com.example.acceptance.fragment.main.kitting.KittingProductFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主页
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.iv_genduo)
    ImageView ivGenduo;
    @BindView(R.id.tv_tuichu)
    TextView tvTuichu;
    @BindView(R.id.gv_one)
    GridView gvOne;
    @BindView(R.id.frame)
    FrameLayout frame;
    @BindView(R.id.gv_two)
    GridView gvTwo;
    @BindView(R.id.gv_three)
    GridView gvThree;
    @BindView(R.id.iv_up)
    ImageView iv_up;
    @BindView(R.id.ll_title)
    LinearLayout ll_title;

    private TitleAdapter titleAdapter;
    private TitleAdapter titleAdapter2;
    private Title2Adapter titleAdapter3;
    private KittingFileFragment kittingFileFragment;
    private KittingProductFragment kittingProductFragment;

    public static Intent openIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    private List<TitleBean> list = new ArrayList<>();
    private List<TitleBean> list2 = new ArrayList<>();
    private List<TitleBean> list3 = new ArrayList<>();
    private FragmentTransaction transaction;
    private ParticularsFragment particularsFragment;
    private ApplyForFragment applyForFragment;
    private int one;
    private int two;
    @Override
    protected void initView() {
        ivGenduo.setOnClickListener(view -> {
            finish();
        });

        iv_up.setOnClickListener(view -> {
            if (ll_title.getVisibility()==View.GONE){
                ll_title.setVisibility(View.VISIBLE);
                iv_up.setImageResource(R.drawable.up);
            }else {
                iv_up.setImageResource(R.drawable.down);
                ll_title.setVisibility(View.GONE);
            }

        });
        list.add(new TitleBean("详情信息"));
        list.add(new TitleBean("验收申请"));
        list.add(new TitleBean("验收任务单"));
        list.add(new TitleBean("齐套性检查"));
        list.add(new TitleBean("过程检查"));
        list.add(new TitleBean("技术类检查"));
        list.add(new TitleBean("验收结论"));
        list.add(new TitleBean("遗留问题落实"));
        list.add(new TitleBean("交付清单"));
        list.get(0).setCheck(true);
        titleAdapter = new TitleAdapter(this, list);
        gvOne.setAdapter(titleAdapter);
        titleAdapter2 = new TitleAdapter(this, list2);
        gvTwo.setAdapter(titleAdapter2);
        titleAdapter3 = new Title2Adapter(this, list3);
        gvThree.setAdapter(titleAdapter3);

        tvTuichu.setText(list.get(0).getTitle());

        transaction = getSupportFragmentManager().beginTransaction();
        particularsFragment = new ParticularsFragment();
        transaction.replace(R.id.frame, particularsFragment);
        transaction.commit();

        gvOne.setOnItemClickListener((adapterView, view, position, l) -> {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setCheck(false);
            }
            list.get(position).setCheck(true);
            titleAdapter.notifyDataSetChanged();
            tvTuichu.setText(list.get(position).getTitle());
            one=position;
            switch (position) {
                case 0://详情信息
                    gvTwo.setVisibility(View.GONE);
                    gvThree.setVisibility(View.GONE);
                    transaction = getSupportFragmentManager().beginTransaction();
                    if (particularsFragment == null) {
                        particularsFragment = new ParticularsFragment();
                    }
                    transaction.replace(R.id.frame, particularsFragment);
                    transaction.commit();
                    break;
                case 1://验收申请
                    gvTwo.setVisibility(View.GONE);
                    gvThree.setVisibility(View.GONE);
                    transaction = getSupportFragmentManager().beginTransaction();
                    if (applyForFragment == null) {
                        applyForFragment = new ApplyForFragment();
                    }
                    transaction.replace(R.id.frame, applyForFragment);
                    transaction.commit();
                    break;
                case 2://验收任务单
                    gvTwo.setVisibility(View.GONE);
                    gvThree.setVisibility(View.GONE);
                    break;
                case 3://齐套性检查
                    gvTwo.setVisibility(View.VISIBLE);
                    gvThree.setVisibility(View.GONE);
                    gvTwo.setNumColumns(8);
                    list2.clear();
                    list2.add(new TitleBean("依据文件检查"));
                    list2.add(new TitleBean("产品齐套性检查"));
                    list2.get(0).setCheck(true);
                    titleAdapter2.notifyDataSetChanged();

                    transaction = getSupportFragmentManager().beginTransaction();
                    if (kittingFileFragment == null) {
                        kittingFileFragment = new KittingFileFragment();
                    }
                    transaction.replace(R.id.frame, kittingFileFragment);
                    transaction.commit();
                    break;
                case 4://过程检查
                    gvTwo.setVisibility(View.VISIBLE);
                    gvThree.setVisibility(View.VISIBLE);
                    gvTwo.setNumColumns(8);
                    list2.clear();
                    list2.add(new TitleBean("电气产品"));
                    list2.add(new TitleBean("机械产品"));
                    list2.get(0).setCheck(true);
                    titleAdapter2.notifyDataSetChanged();

                    gvThree.setNumColumns(5);
                    list3.clear();
                    list3.add(new TitleBean("元器件，原材料，标准件检查"));
                    list3.add(new TitleBean("产品生产前"));
                    list3.add(new TitleBean("产品生产过程"));
                    list3.add(new TitleBean("产品质量问题归零情况"));
                    list3.add(new TitleBean("检查结论"));
                    list3.get(0).setCheck(true);
                    titleAdapter3.notifyDataSetChanged();
                    break;
                case 5://技术类检查
                    gvTwo.setVisibility(View.VISIBLE);
                    gvThree.setVisibility(View.GONE);
                    gvTwo.setNumColumns(4);
                    list2.clear();
                    list2.add(new TitleBean("依据文件检查"));
                    list2.add(new TitleBean("电气产品外观性能试验等检查"));
                    list2.add(new TitleBean("机械产品外观性能试验等检查"));
                    list2.add(new TitleBean("电缆产品外观性能试验等检查"));
                    list2.get(0).setCheck(true);
                    titleAdapter2.notifyDataSetChanged();

                    break;
                case 6://验收结论
                case 7://验收遗留问题落实
                case 8://交付清单
                    gvTwo.setVisibility(View.GONE);
                    gvThree.setVisibility(View.GONE);
                    break;

            }
        });
        gvTwo.setOnItemClickListener((adapterView, view, position, l) -> {
            for (int i = 0; i < list2.size(); i++) {
                list2.get(i).setCheck(false);
            }
            list2.get(position).setCheck(true);
            titleAdapter2.notifyDataSetChanged();
            tvTuichu.setText(list2.get(position).getTitle());
            two=position;
            switch (position) {
                case 0:
                    switch (one){
                        case 3:
                            transaction = getSupportFragmentManager().beginTransaction();
                            if (kittingFileFragment == null) {
                                kittingFileFragment = new KittingFileFragment();
                            }
                            transaction.replace(R.id.frame, kittingFileFragment);
                            transaction.commit();
                            break;
                        case 4:
                            gvThree.setNumColumns(5);
                            list3.clear();
                            list3.add(new TitleBean("元器件,原材料，标准件检查"));
                            list3.add(new TitleBean("产品生产前"));
                            list3.add(new TitleBean("产品生产过程"));
                            list3.add(new TitleBean("产品质量问题归零情况"));
                            list3.add(new TitleBean("检查结论"));
                            list3.get(0).setCheck(true);
                            titleAdapter3.notifyDataSetChanged();
                            break;
                        case 5:
                            gvThree.setVisibility(View.GONE);
                            break;
                    }
                    break;
                case 1:
                    switch (one){
                        case 3:
                            transaction = getSupportFragmentManager().beginTransaction();
                            if (kittingProductFragment == null) {
                                kittingProductFragment = new KittingProductFragment();
                            }
                            transaction.replace(R.id.frame, kittingProductFragment);
                            transaction.commit();
                            break;
                        case 4:
                            gvThree.setNumColumns(5);
                            list3.clear();
                            list3.add(new TitleBean("原材料，标准件检查"));
                            list3.add(new TitleBean("产品生产前"));
                            list3.add(new TitleBean("产品生产过程"));
                            list3.add(new TitleBean("产品质量问题归零情况"));
                            list3.add(new TitleBean("检查结论"));
                            list3.get(0).setCheck(true);
                            titleAdapter3.notifyDataSetChanged();
                            break;
                        case 5:
                            gvThree.setVisibility(View.VISIBLE);
                            gvThree.setNumColumns(4);
                            list3.clear();
                            list3.add(new TitleBean("技术状态检查"));
                            list3.add(new TitleBean("产品外观检查"));
                            list3.add(new TitleBean("产品性能测试验收情况（常温）检查"));
                            list3.add(new TitleBean("查环境应力筛选试验报告及记录"));
                            list3.add(new TitleBean("查环例行试验报告"));
                            list3.add(new TitleBean("包装检查"));
                            list3.add(new TitleBean("质量证明文字签署"));
                            list3.add(new TitleBean("检查结论"));
                            list3.get(0).setCheck(true);
                            titleAdapter3.notifyDataSetChanged();
                            break;
                    }
                    break;
                case 2:
                    switch (one){
                        case 5:
                            gvThree.setVisibility(View.VISIBLE);
                            gvThree.setNumColumns(4);
                            list3.clear();
                            list3.add(new TitleBean("技术状态检查"));
                            list3.add(new TitleBean("产品外观检查"));
                            list3.add(new TitleBean("产品尺寸检查"));
                            list3.add(new TitleBean("查试验报告及记录"));
                            list3.add(new TitleBean("包装检查"));
                            list3.add(new TitleBean("质量证明文字签署"));
                            list3.add(new TitleBean("检查结论"));
                            list3.get(0).setCheck(true);
                            titleAdapter3.notifyDataSetChanged();
                            break;
                    }
                    break;
                case 3:
                    switch (one){
                        case 5:
                            gvThree.setVisibility(View.VISIBLE);
                            gvThree.setNumColumns(4);
                            list3.clear();
                            list3.add(new TitleBean("技术状态检查"));
                            list3.add(new TitleBean("产品外观检查"));
                            list3.add(new TitleBean("产品性能测试验收情况（常温）检查"));
                            list3.add(new TitleBean("查环例行试验报告"));
                            list3.add(new TitleBean("包装检查"));
                            list3.add(new TitleBean("质量证明文字签署"));
                            list3.add(new TitleBean("检查结论"));
                            list3.get(0).setCheck(true);
                            titleAdapter3.notifyDataSetChanged();
                            break;
                    }
                    break;
                case 4:
                    break;
            }
        });

        gvThree.setOnItemClickListener((adapterView, view, position, l) -> {
            for (int i = 0; i < list3.size(); i++) {
                list3.get(i).setCheck(false);
            }
            list3.get(position).setCheck(true);
            titleAdapter3.notifyDataSetChanged();
            tvTuichu.setText(list3.get(position).getTitle());
            switch (position) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
