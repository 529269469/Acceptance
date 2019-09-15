package com.example.acceptance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.acceptance.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author :created by ${ WYW }
 * 时间：2019/9/15 13
 */
public class ChecklistAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;

    public ChecklistAdapter(Context context, List<String> list) {
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

        if (position==0){
            viewHolder.tvXuhao.setText("1");
            viewHolder.tvName.setText("遥安分系统");
        }else if (position==1){
            viewHolder.tvXuhao.setText("2");
            viewHolder.tvName.setText("遥安分系统2");
        }

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_xuhao)
        TextView tvXuhao;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_zerendanwei)
        TextView tvZerendanwei;
        @BindView(R.id.tv_name2)
        TextView tvName2;
        @BindView(R.id.tv_num2)
        TextView tvNum2;
        @BindView(R.id.tv_pici)
        TextView tvPici;
        @BindView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
