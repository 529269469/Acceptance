package com.example.acceptance.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewActivity extends BaseActivity {

    @BindView(R.id.iv_genduo)
    ImageView ivGenduo;
    @BindView(R.id.tv_tuichu)
    TextView tvTuichu;
    @BindView(R.id.bt_yes)
    TextView btYes;
    @BindView(R.id.bt_no)
    TextView btNo;

    public static Intent openIntent(Context context) {
        Intent intent = new Intent(context, NewActivity.class);
        return intent;
    }

    @Override
    protected void initView() {
        btYes.setOnClickListener(view -> {
            startActivity(MainActivity.openIntent(NewActivity.this,""));
            finish();
        });
        btNo.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new;
    }

    @Override
    protected void initDataAndEvent() {

    }


}
