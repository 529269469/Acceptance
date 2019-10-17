package com.example.acceptance.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.greendao.bean.PropertyBean;
import com.example.acceptance.utils.StringUtils;

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

    public List<PropertyBean> getList() {
        return list;
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
    private Integer index = -1;
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.property_set_item, viewGroup, false);
            viewHolder = new ViewHolder(view);
            viewHolder.etValue.setTag(i);
            viewHolder.etValue.setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    index = (Integer) v.getTag();
                }
                return false;
            });

            class MyTextWatcher implements TextWatcher {

                public MyTextWatcher(ViewHolder holder) {
                    mHolder = holder;
                }

                private ViewHolder mHolder;

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    String words = s.toString();
                    //首先内容进行非空判断，空内容（""和null）不处理
                    if (!StringUtils.isBlank(words)) {
                        int pos = (Integer) mHolder.etValue.getTag();
                        list.set(pos, new PropertyBean(list.get(i).getUId(),
                                list.get(i).getDataPackageId(),
                                list.get(i).getCheckFileId(),
                                list.get(i).getCheckGroupId(),
                                list.get(i).getName(),
                                words));

                    }
                }
            }
            viewHolder.etValue.addTextChangedListener(new MyTextWatcher(viewHolder));
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
            viewHolder.etValue.setTag(i);
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
