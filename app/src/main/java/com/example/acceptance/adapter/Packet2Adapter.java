package com.example.acceptance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.utils.SPUtils;
import com.example.acceptance.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author :created by ${ WYW }
 * 时间：2019/9/9 15
 */
public class Packet2Adapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    private String type;
    public Packet2Adapter(Context context, List<String> list, String type) {
        this.context = context;
        this.list = list;
        this.type = type;
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
            view = LayoutInflater.from(context).inflate(R.layout.packet_item, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvNo.setText(position + 1 + "");
        viewHolder.tvType2.setText(list.get(position));

        viewHolder.tvDelete.setOnClickListener(view1 -> {
            list.remove(position);
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                stringBuffer.append(list.get(i)).append(",");
            }

            SPUtils.put(context, type, !StringUtils.isBlank(stringBuffer.toString())?
                    stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1):"");
            notifyDataSetChanged();
        });
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_no)
        TextView tvNo;
        @BindView(R.id.tv_type2)
        TextView tvType2;
        @BindView(R.id.tv_delete)
        TextView tvDelete;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
