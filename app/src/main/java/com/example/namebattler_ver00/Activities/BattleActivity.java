package com.example.namebattler_ver00.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.namebattler_ver00.CharaConfig;
import com.example.namebattler_ver00.CharaConfigInBattle;
import com.example.namebattler_ver00.DBMyOpenHelper;
import com.example.namebattler_ver00.DBOperation;
import com.example.namebattler_ver00.DBTargetOpenHelper;
import com.example.namebattler_ver00.Fragments.PartyStatusFragment;
import com.example.namebattler_ver00.Fragments.HeaderFragment;
import com.example.namebattler_ver00.GameSystem.Fighter;
import com.example.namebattler_ver00.GameSystem.GameManager;
import com.example.namebattler_ver00.GameSystem.Guardian;
import com.example.namebattler_ver00.GameSystem.Party;
import com.example.namebattler_ver00.GameSystem.Player;
import com.example.namebattler_ver00.GameSystem.Priest;
import com.example.namebattler_ver00.GameSystem.Wizard;
import com.example.namebattler_ver00.R;

import java.util.ArrayList;

public class BattleActivity extends AppCompatActivity {

    private String mLog = "";
    private String mSelectStrategyIndex = "";
    private TextView mLogText;
    private LinearLayout mSelectStrategy;
    private ScrollView mLogScrollView;
    private ArrayList<String> mMyParty = new ArrayList<>();
    private ArrayList<String> mTargetParty = new ArrayList<>();
    private ArrayList<CharaConfigInBattle> mMyPartyStatus = new ArrayList<>();
    private ArrayList<CharaConfigInBattle> mTargetPartyStatus = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        mLogText = findViewById(R.id.log);
        mSelectStrategy = findViewById(R.id.select_strategy);
        mLogScrollView = findViewById(R.id.logScrollView);

        Intent intent = getIntent();
        mMyParty = intent.getStringArrayListExtra("MY_PARTY");
        mTargetParty = intent.getStringArrayListExtra("TARGET_PARTY");

        final DBOperation myDbOperation = new DBOperation("CHARACTERS",
                new DBMyOpenHelper(getApplicationContext()));
        final DBOperation targetDbOperation = new DBOperation("TARGET_CHARACTERS",
                new DBTargetOpenHelper(getApplicationContext()));

        final Party myParty = setParty(mMyParty,myDbOperation);
        final Party targetParty = setParty(mTargetParty,targetDbOperation);
        final GameManager gameManager = new GameManager(myParty,targetParty);

        setInitialPartyStatus(myParty, mMyPartyStatus);
        setInitialPartyStatus(targetParty, mTargetPartyStatus);

        final FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.header, HeaderFragment.newInstance("バトル", new CreatePartyActivity()));
        ft.replace(R.id.selected_chara_list, PartyStatusFragment.newInstance(mMyPartyStatus));
        ft.replace(R.id.selected_target_chara_list, PartyStatusFragment.newInstance(mTargetPartyStatus));
        ft.commit();

        // バトル開始前ログ
        mLog = gameManager.startBattleLog();
        setLogText(mLog);

        Button nextTurnButton = findViewById(R.id.next_turn_button);
        nextTurnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int turnNum = 0;
                // 20ターンで終了 or どちらかのパーティ全滅で、画面遷移
                mLog += gameManager.battle(turnNum, mSelectStrategyIndex);

                // キャラステータス更新
                final FragmentTransaction ftForUpdate = fm.beginTransaction();
                ftForUpdate.replace(R.id.selected_chara_list, PartyStatusFragment.newInstance(
                        setUpdatePartyStatus(myParty, mMyPartyStatus)));
                ftForUpdate.replace(R.id.selected_target_chara_list, PartyStatusFragment.newInstance(
                        setUpdatePartyStatus(targetParty, mTargetPartyStatus)));
                ftForUpdate.commit();

                if(turnNum != 20) {
                    if(mLog.contains("バトル終了")) {
                        moveActivity();
                    }
                    turnNum++;
                    setLogText(mLog);
                } else {
                    moveActivity();
                }
            }
        });

        // 変更ボタンが押された時の処理
        Button changeStrategyButton = findViewById(R.id.change_strategy_button);
        changeStrategyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectStrategy.setVisibility(View.VISIBLE);
            }
        });

        // 決定ボタンが押され時の処理
        Button decideButton = findViewById(R.id.decide_button);
        decideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup strategyGroup = findViewById(R.id.strategy_group);
                int checkedId = strategyGroup.getCheckedRadioButtonId();
                RadioButton checkedStrategyRadio = findViewById(checkedId);
                mSelectStrategyIndex = checkedStrategyRadio.getText().toString();

                mSelectStrategy.setVisibility(View.GONE);
            }
        });
    }


    private void setLogText(String turnAction) {
        mLogText.setText(turnAction);

        // スクロールビューの一番下まで移動
        mLogScrollView.post(new Runnable() {
            @Override
            public void run() {
                mLogScrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    private Party setParty(ArrayList<String> selectedChara, DBOperation dbOperation) {

        Party party = new Party();

        for(int i = 0; i < selectedChara.size(); i++) {
            ArrayList<CharaConfig> charaConfig = dbOperation.readDB(selectedChara.get(i));
            String jobName = charaConfig.get(0).getJob();

            Player player = null;

            if(jobName.equals("戦士")) {
                player = new Fighter(selectedChara.get(i));
            } else if(jobName.equals("魔法使い")) {
                player = new Wizard(selectedChara.get(i));
            } else if(jobName.equals("僧侶")) {
                player = new Priest(selectedChara.get(i));
            } else if(jobName.equals("ガーディアン")) {
                player = new Guardian(selectedChara.get(i));
            }

            party.appendPlayer(player);
        }
        return party;
    }

    private void setInitialPartyStatus(Party party, ArrayList<CharaConfigInBattle> partyStatus) {
        for(int i = 0; i < party.getMembers().size(); i++) {
            String name = party.getMembers().get(i).getName();
            int hp = party.getMembers().get(i).getHP();
            int mp = party.getMembers().get(i).getMP();
            partyStatus.add(new CharaConfigInBattle(name,hp,mp));
        }
    }

    private ArrayList<CharaConfigInBattle> setUpdatePartyStatus(Party party,ArrayList<CharaConfigInBattle> charaStatusList) {
        for(int i = 0; i < charaStatusList.size(); i++) {
            String name = charaStatusList.get(i).getName();
            charaStatusList.set(i, new CharaConfigInBattle(name, 0, 0));

            for(int j = 0; j < party.getMembers().size(); j++) {
                if(name.contains(party.getMembers().get(j).getName())) {
                    int hp = party.getMembers().get(j).getHP();
                    int mp = party.getMembers().get(j).getMP();
                    charaStatusList.set(i, new CharaConfigInBattle(name, hp, mp));
                }
            }
        }

        return charaStatusList;
    }

    private void moveActivity() {
        Intent intent = new Intent(getApplication(), BattleResultActivity.class);
        intent.putStringArrayListExtra("MY_PARTY", mMyParty);
        intent.putStringArrayListExtra("TARGET_PARTY", mTargetParty);
        intent.putExtra("RESULT_MSG",setJudgeResult(mLog));
        for(int i = 0; i < 3; i++) {
            intent.putExtra("MY_PARTY_STATUS_" + (i + 1), mMyPartyStatus.get(i));
            intent.putExtra("TARGET_PARTY_STATUS_" + (i + 1), mTargetPartyStatus.get(i));
        }
        startActivity(intent);
    }

    private String setJudgeResult(String log) {
        String resultMsg = "";

        if(log.contains("パーティ1の勝利")) {
            resultMsg = "You Win!";
        } else if(log.contains("パーティ2の勝利")){
            resultMsg = "You Lose... \n\nNext?";
        } else {
            resultMsg = "Draw... \n\nNext?";
        }

        return resultMsg;
    }
}