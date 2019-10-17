package com.example.acceptance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.ApplyItemBean;
import com.example.acceptance.greendao.bean.UnresolvedBean;
import com.example.acceptance.greendao.db.ApplyItemBeanDao;
import com.example.acceptance.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author :created by ${ WYW }
 * 时间：2019/10/10 14
 */
public class UnresolvedAdapter2 extends BaseAdapter {
    private Context context;
    private List<ApplyItemBean> list;

    public UnresolvedAdapter2(Context context, List<ApplyItemBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.unresolve_item2, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvNum.setText(i+1+"");
        viewHolder.tvProductCode.setText(list.get(i).getProductCode());
        viewHolder.tvQuestion.setText(list.get(i).getProductName());
        viewHolder.tvConfirmer.setChecked(false);

        if (!StringUtils.isBlank(list.get(i).getPassCheck())&&list.get(i).getPassCheck().equals("true")){
            viewHolder.tvConfirmer.setChecked(true);
        }else {
            viewHolder.tvConfirmer.setChecked(false);
        }
        viewHolder.tvConfirmer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ApplyItemBeanDao applyItemBeanDao = MyApplication.getInstances().getApplyItemDaoSession().getApplyItemBeanDao();
                ApplyItemBean applyItemBean=new ApplyItemBean(list.get(i).getUId(),
                        list.get(i).getDataPackageId(),
                        list.get(i).getId(),
                        list.get(i).getProductCodeName(),
                        list.get(i).getProductCode(),
                        list.get(i).getProductStatus(),
                        list.get(i).getCheckCount(),
                        list.get(i).getIsPureCheck(),
                        list.get(i).getIsArmyCheck(),
                        list.get(i).getIsCompleteChoice(),
                        list.get(i).getIsCompleteRoutine(),
                        list.get(i).getIsSatisfyRequire(),
                        list.get(i).getDescription(),
                        list.get(i).getProductName(),
                        b+"");
                applyItemBeanDao.update(applyItemBean);
            }
        });


        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_productCode)
        TextView tvProductCode;
        @BindView(R.id.tv_question)
        TextView tvQuestion;
        @BindView(R.id.tv_confirmer)
        Switch tvConfirmer;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
