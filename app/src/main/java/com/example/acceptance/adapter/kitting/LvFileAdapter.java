package com.example.acceptance.adapter.kitting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.DeliveryListBean;
import com.example.acceptance.greendao.bean.DocumentBean;
import com.example.acceptance.greendao.db.DocumentBeanDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author :created by ${ WYW }
 * 时间：2019/9/12 11
 */
public class LvFileAdapter extends BaseAdapter {

    private Context context;
    private List<DeliveryListBean> list;

    public LvFileAdapter(Context context, List<DeliveryListBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.lv_file_kitting, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvNum.setText(i+1+"");
        viewHolder.tvProject.setText(list.get(i).getProject());

        DocumentBeanDao documentBeanDao= MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
        List<DocumentBean> documentBeans=documentBeanDao.queryBuilder()
                .where(DocumentBeanDao.Properties.DataPackageId.eq(list.get(i).getDataPackageId()))
                .where(DocumentBeanDao.Properties.PayClassify.eq(list.get(i).getId()))
                .list();
        if (documentBeans!=null&&!documentBeans.isEmpty()){
            viewHolder.tvCode.setText(documentBeans.get(i).getCode());
            viewHolder.tvName.setText(documentBeans.get(i).getName());
            viewHolder.tv_secret.setText(documentBeans.get(i).getSecret());
            viewHolder.tvTechStatus.setText(documentBeans.get(i).getTechStatus());
            viewHolder.tvApprover.setText(documentBeans.get(i).getApprover());
            viewHolder.tvApprovalDate.setText(documentBeans.get(i).getApprovalDate());
            if (documentBeans.get(i).getIssl().equals("true")){
                viewHolder.tvIssl.setText("是");
            }else {
                viewHolder.tvIssl.setText("否");
            }

            viewHolder.tvConclusion.setText(documentBeans.get(i).getConclusion());
            viewHolder.tvDescription.setText(documentBeans.get(i).getDescription());
        }



        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_project)
        TextView tvProject;
        @BindView(R.id.tv_code)
        TextView tvCode;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_secret)
        TextView tv_secret;
        @BindView(R.id.tv_techStatus)
        TextView tvTechStatus;
        @BindView(R.id.tv_approver)
        TextView tvApprover;
        @BindView(R.id.tv_approvalDate)
        TextView tvApprovalDate;
        @BindView(R.id.tv_issl)
        TextView tvIssl;
        @BindView(R.id.tv_conclusion)
        TextView tvConclusion;
        @BindView(R.id.tv_description)
        TextView tvDescription;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
