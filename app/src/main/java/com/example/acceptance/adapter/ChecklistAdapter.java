package com.example.acceptance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.greendao.bean.DataPackageDBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author :created by ${ WYW }
 * 时间：2019/9/15 13
 */
public class ChecklistAdapter extends BaseAdapter {
    private Context context;
    private List<DataPackageDBean> list;

    public ChecklistAdapter(Context context, List<DataPackageDBean> list) {
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.checklist_item, viewGroup, false);

            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvXuhao.setText(position + 1 + "");
        viewHolder.tvName.setText(list.get(position).getName());
        viewHolder.tvCode.setText(list.get(position).getCode());
        viewHolder.tvType.setText(list.get(position).getType());
        viewHolder.tvResponseUnit.setText(list.get(position).getResponseUnit());
        viewHolder.tvProductName.setText(list.get(position).getProductName());
        viewHolder.tvProductCode.setText(list.get(position).getProductCode());
        viewHolder.tvBatch.setText(list.get(position).getBatch());
        viewHolder.tvCreateTime.setText(list.get(position).getCreateTime());
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_xuhao)
        TextView tvXuhao;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_code)
        TextView tvCode;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_responseUnit)
        TextView tvResponseUnit;
        @BindView(R.id.tv_productName)
        TextView tvProductName;
        @BindView(R.id.tv_productCode)
        TextView tvProductCode;
        @BindView(R.id.tv_batch)
        TextView tvBatch;
        @BindView(R.id.tv_createTime)
        TextView tvCreateTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
