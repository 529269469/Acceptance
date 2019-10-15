package com.example.acceptance.fragment.main;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.DataPackageDBean;
import com.example.acceptance.greendao.db.DataPackageDBeanDao;
import com.example.acceptance.utils.SPUtils;
import com.example.acceptance.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;

/**
 * 详情信息
 */
public class ParticularsFragment extends BaseFragment {

    @BindView(R.id.tv_code)
    EditText tvCode;
    @BindView(R.id.tv_creator)
    EditText tvCreator;
    @BindView(R.id.tv_responseUnit)
    EditText tvResponseUnit;
    @BindView(R.id.tv_productName)
    EditText tvProductName;
    @BindView(R.id.tv_modalCode)
    EditText tvModalCode;
    @BindView(R.id.tv_name_part)
    EditText tvName;
    @BindView(R.id.tv_createTime)
    EditText tvCreateTime;
    @BindView(R.id.tv_type_part)
    EditText tvType;
    @BindView(R.id.tv_productType)
    EditText tvProductType;
    @BindView(R.id.tv_batch)
    EditText tvBatch;
    @BindView(R.id.tv_productCode)
    EditText tvProductCode;
    @BindView(R.id.tv_save)
    TextView tv_save;

    private String id;

    @Override
    protected void initEventAndData() {
        Bundle bundle = getArguments();
        id = bundle.getString("id");
        DataPackageDBeanDao dataPackageDBeanDao = MyApplication.getInstances().getDataPackageDaoSession().getDataPackageDBeanDao();
        List<DataPackageDBean> list = dataPackageDBeanDao.queryBuilder()
                .where(DataPackageDBeanDao.Properties.Id.eq(id))
                .list();

        DataPackageDBean dataPackageDBean = list.get(0);
        SPUtils.put(getActivity(),"path",dataPackageDBean.getUpLoadFile());
        tvCode.setText(dataPackageDBean.getCode());
        tvCreator.setText(dataPackageDBean.getCreator());
        tvResponseUnit.setText(dataPackageDBean.getResponseUnit());
        tvProductName.setText(dataPackageDBean.getProductName());
        tvModalCode.setText(dataPackageDBean.getModalCode());
        tvName.setText(dataPackageDBean.getName());
        tvCreateTime.setText(dataPackageDBean.getCreateTime());
        tvType.setText(dataPackageDBean.getType());
        tvProductType.setText(dataPackageDBean.getProductType());
        tvBatch.setText(dataPackageDBean.getBatch());
        tvProductCode.setText(dataPackageDBean.getProductCode());

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataPackageDBean packageDBean=new DataPackageDBean(list.get(0).getUId(),
                        list.get(0).getNamePackage(),
                        list.get(0).getUpLoadFile(),
                        list.get(0).getId(),
                        tvName.getText().toString().trim(),
                        tvCode.getText().toString().trim(),
                        tvType.getText().toString().trim(),
                        tvResponseUnit.getText().toString().trim(),
                        tvModalCode.getText().toString().trim(),
                        tvProductName.getText().toString().trim(),
                        tvProductCode.getText().toString().trim(),
                        tvProductType.getText().toString().trim(),
                        tvBatch.getText().toString().trim(),
                        tvCreator.getText().toString().trim(),
                        tvCreateTime.getText().toString().trim());

                dataPackageDBeanDao.update(packageDBean);
                ToastUtils.getInstance().showTextToast(getActivity(),"保存成功");
            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_particulars;
    }

}
