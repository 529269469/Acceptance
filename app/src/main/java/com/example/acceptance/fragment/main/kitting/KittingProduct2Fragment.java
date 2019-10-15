package com.example.acceptance.fragment.main.kitting;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.acceptance.R;
import com.example.acceptance.adapter.AddZuAdapter;
import com.example.acceptance.adapter.ProductSetAdapter;
import com.example.acceptance.adapter.kitting.AddZu2Adapter;
import com.example.acceptance.adapter.kitting.ProductAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.CheckGroupBean;
import com.example.acceptance.greendao.bean.CheckItemBean;
import com.example.acceptance.greendao.bean.PropertyBean;
import com.example.acceptance.greendao.bean.PropertyBeanX;
import com.example.acceptance.greendao.db.CheckGroupBeanDao;
import com.example.acceptance.greendao.db.CheckItemBeanDao;
import com.example.acceptance.greendao.db.PropertyBeanDao;
import com.example.acceptance.greendao.db.PropertyBeanXDao;
import com.example.acceptance.net.URLS;
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
public class KittingProduct2Fragment extends BaseFragment implements View.OnClickListener {


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
    private PopupWindow popupWindow;

    private ProductAdapter productAdapter;
    private List<CheckItemBean> list = new ArrayList<>();
    private String id;
    private String checkFileId;
    private int position;
    private String checkGroupId;

    @Override
    protected void initEventAndData() {
        id = getArguments().getString("id");
        checkFileId = getArguments().getString("checkFileId");
        position = getArguments().getInt("position");

        CheckGroupBeanDao checkGroupBeanDao = MyApplication.getInstances().getCheckGroupDaoSession().getCheckGroupBeanDao();
        List<CheckGroupBean> checkGroupBeans = checkGroupBeanDao.queryBuilder()
                .where(CheckGroupBeanDao.Properties.DataPackageId.eq(id))
                .where(CheckGroupBeanDao.Properties.CheckFileId.eq(checkFileId))
                .list();

        CheckItemBeanDao checkItemBeanDao = MyApplication.getInstances().getCheckItemDaoSession().getCheckItemBeanDao();
        List<CheckItemBean> checkItemBeans = checkItemBeanDao.queryBuilder()
                .where(CheckItemBeanDao.Properties.DataPackageId.eq(id))
                .where(CheckItemBeanDao.Properties.CheckFileId.eq(checkFileId))
                .where(CheckItemBeanDao.Properties.CheckGroupId.eq(checkGroupBeans.get(position).getId()))
                .list();
        checkGroupId = checkGroupBeans.get(position).getId();
        list.addAll(checkItemBeans);

        PropertyBeanDao propertyBeanDao = MyApplication.getInstances().getPropertyDaoSession().getPropertyBeanDao();
        List<PropertyBean> propertyBeans = propertyBeanDao.queryBuilder()
                .where(PropertyBeanDao.Properties.DataPackageId.eq(id))
                .where(PropertyBeanDao.Properties.CheckFileId.eq(checkFileId))
                .where(PropertyBeanDao.Properties.CheckGroupId.eq(checkGroupBeans.get(position).getId()))
                .list();

        ProductSetAdapter productSetAdapter = new ProductSetAdapter(getActivity(), propertyBeans);
        lvProductSet.setAdapter(productSetAdapter);


        if (checkGroupBeans.get(position).getIsConclusion().equals("true")) {
            llCheckGroupConclusion.setVisibility(View.VISIBLE);
            llCheckPerson.setVisibility(View.VISIBLE);
        } else {
            llCheckGroupConclusion.setVisibility(View.GONE);
            llCheckPerson.setVisibility(View.GONE);
        }

        etCheckGroupConclusion.setText(checkGroupBeans.get(position).getCheckGroupConclusion());
        productAdapter = new ProductAdapter(getActivity(), list);
        lvProduct.setAdapter(productAdapter);

        ivCheckPerson.setOnClickListener(this);
        tvDel.setOnClickListener(this);
        tvXiugai.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kitting_product2;
    }


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
        }
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
                if (StringUtils.isBlank(tv_name.getText().toString().trim())){
                    ToastUtils.getInstance().showTextToast(getActivity(), "请输入检查项名称");
                    return;
                }
                String optionsString="";
                StringBuffer optionsBuffer=new StringBuffer();
                for (int i = 0; i <optionsList.size() ; i++) {
                    optionsBuffer.append(optionsList.get(i)).append(",");
                }
                if (!StringUtils.isBlank(optionsBuffer.toString())){
                    optionsString=optionsBuffer.toString().substring(0,optionsBuffer.toString().length()-1);
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
                        "");
                checkItemBeanDao.insert(checkGroupBean);

                PropertyBeanXDao propertyBeanXDao = MyApplication.getInstances().getPropertyXDaoSession().getPropertyBeanXDao();
                for (int j = 0; j < propertyList.size(); j++) {
                    String name=addZuAdapter2.getList().get(j);
                    PropertyBeanX propertyBeanX = new PropertyBeanX(null,
                            id,
                            checkFileId,
                            checkGroupId,
                            CheckItemId,
                            name, "");
                    propertyBeanXDao.insert(propertyBeanX);
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
}
