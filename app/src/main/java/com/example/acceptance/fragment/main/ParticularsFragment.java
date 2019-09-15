package com.example.acceptance.fragment.main;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.bean.DataPackageBean;
import com.example.acceptance.net.URLS;
import com.example.acceptance.utils.OpenFileUtil;
import com.thoughtworks.xstream.XStream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.BindView;

/**
 * 详情信息
 */
public class ParticularsFragment extends BaseFragment {

    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_creator)
    TextView tvCreator;
    @BindView(R.id.tv_responseUnit)
    TextView tvResponseUnit;
    @BindView(R.id.tv_productName)
    TextView tvProductName;
    @BindView(R.id.tv_modalCode)
    TextView tvModalCode;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_createTime)
    TextView tvCreateTime;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_productType)
    TextView tvProductType;
    @BindView(R.id.tv_batch)
    TextView tvBatch;
    private DataPackageBean result;

    @Override
    protected void initEventAndData() {
        input();
        tvCode.setText(result.getCode());
        tvResponseUnit.setText(result.getResponseUnit());
        tvProductName.setText(result.getProductName());
        tvModalCode.setText(result.getModalCode());
        tvName.setText(result.getName());
        tvCreateTime.setText(result.getCreateTime());
        tvType.setText(result.getType());
        tvProductType.setText(result.getProductType());
        tvBatch.setText(result.getBatch());


        tvCode.setOnClickListener(view -> {
            startActivity(OpenFileUtil.openFile(Environment.getExternalStorageDirectory() + "/数据包/P011/"+result.getPath()));

        });
    }

    private void input() {
        String content = ""; //文件内容字符串
        File file1 = new File(Environment.getExternalStorageDirectory() + "/数据包/P011/单机产品数据包.xml");
        try {
            InputStream instream = new FileInputStream(file1);
            InputStreamReader inputreader = new InputStreamReader(instream, "gbk");
            BufferedReader buffreader = new BufferedReader(inputreader);
            String line;
            //分行读取
            while ((line = buffreader.readLine()) != null) {
                content += line + "\n";
            }

            instream.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.e("TAG", "onActivityResult: " + content);
        XStream xStream = new XStream();
        xStream.processAnnotations(DataPackageBean.class);//这里需要注解是你自己根据xml写的bean类(下面附代码解释xml)
        result = (DataPackageBean) xStream.fromXML(content);

        Log.e("TAG", "onActivityResult: " + result.toString());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_particulars;
    }
}
