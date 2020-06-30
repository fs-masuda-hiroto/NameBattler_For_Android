package com.example.namebattler_ver00.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.namebattler_ver00.CharaConfig;
import com.example.namebattler_ver00.DBOperation;
import com.example.namebattler_ver00.DBTargetOpenHelper;
import com.example.namebattler_ver00.Fragments.HeaderFragment;
import com.example.namebattler_ver00.Fragments.RegisterCharaListFragment;
import com.example.namebattler_ver00.Fragments.SelectedMyPartyFragment;
import com.example.namebattler_ver00.Fragments.SelectedTargetPartyFragment;
import com.example.namebattler_ver00.GameSystem.Player;
import com.example.namebattler_ver00.R;
import com.example.namebattler_ver00.TargetCharaList;

import java.util.ArrayList;

public class SelectTargetPartyActivity extends AppCompatActivity implements RegisterCharaListFragment.ICharaClickedListener {

    private ArrayList<String> mMyParty = new ArrayList<>();
    private ArrayList<String> mTargetParty = new ArrayList<>();
    private TargetCharaList targetCharaList;
    private DBOperation mDbOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_select);

        Intent intent = getIntent();
        mMyParty = intent.getStringArrayListExtra("MY_PARTY");

        targetCharaList = new TargetCharaList();
        mDbOperation = new DBOperation("TARGET_CHARACTERS",
                new DBTargetOpenHelper(getApplicationContext()));
        if(mDbOperation.readAllDB().size() <= 0) {
            insertDBTargetChara();
        }

        mTargetParty = targetCharaList.genTargetParty();
        // 再挑戦を選択されたら、前回のバトルの敵キャラをセットする
        if(intent.getStringArrayListExtra("TARGET_PARTY") != null) {
            mTargetParty = intent.getStringArrayListExtra("TARGET_PARTY");
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.header, HeaderFragment.newInstance("バトル開始", new CreatePartyActivity()));
        ft.replace(R.id.selected_chara_list, SelectedMyPartyFragment.newInstance(mMyParty));
        ft.replace(R.id.selected_target_chara_list, SelectedTargetPartyFragment.newInstance(mTargetParty));
        ft.commit();

        Button decideTargetButton = findViewById(R.id.decide_target_button);
        decideTargetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), BattleActivity.class);
                intent.putStringArrayListExtra("MY_PARTY", mMyParty);
                intent.putStringArrayListExtra("TARGET_PARTY", mTargetParty);
                startActivity(intent);
            }
        });

        Button selectAgainTargetButton = findViewById(R.id.select_again_target_button);
        selectAgainTargetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), CreatePartyActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCharaClicked(String charaName) {
    }

    // 敵キャラを登録する用のメソッド
    private void insertDBTargetChara() {
        ArrayList<CharaConfig> targetChara = new ArrayList<>();
        ArrayList<Player> setTargetCharaList = new ArrayList<>();
        CreateCharaActivity createCharaActivity = new CreateCharaActivity();

        setTargetCharaList = targetCharaList.getTargetCharaList();

        for(int i = 0; i < setTargetCharaList.size(); i++) {

            String name = setTargetCharaList.get(i).getName();
            String job = setTargetCharaList.get(i).getJobName();
            int hp = setTargetCharaList.get(i).getHP();
            int mp = setTargetCharaList.get(i).getMP();
            int str = setTargetCharaList.get(i).getSTR();
            int def = setTargetCharaList.get(i).getDEF();
            int agi = setTargetCharaList.get(i).getAGI();
            int luck = setTargetCharaList.get(i).getLUCK();
            String createAt = createCharaActivity.getTime();

            targetChara.add(new CharaConfig(name,job,hp,mp,str,def,agi,luck,createAt));
            mDbOperation.insertDB(targetChara);

            targetChara.clear();
        }
    }
}