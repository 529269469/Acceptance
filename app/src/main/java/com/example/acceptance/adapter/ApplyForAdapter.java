package com.example.acceptance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.greendao.bean.ApplyItemBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author :created by ${ WYW }
 * 时间：2019/9/26 14
 */
public class ApplyForAdapter extends BaseAdapter {
    private Context context;
    private List<ApplyItemBean> list;

    public ApplyForAdapter(Context context, List<ApplyItemBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.apply_for_item, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvNum.setText(i+1+"");
        viewHolder.tvProductCodeName.setText(list.get(i).getProductCodeName());
        viewHolder.tvProductName.setText(list.get(i).getProductName());
        viewHolder.tvProductCode.setText(list.get(i).getProductCode());
        viewHolder.tvProductStatus.setText(list.get(i).getProductStatus());
        viewHolder.tvCheckCount.setText(list.get(i).getCheckCount());

        if (list.get(i).getIsPureCheck().equals("true")){
            viewHolder.tvIsPureCheck.setText("是");
        }else {
            viewHolder.tvIsPureCheck.setText("否");
        }
        if (list.get(i).getIsArmyCheck().equals("true")){
            viewHolder.tvIsArmyCheck.setText("是");
        }else {
            viewHolder.tvIsArmyCheck.setText("否");
        }
        if (list.get(i).getIsCompleteChoice().equals("true")){
            viewHolder.tvIsCompleteChoice.setText("是");
        }else {
            viewHolder.tvIsCompleteChoice.setText("否");
        }

        if (list.get(i).getIsCompleteRoutine().equals("true")){
            viewHolder.tvIsCompleteRoutine.setText("是");
        }else {
            viewHolder.tvIsCompleteRoutine.setText("否");
        }
        if (list.get(i).getIsSatisfyRequire().equals("true")){
            viewHolder.tvIsSatisfyRequire.setText("是");
        }else {
            viewHolder.tvIsSatisfyRequire.setText("否");
        }
        viewHolder.tvDescription.setText(list.get(i).getDescription());
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_productCodeName)
        TextView tvProductCodeName;
        @BindView(R.id.tv_productName)
        TextView tvProductName;
        @BindView(R.id.tv_productCode)
        TextView tvProductCode;
        @BindView(R.id.tv_productStatus)
        TextView tvProductStatus;
        @BindView(R.id.tv_checkCount)
        TextView tvCheckCount;
        @BindView(R.id.tv_isPureCheck)
        TextView tvIsPureCheck;
        @BindView(R.id.tv_isArmyCheck)
        TextView tvIsArmyCheck;
        @BindView(R.id.tv_isCompleteChoice)
        TextView tvIsCompleteChoice;
        @BindView(R.id.tv_isCompleteRoutine)
        TextView tvIsCompleteRoutine;
        @BindView(R.id.tv_isSatisfyRequire)
        TextView tvIsSatisfyRequire;
        @BindView(R.id.tv_description)
        TextView tvDescription;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
