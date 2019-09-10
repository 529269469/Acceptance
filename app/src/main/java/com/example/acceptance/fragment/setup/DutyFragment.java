package com.example.acceptance.fragment.setup;

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
 * 责任单位
 */
public class DutyFragment extends BaseFragment {

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

    private PacketAdapter packetAdapter;
    private List<String> list=new ArrayList<>();
    @Override
    protected void initEventAndData() {
        tvType.setText("责任单位名称");
        tvType2.setText("责任单位名称");


        packetAdapter=new PacketAdapter(getActivity(),list);
        lvPacket.setAdapter(packetAdapter);

        tvAdd.setOnClickListener(view -> {
            if (StringUtils.isBlank(etType.getText().toString().trim())){
                ToastUtils.getInstance().showTextToast(getActivity(),"请输入");
            }else {
                list.add(etType.getText().toString().trim());
                packetAdapter.notifyDataSetChanged();
                etType.setText("");
            }

        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_packet;
    }
}
