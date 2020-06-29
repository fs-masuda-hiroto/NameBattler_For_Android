package com.example.namebattler_ver00;

import com.example.namebattler_ver00.GameSystem.Fighter;
import com.example.namebattler_ver00.GameSystem.Guardian;
import com.example.namebattler_ver00.GameSystem.Player;
import com.example.namebattler_ver00.GameSystem.Priest;
import com.example.namebattler_ver00.GameSystem.Wizard;

import java.util.ArrayList;
import java.util.Random;

public class TargetCharaList {

    private ArrayList<Player> mTargetCharaList = new ArrayList<>();

    public TargetCharaList() {
        createTargetChara();
    }

    public ArrayList<String> genTargetParty() {
        Random random = new Random();
        ArrayList<String> targetParty = new ArrayList<>();

        for(int i = 0; i < 3; i++) {
            while(true) {
                int targetIndex = random.nextInt(mTargetCharaList.size());

                if(!targetParty.contains(mTargetCharaList.get(targetIndex).getName())) {
                    targetParty.add(mTargetCharaList.get(targetIndex).getName());
                    break;
                }
            }
        }

        return  targetParty;
    }

    private void createTargetChara() {
        mTargetCharaList.add(new Fighter("ドリアール"));
        mTargetCharaList.add(new Fighter("アーミュー"));
        mTargetCharaList.add(new Fighter("ジャスカー"));
        mTargetCharaList.add(new Fighter("トバイモン"));
        mTargetCharaList.add(new Fighter("ジャイシー"));
        mTargetCharaList.add(new Fighter("ベネテリー"));
        mTargetCharaList.add(new Fighter("ケイブラッド"));
        mTargetCharaList.add(new Fighter("デーヴィッド"));
        mTargetCharaList.add(new Fighter("ニコラリー"));
        mTargetCharaList.add(new Fighter("ジョナンド"));
        mTargetCharaList.add(new Fighter("パトリック"));
        mTargetCharaList.add(new Fighter("ルフレット"));
        mTargetCharaList.add(new Fighter("クスタント"));
        mTargetCharaList.add(new Fighter("ホレス"));
        mTargetCharaList.add(new Fighter("フェビアン"));
        mTargetCharaList.add(new Fighter("アーローム"));
        mTargetCharaList.add(new Fighter("ヴァレッド"));
        mTargetCharaList.add(new Fighter("ルドウエン"));
        mTargetCharaList.add(new Fighter("エセルイス"));
        mTargetCharaList.add(new Fighter("コーニエル"));
        mTargetCharaList.add(new Wizard("モイモレク"));
        mTargetCharaList.add(new Wizard("ルコシエル"));
        mTargetCharaList.add(new Wizard("ルーレプト"));
        mTargetCharaList.add(new Wizard("ラシアダド"));
        mTargetCharaList.add(new Wizard("ベザル"));
        mTargetCharaList.add(new Wizard("アメルカス"));
        mTargetCharaList.add(new Wizard("アムニエン"));
        mTargetCharaList.add(new Wizard("オロバリル"));
        mTargetCharaList.add(new Wizard("ウァサゴー"));
        mTargetCharaList.add(new Wizard("ベリアモン"));
        mTargetCharaList.add(new Wizard("エギビゴル"));
        mTargetCharaList.add(new Wizard("アドラース"));
        mTargetCharaList.add(new Wizard("フィステマ"));
        mTargetCharaList.add(new Wizard("ダンタムズ"));
        mTargetCharaList.add(new Wizard("ウリクサス"));
        mTargetCharaList.add(new Wizard("ベルファス"));
        mTargetCharaList.add(new Wizard("リバイモン"));
        mTargetCharaList.add(new Wizard("ウェパズズ"));
        mTargetCharaList.add(new Wizard("アグナック"));
        mTargetCharaList.add(new Wizard("セエレ"));
        mTargetCharaList.add(new Priest("ダイアニー"));
        mTargetCharaList.add(new Priest("シャローナ"));
        mTargetCharaList.add(new Priest("ドライーズ"));
        mTargetCharaList.add(new Priest("リンジャー"));
        mTargetCharaList.add(new Priest("カーラ"));
        mTargetCharaList.add(new Priest("リザベティ"));
        mTargetCharaList.add(new Priest("ケイ"));
        mTargetCharaList.add(new Priest("アントニア"));
        mTargetCharaList.add(new Priest("ブリジェマ"));
        mTargetCharaList.add(new Priest("キャエレン"));
        mTargetCharaList.add(new Priest("ローレイン"));
        mTargetCharaList.add(new Priest("ジョセアラ"));
        mTargetCharaList.add(new Priest("ディアリー"));
        mTargetCharaList.add(new Priest("コール"));
        mTargetCharaList.add(new Priest("エルヴィラ"));
        mTargetCharaList.add(new Priest("アトリエット"));
        mTargetCharaList.add(new Priest("アイヴィス"));
        mTargetCharaList.add(new Priest("ヴィヴェラ"));
        mTargetCharaList.add(new Priest("クラリーナ"));
        mTargetCharaList.add(new Priest("ダーリジット"));
        mTargetCharaList.add(new Guardian("ヴェランコ"));
        mTargetCharaList.add(new Guardian("ヴェネデット"));
        mTargetCharaList.add(new Guardian("エラルタコ"));
        mTargetCharaList.add(new Guardian("ターヴィオ"));
        mTargetCharaList.add(new Guardian("アンニコラ"));
        mTargetCharaList.add(new Guardian("ベネディオ"));
        mTargetCharaList.add(new Guardian("ティアーノ"));
        mTargetCharaList.add(new Guardian("サラディオ"));
        mTargetCharaList.add(new Guardian("ルフレート"));
        mTargetCharaList.add(new Guardian("ルメネーア"));
        mTargetCharaList.add(new Guardian("アンセスト"));
        mTargetCharaList.add(new Guardian("アポリスタ"));
        mTargetCharaList.add(new Guardian("ミントーレ"));
        mTargetCharaList.add(new Guardian("アンセルモ"));
        mTargetCharaList.add(new Guardian("バルダンテ"));
        mTargetCharaList.add(new Guardian("アナスパレ"));
        mTargetCharaList.add(new Guardian("ルクレンゾ"));
        mTargetCharaList.add(new Guardian("ジルベルト"));
        mTargetCharaList.add(new Guardian("ヴァレーモ"));
        mTargetCharaList.add(new Guardian("ファエーレ"));
    }

    public ArrayList<Player> getTargetCharaList() {
        return mTargetCharaList;
    }

    // 敵キャラを登録する用のメソッド
//    private void insertDBTargetChara() {
//        TargetCharaList targetCharaList = new TargetCharaList();
//        DBOperation dbOperation = new DBOperation("TARGET_CHARACTERS",new DBTargetOpenHelper(getApplicationContext()));
//        ArrayList<CharaConfig> targetChara = new ArrayList<>();
//        ArrayList<Player> setTargetCharaList = new ArrayList<>();
//        CreateCharaActivity createCharaActivity = new CreateCharaActivity();
//
//        setTargetCharaList = targetCharaList.getTargetCharaList();
//
//        for(int i = 0; i < setTargetCharaList.size(); i++) {
//
//            String name = setTargetCharaList.get(i).getName();
//            String job = setTargetCharaList.get(i).getJobName();
//            int hp = setTargetCharaList.get(i).getHP();
//            int mp = setTargetCharaList.get(i).getMP();
//            int str = setTargetCharaList.get(i).getSTR();
//            int def = setTargetCharaList.get(i).getDEF();
//            int agi = setTargetCharaList.get(i).getAGI();
//            int luck = setTargetCharaList.get(i).getLUCK();
//            String createAt = createCharaActivity.getTime();
//
//            targetChara.add(new CharaConfig(name,job,hp,mp,str,def,agi,luck,createAt));
//            dbOperation.insertDB(targetChara);
//
//            targetChara.clear();
//        }
//
//    }
}
