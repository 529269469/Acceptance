package com.example.acceptance.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.DeliveryListBean;
import com.example.acceptance.greendao.db.DeliveryListBeanDao;
import com.example.acceptance.utils.SPUtils;
import com.example.acceptance.utils.StringUtils;
import com.example.acceptance.utils.ToastUtils;

import java.util.List;
import java.util.UUID;

/**
 * @author :created by ${ WYW }
 * 时间：2019/11/4 15
 */
public class AddPrijectPopupWindow extends PopupWindow {
    private Activity context;
    private View view;
    private boolean isProject;
    private String id;
    private String typeDisplay;
    public AddPrijectPopupWindow(Activity context, View tvAdd,boolean isProject,String id) {
        super(context);
        this.context = context;
        this.isProject = isProject;
        this.id = id;
        view = context.getLayoutInflater().inflate(R.layout.popup_add_project, null);
        this.setContentView(view);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        this.setOutsideTouchable(true);
        this.setFocusable(true);
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = 0.7f;
        context.getWindow().setAttributes(lp);
        this.showAtLocation(tvAdd, Gravity.CENTER, 0, 0);
        this.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = context.getWindow().getAttributes();
            lp1.alpha = 1f;
            context.getWindow().setAttributes(lp1);

        });
        typeDisplay="";
        EditText et_project=view.findViewById(R.id.et_project);
        TextView tv_popup_save=view.findViewById(R.id.tv_popup_save);
        TextView tv_project=view.findViewById(R.id.tv_project);
        TextView tv_name=view.findViewById(R.id.tv_name);
        RadioGroup rg_project=view.findViewById(R.id.rg_project);
        LinearLayout ll_project=view.findViewById(R.id.ll_project);
        if (isProject){
            tv_project.setText("添加项目分类");
            tv_name.setText("项目分类");
            ll_project.setVisibility(View.GONE);
        }else {
            tv_project.setText("添加项目");
            tv_name.setText("项目");
            ll_project.setVisibility(View.VISIBLE);
        }

        rg_project.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.rb_manage:
                        typeDisplay="管理";
                        break;
                    case R.id.rb_technology:
                        typeDisplay="技术";
                        break;
                    case R.id.rb_else:
                        typeDisplay="其他";
                        break;
                }
            }
        });

        tv_popup_save.setOnClickListener(view -> {
            String projectStr=et_project.getText().toString().trim();
            if (StringUtils.isBlank(projectStr)){
                ToastUtils.getInstance().showTextToast(context,"请输入项目名称");
                return;
            }

            if (isProject){
                DeliveryListBeanDao deliveryListBeanDao= MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
                List<DeliveryListBean> deliveryListBeans=deliveryListBeanDao.queryBuilder()
                        .where(DeliveryListBeanDao.Properties.DataPackageId.eq((String)SPUtils.get(context,"id","")))
                        .where(DeliveryListBeanDao.Properties.Project.eq(projectStr))
                        .list();
                List<DeliveryListBean> deliveryListBeans2=deliveryListBeanDao.queryBuilder()
                        .where(DeliveryListBeanDao.Properties.DataPackageId.eq((String)SPUtils.get(context,"id","")))
                        .where(DeliveryListBeanDao.Properties.IsParent.eq("true"))
                        .list();
                String sortby="0";
                if (deliveryListBeans2!=null&&!deliveryListBeans2.isEmpty()){
                    try {
                        int sort=Integer.parseInt(deliveryListBeans2.get(deliveryListBeans2.size()-1).getSortBy()) ;
                        sortby=sort+1+"";
                    }catch (Exception ex){}

                }

                if (deliveryListBeans!=null&&!deliveryListBeans.isEmpty()){
                    ToastUtils.getInstance().showTextToast(context,"项目分类已存在");
                    return;
                }

                String deliveryId=System.currentTimeMillis()+"";
                DeliveryListBean deliveryListBean=new DeliveryListBean(null,
                        (String)SPUtils.get(context,"id",""),
                        deliveryId,
                        true+"",
                        projectStr,
                        "", UUID.randomUUID().toString(),"",sortby);
                deliveryListBeanDao.insert(deliveryListBean);
                ToastUtils.getInstance().showTextToast(context,"添加成功");
            }else {
                if (StringUtils.isBlank(typeDisplay)){
                    ToastUtils.getInstance().showTextToast(context,"请选择交付项类别");
                    return;
                }
                DeliveryListBeanDao deliveryListBeanDao= MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
                List<DeliveryListBean> deliveryListBeans=deliveryListBeanDao.queryBuilder()
                        .where(DeliveryListBeanDao.Properties.DataPackageId.eq((String)SPUtils.get(context,"id","")))
                        .where(DeliveryListBeanDao.Properties.ParentId.eq(id))
                        .where(DeliveryListBeanDao.Properties.Project.eq(projectStr))
                        .list();
                List<DeliveryListBean> deliveryListBeans2=deliveryListBeanDao.queryBuilder()
                        .where(DeliveryListBeanDao.Properties.DataPackageId.eq((String)SPUtils.get(context,"id","")))
                        .where(DeliveryListBeanDao.Properties.ParentId.eq(id))
                        .where(DeliveryListBeanDao.Properties.IsParent.eq("false"))
                        .list();
                String sortby="0";
                if (deliveryListBeans2!=null&&!deliveryListBeans2.isEmpty()){
                    try {
                        int sort=Integer.parseInt(deliveryListBeans2.get(deliveryListBeans2.size()-1).getSortBy()) ;
                        sortby=sort+1+"";
                    }catch (Exception ex){}
                }
                if (deliveryListBeans!=null&&!deliveryListBeans.isEmpty()){
                    ToastUtils.getInstance().showTextToast(context,"项目已存在");
                    return;
                }
                String deliveryId=System.currentTimeMillis()+"";
                DeliveryListBean deliveryListBean=new DeliveryListBean(null,
                        (String)SPUtils.get(context,"id",""),
                        deliveryId,
                        "false",
                        projectStr,
                        id,UUID.randomUUID().toString(),typeDisplay,sortby);
                deliveryListBeanDao.insert(deliveryListBean);
                ToastUtils.getInstance().showTextToast(context,"添加成功");
            }
            dismiss();
            addFile.addResult();

        });

    }

    public interface AddFile{
        void addResult();
    }

    private AddFile addFile;

    public void setAddFile(AddFile addFile) {
        this.addFile = addFile;
    }




}
