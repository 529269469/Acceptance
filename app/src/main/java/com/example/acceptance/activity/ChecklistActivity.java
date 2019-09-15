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

    public static Intent openIntent(Context context) {
        Intent intent = new Intent(context, ChecklistActivity.class);
        return intent;
    }

    private ChecklistAdapter checklistAdapter;
    private List<String> list = new ArrayList<>();

    @Override
    protected void initView() {
        for (int i = 0; i < 2; i++) {
            list.add("");
        }
        checklistAdapter=new ChecklistAdapter(this,list);
        lvChecklist.setAdapter(checklistAdapter);

        lvChecklist.setOnItemClickListener((adapterView, view, i, l) -> {
            startActivity(MainActivity.openIntent(ChecklistActivity.this));
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
