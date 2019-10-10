//package com.example.acceptance.fragment.main.technology.electric;
//
//import androidx.fragment.app.Fragment;
//import androidx.viewpager.widget.ViewPager;
//
//import com.example.acceptance.R;
//import com.example.acceptance.adapter.VpAdapter;
//import com.example.acceptance.base.BaseFragment;
//import com.example.acceptance.fragment.main.technology.electric.appearance.Appearance1Fragment;
//import com.example.acceptance.fragment.main.technology.electric.appearance.Appearance2Fragment;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//
//
///**
// * 技术类检查——电气产品外观性能试验等检查——产品外观检查
// */
//public class ElectricAppearanceFragment extends BaseFragment {
//
//    @BindView(R.id.vp_environment)
//    ViewPager vpEnvironment;
//
//
//    private List<Fragment> list=new ArrayList<>();
//    private VpAdapter adapter;
//
//    private Appearance1Fragment appearance1Fragment;
//    private Appearance2Fragment appearance2Fragment;
//
//    @Override
//    protected void initEventAndData() {
//        appearance1Fragment=new Appearance1Fragment();
//        appearance2Fragment=new Appearance2Fragment();
//        list.add(appearance1Fragment);
//        list.add(appearance2Fragment);
//        adapter = new VpAdapter(getChildFragmentManager(),list);
//        vpEnvironment.setAdapter(adapter);
//
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.fragment_electric_appearance;
//    }
//}
