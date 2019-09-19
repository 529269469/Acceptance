package com.example.acceptance.fragment.main.technology.electric;

import com.example.acceptance.R;
import com.example.acceptance.adapter.kitting.ProductAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 技术类检查——电气产品外观性能试验等检查——技术状态检查
 */
public class ElectricConditionsFragment extends BaseFragment {


    @BindView(R.id.lv_conditions)
    MyListView lvConditions;

    private ProductAdapter productAdapter;
    private List<String> list=new ArrayList<>();
    @Override
    protected void initEventAndData() {
        for (int i = 0; i < 4; i++) {
            list.add("");
        }

        productAdapter=new ProductAdapter(getActivity(),list);
        lvConditions.setAdapter(productAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_electric_conditions;
    }
}
