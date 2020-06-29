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

import com.example.namebattler_ver00.CharaListAdapter;
import com.example.namebattler_ver00.Fragments.HeaderFragment;
import com.example.namebattler_ver00.Fragments.RegisterCharaListFragment;
import com.example.namebattler_ver00.R;

import java.util.ArrayList;

public class ShowCharaListActivity extends AppCompatActivity implements RegisterCharaListFragment.ICharaClickedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_chara_list);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.header, HeaderFragment.newInstance("キャラ一覧", new MainActivity()));
        ft.replace(R.id.chara_list, RegisterCharaListFragment.newInstance());
        ft.commit();

        Button createCharaButton = findViewById(R.id.create_chara_button);
        createCharaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), CreateCharaActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCharaClicked(final String charaName) {
        Intent intent = new Intent(getApplication(),ShowCharaActivity.class);
        intent.putExtra("CHARA_NAME",charaName);
        startActivity(intent);
    }
}