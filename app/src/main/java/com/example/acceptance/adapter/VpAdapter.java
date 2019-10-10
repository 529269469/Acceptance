package com.example.acceptance.adapter;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :created by ${ WYW }
 * 时间：2019/9/17 15
 */
public class VpAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> list;
    public VpAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        try {
            super.setPrimaryItem(container, position, object);
        } catch (Exception e) {

        }
    }

}
