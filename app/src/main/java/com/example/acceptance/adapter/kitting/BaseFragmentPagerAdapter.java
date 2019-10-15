package com.example.acceptance.adapter.kitting;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :created by ${ WYW }
 * 时间：2019/10/15 21
 */
public abstract class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private FragmentManager mFragmentManager;
    private List<String> tagList = new ArrayList<String>();

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.mFragmentManager = fm;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        tagList.add(makeFragmentName(container.getId(), getItemId(position)));
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        super.destroyItem(container, position, object);
        tagList.remove(makeFragmentName(container.getId(), getItemId(position)));
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    public void update(int position){
        Fragment fragment = (Fragment)mFragmentManager.findFragmentByTag(tagList.get(position));
        if(fragment == null){
            return;
        }
        if(fragment instanceof UpdateAble){//这里唯一的要求是Fragment要实现UpdateAble接口
            ((UpdateAble)fragment).update();
        }
    }

    public interface UpdateAble {
        public void update();
    }
}

