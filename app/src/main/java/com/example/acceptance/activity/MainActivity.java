package com.example.acceptance.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.example.acceptance.R;
import com.example.acceptance.adapter.TitleAdapter;
import com.example.acceptance.base.BaseActivity;
import com.example.acceptance.bean.TitleBean;
import com.example.acceptance.fragment.main.AcceptanceConclusionFragment;
import com.example.acceptance.fragment.main.ApplyForFragment;
import com.example.acceptance.fragment.main.DeliveryFragment;
import com.example.acceptance.fragment.main.LegacyFragment;
import com.example.acceptance.fragment.main.ParticularsFragment;
import com.example.acceptance.fragment.main.TaskFragment;
import com.example.acceptance.fragment.main.course.CourseFragment;
import com.example.acceptance.fragment.main.kitting.KittingFragment;
import com.example.acceptance.fragment.main.technology.TechnologyFragment;

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
    ListView gvOne;
    @BindView(R.id.frame)
    FrameLayout frame;
    @BindView(R.id.ll_title)
    LinearLayout ll_title;
    @BindView(R.id.tv_operation)
    TextView tvOperation;
    @BindView(R.id.drawerlayout_drawer)
    DrawerLayout drawerLayout;

    private TitleAdapter titleAdapter;
    private KittingFragment kittingFragment;
    private CourseFragment courseFragment;
    private PopupWindow popupWindow;
    private AcceptanceConclusionFragment acceptanceConclusionFragment;
    private LegacyFragment legacyFragment;
    private DeliveryFragment deliveryFragment;
    private TaskFragment taskFragment;
    private TechnologyFragment technologyFragment;
    private void hide(FragmentTransaction transaction) {
        if (kittingFragment != null) {
            transaction.hide(kittingFragment);
        }
        if (courseFragment != null) {
            transaction.hide(courseFragment);
        }
        if (acceptanceConclusionFragment != null) {
            transaction.hide(acceptanceConclusionFragment);
        }
        if (legacyFragment != null) {
            transaction.hide(legacyFragment);
        }
        if (deliveryFragment != null) {
            transaction.hide(deliveryFragment);
        }
        if (taskFragment != null) {
            transaction.hide(taskFragment);
        }
        if (technologyFragment != null) {
            transaction.hide(technologyFragment);
        }
        if (particularsFragment != null) {
            transaction.hide(particularsFragment);
        }
        if (applyForFragment != null) {
            transaction.hide(applyForFragment);
        }
    }
    private boolean isDel;
    public static Intent openIntent(Context context,String id,boolean isDel) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("isDel",isDel);
        return intent;
    }
    private String id;
    private List<TitleBean> list = new ArrayList<>();

    private FragmentTransaction transaction;
    private ParticularsFragment particularsFragment;
    private ApplyForFragment applyForFragment;

    @Override
    protected void initView() {
        id=getIntent().getStringExtra("id");
        isDel=getIntent().getBooleanExtra("isDel",false);
        ivGenduo.setOnClickListener(view -> {
            //显示侧滑菜单
            drawerLayout.openDrawer(GravityCompat.START);
        });

        tvOperation.setOnClickListener(view -> {
            operation();
        });


        tvTuichu.setText("详情信息");
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
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        if (isDel){
            transaction = getSupportFragmentManager().beginTransaction();
            kittingFragment = new KittingFragment();
            kittingFragment.setArguments(bundle);
            bundle.putBoolean("isDel", isDel);
            transaction.add(R.id.frame, kittingFragment);
            transaction.commit();
        }else {
            transaction = getSupportFragmentManager().beginTransaction();
            particularsFragment = new ParticularsFragment();
            particularsFragment.setArguments(bundle);
            transaction.add(R.id.frame, particularsFragment);
            transaction.commit();
        }


        gvOne.setOnItemClickListener((adapterView, view, position, l) -> {
            drawerLayout.closeDrawers();
            tvTuichu.setText(list.get(position).getTitle());
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setCheck(false);
            }
            list.get(position).setCheck(true);
            titleAdapter.notifyDataSetChanged();
            transaction = getSupportFragmentManager().beginTransaction();
            hide(transaction);
            switch (position) {
                case 0://详情信息
                    if ( particularsFragment == null){
                        particularsFragment = new ParticularsFragment();
                        transaction.add(R.id.frame, particularsFragment);
                    }else {
                        transaction.show(particularsFragment);
                    }

                    break;
                case 1://验收申请
                    if (applyForFragment == null){
                        applyForFragment = new ApplyForFragment();
                        applyForFragment.setArguments(bundle);
                        transaction.add(R.id.frame, applyForFragment);
                    }else {
                        transaction.show(applyForFragment);
                    }
                    break;
                case 2://验收任务单
                    if (taskFragment == null){
                        taskFragment = new TaskFragment();
                        taskFragment.setArguments(bundle);
                        transaction.add(R.id.frame, taskFragment);
                    }else {
                        transaction.show(taskFragment);
                    }
                    break;
                case 3://齐套性检查
                    if (kittingFragment ==null){
                        kittingFragment = new KittingFragment();
                        kittingFragment.setArguments(bundle);
                        transaction.add(R.id.frame, kittingFragment);
                    }else {
                        transaction.show(kittingFragment);
                    }
                    break;
                case 4://过程检查
                    if (courseFragment == null){
                        courseFragment = new CourseFragment();
                        courseFragment.setArguments(bundle);
                        transaction.add(R.id.frame, courseFragment);
                    }else {
                        transaction.show(courseFragment);
                    }
                    break;
                case 5://技术类检查
                    if (technologyFragment == null){
                        technologyFragment = new TechnologyFragment();
                        technologyFragment.setArguments(bundle);
                        transaction.add(R.id.frame, technologyFragment);
                    }else {
                        transaction.show(technologyFragment);
                    }
                    break;
                case 6://验收结论
                    if (acceptanceConclusionFragment == null){
                        acceptanceConclusionFragment = new AcceptanceConclusionFragment();
                        acceptanceConclusionFragment.setArguments(bundle);
                        transaction.add(R.id.frame, acceptanceConclusionFragment);
                    }else {
                        transaction.show(acceptanceConclusionFragment);
                    }
                    break;
                case 7://验收遗留问题落实
                    if (legacyFragment == null){
                        legacyFragment = new LegacyFragment();
                        legacyFragment.setArguments(bundle);
                        transaction.add(R.id.frame, legacyFragment);
                    }else {
                        transaction.show(legacyFragment);
                    }
                    break;
                case 8://交付清单
                    if (deliveryFragment == null){
                        deliveryFragment = new DeliveryFragment();
                        deliveryFragment.setArguments(bundle);
                        transaction.add(R.id.frame, deliveryFragment);
                    }else {
                        transaction.show(deliveryFragment);
                    }
                    break;
            }
            transaction.commit();
        });
    }


    private void operation() {
        View poview = getLayoutInflater().inflate(R.layout.tv_operation_view, null);
        popupWindow = new PopupWindow(poview);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        popupWindow.showAsDropDown(tvOperation);

        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = getWindow().getAttributes();
            lp1.alpha = 1f;
            getWindow().setAttributes(lp1);
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
