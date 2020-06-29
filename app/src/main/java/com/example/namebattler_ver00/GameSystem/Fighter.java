package com.example.namebattler_ver00.GameSystem;

//プレイヤー：戦士
public class Fighter extends Player {

	public Fighter(final String name) {
		super(name);
	}

	/**
	 * 名前(name)からキャラクターに必要なパラメータを生成する
	 */
	@Override
	protected void ｍakeCharacter() {
		this.mHp = getNumber(0, 201) + 100;
		this.mMp = getNumber(1, 0);
		this.mStr = getNumber(2, 71) + 30;
		this.mDef = getNumber(3, 71) + 30;
		this.mLuck = getNumber(4, 100) + 1;
		this.mAgi = getNumber(5, 50) + 1;
		this.mJobName = "戦士";
	}

	/**
	 * 対象プレイヤーに攻撃を行う
	 * @param defender : 対象プレイヤー
	 */
	@Override
	public String attack(Player defender) {
		String log = "";
		log += getName() + "の攻撃！\n";

		int damage = calcDamage(defender);

		if(damage == 0) {
			log += "攻撃がミス！\n";
		} else {
			if(damage == getSTR()) {
				log += getName() + "は会心の一撃を出した！\n";
			}
			log += defender.getName() + "に" + damage + "のダメージ！\n";
		}
		defender.damage(damage);

		if (defender.getHP() <= 0) {
			log += defender.getName() + "は力尽きた...\n";
		}

		return log;
	}
}

