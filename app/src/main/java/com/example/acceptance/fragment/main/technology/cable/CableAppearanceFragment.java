//package com.example.acceptance.fragment.main.technology.cable;
//
//import androidx.fragment.app.Fragment;
//import androidx.viewpager.widget.ViewPager;
//
//import com.example.acceptance.R;
//import com.example.acceptance.adapter.VpAdapter;
//import com.example.acceptance.base.BaseFragment;
//import com.example.acceptance.fragment.main.technology.cable.appearance.Appearance1Fragment;
//import com.example.acceptance.fragment.main.technology.cable.appearance.Appearance2Fragment;
//import com.example.acceptance.fragment.main.technology.cable.appearance.Appearance3Fragment;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//
//
///**
// * 技术类检查——电缆产品外观性能试验等检查——产品外观检查
// */
//public class CableAppearanceFragment extends BaseFragment {
//
//    @BindView(R.id.vp_environment)
//    ViewPager vpEnvironment;
//    private Appearance1Fragment appearance1Fragment;
//    private Appearance2Fragment appearance2Fragment;
//    private Appearance3Fragment appearance3Fragment;
//    private List<Fragment> list=new ArrayList<>();
//    private VpAdapter adapter;
//    @Override
//    protected void initEventAndData() {
//        appearance1Fragment=new Appearance1Fragment();
//        appearance2Fragment=new Appearance2Fragment();
//        appearance3Fragment=new Appearance3Fragment();
//        list.add(appearance1Fragment);
//        list.add(appearance3Fragment);
//        list.add(appearance2Fragment);
//        adapter = new VpAdapter(getChildFragmentManager(),list);
//        vpEnvironment.setAdapter(adapter);
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.fragment_cable_appearance;
//    }
//}
