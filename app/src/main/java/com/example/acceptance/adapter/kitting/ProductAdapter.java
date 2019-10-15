package com.example.acceptance.adapter.kitting;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acceptance.R;
import com.example.acceptance.adapter.FileAdapter;
import com.example.acceptance.adapter.GVproAdapetr;
import com.example.acceptance.adapter.OptionsAdapter;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.bean.TitleBean;
import com.example.acceptance.greendao.bean.CheckItemBean;
import com.example.acceptance.greendao.bean.DataPackageDBean;
import com.example.acceptance.greendao.bean.DocumentBean;
import com.example.acceptance.greendao.bean.FileBean;
import com.example.acceptance.greendao.bean.PropertyBean;
import com.example.acceptance.greendao.bean.PropertyBeanX;
import com.example.acceptance.greendao.bean.RelatedDocumentIdSetBean;
import com.example.acceptance.greendao.db.CheckItemBeanDao;
import com.example.acceptance.greendao.db.DataPackageDBeanDao;
import com.example.acceptance.greendao.db.DocumentBeanDao;
import com.example.acceptance.greendao.db.FileBeanDao;
import com.example.acceptance.greendao.db.PropertyBeanDao;
import com.example.acceptance.greendao.db.PropertyBeanXDao;
import com.example.acceptance.greendao.db.RelatedDocumentIdSetBeanDao;
import com.example.acceptance.utils.OpenFileUtil;
import com.example.acceptance.view.HorizontalListView;
import com.example.acceptance.view.MyGridView;
import com.example.acceptance.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author :created by ${ WYW }
 * 时间：2019/9/12 13
 */
public class ProductAdapter extends BaseAdapter {
    private Context context;
    private List<CheckItemBean> list;

    public ProductAdapter(Context context, List<CheckItemBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.lv_product_kitting, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_num.setText(i+1+"");

        viewHolder.btRelevance.setOnClickListener(view1 -> {
            showListDialog(view1);
        });
        viewHolder.tvName.setText(list.get(i).getName());

        String[] options=list.get(i).getOptions().split(",");
        List<TitleBean> titleBeans=new ArrayList<>();

        for (int j = 0; j < options.length; j++) {
            titleBeans.add(new TitleBean(options[j]));
            if (list.get(i).getSelected().equals(options[j])){
                titleBeans.get(j).setCheck(true);
            }
        }
        OptionsAdapter optionsAdapter=new OptionsAdapter(context,titleBeans);
        viewHolder.lvYesNo.setAdapter(optionsAdapter);

        viewHolder.lvYesNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                for (int j = 0; j < titleBeans.size(); j++) {
                    titleBeans.get(j).setCheck(false);
                }
                titleBeans.get(i).setCheck(true);
                optionsAdapter.notifyDataSetChanged();
            }
        });

        PropertyBeanXDao propertyBeanXDao=MyApplication.getInstances().getPropertyXDaoSession().getPropertyBeanXDao();
        List<PropertyBeanX> propertyBeans=propertyBeanXDao.queryBuilder()
                .where(PropertyBeanXDao.Properties.DataPackageId.eq(list.get(i).getDataPackageId()))
                .where(PropertyBeanXDao.Properties.CheckFileId.eq(list.get(0).getCheckFileId()))
                .where(PropertyBeanXDao.Properties.CheckGroupId.eq(list.get(0).getCheckGroupId()))
                .where(PropertyBeanXDao.Properties.CheckItemId.eq(list.get(i).getId()))
                .list();

        GVproAdapetr gVproAdapetr=new GVproAdapetr(context,propertyBeans);
        viewHolder.gvPro.setAdapter(gVproAdapetr);

        RelatedDocumentIdSetBeanDao documentIdSetBeanDao=MyApplication.getInstances().getRelatedDocumentIdSetDaoSession().getRelatedDocumentIdSetBeanDao();
        List<RelatedDocumentIdSetBean> relatedDocumentIdSetBeans=documentIdSetBeanDao.queryBuilder()
                .where(RelatedDocumentIdSetBeanDao.Properties.DataPackageId.eq(list.get(i).getDataPackageId()))
                .where(RelatedDocumentIdSetBeanDao.Properties.CheckFileId.eq(list.get(0).getCheckFileId()))
                .where(RelatedDocumentIdSetBeanDao.Properties.CheckGroupId.eq(list.get(0).getCheckGroupId()))
                .where(RelatedDocumentIdSetBeanDao.Properties.CheckItemId.eq(list.get(i).getId()))
                .list();
        List<FileBean> fileBeanList=new ArrayList<>();
        if (relatedDocumentIdSetBeans!=null&&!relatedDocumentIdSetBeans.isEmpty()){
            DocumentBeanDao documentBeanDao=MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
            for (int j = 0; j <relatedDocumentIdSetBeans.size() ; j++) {
                List<DocumentBean> documentBeans=documentBeanDao.queryBuilder()
                        .where(DocumentBeanDao.Properties.DataPackageId.eq(list.get(i).getDataPackageId()))
                        .where(DocumentBeanDao.Properties.Id.eq(relatedDocumentIdSetBeans.get(j).getRelatedDocumentId()))
                        .list();
                if (documentBeans!=null&&!documentBeans.isEmpty()){
                    FileBeanDao fileBeanDao=MyApplication.getInstances().getCheckFileDaoSession().getFileBeanDao();
                    List<FileBean> fileBeans=fileBeanDao.queryBuilder()
                            .where(FileBeanDao.Properties.DataPackageId.eq(list.get(i).getDataPackageId()))
                            .where(FileBeanDao.Properties.DocumentId.eq(documentBeans.get(0).getId()))
                            .list();
                    fileBeanList.addAll(fileBeans);
                }
            }

        }

        FileAdapter fileAdapter=new FileAdapter(context,fileBeanList);
        viewHolder.lv_file.setAdapter(fileAdapter);

        viewHolder.lv_file.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DataPackageDBeanDao dataPackageDBeanDao=MyApplication.getInstances().getDataPackageDaoSession().getDataPackageDBeanDao();
                List<DataPackageDBean> dataPackageDBeans=dataPackageDBeanDao.queryBuilder()
                        .where(DataPackageDBeanDao.Properties.Id.eq(list.get(i).getDataPackageId()))
                        .list();
                try {
                    context.startActivity(OpenFileUtil.openFile(dataPackageDBeans.get(0).getUpLoadFile()+"/"+fileBeanList.get(i).getPath()));
                }catch (Exception o){
                    Toast.makeText(MyApplication.mContext, "不支持此类型", Toast.LENGTH_SHORT).show();
                }

            }
        });





        return view;
    }


    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.bt_relevance)
        TextView btRelevance;
        @BindView(R.id.lv_yes_no)
        MyGridView lvYesNo;
        @BindView(R.id.gv_pro)
        MyGridView gvPro;
        @BindView(R.id.lv_file)
        MyListView lv_file;
        @BindView(R.id.tv_num)
        TextView tv_num;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }



    private void showListDialog(View view) {
        View poview = LayoutInflater.from(context).inflate(R.layout.relevance, null);
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

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);


    }
}
