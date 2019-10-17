package com.example.acceptance.adapter.kitting;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acceptance.R;
import com.example.acceptance.adapter.FileAdapter;
import com.example.acceptance.adapter.GVproAdapetr;
import com.example.acceptance.adapter.GridAdapter;
import com.example.acceptance.adapter.OptionsAdapter;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.bean.TitleBean;
import com.example.acceptance.greendao.bean.CheckItemBean;
import com.example.acceptance.greendao.bean.DataPackageDBean;
import com.example.acceptance.greendao.bean.DocumentBean;
import com.example.acceptance.greendao.bean.FileBean;
import com.example.acceptance.greendao.bean.PropertyBean;
import com.example.acceptance.greendao.bean.PropertyBeanX;
import com.example.acceptance.greendao.bean.RelatedDocumentIdSetBean;
import com.example.acceptance.greendao.db.CheckItemBeanDao;
import com.example.acceptance.greendao.db.DataPackageDBeanDao;
import com.example.acceptance.greendao.db.DocumentBeanDao;
import com.example.acceptance.greendao.db.FileBeanDao;
import com.example.acceptance.greendao.db.PropertyBeanDao;
import com.example.acceptance.greendao.db.PropertyBeanXDao;
import com.example.acceptance.greendao.db.RelatedDocumentIdSetBeanDao;
import com.example.acceptance.utils.FileUtils;
import com.example.acceptance.utils.OpenFileUtil;
import com.example.acceptance.utils.StringUtils;
import com.example.acceptance.view.HorizontalListView;
import com.example.acceptance.view.MyGridView;
import com.example.acceptance.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author :created by ${ WYW }
 * 时间：2019/9/12 13
 */
public class ProductAdapter extends BaseAdapter {
    private Context context;
    private List<CheckItemBean> list;
    private GVproAdapetr gVproAdapetr;


    public ProductAdapter(Context context, List<CheckItemBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.lv_product_kitting, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_num.setText(i+1+"");

        viewHolder.btRelevance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relevance.setRelevance(i,view);
            }
        });
        viewHolder.tvName.setText(list.get(i).getName());

        String[] options=list.get(i).getOptions().split(",");
        List<TitleBean> titleBeans=new ArrayList<>();

        for (int j = 0; j < options.length; j++) {
            titleBeans.add(new TitleBean(options[j]));
            if (list.get(i).getSelected().equals(options[j])){
                titleBeans.get(j).setCheck(true);
            }
        }
        OptionsAdapter optionsAdapter=new OptionsAdapter(context,titleBeans);
        viewHolder.lvYesNo.setAdapter(optionsAdapter);

        viewHolder.lvYesNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                for (int j = 0; j < titleBeans.size(); j++) {
                    titleBeans.get(j).setCheck(false);
                }
                titleBeans.get(pos).setCheck(true);
                optionsAdapter.notifyDataSetChanged();

                CheckItemBeanDao checkItemBeanDao = MyApplication.getInstances().getCheckItemDaoSession().getCheckItemBeanDao();

                CheckItemBean checkItemBean = new CheckItemBean(list.get(i).getUId(),
                        list.get(i).getDataPackageId(),
                        list.get(i).getCheckFileId(),
                        list.get(i).getCheckGroupId(),
                        list.get(i).getId(),
                        list.get(i).getName(),
                        list.get(i).getOptions(),
                        titleBeans.get(pos).getTitle(),
                        list.get(i).getImgAndVideo());
                checkItemBeanDao.update(checkItemBean);

            }
        });

        PropertyBeanXDao propertyBeanXDao=MyApplication.getInstances().getPropertyXDaoSession().getPropertyBeanXDao();
        List<PropertyBeanX> propertyBeans=propertyBeanXDao.queryBuilder()
                .where(PropertyBeanXDao.Properties.DataPackageId.eq(list.get(i).getDataPackageId()))
                .where(PropertyBeanXDao.Properties.CheckFileId.eq(list.get(0).getCheckFileId()))
                .where(PropertyBeanXDao.Properties.CheckGroupId.eq(list.get(0).getCheckGroupId()))
                .where(PropertyBeanXDao.Properties.CheckItemId.eq(list.get(i).getId()))
                .list();

        gVproAdapetr = new GVproAdapetr(context,propertyBeans);
        viewHolder.gvPro.setAdapter(gVproAdapetr);

        RelatedDocumentIdSetBeanDao documentIdSetBeanDao=MyApplication.getInstances().getRelatedDocumentIdSetDaoSession().getRelatedDocumentIdSetBeanDao();
        List<RelatedDocumentIdSetBean> relatedDocumentIdSetBeans=documentIdSetBeanDao.queryBuilder()
                .where(RelatedDocumentIdSetBeanDao.Properties.DataPackageId.eq(list.get(i).getDataPackageId()))
                .where(RelatedDocumentIdSetBeanDao.Properties.CheckFileId.eq(list.get(i).getCheckFileId()))
                .where(RelatedDocumentIdSetBeanDao.Properties.CheckGroupId.eq(list.get(i).getCheckGroupId()))
                .where(RelatedDocumentIdSetBeanDao.Properties.CheckItemId.eq(list.get(i).getId()))
                .list();
        List<FileBean> fileBeanList=new ArrayList<>();
        if (relatedDocumentIdSetBeans!=null&&!relatedDocumentIdSetBeans.isEmpty()){
            DocumentBeanDao documentBeanDao=MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
            for (int j = 0; j <relatedDocumentIdSetBeans.size() ; j++) {
                List<DocumentBean> documentBeans=documentBeanDao.queryBuilder()
                        .where(DocumentBeanDao.Properties.DataPackageId.eq(list.get(i).getDataPackageId()))
                        .where(DocumentBeanDao.Properties.Id.eq(relatedDocumentIdSetBeans.get(j).getRelatedDocumentId()))
                        .list();
                if (documentBeans!=null&&!documentBeans.isEmpty()){
                    FileBeanDao fileBeanDao=MyApplication.getInstances().getCheckFileDaoSession().getFileBeanDao();
                    List<FileBean> fileBeans=fileBeanDao.queryBuilder()
                            .where(FileBeanDao.Properties.DataPackageId.eq(list.get(i).getDataPackageId()))
                            .where(FileBeanDao.Properties.DocumentId.eq(documentBeans.get(0).getId()))
                            .list();
                    fileBeanList.addAll(fileBeans);
                }
            }

        }

        FileAdapter fileAdapter=new FileAdapter(context,fileBeanList);
        viewHolder.lv_file.setAdapter(fileAdapter);

        List<String> gridList=new ArrayList<>();
        if (!StringUtils.isBlank(list.get(i).getImgAndVideo())){
            gridList.clear();
            String[] imgs=list.get(i).getImgAndVideo().split(",");
            for (int j = 0; j < imgs.length; j++) {
                gridList.add(imgs[j]);
            }
        }
        GridAdapter gridAdapter=new GridAdapter(gridList,context);
        gridAdapter.setOnDel(new GridAdapter.OnDel() {
            @Override
            public void onDel(int position) {
                geidDel.setGeidDel(position,i);
                gridList.remove(position);
                gridAdapter.notifyDataSetChanged();
            }
        });
        viewHolder.gv_video.setAdapter(gridAdapter);

        viewHolder.lv_file.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DataPackageDBeanDao dataPackageDBeanDao=MyApplication.getInstances().getDataPackageDaoSession().getDataPackageDBeanDao();
                List<DataPackageDBean> dataPackageDBeans=dataPackageDBeanDao.queryBuilder()
                        .where(DataPackageDBeanDao.Properties.Id.eq(list.get(i).getDataPackageId()))
                        .list();
                try {
                    context.startActivity(OpenFileUtil.openFile(dataPackageDBeans.get(0).getUpLoadFile()+"/"+fileBeanList.get(i).getPath()));
                }catch (Exception o){
                    Toast.makeText(MyApplication.mContext, "不支持此类型", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }




    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.bt_relevance)
        TextView btRelevance;
        @BindView(R.id.lv_yes_no)
        MyGridView lvYesNo;
        @BindView(R.id.gv_pro)
        MyGridView gvPro;
        @BindView(R.id.lv_file)
        MyListView lv_file;
        @BindView(R.id.tv_num)
        TextView tv_num;
        @BindView(R.id.gv_video)
        MyGridView gv_video;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface GeidDel{
        void setGeidDel(int pos,int pos1);
    }

    private GeidDel geidDel;

    public void setGeidDel(GeidDel geidDel) {
        this.geidDel = geidDel;
    }

    public interface Relevance{
        void setRelevance(int pos, View view);
    }

    private Relevance relevance;

    public void setRelevance(Relevance relevance) {
        this.relevance = relevance;
    }

}
