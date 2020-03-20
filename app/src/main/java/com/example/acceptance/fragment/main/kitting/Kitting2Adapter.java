package com.example.acceptance.fragment.main.kitting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.acceptance.R;
import com.example.acceptance.bean.TitleBean;

import java.util.List;

/**
 * @author :created by ${ WYW }
 * 时间：2020/3/19 15
 */
public class Kitting2Adapter extends RecyclerView.Adapter<Kitting2Adapter.ViewHolder> {
    private List<TitleBean> list;
    private Context context;

    public Kitting2Adapter(List<TitleBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.kitting_tb, null);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_tb.setText(list.get(position).getTitle());

        if (list.get(position).isCheck()){
            holder.tv_tb.setTextColor(context.getResources().getColor(R.color.colorAccent));
            holder.tv_tb.setBackground(context.getDrawable(R.drawable.shape_tb2));
        }else {
            holder.tv_tb.setTextColor(context.getResources().getColor(R.color.color_65A4FA));
            holder.tv_tb.setBackground(context.getDrawable(R.drawable.shape_tb));
        }

        holder.tv_tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ontvTb.setTvTb(position);

            }
        });

        holder.tv_tb.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ontvTb.setLongTvTb(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_tb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tb = itemView.findViewById(R.id.tv_tb);
        }

    }

    public interface OntvTb {
        void setTvTb(int position);
        void setLongTvTb(int position);
    }

    private OntvTb ontvTb;

    public void setOntvTb(OntvTb ontvTb) {
        this.ontvTb = ontvTb;
    }
}
