package com.example.acceptance.fragment.main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.adapter.File2Adapter;
import com.example.acceptance.adapter.FileAdapter;
import com.example.acceptance.adapter.LegacyAdapter;
import com.example.acceptance.adapter.UnresolvedAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.DataPackageDBean;
import com.example.acceptance.greendao.bean.FileBean;
import com.example.acceptance.greendao.bean.UnresolvedBean;
import com.example.acceptance.greendao.db.DataPackageDBeanDao;
import com.example.acceptance.greendao.db.FileBeanDao;
import com.example.acceptance.greendao.db.UnresolvedBeanDao;
import com.example.acceptance.utils.OpenFileUtil;
import com.example.acceptance.utils.StringUtils;
import com.example.acceptance.view.MyListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 验收遗留问题落实
 */
public class LegacyFragment extends BaseFragment {

    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.lv_list)
    MyListView lvList;
    private List<String> list = new ArrayList<>();
    private List<UnresolvedBean> beanList = new ArrayList<>();
    private LegacyAdapter legacyAdapter;
    private String id;

    @Override
    protected void initEventAndData() {
        id = getArguments().getString("id");

        UnresolvedBeanDao unresolvedBeanDao= MyApplication.getInstances().getCheckUnresolvedDaoSession().getUnresolvedBeanDao();
        List<UnresolvedBean> unresolvedBeans=unresolvedBeanDao.queryBuilder()
                .where(UnresolvedBeanDao.Properties.DataPackageId.eq(id))
                .list();
        beanList.addAll(unresolvedBeans);

        legacyAdapter = new LegacyAdapter(getActivity(), beanList);
        lvList.setAdapter(legacyAdapter);

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                addUnresolvedSet(false,i);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_legacy;

    }

    private FileAdapter fileAdapter;
    private List<FileBean> fileBeans = new ArrayList<>();
    private void addUnresolvedSet(boolean isAdd, int pos) {
        View view = getLayoutInflater().inflate(R.layout.popup_add5, null);
        PopupWindow popupWindow = new PopupWindow(view);
        popupWindow.setHeight(600);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;
        getActivity().getWindow().setAttributes(lp);
        popupWindow.showAtLocation(lvList, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = getActivity().getWindow().getAttributes();
            lp1.alpha = 1f;
            getActivity().getWindow().setAttributes(lp1);
        });
        EditText tv_productCode = view.findViewById(R.id.tv_productCode);
        EditText tv_question = view.findViewById(R.id.tv_question);
        EditText tv_confirmer = view.findViewById(R.id.tv_confirmer);
        EditText tv_confirmTime = view.findViewById(R.id.tv_confirmTime);
        TextView tv_file = view.findViewById(R.id.tv_file);
        MyListView lv_file = view.findViewById(R.id.lv_file);
        TextView tv_popup_save = view.findViewById(R.id.tv_popup_save);

        tv_popup_save.setVisibility(View.GONE);
        fileBeans.clear();
        if (!isAdd) {
            tv_productCode.setText(beanList.get(pos).getProductCode());
            tv_question.setText(beanList.get(pos).getQuestion());
            tv_confirmer.setText(beanList.get(pos).getConfirmer());
            tv_confirmTime.setText(beanList.get(pos).getConfirmTime());

            try {
                FileBeanDao fileBeanDao = MyApplication.getInstances().getCheckFileDaoSession().getFileBeanDao();
                List<FileBean> fileBeanList = fileBeanDao.queryBuilder()
                        .where(FileBeanDao.Properties.DataPackageId.eq(id))
                        .where(FileBeanDao.Properties.DocumentId.eq(beanList.get(pos).getFileId()))
                        .list();
                fileBeans.addAll(fileBeanList);
            }catch (Exception o){

            }

        }

        fileAdapter = new FileAdapter(getActivity(), fileBeans);
        lv_file.setAdapter(fileAdapter);
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
//        tv_file.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                startActivityForResult(intent, 1);
//            }
//        });


        tv_popup_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UnresolvedBeanDao unresolvedBeanDao = MyApplication.getInstances().getCheckUnresolvedDaoSession().getUnresolvedBeanDao();
                String unresolvedId = System.currentTimeMillis() + "";
                if (isAdd) {
                    FileBeanDao fileBeanDao = MyApplication.getInstances().getCheckFileDaoSession().getFileBeanDao();
                    for (int i = 0; i < fileBeans.size(); i++) {
                        FileBean fileBean = new FileBean(null,
                                id,
                                unresolvedId,
                                fileBeans.get(i).getName(),
                                fileBeans.get(i).getPath(),
                                fileBeans.get(i).getType(),
                                fileBeans.get(i).getSecret());
                        fileBeanDao.insert(fileBean);
                    }
                    UnresolvedBean unresolvedBean = new UnresolvedBean(null,
                            id,
                            unresolvedId,
                            tv_productCode.getText().toString().trim(),
                            tv_question.getText().toString().trim(),
                            tv_confirmer.getText().toString().trim(),
                            tv_confirmTime.getText().toString().trim(),
                            unresolvedId);
                    unresolvedBeanDao.insert(unresolvedBean);
                } else {
                    FileBeanDao fileBeanDao = MyApplication.getInstances().getCheckFileDaoSession().getFileBeanDao();
                    for (int i = 0; i < fileBeans.size(); i++) {
                        FileBean fileBean = new FileBean(null,
                                id,
                                StringUtils.isBlank(beanList.get(pos).getFileId())?unresolvedId:beanList.get(pos).getFileId(),
                                fileBeans.get(i).getName(),
                                fileBeans.get(i).getPath(),
                                fileBeans.get(i).getType(),
                                fileBeans.get(i).getSecret());
                        fileBeanDao.insert(fileBean);
                    }
                    UnresolvedBean unresolvedBean = new UnresolvedBean(beanList.get(pos).getUId(),
                            id,
                            beanList.get(pos).getId(),
                            tv_productCode.getText().toString().trim(),
                            tv_question.getText().toString().trim(),
                            tv_confirmer.getText().toString().trim(),
                            tv_confirmTime.getText().toString().trim(),
                            StringUtils.isBlank(beanList.get(pos).getFileId())?unresolvedId:beanList.get(pos).getFileId());
                    unresolvedBeanDao.update(unresolvedBean);
                }
                UnresolvedBeanDao unresolvedBeanDao2 = MyApplication.getInstances().getCheckUnresolvedDaoSession().getUnresolvedBeanDao();
                List<UnresolvedBean> unresolvedBeans = unresolvedBeanDao2.queryBuilder()
                        .where(UnresolvedBeanDao.Properties.DataPackageId.eq(id))
                        .list();
                beanList.clear();
                beanList.addAll(unresolvedBeans);
                legacyAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
            }
        });


    }
}
