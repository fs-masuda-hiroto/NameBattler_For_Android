package com.example.namebattler_ver00.GameSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameManager {

	private static ArrayList<Player> mAllMember = new ArrayList<Player>();
	private Party mParty1;
	private Party mParty2;
	private static Party mMyParty = new Party();
	private static Party mTargetParty = new Party();

	public GameManager(Party party1, Party party2) {
		mParty1 = party1;
		mParty2 = party2;
	}

	public String startBattleLog() {
		String log = "";
		mAllMember.clear();

		for(int i = 0; i < 3; i++) {
			mAllMember.add(mParty1.getMembers().get(i));
			mAllMember.add(mParty2.getMembers().get(i));
		}

		log += printStatus(mParty1);
		log += printStatus(mParty2);
		log += "\n=== バトル開始 ===\n";

		return log;
	}

	public String battle(int turnNumber, String strategyNumber) {
		String log = "";

	    	log += "--------------------------------\n";
	    	log += "- ターン" + (turnNumber +1) + " -\n";

	    	// AGIの高い順に並び替え
	    	Collections.sort(mAllMember);

	    	int playerCount = 0;
	    	while(playerCount < mAllMember.size()) {
	    		// ■攻撃処理
	    		// 現在選択されているplayerが見方か敵か判定
				if(mParty1.getMembers().contains(mAllMember.get(playerCount))) {
					mMyParty = mParty1;
					mTargetParty = mParty2;
				} else {
					mMyParty = mParty2;
					mTargetParty = mParty1;
				}

	    		// 攻撃対象を選択
	    		int targetIndex = chooseStrategy(strategyNumber);

	    		// 麻痺状態なら行動不能
				if(mAllMember.get(playerCount).getJugdePorize()) {
					log += mAllMember.get(playerCount).getName() + "は行動不能\n";
					mAllMember.get(playerCount).mJudgePorize = false;
				} else {
					if(mAllMember.get(playerCount).getJobName() == "僧侶" ||
							mAllMember.get(playerCount).getJobName() == "ガーディアン") {

						log += mAllMember.get(playerCount).attack(mTargetParty.getMembers().get(targetIndex),mMyParty);
					} else {
						log += mAllMember.get(playerCount).attack(mTargetParty.getMembers().get(targetIndex));
					}
				}
				// 毒状態なら20ダメージ
	    		if(mAllMember.get(playerCount).getJudgePoison() && mAllMember.get(playerCount).getHP() > 0) {
	    			log += mAllMember.get(playerCount).getName() + "は毒状態だ(20のダメージ)\n";
	    			mAllMember.get(playerCount).mHp -= 20;

					// 毒死判定
					if (mAllMember.get(playerCount).getHP() <= 0) {
						// mAllMemberとmMyPartyからplayerを削除
						int removePlayerIndex = mMyParty.getMembers().indexOf(mAllMember.get(playerCount));
						if(removePlayerIndex >= 0) {
							mMyParty.removePlayer(mAllMember.get(removePlayerIndex));
						}
						mAllMember.remove(playerCount);

						log += "毒により力尽きた...\n";
					}
	    		}

	    		// 天からの落とし物
	    		log += randomDamage();

	    		// 敗北判定
	    		if(mTargetParty.getMembers().get(targetIndex).getHP() <= 0) {
	    			// mAllMemberとmTargetPartyからplayerを削除
					int removeTargetPlayerIndex = mAllMember.indexOf(mTargetParty.getMembers().get(targetIndex));
					if(removeTargetPlayerIndex >= 0) {
						mAllMember.remove(removeTargetPlayerIndex);
					}
	    			mTargetParty.removePlayer(mTargetParty.getMembers().get(targetIndex));
	    		}

	    		if (mMyParty.getMembers().size() <= 0 || mTargetParty.getMembers().size() <= 0) {
	    			break;
	    		}

	    		playerCount++;
	    	}

		// ターン終了時、全滅判定
		if (mParty1.getMembers().size() <= 0 || mParty2.getMembers().size() <= 0) {
			log += judgeWinOrLose(mParty1,mParty2);
		} else {
			log += printStatus(mParty1);
			log += printStatus(mParty2);
		}

	    return log;
	}

	// ランダムで天からの落とし物
	private String randomDamage() {
		Random random = new Random();
		String log = "";

		// ランダムで敵の誰かに30ダメージ
		if(20 > random.nextInt(100)) {
			int raid = random.nextInt(mTargetParty.getMembers().size());

			log += "天からの落とし物(" + mTargetParty.getMembers().get(raid).getName() + "に30ダメージ)\n";
			mTargetParty.getMembers().get(raid).mHp -= 30;
		}

		return log;
	}

	// 勝敗判定
	private String judgeWinOrLose(Party party1, Party party2) {
		String log = "";

		log += "\n=== バトル終了 ===\n";

		// 勝敗判定（パーティの残り人数が多い方が勝ち）
		if(party1.getMembers().size() > party2.getMembers().size()) {
			log += "パーティ1の勝利！！\n";
		} else if(party1.getMembers().size() < party2.getMembers().size()) {
			log += "パーティ2の勝利！！\n";
		} else {
			if(party1.getMembers().get(0).getMP() > party2.getMembers().get(0).getHP()) {
				log += "パーティ1の勝利！！\n";
			} else if(party1.getMembers().get(0).getHP() < party2.getMembers().get(0).getHP()) {
				log += "パーティ2の勝利！！\n";
			} else {
				log += "引き分け...\n";
			}
		}

		return log;
	}

	/**
	 * 作戦を選択する
	 */
	protected int chooseStrategy(String selectStrategy) {
		int selectStrategyIndex = 0;

		if(selectStrategy.equals("HPが低い敵を狙う")) {
			selectStrategyIndex = new StrategyLowHP().getStrategy(mTargetParty);
		} else if(selectStrategy.equals("DEFが低い敵を狙う")){
			selectStrategyIndex = new StrategyLowDEF().getStrategy(mTargetParty);
		} else if(selectStrategy.equals("AGIが低い敵を狙う")){
			selectStrategyIndex = new StrategyLowAGI().getStrategy(mTargetParty);
		} else if(selectStrategy.equals("HPが100以下の敵を狙う")){
			selectStrategyIndex = new StrategyUnder100().getStrategy(mTargetParty);
		} else if(selectStrategy.equals("魔法使いを狙う")){
			selectStrategyIndex = new StrategyAttackWizard().getStrategy(mTargetParty);
		} else {
			selectStrategyIndex = new StrategyAttackPriest().getStrategy(mTargetParty);
		}

		return selectStrategyIndex;
	}

	private String printStatus(Party party) {
		String log = "";
		for(int i = 0; i < party.getMembers().size(); i++) {
			log += party.getMembers().get(i).printStatus();
		}

		return log;
	}

}
