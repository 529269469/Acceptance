package com.example.acceptance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.greendao.bean.ApplyDeptBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author :created by ${ WYW }
 * 时间：2019/10/14 15
 */
public class ApplyDeptAdapter extends BaseAdapter {
    private Context context;
    private List<ApplyDeptBean> list;

    public ApplyDeptAdapter(Context context, List<ApplyDeptBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.apply_dept_item, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvNum.setText(i+1+"");

        viewHolder.tvDepartment.setText(list.get(i).getDepartment());
        viewHolder.tvAcceptor.setText(list.get(i).getAcceptor());
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_department)
        TextView tvDepartment;
        @BindView(R.id.tv_acceptor)
        TextView tvAcceptor;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
