package com.example.namebattler_ver00;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBOperation {

    private String mTableName;
    private SQLiteOpenHelper mHelper;
    private SQLiteDatabase mDb;

    public DBOperation(String tableName, SQLiteOpenHelper helper) {
        this.mTableName = tableName;
        this.mHelper = helper;
    }

    public ArrayList<CharaConfig> readDB(String searchChara) {
        if(mDb == null) {
            mDb = mHelper.getReadableDatabase();
        }

        Cursor cursor = mDb.query(mTableName,
                new String[] {"NAME","JOB","HP","MP","STR","DEF","AGI","LUCK","CREATE_AT"},
                "NAME = ?",
                new String[]{searchChara},
                null,
                null,
                null
        );

        ArrayList<CharaConfig> charaList = new ArrayList<>();

        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++) {
            charaList.add(new CharaConfig(cursor.getString(0),
                    cursor.getString(1),Integer.parseInt(cursor.getString(2)),
                    Integer.parseInt(cursor.getString(3)),Integer.parseInt(cursor.getString(4)),
                    Integer.parseInt(cursor.getString(5)),Integer.parseInt(cursor.getString(6)),
                    Integer.parseInt(cursor.getString(7)),cursor.getString(8)));
        }
        cursor.close();

        return charaList;
    }

    public ArrayList<CharaConfig> readAllDB() {
        if(mDb == null) {
            mDb = mHelper.getReadableDatabase();
        }

        Cursor cursor = mDb.query(mTableName,
                new String[] {"NAME","JOB","HP","MP","STR","DEF","AGI","LUCK","CREATE_AT"},
                null,
                null,
                null,
                null,
                null
        );

        ArrayList<CharaConfig> charaList = new ArrayList<>();

        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++) {
            charaList.add(new CharaConfig(cursor.getString(0),
                    cursor.getString(1),Integer.parseInt(cursor.getString(2)),
                    Integer.parseInt(cursor.getString(3)),Integer.parseInt(cursor.getString(4)),
                    Integer.parseInt(cursor.getString(5)),Integer.parseInt(cursor.getString(6)),
                    Integer.parseInt(cursor.getString(7)),cursor.getString(8)));

            cursor.moveToNext();
        }
        cursor.close();

        return charaList;
    }

    public void insertDB(ArrayList<CharaConfig> insertChara) {
        if(mDb == null) {
            mDb = mHelper.getReadableDatabase();
        }

        ContentValues values = new ContentValues();
        values.put("NAME", insertChara.get(0).getName());
        values.put("JOB", insertChara.get(0).getJob());
        values.put("HP", String.valueOf(insertChara.get(0).getHp()));
        values.put("MP", String.valueOf(insertChara.get(0).getMp()));
        values.put("STR", String.valueOf(insertChara.get(0).getStr()));
        values.put("DEF", String.valueOf(insertChara.get(0).getDef()));
        values.put("AGI", String.valueOf(insertChara.get(0).getAgi()));
        values.put("LUCK", String.valueOf(insertChara.get(0).getLuck()));
        values.put("CREATE_AT", insertChara.get(0).getCreateAt());

        mDb.insert(mTableName,null,values);
    }

    public void deleteCharaDB(String charaName) {
        if(mDb == null) {
            mDb = mHelper.getReadableDatabase();
        }

        mDb.delete("CHARACTERS","NAME=?",new String[]{charaName});
    }

    public int getRecodeCount(String charaName) {
        if(mDb == null) {
            mDb = mHelper.getReadableDatabase();
        }

        int recodeCount = (int) DatabaseUtils.queryNumEntries(mDb, mTableName,"NAME = ?",new String[]{charaName});

        return recodeCount;
    }


}
