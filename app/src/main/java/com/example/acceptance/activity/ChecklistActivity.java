package com.example.acceptance.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.acceptance.R;
import com.example.acceptance.base.BaseActivity;

/**
 * 历史任务
 */
public class ChecklistActivity extends BaseActivity {

    public static Intent openIntent(Context context) {
        Intent intent = new Intent(context, ChecklistActivity.class);
        return intent;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_checklist;
    }

    @Override
    protected void initDataAndEvent() {

    }
}
