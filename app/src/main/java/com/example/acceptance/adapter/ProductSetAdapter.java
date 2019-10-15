package com.example.acceptance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.greendao.bean.PropertyBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author :created by ${ WYW }
 * 时间：2019/10/15 15
 */
public class ProductSetAdapter extends BaseAdapter {
    private Context context;
    private List<PropertyBean> list;

    public ProductSetAdapter(Context context, List<PropertyBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.property_set_item, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvName.setText(list.get(i).getName());
        viewHolder.etValue.setText(list.get(i).getValue());

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.et_value)
        EditText etValue;
        @BindView(R.id.ll_checkGroupConclusion)
        LinearLayout llCheckGroupConclusion;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
