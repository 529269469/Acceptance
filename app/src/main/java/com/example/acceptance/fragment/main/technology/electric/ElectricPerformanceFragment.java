package com.example.acceptance.fragment.main.technology.electric;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.acceptance.R;
import com.example.acceptance.adapter.VpAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.fragment.main.technology.electric.performance.Performance1Fragment;
import com.example.acceptance.fragment.main.technology.electric.performance.Performance2Fragment;
import com.example.acceptance.fragment.main.technology.electric.performance.Performance3Fragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 技术类检查——电气产品外观性能试验等检查——产品性能测试验收情况（常温）检查
 */
public class ElectricPerformanceFragment extends BaseFragment {

    @BindView(R.id.vp_environment)
    ViewPager vpEnvironment;


    private List<Fragment> list=new ArrayList<>();
    private VpAdapter adapter;

    private Performance1Fragment performance1Fragment;
    private Performance2Fragment performance2Fragment;
    private Performance3Fragment performance3Fragment;
    @Override
    protected void initEventAndData() {
        performance1Fragment=new Performance1Fragment();
        performance2Fragment=new Performance2Fragment();
        performance3Fragment=new Performance3Fragment();
        list.add(performance1Fragment);
        list.add(performance2Fragment);
        list.add(performance3Fragment);
        adapter = new VpAdapter(getActivity().getSupportFragmentManager(),list);
        vpEnvironment.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_electric_performance;
    }
}
