package com.example.acceptance.adapter.kitting;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.acceptance.R;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.net.Contents;
import com.example.acceptance.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author :created by ${ WYW }
 * 时间：2019/9/12 13
 */
public class ProductAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;

    public ProductAdapter(Context context, List<String> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.lv_product_kitting, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.btRelevance.setOnClickListener(view1 -> {
            showListDialog(view1);

        });


        return view;
    }

    static class ViewHolder {
        @BindView(R.id.bt_relevance)
        Button btRelevance;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    private void showListDialog(View view) {
        View poview =LayoutInflater.from(context).inflate(R.layout.relevance, null);
        PopupWindow popupWindow = new PopupWindow(poview);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        WindowManager.LayoutParams lp = MyApplication.mContext.getWindow().getAttributes();
        lp.alpha = 0.7f;
        MyApplication.mContext.getWindow().setAttributes(lp);

        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = MyApplication.mContext.getWindow().getAttributes();
            lp1.alpha = 1f;
            MyApplication.mContext.getWindow().setAttributes(lp1);
        });

        popupWindow.showAsDropDown(view);


    }


}
