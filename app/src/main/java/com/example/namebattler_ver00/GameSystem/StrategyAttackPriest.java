package com.example.namebattler_ver00.GameSystem;

public class StrategyAttackPriest implements StrategyBase{

	/**
	 * 作戦を生成する
	 * @return targetIndex : 攻撃対象
	 */
	public int getStrategy(Party targetParty) {
		int targetIndex = 0;

		for(int i = 1; i < targetParty.getMembers().size(); i++) {
			if(targetParty.getMembers().get(i).getJobName().equals("僧侶")) {
				targetIndex  = i;
			} else {
				targetIndex = 0;
			}
		}

		return targetIndex;
	}

}
