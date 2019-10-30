package com.example.acceptance.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import com.example.acceptance.utils.DaoUtils;
import com.example.acceptance.utils.StringUtils;
import com.example.acceptance.utils.ToastUtils;

import java.io.File;
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
    @BindView(R.id.help_loading)
    RelativeLayout help_loading;


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
    private String type;


    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    help_loading.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    help_loading.setVisibility(View.GONE);
                    ToastUtils.getInstance().showTextToast(MainActivity.this,"数据包已导出");
                    MainActivity.this.startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    finish();
                    break;
                case 3:
                    help_loading.setVisibility(View.GONE);
                    ToastUtils.getInstance().showTextToast(MainActivity.this,"模板已导出");
                    break;
            }

        }
    };

    public static Intent openIntent(Context context,String id,boolean isDel,String type) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("isDel",isDel);
        intent.putExtra("type",type);
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
        type=getIntent().getStringExtra("type");
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
            hide(transaction);
            switch (type){
                case "2":
                    tvTuichu.setText("齐套性检查");
                    transaction = getSupportFragmentManager().beginTransaction();
                    kittingFragment = new KittingFragment();
                    bundle.putBoolean("isDel", isDel);
                    kittingFragment.setArguments(bundle);
                    transaction.add(R.id.frame, kittingFragment);
                    transaction.commit();
                    break;
                case "3":
                    tvTuichu.setText("过程检查");
                    transaction = getSupportFragmentManager().beginTransaction();
                    courseFragment = new CourseFragment();
                    bundle.putBoolean("isDel", isDel);
                    courseFragment.setArguments(bundle);
                    transaction.add(R.id.frame, courseFragment);
                    transaction.commit();
                    break;
                case "4":
                    tvTuichu.setText("技术类检查");
                    transaction = getSupportFragmentManager().beginTransaction();
                    technologyFragment = new TechnologyFragment();
                    bundle.putBoolean("isDel", isDel);
                    technologyFragment.setArguments(bundle);
                    transaction.add(R.id.frame, technologyFragment);
                    transaction.commit();
                    break;
            }
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
                        applyForFragment.setArguments(bundle);
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
        TextView tv_daochu1=poview.findViewById(R.id.tv_daochu1);
        TextView tv_daochu2=poview.findViewById(R.id.tv_daochu2);

        tv_daochu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                handler.sendEmptyMessage(1);
                new Thread(){
                    @Override
                    public void run() {
                        DaoUtils.setDao(id);
                        handler.sendEmptyMessage(2);
                        //需要在子线程中处理的逻辑
                    }
                }.start();

            }
        });

        tv_daochu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();

                daochumpban();
            }
        });

    }

    private void daochumpban() {
        View poview = getLayoutInflater().inflate(R.layout.moban, null);
        PopupWindow daochu = new PopupWindow(poview);
        daochu.setHeight(300);
        daochu.setWidth(600);
        daochu.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        daochu.setOutsideTouchable(true);
        daochu.setFocusable(true);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        daochu.showAtLocation(tvOperation, Gravity.CENTER,0,0);

        daochu.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = getWindow().getAttributes();
            lp1.alpha = 1f;
            getWindow().setAttributes(lp1);
        });

        EditText edit_name=poview.findViewById(R.id.edit_name);
        TextView tv_no=poview.findViewById(R.id.tv_no);
        TextView tv_yes=poview.findViewById(R.id.tv_yes);

        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daochu.dismiss();
            }
        });
        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtils.isBlank(edit_name.getText().toString().trim())){
                    ToastUtils.getInstance().showTextToast(MainActivity.this,"请输入模板名称");
                    return;
                }
                File file=new File(Environment.getExternalStorageDirectory()+"/模板");
                if (!file.exists()){
                    file.mkdirs();
                }
                daochu.dismiss();
                handler.sendEmptyMessage(1);
                new Thread(){
                    @Override
                    public void run() {
                        DaoUtils.setmoban(id,edit_name.getText().toString().trim());
                        handler.sendEmptyMessage(3);
                        //需要在子线程中处理的逻辑
                    }
                }.start();

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
