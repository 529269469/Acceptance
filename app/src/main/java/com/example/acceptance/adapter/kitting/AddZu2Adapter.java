package com.example.acceptance.adapter.kitting;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.acceptance.R;
import com.example.acceptance.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author :created by ${ WYW }
 * 时间：2019/10/15 13
 */
public class AddZu2Adapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    private Integer index = -1;

    public List<String> getList() {
        return list;
    }

    public AddZu2Adapter(Context context, List<String> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.add_view_item, viewGroup, false);
            viewHolder = new ViewHolder(view);
            viewHolder.etAdd.setTag(i);
            viewHolder.etAdd.setOnTouchListener((v, event) -> {
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
                        int pos = (Integer) mHolder.etAdd.getTag();
                        list.set(pos, words);

                    }
                }
            }
            viewHolder.etAdd.addTextChangedListener(new MyTextWatcher(viewHolder));

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
            viewHolder.etAdd.setTag(i);
        }
        if (i<2){
            viewHolder.ivDel.setVisibility(View.GONE);
        }else {
            viewHolder.ivDel.setVisibility(View.VISIBLE);
        }
        viewHolder.etAdd.setText(list.get(i));

        viewHolder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDel.onDel(i);
            }
        });

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.et_add)
        EditText etAdd;
        @BindView(R.id.iv_del)
        ImageView ivDel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface OnDel {
        void onDel(int position);
    }

    private OnDel onDel;

    public void setOnDel(OnDel onDel) {
        this.onDel = onDel;
    }

}
