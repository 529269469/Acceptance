package com.example.acceptance.fragment.main.technology.machinery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.acceptance.R;
import com.example.acceptance.activity.ChecklistActivity;
import com.example.acceptance.adapter.AddZuAdapter;
import com.example.acceptance.adapter.TbAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.fragment.main.kitting.KittingProduct2Fragment;
import com.example.acceptance.greendao.bean.CheckFileBean;
import com.example.acceptance.greendao.bean.CheckGroupBean;
import com.example.acceptance.greendao.bean.CheckItemBean;
import com.example.acceptance.greendao.bean.FileBean;
import com.example.acceptance.greendao.bean.PropertyBean;
import com.example.acceptance.greendao.db.CheckFileBeanDao;
import com.example.acceptance.greendao.db.CheckGroupBeanDao;
import com.example.acceptance.greendao.db.CheckItemBeanDao;
import com.example.acceptance.greendao.db.FileBeanDao;
import com.example.acceptance.greendao.db.PropertyBeanDao;
import com.example.acceptance.net.Contents;
import com.example.acceptance.utils.FileUtils;
import com.example.acceptance.utils.SPUtils;
import com.example.acceptance.utils.StringUtils;
import com.example.acceptance.utils.ToastUtils;
import com.example.acceptance.view.ContentViewPager;
import com.example.acceptance.view.LinePathView;
import com.example.acceptance.view.MyListView;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;

/**
 * 齐套性检查——产品齐套性检查
 */
public class TechnologySizeFragment extends BaseFragment implements View.OnClickListener, KittingProduct2Fragment.OnDel, KittingProduct2Fragment.OnXiugai {

    @BindView(R.id.tb)
    TabLayout tb;
    @BindView(R.id.vp)
    ContentViewPager vp;
    @BindView(R.id.et_conclusion)
    EditText etConclusion;
    @BindView(R.id.iv_checkPerson2)
    ImageView ivCheckPerson2;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_signature)
    EditText tvSignature;


    private String id;
    private List<String> listTitle = new ArrayList<>();
    private List<Fragment> list = new ArrayList<>();
    private String checkFileId;
    private List<CheckGroupBean> checkGroupBeans;
    private TbAdapter adapter;
    private KittingProduct2Fragment kittingProduct2Fragment;
    private String type;

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
                CheckFileBeanDao checkFileBeanDao = MyApplication.getInstances().getCheckFileDaoSession().getCheckFileBeanDao();
                List<CheckFileBean> checkFileBeans = checkFileBeanDao.queryBuilder()
                        .where(CheckFileBeanDao.Properties.DataPackageId.eq(id))
                        .where(CheckFileBeanDao.Properties.DocType.eq(Contents.技术类检查))
                        .list();
                CheckFileBean checkFileBean = new CheckFileBean(checkFileBeans.get(0).getUId(),
                        checkFileBeans.get(0).getDataPackageId(),
                        checkFileBeans.get(0).getId(),
                        checkFileBeans.get(0).getName(),
                        checkFileBeans.get(0).getCode(),
                        checkFileBeans.get(0).getDocType(),
                        checkFileBeans.get(0).getProductType(),
                        etConclusion.getText().toString().trim(),
                        tvSignature.getText().toString().trim(),
                        checkFileBeans.get(0).getCheckDate(),
                        checkFileBeans.get(0).getSortBy());
                checkFileBeanDao.update(checkFileBean);
            }

        }
    };

    @Override
    protected void initEventAndData() {
        id = getArguments().getString("id");
        type = getArguments().getString("type");
        tvSignature.addTextChangedListener(textWatcher);
        etConclusion.addTextChangedListener(textWatcher);
        addData();
        adapter = new TbAdapter(getChildFragmentManager(), listTitle, list);
        vp.setAdapter(adapter);
        tb.setTabMode(TabLayout.MODE_SCROLLABLE);
        tb.setupWithViewPager(vp);

        ivCheckPerson2.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        tvAdd.setOnClickListener(view ->
                addPopup());

    }
    private String checkFileName="技术类检查确认人签字";
    private void addData() {
        CheckFileBeanDao checkFileBeanDao = MyApplication.getInstances().getCheckFileDaoSession().getCheckFileBeanDao();
        List<CheckFileBean> checkFileBeans = checkFileBeanDao.queryBuilder()
                .where(CheckFileBeanDao.Properties.DataPackageId.eq(id))
                .where(CheckFileBeanDao.Properties.DocType.eq(Contents.技术类检查))
                .list();
        if (checkFileBeans != null && !checkFileBeans.isEmpty()) {
            checkFileId = checkFileBeans.get(0).getId();
            FileBeanDao fileBeanDao = MyApplication.getInstances().getFileDaoSession().getFileBeanDao();
            List<FileBean> fileBeanList = fileBeanDao.queryBuilder()
                    .where(FileBeanDao.Properties.DataPackageId.eq(id))
                    .where(FileBeanDao.Properties.DocumentId.eq(checkFileId))
                    .list();

            if (!fileBeanList.isEmpty()) {
                Glide.with(getActivity())
                        .load(new File(SPUtils.get(getActivity(), "path", "") + File.separator + fileBeanList.get(0).getPath()))
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(ivCheckPerson2);
            }


            CheckGroupBeanDao checkGroupBeanDao = MyApplication.getInstances().getCheckGroupDaoSession().getCheckGroupBeanDao();
            checkGroupBeans = checkGroupBeanDao.queryBuilder()
                    .where(CheckGroupBeanDao.Properties.DataPackageId.eq(id))
                    .where(CheckGroupBeanDao.Properties.CheckFileId.eq(checkFileId))
                    .list();
            listTitle.clear();
            list.clear();
            tvSignature.setText(checkFileBeans.get(0).getCheckPerson());
            etConclusion.setText(checkFileBeans.get(0).getConclusion());
            for (int i = 0; i < checkGroupBeans.size(); i++) {
                listTitle.add(checkGroupBeans.get(i).getGroupName());
                kittingProduct2Fragment = new KittingProduct2Fragment();
                kittingProduct2Fragment.setOnDel(this);
                kittingProduct2Fragment.setOnXiugai(this);
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("checkFileId", checkFileId);
                bundle.putInt("position", i);
                kittingProduct2Fragment.setArguments(bundle);
                list.add(kittingProduct2Fragment);
            }
        } else {
            checkFileId = System.currentTimeMillis() + "";
        }

    }

    private void addPopup() {
        View poview = getLayoutInflater().inflate(R.layout.add_view, null);
        PopupWindow popupWindow = new PopupWindow(poview);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(600);
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

        EditText tv_groupName = poview.findViewById(R.id.tv_groupName);
        MyListView lv_PropertySet = poview.findViewById(R.id.lv_PropertySet);
        Switch tv_isConclusion = poview.findViewById(R.id.tv_isConclusion);
        Switch tv_isTable = poview.findViewById(R.id.tv_isTable);
        TextView tv_PropertySet = poview.findViewById(R.id.tv_PropertySet);
        TextView tv_save = poview.findViewById(R.id.tv_save);


        List<String> propertyList = new ArrayList<>();
        AddZuAdapter addZuAdapter = new AddZuAdapter(getActivity(), propertyList);
        lv_PropertySet.setAdapter(addZuAdapter);
        addZuAdapter.setOnDel(new AddZuAdapter.OnDel() {
            @Override
            public void onDel(int position) {
                propertyList.remove(position);
                addZuAdapter.notifyDataSetChanged();


            }
        });
        tv_PropertySet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                propertyList.add("");
                addZuAdapter.notifyDataSetChanged();
            }
        });


        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (StringUtils.isBlank(tv_groupName.getText().toString().trim())) {
                    ToastUtils.getInstance().showTextToast(getActivity(), "请输入检查组名称");
                    return;
                }
                CheckGroupBeanDao checkGroupBeanDao = MyApplication.getInstances().getCheckGroupDaoSession().getCheckGroupBeanDao();
                String CheckGroupId = System.currentTimeMillis() + "";
                CheckGroupBean checkGroupBean = new CheckGroupBean(null,
                        id,
                        checkFileId,
                        CheckGroupId,
                        tv_groupName.getText().toString().trim(),
                        "", "",
                        tv_isConclusion.isChecked() + "",
                        tv_isTable.isChecked() + "", UUID.randomUUID().toString());
                checkGroupBeanDao.insert(checkGroupBean);

                PropertyBeanDao propertyBeanDao = MyApplication.getInstances().getPropertyDaoSession().getPropertyBeanDao();

                for (int j = 0; j < propertyList.size(); j++) {
                    if (!StringUtils.isBlank(addZuAdapter.getList().get(j))) {
                        PropertyBean propertyBean = new PropertyBean(null, id, checkFileId, CheckGroupId, addZuAdapter.getList().get(j), "");
                        propertyBeanDao.insert(propertyBean);
                    }
                }
                addData();
                adapter.notifyDataSetChanged();
                vp.setCurrentItem(list.size());
                popupWindow.dismiss();
                ToastUtils.getInstance().showTextToast(getActivity(), "保存成功");
            }
        });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kitting_product;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_checkPerson2:
                pathPopu(ivCheckPerson2);
                break;
            case R.id.tv_save:
                CheckFileBeanDao checkFileBeanDao = MyApplication.getInstances().getCheckFileDaoSession().getCheckFileBeanDao();
                List<CheckFileBean> checkFileBeans = checkFileBeanDao.queryBuilder()
                        .where(CheckFileBeanDao.Properties.DataPackageId.eq(id))
                        .where(CheckFileBeanDao.Properties.DocType.eq(Contents.技术类检查))
                        .list();
                CheckFileBean checkFileBean = new CheckFileBean(checkFileBeans.get(0).getUId(),
                        checkFileBeans.get(0).getDataPackageId(),
                        checkFileBeans.get(0).getId(),
                        checkFileBeans.get(0).getName(),
                        checkFileBeans.get(0).getCode(),
                        checkFileBeans.get(0).getDocType(),
                        checkFileBeans.get(0).getProductType(),
                        etConclusion.getText().toString().trim(),
                        checkFileBeans.get(0).getCheckPerson(),
                        checkFileBeans.get(0).getCheckDate(),
                        checkFileBeans.get(0).getSortBy());
                checkFileBeanDao.update(checkFileBean);
                ToastUtils.getInstance().showTextToast(getActivity(), "保存成功");
                break;
        }
    }

    private LinePathView mPathView;

    private void pathPopu(ImageView iv) {
        View poview = getLayoutInflater().inflate(R.layout.path_view, null);
        PopupWindow popupWindow = new PopupWindow(poview);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;
        getActivity().getWindow().setAttributes(lp);
        popupWindow.showAtLocation(iv, Gravity.TOP, 0, 80);

        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = getActivity().getWindow().getAttributes();
            lp1.alpha = 1f;
            getActivity().getWindow().setAttributes(lp1);
        });

        mPathView = poview.findViewById(R.id.path_view);
        TextView mBtnClear = poview.findViewById(R.id.m_btn_clear);
        TextView mBtnSave = poview.findViewById(R.id.m_btn_save);

        //修改背景、笔宽、颜色
        mPathView.setBackColor(Color.WHITE);
        mPathView.setPaintWidth(10);
        mPathView.setPenColor(Color.BLACK);
        //清除
        mBtnClear.setOnClickListener(v -> {
            mPathView.clear();
            mPathView.setBackColor(Color.WHITE);
            mPathView.setPaintWidth(10);
            mPathView.setPenColor(Color.BLACK);
        });
        //保存
        String path = System.currentTimeMillis() + ".png";
        mBtnSave.setOnClickListener(v -> {
            if (mPathView.getTouched()) {
                try {
                    mPathView.save(SPUtils.get(getActivity(), "path", "") + File.separator + path, true, 100);
                    Glide.with(getActivity())
                            .load(new File(SPUtils.get(getActivity(), "path", "") + File.separator + path))
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(iv);
                    Toast.makeText(getActivity(), "签名成功~", Toast.LENGTH_SHORT).show();
                    FileBeanDao fileBeanDao=MyApplication.getInstances().getFileDaoSession().getFileBeanDao();
                    List<FileBean> fileBeanList=fileBeanDao.queryBuilder()
                            .where(FileBeanDao.Properties.DataPackageId.eq(id))
                            .where(FileBeanDao.Properties.DocumentId.eq(checkFileId))
                            .list();
                    if (fileBeanList!=null&&!fileBeanList.isEmpty()){
                        FileUtils.delFile(SPUtils.get(getActivity(), "path", "") + File.separator + fileBeanList.get(0).getPath());
                        FileBean fileBean=new FileBean(fileBeanList.get(0).getUId(),
                                id,
                                checkFileId,
                                checkFileName,
                                path,
                                "主内容",
                                "非密","");
                        fileBeanDao.update(fileBean);
                    }else {
                        FileBean fileBean=new FileBean(null,
                                id,
                                checkFileId,
                                checkFileName,
                                path,
                                "主内容",
                                "非密","");
                        fileBeanDao.insert(fileBean);
                    }
                    popupWindow.dismiss();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getActivity(), "您没有签名~", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onDel(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("是否删除此检查组");
        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CheckGroupBeanDao checkGroupBeanDao = MyApplication.getInstances().getCheckGroupDaoSession().getCheckGroupBeanDao();
                List<CheckGroupBean> checkGroupBeanList = checkGroupBeanDao.queryBuilder()
                        .where(CheckGroupBeanDao.Properties.DataPackageId.eq(checkGroupBeans.get(position).getDataPackageId()))
                        .where(CheckGroupBeanDao.Properties.CheckFileId.eq(checkGroupBeans.get(position).getCheckFileId()))
                        .where(CheckGroupBeanDao.Properties.Id.eq(checkGroupBeans.get(position).getId()))
                        .list();

                CheckItemBeanDao checkItemBeanDao = MyApplication.getInstances().getCheckItemDaoSession().getCheckItemBeanDao();
                List<CheckItemBean> checkItemBeanList = checkItemBeanDao.queryBuilder()
                        .where(CheckItemBeanDao.Properties.DataPackageId.eq(checkGroupBeanList.get(0).getDataPackageId()))
                        .where(CheckItemBeanDao.Properties.CheckFileId.eq(checkGroupBeanList.get(0).getCheckFileId()))
                        .where(CheckItemBeanDao.Properties.CheckGroupId.eq(checkGroupBeanList.get(0).getId()))
                        .list();

                for (int i = 0; i < checkItemBeanList.size(); i++) {
                    checkItemBeanDao.deleteByKey(checkItemBeanList.get(i).getUId());
                }

                checkGroupBeanDao.deleteByKey(checkGroupBeans.get(position).getUId());

                getActivity().startActivity(ChecklistActivity.openIntent(getContext(), true, type,id));
                getActivity().finish();
//                list.remove(position);
//                listTitle.remove(position);
//                adapter.notifyDataSetChanged();
//                vp.setOffscreenPageLimit(0);
//                vp.setCurrentItem(0);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    @Override
    public void onXiugai(int position) {
        addPopup2(position);
    }

    private void addPopup2(int position) {
        View poview = getLayoutInflater().inflate(R.layout.add_view, null);
        PopupWindow popupWindow = new PopupWindow(poview);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(600);
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

        EditText tv_groupName = poview.findViewById(R.id.tv_groupName);
        MyListView lv_PropertySet = poview.findViewById(R.id.lv_PropertySet);
        Switch tv_isConclusion = poview.findViewById(R.id.tv_isConclusion);
        Switch tv_isTable = poview.findViewById(R.id.tv_isTable);
        TextView tv_PropertySet = poview.findViewById(R.id.tv_PropertySet);
        TextView tv_save = poview.findViewById(R.id.tv_save);


        tv_groupName.setText(checkGroupBeans.get(position).getGroupName());

        if (checkGroupBeans.get(position).getIsConclusion().equals("true")) {
            tv_isConclusion.setChecked(true);
        } else {
            tv_isConclusion.setChecked(false);
        }
        if (checkGroupBeans.get(position).getIsTable().equals("true")) {
            tv_isTable.setChecked(true);
        } else {
            tv_isTable.setChecked(false);
        }

        List<String> propertyList = new ArrayList<>();
        PropertyBeanDao propertyBeanDao = MyApplication.getInstances().getPropertyDaoSession().getPropertyBeanDao();
        List<PropertyBean> propertyBeans = propertyBeanDao.queryBuilder()
                .where(PropertyBeanDao.Properties.DataPackageId.eq(id))
                .where(PropertyBeanDao.Properties.CheckFileId.eq(checkFileId))
                .where(PropertyBeanDao.Properties.CheckGroupId.eq(checkGroupBeans.get(position).getId()))
                .list();
        for (int i = 0; i < propertyBeans.size(); i++) {
            propertyList.add(propertyBeans.get(i).getName());
        }
        AddZuAdapter addZuAdapter = new AddZuAdapter(getActivity(), propertyList);
        lv_PropertySet.setAdapter(addZuAdapter);
        addZuAdapter.setOnDel(new AddZuAdapter.OnDel() {
            @Override
            public void onDel(int position) {
                for (int i = 0; i < propertyBeans.size(); i++) {
                    if (propertyList.get(position).equals(propertyBeans.get(i).getName())) {
                        propertyBeanDao.deleteByKey(propertyBeans.get(i).getUId());
                        break;
                    }
                }
                propertyList.remove(position);
                addZuAdapter.notifyDataSetChanged();

            }
        });
        tv_PropertySet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                propertyList.add("");
                addZuAdapter.notifyDataSetChanged();
            }
        });


        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtils.isBlank(tv_groupName.getText().toString().trim())) {
                    ToastUtils.getInstance().showTextToast(getActivity(), "请输入检查组名称");
                    return;
                }
                CheckGroupBeanDao checkGroupBeanDao = MyApplication.getInstances().getCheckGroupDaoSession().getCheckGroupBeanDao();
                List<CheckGroupBean> checkGroupBeans2 = checkGroupBeanDao.queryBuilder()
                        .where(CheckGroupBeanDao.Properties.DataPackageId.eq(id))
                        .where(CheckGroupBeanDao.Properties.CheckFileId.eq(checkFileId))
                        .where(CheckGroupBeanDao.Properties.Id.eq(checkGroupBeans.get(position).getId()))
                        .list();
                CheckGroupBean checkGroupBean = new CheckGroupBean(checkGroupBeans2.get(0).getUId(),
                        id,
                        checkFileId,
                        checkGroupBeans2.get(0).getId(),
                        tv_groupName.getText().toString().trim(),
                        checkGroupBeans2.get(0).getCheckGroupConclusion(),
                        checkGroupBeans2.get(0).getCheckPerson(),
                        tv_isConclusion.isChecked() + "",
                        tv_isTable.isChecked() + "",
                        checkGroupBeans2.get(0).getUniqueValue());
                checkGroupBeanDao.update(checkGroupBean);

                PropertyBeanDao propertyBeanDao = MyApplication.getInstances().getPropertyDaoSession().getPropertyBeanDao();
                List<PropertyBean> propertyBeans = propertyBeanDao.queryBuilder()
                        .where(PropertyBeanDao.Properties.DataPackageId.eq(id))
                        .where(PropertyBeanDao.Properties.CheckFileId.eq(checkFileId))
                        .where(PropertyBeanDao.Properties.CheckGroupId.eq(checkGroupBeans.get(position).getId()))
                        .list();
                for (int j = 0; j < propertyList.size(); j++) {
                    boolean isAdddd = false;
                    int posiii = 0;
                    for (int i = 0; i < propertyBeans.size(); i++) {
                        if (propertyList.get(j).equals(propertyBeans.get(i).getName())) {
                            isAdddd = true;
                            posiii = i;
                        }
                    }
                    if (isAdddd) {
                        PropertyBean propertyBean = new PropertyBean(propertyBeans.get(posiii).getUId(),
                                id,
                                checkFileId,
                                checkGroupBeans2.get(0).getId(),
                                addZuAdapter.getList().get(j),
                                propertyBeans.get(posiii).getValue());
                        propertyBeanDao.update(propertyBean);
                    } else {
                        PropertyBean propertyBean = new PropertyBean(null,
                                id,
                                checkFileId,
                                checkGroupBeans2.get(0).getId(),
                                addZuAdapter.getList().get(j),
                                "");
                        propertyBeanDao.insert(propertyBean);
                    }
                }

                addData();
                adapter.notifyDataSetChanged();

                vp.setOffscreenPageLimit(0);
                popupWindow.dismiss();
                ToastUtils.getInstance().showTextToast(getActivity(), "保存成功");
            }
        });


    }
}
