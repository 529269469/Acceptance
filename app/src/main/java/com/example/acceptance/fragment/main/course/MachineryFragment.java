package com.example.acceptance.fragment.main.course;

import com.example.acceptance.R;
import com.example.acceptance.adapter.kitting.ProductAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.greendao.bean.CheckItemBean;
import com.example.acceptance.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 过程检查——机械产品——原材料，标准件检查
 */

public class MachineryFragment extends BaseFragment {


    @BindView(R.id.lv_standard)
    MyListView lvStandard;

    private ProductAdapter productAdapter;
    private List<CheckItemBean> list=new ArrayList<>();
    @Override
    protected void initEventAndData() {


        productAdapter=new ProductAdapter(getActivity(),list);
        lvStandard.setAdapter(productAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_machinery;
    }
}
