package com.example.acceptance.fragment.main;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.utils.PopupWindowUtils;

import butterknife.BindView;

/**
 * 验收申请
 */

public class ApplyForFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.tv_conclusion)
    TextView tvConclusion;
    @BindView(R.id.bt_up)
    Button btUp;
    @BindView(R.id.tv_other)
    TextView tvOther;

    @Override
    protected void initEventAndData() {
        btUp.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_apply_for;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_up:
                PopupWindowUtils.getInstance().getPopup(getActivity(),1);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
