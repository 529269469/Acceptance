package com.example.acceptance.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.example.acceptance.R;
import com.example.acceptance.adapter.TitleAdapter;
import com.example.acceptance.base.BaseActivity;
import com.example.acceptance.bean.TitleBean;
import com.example.acceptance.fragment.main.ApplyForFragment;
import com.example.acceptance.fragment.main.ParticularsFragment;
import com.example.acceptance.utils.DataUtils;

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
    @BindView(R.id.frame)
    FrameLayout frame;
    @BindView(R.id.lv_title)
    ListView lvTitle;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    private TitleAdapter titleAdapter;

    public static Intent openIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    private List<TitleBean> list = new ArrayList<>();
    private FragmentTransaction transaction;
    private ParticularsFragment particularsFragment;
    private ApplyForFragment applyForFragment;

    @Override
    protected void initView() {
        ivGenduo.setOnClickListener(view -> {
            drawerLayout.openDrawer(Gravity.LEFT);
        });

        list.add(new TitleBean("详情信息"));
        list.add(new TitleBean("验收申请"));
        list.add(new TitleBean("验收任务单"));
        list.add(new TitleBean("齐套性检查"));
        list.add(new TitleBean("过程检查"));
        list.add(new TitleBean("技术类检查"));
        list.add(new TitleBean("验收结论"));
        list.add(new TitleBean("验收遗留问题落实"));
        list.add(new TitleBean("交付清单"));
        list.get(0).setCheck(true);
        titleAdapter = new TitleAdapter(this, list);
        lvTitle.setAdapter(titleAdapter);

        tvTuichu.setText(list.get(0).getTitle());

        transaction = getSupportFragmentManager().beginTransaction();
        particularsFragment = new ParticularsFragment();
        transaction.replace(R.id.frame, particularsFragment);
        transaction.commit();

        lvTitle.setOnItemClickListener((adapterView, view, position, l) -> {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setCheck(false);
            }
            list.get(position).setCheck(true);
            titleAdapter.notifyDataSetChanged();
            tvTuichu.setText(list.get(position).getTitle());
            switch (position) {
                case 0://详情信息
                    transaction = getSupportFragmentManager().beginTransaction();
                    if (particularsFragment==null){
                        particularsFragment = new ParticularsFragment();
                    }
                    transaction.replace(R.id.frame, particularsFragment);
                    transaction.commit();
                    break;
                case 1://验收申请
                    break;
                case 2://验收任务单
                    break;
                case 3://齐套性检查
                    break;
                case 4://过程检查
                    break;
                case 5://技术类检查
                    break;
                case 6://验收结论
                    break;
                case 7://验收遗留问题落实
                    break;
                case 8://交付清单
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

}
