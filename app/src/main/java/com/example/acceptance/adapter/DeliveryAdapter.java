package com.example.acceptance.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acceptance.R;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.DataPackageDBean;
import com.example.acceptance.greendao.bean.DeliveryListBean;
import com.example.acceptance.greendao.bean.DocumentBean;
import com.example.acceptance.greendao.bean.FileBean;
import com.example.acceptance.greendao.db.DataPackageDBeanDao;
import com.example.acceptance.greendao.db.DeliveryListBeanDao;
import com.example.acceptance.greendao.db.DocumentBeanDao;
import com.example.acceptance.greendao.db.FileBeanDao;
import com.example.acceptance.utils.OpenFileUtil;
import com.example.acceptance.view.MyListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author :created by ${ WYW }
 * 时间：2019/9/19 10
 */
public class DeliveryAdapter extends BaseAdapter {

    private Context context;
    private List<DeliveryListBean> list;

    public DeliveryAdapter(Context context, List<DeliveryListBean> list) {
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

        viewHolder.tvProject.setText(list.get(i).getProject());

        DeliveryListBeanDao deliveryListBeanDao= MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
        List<DeliveryListBean> deliveryListBeans=deliveryListBeanDao.queryBuilder()
                .where(DeliveryListBeanDao.Properties.DataPackageId.eq(list.get(i).getDataPackageId()))
                .where(DeliveryListBeanDao.Properties.ParentId.eq(list.get(i).getId()))
                .list();

        Delivery2Adapter legacyAdapter = new Delivery2Adapter(context, deliveryListBeans);
        viewHolder.lv_list.setAdapter(legacyAdapter);
        legacyAdapter.setAddDelivery(new Delivery2Adapter.AddDelivery() {
            @Override
            public void setAddDelivery(int position) {

            }
        });
        viewHolder.tvProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDelivery.setAddDelivery(i);
            }
        });

        viewHolder.lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                addDelivery.setDelivery(deliveryListBeans,i);
            }
        });

        viewHolder.lv_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("是否删除本条数据");
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DeliveryListBeanDao deliveryListBeanDao= MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
                        DocumentBeanDao documentBeanDao= MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
                        List<DocumentBean> documentBeans=documentBeanDao.queryBuilder()
                                .where(DocumentBeanDao.Properties.DataPackageId.eq(deliveryListBeans.get(i).getDataPackageId()))
                                .where(DocumentBeanDao.Properties.PayClassify.eq(deliveryListBeans.get(i).getId()))
                                .list();
                        if (documentBeans!=null&&!documentBeans.isEmpty()){
                            FileBeanDao fileBeanDao=MyApplication.getInstances().getFileDaoSession().getFileBeanDao();
                            List<FileBean> fileBeans= fileBeanDao.queryBuilder()
                                    .where(FileBeanDao.Properties.DataPackageId.eq(deliveryListBeans.get(i).getDataPackageId()))
                                    .where(FileBeanDao.Properties.DocumentId.eq(documentBeans.get(0).getId()))
                                    .list();
                            for (int j = 0; j < fileBeans.size(); j++) {
                                fileBeanDao.deleteByKey(fileBeans.get(j).getUId());
                            }
                            documentBeanDao.deleteByKey(documentBeans.get(0).getUId());
                        }
                        deliveryListBeanDao.deleteByKey(deliveryListBeans.get(i).getUId());
                        deliveryListBeans.remove(i);
                        legacyAdapter.notifyDataSetChanged();
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
        @BindView(R.id.lv_list)
        MyListView lv_list;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface AddDelivery{
        void setAddDelivery(int position);
        void setDelivery(List<DeliveryListBean> deliveryListBeans, int position);
    }
     private AddDelivery addDelivery;

    public void setAddDelivery(AddDelivery addDelivery) {
        this.addDelivery = addDelivery;
    }
}
