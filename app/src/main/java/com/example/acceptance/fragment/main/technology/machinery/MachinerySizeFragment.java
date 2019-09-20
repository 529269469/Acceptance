package com.example.acceptance.fragment.main.technology.machinery;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.acceptance.R;
import com.example.acceptance.adapter.VpAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.fragment.main.technology.machinery.performance.Performance1Fragment;
import com.example.acceptance.fragment.main.technology.machinery.performance.Performance2Fragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 技术类检查——机械产品外观性能试验等检查——产品尺寸检查
 */
public class MachinerySizeFragment extends BaseFragment {
    @BindView(R.id.vp_environment)
    ViewPager vpEnvironment;


    private List<Fragment> list=new ArrayList<>();
    private VpAdapter adapter;

    private Performance1Fragment performance1Fragment;
    private Performance2Fragment performance2Fragment;
    @Override
    protected void initEventAndData() {
        performance1Fragment=new Performance1Fragment();
        performance2Fragment=new Performance2Fragment();
        list.add(performance1Fragment);
        list.add(performance2Fragment);
        adapter = new VpAdapter(getChildFragmentManager(),list);
        vpEnvironment.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_machinery_size;
    }
}
