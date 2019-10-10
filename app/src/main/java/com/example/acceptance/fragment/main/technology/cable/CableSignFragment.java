//package com.example.acceptance.fragment.main.technology.cable;
//
//import com.example.acceptance.R;
//import com.example.acceptance.adapter.kitting.ProductAdapter;
//import com.example.acceptance.base.BaseFragment;
//import com.example.acceptance.view.MyListView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//
//
///**
// * 技术类检查——电缆产品外观性能试验等检查——质量证明文字签署
// */
//public class CableSignFragment extends BaseFragment {
//    @BindView(R.id.lv_list)
//    MyListView lvList;
//    private ProductAdapter productAdapter;
//    private List<String> list=new ArrayList<>();
//    @Override
//    protected void initEventAndData() {
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
//        return R.layout.fragment_able_sign;
//    }
//}
