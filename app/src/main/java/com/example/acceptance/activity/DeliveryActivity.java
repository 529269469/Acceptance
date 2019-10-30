package com.example.acceptance.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.acceptance.R;
import com.example.acceptance.adapter.DeliveryAdapter;
import com.example.acceptance.adapter.DeliveryAdapter2;
import com.example.acceptance.base.BaseActivity;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.DeliveryListBean;
import com.example.acceptance.greendao.db.DeliveryListBeanDao;
import com.example.acceptance.view.MyListView;

import java.util.List;

import butterknife.BindView;

public class DeliveryActivity extends BaseActivity {
    @BindView(R.id.lv_list)
    ListView lvList;

    public static Intent openIntent(Context context, String id) {
        Intent intent = new Intent(context, DeliveryActivity.class);
        intent.putExtra("id",id);;
        return intent;
    }
    @Override
    protected void initView() {
        String id = getIntent().getStringExtra("id");
        DeliveryListBeanDao deliveryListBeanDao= MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
        List<DeliveryListBean> deliveryListBeans=deliveryListBeanDao.queryBuilder()
                .where(DeliveryListBeanDao.Properties.DataPackageId.eq(id))
                .whereOr(DeliveryListBeanDao.Properties.ParentId.eq("null"),DeliveryListBeanDao.Properties.ParentId.eq(""))
                .list();

        DeliveryAdapter2 legacyAdapter = new DeliveryAdapter2(this, deliveryListBeans);
        lvList.setAdapter(legacyAdapter);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_delivery;
    }

    @Override
    protected void initDataAndEvent() {

    }
}
