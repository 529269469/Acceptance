package com.example.acceptance.fragment.main;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.acceptance.R;
import com.example.acceptance.adapter.UnresolvedAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.CheckVerdBean;
import com.example.acceptance.greendao.bean.UnresolvedBean;
import com.example.acceptance.greendao.db.CheckVerdBeanDao;
import com.example.acceptance.greendao.db.UnresolvedBeanDao;
import com.example.acceptance.net.URLS;
import com.example.acceptance.view.LinePathView;
import com.example.acceptance.view.MyListView;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;

/**
 * 验收结论
 */

public class AcceptanceConclusionFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.lv_list1)
    MyListView lvList1;
    @BindView(R.id.lv_list2)
    MyListView lvList2;
    @BindView(R.id.iv_signature)
    ImageView ivSignature;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_qConclusion)
    EditText etQConclusion;
    @BindView(R.id.et_gConclusion)
    EditText etGConclusion;
    @BindView(R.id.et_jConclusion)
    EditText etJConclusion;
    @BindView(R.id.tv_time)
    TextView tvTime;
    private PopupWindow popupWindow;
    private LinePathView mPathView;

    @Override
    protected void initEventAndData() {
        String id = getArguments().getString("id");
        ivSignature.setOnClickListener(this);

        CheckVerdBeanDao checkVerdBeanDao= MyApplication.getInstances().getCheckVerdDaoSession().getCheckVerdBeanDao();
        List<CheckVerdBean> checkVerdBeans=checkVerdBeanDao.queryBuilder()
                .where(CheckVerdBeanDao.Properties.DataPackageId.eq(id))
                .list();

        tvCode.setText("编号："+checkVerdBeans.get(0).getCode());
        tvName.setText("名称："+checkVerdBeans.get(0).getName());
        etQConclusion.setText(checkVerdBeans.get(0).getQConclusion());
        etGConclusion.setText(checkVerdBeans.get(0).getQConclusion());
        etJConclusion.setText(checkVerdBeans.get(0).getQConclusion());


        UnresolvedBeanDao unresolvedBeanDao=MyApplication.getInstances().getCheckUnresolvedDaoSession().getUnresolvedBeanDao();
        List<UnresolvedBean> unresolvedBeans=unresolvedBeanDao.queryBuilder()
                .where(UnresolvedBeanDao.Properties.DataPackageId.eq(id))
                .list();

        UnresolvedAdapter unresolvedAdapter=new UnresolvedAdapter(getActivity(),unresolvedBeans);
        lvList2.setAdapter(unresolvedAdapter);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_acceptance_conclusion;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_signature:
                pathPopu();
                break;

        }
    }

    private void pathPopu() {
        View poview = getLayoutInflater().inflate(R.layout.path_view, null);
        popupWindow = new PopupWindow(poview);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;
        getActivity().getWindow().setAttributes(lp);
        popupWindow.showAtLocation(ivSignature, Gravity.TOP, 0, 80);

        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = getActivity().getWindow().getAttributes();
            lp1.alpha = 1f;
            getActivity().getWindow().setAttributes(lp1);
        });

        mPathView = poview.findViewById(R.id.path_view);
        Button mBtnClear = poview.findViewById(R.id.m_btn_clear);
        Button mBtnSave = poview.findViewById(R.id.m_btn_save);

        //修改背景、笔宽、颜色
        mPathView.setBackColor(Color.WHITE);
        mPathView.setPaintWidth(10);
        mPathView.setPenColor(Color.BLACK);
        //清除
        mBtnClear.setOnClickListener(v -> {
            mPathView.clear();
            mPathView.setBackColor(Color.WHITE);
            mPathView.setPaintWidth(10);
            mPathView.setPenColor(Color.BLACK);
        });
        //保存
        String path = System.currentTimeMillis() + ".png";
        mBtnSave.setOnClickListener(v -> {
            if (mPathView.getTouched()) {
                try {
                    mPathView.save(URLS.SINGA + File.separator + path, true, 10);
                    Glide.with(getActivity())
                            .load(new File(URLS.SINGA + File.separator + path))
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(ivSignature);
                    Toast.makeText(getActivity(), "签名成功~", Toast.LENGTH_SHORT).show();
                    popupWindow.dismiss();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getActivity(), "您没有签名~", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
