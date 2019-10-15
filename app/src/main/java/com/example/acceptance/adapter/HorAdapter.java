package com.example.acceptance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.bean.TitleBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author :created by ${ WYW }
 * 时间：2019/10/15 20
 */
public class HorAdapter extends BaseAdapter {
    private Context context;
    private List<TitleBean> list;

    public HorAdapter(Context context, List<TitleBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.hro_item, null);
            viewHolder=new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.tvHor.setText(list.get(i).getTitle());
        if (list.get(i).isCheck()){
            viewHolder.tvHor.setTextColor(context.getResources().getColor(R.color.color_5398F7));
        }else {
            viewHolder.tvHor.setTextColor(context.getResources().getColor(R.color.color_000));
        }

        return view;
    }

    static
    class ViewHolder {
        @BindView(R.id.tv_hor)
        TextView tvHor;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
