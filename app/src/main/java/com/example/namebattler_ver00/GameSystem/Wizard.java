package com.example.namebattler_ver00.GameSystem;

import java.util.ArrayList;
import java.util.Random;

// プレイヤー：魔法使い
public class Wizard extends Player {

	private ArrayList<Magic> mMagicList = new ArrayList<Magic>();
	private String mLog = "";

	public Wizard(final String name) {
		super(name);
		makeMagicList();
	}

	/**
	 * magicListを生成する
	 */
	protected final void makeMagicList() {
		mMagicList.add(new Fire("ファイア",10));
		mMagicList.add(new Thunder("サンダー", 20));
	}

	/**
	 * 名前(name)からキャラクターに必要なパラメータを生成する
	 */
	@Override
	protected void ｍakeCharacter() {
		this.mHp = getNumber(0, 101) + 50;
		this.mMp = getNumber(1, 51) + 30;
		this.mStr = getNumber(2, 50) + 1;
		this.mDef = getNumber(3, 50) + 1;
		this.mLuck = getNumber(4, 100) + 1;
		this.mAgi = getNumber(5, 41) + 20;
		this.mJobName = "魔法使い";
	}

	/**
	 * 対象プレイヤーに攻撃を行う
	 * @param defender : 対象プレイヤー
	 */
	@Override
	public String attack(Player defender) {
		mLog = "";
		mLog += getName() + "の攻撃！\n";

        // 与えるダメージを求める
		int damage = calcDamage(defender);

		if(mMp >= 10) {
			damage = mMagicList.get(chooseMagic()).useMagic();
		}

		if(damage == 0) {
			mLog += "攻撃がミス！\n";
		} else {
			mLog += defender.getName() + "に" + damage + "のダメージ！\n";
		}
        defender.damage(damage);

        if (defender.getHP() <= 0) {
			mLog += defender.getName() + "は力尽きた...\n";
        }

        return mLog;
	}

	protected int chooseMagic() {
		Random random = new Random();
		int useMagicIndex;

		while(true) {
			useMagicIndex = random.nextInt(mMagicList.size());
			if(mMp - mMagicList.get(useMagicIndex).getMagicCost() >= 0) {
				break;
			}
		}

		mLog += mMagicList.get(useMagicIndex).getMagicName() + "を唱えた！\n";
		mMp -= mMagicList.get(useMagicIndex).getMagicCost();

		return useMagicIndex;
	}
}
