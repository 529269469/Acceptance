package com.example.acceptance.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :created by ${ WYW }
 * 时间：2019/9/27 10
 */
public class Tb2Adapter extends FragmentStatePagerAdapter {
    public List<Fragment> mFragments ;
    private ViewPager mPager;
    private List<String> listTitle;
    //当前View
//    private Fragment mPrimaryItem;

    public Tb2Adapter(FragmentManager fm, ViewPager vp,List<Fragment> mFragments,List<String> listTitle) {
        super(fm);
        this.mPager = vp;
        this.mFragments=mFragments;
        this.listTitle=listTitle;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTitle.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position,
                               Object object) {
        super.setPrimaryItem(container, position, object);
//        mPrimaryItem = (Fragment) object;
    }

    @Override
    public int getItemPosition(Object object) {
        // 加上这个判断之后添加功能没问题，
        // 添加完页面之后，其它已添加的页面也不会刷新，
        // 但是删除的时候就出现空白页面
//        if (object == mPrimaryItem) {
//            return POSITION_UNCHANGED;
//        }
        //要使Adapter的notifyDataSetChanged()方法生效，
        // 除了Adapter需要继承FragmentStatePagerAdapter之外，
        // 还需要这里设置返回值为POSITION_NONE
        return POSITION_NONE;
    }

    public void addPage(Fragment titleViewBean,String name) {
        mFragments.add(mFragments.size(), titleViewBean);
        listTitle.add(name);
        this.notifyDataSetChanged();
        //每次添加都自动跳到最后一个页面
        mPager.setCurrentItem(mFragments.size(), false);
        //每次动态添加、删除页面之后重新设置viewpager的缓存数量
        mPager.setOffscreenPageLimit(mFragments.size());
    }

    /**
     * This method removes the pages from ViewPager
     */
    public void removePages(int position) {
        mFragments.remove(position);
        listTitle.remove(position);
        mPager.removeViewAt(position);
        this.notifyDataSetChanged();
        //每次动态添加、删除页面之后重新设置viewpager的缓存数量
        mPager.setOffscreenPageLimit(mFragments.size());
    }
}
