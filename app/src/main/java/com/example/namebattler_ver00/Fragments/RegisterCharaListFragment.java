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
import com.example.namebattler_ver00.DBMyOpenHelper;
import com.example.namebattler_ver00.DBOperation;
import com.example.namebattler_ver00.R;

import java.util.ArrayList;


public class RegisterCharaListFragment extends Fragment {

    public interface ICharaClickedListener {
        void onCharaClicked(String charaName);
    }

    private ICharaClickedListener _charaClickedListener;

    public static RegisterCharaListFragment newInstance() {
        RegisterCharaListFragment fragment = new RegisterCharaListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_register_chara_list, container, false);

        ArrayList<CharaConfig> registerCharaList = new ArrayList<>();
        final DBOperation dbMyOperation = new DBOperation("CHARACTERS",new DBMyOpenHelper(getContext()));

        registerCharaList = dbMyOperation.readAllDB();

        final RecyclerView recyclerView = view.findViewById(R.id.register_chara_list);
        recyclerView.setHasFixedSize(true);
        final RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(rLayoutManager);

        final RecyclerView.Adapter rAdapter = new CharaListAdapter(registerCharaList, _charaClickedListener);
        recyclerView.setAdapter(rAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ICharaClickedListener) {
            _charaClickedListener = (ICharaClickedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SampleCallback");
        }
    }
}