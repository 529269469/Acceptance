package com.example.acceptance.fragment.main.kitting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.acceptance.R;
import com.example.acceptance.activity.DeliveryActivity;
import com.example.acceptance.adapter.AcceptDeviceAdapter;
import com.example.acceptance.adapter.AddZuAdapter;
import com.example.acceptance.adapter.ProductSetAdapter;
import com.example.acceptance.adapter.kitting.AddZu2Adapter;
import com.example.acceptance.adapter.kitting.ProductAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.AcceptDeviceBean;
import com.example.acceptance.greendao.bean.CheckGroupBean;
import com.example.acceptance.greendao.bean.CheckItemBean;
import com.example.acceptance.greendao.bean.DeliveryListBean;
import com.example.acceptance.greendao.bean.DocumentBean;
import com.example.acceptance.greendao.bean.FileBean;
import com.example.acceptance.greendao.bean.PropertyBean;
import com.example.acceptance.greendao.bean.PropertyBeanX;
import com.example.acceptance.greendao.bean.RelatedDocumentIdSetBean;
import com.example.acceptance.greendao.db.AcceptDeviceBeanDao;
import com.example.acceptance.greendao.db.CheckGroupBeanDao;
import com.example.acceptance.greendao.db.CheckItemBeanDao;
import com.example.acceptance.greendao.db.DeliveryListBeanDao;
import com.example.acceptance.greendao.db.DocumentBeanDao;
import com.example.acceptance.greendao.db.FileBeanDao;
import com.example.acceptance.greendao.db.PropertyBeanDao;
import com.example.acceptance.greendao.db.PropertyBeanXDao;
import com.example.acceptance.greendao.db.RelatedDocumentIdSetBeanDao;
import com.example.acceptance.utils.DataUtils;
import com.example.acceptance.utils.FileUtils;
import com.example.acceptance.utils.SPUtils;
import com.example.acceptance.utils.StringUtils;
import com.example.acceptance.utils.ToastUtils;
import com.example.acceptance.view.AddPopupWindow;
import com.example.acceptance.view.AddPopupWindow2;
import com.example.acceptance.view.LinePathView;
import com.example.acceptance.view.MyListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

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
    @BindView(R.id.tv_signature)
    EditText tvSignature;
    private PopupWindow popupWindow;
    private ProductAdapter productAdapter;
    private String id;
    private String checkFileId;
    private int position;
    private String checkGroupId;
    private AcceptDeviceAdapter acceptDeviceAdapter;
    private ProductSetAdapter productSetAdapter;
    private String type;
    private String imgVideoId;
    private String imgVideoParentId;
    private AddPopupWindow2 addPopupWindow;

    private List<CheckItemBean> list = new ArrayList<>();
    private List<PropertyBean> propertyBeanArrayList = new ArrayList<>();
    private List<AcceptDeviceBean> acceptDeviceBeans = new ArrayList<>();

    private String groupName = "";
    private AddPopupWindow addPopupWindow1;

    @Override
    protected void initEventAndData() {
        tvDel.setFocusable(true);
        tvDel.setFocusableInTouchMode(true);
        tvDel.requestFocus();
        id = getArguments().getString("id");
        checkFileId = getArguments().getString("checkFileId");
        position = getArguments().getInt("position");
        type = getArguments().getString("type");

        DeliveryListBeanDao deliveryListBeanDao = MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
        List<DeliveryListBean> parentIdList = deliveryListBeanDao.queryBuilder()
                .where(DeliveryListBeanDao.Properties.DataPackageId.eq(id))
                .where(DeliveryListBeanDao.Properties.IsParent.eq("true"))
                .where(DeliveryListBeanDao.Properties.Project.eq("照片AND视频"))
                .list();
        List<DeliveryListBean> parentIdListSize = deliveryListBeanDao.queryBuilder()
                .where(DeliveryListBeanDao.Properties.DataPackageId.eq(id))
                .where(DeliveryListBeanDao.Properties.IsParent.eq("true"))
                .list();
        if (parentIdList != null && !parentIdList.isEmpty()) {
            List<DeliveryListBean> parentIdList2 = deliveryListBeanDao.queryBuilder()
                    .where(DeliveryListBeanDao.Properties.DataPackageId.eq(id))
                    .where(DeliveryListBeanDao.Properties.IsParent.eq("false"))
                    .where(DeliveryListBeanDao.Properties.Project.eq("照片AND视频"))
                    .where(DeliveryListBeanDao.Properties.ParentId.eq(parentIdList.get(0).getId()))
                    .list();
            if (parentIdList2 != null && !parentIdList2.isEmpty()) {
                imgVideoParentId = parentIdList2.get(0).getId();
            } else {
                imgVideoParentId = System.currentTimeMillis() + "";
                DeliveryListBean deliveryListBean2 = new DeliveryListBean(null,
                        id,
                        imgVideoParentId,
                        false + "",
                        "照片AND视频", parentIdList.get(0).getId(), UUID.randomUUID().toString(), "其他", "" + parentIdListSize.size(), "");
                deliveryListBeanDao.insert(deliveryListBean2);
            }
        } else {
            imgVideoId = System.currentTimeMillis() + "";
            imgVideoParentId = System.currentTimeMillis() + "21";
            DeliveryListBean deliveryListBean = new DeliveryListBean(null,
                    id,
                    imgVideoId,
                    true + "",
                    "照片AND视频", "", UUID.randomUUID().toString(), "", "" + parentIdListSize.size(), "");
            deliveryListBeanDao.insert(deliveryListBean);

            DeliveryListBean deliveryListBean2 = new DeliveryListBean(null,
                    id,
                    imgVideoParentId,
                    false + "",
                    "照片AND视频", imgVideoId, UUID.randomUUID().toString(), "其他", "" + parentIdListSize.size(), "");
            deliveryListBeanDao.insert(deliveryListBean2);
        }

        CheckGroupBeanDao checkGroupBeanDao = MyApplication.getInstances().getCheckGroupDaoSession().getCheckGroupBeanDao();
        List<CheckGroupBean> checkGroupBeans = checkGroupBeanDao.queryBuilder()
                .where(CheckGroupBeanDao.Properties.DataPackageId.eq(id))
                .where(CheckGroupBeanDao.Properties.CheckFileId.eq(checkFileId))
                .list();
        checkGroupId = checkGroupBeans.get(position).getId();
        groupName = checkGroupBeans.get(position).getGroupName() + "确认人签字";
        FileBeanDao fileBeanDao = MyApplication.getInstances().getFileDaoSession().getFileBeanDao();
        List<FileBean> fileBeanList = fileBeanDao.queryBuilder()
                .where(FileBeanDao.Properties.DataPackageId.eq(id))
                .where(FileBeanDao.Properties.DocumentId.eq(checkGroupId))
                .list();
        if (!fileBeanList.isEmpty()) {
            Glide.with(getActivity())
                    .load(new File(SPUtils.get(getActivity(), "path", "") + File.separator + fileBeanList.get(0).getPath()))
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(ivCheckPerson);
        }

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
        propertyBeanArrayList.clear();
        propertyBeanArrayList.addAll(propertyBeans);
        productSetAdapter = new ProductSetAdapter(getActivity(), propertyBeans);
        lvProductSet.setAdapter(productSetAdapter);


        if (checkGroupBeans.get(position).getIsConclusion().equals("true")) {
            llCheckGroupConclusion.setVisibility(View.VISIBLE);
            llCheckPerson.setVisibility(View.VISIBLE);
            tvSignature.setVisibility(View.VISIBLE);
        } else {
            tvSignature.setVisibility(View.GONE);
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
        tvSignature.setText(checkGroupBeans.get(position).getCheckPerson());
        productAdapter = new ProductAdapter(getActivity(), list);
        productAdapter.setRelevance(this);
        productAdapter.setGeidDel(this);
        lvProduct.setAdapter(productAdapter);

        etCheckGroupConclusion.addTextChangedListener(textWatcher);
        tvSignature.addTextChangedListener(textWatcher);
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
                CheckGroupBeanDao checkGroupBeanDao = MyApplication.getInstances().getCheckGroupDaoSession().getCheckGroupBeanDao();
                List<CheckGroupBean> checkGroupBeans = checkGroupBeanDao.queryBuilder()
                        .where(CheckGroupBeanDao.Properties.DataPackageId.eq(id))
                        .where(CheckGroupBeanDao.Properties.CheckFileId.eq(checkFileId))
                        .where(CheckGroupBeanDao.Properties.Id.eq(checkGroupId))
                        .list();
                CheckGroupBean checkGroupBean = new CheckGroupBean(
                        checkGroupBeans.get(0).getUId(),
                        checkGroupBeans.get(0).getDataPackageId(),
                        checkGroupBeans.get(0).getCheckFileId(),
                        checkGroupBeans.get(0).getId(),
                        checkGroupBeans.get(0).getGroupName(),
                        etCheckGroupConclusion.getText().toString().trim(),
                        tvSignature.getText().toString().trim(),
                        checkGroupBeans.get(0).getIsConclusion(),
                        checkGroupBeans.get(0).getIsTable(),
                        checkGroupBeans.get(0).getUniqueValue(),
                        DataUtils.getData(),
                        checkGroupBeans.get(0).getConclusionF(),
                        checkGroupBeans.get(0).getCheckPersonF(),
                        checkGroupBeans.get(0).getSort(),
                        checkGroupBeans.get(0).getCheckTimeF(),
                        checkGroupBeans.get(0).getTestTable());
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
                        "", UUID.randomUUID().toString(),list.size()+1+"","");
                checkItemBeanDao.insert(checkGroupBean);

                PropertyBeanXDao propertyBeanXDao = MyApplication.getInstances().getPropertyXDaoSession().getPropertyBeanXDao();
                for (int j = 0; j < propertyList.size(); j++) {
                    if (!StringUtils.isBlank(addZuAdapter.getList().get(j))) {
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
        TextView tv_file2 = poview.findViewById(R.id.tv_file2);

        tv_file2.setOnClickListener(view14 -> {
            popupWindow.dismiss();
            startActivityForResult(DeliveryActivity.openIntent(getActivity(), id), 100);

        });

        tv_file.setOnClickListener(view1 -> {
            popupWindow.dismiss();
            addPopupWindow = new AddPopupWindow2(getActivity(), tv_file, checkFileId, checkGroupId, list.get(pos).getId());
            addPopupWindow.setAddFile(new AddPopupWindow2.AddFile() {
                @Override
                public void addfile1() {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivityForResult(intent, 11);
                }

                @Override
                public void addfile2() {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivityForResult(intent, 22);
                }

                @Override
                public void addResult() {
                    CheckItemBeanDao checkItemBeanDao = MyApplication.getInstances().getCheckItemDaoSession().getCheckItemBeanDao();
                    List<CheckItemBean> checkItemBeans = checkItemBeanDao.queryBuilder()
                            .where(CheckItemBeanDao.Properties.DataPackageId.eq(id))
                            .where(CheckItemBeanDao.Properties.CheckFileId.eq(checkFileId))
                            .where(CheckItemBeanDao.Properties.CheckGroupId.eq(checkGroupId))
                            .list();
                    list.clear();
                    list.addAll(checkItemBeans);
                    productAdapter.notifyDataSetChanged();

                }
            });
        });

        tv_del.setOnClickListener(view12 -> {
            CheckItemBeanDao checkItemBeanDao = MyApplication.getInstances().getCheckItemDaoSession().getCheckItemBeanDao();

            RelatedDocumentIdSetBeanDao documentIdSetBeanDao = MyApplication.getInstances().getRelatedDocumentIdSetDaoSession().getRelatedDocumentIdSetBeanDao();
            List<RelatedDocumentIdSetBean> relatedDocumentIdSetBeanList = documentIdSetBeanDao.queryBuilder()
                    .where(RelatedDocumentIdSetBeanDao.Properties.DataPackageId.eq(id))
                    .where(RelatedDocumentIdSetBeanDao.Properties.CheckFileId.eq(checkFileId))
                    .where(RelatedDocumentIdSetBeanDao.Properties.CheckGroupId.eq(checkGroupId))
                    .where(RelatedDocumentIdSetBeanDao.Properties.CheckItemId.eq(list.get(pos).getId()))
                    .list();
            if (relatedDocumentIdSetBeanList != null && !relatedDocumentIdSetBeanList.isEmpty()) {
                for (int i = 0; i < relatedDocumentIdSetBeanList.size(); i++) {
                    documentIdSetBeanDao.deleteByKey(relatedDocumentIdSetBeanList.get(i).getUId());
                }
            }

            checkItemBeanDao.deleteByKey(list.get(pos).getUId());
            list.remove(pos);
            productAdapter.notifyDataSetChanged();
            popupWindow.dismiss();
        });

        tv_img.setOnClickListener(view13 -> {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraSavePath = new File(SPUtils.get(getActivity(), "path", "") + "/" + System.currentTimeMillis() + ".jpg");
            uri = Uri.fromFile(cameraSavePath);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, 2);
            popupWindow.dismiss();
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


    @Override
    public void setGeidDel(int pos, int pos1) {

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

    /**
     * 签名
     */
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
                    FileBeanDao fileBeanDao = MyApplication.getInstances().getFileDaoSession().getFileBeanDao();
                    List<FileBean> fileBeanList = fileBeanDao.queryBuilder()
                            .where(FileBeanDao.Properties.DataPackageId.eq(id))
                            .where(FileBeanDao.Properties.DocumentId.eq(checkGroupId))
                            .list();

                    if (fileBeanList != null && !fileBeanList.isEmpty()) {
                        FileUtils.delFile(SPUtils.get(getActivity(), "path", "") + File.separator + fileBeanList.get(0).getPath());
                        FileBean fileBean = new FileBean(fileBeanList.get(0).getUId(),
                                id,
                                checkGroupId,
                                groupName,
                                path,
                                "主内容",
                                "非密", "");
                        fileBeanDao.update(fileBean);
                    } else {
                        FileBean fileBean = new FileBean(null,
                                id,
                                checkGroupId,
                                groupName,
                                path,
                                "主内容",
                                "非密", "");
                        fileBeanDao.insert(fileBean);
                    }


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
    public void setaddResult(String pos, View view) {
        addPopupWindow1 = new AddPopupWindow(getActivity(), view, pos, true);
        addPopupWindow1.setAddFile(new AddPopupWindow.AddFile() {
            @Override
            public void addfile1() {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 11);
            }

            @Override
            public void addfile2() {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 22);
            }

            @Override
            public void addResult() {
                CheckItemBeanDao checkItemBeanDao = MyApplication.getInstances().getCheckItemDaoSession().getCheckItemBeanDao();
                List<CheckItemBean> checkItemBeans = checkItemBeanDao.queryBuilder()
                        .where(CheckItemBeanDao.Properties.DataPackageId.eq(id))
                        .where(CheckItemBeanDao.Properties.CheckFileId.eq(checkFileId))
                        .where(CheckItemBeanDao.Properties.CheckGroupId.eq(checkGroupId))
                        .list();
                list.clear();
                list.addAll(checkItemBeans);
                productAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (addPopupWindow != null) {
                addPopupWindow.setResult(data, requestCode);
            }

            if (addPopupWindow1 != null) {
                addPopupWindow1.setResult(data, requestCode);
            }

            if (requestCode == 2 || requestCode == 3) {
                String photoPath = String.valueOf(cameraSavePath);
                File file = new File(photoPath);
                String relatedDocumentId = System.currentTimeMillis() + "";

                RelatedDocumentIdSetBeanDao documentIdSetBeanDao = MyApplication.getInstances().getRelatedDocumentIdSetDaoSession().getRelatedDocumentIdSetBeanDao();
                RelatedDocumentIdSetBean relatedDocumentIdSetBean = new RelatedDocumentIdSetBean(null,
                        id,
                        checkFileId,
                        checkGroupId,
                        list.get(pos).getId(),
                        relatedDocumentId);
                documentIdSetBeanDao.insert(relatedDocumentIdSetBean);
                String codeStr = file.getName().substring(0, file.getName().length() - 4);
                DocumentBeanDao documentBeanDao = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
                DocumentBean documentBean = new DocumentBean(null,
                        id,
                        relatedDocumentId,
                        codeStr,
                        list.get(pos).getName(),
                        "非密",
                        imgVideoParentId,
                        "照片AND视频",
                        (String) SPUtils.get(getActivity(), "modelCode", ""),
                        (String) SPUtils.get(getActivity(), "productCode", ""),
                        codeStr,
                        "初始阶段",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "false",
                        "",
                        UUID.randomUUID().toString());
                documentBeanDao.insert(documentBean);


                FileBeanDao fileBeanDao = MyApplication.getInstances().getFileDaoSession().getFileBeanDao();
                FileBean fileBean = new FileBean(null,
                        id,
                        relatedDocumentId,
                        file.getName(),
                        file.getName(),
                        "主内容",
                        "非密", "");
                fileBeanDao.insert(fileBean);

                CheckItemBeanDao checkItemBeanDao2 = MyApplication.getInstances().getCheckItemDaoSession().getCheckItemBeanDao();
                List<CheckItemBean> checkItemBeans2 = checkItemBeanDao2.queryBuilder()
                        .where(CheckItemBeanDao.Properties.DataPackageId.eq(id))
                        .where(CheckItemBeanDao.Properties.CheckFileId.eq(checkFileId))
                        .where(CheckItemBeanDao.Properties.CheckGroupId.eq(checkGroupId))
                        .list();
                list.clear();
                list.addAll(checkItemBeans2);
                productAdapter.notifyDataSetChanged();
                Log.e("拍照返回图片路径:", photoPath);
            } else if (requestCode == 100) {
                String DocumentId = data.getStringExtra("DocumentId");

                RelatedDocumentIdSetBeanDao documentIdSetBeanDao = MyApplication.getInstances().getRelatedDocumentIdSetDaoSession().getRelatedDocumentIdSetBeanDao();
                RelatedDocumentIdSetBean relatedDocumentIdSetBean = new RelatedDocumentIdSetBean(null,
                        id,
                        checkFileId,
                        checkGroupId,
                        list.get(pos).getId(),
                        DocumentId);
                documentIdSetBeanDao.insert(relatedDocumentIdSetBean);

                CheckItemBeanDao checkItemBeanDao2 = MyApplication.getInstances().getCheckItemDaoSession().getCheckItemBeanDao();
                List<CheckItemBean> checkItemBeans2 = checkItemBeanDao2.queryBuilder()
                        .where(CheckItemBeanDao.Properties.DataPackageId.eq(id))
                        .where(CheckItemBeanDao.Properties.CheckFileId.eq(checkFileId))
                        .where(CheckItemBeanDao.Properties.CheckGroupId.eq(checkGroupId))
                        .list();
                list.clear();
                list.addAll(checkItemBeans2);
                productAdapter.notifyDataSetChanged();

            }
        }


    }


}
