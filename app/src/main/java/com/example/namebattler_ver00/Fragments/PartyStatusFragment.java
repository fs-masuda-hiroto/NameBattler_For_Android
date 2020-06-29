package com.example.namebattler_ver00.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.namebattler_ver00.CharaConfigInBattle;
import com.example.namebattler_ver00.R;

import java.util.ArrayList;


public class PartyStatusFragment extends Fragment {

    private CharaConfigInBattle mCharaStatus1;
    private CharaConfigInBattle mCharaStatus2;
    private CharaConfigInBattle mCharaStatus3;

    public static PartyStatusFragment newInstance(ArrayList<CharaConfigInBattle> charaStatus) {
        PartyStatusFragment fragment = new PartyStatusFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("CHARA_STATUS_1",charaStatus.get(0));
        bundle.putSerializable("CHARA_STATUS_2",charaStatus.get(1));
        bundle.putSerializable("CHARA_STATUS_3",charaStatus.get(2));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mCharaStatus1 = (CharaConfigInBattle) getArguments().getSerializable("CHARA_STATUS_1");
            mCharaStatus2 = (CharaConfigInBattle) getArguments().getSerializable("CHARA_STATUS_2");
            mCharaStatus3 = (CharaConfigInBattle) getArguments().getSerializable("CHARA_STATUS_3");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_chara_status_in_battle, container, false);

        final FragmentManager fm = getChildFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.selected_chara1, PartyStatusItemFragment.newInstance(mCharaStatus1));
        ft.replace(R.id.selected_chara2, PartyStatusItemFragment.newInstance(mCharaStatus2));
        ft.replace(R.id.selected_chara3, PartyStatusItemFragment.newInstance(mCharaStatus3));
        ft.commit();

        return view;
    }
}