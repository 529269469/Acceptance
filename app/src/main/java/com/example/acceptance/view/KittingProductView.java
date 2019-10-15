package com.example.acceptance.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.acceptance.R;

/**
 * @author :created by ${ WYW }
 * 时间：2019/10/15 22
 */
public class KittingProductView extends LinearLayout {


    public KittingProductView(Context context) {
        super(context);
        View view= LayoutInflater.from(context).inflate(R.layout.fragment_kitting_product2,null);
    }
}
