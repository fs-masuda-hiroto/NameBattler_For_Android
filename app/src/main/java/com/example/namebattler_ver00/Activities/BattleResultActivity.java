package com.example.namebattler_ver00.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.namebattler_ver00.CharaConfigInBattle;
import com.example.namebattler_ver00.Fragments.PartyStatusFragment;
import com.example.namebattler_ver00.GameSystem.Party;
import com.example.namebattler_ver00.R;

import java.util.ArrayList;

public class BattleResultActivity extends AppCompatActivity {

    private String mResultMsg = "";
    private ArrayList<String> mMyParty = new ArrayList<>();
    private ArrayList<String> mTargetParty = new ArrayList<>();
    private ArrayList<CharaConfigInBattle> mMyPartyStatus = new ArrayList<>();
    private ArrayList<CharaConfigInBattle> mTargetPartyStatus = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_result);

        Intent intent = getIntent();
        mResultMsg = intent.getStringExtra("RESULT_MSG");
        mMyParty = intent.getStringArrayListExtra("MY_PARTY");
        mTargetParty = intent.getStringArrayListExtra("TARGET_PARTY");

        for(int i = 1; i <= 3; i++) {
            mMyPartyStatus.add((CharaConfigInBattle) intent.getSerializableExtra("MY_PARTY_STATUS_" + i));
            mTargetPartyStatus.add((CharaConfigInBattle) intent.getSerializableExtra("TARGET_PARTY_STATUS_" + i));
        }

        final FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.selected_chara_list, PartyStatusFragment.newInstance(mMyPartyStatus));
        ft.replace(R.id.selected_target_chara_list, PartyStatusFragment.newInstance(mTargetPartyStatus));
        ft.commit();

        final TextView result = findViewById(R.id.result);
        result.setText(mResultMsg);

        // 再挑戦
        Button challengeAgainButton = findViewById(R.id.challenge_again_button);
        challengeAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), SelectTargetPartyActivity.class);
                intent.putStringArrayListExtra("MY_PARTY", mMyParty);
                intent.putStringArrayListExtra("TARGET_PARTY", mTargetParty);
                startActivity(intent);
            }
        });

        // 次の対戦へ
        Button nextBattleButton = findViewById(R.id.next_battle_button);
        nextBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplication(), SelectTargetPartyActivity.class);
                intent.putStringArrayListExtra("MY_PARTY", mMyParty);
                startActivity(intent);
            }
        });

        // 対戦を終了する
        Button endBattleButton = findViewById(R.id.end_battle_button);
        endBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}