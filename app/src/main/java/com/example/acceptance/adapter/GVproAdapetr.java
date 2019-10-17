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
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.PropertyBean;
import com.example.acceptance.greendao.bean.PropertyBeanX;
import com.example.acceptance.greendao.db.PropertyBeanXDao;
import com.example.acceptance.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author :created by ${ WYW }
 * 时间：2019/10/8 14
 */
public class GVproAdapetr extends BaseAdapter {
    private Context context;
    private List<PropertyBeanX> list;

    public GVproAdapetr(Context context, List<PropertyBeanX> list) {
        this.context = context;
        this.list = list;
    }

    public List<PropertyBeanX> getList() {
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
        ViewHolder viewHolder=null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.lv_product_gv_item, viewGroup, false);
            viewHolder = new ViewHolder(view);
            viewHolder.et_value.setTag(i);
            viewHolder.et_value.setOnTouchListener((v, event) -> {
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
                        int pos = (Integer) mHolder.et_value.getTag();
                        list.set(pos, new PropertyBeanX(list.get(i).getUId(),
                                list.get(i).getDataPackageId(),
                                list.get(i).getCheckFileId(),
                                list.get(i).getCheckGroupId(),
                                list.get(i).getCheckItemId(),
                                list.get(i).getName(),
                                words));

                        PropertyBeanXDao propertyBeanXDao= MyApplication.getInstances().getPropertyXDaoSession().getPropertyBeanXDao();
                        PropertyBeanX propertyBeanX=new PropertyBeanX(list.get(i).getUId(),
                                list.get(i).getDataPackageId(),
                                list.get(i).getCheckFileId(),
                                list.get(i).getCheckGroupId(),
                                list.get(i).getCheckItemId(),
                                list.get(i).getName(),
                                words);
                        propertyBeanXDao.update(propertyBeanX);
                    }
                }
            }
            viewHolder.et_value.addTextChangedListener(new MyTextWatcher(viewHolder));
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
            viewHolder.et_value.setTag(i);
        }
        viewHolder.tv_name.setText(list.get(i).getName());
        viewHolder.et_value.setText(list.get(i).getValue());
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.et_value)
        EditText et_value;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
