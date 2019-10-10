package com.example.acceptance.fragment.main;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.adapter.ApplyForAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.ApplyItemBean;
import com.example.acceptance.greendao.bean.CheckTaskBean;
import com.example.acceptance.greendao.db.ApplyItemBeanDao;
import com.example.acceptance.greendao.db.CheckApplyBeanDao;
import com.example.acceptance.greendao.db.CheckTaskBeanDao;
import com.example.acceptance.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 验收任务单
 */
public class TaskFragment extends BaseFragment {

    @BindView(R.id.lv_list)
    MyListView lvList;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_issuer)
    EditText tvIssuer;
    @BindView(R.id.tv_accepter)
    EditText tvAccepter;
    @BindView(R.id.tv_applyCompany)
    EditText tvApplyCompany;
    @BindView(R.id.tv_phone)
    EditText tvPhone;
    @BindView(R.id.tv_issueDept)
    EditText tvIssueDept;
    @BindView(R.id.tv_acceptDate)
    EditText tvAcceptDate;
    @BindView(R.id.tv_applicant)
    EditText tvApplicant;
    @BindView(R.id.tv_checkDate)
    EditText tvCheckDate;

    private List<ApplyItemBean> list = new ArrayList<>();
    private ApplyForAdapter applyForAdapter;

    @Override
    protected void initEventAndData() {
        Bundle bundle=getArguments();
        String id=bundle.getString("id");

        CheckTaskBeanDao checkTaskBeanDao=MyApplication.getInstances().getCheckTaskDaoSession().getCheckTaskBeanDao();
        List<CheckTaskBean> checkTaskBeans = checkTaskBeanDao.queryBuilder()
                .where(CheckTaskBeanDao.Properties.DataPackageId.eq(id))
                .list();
        CheckTaskBean checkTaskBean=checkTaskBeans.get(0);
        tvCode.setText("编号："+checkTaskBean.getCode());
        tvName.setText("名称："+checkTaskBean.getName());
        tvIssuer.setText(checkTaskBean.getIssuer());
        tvAccepter.setText(checkTaskBean.getAccepter());
        tvApplyCompany.setText(checkTaskBean.getApplyCompany());
        tvPhone.setText(checkTaskBean.getPhone());
        tvIssueDept.setText(checkTaskBean.getIssueDept());
        tvAcceptDate.setText(checkTaskBean.getAcceptDate());
        tvApplicant.setText(checkTaskBean.getApplicant());
        tvCheckDate.setText(checkTaskBean.getCheckDate());

        ApplyItemBeanDao applyItemBeanDao = MyApplication.getInstances().getApplyItemDaoSession().getApplyItemBeanDao();
        List<ApplyItemBean> applyItemBeans = applyItemBeanDao.queryBuilder()
                .where(ApplyItemBeanDao.Properties.DataPackageId.eq(id))
                .list();
        list.addAll(applyItemBeans);
        applyForAdapter = new ApplyForAdapter(getActivity(), list);
        lvList.setAdapter(applyForAdapter);



    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task;
    }
}
