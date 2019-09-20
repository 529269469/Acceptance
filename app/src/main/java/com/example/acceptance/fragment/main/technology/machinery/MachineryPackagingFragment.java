package com.example.acceptance.fragment.main.technology.machinery;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.acceptance.R;
import com.example.acceptance.adapter.VpAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.fragment.main.technology.machinery.packaging.Packaging1Fragment;
import com.example.acceptance.fragment.main.technology.machinery.packaging.Packaging2Fragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 技术类检查——机械产品外观性能试验等检查——包装检查
 */
public class MachineryPackagingFragment extends BaseFragment {
    private List<Fragment> list=new ArrayList<>();
    private VpAdapter adapter;
    @BindView(R.id.vp_environment)
    ViewPager vpEnvironment;

    private Packaging1Fragment packaging1Fragment;
    private Packaging2Fragment packaging2Fragment;
    @Override
    protected void initEventAndData() {
        packaging1Fragment=new Packaging1Fragment();
        packaging2Fragment=new Packaging2Fragment();

        list.add(packaging1Fragment);
        list.add(packaging2Fragment);
        adapter = new VpAdapter(getChildFragmentManager(),list);
        vpEnvironment.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_machinery_packaging;
    }
}
