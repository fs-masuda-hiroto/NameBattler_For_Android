package com.example.namebattler_ver00.GameSystem;

import java.io.Serializable;
import java.util.ArrayList;

public class Party implements Serializable {

	private ArrayList<Player> mMembers;

	public Party() {
		mMembers = new ArrayList<Player>();
	}

	/**
	* パーティーメンバーを ArrayList で取得する
	*/
	public ArrayList<Player> getMembers() {
	return mMembers;
	}

	/**
	* パーティーにプレイヤーを追加する
	* @param player : 追加するプレイヤー
	*/
	public void appendPlayer(Player player) {
		mMembers.add(player);
	}

	/**
	* パーティーからプレイヤーを離脱させる
	* @param player : 離脱させるプレイヤー
	*/
	public void removePlayer(Player player) {
		mMembers.remove(player);
	}
}
