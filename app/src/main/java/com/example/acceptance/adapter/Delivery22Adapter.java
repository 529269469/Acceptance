package com.example.acceptance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.DeliveryListBean;
import com.example.acceptance.greendao.bean.DocumentBean;
import com.example.acceptance.greendao.db.DocumentBeanDao;
import com.example.acceptance.view.MyListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author :created by ${ WYW }
 * 时间：2019/9/19 10
 */
public class Delivery22Adapter extends BaseAdapter {

    private Context context;
    private List<DocumentBean> list;


    public Delivery22Adapter(Context context, List<DocumentBean> list) {
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
        viewHolder.tvProductCode.setText(list.get(i).getCode());
        viewHolder.tvNameFile.setText(list.get(i).getName());
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

}
