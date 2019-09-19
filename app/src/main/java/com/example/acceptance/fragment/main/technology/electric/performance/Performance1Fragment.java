package com.example.acceptance.fragment.main.technology.electric.performance;

import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.acceptance.R;
import com.example.acceptance.base.BaseFragment;

import butterknife.BindView;


public class Performance1Fragment extends BaseFragment {


    @BindView(R.id.lv_list)
    ListView lvList;

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_performance1;
    }
}
