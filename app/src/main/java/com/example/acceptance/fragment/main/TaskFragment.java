package com.example.acceptance.fragment.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.adapter.ApplyDeptAdapter;
import com.example.acceptance.adapter.ApplyForAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.ApplyDeptBean;
import com.example.acceptance.greendao.bean.ApplyItemBean;
import com.example.acceptance.greendao.bean.CheckTaskBean;
import com.example.acceptance.greendao.db.ApplyDeptBeanDao;
import com.example.acceptance.greendao.db.ApplyItemBeanDao;
import com.example.acceptance.greendao.db.CheckTaskBeanDao;
import com.example.acceptance.utils.StringUtils;
import com.example.acceptance.utils.ToastUtils;
import com.example.acceptance.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 验收任务单
 */
public class TaskFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.lv_list)
    MyListView lvList;
    @BindView(R.id.lv_list2)
    MyListView lvList2;
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
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.tv_add2)
    TextView tvAdd2;

    private List<ApplyItemBean> list = new ArrayList<>();
    private List<ApplyDeptBean> list2 = new ArrayList<>();
    private ApplyForAdapter applyForAdapter;
    private String id;
    private ApplyDeptAdapter applyDeptAdapter;

    @Override
    protected void initEventAndData() {
        id = getArguments().getString("id");

        CheckTaskBeanDao checkTaskBeanDao = MyApplication.getInstances().getCheckTaskDaoSession().getCheckTaskBeanDao();
        List<CheckTaskBean> checkTaskBeans = checkTaskBeanDao.queryBuilder()
                .where(CheckTaskBeanDao.Properties.DataPackageId.eq(id))
                .list();
        CheckTaskBean checkTaskBean = checkTaskBeans.get(0);
        tvCode.setText("编号：" + checkTaskBean.getCode());
        tvName.setText("名称：" + checkTaskBean.getName());
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

        tvSave.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
        tvAdd2.setOnClickListener(this);


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


        ApplyDeptBeanDao applyDeptBeanDao=MyApplication.getInstances().getApplyDeptDaoSession().getApplyDeptBeanDao();
        List<ApplyDeptBean> applyDeptBeans=applyDeptBeanDao.queryBuilder()
                .where(ApplyDeptBeanDao.Properties.DataPackageId.eq(id))
                .where(ApplyDeptBeanDao.Properties.CheckTaskId.eq(checkTaskBeans.get(0).getId()))
                .list();
        list2.addAll(applyDeptBeans);
        applyDeptAdapter = new ApplyDeptAdapter(getActivity(),list2);
        lvList2.setAdapter(applyDeptAdapter);

        lvList2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                addPopup2(true,i);
            }
        });

        lvList2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("是否删除本条数据");
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ApplyDeptBeanDao applyDeptBeanDao=MyApplication.getInstances().getApplyDeptDaoSession().getApplyDeptBeanDao();
                        applyDeptBeanDao.deleteByKey(list2.get(i).getUId());
                        list2.remove(i);
                        applyDeptAdapter.notifyDataSetChanged();
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
        return R.layout.fragment_task;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_save:
                CheckTaskBeanDao checkTaskBeanDao = MyApplication.getInstances().getCheckTaskDaoSession().getCheckTaskBeanDao();
                List<CheckTaskBean> checkTaskBeans = checkTaskBeanDao.queryBuilder()
                        .where(CheckTaskBeanDao.Properties.DataPackageId.eq(id))
                        .list();
                CheckTaskBean checkTaskBean=new CheckTaskBean(checkTaskBeans.get(0).getUId(),
                        checkTaskBeans.get(0).getDataPackageId(),
                        checkTaskBeans.get(0).getId(),
                        checkTaskBeans.get(0).getName(),
                        checkTaskBeans.get(0).getCode(),
                        tvIssuer.getText().toString().trim(),
                        tvIssueDept.getText().toString().trim(),
                        tvAccepter.getText().toString().trim(),
                        tvAcceptDate.getText().toString().trim(),
                        tvCheckDate.getText().toString().trim(),
                        tvApplicant.getText().toString().trim(),
                        tvApplyCompany.getText().toString().trim(),
                        tvPhone.getText().toString().trim(),
                        checkTaskBeans.get(0).getApplyDeptSet());
                checkTaskBeanDao.update(checkTaskBean);
                ToastUtils.getInstance().showTextToast(getActivity(),"保存成功");
                break;
            case R.id.tv_add:
                addPopup(false,0);
                break;
            case R.id.tv_add2:
                addPopup2(false,0);
                break;
        }
    }

    private void addPopup2(boolean isAdd, int position) {
        View view = getLayoutInflater().inflate(R.layout.popup_add2, null);
        PopupWindow popupWindow = new PopupWindow(view);
        popupWindow.setHeight(300);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;
        getActivity().getWindow().setAttributes(lp);
        popupWindow.showAtLocation(tvAdd2, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = getActivity().getWindow().getAttributes();
            lp1.alpha = 1f;
            getActivity().getWindow().setAttributes(lp1);
        });

        EditText tv_department=view.findViewById(R.id.tv_department);
        EditText tv_acceptor=view.findViewById(R.id.tv_acceptor);
        TextView tv_popup_save=view.findViewById(R.id.tv_popup_save);
        if (isAdd) {
            tv_department.setText(list2.get(position).getDepartment());
            tv_acceptor.setText(list2.get(position).getAcceptor());
        }

        tv_popup_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckTaskBeanDao checkTaskBeanDao = MyApplication.getInstances().getCheckTaskDaoSession().getCheckTaskBeanDao();
                List<CheckTaskBean> checkTaskBeans = checkTaskBeanDao.queryBuilder()
                        .where(CheckTaskBeanDao.Properties.DataPackageId.eq(id))
                        .list();

                ApplyDeptBeanDao applyDeptBeanDao=MyApplication.getInstances().getApplyDeptDaoSession().getApplyDeptBeanDao();
                if (isAdd) {
                    ApplyDeptBean applyItemBean = new ApplyDeptBean(list2.get(position).getUId(),
                            list2.get(position).getDataPackageId(),
                            list2.get(position).getCheckTaskId(),
                            list2.get(position).getId(),
                            tv_department.getText().toString().trim(),
                            tv_acceptor.getText().toString().trim() ,
                            list2.get(position).getOther());
                    applyDeptBeanDao.update(applyItemBean);
                } else {
                    ApplyDeptBean applyItemBean = new ApplyDeptBean(null,
                            id,
                            checkTaskBeans.get(0).getId(),
                            System.currentTimeMillis() + "",
                            tv_department.getText().toString().trim(),
                            tv_acceptor.getText().toString().trim() ,
                            "");
                    applyDeptBeanDao.insert(applyItemBean);
                }

                List<ApplyDeptBean> applyDeptBeans=applyDeptBeanDao.queryBuilder()
                        .where(ApplyDeptBeanDao.Properties.DataPackageId.eq(id))
                        .where(ApplyDeptBeanDao.Properties.CheckTaskId.eq(checkTaskBeans.get(0).getId()))
                        .list();
                list2.clear();
                list2.addAll(applyDeptBeans);
                applyDeptAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
                ToastUtils.getInstance().showTextToast(getActivity(),"保存成功");
            }
        });

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
        popupWindow.showAtLocation(tvAdd, Gravity.CENTER, 0, 0);
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
                tv_checkCount.setText(checkCoun[0]);
                tv_checkCount2.setText(checkCoun[1]);
                tv_checkCount3.setText(checkCoun[2]);
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
                            tv_productName.getText().toString().trim());
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
                            tv_productName.getText().toString().trim());
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
