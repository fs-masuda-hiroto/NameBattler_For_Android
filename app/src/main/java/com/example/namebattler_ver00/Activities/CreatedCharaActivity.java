package com.example.namebattler_ver00.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.namebattler_ver00.Fragments.CharaStatusFragment;
import com.example.namebattler_ver00.Fragments.HeaderFragment;
import com.example.namebattler_ver00.R;

public class CreatedCharaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_chara);

        Intent intent = getIntent();
        final String charaName = intent.getStringExtra("CHARA_NAME");

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.header, HeaderFragment.newInstance("キャラ詳細", new ShowCharaListActivity()));
        ft.replace(R.id.chara_status, CharaStatusFragment.newInstance(charaName));
        ft.commit();

        Button continueCreateCharaButton = findViewById(R.id.continue_create_chara_button);
        continueCreateCharaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), CreateCharaActivity.class);
                startActivity(intent);
            }
        });

        Button endCreateCharaButton = findViewById(R.id.end_create_chara_button);
        endCreateCharaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), ShowCharaListActivity.class);
                startActivity(intent);
            }
        });
    }
}