package com.example.acceptance.fragment.main;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.adapter.MoAdapter;
import com.example.acceptance.adapter.TypeAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.bean.PackageBean;
import com.example.acceptance.greendao.bean.DataPackageDBean;
import com.example.acceptance.greendao.db.DataPackageDBeanDao;
import com.example.acceptance.utils.SPUtils;
import com.example.acceptance.utils.StringUtils;
import com.example.acceptance.utils.ToastUtils;
import com.example.acceptance.view.MyListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    @BindView(R.id.tv_modelCode)
    EditText tvModelCode;
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

    @BindView(R.id.tv_modelSeries)
    EditText tvModelSeries;
    @BindView(R.id.tv_modelSeriesName)
    EditText tvModelSeriesName;
    @BindView(R.id.tv_type_part2)
    TextView tvTypePart2;
    @BindView(R.id.tv_responseUnit2)
    TextView tvResponseUnit2;
    @BindView(R.id.tv_productType2)
    TextView tvProductType2;

    private String id;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String words = editable.toString();
            if (!StringUtils.isBlank(words)) {
                DataPackageDBeanDao dataPackageDBeanDao = MyApplication.getInstances().getDataPackageDaoSession().getDataPackageDBeanDao();
                List<DataPackageDBean> list = dataPackageDBeanDao.queryBuilder()
                        .where(DataPackageDBeanDao.Properties.Id.eq(id))
                        .list();
                DataPackageDBean packageDBean = new DataPackageDBean(list.get(0).getUId(),
                        list.get(0).getNamePackage(),
                        list.get(0).getUpLoadFile(),
                        list.get(0).getId(),
                        tvName.getText().toString().trim(),
                        tvCode.getText().toString().trim(),
                        tvType.getText().toString().trim(),
                        tvResponseUnit.getText().toString().trim(),
                        tvModelCode.getText().toString().trim(),
                        tvProductName.getText().toString().trim(),
                        tvProductCode.getText().toString().trim(),
                        tvProductType.getText().toString().trim(),
                        tvBatch.getText().toString().trim(),
                        tvCreator.getText().toString().trim(),
                        tvCreateTime.getText().toString().trim(),
                        tvModelSeries.getText().toString().trim(),
                        tvModelSeriesName.getText().toString().trim(),
                        list.get(0).getPkgTemplateId(),
                        list.get(0).getLifecycleStateId(),
                        list.get(0).getLifecycleStateIdentifier());
                dataPackageDBeanDao.update(packageDBean);
            }

        }
    };


    @Override
    protected void initEventAndData() {
        Bundle bundle = getArguments();
        id = bundle.getString("id");
        DataPackageDBeanDao dataPackageDBeanDao = MyApplication.getInstances().getDataPackageDaoSession().getDataPackageDBeanDao();
        List<DataPackageDBean> list = dataPackageDBeanDao.queryBuilder()
                .where(DataPackageDBeanDao.Properties.Id.eq(id))
                .list();

        DataPackageDBean dataPackageDBean = list.get(0);
        SPUtils.put(getActivity(), "path", dataPackageDBean.getUpLoadFile());

        tvCode.setText(dataPackageDBean.getCode());
        tvCreator.setText(dataPackageDBean.getCreator());
        tvResponseUnit.setText(dataPackageDBean.getResponseUnit());
        tvProductName.setText(dataPackageDBean.getProductName());
        tvModelCode.setText(dataPackageDBean.getModelCode());
        tvName.setText(dataPackageDBean.getName());
        tvCreateTime.setText(dataPackageDBean.getCreateTime());
        tvType.setText(dataPackageDBean.getType());
        tvProductType.setText(dataPackageDBean.getProductType());
        tvBatch.setText(dataPackageDBean.getBatch());
        tvProductCode.setText(dataPackageDBean.getProductCode());
        tvModelSeries.setText(dataPackageDBean.getModelSeries());
        tvModelSeriesName.setText(dataPackageDBean.getModelSeriesName());

        tvModelSeries.addTextChangedListener(textWatcher);
        tvModelSeriesName.addTextChangedListener(textWatcher);
        tvCode.addTextChangedListener(textWatcher);
        tvCreator.addTextChangedListener(textWatcher);
        tvResponseUnit.addTextChangedListener(textWatcher);
        tvProductName.addTextChangedListener(textWatcher);
        tvModelCode.addTextChangedListener(textWatcher);
        tvName.addTextChangedListener(textWatcher);
        tvCreateTime.addTextChangedListener(textWatcher);
        tvType.addTextChangedListener(textWatcher);
        tvProductType.addTextChangedListener(textWatcher);
        tvBatch.addTextChangedListener(textWatcher);
        tvProductCode.addTextChangedListener(textWatcher);

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ToastUtils.getInstance().showTextToast(getActivity(), "保存成功");
            }
        });

        tvTypePart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type= (String) SPUtils.get(getActivity(),"PacketType","");
                if (!StringUtils.isBlank(type)){
                    mobanPo(tvType,type);
                }
            }
        });
        tvResponseUnit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type= (String) SPUtils.get(getActivity(),"DutyType","");
                if (!StringUtils.isBlank(type)){
                    mobanPo(tvResponseUnit,type);
                }
            }
        });
        tvProductType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type= (String) SPUtils.get(getActivity(),"ProductType","");
                if (!StringUtils.isBlank(type)){
                    mobanPo(tvProductType,type);
                }
            }
        });
    }


    private void mobanPo(EditText editText,String str) {
        View poview = getLayoutInflater().inflate(R.layout.moban_item2, null);
        PopupWindow daochu = new PopupWindow(poview);
        daochu.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        daochu.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        daochu.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        daochu.setOutsideTouchable(true);
        daochu.setFocusable(true);
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;
        getActivity().getWindow().setAttributes(lp);
        daochu.showAsDropDown(editText);

        daochu.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = getActivity().getWindow().getAttributes();
            lp1.alpha = 1f;
            getActivity().getWindow().setAttributes(lp1);
        });

        List<String> list = new ArrayList<>();
        MyListView lv_moban = poview.findViewById(R.id.lv_moban);
        String[] ss=str.split(",");
        for (int i = 0; i < ss.length; i++) {
            list.add(ss[i]);
        }

        TypeAdapter moAdapter = new TypeAdapter(getActivity(), list);
        lv_moban.setAdapter(moAdapter);

        lv_moban.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                daochu.dismiss();
                editText.setText(list.get(i));
                DataPackageDBeanDao dataPackageDBeanDao = MyApplication.getInstances().getDataPackageDaoSession().getDataPackageDBeanDao();
                List<DataPackageDBean> list = dataPackageDBeanDao.queryBuilder()
                        .where(DataPackageDBeanDao.Properties.Id.eq(id))
                        .list();
                DataPackageDBean packageDBean = new DataPackageDBean(list.get(0).getUId(),
                        list.get(0).getNamePackage(),
                        list.get(0).getUpLoadFile(),
                        list.get(0).getId(),
                        tvName.getText().toString().trim(),
                        tvCode.getText().toString().trim(),
                        tvType.getText().toString().trim(),
                        tvResponseUnit.getText().toString().trim(),
                        tvModelCode.getText().toString().trim(),
                        tvProductName.getText().toString().trim(),
                        tvProductCode.getText().toString().trim(),
                        tvProductType.getText().toString().trim(),
                        tvBatch.getText().toString().trim(),
                        tvCreator.getText().toString().trim(),
                        tvCreateTime.getText().toString().trim(),
                        tvModelSeries.getText().toString().trim(),
                        tvModelSeriesName.getText().toString().trim(),
                        list.get(0).getPkgTemplateId(),
                        list.get(0).getLifecycleStateId(),
                        list.get(0).getLifecycleStateIdentifier());
                dataPackageDBeanDao.update(packageDBean);

            }
        });


    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_particulars;
    }

}
