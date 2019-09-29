package com.example.acceptance.fragment.main;

import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.adapter.ApplyForAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 验收申请
 */

public class ApplyForFragment extends BaseFragment {

    @BindView(R.id.tv_conclusion)
    TextView tvConclusion;

    @BindView(R.id.tv_other)
    TextView tvOther;
    @BindView(R.id.lv_list)
    MyListView lvList;
    private List<String> list=new ArrayList<>();
    private ApplyForAdapter applyForAdapter;

    @Override
    protected void initEventAndData() {
        for (int i = 0; i < 4; i++) {
            list.add("");
        }
        applyForAdapter = new ApplyForAdapter(getActivity(),list);
        lvList.setAdapter(applyForAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_apply_for;
    }


}
