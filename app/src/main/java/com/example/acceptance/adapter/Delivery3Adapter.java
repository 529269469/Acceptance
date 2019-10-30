package com.example.acceptance.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.DataPackageDBean;
import com.example.acceptance.greendao.bean.DeliveryListBean;
import com.example.acceptance.greendao.bean.DocumentBean;
import com.example.acceptance.greendao.bean.FileBean;
import com.example.acceptance.greendao.db.DataPackageDBeanDao;
import com.example.acceptance.greendao.db.DocumentBeanDao;
import com.example.acceptance.greendao.db.FileBeanDao;
import com.example.acceptance.utils.OpenFileUtil;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author :created by ${ WYW }
 * 时间：2019/9/19 10
 */
public class Delivery3Adapter extends BaseAdapter {

    private Activity context;
    private List<DeliveryListBean> list;
    private List<FileBean> fileBeans;

    public Delivery3Adapter(Activity context, List<DeliveryListBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.delivery_item, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        DocumentBeanDao documentBeanDao= MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
        List<DocumentBean> documentBeans=documentBeanDao.queryBuilder()
                .where(DocumentBeanDao.Properties.DataPackageId.eq(list.get(i).getDataPackageId()))
                .where(DocumentBeanDao.Properties.PayClassify.eq(list.get(i).getId()))
                .list();

        if (documentBeans!=null&&!documentBeans.isEmpty()){

            viewHolder.tvName.setText(documentBeans.get(0).getName());
            viewHolder.tvProductCode.setText(documentBeans.get(0).getCode());

            FileBeanDao fileBeanDao=MyApplication.getInstances().getFileDaoSession().getFileBeanDao();
            List<FileBean> fileBeans= fileBeanDao.queryBuilder()
                    .where(FileBeanDao.Properties.DataPackageId.eq(list.get(i).getDataPackageId()))
                    .where(FileBeanDao.Properties.DocumentId.eq(documentBeans.get(0).getId()))
                    .list();

            if (fileBeans!=null&&!fileBeans.isEmpty()){
                viewHolder.tvNameFile.setText(fileBeans.get(0).getName());
            }
            viewHolder.tvNameFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("DocumentId",documentBeans.get(0).getId());
                    context.setResult(context.RESULT_OK,intent);
                    context.finish();

                }
            });


        }else {
            viewHolder.tvName.setText("");
            viewHolder.tvProductCode.setText("");
            viewHolder.tvNameFile.setText("");
        }





        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_project)
        TextView tvProject;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_productCode)
        TextView tvProductCode;
        @BindView(R.id.tv_name_file)
        TextView tvNameFile;
        @BindView(R.id.tv_caozuo)
        TextView tvCaozuo;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
