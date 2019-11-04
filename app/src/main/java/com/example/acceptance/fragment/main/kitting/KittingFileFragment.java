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
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.acceptance.R;
import com.example.acceptance.adapter.File2Adapter;
import com.example.acceptance.adapter.FileAdapter;
import com.example.acceptance.adapter.kitting.LvFileAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.ApplyDeptBean;
import com.example.acceptance.greendao.bean.DataPackageDBean;
import com.example.acceptance.greendao.bean.DeliveryListBean;
import com.example.acceptance.greendao.bean.DocumentBean;
import com.example.acceptance.greendao.bean.FileBean;
import com.example.acceptance.greendao.db.ApplyDeptBeanDao;
import com.example.acceptance.greendao.db.ApplyItemBeanDao;
import com.example.acceptance.greendao.db.CheckItemBeanDao;
import com.example.acceptance.greendao.db.CheckTaskBeanDao;
import com.example.acceptance.greendao.db.DataPackageDBeanDao;
import com.example.acceptance.greendao.db.DeliveryListBeanDao;
import com.example.acceptance.greendao.db.DocumentBeanDao;
import com.example.acceptance.greendao.db.FileBeanDao;
import com.example.acceptance.utils.FileUtils;
import com.example.acceptance.utils.OpenFileUtil;
import com.example.acceptance.utils.StringUtils;
import com.example.acceptance.utils.ToastUtils;
import com.example.acceptance.utils.ZipUtils2;
import com.example.acceptance.view.AddFilePopupWindow;
import com.example.acceptance.view.AddPopupWindow;
import com.example.acceptance.view.MyListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 齐套性检查——依据文件检查
 */
public class KittingFileFragment extends BaseFragment {

    @BindView(R.id.lv_file_kitting)
    MyListView lvFileKitting;
    @BindView(R.id.lv_file_kitting2)
    MyListView lvFileKitting2;
    @BindView(R.id.tv_add)
    TextView tvAdd;

    private List<DocumentBean> list = new ArrayList<>();
    private List<DocumentBean> list2 = new ArrayList<>();
    private String id;
    private LvFileAdapter lvFileAdapter;
    private LvFileAdapter lvFileAdapter2;
    private String parentId;
    private AddFilePopupWindow addFilePopupWindow;

    @Override
    protected void initEventAndData() {
        id = getArguments().getString("id");
        DeliveryListBeanDao deliveryListBeanDao = MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
        List<DeliveryListBean> deliveryListBeanList = deliveryListBeanDao.queryBuilder()
                .where(DeliveryListBeanDao.Properties.Project.eq("验收依据文件"))
                .list();
        if (deliveryListBeanList != null && !deliveryListBeanList.isEmpty()) {
            parentId = deliveryListBeanList.get(0).getId();
        } else {
            parentId = System.currentTimeMillis() + "";
            DeliveryListBean deliveryListBean = new DeliveryListBean(null,
                    id,
                    parentId,
                    true + "",
                    "验收依据文件", "");
            deliveryListBeanDao.insert(deliveryListBean);
        }


        DocumentBeanDao documentBeanDao = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
        List<DocumentBean> documentBeans = documentBeanDao.queryBuilder()
                .where(DocumentBeanDao.Properties.DataPackageId.eq(id))
                .whereOr(DocumentBeanDao.Properties.PayClassifyName.eq("合同"),
                        DocumentBeanDao.Properties.PayClassifyName.eq("明细表"),
                        DocumentBeanDao.Properties.PayClassifyName.eq("任务书"))
                .list();
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

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFilePopupWindow = new AddFilePopupWindow(MyApplication.mContext, tvAdd, "", false, true);

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
                        List<DocumentBean> documentBeans = documentBeanDao3.queryBuilder()
                                .where(DocumentBeanDao.Properties.DataPackageId.eq(id))
                                .whereOr(DocumentBeanDao.Properties.PayClassifyName.eq("合同"),
                                        DocumentBeanDao.Properties.PayClassifyName.eq("明细表"),
                                        DocumentBeanDao.Properties.PayClassifyName.eq("任务书"))
                                .list();
                        list.clear();
                        list.addAll(documentBeans);
                        lvFileAdapter.notifyDataSetChanged();

                    }
                });

            }
        });

        lvFileKitting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                addFilePopupWindow = new AddFilePopupWindow(MyApplication.mContext, lvFileKitting, list.get(i).getId(), true, true);
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
                        List<DocumentBean> documentBeans = documentBeanDao3.queryBuilder()
                                .where(DocumentBeanDao.Properties.DataPackageId.eq(id))
                                .whereOr(DocumentBeanDao.Properties.PayClassifyName.eq("合同"),
                                        DocumentBeanDao.Properties.PayClassifyName.eq("明细表"),
                                        DocumentBeanDao.Properties.PayClassifyName.eq("任务书"))
                                .list();
                        list.clear();
                        list.addAll(documentBeans);
                        lvFileAdapter.notifyDataSetChanged();

                    }
                });
            }
        });

        lvFileKitting2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                addFilePopupWindow = new AddFilePopupWindow(MyApplication.mContext, lvFileKitting2, list2.get(i).getId(), true, false);

            }
        });

        lvFileKitting.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("是否删除本条数据");
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeliveryListBeanDao deliveryListBeanDao = MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
                        List<DeliveryListBean> deliveryListBeanList = deliveryListBeanDao.queryBuilder()
                                .where(DeliveryListBeanDao.Properties.Id.eq(list.get(i).getPayClassify()))
                                .list();
                        deliveryListBeanDao.deleteByKey(deliveryListBeanList.get(0).getUId());

                        FileBeanDao fileBeanDao = MyApplication.getInstances().getFileDaoSession().getFileBeanDao();
                        List<FileBean> fileBeans = fileBeanDao.queryBuilder()
                                .where(FileBeanDao.Properties.DataPackageId.eq(id))
                                .where(FileBeanDao.Properties.DocumentId.eq(list.get(i).getId()))
                                .list();

                        for (int j = 0; j < fileBeans.size(); j++) {
                            fileBeanDao.deleteByKey(fileBeans.get(j).getUId());
                        }
                        documentBeanDao.deleteByKey(list.get(i).getUId());


                        DocumentBeanDao documentBeanDao2 = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
                        List<DocumentBean> documentBeans2 = documentBeanDao2.queryBuilder()
                                .where(DocumentBeanDao.Properties.DataPackageId.eq(id))
                                .whereOr(DocumentBeanDao.Properties.PayClassifyName.eq("合同"),
                                        DocumentBeanDao.Properties.PayClassifyName.eq("明细表"),
                                        DocumentBeanDao.Properties.PayClassifyName.eq("任务书"))
                                .list();
                        list.clear();
                        list.addAll(documentBeans2);
                        lvFileAdapter.notifyDataSetChanged();
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
