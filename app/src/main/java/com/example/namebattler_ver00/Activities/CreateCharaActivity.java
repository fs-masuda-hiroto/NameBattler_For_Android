package com.example.namebattler_ver00.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.namebattler_ver00.CharaConfig;
import com.example.namebattler_ver00.DBMyOpenHelper;
import com.example.namebattler_ver00.DBOperation;
import com.example.namebattler_ver00.Fragments.HeaderFragment;
import com.example.namebattler_ver00.GameSystem.Fighter;
import com.example.namebattler_ver00.GameSystem.Guardian;
import com.example.namebattler_ver00.GameSystem.Player;
import com.example.namebattler_ver00.GameSystem.Priest;
import com.example.namebattler_ver00.GameSystem.Wizard;
import com.example.namebattler_ver00.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CreateCharaActivity extends AppCompatActivity {

    private DBOperation dbOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chara);

        dbOperation = new DBOperation("CHARACTERS", new DBMyOpenHelper(getApplicationContext()));

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.header, HeaderFragment.newInstance("キャラ作成", new ShowCharaListActivity()));
        ft.commit();

        final EditText inputNameText = findViewById(R.id.input_name);
        final RadioGroup jobGroup = findViewById(R.id.job_list);

        Button createCharaButton = findViewById(R.id.create_chara_button);
        createCharaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName = inputNameText.getText().toString();

                int checkedId = jobGroup.getCheckedRadioButtonId();
                RadioButton checkedJobRadio = findViewById(checkedId);
                String checkedJob = checkedJobRadio.getText().toString();

                // 登録されている名前が存在するか判定
                if(dbOperation.getRecodeCount(inputName) > 0) {
                    alertRegisterAlready();
                } else {
                    registerCharaToDB(createChara(inputName, checkedJob));
                    Intent intent = new Intent(getApplication(), CreatedCharaActivity.class);
                    intent.putExtra("CHARA_NAME",inputName);
                    startActivity(intent);
                }
            }
        });
    }

    private void alertRegisterAlready() {
        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(this);
        myAlertBuilder.setMessage("既に登録されているキャラです");

        myAlertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        myAlertBuilder.show();
    }

    private Player createChara(String charaName, String jobName) {
        Player player = null;

        if(jobName.equals("戦士")) {
            player = new Fighter(charaName);
        } else if(jobName.equals("魔法使い")) {
            player = new Wizard(charaName);
        } else if(jobName.equals("僧侶")) {
            player = new Priest(charaName);
        } else if(jobName.equals("ガーディアン")) {
            player = new Guardian(charaName);
        }

        return player;
    }

    private void registerCharaToDB(Player player) {
        final String name = player.getName();
        final String job = player.getJobName();
        final int hp = player.getHP();
        final int mp = player.getMP();
        final int str = player.getSTR();
        final int def = player.getDEF();
        final int agi = player.getAGI();
        final int luck = player.getLUCK();
        ArrayList<CharaConfig> charaStatus = new ArrayList<>();

        charaStatus.add(new CharaConfig(name, job, hp, mp, str, def, agi, luck, getTime()));
        dbOperation.insertDB(charaStatus);
    }

    public String getTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
        return sdf.format(date);
    }
}