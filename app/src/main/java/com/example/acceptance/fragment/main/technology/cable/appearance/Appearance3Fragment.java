//package com.example.acceptance.fragment.main.technology.cable.appearance;
//
//import android.widget.TextView;
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
//public class Appearance3Fragment extends BaseFragment {
//
//    @BindView(R.id.iv_appearance)
//    MyListView ivAppearance;
//    @BindView(R.id.tv_title_name)
//    TextView tvTitleName;
//    private ProductAdapter productAdapter;
//    private List<String> list = new ArrayList<>();
//
//    @Override
//    protected void initEventAndData() {
//        tvTitleName.setText("产品外观检查——电连接器外观检查");
//        for (int i = 0; i < 2; i++) {
//            list.add("");
//        }
//
//        productAdapter = new ProductAdapter(getActivity(), list);
//        ivAppearance.setAdapter(productAdapter);
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.fragment_appearance1;
//    }
//}
