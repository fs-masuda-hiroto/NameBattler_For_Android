package com.example.namebattler_ver00.GameSystem;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;

// プレイヤークラス(各種ジョブの基底クラス)
public class Player implements Comparable<Player>{

	protected String mName;	// 名前
	protected int mHp;		// HP
	protected int mMp;		// MP
	protected int mStr;		// 攻撃力
	protected int mDef;		// 防御力
	protected int mLuck;		// 会心率
	protected int mAgi;		// 素早さ
	protected int mMaxHp;		// 最大HP
	protected boolean mJudgePorize = false;		// 麻痺状態
	protected boolean mJudgePoison = false;		// 毒状態
	protected String mJobName;		// 毒状態

	/**
	 * コンストラクタ
	 * @param name : プレイヤー名
	 */
	public Player(final String name) {
		this.mName = name;
		// キャラクターのパラメータ生成
		ｍakeCharacter();
		this.mMaxHp = mHp;
	}

	/**
	 * プレイヤー名を取得する
	 * @return プレイヤー名
	 */
	public String getName() {
		return this.mName;
	}

	/**
	 * 現在HPを取得する
	 * @return 現在HP
	 */
	public int getHP() {
		return this.mHp;
	}

	/**
	 * 現在HPを取得する
	 * @return 現在MP
	 */
	public int getMP() {
		return this.mMp;
	}

	/**
	 * 攻撃力を取得する
	 * @return 攻撃力
	 */
	public int getSTR() {
		return this.mStr;
	}

	/**
	 * 防御力を取得する
	 * @return 防御力
	 */
	public int getDEF() {
		return this.mDef;
	}

	/**
	 * 会心率を取得する
	 * @return 会心率
	 */
	public int getLUCK() {
		return this.mLuck;
	}

	/**
	 * 素早さを取得する
	 * @return 素早さ
	 */
	public int getAGI() {
		return this.mAgi;
	}

	/**
	 * 最大HPを取得する
	 * @return 最大HP
	 */
	public int getMAX_HP() {
		return this.mMaxHp;
	}

	/**
	 * 麻痺状態を取得する
	 * @return 麻痺状態
	 */
	public boolean getJugdePorize() {
		return this.mJudgePorize;
	}

	/**
	 * 毒状態を取得する
	 * @return 毒状態
	 */
	public boolean getJudgePoison() {
		return this.mJudgePoison;
	}

	/**
	 * 職業名を取得する
	 * @return 職業名
	 */
	public String getJobName() {
		return this.mJobName;
	}

	/**
	 * 名前(name)からキャラクターに必要なパラメータを生成する
	 */
	protected void ｍakeCharacter() {
		// ジョブごとにオーバーライドして処理を記述してください
	}

	/**
	 * 名前(name)からハッシュ値を生成し、指定された位置の数値を取り出す
	 * @param index : 何番目の数値を取り出すか
	 * @param max : 最大値(内部的に0～255の値を生成するが、0～maxまでの値に補正)
	 * @return 数値(0～max) ※maxも含む
	 */
	protected int getNumber(int index, int max) {
		try {
			// 名前からハッシュ値を生成する
			byte[] result = MessageDigest.getInstance("SHA-1").digest(this.mName.getBytes());
			String digest = String.format("%040x", new BigInteger(1, result));

			// ハッシュ値から指定された位置の文字列を取り出す（２文字分）
			String hex = digest.substring(index * 2, index * 2 + 2);

			// 取り出した文字列（16進数）を数値に変換する
			int val = Integer.parseInt(hex, 16);
			return val * max / 255;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}


	/**
	 * 現在のステータスを System.out で表示する
	 */
	public String printStatus() {
		String status = this.getName() + " (HP=" + this.getHP() + " : MP=" + this.getMP() +
				" : STR=" + this.getSTR() + " : DEF=" + this.getDEF() + " : LUCK=" + this.getLUCK() +
				" : AGI=" + this.getAGI() + ")\n";

		return status;
	}

	/**
	 * 対象プレイヤーに攻撃を行う
	 * @param defender : 対象プレイヤー
	 */
	public String attack(Player defender) {
		// ジョブごとにオーバーライドして処理を記述してください
		return null;
	}

	/**
	 * 対象プレイヤー(target)に対して与えるダメージを計算する
	 * @param target : 対象プレイヤー
	 * @return ダメージ値(0～)
	 */
	protected int calcDamage(Player target) {
		Random random = new Random();
		int damage = 0;
		if(this.mLuck > random.nextInt(100)) {
			damage = getSTR();
		} else {
			damage = getSTR() - target.getDEF();
		}

		if (damage < 0) {
			damage = 0;
		}
		return damage;
	}

	/**
	 * ダメージを受ける
	 * @param damage : ダメージ値
	 */
	protected void damage(int damage) {
		// ダメージ値分、HPを減少させる
		this.mHp = Math.max(this.getHP() - damage, 0);
	}

	/**
	* AGIの高い順番い並び替える
	*/
	@Override
	public int compareTo(Player player) {
		if(this.mAgi > player.getAGI()) {
			return -1;
		} else if(this.mAgi < player.getAGI()) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 対象プレイヤーに攻撃を行う
	 * @param defender : 対象プレイヤー
	 * @param party : 対象パーティ
	 */
	public String attack(Player defender, Party party) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}


}