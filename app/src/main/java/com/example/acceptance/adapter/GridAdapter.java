package com.example.acceptance.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.acceptance.R;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.FileBean;
import com.example.acceptance.net.URLS;
import com.example.acceptance.utils.OpenFileUtil;
import com.example.acceptance.utils.SPUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GridAdapter extends BaseAdapter {
    private List<FileBean> list;
    private Context context;

    public GridAdapter(List<FileBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolde viewHolde = null;
        if (convertView == null) {
            viewHolde = new ViewHolde();
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
            viewHolde.iv_grid = convertView.findViewById(R.id.iv_grid);
            viewHolde.iv_grid2 = convertView.findViewById(R.id.iv_grid2);
            viewHolde.iv_del = convertView.findViewById(R.id.iv_del);

            convertView.setTag(viewHolde);
        } else {
            viewHolde = (ViewHolde) convertView.getTag();
        }


        /* 取得扩展名 */
       String end = list.get(position).getPath().substring(list.get(position).getPath().lastIndexOf(".") + 1, list.get(position).getPath().length()).toLowerCase(Locale.getDefault());
        if (end.equals("m4a") ||
                end.equals("mp3") ||
                end.equals("mid") ||
                end.equals("xmf") ||
                end.equals("ogg") ||
                end.equals("wav") ||
                end.equals("3gp") ||
                end.equals("mp4") ||
                end.equals("wmv")) {
            viewHolde.iv_grid2.setVisibility(View.VISIBLE);
        } else {
            viewHolde.iv_grid2.setVisibility(View.GONE);
        }
        Glide.with(context)
                .load(new File(SPUtils.get(context, "path", "") + File.separator +list.get(position).getPath()))
                .into(viewHolde.iv_grid);

        viewHolde.iv_grid.setOnClickListener(new View.OnClickListener() {
            private PopupWindow popupWindow;

            @Override
            public void onClick(View v) {
                if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg") || end.equals("bmp")) {
                    View poview = LayoutInflater.from(context).inflate(R.layout.image_view, null);
                    popupWindow = null;
                    popupWindow = new PopupWindow(poview);
                    popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                    popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setFocusable(true);

                    WindowManager.LayoutParams lp = MyApplication.mContext.getWindow().getAttributes();
                    lp.alpha = 0.7f;
                    MyApplication.mContext.getWindow().setAttributes(lp);

                    popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                    ImageView iv_image = poview.findViewById(R.id.iv_image);
                    Glide.with(context)
                            .load(new File(SPUtils.get(context, "path", "") + File.separator +list.get(position).getPath()))
                            .into(iv_image);
                    popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            WindowManager.LayoutParams lp = MyApplication.mContext.getWindow().getAttributes();
                            lp.alpha = 1f;
                            MyApplication.mContext.getWindow().setAttributes(lp);
                        }
                    });


                    iv_image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                } else {
                    try {
                        context.startActivity(OpenFileUtil.openFile( SPUtils.get(context, "path", "") + File.separator +list.get(position).getPath()));
                    } catch (Exception e) {
                        Toast.makeText(context, "打开失败，原因：文件已经被移动或者删除", Toast.LENGTH_SHORT).show();
                    }

                }
            }

        });


        viewHolde.iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDel.onDel(position);
            }
        });

        return convertView;
    }

    public static class ViewHolde {
        private ImageView iv_grid;
        private ImageView iv_grid2;
        private ImageView iv_del;
    }

    public interface OnDel{
        public void onDel(int position);
    }
    private OnDel onDel;

    public void setOnDel(OnDel onDel) {
        this.onDel = onDel;
    }
}
