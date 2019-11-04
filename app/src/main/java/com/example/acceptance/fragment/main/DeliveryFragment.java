package com.example.acceptance.fragment.main;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.acceptance.R;
import com.example.acceptance.adapter.DeliveryAdapter;
import com.example.acceptance.adapter.File2Adapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.DeliveryListBean;
import com.example.acceptance.greendao.db.DeliveryListBeanDao;
import com.example.acceptance.utils.StringUtils;
import com.example.acceptance.view.AddPopupWindow;
import com.example.acceptance.view.AddPrijectPopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 交付清单
 */
public class DeliveryFragment extends BaseFragment implements DeliveryAdapter.AddDelivery {
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.tv_add_project)
    TextView tvAddProject;
    private File2Adapter fileAdapter;
    private String id;
    private List<DeliveryListBean> listBeans = new ArrayList<>();
    private DeliveryAdapter legacyAdapter;
    private AddPopupWindow addPopupWindow;

    @Override
    protected void onVisible() {
        super.onVisible();
        DeliveryListBeanDao deliveryListBeanDao = MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
        List<DeliveryListBean> deliveryListBeans = deliveryListBeanDao.queryBuilder()
                .where(DeliveryListBeanDao.Properties.DataPackageId.eq(id))
                .where(DeliveryListBeanDao.Properties.IsParent.eq("true"))
                .list();
        listBeans.clear();
        listBeans.addAll(deliveryListBeans);
        legacyAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initEventAndData() {
        id = getArguments().getString("id");
        DeliveryListBeanDao deliveryListBeanDao = MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
        List<DeliveryListBean> deliveryListBeans = deliveryListBeanDao.queryBuilder()
                .where(DeliveryListBeanDao.Properties.DataPackageId.eq(id))
                .where(DeliveryListBeanDao.Properties.IsParent.eq("true"))
                .list();
        listBeans.addAll(deliveryListBeans);
        legacyAdapter = new DeliveryAdapter(getActivity(), listBeans);
        lvList.setAdapter(legacyAdapter);
        legacyAdapter.setAddDelivery(this);

        tvAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProject();
            }
        });
    }

    private void addProject() {
        AddPrijectPopupWindow addPrijectPopupWindow=new AddPrijectPopupWindow(getActivity(),tvAddProject,true,"");
        addPrijectPopupWindow.setAddFile(() -> {
            DeliveryListBeanDao deliveryListBeanDao = MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
            List<DeliveryListBean> deliveryListBeans = deliveryListBeanDao.queryBuilder()
                    .where(DeliveryListBeanDao.Properties.DataPackageId.eq(id))
                    .where(DeliveryListBeanDao.Properties.IsParent.eq("true"))
                    .list();
            listBeans.clear();
            listBeans.addAll(deliveryListBeans);
            legacyAdapter.notifyDataSetChanged();
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_delivery;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (addPopupWindow != null) {
                addPopupWindow.setResult(data, requestCode);
            }
        }
    }

    @Override
    public void setAddDelivery(String documentId) {
        if (StringUtils.isBlank(documentId)) {
            addPopupWindow = new AddPopupWindow(getActivity(), lvList, "", false);
        } else {
            addPopupWindow = new AddPopupWindow(getActivity(), lvList, documentId, true);
        }
        addPopupWindow.setAddFile(new AddPopupWindow.AddFile() {
            @Override
            public void addfile1() {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 11);
            }

            @Override
            public void addfile2() {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 22);
            }

            @Override
            public void addResult() {
                DeliveryListBeanDao deliveryListBeanDao = MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
                List<DeliveryListBean> deliveryListBeans = deliveryListBeanDao.queryBuilder()
                        .where(DeliveryListBeanDao.Properties.DataPackageId.eq(id))
                        .whereOr(DeliveryListBeanDao.Properties.ParentId.eq("null"), DeliveryListBeanDao.Properties.ParentId.eq(""))
                        .list();
                listBeans.clear();
                listBeans.addAll(deliveryListBeans);
                legacyAdapter.notifyDataSetChanged();

            }
        });

    }

    @Override
    public void setAddProject(String documentId) {
        AddPrijectPopupWindow addPrijectPopupWindow=new AddPrijectPopupWindow(getActivity(),tvAddProject,false,documentId);
        addPrijectPopupWindow.setAddFile(() -> {
            DeliveryListBeanDao deliveryListBeanDao = MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
            List<DeliveryListBean> deliveryListBeans = deliveryListBeanDao.queryBuilder()
                    .where(DeliveryListBeanDao.Properties.DataPackageId.eq(id))
                    .where(DeliveryListBeanDao.Properties.IsParent.eq("true"))
                    .list();
            listBeans.clear();
            listBeans.addAll(deliveryListBeans);
            legacyAdapter.notifyDataSetChanged();
        });
    }

}
