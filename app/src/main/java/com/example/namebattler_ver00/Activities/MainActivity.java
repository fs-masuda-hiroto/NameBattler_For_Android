package com.example.namebattler_ver00.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.namebattler_ver00.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button showCharaListButton = findViewById(R.id.show_chara_list_button);
        showCharaListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), ShowCharaListActivity.class);
                startActivity(intent);
            }
        });

        Button startBattleButton = findViewById(R.id.start_battle_button);
        startBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), CreatePartyActivity.class);
                startActivity(intent);
            }
        });
    }
}