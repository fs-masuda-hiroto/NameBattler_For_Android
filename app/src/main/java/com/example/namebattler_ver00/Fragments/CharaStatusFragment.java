package com.example.namebattler_ver00.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.namebattler_ver00.CharaConfig;
import com.example.namebattler_ver00.DBMyOpenHelper;
import com.example.namebattler_ver00.DBOperation;
import com.example.namebattler_ver00.R;

import java.util.ArrayList;


public class CharaStatusFragment extends Fragment {

    private String mCharaName;

    public static CharaStatusFragment newInstance(String charaName) {
        CharaStatusFragment fragment = new CharaStatusFragment();
        Bundle bundle = new Bundle();
        bundle.putString("CHARA_NAME", charaName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mCharaName = getArguments().getString("CHARA_NAME");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_chara_status, container, false);

        final TextView charaNameText = view.findViewById(R.id.chara_name);
        final TextView charaJobText = view.findViewById(R.id.chara_job);
        final TextView charaHpText = view.findViewById(R.id.chara_hp);
        final TextView charaMpText = view.findViewById(R.id.chara_mp);
        final TextView charaStrText = view.findViewById(R.id.chara_str);
        final TextView charaDefText = view.findViewById(R.id.chara_def);
        final TextView charaAgiText = view.findViewById(R.id.chara_agi);
        final TextView charaLuckText = view.findViewById(R.id.chara_luck);
        final TextView charaCreateAtText = view.findViewById(R.id.chara_create_at);

        final DBOperation dbMyOperation = new DBOperation("CHARACTERS",new DBMyOpenHelper(getContext()));
        ArrayList<CharaConfig> charaStatus = dbMyOperation.readDB(mCharaName);

        charaNameText.setText(charaStatus.get(0).getName());
        charaJobText.setText(charaStatus.get(0).getJob());
        charaHpText.setText("HP：" + charaStatus.get(0).getHp());
        charaMpText.setText("MP：" + charaStatus.get(0).getMp());
        charaStrText.setText("STR：" + charaStatus.get(0).getStr());
        charaDefText.setText("DEF：" + charaStatus.get(0).getDef());
        charaAgiText.setText("AGI：" + charaStatus.get(0).getAgi());
        charaLuckText.setText("LUCK：" + charaStatus.get(0).getLuck());
        charaCreateAtText.setText("作成日：" + charaStatus.get(0).getCreateAt());

        return view;
    }
}