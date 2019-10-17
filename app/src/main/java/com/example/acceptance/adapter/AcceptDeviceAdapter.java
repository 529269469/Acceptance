package com.example.acceptance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.greendao.bean.AcceptDeviceBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author :created by ${ WYW }
 * 时间：2019/10/16 10
 */
public class AcceptDeviceAdapter extends BaseAdapter {
    private Context context;
    private List<AcceptDeviceBean> list;

    public AcceptDeviceAdapter(Context context, List<AcceptDeviceBean> list) {
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
        ViewHolder viewHolder=null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.accept_device_item, viewGroup, false);
            viewHolder=new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.tvNum.setText(i+1+"");
        viewHolder.tvName.setText(list.get(i).getName());
        viewHolder.tvSpecification.setText(list.get(i).getSpecification());
        viewHolder.tvAccuracy.setText(list.get(i).getAccuracy());
        viewHolder.tvCertificate.setText(list.get(i).getCertificate());
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_specification)
        TextView tvSpecification;
        @BindView(R.id.tv_accuracy)
        TextView tvAccuracy;
        @BindView(R.id.tv_certificate)
        TextView tvCertificate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
