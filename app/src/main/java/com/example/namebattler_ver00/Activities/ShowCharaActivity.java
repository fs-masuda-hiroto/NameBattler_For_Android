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

import com.example.namebattler_ver00.DBMyOpenHelper;
import com.example.namebattler_ver00.DBOperation;
import com.example.namebattler_ver00.Fragments.CharaStatusFragment;
import com.example.namebattler_ver00.Fragments.HeaderFragment;
import com.example.namebattler_ver00.R;

public class ShowCharaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_chara);

        Intent intent = getIntent();
        final String charaName = intent.getStringExtra("CHARA_NAME");

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.header, HeaderFragment.newInstance("キャラ詳細", new ShowCharaListActivity()));
        ft.replace(R.id.chara_status, CharaStatusFragment.newInstance(charaName));
        ft.commit();

        Button deleteCharaButton = findViewById(R.id.delete_chara_button);
        deleteCharaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDeleteButton(charaName);
            }
        });
    }

    private void onClickDeleteButton(final String charaName) {

        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(this);
        myAlertBuilder.setMessage("登録されているキャラを削除しますか？");

        myAlertBuilder.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        myAlertBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final DBOperation dbOperation = new DBOperation("CHARACTERS",
                        new DBMyOpenHelper(getApplicationContext()));

                dbOperation.deleteCharaDB(charaName);

                Intent intent = new Intent(getApplication(), ShowCharaListActivity.class);
                startActivity(intent);
            }
        });

        myAlertBuilder.show();
    }
}