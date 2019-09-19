package com.example.acceptance.fragment.main.technology;

import com.example.acceptance.R;
import com.example.acceptance.adapter.kitting.LvFileAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 技术类检查——依据文件检查
 */
public class TechnologyFileFragment extends BaseFragment {

    @BindView(R.id.lv_file_kitting)
    MyListView lvFileKitting;
    @BindView(R.id.lv_file_kitting2)
    MyListView lvFileKitting2;

    private List<String> list=new ArrayList<>();
    private List<String> list2=new ArrayList<>();
    @Override
    protected void initEventAndData() {

        list.add("");
        list.add("");
        list.add("");
        LvFileAdapter lvFileAdapter=new LvFileAdapter(getActivity(),list);
        lvFileKitting.setAdapter(lvFileAdapter);

        for (int i = 0; i < 6; i++) {
            list2.add("");
        }
        LvFileAdapter lvFileAdapter2=new LvFileAdapter(getActivity(),list2);
        lvFileKitting2.setAdapter(lvFileAdapter2);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_technology_file;
    }
}
