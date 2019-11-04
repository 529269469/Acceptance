package com.example.acceptance.fragment.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.example.acceptance.R;
import com.example.acceptance.activity.MainActivity;
import com.example.acceptance.activity.ToActivity;
import com.example.acceptance.adapter.ApplyForAdapter;
import com.example.acceptance.adapter.GridAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.ApplyItemBean;
import com.example.acceptance.greendao.bean.CheckApplyBean;
import com.example.acceptance.greendao.bean.DataPackageDBean;
import com.example.acceptance.greendao.bean.FileBean;
import com.example.acceptance.greendao.db.ApplyItemBeanDao;
import com.example.acceptance.greendao.db.CheckApplyBeanDao;
import com.example.acceptance.greendao.db.DataPackageDBeanDao;
import com.example.acceptance.utils.FileUtils;
import com.example.acceptance.utils.SPUtils;
import com.example.acceptance.utils.StringUtils;
import com.example.acceptance.utils.ToastUtils;
import com.example.acceptance.view.MyGridView;
import com.example.acceptance.view.MyListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 验收申请
 */

public class ApplyForFragment extends BaseFragment implements View.OnClickListener {

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
    @BindView(R.id.tv_add)
    TextView tv_add;
    @BindView(R.id.tv_save)
    TextView tv_save;
    @BindView(R.id.gv_conclusion)
    MyGridView gv_conclusion;
    @BindView(R.id.tv_paizhao)
    TextView tv_paizhao;


    private List<ApplyItemBean> list = new ArrayList<>();
    private ApplyForAdapter applyForAdapter;
    private String id;
    private List<FileBean> gridList = new ArrayList<>();
    private File cameraSavePath;
    private String photoPath;
    private Uri uri;
    private GridAdapter gridAdapter;

    private TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String words= editable.toString();
            if (!StringUtils.isBlank(words)) {
                CheckApplyBeanDao checkApplyBeanDao = MyApplication.getInstances().getCheckApplyDaoSession().getCheckApplyBeanDao();
                List<CheckApplyBean> checkApplyBeans = checkApplyBeanDao.queryBuilder()
                        .where(CheckApplyBeanDao.Properties.DataPackageId.eq(id))
                        .list();
                StringBuffer gridStringBuffer=new StringBuffer();
                for (int i = 0; i < gridList.size(); i++) {
                    gridStringBuffer.append(gridList.get(i)).append(",");
                }
                if (!StringUtils.isBlank(gridStringBuffer.toString())){
                    gridString=gridStringBuffer.toString().substring(0,gridStringBuffer.toString().length()-1);
                }
                CheckApplyBean checkApplyBean = new CheckApplyBean(checkApplyBeans.get(0).getUId(),
                        checkApplyBeans.get(0).getDataPackageId(),
                        checkApplyBeans.get(0).getId(),
                        checkApplyBeans.get(0).getName(),
                        checkApplyBeans.get(0).getCode(),
                        checkApplyBeans.get(0).getContractCode(),
                        checkApplyBeans.get(0).getContractName(),
                        etApplicant.getText().toString().trim(),
                        etApplyCompany.getText().toString().trim(),
                        etPhone.getText().toString().trim(),
                        etConclusion.getText().toString().trim(),
                        etDescription.getText().toString().trim(),
                        checkApplyBeans.get(0).getDocTypeVal());
                checkApplyBeanDao.update(checkApplyBean);
            }

        }
    };

    @Override
    protected void onVisible() {
        super.onVisible();
        CheckApplyBeanDao checkApplyBeanDao = MyApplication.getInstances().getCheckApplyDaoSession().getCheckApplyBeanDao();
        List<CheckApplyBean> checkApplyBeans = checkApplyBeanDao.queryBuilder()
                .where(CheckApplyBeanDao.Properties.DataPackageId.eq(id))
                .list();

        CheckApplyBean checkApplyBean = checkApplyBeans.get(0);
        tvCode.setText("编号：" + checkApplyBean.getCode());
        tvName.setText("名称：" + checkApplyBean.getName());
        tvContractCode.setText("合同编号：" + checkApplyBean.getContractCode());
        tvContractName.setText("合同名称：" + checkApplyBean.getContractName());

        etApplyCompany.setText(checkApplyBean.getApplyCompany());
        etApplicant.setText(checkApplyBean.getApplicant());
        etPhone.setText(checkApplyBean.getPhone());
        etConclusion.setText(checkApplyBean.getConclusion());
        etDescription.setText(checkApplyBean.getDescription());

        ApplyItemBeanDao applyItemBeanDao = MyApplication.getInstances().getApplyItemDaoSession().getApplyItemBeanDao();
        List<ApplyItemBean> applyItemBeans = applyItemBeanDao.queryBuilder()
                .where(ApplyItemBeanDao.Properties.DataPackageId.eq(id))
                .list();
        list.clear();
        list.addAll(applyItemBeans);
        applyForAdapter.notifyDataSetChanged();

    }

    @Override
    protected void initEventAndData() {
        Bundle bundle = getArguments();
        id = bundle.getString("id");

        CheckApplyBeanDao checkApplyBeanDao = MyApplication.getInstances().getCheckApplyDaoSession().getCheckApplyBeanDao();
        List<CheckApplyBean> checkApplyBeans = checkApplyBeanDao.queryBuilder()
                .where(CheckApplyBeanDao.Properties.DataPackageId.eq(id))
                .list();

        CheckApplyBean checkApplyBean = checkApplyBeans.get(0);
        tvCode.setText("编号：" + checkApplyBean.getCode());
        tvName.setText("名称：" + checkApplyBean.getName());
        tvContractCode.setText("合同编号：" + checkApplyBean.getContractCode());
        tvContractName.setText("合同名称：" + checkApplyBean.getContractName());

        etApplyCompany.setText(checkApplyBean.getApplyCompany());
        etApplicant.setText(checkApplyBean.getApplicant());
        etPhone.setText(checkApplyBean.getPhone());
        etConclusion.setText(checkApplyBean.getConclusion());
        etDescription.setText(checkApplyBean.getDescription());


        gridAdapter = new GridAdapter(gridList, getActivity());
        gv_conclusion.setAdapter(gridAdapter);
        gridAdapter.setOnDel(new GridAdapter.OnDel() {
            @Override
            public void onDel(int position) {
//                FileUtils.delFile(gridList.get(position));
                gridList.remove(position);
                gridAdapter.notifyDataSetChanged();

                StringBuffer gridStringBuffer=new StringBuffer();
                for (int i = 0; i < gridList.size(); i++) {
                    gridStringBuffer.append(gridList.get(i)).append(",");
                }
                if (!StringUtils.isBlank(gridString.toString())){
                    gridString=gridString.toString().substring(0,gridString.toString().length()-1);
                }
                CheckApplyBean checkApplyBean = new CheckApplyBean(checkApplyBeans.get(0).getUId(),
                        checkApplyBeans.get(0).getDataPackageId(),
                        checkApplyBeans.get(0).getId(),
                        checkApplyBeans.get(0).getName(),
                        checkApplyBeans.get(0).getCode(),
                        checkApplyBeans.get(0).getContractCode(),
                        checkApplyBeans.get(0).getContractName(),
                        etApplicant.getText().toString().trim(),
                        etApplyCompany.getText().toString().trim(),
                        etPhone.getText().toString().trim(),
                        etConclusion.getText().toString().trim(),
                        etDescription.getText().toString().trim(),
                        checkApplyBeans.get(0).getDocTypeVal());
                checkApplyBeanDao.update(checkApplyBean);
            }
        });


        ApplyItemBeanDao applyItemBeanDao = MyApplication.getInstances().getApplyItemDaoSession().getApplyItemBeanDao();
        List<ApplyItemBean> applyItemBeans = applyItemBeanDao.queryBuilder()
                .where(ApplyItemBeanDao.Properties.DataPackageId.eq(id))
                .list();
        list.addAll(applyItemBeans);

        applyForAdapter = new ApplyForAdapter(getActivity(), list);
        lvList.setAdapter(applyForAdapter);

        etApplicant.addTextChangedListener(textWatcher);
        etApplyCompany.addTextChangedListener(textWatcher);
        etPhone.addTextChangedListener(textWatcher);
        etConclusion.addTextChangedListener(textWatcher);
        etDescription.addTextChangedListener(textWatcher);

        tv_add.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        tv_paizhao.setOnClickListener(this);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                addPopup(true, i);
            }
        });

        lvList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("是否删除本条数据");
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ApplyItemBeanDao applyItemBeanDao = MyApplication.getInstances().getApplyItemDaoSession().getApplyItemBeanDao();
                        applyItemBeanDao.deleteByKey(list.get(i).getUId());
                        list.remove(i);
                        applyForAdapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();

                return true;
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_apply_for;
    }




    private String gridString="";
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                addPopup(false, 0);
                break;
            case R.id.tv_save:
                CheckApplyBeanDao checkApplyBeanDao = MyApplication.getInstances().getCheckApplyDaoSession().getCheckApplyBeanDao();
                List<CheckApplyBean> checkApplyBeans = checkApplyBeanDao.queryBuilder()
                        .where(CheckApplyBeanDao.Properties.DataPackageId.eq(id))
                        .list();
                StringBuffer gridStringBuffer=new StringBuffer();
                for (int i = 0; i < gridList.size(); i++) {
                    gridStringBuffer.append(gridList.get(i)).append(",");
                }
                if (!StringUtils.isBlank(gridStringBuffer.toString())){
                    gridString=gridStringBuffer.toString().substring(0,gridStringBuffer.toString().length()-1);
                }
                CheckApplyBean checkApplyBean = new CheckApplyBean(checkApplyBeans.get(0).getUId(),
                        checkApplyBeans.get(0).getDataPackageId(),
                        checkApplyBeans.get(0).getId(),
                        checkApplyBeans.get(0).getName(),
                        checkApplyBeans.get(0).getCode(),
                        checkApplyBeans.get(0).getContractCode(),
                        checkApplyBeans.get(0).getContractName(),
                        etApplicant.getText().toString().trim(),
                        etApplyCompany.getText().toString().trim(),
                        etPhone.getText().toString().trim(),
                        etConclusion.getText().toString().trim(),
                        etDescription.getText().toString().trim(),
                        checkApplyBeans.get(0).getDocTypeVal());
                checkApplyBeanDao.update(checkApplyBean);
                ToastUtils.getInstance().showTextToast(getActivity(),"保存成功");
                break;
            case R.id.tv_paizhao:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraSavePath = new File(SPUtils.get(getActivity(), "path", "") + "/" + System.currentTimeMillis() + ".jpg");
                uri = Uri.fromFile(cameraSavePath);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1 && resultCode == getActivity().RESULT_OK) {
//            photoPath = String.valueOf(cameraSavePath);
//            gridList.add(photoPath);
//            gridAdapter.notifyDataSetChanged();
//            Log.e("拍照返回图片路径:", photoPath);
//
//            CheckApplyBeanDao checkApplyBeanDao = MyApplication.getInstances().getCheckApplyDaoSession().getCheckApplyBeanDao();
//            List<CheckApplyBean> checkApplyBeans = checkApplyBeanDao.queryBuilder()
//                    .where(CheckApplyBeanDao.Properties.DataPackageId.eq(id))
//                    .list();
//            StringBuffer gridStringBuffer=new StringBuffer();
//            for (int i = 0; i < gridList.size(); i++) {
//                gridStringBuffer.append(gridList.get(i)).append(",");
//            }
//            if (!StringUtils.isBlank(gridStringBuffer.toString())){
//                gridString=gridStringBuffer.toString().substring(0,gridStringBuffer.toString().length()-1);
//            }
//            CheckApplyBean checkApplyBean = new CheckApplyBean(checkApplyBeans.get(0).getUId(),
//                    checkApplyBeans.get(0).getDataPackageId(),
//                    checkApplyBeans.get(0).getId(),
//                    checkApplyBeans.get(0).getName(),
//                    checkApplyBeans.get(0).getCode(),
//                    checkApplyBeans.get(0).getContractCode(),
//                    checkApplyBeans.get(0).getContractName(),
//                    etApplicant.getText().toString().trim(),
//                    etApplyCompany.getText().toString().trim(),
//                    etPhone.getText().toString().trim(),
//                    etConclusion.getText().toString().trim(),
//                    etDescription.getText().toString().trim(),
//                    checkApplyBeans.get(0).getDocTypeVal());
//            checkApplyBeanDao.update(checkApplyBean);
//            ToastUtils.getInstance().showTextToast(getActivity(),"保存成功");
//        }
    }

    private void addPopup(boolean isAdd, int position) {
        View view = getLayoutInflater().inflate(R.layout.popup_add, null);
        PopupWindow popupWindow = new PopupWindow(view);
        popupWindow.setHeight(600);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;
        getActivity().getWindow().setAttributes(lp);
        popupWindow.showAtLocation(tv_add, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = getActivity().getWindow().getAttributes();
            lp1.alpha = 1f;
            getActivity().getWindow().setAttributes(lp1);
        });
        EditText tv_productCodeName = view.findViewById(R.id.tv_productCodeName);
        EditText tv_productName = view.findViewById(R.id.tv_productName);
        EditText tv_productCode = view.findViewById(R.id.tv_productCode);
        EditText tv_productStatus = view.findViewById(R.id.tv_productStatus);
        EditText tv_checkCount = view.findViewById(R.id.tv_checkCount);
        EditText tv_checkCount2 = view.findViewById(R.id.tv_checkCount2);
        EditText tv_checkCount3 = view.findViewById(R.id.tv_checkCount3);
        EditText tv_description = view.findViewById(R.id.tv_description);
        Switch tv_isPureCheck = view.findViewById(R.id.tv_isPureCheck);
        Switch tv_isArmyCheck = view.findViewById(R.id.tv_isArmyCheck);
        Switch tv_isCompleteChoice = view.findViewById(R.id.tv_isCompleteChoice);
        Switch tv_isCompleteRoutine = view.findViewById(R.id.tv_isCompleteRoutine);
        Switch tv_isSatisfyRequire = view.findViewById(R.id.tv_isSatisfyRequire);
        TextView tv_popup_save = view.findViewById(R.id.tv_popup_save);

        if (isAdd) {
            tv_productCodeName.setText(list.get(position).getProductCodeName());
            tv_productName.setText(list.get(position).getProductName());
            tv_productCode.setText(list.get(position).getProductCode());
            tv_productStatus.setText(list.get(position).getProductStatus());
            tv_description.setText(list.get(position).getDescription());
            if (!StringUtils.isBlank(list.get(position).getIsPureCheck())&&list.get(position).getIsPureCheck().equals("true")) {
                tv_isPureCheck.setChecked(true);
            } else {
                tv_isPureCheck.setChecked(false);
            }
            if (!StringUtils.isBlank(list.get(position).getIsArmyCheck())&&list.get(position).getIsArmyCheck().equals("true")) {
                tv_isArmyCheck.setChecked(true);
            } else {
                tv_isArmyCheck.setChecked(false);
            }
            if (!StringUtils.isBlank(list.get(position).getIsCompleteChoice())&&list.get(position).getIsCompleteChoice().equals("true")) {
                tv_isCompleteChoice.setChecked(true);
            } else {
                tv_isCompleteChoice.setChecked(false);
            }
            if (!StringUtils.isBlank(list.get(position).getIsCompleteRoutine())&&list.get(position).getIsCompleteRoutine().equals("true")) {
                tv_isCompleteRoutine.setChecked(true);
            } else {
                tv_isCompleteRoutine.setChecked(false);
            }

            if (!StringUtils.isBlank(list.get(position).getIsSatisfyRequire())&&list.get(position).getIsSatisfyRequire().equals("true")) {
                tv_isSatisfyRequire.setChecked(true);
            } else {
                tv_isSatisfyRequire.setChecked(false);
            }
            if (!StringUtils.isBlank(list.get(position).getCheckCount())){
                String[] checkCoun = list.get(position).getCheckCount().split("/");
                try {
                    tv_checkCount.setText(checkCoun[0]);
                    tv_checkCount2.setText(checkCoun[1]);
                    tv_checkCount3.setText(checkCoun[2]);
                }catch (Exception o){

                }
            }

        }

        tv_popup_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplyItemBeanDao applyItemBeanDao = MyApplication.getInstances().getApplyItemDaoSession().getApplyItemBeanDao();
                if (isAdd) {
                    ApplyItemBean applyItemBean = new ApplyItemBean(list.get(position).getUId(),
                            list.get(position).getDataPackageId(),
                            list.get(position).getId(),
                            tv_productCodeName.getText().toString().trim(),
                            tv_productCode.getText().toString().trim(),
                            tv_productStatus.getText().toString().trim(),
                            tv_checkCount.getText().toString().trim() + "/" + tv_checkCount2.getText().toString().trim() + "/" + tv_checkCount3.getText().toString().trim(),
                            tv_isPureCheck.isChecked() + "",
                            tv_isArmyCheck.isChecked() + "",
                            tv_isCompleteChoice.isChecked() + "",
                            tv_isCompleteRoutine.isChecked() + "",
                            tv_isSatisfyRequire.isChecked() + "",
                            tv_description.getText().toString().trim(),
                            tv_productName.getText().toString().trim(),list.get(position).getPassCheck());
                    applyItemBeanDao.update(applyItemBean);
                } else {
                    ApplyItemBean applyItemBean = new ApplyItemBean(null,
                            id,
                            System.currentTimeMillis() + "",
                            tv_productCodeName.getText().toString().trim(),
                            tv_productCode.getText().toString().trim(),
                            tv_productStatus.getText().toString().trim(),
                            tv_checkCount.getText().toString().trim() + "/" + tv_checkCount2.getText().toString().trim() + "/" + tv_checkCount3.getText().toString().trim(),
                            tv_isPureCheck.isChecked() + "",
                            tv_isArmyCheck.isChecked() + "",
                            tv_isCompleteChoice.isChecked() + "",
                            tv_isCompleteRoutine.isChecked() + "",
                            tv_isSatisfyRequire.isChecked() + "",
                            tv_description.getText().toString().trim(),
                            tv_productName.getText().toString().trim(),"");
                    applyItemBeanDao.insert(applyItemBean);
                }
                List<ApplyItemBean> applyItemBeans = applyItemBeanDao.queryBuilder()
                        .where(ApplyItemBeanDao.Properties.DataPackageId.eq(id))
                        .list();
                list.clear();
                list.addAll(applyItemBeans);
                applyForAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
                ToastUtils.getInstance().showTextToast(getActivity(),"保存成功");
            }
        });

    }
}
