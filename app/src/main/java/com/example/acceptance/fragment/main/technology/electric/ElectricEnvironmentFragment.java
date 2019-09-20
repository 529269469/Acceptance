package com.example.acceptance.fragment.main.technology.electric;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.acceptance.R;
import com.example.acceptance.adapter.VpAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.fragment.main.technology.electric.environment.Environment1Fragment;
import com.example.acceptance.fragment.main.technology.electric.environment.Environment2Fragment;
import com.example.acceptance.fragment.main.technology.electric.environment.Environment3Fragment;
import com.example.acceptance.fragment.main.technology.electric.environment.Environment4Fragment;
import com.example.acceptance.fragment.main.technology.electric.environment.Environment5Fragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 技术类检查——电气产品外观性能试验等检查——查环境应力筛选试验报告及记录
 */
public class ElectricEnvironmentFragment extends BaseFragment {

    @BindView(R.id.vp_environment)
    ViewPager vpEnvironment;
    private List<Fragment> list=new ArrayList<>();
    private VpAdapter adapter;

    private Environment1Fragment environment1Fragment;
    private Environment2Fragment environment2Fragment;
    private Environment3Fragment environment3Fragment;
    private Environment4Fragment environment4Fragment;
    private Environment5Fragment environment5Fragment;


    @Override
    protected void initEventAndData() {
        environment1Fragment=new Environment1Fragment();
        environment2Fragment=new Environment2Fragment();
        environment3Fragment=new Environment3Fragment();
        environment4Fragment=new Environment4Fragment();
        environment5Fragment=new Environment5Fragment();
        list.add(environment1Fragment);
        list.add(environment2Fragment);
        list.add(environment3Fragment);
        list.add(environment4Fragment);
        list.add(environment5Fragment);

        adapter = new VpAdapter(getChildFragmentManager(),list);
        vpEnvironment.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_electric_environment;
    }
}
