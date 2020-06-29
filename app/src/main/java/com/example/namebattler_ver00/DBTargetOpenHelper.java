package com.example.namebattler_ver00;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBTargetOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "NameBattlerTargetDB.db";
    private static final String TABLE_NAME = "TARGET_CHARACTERS";
    private static final String NAME = "NAME TEXT(20) PRIMARY KEY NOT NULL DEFAULT ' ',";
    private static final String JOB = "JOB TEXT NOT NULL DEFAULT ' ',";
    private static final String HP = "HP INTEGER NOT NULL DEFAULT 0,";
    private static final String MP = "MP INTEGER NOT NULL DEFAULT 0,";
    private static final String STR = "STR INTEGER NOT NULL DEFAULT 0,";
    private static final String DEF = "DEF INTEGER NOT NULL DEFAULT 0,";
    private static final String AGI = "AGI INTEGER NOT NULL DEFAULT 0,";
    private static final String LUCK = "LUCK INTEGER NOT NULL DEFAULT 0,";
    private static final String CREATE_AT = "CREATE_AT TEXT DEFAULT NULL";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" + NAME + JOB + HP + MP + STR + DEF + AGI
                    + LUCK + CREATE_AT + ")";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DBTargetOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // テーブル作成
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}


