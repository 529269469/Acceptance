package com.example.acceptance.fragment.main.kitting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.acceptance.R;
import com.example.acceptance.activity.ChecklistActivity;
import com.example.acceptance.adapter.AddZuAdapter;
import com.example.acceptance.adapter.HorAdapter;
import com.example.acceptance.adapter.Tb2Adapter;
import com.example.acceptance.adapter.TbAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.bean.TitleBean;
import com.example.acceptance.greendao.bean.CheckFileBean;
import com.example.acceptance.greendao.bean.CheckGroupBean;
import com.example.acceptance.greendao.bean.CheckItemBean;
import com.example.acceptance.greendao.bean.PropertyBean;
import com.example.acceptance.greendao.db.ApplyItemBeanDao;
import com.example.acceptance.greendao.db.CheckFileBeanDao;
import com.example.acceptance.greendao.db.CheckGroupBeanDao;
import com.example.acceptance.greendao.db.CheckItemBeanDao;
import com.example.acceptance.greendao.db.PropertyBeanDao;
import com.example.acceptance.net.URLS;
import com.example.acceptance.utils.StringUtils;
import com.example.acceptance.utils.ToastUtils;
import com.example.acceptance.view.ContentViewPager;
import com.example.acceptance.view.HorizontalListView;
import com.example.acceptance.view.LinePathView;
import com.example.acceptance.view.MyListView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 齐套性检查——产品齐套性检查
 */
public class KittingProductFragment extends BaseFragment implements View.OnClickListener, KittingProduct2Fragment.OnDel, KittingProduct2Fragment.OnXiugai {

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


    private String id;
    private List<String> listTitle = new ArrayList<>();
    private List<Fragment> list = new ArrayList<>();
    private String checkFileId;
    private List<CheckGroupBean> checkGroupBeans;
    private TbAdapter adapter;
    private KittingProduct2Fragment kittingProduct2Fragment;

    @Override
    protected void initEventAndData() {
        id = getArguments().getString("id");


        addData();
        adapter = new TbAdapter(getChildFragmentManager(),listTitle,list);
        vp.setAdapter(adapter);
        tb.setTabMode(TabLayout.MODE_FIXED);
        tb.setupWithViewPager(vp);

        ivCheckPerson2.setOnClickListener(this);
        tvAdd.setOnClickListener(view ->
                addPopup());

    }

    private void addData() {
        CheckFileBeanDao checkFileBeanDao = MyApplication.getInstances().getCheckFileDaoSession().getCheckFileBeanDao();
        List<CheckFileBean> checkFileBeans = checkFileBeanDao.queryBuilder()
                .where(CheckFileBeanDao.Properties.DataPackageId.eq(id))
                .where(CheckFileBeanDao.Properties.DocType.eq("齐套性检查"))
                .list();
        checkFileId = checkFileBeans.get(0).getId();
        CheckGroupBeanDao checkGroupBeanDao = MyApplication.getInstances().getCheckGroupDaoSession().getCheckGroupBeanDao();
        checkGroupBeans = checkGroupBeanDao.queryBuilder()
                .where(CheckGroupBeanDao.Properties.DataPackageId.eq(id))
                .where(CheckGroupBeanDao.Properties.CheckFileId.eq(checkFileId))
                .list();
        listTitle.clear();
        list.clear();
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

                if (StringUtils.isBlank(tv_groupName.getText().toString().trim())){
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
                        tv_isTable.isChecked() + "");
                checkGroupBeanDao.insert(checkGroupBean);

                PropertyBeanDao propertyBeanDao = MyApplication.getInstances().getPropertyDaoSession().getPropertyBeanDao();

                for (int j = 0; j < propertyList.size(); j++) {
                    PropertyBean propertyBean = new PropertyBean(null, id, checkFileId, CheckGroupId, addZuAdapter.getList().get(j), "");
                    propertyBeanDao.insert(propertyBean);
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
        Button mBtnClear = poview.findViewById(R.id.m_btn_clear);
        Button mBtnSave = poview.findViewById(R.id.m_btn_save);

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
                    mPathView.save(URLS.SINGA + File.separator + path, true, 100);
                    Glide.with(getActivity())
                            .load(new File(URLS.SINGA + File.separator + path))
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(iv);
                    Toast.makeText(getActivity(), "签名成功~", Toast.LENGTH_SHORT).show();
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
                List<CheckGroupBean> checkGroupBeanList=checkGroupBeanDao.queryBuilder()
                        .where(CheckGroupBeanDao.Properties.DataPackageId.eq(checkGroupBeans.get(position).getDataPackageId()))
                        .where(CheckGroupBeanDao.Properties.CheckFileId.eq(checkGroupBeans.get(position).getCheckFileId()))
                        .where(CheckGroupBeanDao.Properties.Id.eq(checkGroupBeans.get(position).getId()))
                        .list();

                CheckItemBeanDao checkItemBeanDao=MyApplication.getInstances().getCheckItemDaoSession().getCheckItemBeanDao();
                List<CheckItemBean> checkItemBeanList=checkItemBeanDao.queryBuilder()
                        .where(CheckItemBeanDao.Properties.DataPackageId.eq(checkGroupBeanList.get(0).getDataPackageId()))
                        .where(CheckItemBeanDao.Properties.CheckFileId.eq(checkGroupBeanList.get(0).getCheckFileId()))
                        .where(CheckItemBeanDao.Properties.CheckGroupId.eq(checkGroupBeanList.get(0).getId()))
                        .list();

                for (int i = 0; i <checkItemBeanList.size() ; i++) {
                    checkItemBeanDao.deleteByKey(checkItemBeanList.get(i).getUId());
                }

                checkGroupBeanDao.deleteByKey(checkGroupBeans.get(position).getUId());

                getActivity().startActivity(ChecklistActivity.openIntent(getContext(),true));
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

        if (checkGroupBeans.get(position).getIsConclusion().equals("true")){
            tv_isConclusion.setChecked(true);
        }else {
            tv_isConclusion.setChecked(false);
        }
        if (checkGroupBeans.get(position).getIsTable().equals("true")){
            tv_isTable.setChecked(true);
        }else {
            tv_isTable.setChecked(false);
        }

        List<String> propertyList = new ArrayList<>();
        PropertyBeanDao propertyBeanDao = MyApplication.getInstances().getPropertyDaoSession().getPropertyBeanDao();
        List<PropertyBean> propertyBeans=propertyBeanDao.queryBuilder()
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
                    if (propertyList.get(position).equals(propertyBeans.get(i).getName())){
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
                if (StringUtils.isBlank(tv_groupName.getText().toString().trim())){
                    ToastUtils.getInstance().showTextToast(getActivity(), "请输入检查组名称");
                    return;
                }
                CheckGroupBeanDao checkGroupBeanDao = MyApplication.getInstances().getCheckGroupDaoSession().getCheckGroupBeanDao();
                List<CheckGroupBean> checkGroupBeans2=checkGroupBeanDao.queryBuilder()
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
                        tv_isTable.isChecked() + "");
                checkGroupBeanDao.update(checkGroupBean);

                PropertyBeanDao propertyBeanDao = MyApplication.getInstances().getPropertyDaoSession().getPropertyBeanDao();
                List<PropertyBean> propertyBeans=propertyBeanDao.queryBuilder()
                        .where(PropertyBeanDao.Properties.DataPackageId.eq(id))
                        .where(PropertyBeanDao.Properties.CheckFileId.eq(checkFileId))
                        .where(PropertyBeanDao.Properties.CheckGroupId.eq(checkGroupBeans.get(position).getId()))
                        .list();
                for (int j = 0; j < propertyList.size(); j++) {
                    boolean isAdddd=false;
                    int posiii=0;
                    for (int i = 0; i < propertyBeans.size(); i++) {
                        if (propertyList.get(j).equals(propertyBeans.get(i).getName())){
                            isAdddd=true;
                            posiii=i;
                        }
                    }
                    if (isAdddd){
                        PropertyBean propertyBean = new PropertyBean(propertyBeans.get(posiii).getUId(),
                                id,
                                checkFileId,
                                checkGroupBeans2.get(0).getId(),
                                addZuAdapter.getList().get(j),
                                propertyBeans.get(posiii).getValue());
                        propertyBeanDao.update(propertyBean);
                    }else {
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
