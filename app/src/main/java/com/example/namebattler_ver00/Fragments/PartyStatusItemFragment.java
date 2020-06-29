package com.example.namebattler_ver00.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.namebattler_ver00.CharaConfigInBattle;
import com.example.namebattler_ver00.R;


public class PartyStatusItemFragment extends Fragment {

    private String mName;
    private int mHp;
    private int mMp;

    public static PartyStatusItemFragment newInstance(CharaConfigInBattle charaStatus) {
        PartyStatusItemFragment fragment = new PartyStatusItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name",charaStatus.getName());
        bundle.putInt("hp",charaStatus.getHp());
        bundle.putInt("mp",charaStatus.getMp());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mName = getArguments().getString("name");
            mHp = getArguments().getInt("hp");
            mMp = getArguments().getInt("mp");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_chara_status_in_battle_item, container, false);

        final TextView charaNameText = view.findViewById(R.id.chara_name);
        final TextView charaHpText = view.findViewById(R.id.chara_hp);
        final TextView charaMpText = view.findViewById(R.id.chara_mp);

        charaNameText.setText(mName);
        charaHpText.setText("HP:" + mHp);
        charaMpText.setText("MP" + mMp);

        return view;
    }
}