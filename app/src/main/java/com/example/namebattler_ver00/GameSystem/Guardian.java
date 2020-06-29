package com.example.namebattler_ver00.GameSystem;

import java.util.ArrayList;
import java.util.Random;

//プレイヤー：ガーディアン
public class Guardian extends Player {

	private ArrayList<Magic> mMagicList = new ArrayList<Magic>();
	private int mHeelPlayer = 0;
	private String mLog = "";

	public Guardian(final String name) {
		super(name);
		makeMagicList();
	}

	/**
	 * 名前(name)からキャラクターに必要なパラメータを生成する
	 */
	@Override
	protected void ｍakeCharacter() {
		this.mHp = getNumber(0, 301) + 400;
		this.mMp = getNumber(1, 31) + 20;
		this.mStr = getNumber(2, 10) + 1;
		this.mDef = getNumber(3, 51) + 50;
		this.mLuck = getNumber(4, 100) + 1;
		this.mAgi = getNumber(5, 30) + 1;
		this.mJobName = "ガーディアン";
	}

	/**
	 * magicListを生成する
	 */
	protected final void makeMagicList() {
		mMagicList.add(new Heel("ヒール", 20));
	}

	/**
	 * 対象プレイヤーに攻撃を行う
	 * @param defender : 対象プレイヤー
	 */
	@Override
	public String attack(Player defender, Party party) {
		mLog = "";

		// ダメージを受けているメンバーを探す
		for(int i = 0; i < party.getMembers().size(); i++) {
			if(party.getMembers().get(i).getHP() < party.getMembers().get(i).getMAX_HP()) {
				mHeelPlayer = i;
				break;
			}
			// ダメージを負っていなければ10をセット
			mHeelPlayer = 10;
		}

		// ダメージを受けているメンバーがいて、ヒールを使えるか判定
		if(mHeelPlayer != 10 && mMp >= 20) {
			magic(defender, party);
		} else {
			mLog += getName() + "の攻撃！\n";

			// 求めたダメージを対象プレイヤーに与える
			int damage = calcDamage(defender);

			if(damage == 0) {
				mLog += "攻撃がミス！\n";
			} else {
				if(damage == getSTR()) {
					mLog += getName() + "は会心の一撃を出した！\n";
				}
				mLog += defender.getName() + "に" + damage + "のダメージ！\n";
			}
			defender.damage(damage);
		}

	     if (defender.getHP() <= 0) {
	     	mLog += defender.getName() + "は力尽きた...\n";
	     }

	     return mLog;
	}

	/**
	 * 魔法選択処理
	 * @param defender : 対象プレイヤー
	 */
	public void magic(Player defender, Party party) {
		int useMagicIndex = 0;

		mLog += getName() + mMagicList.get(useMagicIndex).getMagicName() + "を唱えた！\n";
		mMp -= mMagicList.get(useMagicIndex).getMagicCost();

		switch(useMagicIndex) {
		case 0:
			// HPを回復
			party.getMembers().get(mHeelPlayer).mHp += mMagicList.get(useMagicIndex).useMagic();
			// MAX_HPよりHPが回復した場合は、MAX_HPの値にする
			if(party.getMembers().get(mHeelPlayer).getHP() > party.getMembers().get(mHeelPlayer).getMAX_HP()) {
				party.getMembers().get(mHeelPlayer).mHp = party.getMembers().get(mHeelPlayer).getMAX_HP();
			}
			mLog += party.getMembers().get(mHeelPlayer).getName() + "はHP"
					+ mMagicList.get(useMagicIndex).useMagic() + "回復した！\n";
			break;
		default:
			break;
		}
	}

}

