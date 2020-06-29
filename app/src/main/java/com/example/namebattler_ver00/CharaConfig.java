package com.example.namebattler_ver00;

public class CharaConfig {

    private String mName;
    private String mJob;
    private int mHp;
    private int mMp;
    private int mStr;
    private int mDef;
    private int mAgi;
    private int mLuck;
    private String mCreateAt;

    public CharaConfig(String name, String job, int hp, int mp, int str, int def, int agi, int luck, String createAt) {
        this.mName = name;
        this.mJob = job;
        this.mHp = hp;
        this.mMp = mp;
        this.mStr = str;
        this.mDef = def;
        this.mAgi = agi;
        this.mLuck = luck;
        this.mCreateAt = createAt;
    }

    // Getter
    public String getName() {
        return this.mName;
    }

    public String getJob() {
        return this.mJob;
    }

    public int getHp() {
        return this.mHp;
    }

    public int getMp() {
        return this.mMp;
    }

    public int getStr() {
        return this.mStr;
    }

    public int getDef() {
        return this.mDef;
    }

    public int getAgi() {
        return this.mAgi;
    }

    public int getLuck() {
        return this.mLuck;
    }

    public String getCreateAt() {
        return this.mCreateAt;
    }

}
