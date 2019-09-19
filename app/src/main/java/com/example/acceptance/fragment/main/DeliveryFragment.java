package com.example.acceptance.fragment.main;

import com.example.acceptance.R;
import com.example.acceptance.adapter.DeliveryAdapter;
import com.example.acceptance.adapter.LegacyAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 交付清单
 */
public class DeliveryFragment extends BaseFragment {
    @BindView(R.id.lv_list)
    MyListView lvList;
    private List<String> list = new ArrayList<>();

    @Override
    protected void initEventAndData() {
        for (int i = 0; i < 5; i++) {
            list.add("");
        }
        DeliveryAdapter legacyAdapter = new DeliveryAdapter(getActivity(), list);
        lvList.setAdapter(legacyAdapter);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_delivery;
    }
}
