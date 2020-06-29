package com.example.namebattler_ver00.GameSystem;

import java.util.ArrayList;
import java.util.Random;


//プレイヤー：僧侶
public class Priest extends Player {

	private ArrayList<Magic> mMagicList = new ArrayList<Magic>();
	private Random mRandom = new Random();
	private int mUseMagicIndex;
	private String mLog = "";

	public Priest(final String name) {
		super(name);
		makeMagicList();
	}

	/**
	 * 名前(name)からキャラクターに必要なパラメータを生成する
	 */
	@Override
	protected void ｍakeCharacter() {
		this.mHp = getNumber(0, 121) + 80;
		this.mMp = getNumber(1, 31) + 20;
		this.mStr = getNumber(2, 61) + 10;
		this.mDef = getNumber(3, 61) + 10;
		this.mLuck = getNumber(4, 100) + 1;
		this.mAgi = getNumber(5, 41) + 20;
		this.mJobName = "僧侶";
	}

	/**
	 * magicListを生成する
	 */
	protected final void makeMagicList() {
		mMagicList.add(new Heel("ヒール", 20));
		mMagicList.add(new Porize("ポライズ", 10));
		mMagicList.add(new Poisen("ポイズン", 10));
	}

	/**
	 * 対象プレイヤーに攻撃を行う
	 * @param defender : 対象プレイヤー
	 */
	@Override
	public String attack(Player defender, Party party) {
		mLog = "";
		// 最少消費MPが現在のMP以下なら実行する
		if(mMp >= 10) {
			magic(defender,party);
		} else {
			// 与えるダメージを求める
			mLog +=getName() + "の攻撃！\n";
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
	 * 使用する魔法のインデックス番号を取得
	 * @return mUseMagicIndex : 使用される魔法のインデックス番号
	 */
	protected void getUseMagicIndex() {

		if(mMp >= 20 && getMAX_HP() > mHp) {
			mUseMagicIndex = 0;
		} else if(mMp >= 10) {
			mUseMagicIndex = mRandom.nextInt(mMagicList.size());
			if(mUseMagicIndex == 0) {
				mUseMagicIndex += 1;
			}
		}
	}

	/**
	 * 魔法選択処理
	 * @param defender : 対象プレイヤー
	 */
	public void magic(Player defender, Party party) {
		getUseMagicIndex();
		int heelPlayer = 0;

		while(true) {
			// ダメージを受けているメンバーを探す
			for(int i = 0; i < party.getMembers().size(); i++) {
				if(party.getMembers().get(i).getHP() < party.getMembers().get(i).getMAX_HP()) {
					heelPlayer = i;
					break;
				}
				// HPが減っているplayerがいなければ10をセット
				heelPlayer = 10;
			}

			// HPが減っていて、現在のMPから消費MPを引いても正の値ならヒール
			if(heelPlayer != 10 && mMp - mMagicList.get(mUseMagicIndex).getMagicCost() >= 0) {
				mUseMagicIndex = 0;
			} else {
				mUseMagicIndex = mRandom.nextInt(2) + 1;
			}

			if(mMp - mMagicList.get(mUseMagicIndex).getMagicCost() >= 0) {
				break;
			}
		}

		mLog += getName() + mMagicList.get(mUseMagicIndex).getMagicName() + "を唱えた！\n";
		mMp -= mMagicList.get(mUseMagicIndex).getMagicCost();

		// mUseMagicIndexによって魔法を選択
		switch(mUseMagicIndex) {
			case 0:
				// HPを回復する
				party.getMembers().get(heelPlayer).mHp += mMagicList.get(mUseMagicIndex).useMagic();
				// MAX_HPより回復したら、MAX_HPの値にする
				if(party.getMembers().get(heelPlayer).getHP() > party.getMembers().get(heelPlayer).getMAX_HP()) {
					party.getMembers().get(heelPlayer).mHp = party.getMembers().get(heelPlayer).getMAX_HP();
				}

				mLog += party.getMembers().get(heelPlayer).getName() + "はHP"
						+ mMagicList.get(mUseMagicIndex).useMagic() + "回復した！\n";
				break;
			case 1:
				// Porizeが成功したか判定
				defender.mJudgePorize = mMagicList.get(mUseMagicIndex).judgeMagic();
				if(defender.getJugdePorize()) {
					mLog += defender.getName() + "は次のターン行動不能になった\n";
				} else {
					mLog += "ポライズは失敗した\n";
				}
				break;
			case 2:
				if(defender.getJudgePoison()) {
					mLog += defender.getName() + "は既に毒状態だ\n";
				} else {
					mLog += defender.getName() + "は毒状態になった\n";
				}
				defender.mJudgePoison = mMagicList.get(mUseMagicIndex).judgeMagic();
				break;
			default :
				break;
		}
	}
}

