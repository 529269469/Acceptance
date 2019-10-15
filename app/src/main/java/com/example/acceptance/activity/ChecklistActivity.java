package com.example.acceptance.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.adapter.ChecklistAdapter;
import com.example.acceptance.base.BaseActivity;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.DataPackageDBean;
import com.example.acceptance.greendao.db.DataPackageDBeanDao;
import com.example.acceptance.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 历史任务
 */
public class ChecklistActivity extends BaseActivity {

    @BindView(R.id.iv_genduo)
    ImageView ivGenduo;
    @BindView(R.id.tv_tuichu)
    TextView tvTuichu;
    @BindView(R.id.lv_checklist)
    MyListView lvChecklist;
    private boolean isDel;
    public static Intent openIntent(Context context,boolean isDel) {
        Intent intent = new Intent(context, ChecklistActivity.class);
        intent.putExtra("isDel",isDel);
        return intent;
    }

    private ChecklistAdapter checklistAdapter;
    private List<DataPackageDBean> list = new ArrayList<>();

    @Override
    protected void initView() {
        ivGenduo.setOnClickListener(view -> finish());
        isDel=getIntent().getBooleanExtra("isDel",false);

        DataPackageDBeanDao dataPackageDBeanDao= MyApplication.getInstances().getDataPackageDaoSession().getDataPackageDBeanDao();
        List<DataPackageDBean> dataPackageDBeans=dataPackageDBeanDao.loadAll();

        list.addAll(dataPackageDBeans);
        checklistAdapter=new ChecklistAdapter(this,list);
        lvChecklist.setAdapter(checklistAdapter);

        if (isDel){
            startActivity(MainActivity.openIntent(ChecklistActivity.this,list.get(0).getId(),true));
            finish();
        }
        lvChecklist.setOnItemClickListener((adapterView, view, i, l) -> {
            startActivity(MainActivity.openIntent(ChecklistActivity.this,list.get(i).getId(),false));
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_checklist;
    }

    @Override
    protected void initDataAndEvent() {

    }


}
