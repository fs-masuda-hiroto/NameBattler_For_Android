package com.example.namebattler_ver00.GameSystem;

import java.util.Random;

public class Porize extends Magic{

	public Porize(final String magicName, final int magicCost) {
		super(magicName, magicCost);
	}

	/**
	 * 魔法の効果処理
	 */
	@Override
	protected boolean judgeMagic() {
		Random random = new Random();
		if(20 > random.nextInt(100)) {
			return true;
		} else {
			return false;
		}
	}

}
