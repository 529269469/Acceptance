package com.example.acceptance.fragment.main;

import com.example.acceptance.R;
import com.example.acceptance.adapter.DeliveryAdapter;
import com.example.acceptance.adapter.LegacyAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.DeliveryListBean;
import com.example.acceptance.greendao.db.DeliveryListBeanDao;
import com.example.acceptance.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 交付清单
 */
public class DeliveryFragment extends BaseFragment {
    @BindView(R.id.lv_list)
    MyListView lvList;

    @Override
    protected void initEventAndData() {
        String id = getArguments().getString("id");
        DeliveryListBeanDao deliveryListBeanDao= MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
        List<DeliveryListBean> deliveryListBeans=deliveryListBeanDao.queryBuilder()
                .where(DeliveryListBeanDao.Properties.DataPackageId.eq(id))
                .whereOr(DeliveryListBeanDao.Properties.ParentId.eq("null"),DeliveryListBeanDao.Properties.ParentId.eq(""))
                .list();

        DeliveryAdapter legacyAdapter = new DeliveryAdapter(getActivity(), deliveryListBeans);
        lvList.setAdapter(legacyAdapter);




    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_delivery;
    }
}
