package com.example.namebattler_ver00.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.namebattler_ver00.Fragments.HeaderFragment;
import com.example.namebattler_ver00.Fragments.RegisterCharaListFragment;
import com.example.namebattler_ver00.R;

import java.util.ArrayList;

public class CreatePartyActivity extends AppCompatActivity implements RegisterCharaListFragment.ICharaClickedListener{

    private TextView mSelectCharaCountText;
    private TextView mSelectedChara1;
    private TextView mSelectedChara2;
    private TextView mSelectedChara3;
    private ArrayList<String> mMyParty = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.header, HeaderFragment.newInstance("パーティ編成", new MainActivity()));
        ft.replace(R.id.chara_list, RegisterCharaListFragment.newInstance());
        ft.commit();

        mSelectCharaCountText = findViewById(R.id.select_chara_count);
        mSelectedChara1 = findViewById(R.id.selected_chara_1);
        mSelectedChara2 = findViewById(R.id.selected_chara_2);
        mSelectedChara3 = findViewById(R.id.selected_chara_3);

        Button selectedPartyButton = findViewById(R.id.selected_party_button);
        selectedPartyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMyParty.size() == 3) {
                    Intent intent = new Intent(getApplication(), SelectTargetPartyActivity.class);
                    intent.putStringArrayListExtra("MY_PARTY", mMyParty);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplication(),"キャラが3体選択されていません",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onCharaClicked(String charaName) {
        if(mMyParty.contains(charaName)) {
            hideSelectedChara(charaName);
        }else if(!mMyParty.contains(charaName) && mMyParty.size() < 3) {
            mMyParty.add(charaName);
            mSelectCharaCountText.setText("(" + mMyParty.size() + "/3)");
            showSelectedChara(charaName);
        } else {
            Toast.makeText(getApplication(),"キャラを3体選択済みです",Toast.LENGTH_SHORT).show();
        }
    }

    private void hideSelectedChara(String selectedCharaName) {
        if(mMyParty.contains(selectedCharaName)) {
            if(mSelectedChara1.getText().toString().equals(selectedCharaName)) {
                mSelectedChara1.setText("");
            } else if(mSelectedChara2.getText().toString().equals(selectedCharaName)) {
                mSelectedChara2.setText("");
            } else {
                mSelectedChara3.setText("");
            }

            mMyParty.remove(selectedCharaName);
        }
    }

    private void showSelectedChara(String selectedCharaName) {
        if(mSelectedChara1.getText().toString().equals("")) {
            mSelectedChara1.setText(selectedCharaName);
        } else if(mSelectedChara2.getText().toString().equals("")) {
            mSelectedChara2.setText(selectedCharaName);
        } else {
            mSelectedChara3.setText(selectedCharaName);
        }
    }
}