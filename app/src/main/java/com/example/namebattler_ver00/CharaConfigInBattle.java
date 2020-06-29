package com.example.namebattler_ver00;

import java.io.Serializable;

public class CharaConfigInBattle implements Serializable {

    private String mName;
    private int mHp;
    private int mMp;

    public CharaConfigInBattle(String name, int hp, int mp) {
        this.mName = name;
        this.mHp = hp;
        this.mMp = mp;
    }

    public String getName() {
        return mName;
    }

    public int getHp() {
        return mHp;
    }

    public int getMp() {
        return mMp;
    }
}
