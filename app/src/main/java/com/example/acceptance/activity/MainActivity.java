package com.example.acceptance.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.example.acceptance.R;
import com.example.acceptance.adapter.Title2Adapter;
import com.example.acceptance.adapter.TitleAdapter;
import com.example.acceptance.base.BaseActivity;
import com.example.acceptance.bean.TitleBean;
import com.example.acceptance.fragment.main.AcceptanceConclusionFragment;
import com.example.acceptance.fragment.main.ApplyForFragment;
import com.example.acceptance.fragment.main.DeliveryFragment;
import com.example.acceptance.fragment.main.LegacyFragment;
import com.example.acceptance.fragment.main.ParticularsFragment;
import com.example.acceptance.fragment.main.TaskFragment;
import com.example.acceptance.fragment.main.course.ConclusionsCourseFragment;
import com.example.acceptance.fragment.main.course.MachineryFragment;
import com.example.acceptance.fragment.main.course.PreProductionFragment;
import com.example.acceptance.fragment.main.course.ProductInProductionFragment;
import com.example.acceptance.fragment.main.course.StandardFragment;
import com.example.acceptance.fragment.main.course.ZeroFragment;
import com.example.acceptance.fragment.main.kitting.KittingFileFragment;
import com.example.acceptance.fragment.main.kitting.KittingProductFragment;
import com.example.acceptance.fragment.main.technology.TechnologyFileFragment;
import com.example.acceptance.fragment.main.technology.cable.CableAppearanceFragment;
import com.example.acceptance.fragment.main.technology.cable.CableConclusionFragment;
import com.example.acceptance.fragment.main.technology.cable.CableConditionsFragment;
import com.example.acceptance.fragment.main.technology.cable.CablePackagingFragment;
import com.example.acceptance.fragment.main.technology.cable.CablePerformanceFragment;
import com.example.acceptance.fragment.main.technology.cable.CableReportFragment;
import com.example.acceptance.fragment.main.technology.cable.CableSignFragment;
import com.example.acceptance.fragment.main.technology.electric.ElectricAppearanceFragment;
import com.example.acceptance.fragment.main.technology.electric.ElectricConclusionFragment;
import com.example.acceptance.fragment.main.technology.electric.ElectricConditionsFragment;
import com.example.acceptance.fragment.main.technology.electric.ElectricEnvironmentFragment;
import com.example.acceptance.fragment.main.technology.electric.ElectricPackagingFragment;
import com.example.acceptance.fragment.main.technology.electric.ElectricPerformanceFragment;
import com.example.acceptance.fragment.main.technology.electric.ElectricReportFragment;
import com.example.acceptance.fragment.main.technology.electric.ElectricSignFragment;
import com.example.acceptance.fragment.main.technology.machinery.MachineryAppearanceFragment;
import com.example.acceptance.fragment.main.technology.machinery.MachineryConclusionFragment;
import com.example.acceptance.fragment.main.technology.machinery.MachineryConditionsFragment;
import com.example.acceptance.fragment.main.technology.machinery.MachineryPackagingFragment;
import com.example.acceptance.fragment.main.technology.machinery.MachineryReportFragment;
import com.example.acceptance.fragment.main.technology.machinery.MachinerySignFragment;
import com.example.acceptance.fragment.main.technology.machinery.MachinerySizeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
    @BindView(R.id.tv_operation)
    TextView tvOperation;

    private TitleAdapter titleAdapter;
    private TitleAdapter titleAdapter2;
    private Title2Adapter titleAdapter3;
    private KittingFileFragment kittingFileFragment;
    private KittingProductFragment kittingProductFragment;
    private StandardFragment standardFragment;
    private PreProductionFragment preProductionFragment;
    private PopupWindow popupWindow;
    private ProductInProductionFragment productInProductionFragment;
    private ZeroFragment zeroFragment;
    private ConclusionsCourseFragment conclusionsCourseFragment;
    private MachineryFragment machineryFragment;
    private AcceptanceConclusionFragment acceptanceConclusionFragment;
    private LegacyFragment legacyFragment;
    private DeliveryFragment deliveryFragment;
    private TaskFragment taskFragment;
    private TechnologyFileFragment technologyFileFragment;
    private ElectricConditionsFragment electricConditionsFragment;
    private MachineryConditionsFragment machineryConditionsFragment;
    private CableConditionsFragment cableConditionsFragment;
    private ElectricAppearanceFragment electricAppearanceFragment;
    private MachineryAppearanceFragment machineryAppearanceFragment;
    private CableAppearanceFragment cableAppearanceFragment;
    private ElectricPerformanceFragment electricPerformanceFragment;
    private MachinerySizeFragment machinerySizeFragment;
    private CablePerformanceFragment cablePerformanceFragment;
    private ElectricEnvironmentFragment electricEnvironmentFragment;
    private MachineryReportFragment machineryReportFragment;
    private CableReportFragment cableReportFragment;
    private ElectricReportFragment electricReportFragment;
    private MachineryPackagingFragment machineryPackagingFragment;
    private CablePackagingFragment cablePackagingFragment;
    private ElectricPackagingFragment electricPackagingFragment;
    private MachinerySignFragment machinerySignFragment;
    private CableSignFragment cableSignFragment;
    private ElectricSignFragment electricSignFragment;
    private CableConclusionFragment cableConclusionFragment;
    private MachineryConclusionFragment machineryConclusionFragment;
    private ElectricConclusionFragment electricConclusionFragment;

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

        tvOperation.setOnClickListener(view -> {
            operation();
        });

        iv_up.setOnClickListener(view -> {
            if (ll_title.getVisibility() == View.GONE) {
                ll_title.setVisibility(View.VISIBLE);
                iv_up.setImageResource(R.drawable.up);
            } else {
                iv_up.setImageResource(R.drawable.down);
                ll_title.setVisibility(View.GONE);
            }

        });

        tvTuichu.setText("C021，XX单位遥安分系统验收数据包");
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

            one = position;
            transaction = getSupportFragmentManager().beginTransaction();
            switch (position) {
                case 0://详情信息
                    gvTwo.setVisibility(View.GONE);
                    gvThree.setVisibility(View.GONE);
                    if (particularsFragment == null) {
                        particularsFragment = new ParticularsFragment();
                    }
                    transaction.replace(R.id.frame, particularsFragment);
                    transaction.commit();
                    break;
                case 1://验收申请
                    gvTwo.setVisibility(View.GONE);
                    gvThree.setVisibility(View.GONE);
                    if (applyForFragment == null) {
                        applyForFragment = new ApplyForFragment();
                    }
                    transaction.replace(R.id.frame, applyForFragment);
                    transaction.commit();
                    break;
                case 2://验收任务单
                    gvTwo.setVisibility(View.GONE);
                    gvThree.setVisibility(View.GONE);

                    if (taskFragment == null) {
                        taskFragment = new TaskFragment();
                    }
                    transaction.replace(R.id.frame, taskFragment);
                    transaction.commit();

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


                    if (standardFragment == null) {
                        standardFragment = new StandardFragment();
                    }
                    transaction.replace(R.id.frame, standardFragment);
                    transaction.commit();

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

                    if (technologyFileFragment == null) {
                        technologyFileFragment = new TechnologyFileFragment();
                    }
                    transaction.replace(R.id.frame, technologyFileFragment);
                    transaction.commit();


                    break;
                case 6://验收结论
                    gvTwo.setVisibility(View.GONE);
                    gvThree.setVisibility(View.GONE);
                    if (acceptanceConclusionFragment == null) {
                        acceptanceConclusionFragment = new AcceptanceConclusionFragment();
                    }
                    transaction.replace(R.id.frame, acceptanceConclusionFragment);
                    transaction.commit();

                    break;
                case 7://验收遗留问题落实

                    gvTwo.setVisibility(View.GONE);
                    gvThree.setVisibility(View.GONE);

                    if (legacyFragment == null) {
                        legacyFragment = new LegacyFragment();
                    }
                    transaction.replace(R.id.frame, legacyFragment);
                    transaction.commit();
                    break;
                case 8://交付清单
                    gvTwo.setVisibility(View.GONE);
                    gvThree.setVisibility(View.GONE);
                    if (deliveryFragment == null) {
                        deliveryFragment = new DeliveryFragment();
                    }
                    transaction.replace(R.id.frame, deliveryFragment);
                    transaction.commit();

                    break;

            }
        });
        gvTwo.setOnItemClickListener((adapterView, view, position, l) -> {
            for (int i = 0; i < list2.size(); i++) {
                list2.get(i).setCheck(false);
            }
            list2.get(position).setCheck(true);
            titleAdapter2.notifyDataSetChanged();
            two = position;
            transaction = getSupportFragmentManager().beginTransaction();

            switch (position) {
                case 0:
                    switch (one) {
                        case 3:
                            //齐套性检查——依据文件检查
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
                            //过程检查——电气产品——元器件，原材料，标准件检查
                            if (standardFragment == null) {
                                standardFragment = new StandardFragment();
                            }
                            transaction.replace(R.id.frame, standardFragment);
                            transaction.commit();
                            break;
                        case 5:
                            gvThree.setVisibility(View.GONE);
                            if (technologyFileFragment == null) {
                                technologyFileFragment = new TechnologyFileFragment();
                            }
                            transaction.replace(R.id.frame, technologyFileFragment);
                            transaction.commit();
                            break;
                    }
                    break;
                case 1:
                    switch (one) {
                        case 3:
                            //齐套性检查——产品齐套性检查
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

                            if (machineryFragment == null) {
                                machineryFragment = new MachineryFragment();
                            }
                            transaction.replace(R.id.frame, machineryFragment);
                            transaction.commit();
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

                            if (electricConditionsFragment == null) {
                                electricConditionsFragment = new ElectricConditionsFragment();
                            }
                            transaction.replace(R.id.frame, electricConditionsFragment);
                            transaction.commit();

                            break;
                    }
                    break;
                case 2:
                    switch (one) {
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

                            if (machineryConditionsFragment == null) {
                                machineryConditionsFragment = new MachineryConditionsFragment();
                            }
                            transaction.replace(R.id.frame, machineryConditionsFragment);
                            transaction.commit();
                            break;
                    }
                    break;
                case 3:
                    switch (one) {
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

                            if (cableConditionsFragment == null) {
                                cableConditionsFragment = new CableConditionsFragment();
                            }
                            transaction.replace(R.id.frame, cableConditionsFragment);
                            transaction.commit();
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

            transaction = getSupportFragmentManager().beginTransaction();

            switch (position) {
                case 0:
                    switch (one) {
                        case 4:
                            if (two == 0) {
                                //过程检查——电气产品——元器件，原材料，标准件检查
                                if (standardFragment == null) {
                                    standardFragment = new StandardFragment();
                                }
                                transaction.replace(R.id.frame, standardFragment);
                                transaction.commit();
                            } else if (two == 1) {
                                //过程检查—机械产品——原材料，标准件检查
                                if (standardFragment == null) {
                                    standardFragment = new StandardFragment();
                                }
                                transaction.replace(R.id.frame, standardFragment);
                                transaction.commit();
                            }

                            break;
                        case 5:
                            switch (two){
                                case 1:
                                    if (electricConditionsFragment == null) {
                                        electricConditionsFragment = new ElectricConditionsFragment();
                                    }
                                    transaction.replace(R.id.frame, electricConditionsFragment);
                                    transaction.commit();
                                    break;
                                case 2:
                                    if (machineryConditionsFragment == null) {
                                        machineryConditionsFragment = new MachineryConditionsFragment();
                                    }
                                    transaction.replace(R.id.frame, machineryConditionsFragment);
                                    transaction.commit();
                                    break;
                                case 3:
                                    if (cableConditionsFragment == null) {
                                        cableConditionsFragment = new CableConditionsFragment();
                                    }
                                    transaction.replace(R.id.frame, cableConditionsFragment);
                                    transaction.commit();
                                    break;

                            }
                            break;

                    }
                    break;
                case 1:
                    switch (one) {
                        case 4:
                            if (two == 0) {
                                //过程检查——电气产品——产品生产前
                                if (preProductionFragment == null) {
                                    preProductionFragment = new PreProductionFragment();
                                }
                                transaction.replace(R.id.frame, preProductionFragment);
                                transaction.commit();
                            } else if (two == 1) {
                                //过程检查——机械产品——产品生产前
                                if (preProductionFragment == null) {
                                    preProductionFragment = new PreProductionFragment();
                                }
                                transaction.replace(R.id.frame, preProductionFragment);
                                transaction.commit();
                            }

                            break;
                        case 5:
                            switch (two){
                                case 1:
                                    if (electricAppearanceFragment == null) {
                                        electricAppearanceFragment = new ElectricAppearanceFragment();
                                    }
                                    transaction.replace(R.id.frame, electricAppearanceFragment);
                                    transaction.commit();
                                    break;
                                case 2:
                                    if (machineryAppearanceFragment == null) {
                                        machineryAppearanceFragment = new MachineryAppearanceFragment();
                                    }
                                    transaction.replace(R.id.frame, machineryAppearanceFragment);
                                    transaction.commit();
                                    break;
                                case 3:
                                    if (cableAppearanceFragment == null) {
                                        cableAppearanceFragment = new CableAppearanceFragment();
                                    }
                                    transaction.replace(R.id.frame, cableAppearanceFragment);
                                    transaction.commit();
                                    break;

                            }
                            break;

                    }

                    break;
                case 2:

                    switch (one) {
                        case 4:
                            if (two == 0) {
                                //过程检查——电气产品——产品生产中
                                if (productInProductionFragment == null) {
                                    productInProductionFragment = new ProductInProductionFragment();
                                }
                                transaction.replace(R.id.frame, productInProductionFragment);
                                transaction.commit();
                            } else if (two == 1) {
                                //过程检查——机械产品——产品生产中
                                transaction = getSupportFragmentManager().beginTransaction();
                                if (productInProductionFragment == null) {
                                    productInProductionFragment = new ProductInProductionFragment();
                                }
                                transaction.replace(R.id.frame, productInProductionFragment);
                                transaction.commit();
                            }
                            break;
                        case 5:
                            switch (two){
                                case 1:
                                    if (electricPerformanceFragment == null) {
                                        electricPerformanceFragment = new ElectricPerformanceFragment();
                                    }
                                    transaction.replace(R.id.frame, electricPerformanceFragment);
                                    transaction.commit();
                                    break;
                                case 2:
                                    if (machinerySizeFragment == null) {
                                        machinerySizeFragment = new MachinerySizeFragment();
                                    }
                                    transaction.replace(R.id.frame, machinerySizeFragment);
                                    transaction.commit();
                                    break;
                                case 3:
                                    if (cablePerformanceFragment == null) {
                                        cablePerformanceFragment = new CablePerformanceFragment();
                                    }
                                    transaction.replace(R.id.frame, cablePerformanceFragment);
                                    transaction.commit();
                                    break;
                            }
                            break;

                    }
                    break;
                case 3:
                    switch (one) {
                        case 4:
                            if (two == 0) {
                                //过程检查——电气产品——产品质量问题归零情况
                                if (zeroFragment == null) {
                                    zeroFragment = new ZeroFragment();
                                }
                                transaction.replace(R.id.frame, zeroFragment);
                                transaction.commit();
                            } else if (two == 1) {
                                //过程检查——机械产品——产品质量问题归零情况
                                if (zeroFragment == null) {
                                    zeroFragment = new ZeroFragment();
                                }
                                transaction.replace(R.id.frame, zeroFragment);
                                transaction.commit();
                            }

                            break;
                        case 5:
                            switch (two){
                                case 1:
                                    if (electricEnvironmentFragment == null) {
                                        electricEnvironmentFragment = new ElectricEnvironmentFragment();
                                    }
                                    transaction.replace(R.id.frame, electricEnvironmentFragment);
                                    transaction.commit();
                                    break;
                                case 2:
                                    if (machineryReportFragment == null) {
                                        machineryReportFragment = new MachineryReportFragment();
                                    }
                                    transaction.replace(R.id.frame, machineryReportFragment);
                                    transaction.commit();
                                    break;
                                case 3:
                                    if (cableReportFragment == null) {
                                        cableReportFragment = new CableReportFragment();
                                    }
                                    transaction.replace(R.id.frame, cableReportFragment);
                                    transaction.commit();
                                    break;
                            }
                            break;

                    }
                    break;
                case 4:
                    switch (one) {
                        case 4:
                            if (two == 0) {
                                //过程检查——电气产品——检查结论
                                if (conclusionsCourseFragment == null) {
                                    conclusionsCourseFragment = new ConclusionsCourseFragment();
                                }
                                transaction.replace(R.id.frame, conclusionsCourseFragment);
                                transaction.commit();
                            } else if (two == 1) {
                                //过程检查——机械产品——检查结论
                                if (conclusionsCourseFragment == null) {
                                    conclusionsCourseFragment = new ConclusionsCourseFragment();
                                }
                                transaction.replace(R.id.frame, conclusionsCourseFragment);
                                transaction.commit();
                            }

                            break;
                        case 5:
                            switch (two){
                                case 1:
                                    if (electricReportFragment == null) {
                                        electricReportFragment = new ElectricReportFragment();
                                    }
                                    transaction.replace(R.id.frame, electricReportFragment);
                                    transaction.commit();
                                    break;
                                case 2:
                                    if (machineryPackagingFragment == null) {
                                        machineryPackagingFragment = new MachineryPackagingFragment();
                                    }
                                    transaction.replace(R.id.frame, machineryPackagingFragment);
                                    transaction.commit();
                                    break;
                                case 3:
                                    if (cablePackagingFragment == null) {
                                        cablePackagingFragment = new CablePackagingFragment();
                                    }
                                    transaction.replace(R.id.frame, cablePackagingFragment);
                                    transaction.commit();
                                    break;
                            }
                            break;
                    }
                    break;
                case 5:
                    if (one==5){
                        switch (two){
                            case 1:
                                if (electricPackagingFragment == null) {
                                    electricPackagingFragment = new ElectricPackagingFragment();
                                }
                                transaction.replace(R.id.frame, electricPackagingFragment);
                                transaction.commit();
                                break;
                            case 2:
                                if (machinerySignFragment == null) {
                                    machinerySignFragment = new MachinerySignFragment();
                                }
                                transaction.replace(R.id.frame, machinerySignFragment);
                                transaction.commit();
                                break;
                            case 3:
                                if (cableSignFragment == null) {
                                    cableSignFragment = new CableSignFragment();
                                }
                                transaction.replace(R.id.frame, cableSignFragment);
                                transaction.commit();
                                break;
                        }
                        break;
                    }
                    break;
                case 6:
                    if (one==5){
                        switch (two){
                            case 1:
                                if (electricSignFragment == null) {
                                    electricSignFragment = new ElectricSignFragment();
                                }
                                transaction.replace(R.id.frame, electricSignFragment);
                                transaction.commit();
                                break;
                            case 2:
                                if (machineryConclusionFragment == null) {
                                    machineryConclusionFragment = new MachineryConclusionFragment();
                                }
                                transaction.replace(R.id.frame, machineryConclusionFragment);
                                transaction.commit();
                                break;
                            case 3:
                                if (cableConclusionFragment == null) {
                                    cableConclusionFragment = new CableConclusionFragment();
                                }
                                transaction.replace(R.id.frame, cableConclusionFragment);
                                transaction.commit();
                                break;
                        }
                        break;
                    }
                    break;
                case 7:
                    if (one==5){
                        switch (two){
                            case 1:
                                if (electricConclusionFragment == null) {
                                    electricConclusionFragment = new ElectricConclusionFragment();
                                }
                                transaction.replace(R.id.frame, electricConclusionFragment);
                                transaction.commit();
                                break;
                        }
                        break;
                    }
                    break;
            }
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
