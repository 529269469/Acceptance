package com.example.acceptance.fragment.main;

import com.example.acceptance.R;
import com.example.acceptance.adapter.LegacyAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 验收遗留问题落实
 */
public class LegacyFragment extends BaseFragment {
    @BindView(R.id.lv_list2)
    MyListView lvList2;
    private List<String> list = new ArrayList<>();

    @Override
    protected void initEventAndData() {
        for (int i = 0; i < 5; i++) {
            list.add("");
        }
        LegacyAdapter legacyAdapter = new LegacyAdapter(getActivity(), list);
        lvList2.setAdapter(legacyAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_legacy;
    }
}
