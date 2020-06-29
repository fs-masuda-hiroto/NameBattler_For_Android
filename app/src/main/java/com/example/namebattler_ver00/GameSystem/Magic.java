package com.example.namebattler_ver00.GameSystem;

import java.util.Random;

public class Magic {

	private String mMagicName;
	private int mMagicCost;

	public Magic(final String magicName, final int magicCost) {
		this.mMagicName = magicName;
		this.mMagicCost = magicCost;
	}

	/**
	 * @return magicName
	 */
	public String getMagicName() {
		return mMagicName;
	}

	/**
	 * @return magicCost
	 */
	public int getMagicCost() {
		return mMagicCost;
	}

	/**
	 * 魔法効果を実装
	 */
	protected int useMagic() {
		// ジョブごとにオーバーライドして処理を記述してください
		Random random = new Random();
		int damage = random.nextInt(21) + 10;
		return damage;
	}

	/**
	 * 魔法効果を実装
	 */
	protected boolean judgeMagic() {
		// ジョブごとにオーバーライドして処理を記述してください
		return true;
	}


}
