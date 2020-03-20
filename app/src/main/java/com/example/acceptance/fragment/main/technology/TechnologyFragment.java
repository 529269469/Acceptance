package com.example.acceptance.fragment.main.technology;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.acceptance.R;
import com.example.acceptance.adapter.TbAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.fragment.main.kitting.KittingFileFragment;
import com.example.acceptance.fragment.main.kitting.KittingProductFragment;
import com.example.acceptance.fragment.main.technology.machinery.TechnologySizeFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class TechnologyFragment extends BaseFragment {

    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.tb)
    TabLayout tb;
    private TbAdapter adapter;
    private List<String> listTitle=new ArrayList<>();
    private List<Fragment> list=new ArrayList<>();
    private TechnologyFileFragment technologyFileFragment;
    private TechnologySizeFragment technologySizeFragment;
    private String id;
    private boolean isDel;
    private int pos;
    @Override
    protected void onVisible() {
        super.onVisible();
        listTitle.clear();
        listTitle.add("依据文件检查");
        listTitle.add("技术类检查");

        Bundle bundle=new Bundle();
        bundle.putString("id", id);
        bundle.putString("type", "4");

        technologyFileFragment=new TechnologyFileFragment();
        technologyFileFragment.setArguments(bundle);
        technologySizeFragment=new TechnologySizeFragment();
        technologySizeFragment.setArguments(bundle);
        list.clear();
        list.add(technologyFileFragment);
        list.add(technologySizeFragment);

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void initEventAndData() {
        id = getArguments().getString("id");
        isDel = getArguments().getBoolean("isDel");
        pos= getArguments().getInt("pos");
        listTitle.add("依据文件检查");
        listTitle.add("技术类检查");

        Bundle bundle=new Bundle();
        bundle.putString("id", id);
        bundle.putString("type", "4");
        bundle.putInt("pos", pos);
        technologyFileFragment=new TechnologyFileFragment();
        technologyFileFragment.setArguments(bundle);
        list.add(technologyFileFragment);
        technologySizeFragment=new TechnologySizeFragment();
        technologySizeFragment.setArguments(bundle);
        list.add(technologySizeFragment);

        adapter=new TbAdapter(getChildFragmentManager(),listTitle,list);
        vp.setAdapter(adapter);
        tb.setTabMode(TabLayout.MODE_FIXED);
        tb.setupWithViewPager(vp);

        if (isDel){
            vp.setCurrentItem(1);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_technology;
    }
}
