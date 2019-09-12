package com.example.acceptance.fragment.setup;

import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.adapter.PacketAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.utils.StringUtils;
import com.example.acceptance.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 文件类别
 */
public class FileFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.et_type)
    EditText etType;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.tv_type2)
    TextView tvType2;
    @BindView(R.id.lv_packet)
    ListView lvPacket;
    @BindView(R.id.tv_type1)
    TextView tvType1;
    @BindView(R.id.et_type2)
    TextView etType2;
    @BindView(R.id.tv_add2)
    TextView tvAdd2;
    @BindView(R.id.tv_type22)
    TextView tvType22;
    @BindView(R.id.lv_packet2)
    ListView lvPacket2;
    @BindView(R.id.lv_file)
    ListView lvFile;

    private PacketAdapter packetAdapter;
    private List<String> list = new ArrayList<>();

    @Override
    protected void initEventAndData() {


        packetAdapter = new PacketAdapter(getActivity(), list);
        lvPacket.setAdapter(packetAdapter);

        tvAdd.setOnClickListener(this);
        tvAdd2.setOnClickListener(this);
        etType2.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_file;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                if (StringUtils.isBlank(etType.getText().toString().trim())) {
                    ToastUtils.getInstance().showTextToast(getActivity(), "请输入");
                } else {
                    list.add(etType.getText().toString().trim());
                    packetAdapter.notifyDataSetChanged();
                    etType.setText("");
                }
                break;
            case R.id.tv_add2:

                break;
        }
    }
}
