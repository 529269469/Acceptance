//package com.example.acceptance.fragment.main.technology.electric;
//
//import androidx.fragment.app.Fragment;
//import androidx.viewpager.widget.ViewPager;
//
//import com.example.acceptance.R;
//import com.example.acceptance.adapter.VpAdapter;
//import com.example.acceptance.base.BaseFragment;
//import com.example.acceptance.fragment.main.technology.electric.environment.Environment3Fragment;
//import com.example.acceptance.fragment.main.technology.electric.environment.Environment4Fragment;
//import com.example.acceptance.fragment.main.technology.electric.environment.Environment5Fragment;
//import com.example.acceptance.fragment.main.technology.electric.report.Report1Fragment;
//import com.example.acceptance.fragment.main.technology.electric.report.Report2Fragment;
//import com.example.acceptance.fragment.main.technology.electric.report.Report3Fragment;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//
//
///**
// * 技术类检查——电气产品外观性能试验等检查——查环例行试验报告
// */
//public class ElectricReportFragment extends BaseFragment {
//
//    @BindView(R.id.vp_environment)
//    ViewPager vpEnvironment;
//
//
//    private List<Fragment> list=new ArrayList<>();
//    private VpAdapter adapter;
//
//    private Report1Fragment report1Fragment;
//    private Report2Fragment report2Fragment;
//    private Report3Fragment report3Fragment;
//    @Override
//    protected void initEventAndData() {
//
//        report1Fragment=new Report1Fragment();
//        report2Fragment=new Report2Fragment();
//        report3Fragment=new Report3Fragment();
//        list.add(report1Fragment);
//        list.add(report2Fragment);
//        list.add(report3Fragment);
//
//
//        adapter = new VpAdapter(getChildFragmentManager(),list);
//        vpEnvironment.setAdapter(adapter);
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.fragment_electric_report;
//    }
//}
