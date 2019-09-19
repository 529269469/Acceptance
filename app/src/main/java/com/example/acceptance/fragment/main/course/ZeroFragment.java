package com.example.acceptance.fragment.main.course;

import com.example.acceptance.R;
import com.example.acceptance.adapter.kitting.ProductAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 过程检查——电气产品——产品质量问题归零情况
 */

public class ZeroFragment extends BaseFragment {


    @BindView(R.id.lv_standard)
    MyListView lvStandard;

    private ProductAdapter productAdapter;
    private List<String> list=new ArrayList<>();
    @Override
    protected void initEventAndData() {
        for (int i = 0; i < 5; i++) {
            list.add("");
        }

        productAdapter=new ProductAdapter(getActivity(),list);
        lvStandard.setAdapter(productAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zero;
    }
}
