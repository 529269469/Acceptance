package com.example.acceptance.fragment.setup;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.adapter.PacketAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.utils.SPUtils;
import com.example.acceptance.utils.StringUtils;
import com.example.acceptance.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 检查结论
 */
public class ConclusionsFragment extends BaseFragment {

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
    private String type="ConclusionsType";
    @Override
    protected void initEventAndData() {
        tvType.setText("检查结论名称");
        tvType2.setText("检查结论名称");

        String strings= (String) SPUtils.get(getActivity(),type,"");
        String[] listlist=strings.split(",");
        if (!StringUtils.isBlank(strings)){
            for (int i = 0; i < listlist.length; i++) {
                list.add(listlist[i]);
            }
        }
        packetAdapter=new PacketAdapter(getActivity(),list,type);

        lvPacket.setAdapter(packetAdapter);

        tvAdd.setOnClickListener(view -> {
            if (StringUtils.isBlank(etType.getText().toString().trim())){
                ToastUtils.getInstance().showTextToast(getActivity(),"请输入");
            }else {
                list.add(etType.getText().toString().trim());
                packetAdapter.notifyDataSetChanged();
                etType.setText("");
                StringBuffer stringBuffer=new StringBuffer();
                for (int i = 0; i < list.size(); i++) {
                    stringBuffer.append(list.get(i)).append(",");
                }
                SPUtils.put(getActivity(),type,stringBuffer.toString().substring(0,stringBuffer.toString().length()-1));

            }

        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_packet;
    }
}
