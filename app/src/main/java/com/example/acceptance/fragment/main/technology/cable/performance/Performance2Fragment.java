package com.example.acceptance.fragment.main.technology.cable.performance;

import android.widget.ListView;

import com.example.acceptance.R;
import com.example.acceptance.base.BaseFragment;

import butterknife.BindView;


public class Performance2Fragment extends BaseFragment {


    @BindView(R.id.lv_list)
    ListView lvList;

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_performance2;
    }
}
