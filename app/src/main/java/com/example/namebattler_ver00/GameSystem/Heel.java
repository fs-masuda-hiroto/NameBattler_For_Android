package com.example.namebattler_ver00.GameSystem;

public class Heel extends Magic{

	public Heel(final String magicName, final int magicCost) {
		super(magicName, magicCost);
	}

	/**
	 * 魔法の効果処理
	 */
	@Override
	protected int useMagic() {
		final int hellHP = 50;
		return hellHP;
	}

}
