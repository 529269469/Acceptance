package com.example.acceptance.fragment.main.technology.machinery;

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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.acceptance.R;
import com.example.acceptance.adapter.kitting.ProductAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.CheckFileBean;
import com.example.acceptance.greendao.bean.CheckGroupBean;
import com.example.acceptance.greendao.bean.CheckItemBean;
import com.example.acceptance.greendao.db.CheckFileBeanDao;
import com.example.acceptance.greendao.db.CheckGroupBeanDao;
import com.example.acceptance.greendao.db.CheckItemBeanDao;
import com.example.acceptance.net.URLS;
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
public class TechnologySizeFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_name)
//    TextView tvName;
//    @BindView(R.id.tv_groupName)
    TextView tvGroupName;
    @BindView(R.id.lv_product)
    MyListView lvProduct;
    @BindView(R.id.et_checkGroupConclusion)
    EditText etCheckGroupConclusion;
    @BindView(R.id.ll_checkGroupConclusion)
    LinearLayout llCheckGroupConclusion;
    @BindView(R.id.iv_checkPerson)
    ImageView ivCheckPerson;
    @BindView(R.id.ll_checkPerson)
    LinearLayout llCheckPerson;
    @BindView(R.id.et_conclusion)
    EditText etConclusion;
    @BindView(R.id.iv_checkPerson2)
    ImageView ivCheckPerson2;
    private PopupWindow popupWindow;

    private ProductAdapter productAdapter;
    private List<CheckItemBean> list = new ArrayList<>();

    @Override
    protected void initEventAndData() {
        String id = getArguments().getString("id");

        CheckFileBeanDao checkFileBeanDao = MyApplication.getInstances().getCheckFileDaoSession().getCheckFileBeanDao();
        List<CheckFileBean> checkFileBeans = checkFileBeanDao.queryBuilder()
                .where(CheckFileBeanDao.Properties.DataPackageId.eq(id))
                .where(CheckFileBeanDao.Properties.DocType.eq("技术类检查"))
                .list();

        CheckGroupBeanDao checkGroupBeanDao = MyApplication.getInstances().getCheckGroupDaoSession().getCheckGroupBeanDao();
        List<CheckGroupBean> checkGroupBeans = checkGroupBeanDao.queryBuilder()
                .where(CheckGroupBeanDao.Properties.DataPackageId.eq(id))
                .where(CheckGroupBeanDao.Properties.CheckFileId.eq(checkFileBeans.get(0).getId()))
                .list();

        CheckItemBeanDao checkItemBeanDao = MyApplication.getInstances().getCheckItemDaoSession().getCheckItemBeanDao();
        List<CheckItemBean> checkItemBeans = checkItemBeanDao.queryBuilder()
                .where(CheckItemBeanDao.Properties.DataPackageId.eq(id))
                .where(CheckItemBeanDao.Properties.CheckFileId.eq(checkFileBeans.get(0).getId()))
                .where(CheckItemBeanDao.Properties.CheckGroupId.eq(checkGroupBeans.get(0).getId()))
                .list();
        list.addAll(checkItemBeans);
        tvCode.setText("编号：" + checkFileBeans.get(0).getCode());
//        tvName.setText("名称：" + checkFileBeans.get(0).getName());
        tvGroupName.setText(checkGroupBeans.get(0).getGroupName());
        etCheckGroupConclusion.setText(checkGroupBeans.get(0).getCheckGroupConclusion());
        etConclusion.setText(checkFileBeans.get(0).getCheckPerson());

        if (checkGroupBeans.get(0).getIsConclusion().equals("true")) {
            llCheckGroupConclusion.setVisibility(View.VISIBLE);
            llCheckPerson.setVisibility(View.VISIBLE);
        } else {
            llCheckGroupConclusion.setVisibility(View.GONE);
            llCheckPerson.setVisibility(View.GONE);
        }


        productAdapter = new ProductAdapter(getActivity(), list);
        lvProduct.setAdapter(productAdapter);

        ivCheckPerson.setOnClickListener(this);
        ivCheckPerson2.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kitting_product;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_checkPerson2:
                pathPopu(ivCheckPerson);
                break;
            case R.id.iv_checkPerson:
                pathPopu(ivCheckPerson2);
                break;
        }
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
