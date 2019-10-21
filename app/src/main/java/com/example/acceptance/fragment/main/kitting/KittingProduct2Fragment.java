package com.example.acceptance.fragment.main.kitting;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.acceptance.R;
import com.example.acceptance.adapter.AcceptDeviceAdapter;
import com.example.acceptance.adapter.AddZuAdapter;
import com.example.acceptance.adapter.File2Adapter;
import com.example.acceptance.adapter.ProductSetAdapter;
import com.example.acceptance.adapter.kitting.AddZu2Adapter;
import com.example.acceptance.adapter.kitting.ProductAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.AcceptDeviceBean;
import com.example.acceptance.greendao.bean.CheckFileBean;
import com.example.acceptance.greendao.bean.CheckGroupBean;
import com.example.acceptance.greendao.bean.CheckItemBean;
import com.example.acceptance.greendao.bean.DataPackageDBean;
import com.example.acceptance.greendao.bean.DeliveryListBean;
import com.example.acceptance.greendao.bean.DocumentBean;
import com.example.acceptance.greendao.bean.FileBean;
import com.example.acceptance.greendao.bean.PropertyBean;
import com.example.acceptance.greendao.bean.PropertyBeanX;
import com.example.acceptance.greendao.bean.RelatedDocumentIdSetBean;
import com.example.acceptance.greendao.db.AcceptDeviceBeanDao;
import com.example.acceptance.greendao.db.CheckFileBeanDao;
import com.example.acceptance.greendao.db.CheckGroupBeanDao;
import com.example.acceptance.greendao.db.CheckItemBeanDao;
import com.example.acceptance.greendao.db.DataPackageDBeanDao;
import com.example.acceptance.greendao.db.DeliveryListBeanDao;
import com.example.acceptance.greendao.db.DocumentBeanDao;
import com.example.acceptance.greendao.db.FileBeanDao;
import com.example.acceptance.greendao.db.PropertyBeanDao;
import com.example.acceptance.greendao.db.PropertyBeanXDao;
import com.example.acceptance.greendao.db.RelatedDocumentIdSetBeanDao;
import com.example.acceptance.utils.FileUtils;
import com.example.acceptance.utils.OpenFileUtil;
import com.example.acceptance.utils.SPUtils;
import com.example.acceptance.utils.StringUtils;
import com.example.acceptance.utils.ToastUtils;
import com.example.acceptance.view.LinePathView;
import com.example.acceptance.view.MyListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 齐套性检查——产品齐套性检查
 */
public class KittingProduct2Fragment extends BaseFragment implements View.OnClickListener, ProductAdapter.Relevance, ProductAdapter.GeidDel {


    @BindView(R.id.lv_product)
    MyListView lvProduct;
    @BindView(R.id.lv_product_set)
    MyListView lvProductSet;
    @BindView(R.id.et_checkGroupConclusion)
    EditText etCheckGroupConclusion;
    @BindView(R.id.ll_checkGroupConclusion)
    LinearLayout llCheckGroupConclusion;
    @BindView(R.id.iv_checkPerson)
    ImageView ivCheckPerson;
    @BindView(R.id.ll_checkPerson)
    LinearLayout llCheckPerson;
    @BindView(R.id.tv_del)
    TextView tvDel;
    @BindView(R.id.tv_xiugai)
    TextView tvXiugai;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.ll_acceptDeviceSet)
    LinearLayout llAcceptDeviceSet;
    @BindView(R.id.lv_acceptDeviceSet)
    MyListView lvAcceptDeviceSet;
    @BindView(R.id.tv_add2)
    TextView tvAdd2;
    @BindView(R.id.tv_save)
    TextView tvSave;
    private PopupWindow popupWindow;

    private ProductAdapter productAdapter;
    private List<CheckItemBean> list = new ArrayList<>();
    private String id;
    private String checkFileId;
    private int position;
    private String checkGroupId;
    private List<AcceptDeviceBean> acceptDeviceBeans = new ArrayList<>();
    private AcceptDeviceAdapter acceptDeviceAdapter;
    private File2Adapter fileAdapter;
    private ProductSetAdapter productSetAdapter;
    private String type;
    private String parentId;

    @Override
    protected void initEventAndData() {
        id = getArguments().getString("id");
        checkFileId = getArguments().getString("checkFileId");
        position = getArguments().getInt("position");
        type= getArguments().getString("type");
        DeliveryListBeanDao deliveryListBeanDao = MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
        List<DeliveryListBean> parentIdList = deliveryListBeanDao.queryBuilder()
                .where(DeliveryListBeanDao.Properties.DataPackageId.eq(id))
                .where(DeliveryListBeanDao.Properties.Project.eq("验收依据文件"))
                .list();
        if (parentIdList!=null&&!parentIdList.isEmpty()){
            parentId = parentIdList.get(0).getId();
        }else {
            parentId=System.currentTimeMillis() + "";
            DeliveryListBean deliveryListBean=new DeliveryListBean(null,
                    id,
                    parentId,
                    true+"",
                    "验收依据文件","");
            deliveryListBeanDao.insert(deliveryListBean);
        }

        CheckGroupBeanDao checkGroupBeanDao = MyApplication.getInstances().getCheckGroupDaoSession().getCheckGroupBeanDao();
        List<CheckGroupBean> checkGroupBeans = checkGroupBeanDao.queryBuilder()
                .where(CheckGroupBeanDao.Properties.DataPackageId.eq(id))
                .where(CheckGroupBeanDao.Properties.CheckFileId.eq(checkFileId))
                .list();
        Glide.with(getActivity())
                .load(checkGroupBeans.get(position).getCheckPerson())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(ivCheckPerson);

        checkGroupId = checkGroupBeans.get(position).getId();
        CheckItemBeanDao checkItemBeanDao = MyApplication.getInstances().getCheckItemDaoSession().getCheckItemBeanDao();
        List<CheckItemBean> checkItemBeans = checkItemBeanDao.queryBuilder()
                .where(CheckItemBeanDao.Properties.DataPackageId.eq(id))
                .where(CheckItemBeanDao.Properties.CheckFileId.eq(checkFileId))
                .where(CheckItemBeanDao.Properties.CheckGroupId.eq(checkGroupId))
                .list();
        list.addAll(checkItemBeans);

        PropertyBeanDao propertyBeanDao = MyApplication.getInstances().getPropertyDaoSession().getPropertyBeanDao();
        List<PropertyBean> propertyBeans = propertyBeanDao.queryBuilder()
                .where(PropertyBeanDao.Properties.DataPackageId.eq(id))
                .where(PropertyBeanDao.Properties.CheckFileId.eq(checkFileId))
                .where(PropertyBeanDao.Properties.CheckGroupId.eq(checkGroupId))
                .list();

        productSetAdapter = new ProductSetAdapter(getActivity(), propertyBeans);
        lvProductSet.setAdapter(productSetAdapter);


        if (checkGroupBeans.get(position).getIsConclusion().equals("true")) {
            llCheckGroupConclusion.setVisibility(View.VISIBLE);
            llCheckPerson.setVisibility(View.VISIBLE);
        } else {
            llCheckGroupConclusion.setVisibility(View.GONE);
            llCheckPerson.setVisibility(View.GONE);
        }
        if (checkGroupBeans.get(position).getIsTable().equals("true")) {
            llAcceptDeviceSet.setVisibility(View.VISIBLE);
            lvAcceptDeviceSet.setVisibility(View.VISIBLE);
            tvAdd2.setVisibility(View.VISIBLE);
            AcceptDeviceBeanDao acceptDeviceBeanDao = MyApplication.getInstances().getAcceptDeviceaDaoSession().getAcceptDeviceBeanDao();
            List<AcceptDeviceBean> acceptDeviceBeanList = acceptDeviceBeanDao.queryBuilder()
                    .where(AcceptDeviceBeanDao.Properties.DataPackageId.eq(id))
                    .where(AcceptDeviceBeanDao.Properties.CheckFileId.eq(checkFileId))
                    .where(AcceptDeviceBeanDao.Properties.CheckGroupId.eq(checkGroupId))
                    .list();
            acceptDeviceBeans.clear();
            acceptDeviceBeans.addAll(acceptDeviceBeanList);
        } else {
            llAcceptDeviceSet.setVisibility(View.GONE);
            lvAcceptDeviceSet.setVisibility(View.GONE);
            tvAdd2.setVisibility(View.GONE);
        }

        acceptDeviceAdapter = new AcceptDeviceAdapter(getActivity(), acceptDeviceBeans);
        lvAcceptDeviceSet.setAdapter(acceptDeviceAdapter);

        lvAcceptDeviceSet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                addAcceptDevicea(false, i);
            }
        });
        lvAcceptDeviceSet.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("是否删除此专用仪表");
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AcceptDeviceBeanDao acceptDeviceBeanDao = MyApplication.getInstances().getAcceptDeviceaDaoSession().getAcceptDeviceBeanDao();
                        acceptDeviceBeanDao.deleteByKey(acceptDeviceBeans.get(i).getUId());
                        acceptDeviceBeans.remove(i);
                        acceptDeviceAdapter.notifyDataSetChanged();
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


        etCheckGroupConclusion.setText(checkGroupBeans.get(position).getCheckGroupConclusion());
        productAdapter = new ProductAdapter(getActivity(), list);
        productAdapter.setRelevance(this);
        productAdapter.setGeidDel(this);
        lvProduct.setAdapter(productAdapter);

        etCheckGroupConclusion.addTextChangedListener(textWatcher);

        ivCheckPerson.setOnClickListener(this);
        tvDel.setOnClickListener(this);
        tvXiugai.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
        tvAdd2.setOnClickListener(this);
        tvSave.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kitting_product2;
    }

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
                CheckGroupBeanDao checkGroupBeanDao = MyApplication.getInstances().getCheckGroupDaoSession().getCheckGroupBeanDao();
                List<CheckGroupBean> checkGroupBeans = checkGroupBeanDao.queryBuilder()
                        .where(CheckGroupBeanDao.Properties.DataPackageId.eq(id))
                        .where(CheckGroupBeanDao.Properties.CheckFileId.eq(checkFileId))
                        .where(CheckGroupBeanDao.Properties.Id.eq(checkGroupId))
                        .list();
                CheckGroupBean checkGroupBean=new CheckGroupBean(
                        checkGroupBeans.get(0).getUId(),
                        checkGroupBeans.get(0).getDataPackageId(),
                        checkGroupBeans.get(0).getCheckFileId(),
                        checkGroupBeans.get(0).getId(),
                        checkGroupBeans.get(0).getGroupName(),
                        etCheckGroupConclusion.getText().toString().trim(),
                        checkGroupBeans.get(0).getCheckPerson(),
                        checkGroupBeans.get(0).getIsConclusion(),
                        checkGroupBeans.get(0).getIsTable());
                checkGroupBeanDao.update(checkGroupBean);
            }

        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_checkPerson:
                pathPopu(ivCheckPerson);
                break;
            case R.id.tv_del:
                onDel.onDel(position);
                break;
            case R.id.tv_xiugai:
                onXiugai.onXiugai(position);
                break;
            case R.id.tv_add:
                addPopup();
                break;
            case R.id.tv_add2:
                addAcceptDevicea(true, 0);
                break;
            case R.id.tv_save:
                CheckGroupBeanDao checkGroupBeanDao = MyApplication.getInstances().getCheckGroupDaoSession().getCheckGroupBeanDao();
                List<CheckGroupBean> checkGroupBeans = checkGroupBeanDao.queryBuilder()
                        .where(CheckGroupBeanDao.Properties.DataPackageId.eq(id))
                        .where(CheckGroupBeanDao.Properties.CheckFileId.eq(checkFileId))
                        .where(CheckGroupBeanDao.Properties.Id.eq(checkGroupId))
                        .list();
                CheckGroupBean checkGroupBean=new CheckGroupBean(
                        checkGroupBeans.get(0).getUId(),
                        checkGroupBeans.get(0).getDataPackageId(),
                        checkGroupBeans.get(0).getCheckFileId(),
                        checkGroupBeans.get(0).getId(),
                        checkGroupBeans.get(0).getGroupName(),
                        etCheckGroupConclusion.getText().toString().trim(),
                        checkGroupBeans.get(0).getCheckPerson(),
                        checkGroupBeans.get(0).getIsConclusion(),
                        checkGroupBeans.get(0).getIsTable());
                checkGroupBeanDao.update(checkGroupBean);

                PropertyBeanDao propertyBeanDao = MyApplication.getInstances().getPropertyDaoSession().getPropertyBeanDao();
                List<PropertyBean> propertyBeans = propertyBeanDao.queryBuilder()
                        .where(PropertyBeanDao.Properties.DataPackageId.eq(id))
                        .where(PropertyBeanDao.Properties.CheckFileId.eq(checkFileId))
                        .where(PropertyBeanDao.Properties.CheckGroupId.eq(checkGroupId))
                        .list();
                for (int i = 0; i <propertyBeans.size() ; i++) {
                    PropertyBean propertyBean=new PropertyBean(
                            propertyBeans.get(i).getUId(),
                            propertyBeans.get(i).getDataPackageId(),
                            propertyBeans.get(i).getCheckFileId(),
                            propertyBeans.get(i).getCheckGroupId(),
                            propertyBeans.get(i).getName(),
                            productSetAdapter.getList().get(i).getValue());
                    propertyBeanDao.update(propertyBean);
                }


                ToastUtils.getInstance().showTextToast(getActivity(),"保存成功");
                break;
        }
    }


    private void addAcceptDevicea(boolean isAdd, int positions) {
        View poview = getLayoutInflater().inflate(R.layout.add_accept_devicea, null);
        PopupWindow popupWindow = new PopupWindow(poview);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(600);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;
        getActivity().getWindow().setAttributes(lp);
        popupWindow.showAtLocation(tvAdd, Gravity.TOP, 0, 0);

        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = getActivity().getWindow().getAttributes();
            lp1.alpha = 1f;
            getActivity().getWindow().setAttributes(lp1);
        });

        EditText tv_name = poview.findViewById(R.id.tv_name);
        EditText tv_specification = poview.findViewById(R.id.tv_specification);
        EditText tv_accuracy = poview.findViewById(R.id.tv_accuracy);
        EditText tv_certificate = poview.findViewById(R.id.tv_certificate);
        TextView tv_save = poview.findViewById(R.id.tv_save);

        if (!isAdd) {
            tv_name.setText(acceptDeviceBeans.get(positions).getName());
            tv_specification.setText(acceptDeviceBeans.get(positions).getSpecification());
            tv_accuracy.setText(acceptDeviceBeans.get(positions).getAccuracy());
            tv_certificate.setText(acceptDeviceBeans.get(positions).getCertificate());
        }

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AcceptDeviceBeanDao acceptDeviceBeanDao = MyApplication.getInstances().getAcceptDeviceaDaoSession().getAcceptDeviceBeanDao();
                if (isAdd) {
                    AcceptDeviceBean acceptDeviceBean = new AcceptDeviceBean(null,
                            id,
                            checkFileId,
                            checkGroupId,
                            System.currentTimeMillis() + "",
                            tv_name.getText().toString().trim(),
                            tv_specification.getText().toString().trim(),
                            tv_accuracy.getText().toString().trim(),
                            tv_certificate.getText().toString().trim(), "");
                    acceptDeviceBeanDao.insert(acceptDeviceBean);
                    acceptDeviceBeans.add(acceptDeviceBean);
                    acceptDeviceAdapter.notifyDataSetChanged();
                } else {
                    AcceptDeviceBean acceptDeviceBean = new AcceptDeviceBean(acceptDeviceBeans.get(positions).getUId(),
                            id,
                            checkFileId,
                            checkGroupId,
                            acceptDeviceBeans.get(positions).getId(),
                            tv_name.getText().toString().trim(),
                            tv_specification.getText().toString().trim(),
                            tv_accuracy.getText().toString().trim(),
                            tv_certificate.getText().toString().trim(),
                            acceptDeviceBeans.get(positions).getDescription());
                    acceptDeviceBeanDao.update(acceptDeviceBean);
                    AcceptDeviceBeanDao acceptDeviceBeanDao2 = MyApplication.getInstances().getAcceptDeviceaDaoSession().getAcceptDeviceBeanDao();
                    List<AcceptDeviceBean> acceptDeviceBeanList = acceptDeviceBeanDao2.queryBuilder()
                            .where(AcceptDeviceBeanDao.Properties.DataPackageId.eq(id))
                            .where(AcceptDeviceBeanDao.Properties.CheckFileId.eq(checkFileId))
                            .where(AcceptDeviceBeanDao.Properties.CheckGroupId.eq(checkGroupId))
                            .list();
                    acceptDeviceBeans.clear();
                    acceptDeviceBeans.addAll(acceptDeviceBeanList);
                    acceptDeviceAdapter.notifyDataSetChanged();
                }
                popupWindow.dismiss();
            }
        });


    }

    private void addPopup() {
        View poview = getLayoutInflater().inflate(R.layout.add_view3, null);
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

        EditText tv_name = poview.findViewById(R.id.tv_name);
        MyListView lv_options = poview.findViewById(R.id.lv_options);
        MyListView lv_Property = poview.findViewById(R.id.lv_Property);
        TextView tv_options = poview.findViewById(R.id.tv_options);
        TextView tv_Property = poview.findViewById(R.id.tv_Property);
        TextView tv_save = poview.findViewById(R.id.tv_save);

        List<String> optionsList = new ArrayList<>();
        optionsList.add("是");
        optionsList.add("否");
        AddZu2Adapter addZuAdapter = new AddZu2Adapter(getActivity(), optionsList);
        lv_options.setAdapter(addZuAdapter);
        addZuAdapter.setOnDel(new AddZu2Adapter.OnDel() {
            @Override
            public void onDel(int position) {
                optionsList.remove(position);
                addZuAdapter.notifyDataSetChanged();


            }
        });
        tv_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionsList.add("");
                addZuAdapter.notifyDataSetChanged();
            }
        });


        List<String> propertyList = new ArrayList<>();
        AddZuAdapter addZuAdapter2 = new AddZuAdapter(getActivity(), propertyList);
        lv_Property.setAdapter(addZuAdapter2);
        addZuAdapter2.setOnDel(new AddZuAdapter.OnDel() {
            @Override
            public void onDel(int position) {
                propertyList.remove(position);
                addZuAdapter2.notifyDataSetChanged();


            }
        });
        tv_Property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                propertyList.add("");
                addZuAdapter2.notifyDataSetChanged();
            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtils.isBlank(tv_name.getText().toString().trim())) {
                    ToastUtils.getInstance().showTextToast(getActivity(), "请输入检查项名称");
                    return;
                }
                String optionsString = "";
                StringBuffer optionsBuffer = new StringBuffer();
                for (int i = 0; i < optionsList.size(); i++) {
                    optionsBuffer.append(optionsList.get(i)).append(",");
                }
                if (!StringUtils.isBlank(optionsBuffer.toString())) {
                    optionsString = optionsBuffer.toString().substring(0, optionsBuffer.toString().length() - 1);
                }
                String CheckItemId = System.currentTimeMillis() + "";
                CheckItemBeanDao checkItemBeanDao = MyApplication.getInstances().getCheckItemDaoSession().getCheckItemBeanDao();
                CheckItemBean checkGroupBean = new CheckItemBean(null,
                        id,
                        checkFileId,
                        checkGroupId,
                        CheckItemId,
                        tv_name.getText().toString().trim(),
                        optionsString,
                        "", "");
                checkItemBeanDao.insert(checkGroupBean);

                PropertyBeanXDao propertyBeanXDao = MyApplication.getInstances().getPropertyXDaoSession().getPropertyBeanXDao();
                for (int j = 0; j < propertyList.size(); j++) {
                    if (!StringUtils.isBlank(addZuAdapter.getList().get(j))){
                        String name = addZuAdapter2.getList().get(j);
                        PropertyBeanX propertyBeanX = new PropertyBeanX(null,
                                id,
                                checkFileId,
                                checkGroupId,
                                CheckItemId,
                                name, "");
                        propertyBeanXDao.insert(propertyBeanX);
                    }
                }

                CheckItemBeanDao checkItemBeanDao2 = MyApplication.getInstances().getCheckItemDaoSession().getCheckItemBeanDao();
                List<CheckItemBean> checkItemBeans = checkItemBeanDao2.queryBuilder()
                        .where(CheckItemBeanDao.Properties.DataPackageId.eq(id))
                        .where(CheckItemBeanDao.Properties.CheckFileId.eq(checkFileId))
                        .where(CheckItemBeanDao.Properties.CheckGroupId.eq(checkGroupId))
                        .list();
                list.clear();
                list.addAll(checkItemBeans);
                productAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
                ToastUtils.getInstance().showTextToast(getActivity(), "保存成功");
            }
        });


    }

    @Override
    public void setRelevance(int pos, View view) {
        showListDialog(pos, view);
    }

    private File cameraSavePath;
    private Uri uri;
    private int pos = 0;

    private void showListDialog(int pos, View view) {
        this.pos = pos;
        View poview = LayoutInflater.from(getActivity()).inflate(R.layout.relevance, null);
        PopupWindow popupWindow = new PopupWindow(poview);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        WindowManager.LayoutParams lp = MyApplication.mContext.getWindow().getAttributes();
        lp.alpha = 0.7f;
        MyApplication.mContext.getWindow().setAttributes(lp);

        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = MyApplication.mContext.getWindow().getAttributes();
            lp1.alpha = 1f;
            MyApplication.mContext.getWindow().setAttributes(lp1);
        });
        popupWindow.showAsDropDown(view);

        TextView tv_file = poview.findViewById(R.id.tv_file);
        TextView tv_del = poview.findViewById(R.id.tv_del);
        TextView tv_img = poview.findViewById(R.id.tv_img);
        TextView tv_video = poview.findViewById(R.id.tv_video);

        tv_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                addPopup2(pos);
            }
        });

        tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckItemBeanDao checkItemBeanDao = MyApplication.getInstances().getCheckItemDaoSession().getCheckItemBeanDao();
                checkItemBeanDao.deleteByKey(list.get(pos).getUId());
                list.remove(pos);
                productAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
            }
        });

        tv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraSavePath = new File(SPUtils.get(getActivity(), "path", "") + "/" + System.currentTimeMillis() + ".jpg");
                uri = Uri.fromFile(cameraSavePath);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, 2);
                popupWindow.dismiss();
            }
        });

        tv_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                cameraSavePath = new File(SPUtils.get(getActivity(), "path", "") + "/" + System.currentTimeMillis() + ".mp4");
                uri = Uri.fromFile(cameraSavePath);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, 3);
                popupWindow.dismiss();
            }
        });

    }

    private List<FileBean> fileBeans = new ArrayList<>();
    boolean isAdd = false;

    private void addPopup2(int pos) {
        View view = getLayoutInflater().inflate(R.layout.popup_add3, null);
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

        EditText tv_code = view.findViewById(R.id.tv_code);
        EditText tv_name = view.findViewById(R.id.tv_name);
        EditText tv_payClassify = view.findViewById(R.id.tv_payClassify);
        EditText tv_secret = view.findViewById(R.id.tv_secret);
        EditText tv_techStatus = view.findViewById(R.id.tv_techStatus);
        EditText tv_approver = view.findViewById(R.id.tv_approver);
        EditText tv_approvalDate = view.findViewById(R.id.tv_approvalDate);
        Switch tv_issl = view.findViewById(R.id.tv_issl);
        EditText tv_conclusion = view.findViewById(R.id.tv_conclusion);
        TextView tv_file = view.findViewById(R.id.tv_file);
        EditText tv_description = view.findViewById(R.id.tv_description);
        TextView tv_popup_save = view.findViewById(R.id.tv_popup_save);
        MyListView lv_file = view.findViewById(R.id.lv_file);
        isAdd = false;
        RelatedDocumentIdSetBeanDao documentIdSetBeanDao = MyApplication.getInstances().getRelatedDocumentIdSetDaoSession().getRelatedDocumentIdSetBeanDao();
        List<RelatedDocumentIdSetBean> relatedDocumentIdSetBeanList = documentIdSetBeanDao.queryBuilder()
                .where(RelatedDocumentIdSetBeanDao.Properties.DataPackageId.eq(id))
                .where(RelatedDocumentIdSetBeanDao.Properties.CheckFileId.eq(checkFileId))
                .where(RelatedDocumentIdSetBeanDao.Properties.CheckGroupId.eq(checkGroupId))
                .where(RelatedDocumentIdSetBeanDao.Properties.CheckItemId.eq(list.get(pos).getId()))
                .list();
        fileBeans.clear();
        if (relatedDocumentIdSetBeanList != null && !relatedDocumentIdSetBeanList.isEmpty()) {
            DocumentBeanDao documentBeanDao = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
            List<DocumentBean> documentBeans = documentBeanDao.queryBuilder()
                    .where(DocumentBeanDao.Properties.DataPackageId.eq(id))
                    .where(DocumentBeanDao.Properties.Id.eq(relatedDocumentIdSetBeanList.get(0).getRelatedDocumentId()))
                    .list();

            if (documentBeans != null && !documentBeans.isEmpty()) {
                isAdd = true;
                DeliveryListBeanDao deliveryListBeanDao = MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
                List<DeliveryListBean> deliveryListBeanList = deliveryListBeanDao.queryBuilder()
                        .where(DeliveryListBeanDao.Properties.DataPackageId.eq(id))
                        .where(DeliveryListBeanDao.Properties.Id.eq(documentBeans.get(0).getPayClassify()))
                        .list();
                tv_payClassify.setText(deliveryListBeanList.get(0).getProject());

                tv_code.setText(documentBeans.get(0).getCode());
                tv_name.setText(documentBeans.get(0).getName());
                tv_secret.setText(documentBeans.get(0).getSecret());
                tv_techStatus.setText(documentBeans.get(0).getTechStatus());
                tv_approver.setText(documentBeans.get(0).getApprover());
                tv_approvalDate.setText(documentBeans.get(0).getApprovalDate());
                if (!StringUtils.isBlank(documentBeans.get(0).getIssl()) && documentBeans.get(0).getIssl().equals("true")) {
                    tv_issl.setChecked(true);
                } else {
                    tv_issl.setChecked(false);
                }
                tv_conclusion.setText(documentBeans.get(0).getConclusion());
                tv_description.setText(documentBeans.get(0).getDescription());

                FileBeanDao fileBeanDao = MyApplication.getInstances().getCheckFileDaoSession().getFileBeanDao();
                List<FileBean> fileBeanList = fileBeanDao.queryBuilder()
                        .where(FileBeanDao.Properties.DataPackageId.eq(id))
                        .where(FileBeanDao.Properties.DocumentId.eq(documentBeans.get(0).getId()))
                        .list();
                fileBeans.clear();
                fileBeans.addAll(fileBeanList);
            }
        }
        fileAdapter = new File2Adapter(getActivity(), fileBeans);
        lv_file.setAdapter(fileAdapter);
        fileAdapter.setOnDel(new File2Adapter.OnDel() {
            @Override
            public void onDel(int position) {
                RelatedDocumentIdSetBeanDao documentIdSetBeanDao = MyApplication.getInstances().getRelatedDocumentIdSetDaoSession().getRelatedDocumentIdSetBeanDao();
                List<RelatedDocumentIdSetBean> relatedDocumentIdSetBeanList = documentIdSetBeanDao.queryBuilder()
                        .where(RelatedDocumentIdSetBeanDao.Properties.DataPackageId.eq(id))
                        .where(RelatedDocumentIdSetBeanDao.Properties.CheckFileId.eq(checkFileId))
                        .where(RelatedDocumentIdSetBeanDao.Properties.CheckGroupId.eq(checkGroupId))
                        .where(RelatedDocumentIdSetBeanDao.Properties.CheckItemId.eq(list.get(pos).getId()))
                        .list();
                try {
                    DocumentBeanDao documentBeanDao = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
                    List<DocumentBean> documentBeans = documentBeanDao.queryBuilder()
                            .where(DocumentBeanDao.Properties.DataPackageId.eq(id))
                            .where(DocumentBeanDao.Properties.Id.eq(relatedDocumentIdSetBeanList.get(0).getRelatedDocumentId()))
                            .list();
                    FileBeanDao fileBeanDao = MyApplication.getInstances().getCheckFileDaoSession().getFileBeanDao();
                    List<FileBean> fileBeanList = fileBeanDao.queryBuilder()
                            .where(FileBeanDao.Properties.DataPackageId.eq(id))
                            .where(FileBeanDao.Properties.DocumentId.eq(documentBeans.get(0).getId()))
                            .list();
                    DataPackageDBeanDao dataPackageDBeanDao = MyApplication.getInstances().getDataPackageDaoSession().getDataPackageDBeanDao();
                    List<DataPackageDBean> dataPackageDBeans = dataPackageDBeanDao.queryBuilder()
                            .where(DataPackageDBeanDao.Properties.Id.eq(id))
                            .list();
                    for (int i = 0; i < fileBeanList.size(); i++) {
                        if (fileBeanList.get(i).getName().equals(fileBeans.get(position).getName())) {
                            FileUtils.delFile(dataPackageDBeans.get(0).getUpLoadFile() + "/" + fileBeanList.get(i).getPath());
                            fileBeanDao.deleteByKey(fileBeanList.get(i).getUId());
                            fileBeans.remove(position);
                            break;
                        } else {
                            break;
                        }
                    }
                }catch (Exception o){
                    fileBeans.remove(position);
                }
                fileAdapter.notifyDataSetChanged();
            }
        });

        lv_file.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DataPackageDBeanDao dataPackageDBeanDao = MyApplication.getInstances().getDataPackageDaoSession().getDataPackageDBeanDao();
                List<DataPackageDBean> dataPackageDBeans = dataPackageDBeanDao.queryBuilder()
                        .where(DataPackageDBeanDao.Properties.Id.eq(id))
                        .list();
                File file = new File(dataPackageDBeans.get(0).getUpLoadFile() + "/" + fileBeans.get(i).getPath());
                if (file.isFile() && file.exists()) {
                    try {
                        startActivity(OpenFileUtil.openFile(dataPackageDBeans.get(0).getUpLoadFile() + "/" + fileBeans.get(i).getPath()));
                    } catch (Exception o) {
                    }
                } else {
                    try {
                        startActivity(OpenFileUtil.openFile(fileBeans.get(i).getPath()));
                    } catch (Exception o) {
                    }
                }

            }
        });
        tv_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
            }
        });

        tv_popup_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeliveryListBeanDao deliveryListBeanDao = MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
                String deliveryListParentId = System.currentTimeMillis() + "";
                String documentId = System.currentTimeMillis() + "";

                if (!isAdd) {
                    RelatedDocumentIdSetBean relatedDocumentIdSetBean = new RelatedDocumentIdSetBean(null,
                            id,
                            checkFileId,
                            checkGroupId,
                            list.get(pos).getId(),
                            documentId);
                    documentIdSetBeanDao.insert(relatedDocumentIdSetBean);
                }


                if (isAdd) {
                    DocumentBeanDao documentBeanDao = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
                    List<DocumentBean> documentBeans = documentBeanDao.queryBuilder()
                            .where(DocumentBeanDao.Properties.DataPackageId.eq(list.get(position).getDataPackageId()))
                            .where(DocumentBeanDao.Properties.Id.eq(relatedDocumentIdSetBeanList.get(0).getRelatedDocumentId()))
                            .list();
                    DocumentBean documentBean = new DocumentBean(documentBeans.get(0).getUId(),
                            id,
                            documentBeans.get(0).getId(),
                            tv_code.getText().toString().trim(),
                            tv_name.getText().toString().trim(),
                            tv_secret.getText().toString().trim(),
                            documentBeans.get(0).getPayClassify(),
                            "",
                            "",
                            "",
                            "",
                            tv_techStatus.getText().toString().trim(),
                            tv_approver.getText().toString().trim(),
                            tv_approvalDate.getText().toString().trim(),
                            tv_issl.isChecked() + "",
                            tv_conclusion.getText().toString().trim(),
                            tv_description.getText().toString().trim());
                    documentBeanDao.update(documentBean);
                } else {
                    DocumentBeanDao documentBeanDao = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
                    DocumentBean documentBean = new DocumentBean(null,
                            id,
                            documentId,
                            tv_code.getText().toString().trim(),
                            tv_name.getText().toString().trim(),
                            tv_secret.getText().toString().trim(),
                            deliveryListParentId,
                            "",
                            "",
                            "",
                            "",
                            tv_techStatus.getText().toString().trim(),
                            tv_approver.getText().toString().trim(),
                            tv_approvalDate.getText().toString().trim(),
                            tv_issl.isChecked() + "",
                            tv_conclusion.getText().toString().trim(),
                            tv_description.getText().toString().trim());
                    documentBeanDao.insert(documentBean);
                }

                if (isAdd) {
                    DocumentBeanDao documentBeanDao = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
                    List<DocumentBean> documentBeans = documentBeanDao.queryBuilder()
                            .where(DocumentBeanDao.Properties.DataPackageId.eq(id))
                            .where(DocumentBeanDao.Properties.Id.eq(relatedDocumentIdSetBeanList.get(0).getRelatedDocumentId()))
                            .list();
                    List<DeliveryListBean> deliveryListBeanList = deliveryListBeanDao.queryBuilder()
                            .where(DeliveryListBeanDao.Properties.DataPackageId.eq(id))
                            .where(DeliveryListBeanDao.Properties.Id.eq(documentBeans.get(0).getPayClassify()))
                            .list();
                    DeliveryListBean deliveryListBean = new DeliveryListBean(
                            deliveryListBeanList.get(0).getUId(),
                            id,
                            deliveryListBeanList.get(0).getId(),
                            false + "",
                            tv_payClassify.getText().toString(),
                            deliveryListBeanList.get(0).getParentId());
                    deliveryListBeanDao.update(deliveryListBean);
                } else {
                    DeliveryListBean deliveryListBean = new DeliveryListBean(null,
                            id,
                            deliveryListParentId,
                            false + "",
                            tv_payClassify.getText().toString(),
                            parentId);
                    deliveryListBeanDao.insert(deliveryListBean);
                }


                if (isAdd) {
                    DocumentBeanDao documentBeanDao = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
                    List<DocumentBean> documentBeans = documentBeanDao.queryBuilder()
                            .where(DocumentBeanDao.Properties.DataPackageId.eq(list.get(position).getDataPackageId()))
                            .where(DocumentBeanDao.Properties.Id.eq(relatedDocumentIdSetBeanList.get(0).getRelatedDocumentId()))
                            .list();
                    FileBeanDao fileBeanDao = MyApplication.getInstances().getCheckFileDaoSession().getFileBeanDao();
                    List<FileBean> fileBeanList = fileBeanDao.queryBuilder()
                            .where(FileBeanDao.Properties.DataPackageId.eq(id))
                            .where(FileBeanDao.Properties.DocumentId.eq(documentBeans.get(0).getId()))
                            .list();
                    for (int i = 0; i < fileBeans.size(); i++) {
                        boolean isFile = false;
                        for (int j = 0; j < fileBeanList.size(); j++) {
                            if (fileBeans.get(i).getName().equals(fileBeanList.get(j).getName())) {
                                isFile = true;
                            }
                        }
                        if (!isFile) {
                            FileBean fileBean = new FileBean(null,
                                    id,
                                    documentBeans.get(0).getId(),
                                    fileBeans.get(i).getName(),
                                    fileBeans.get(i).getName(),
                                    "");
                            fileBeanDao.insert(fileBean);
                            DataPackageDBeanDao dataPackageDBeanDao = MyApplication.getInstances().getDataPackageDaoSession().getDataPackageDBeanDao();
                            List<DataPackageDBean> dataPackageDBeans = dataPackageDBeanDao.queryBuilder()
                                    .where(DataPackageDBeanDao.Properties.Id.eq(id))
                                    .list();
                            FileUtils.copyFile(fileBeans.get(i).getPath(), dataPackageDBeans.get(0).getUpLoadFile() + "/" + fileBeans.get(i).getName());
                        }

                    }
                } else {

                    FileBeanDao fileBeanDao = MyApplication.getInstances().getCheckFileDaoSession().getFileBeanDao();
                    List<FileBean> fileBeanList = fileBeanDao.queryBuilder()
                            .where(FileBeanDao.Properties.DataPackageId.eq(id))
                            .where(FileBeanDao.Properties.DocumentId.eq(documentId))
                            .list();
                    for (int i = 0; i < fileBeans.size(); i++) {
                        boolean isFile = false;
                        for (int j = 0; j < fileBeanList.size(); j++) {
                            if (fileBeans.get(i).getName().equals(fileBeanList.get(j).getName())) {
                                isFile = true;
                            }
                        }
                        if (!isFile) {
                            FileBean fileBean = new FileBean(null,
                                    id,
                                    documentId,
                                    fileBeans.get(i).getName(),
                                    fileBeans.get(i).getName(),
                                    "");
                            fileBeanDao.insert(fileBean);
                            DataPackageDBeanDao dataPackageDBeanDao = MyApplication.getInstances().getDataPackageDaoSession().getDataPackageDBeanDao();
                            List<DataPackageDBean> dataPackageDBeans = dataPackageDBeanDao.queryBuilder()
                                    .where(DataPackageDBeanDao.Properties.Id.eq(id))
                                    .list();
                            FileUtils.copyFile(fileBeans.get(i).getPath(), dataPackageDBeans.get(0).getUpLoadFile() + "/" + fileBeans.get(i).getName());
                        }

                    }
                }

                CheckItemBeanDao checkItemBeanDao = MyApplication.getInstances().getCheckItemDaoSession().getCheckItemBeanDao();
                List<CheckItemBean> checkItemBeans = checkItemBeanDao.queryBuilder()
                        .where(CheckItemBeanDao.Properties.DataPackageId.eq(id))
                        .where(CheckItemBeanDao.Properties.CheckFileId.eq(checkFileId))
                        .where(CheckItemBeanDao.Properties.CheckGroupId.eq(checkGroupId))
                        .list();
                list.clear();
                list.addAll(checkItemBeans);
                productAdapter.notifyDataSetChanged();

                popupWindow.dismiss();
                ToastUtils.getInstance().showTextToast(getActivity(), "保存成功");
            }
        });

    }

    @Override
    public void setGeidDel(int pos, int pos1) {
        List<String> gridList = new ArrayList<>();
        String[] imgs = list.get(pos1).getImgAndVideo().split(",");
        for (int i = 0; i < imgs.length; i++) {
            gridList.add(imgs[i]);
        }
        FileUtils.delFile(gridList.get(pos));
        gridList.remove(pos);
        String imgVideo = "";
        for (int j = 0; j < gridList.size(); j++) {
            if (j == 0) {
                imgVideo = gridList.get(j);
            } else {
                imgVideo = imgVideo + "," + gridList.get(j);
            }
        }
        CheckItemBeanDao checkItemBeanDao = MyApplication.getInstances().getCheckItemDaoSession().getCheckItemBeanDao();
        CheckItemBean checkItemBean = new CheckItemBean(list.get(0).getUId(),
                list.get(0).getDataPackageId(),
                list.get(0).getCheckFileId(),
                list.get(0).getCheckGroupId(),
                list.get(0).getId(),
                list.get(0).getName(),
                list.get(0).getOptions(),
                list.get(0).getSelected(),
                imgVideo);
        checkItemBeanDao.update(checkItemBean);
        list.get(pos1).setImgAndVideo(imgVideo);
        productAdapter.notifyDataSetChanged();

    }

    public interface OnDel {
        void onDel(int position);
    }

    private OnDel onDel;

    public void setOnDel(OnDel onDel) {
        this.onDel = onDel;
    }

    public interface OnXiugai {
        void onXiugai(int position);
    }

    private OnXiugai onXiugai;

    public void setOnXiugai(OnXiugai onXiugai) {
        this.onXiugai = onXiugai;
    }

    private LinePathView mPathView;

    private void pathPopu(ImageView iv) {
        View poview = getLayoutInflater().inflate(R.layout.path_view, null);
        popupWindow = new PopupWindow(poview);
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
                    CheckGroupBeanDao checkGroupBeanDao = MyApplication.getInstances().getCheckGroupDaoSession().getCheckGroupBeanDao();
                    List<CheckGroupBean> checkGroupBeans = checkGroupBeanDao.queryBuilder()
                            .where(CheckGroupBeanDao.Properties.DataPackageId.eq(id))
                            .where(CheckGroupBeanDao.Properties.CheckFileId.eq(checkFileId))
                            .where(CheckGroupBeanDao.Properties.Id.eq(checkGroupId))
                            .list();
                    CheckGroupBean checkGroupBean=new CheckGroupBean(
                            checkGroupBeans.get(0).getUId(),
                            checkGroupBeans.get(0).getDataPackageId(),
                            checkGroupBeans.get(0).getCheckFileId(),
                            checkGroupBeans.get(0).getId(),
                            checkGroupBeans.get(0).getGroupName(),
                            etCheckGroupConclusion.getText().toString().trim(),
                            SPUtils.get(getActivity(), "path", "") + File.separator + path,
                            checkGroupBeans.get(0).getIsConclusion(),
                            checkGroupBeans.get(0).getIsTable());
                    checkGroupBeanDao.update(checkGroupBean);
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

    private String photoPath;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == 1) {
                Uri uri = data.getData();
                if (uri != null) {
                    String path = getPath(getActivity(), uri);
                    if (path != null) {
                        File file = new File(path);
                        if (file.exists()) {
                            String upLoadFilePath = file.toString();
                            String upLoadFileName = file.getName();
                            Log.e("TAG", "upLoadFilePath: " + upLoadFilePath);
                            Log.e("TAG", "upLoadFileName: " + upLoadFileName);
                            fileBeans.add(new FileBean(null, "", "", upLoadFileName, upLoadFilePath, ""));
                            fileAdapter.notifyDataSetChanged();
                        }
                    }
                }
            } else if (requestCode == 2 || requestCode == 3) {
                photoPath = String.valueOf(cameraSavePath);
                String imgVideo = "";
                if (StringUtils.isBlank(list.get(pos).getImgAndVideo())) {
                    imgVideo = photoPath;
                    list.get(pos).setImgAndVideo(imgVideo);
                } else {
                    imgVideo = list.get(pos).getImgAndVideo() + "," + photoPath;
                    list.get(pos).setImgAndVideo(imgVideo);
                }
                CheckItemBeanDao checkItemBeanDao = MyApplication.getInstances().getCheckItemDaoSession().getCheckItemBeanDao();
                List<CheckItemBean> checkItemBeans = checkItemBeanDao.queryBuilder()
                        .where(CheckItemBeanDao.Properties.DataPackageId.eq(id))
                        .where(CheckItemBeanDao.Properties.CheckFileId.eq(checkFileId))
                        .where(CheckItemBeanDao.Properties.CheckGroupId.eq(checkGroupId))
                        .where(CheckItemBeanDao.Properties.Id.eq(list.get(pos).getId()))
                        .list();
                CheckItemBean checkItemBean = new CheckItemBean(checkItemBeans.get(0).getUId(),
                        checkItemBeans.get(0).getDataPackageId(),
                        checkItemBeans.get(0).getCheckFileId(),
                        checkItemBeans.get(0).getCheckGroupId(),
                        checkItemBeans.get(0).getId(),
                        checkItemBeans.get(0).getName(),
                        checkItemBeans.get(0).getOptions(),
                        checkItemBeans.get(0).getSelected(),
                        imgVideo);
                checkItemBeanDao.update(checkItemBean);
                FileUtils.copyFile(photoPath, SPUtils.get(getActivity(), "path", "") + "/" + photoPath);
                productAdapter.notifyDataSetChanged();
                Log.e("拍照返回图片路径:", photoPath);
            }
        }


    }

    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
//                Log.i(TAG,"isDownloadsDocument***"+uri.toString());
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
//                Log.i(TAG,"isMediaDocument***"+uri.toString());
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
//            Log.i(TAG,"content***"+uri.toString());
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            Log.i(TAG,"file***"+uri.toString());
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

}
