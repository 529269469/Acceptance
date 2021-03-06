package com.example.acceptance.fragment.main.course;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.acceptance.R;
import com.example.acceptance.adapter.TbAdapter;
import com.example.acceptance.adapter.kitting.ProductAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.fragment.main.kitting.KittingFileFragment;
import com.example.acceptance.fragment.main.kitting.KittingProductFragment;
import com.example.acceptance.view.MyListView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 过程检查——电气产品——元器件，原材料，标准件检查
 */

public class CourseFragment extends BaseFragment {


    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.tb)
    TabLayout tb;
    private TbAdapter adapter;
    private List<String> listTitle=new ArrayList<>();
    private List<Fragment> list=new ArrayList<>();
    private StandardFragment2 standardFragment;
    private String id;

    private int pos;
    @Override
    protected void onVisible() {
        super.onVisible();
        listTitle.clear();
        listTitle.add("过程检查");
        standardFragment=new StandardFragment2();
        Bundle bundle=new Bundle();
        bundle.putString("id", id);
        bundle.putString("type", "3");
        standardFragment.setArguments(bundle);
        list.clear();
        list.add(standardFragment);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void initEventAndData() {
        id = getArguments().getString("id");
        pos= getArguments().getInt("pos");
        listTitle.add("过程检查");

        standardFragment=new StandardFragment2();
        Bundle bundle=new Bundle();
        bundle.putString("id", id);
        bundle.putString("type", "3");
        bundle.putInt("pos", pos);
        standardFragment.setArguments(bundle);
        list.add(standardFragment);

        adapter=new TbAdapter(getChildFragmentManager(),listTitle,list);
        vp.setAdapter(adapter);
        tb.setTabMode(TabLayout.MODE_FIXED);
        tb.setupWithViewPager(vp);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_course;
    }
}
