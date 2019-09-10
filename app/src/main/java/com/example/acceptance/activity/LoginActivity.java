package com.example.acceptance.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.acceptance.R;
import com.example.acceptance.base.BaseActivity;
import com.example.acceptance.view.ChangeTextViewSpace;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 登录页
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.ll_new)
    LinearLayout llNew;
    @BindView(R.id.ll_file)
    LinearLayout llFile;
    @BindView(R.id.ll_checklist)
    LinearLayout llChecklist;
    @BindView(R.id.iv_setup)
    ImageView ivSetup;


    @Override
    protected void initView() {

        llNew.setOnClickListener(this);
        llFile.setOnClickListener(this);
        llChecklist.setOnClickListener(this);
        ivSetup.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initDataAndEvent() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_new:
                startActivity(MainActivity.openIntent(LoginActivity.this));
                break;
            case R.id.ll_file:
                break;
            case R.id.ll_checklist:
                startActivity(ChecklistActivity.openIntent(LoginActivity.this));
                break;
            case R.id.iv_setup:
                startActivity(SetupActivity.openIntent(LoginActivity.this));
                break;
        }
    }


}
