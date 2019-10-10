package com.example.acceptance.fragment.main;

import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.adapter.LegacyAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.UnresolvedBean;
import com.example.acceptance.greendao.db.UnresolvedBeanDao;
import com.example.acceptance.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 验收遗留问题落实
 */
public class LegacyFragment extends BaseFragment {

    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.lv_list)
    MyListView lvList;
    private List<String> list = new ArrayList<>();

    @Override
    protected void initEventAndData() {
        String id = getArguments().getString("id");

        UnresolvedBeanDao unresolvedBeanDao= MyApplication.getInstances().getCheckUnresolvedDaoSession().getUnresolvedBeanDao();
        List<UnresolvedBean> unresolvedBeans=unresolvedBeanDao.queryBuilder()
                .where(UnresolvedBeanDao.Properties.DataPackageId.eq(id))
                .list();
        LegacyAdapter legacyAdapter = new LegacyAdapter(getActivity(), unresolvedBeans);
        lvList.setAdapter(legacyAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_legacy;
    }
}
