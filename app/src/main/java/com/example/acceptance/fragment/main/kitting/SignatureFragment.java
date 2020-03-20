package com.example.acceptance.fragment.main.kitting;

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
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.acceptance.R;
import com.example.acceptance.activity.ChecklistActivity;
import com.example.acceptance.adapter.AddZuAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.base.MyApplication;
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
import com.example.acceptance.utils.DataUtils;
import com.example.acceptance.utils.FileUtils;
import com.example.acceptance.utils.SPUtils;
import com.example.acceptance.utils.StringUtils;
import com.example.acceptance.utils.ToastUtils;
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
 * 过程检查——电气产品——元器件，原材料，标准件检查
 */

public class SignatureFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.et_conclusion)
    EditText etConclusion;
    @BindView(R.id.iv_checkPerson2)
    ImageView ivCheckPerson2;
    @BindView(R.id.tv_signature)
    EditText tvSignature;


    private String id;
    private List<String> listTitle = new ArrayList<>();
    private List<Fragment> list = new ArrayList<>();
    private String checkFileId;
    private List<CheckGroupBean> checkGroupBeans;

    private String type;

    @Override
    protected void initEventAndData() {
        id = getArguments().getString("id");
        type = getArguments().getString("type");

        addData();
        tvSignature.addTextChangedListener(textWatcher);
        etConclusion.addTextChangedListener(textWatcher);
        ivCheckPerson2.setOnClickListener(this);


    }

    private String docType = "";

    private void addData() {
        CheckFileBeanDao checkFileBeanDao = MyApplication.getInstances().getCheckFileDaoSession().getCheckFileBeanDao();

        switch (type) {
            case "2":
                docType = Contents.齐套性检查;
                break;
            case "3":
                docType = Contents.过程检查;
                break;
            case "4":
                docType = Contents.技术类检查;
                break;
        }
        List<CheckFileBean> checkFileBeans = checkFileBeanDao.queryBuilder()
                .where(CheckFileBeanDao.Properties.DataPackageId.eq(id))
                .where(CheckFileBeanDao.Properties.DocType.eq(docType))
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

        } else {
            checkFileId = System.currentTimeMillis() + "";
        }

    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_signature;
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
                CheckFileBeanDao checkFileBeanDao = MyApplication.getInstances().getCheckFileDaoSession().getCheckFileBeanDao();
                List<CheckFileBean> checkFileBeans = checkFileBeanDao.queryBuilder()
                        .where(CheckFileBeanDao.Properties.DataPackageId.eq(id))
                        .where(CheckFileBeanDao.Properties.DocType.eq(docType))
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
                        checkFileBeans.get(0).getSortBy(),
                        checkFileBeans.get(0).getCheckTime(),
                        checkFileBeans.get(0).getSort(),
                        checkFileBeans.get(0).getTabsName(),
                        checkFileBeans.get(0).getAccordFile(),
                        checkFileBeans.get(0).getSelectEdit(),
                        checkFileBeans.get(0).getUniqueValue(),
                        checkFileBeans.get(0).getProductTypeValue(),
                        checkFileBeans.get(0).getDescription());
                checkFileBeanDao.update(checkFileBean);
            }

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_checkPerson2:
                pathPopu(ivCheckPerson2);
                break;
        }
    }

    private LinePathView mPathView;
    private String checkFileName = "过程检查确认人签字";

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
                    FileBeanDao fileBeanDao = MyApplication.getInstances().getFileDaoSession().getFileBeanDao();
                    List<FileBean> fileBeanList = fileBeanDao.queryBuilder()
                            .where(FileBeanDao.Properties.DataPackageId.eq(id))
                            .where(FileBeanDao.Properties.DocumentId.eq(checkFileId))
                            .list();
                    if (fileBeanList != null && !fileBeanList.isEmpty()) {
                        FileUtils.delFile(SPUtils.get(getActivity(), "path", "") + File.separator + fileBeanList.get(0).getPath());
                        FileBean fileBean = new FileBean(fileBeanList.get(0).getUId(),
                                id,
                                checkFileId,
                                checkFileName,
                                path,
                                "主内容",
                                "非密", "");
                        fileBeanDao.update(fileBean);
                    } else {
                        FileBean fileBean = new FileBean(null,
                                id,
                                checkFileId,
                                checkFileName,
                                path,
                                "主内容",
                                "非密", "");
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

}
