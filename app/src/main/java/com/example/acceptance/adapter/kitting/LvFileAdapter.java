package com.example.acceptance.adapter.kitting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.acceptance.R;

import java.util.List;

/**
 * @author :created by ${ WYW }
 * 时间：2019/9/12 11
 */
public class LvFileAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;

    public LvFileAdapter(Context context, List<String> list) {
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
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.lv_file_kitting,viewGroup,false);
            viewHolder=new ViewHolder();
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }

        return view;
    }

    static class ViewHolder{

    }
}
