//package com.example.acceptance.fragment.main.technology.cable.report;
//
//import android.widget.ListView;
//
//import com.example.acceptance.R;
//import com.example.acceptance.adapter.kitting.ProductAdapter;
//import com.example.acceptance.base.BaseFragment;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//
//
//public class Report2Fragment extends BaseFragment {
//
//    @BindView(R.id.lv_list)
//    ListView lvList;
//
//    private ProductAdapter productAdapter;
//    private List<String> list=new ArrayList<>();
//    @Override
//    protected void initEventAndData() {
//
//        for (int i = 0; i < 2; i++) {
//            list.add("");
//        }
//
//        productAdapter=new ProductAdapter(getActivity(),list);
//        lvList.setAdapter(productAdapter);
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.fragment_cable_report2;
//    }
//}
