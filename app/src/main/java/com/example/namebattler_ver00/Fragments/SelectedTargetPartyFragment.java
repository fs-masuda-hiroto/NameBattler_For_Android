package com.example.namebattler_ver00.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.namebattler_ver00.CharaConfig;
import com.example.namebattler_ver00.CharaListAdapter;
import com.example.namebattler_ver00.DBOperation;
import com.example.namebattler_ver00.DBTargetOpenHelper;
import com.example.namebattler_ver00.R;

import java.util.ArrayList;


public class SelectedTargetPartyFragment extends Fragment {

    private static ArrayList<String> mTargetParty = new ArrayList<>();
    private String mName;
    private String mJob;
    private int mHp;
    private int mMp;
    private int mStr;
    private int mDef;
    private int mAgi;
    private int mLuck;
    private String mCreateAt;
    private RegisterCharaListFragment.ICharaClickedListener _charaClickedListener;

    public static SelectedTargetPartyFragment newInstance(ArrayList<String> targetParty) {
        SelectedTargetPartyFragment fragment = new SelectedTargetPartyFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("TARGET_PARTY",targetParty);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mTargetParty = getArguments().getStringArrayList("TARGET_PARTY");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_selected_target_chara_list, container, false);

        ArrayList<CharaConfig> selectedTargetCharaList = new ArrayList<>();
        final DBOperation dbTargetOperation = new DBOperation("TARGET_CHARACTERS",new DBTargetOpenHelper(getContext()));

        for(int i = 0; i < mTargetParty.size(); i++) {
            setStatus(dbTargetOperation.readDB(mTargetParty.get(i)));
            selectedTargetCharaList.add(new CharaConfig(mName,mJob,mHp,mMp,mStr,mDef,mAgi,mLuck,mCreateAt));
        }

        final RecyclerView recyclerView = view.findViewById(R.id.selected_chara_list);
        recyclerView.setHasFixedSize(true);
        final RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(rLayoutManager);

        final RecyclerView.Adapter rAdapter = new CharaListAdapter(selectedTargetCharaList, _charaClickedListener);
        recyclerView.setAdapter(rAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RegisterCharaListFragment.ICharaClickedListener) {
            _charaClickedListener = (RegisterCharaListFragment.ICharaClickedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SampleCallback");
        }
    }

    private void setStatus(ArrayList<CharaConfig> selectedChara) {
        mName = selectedChara.get(0).getName();
        mJob = selectedChara.get(0).getJob();
        mHp = selectedChara.get(0).getHp();
        mMp = selectedChara.get(0).getMp();
        mStr = selectedChara.get(0).getStr();
        mDef = selectedChara.get(0).getMp();
        mAgi = selectedChara.get(0).getAgi();
        mLuck = selectedChara.get(0).getLuck();
        mCreateAt = selectedChara.get(0).getCreateAt();
    }
}