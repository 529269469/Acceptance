package com.example.acceptance.fragment.main.technology;

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

import androidx.annotation.Nullable;

import com.example.acceptance.R;
import com.example.acceptance.adapter.File2Adapter;
import com.example.acceptance.adapter.kitting.LvFileAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.DataPackageDBean;
import com.example.acceptance.greendao.bean.DeliveryListBean;
import com.example.acceptance.greendao.bean.DocumentBean;
import com.example.acceptance.greendao.bean.FileBean;
import com.example.acceptance.greendao.db.DataPackageDBeanDao;
import com.example.acceptance.greendao.db.DeliveryListBeanDao;
import com.example.acceptance.greendao.db.DocumentBeanDao;
import com.example.acceptance.greendao.db.FileBeanDao;
import com.example.acceptance.utils.FileUtils;
import com.example.acceptance.utils.OpenFileUtil;
import com.example.acceptance.utils.StringUtils;
import com.example.acceptance.utils.ToastUtils;
import com.example.acceptance.view.AddFilePopupWindow;
import com.example.acceptance.view.MyListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 技术类检查——依据文件检查
 */
public class TechnologyFileFragment extends BaseFragment {
    @BindView(R.id.lv_file_kitting)
    MyListView lvFileKitting;
    @BindView(R.id.lv_file_kitting2)
    MyListView lvFileKitting2;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.tv_add2)
    TextView tvAdd2;

    private List<DocumentBean> list = new ArrayList<>();
    private List<DocumentBean> list2 = new ArrayList<>();
    private List<FileBean> fileBeans = new ArrayList<>();
    private File2Adapter fileAdapter;
    private String id;
    private LvFileAdapter lvFileAdapter;
    private LvFileAdapter lvFileAdapter2;
    private String parentId;
    private AddFilePopupWindow addFilePopupWindow;

    @Override
    protected void initEventAndData() {
        id = getArguments().getString("id");
        tvAdd.setText("管理文件");
        tvAdd2.setText("添加技术文件");

        DocumentBeanDao documentBeanDao = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
        List<DocumentBean> documentBeans = documentBeanDao.queryBuilder()
                .where(DocumentBeanDao.Properties.DataPackageId.eq(id))
                .where(DocumentBeanDao.Properties.PayClassifyName.notEq("照片AND视频"))
                .whereOr(DocumentBeanDao.Properties.PayClassifyName.eq("合同"),
                        DocumentBeanDao.Properties.PayClassifyName.eq("明细表"),
                        DocumentBeanDao.Properties.PayClassifyName.eq("任务书"))
                .list();

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


        list.addAll(documentBeans);
        lvFileAdapter = new LvFileAdapter(getActivity(), list);
        lvFileKitting.setAdapter(lvFileAdapter);


        List<DocumentBean> documentBeanList = documentBeanDao.queryBuilder()
                .where(DocumentBeanDao.Properties.DataPackageId.eq(id))
                .where(DocumentBeanDao.Properties.PayClassifyName.notEq("照片AND视频"))
                .where(DocumentBeanDao.Properties.PayClassifyName.notEq("合同"),
                        DocumentBeanDao.Properties.PayClassifyName.notEq("明细表"),
                        DocumentBeanDao.Properties.PayClassifyName.notEq("任务书"))
                .list();
        list2.addAll(documentBeanList);
        lvFileAdapter2 = new LvFileAdapter(getActivity(), list2);
        lvFileKitting2.setAdapter(lvFileAdapter2);

        tvAdd2.setOnClickListener(view -> {
            addFilePopupWindow = new AddFilePopupWindow(MyApplication.mContext, view, "", false, true);
            addFilePopupWindow.setAddFile(new AddFilePopupWindow.AddFile() {
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
                    DocumentBeanDao documentBeanDao3 = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
                    List<DocumentBean> documentBeanList1 = documentBeanDao3.queryBuilder()
                            .where(DocumentBeanDao.Properties.DataPackageId.eq(id))
                            .where(DocumentBeanDao.Properties.PayClassifyName.notEq("照片AND视频"))
                            .where(DocumentBeanDao.Properties.PayClassifyName.notEq("合同"),
                                    DocumentBeanDao.Properties.PayClassifyName.notEq("明细表"),
                                    DocumentBeanDao.Properties.PayClassifyName.notEq("任务书"))
                            .list();
                    list2.clear();
                    list2.addAll(documentBeanList1);
                    lvFileAdapter2.notifyDataSetChanged();
                }
            });
        });

        lvFileKitting.setOnItemClickListener((adapterView, view, i, l) ->
                addFilePopupWindow = new AddFilePopupWindow(MyApplication.mContext, view, list.get(i).getId(), true, false));

        lvFileKitting2.setOnItemClickListener((adapterView, view, i, l) -> {
            addFilePopupWindow = new AddFilePopupWindow(MyApplication.mContext, view, list.get(i).getId(), true, true);
            addFilePopupWindow.setAddFile(new AddFilePopupWindow.AddFile() {
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
                    DocumentBeanDao documentBeanDao3 = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
                    List<DocumentBean> documentBeanList12 = documentBeanDao3.queryBuilder()
                            .where(DocumentBeanDao.Properties.DataPackageId.eq(id))
                            .where(DocumentBeanDao.Properties.PayClassifyName.notEq("照片AND视频"))
                            .where(DocumentBeanDao.Properties.PayClassifyName.notEq("合同"),
                                    DocumentBeanDao.Properties.PayClassifyName.notEq("明细表"),
                                    DocumentBeanDao.Properties.PayClassifyName.notEq("任务书"))
                            .list();
                    list2.clear();
                    list2.addAll(documentBeanList12);
                    lvFileAdapter2.notifyDataSetChanged();

                }
            });
        });

        lvFileKitting2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("是否删除本条数据");
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeliveryListBeanDao deliveryListBeanDao = MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
                        DocumentBeanDao documentBeanDao = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
                        List<DocumentBean> documentBeans = documentBeanDao.queryBuilder()
                                .where(DocumentBeanDao.Properties.DataPackageId.eq(id))
                                .where(DocumentBeanDao.Properties.PayClassify.eq(list2.get(i).getId()))
                                .list();

                        if (documentBeans != null && !documentBeans.isEmpty()) {
                            FileBeanDao fileBeanDao = MyApplication.getInstances().getFileDaoSession().getFileBeanDao();
                            List<FileBean> fileBeans = fileBeanDao.queryBuilder()
                                    .where(FileBeanDao.Properties.DataPackageId.eq(id))
                                    .where(FileBeanDao.Properties.DocumentId.eq(documentBeans.get(0).getId()))
                                    .list();

                            for (int j = 0; j < fileBeans.size(); j++) {
                                fileBeanDao.deleteByKey(fileBeans.get(j).getUId());
                            }
                            documentBeanDao.deleteByKey(documentBeans.get(0).getUId());
                        }
                        deliveryListBeanDao.deleteByKey(list2.get(i).getUId());


                        DocumentBeanDao documentBeanDao2 = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
                        List<DocumentBean> documentBeanList = documentBeanDao2.queryBuilder()
                                .where(DocumentBeanDao.Properties.DataPackageId.eq(id))
                                .where(DocumentBeanDao.Properties.PayClassifyName.notEq("照片AND视频"))
                                .where(DocumentBeanDao.Properties.PayClassifyName.notEq("合同"),
                                        DocumentBeanDao.Properties.PayClassifyName.notEq("明细表"),
                                        DocumentBeanDao.Properties.PayClassifyName.notEq("任务书"))
                                .list();
                        list2.clear();
                        list2.addAll(documentBeanList);
                        lvFileAdapter2.notifyDataSetChanged();
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
        return R.layout.fragment_kitting_file;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (addFilePopupWindow!=null){
                addFilePopupWindow.setResult(data,requestCode);
            }
        }
    }

}
