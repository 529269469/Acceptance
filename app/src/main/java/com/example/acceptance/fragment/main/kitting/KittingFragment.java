package com.example.acceptance.fragment.main.kitting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.acceptance.R;
import com.example.acceptance.adapter.TbAdapter;
import com.example.acceptance.base.BaseFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 齐套性检查——依据文件检查
 */
public class KittingFragment extends BaseFragment {


    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.tb)
    TabLayout tb;
    private TbAdapter adapter;
    private List<String> listTitle=new ArrayList<>();
    private List<Fragment> list=new ArrayList<>();
    private KittingFileFragment kittingFileFragment;
    private KittingProductFragment kittingProductFragment;
    @Override
    protected void initEventAndData() {
        String id=getArguments().getString("id");

        listTitle.add("依据文件检查");
        listTitle.add("产品齐套性检查");
        kittingFileFragment=new KittingFileFragment();
        kittingProductFragment=new KittingProductFragment();
        Bundle bundle=new Bundle();
        bundle.putString("id", id);
        kittingFileFragment.setArguments(bundle);
        kittingProductFragment.setArguments(bundle);
        list.add(kittingFileFragment);
        list.add(kittingProductFragment);

        adapter=new TbAdapter(getChildFragmentManager(),listTitle,list);
        vp.setAdapter(adapter);
        tb.setTabMode(TabLayout.MODE_FIXED);
        tb.setupWithViewPager(vp);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kitting;
    }
}
