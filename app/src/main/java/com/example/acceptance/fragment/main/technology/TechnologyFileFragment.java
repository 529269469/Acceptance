package com.example.acceptance.fragment.main.technology;

import com.example.acceptance.R;
import com.example.acceptance.adapter.kitting.LvFileAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.DeliveryListBean;
import com.example.acceptance.greendao.db.DeliveryListBeanDao;
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
    private List<String> list = new ArrayList<>();
    private List<String> list2 = new ArrayList<>();

    @Override
    protected void initEventAndData() {
        String id=getArguments().getString("id");


        DeliveryListBeanDao deliveryListBeanDao= MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
        List<DeliveryListBean> deliveryListBeans=deliveryListBeanDao.queryBuilder()
                .where(DeliveryListBeanDao.Properties.DataPackageId.eq(id))
                .where(DeliveryListBeanDao.Properties.Project.eq("合同"),
                        DeliveryListBeanDao.Properties.Project.eq("明细表"),
                        DeliveryListBeanDao.Properties.Project.eq("任务书"))
                .list();

        LvFileAdapter lvFileAdapter=new LvFileAdapter(getActivity(),deliveryListBeans);
        lvFileKitting.setAdapter(lvFileAdapter);


        List<DeliveryListBean> deliveryListBeanList=deliveryListBeanDao.queryBuilder()
                .where(DeliveryListBeanDao.Properties.DataPackageId.eq(id))
                .where(DeliveryListBeanDao.Properties.Project.notEq("合同"),
                        DeliveryListBeanDao.Properties.Project.notEq("明细表"),
                        DeliveryListBeanDao.Properties.Project.notEq("任务书"))
                .list();
        LvFileAdapter lvFileAdapter2=new LvFileAdapter(getActivity(),deliveryListBeanList);
        lvFileKitting2.setAdapter(lvFileAdapter2);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_technology_file;
    }
}
