package com.example.acceptance.fragment.main.kitting;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.acceptance.R;
import com.example.acceptance.adapter.kitting.ProductAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.net.URLS;
import com.example.acceptance.view.LinePathView;
import com.example.acceptance.view.MyListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 齐套性检查——产品齐套性检查
 */
public class KittingProductFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.lv_product)
    MyListView lvProduct;
    @BindView(R.id.iv_qianming)
    ImageView ivQianming;
    private PopupWindow popupWindow;

    private ProductAdapter productAdapter;
    private List<String> list=new ArrayList<>();

    @Override
    protected void initEventAndData() {

        for (int i = 0; i < 2; i++) {
            list.add("");
        }

        productAdapter=new ProductAdapter(getActivity(),list);
        lvProduct.setAdapter(productAdapter);

        ivQianming.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kitting_product;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_qianming:
                pathPopu();
                break;
        }
    }

    private LinePathView mPathView;
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
        popupWindow.showAtLocation(ivQianming, Gravity.TOP, 0, 80);

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
      String  path =  System.currentTimeMillis() + ".png";
        mBtnSave.setOnClickListener(v -> {
            if (mPathView.getTouched()) {
                try {
                    mPathView.save(URLS.SINGA + File.separator + path, true, 100);
                    Glide.with(getActivity())
                            .load(new File(URLS.SINGA + File.separator + path))
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(ivQianming);
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
