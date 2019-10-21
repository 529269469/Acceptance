package com.example.acceptance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.bean.PackageBean;
import com.example.acceptance.greendao.bean.DataPackageDBean;
import com.example.acceptance.greendao.db.DataPackageDBeanDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author :created by ${ WYW }
 * 时间：2019/10/12 14
 */
public class ToAdapter extends BaseAdapter {
    private Context context;
    private List<PackageBean> list;

    public ToAdapter(Context context, List<PackageBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.to_item, viewGroup, false);

            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvNum.setText(i + 1 + "");
        viewHolder.tvName.setText(list.get(i).getName());
        viewHolder.tvType.setText("未导入");
        viewHolder.tvType.setTextColor(context.getResources().getColor(R.color.colorAccent));

        DataPackageDBeanDao dataPackageDBeanDao= MyApplication.getInstances().getDataPackageDaoSession().getDataPackageDBeanDao();
        List<DataPackageDBean> dataPackageDBeans=dataPackageDBeanDao.loadAll();
        if (dataPackageDBeans!=null&&!dataPackageDBeans.isEmpty()){
            for (int j = 0; j < dataPackageDBeans.size(); j++) {
                if (dataPackageDBeans.get(j).getNamePackage().equals(list.get(i).getName())){
                    viewHolder.tvType.setText("已导入");
                    viewHolder.tvType.setTextColor(context.getResources().getColor(R.color.color_5398F7));
                    break;
                }
            }
        }

        return view;
    }

    static
    class ViewHolder {
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_type)
        TextView tvType;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
