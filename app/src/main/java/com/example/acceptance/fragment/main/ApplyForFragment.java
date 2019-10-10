package com.example.acceptance.fragment.main;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.adapter.ApplyForAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.ApplyItemBean;
import com.example.acceptance.greendao.bean.CheckApplyBean;
import com.example.acceptance.greendao.db.ApplyItemBeanDao;
import com.example.acceptance.greendao.db.CheckApplyBeanDao;
import com.example.acceptance.greendao.db.DataPackageDBeanDao;
import com.example.acceptance.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 验收申请
 */

public class ApplyForFragment extends BaseFragment {

    @BindView(R.id.tv_conclusion)
    TextView tvConclusion;
    @BindView(R.id.tv_other)
    TextView tvOther;
    @BindView(R.id.lv_list)
    MyListView lvList;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_contractCode)
    TextView tvContractCode;
    @BindView(R.id.tv_contractName)
    TextView tvContractName;
    @BindView(R.id.et_applyCompany)
    EditText etApplyCompany;
    @BindView(R.id.et_applicant)
    EditText etApplicant;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_conclusion)
    EditText etConclusion;
    @BindView(R.id.et_description)
    EditText etDescription;
    private List<ApplyItemBean> list = new ArrayList<>();
    private ApplyForAdapter applyForAdapter;

    @Override
    protected void initEventAndData() {
        Bundle bundle=getArguments();
        String id=bundle.getString("id");

        CheckApplyBeanDao checkApplyBeanDao= MyApplication.getInstances().getCheckApplyDaoSession().getCheckApplyBeanDao();
        List<CheckApplyBean> checkApplyBeans=checkApplyBeanDao.queryBuilder()
                .where(CheckApplyBeanDao.Properties.DataPackageId.eq(id))
                .list();

        CheckApplyBean checkApplyBean=checkApplyBeans.get(0);
        tvCode.setText("编号："+checkApplyBean.getCode());
        tvName.setText("名称："+checkApplyBean.getName());
        tvContractCode.setText("合同编号："+checkApplyBean.getContractCode());
        tvContractName.setText("合同名称："+checkApplyBean.getContractName());

        etApplyCompany.setText(checkApplyBean.getApplyCompany());
        etApplicant.setText(checkApplyBean.getApplicant());
        etPhone.setText(checkApplyBean.getPhone());
        etConclusion.setText(checkApplyBean.getConclusion());
        etDescription.setText(checkApplyBean.getDescription());


        ApplyItemBeanDao applyItemBeanDao=MyApplication.getInstances().getApplyItemDaoSession().getApplyItemBeanDao();
        List<ApplyItemBean> applyItemBeans=applyItemBeanDao.queryBuilder()
                .where(ApplyItemBeanDao.Properties.DataPackageId.eq(id))
                .list();
        list.addAll(applyItemBeans);

        applyForAdapter = new ApplyForAdapter(getActivity(), list);
        lvList.setAdapter(applyForAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_apply_for;
    }


}
