package com.mygdx.game;

public class Secrets {

    static int[] keys = new int[]{41, 43, 46, 33, 47, 29, 42, 37, 31, 44, 40, 54};//moresanicplz
    static int countdown = 1200;
    static int index = 0;

    public static void menuSecret() {

        countdown--;
        if (index == keys.length) {
            musicPlayer.secretMusic.play();
            musicPlayer.menuMusicPause();
        } else {
            if (countdown <= 0) {
                countdown = 120;
                index = 0;
            }
            else if (inputHandler.currentKey == keys[index]) {
                countdown = 120;
                index++;
            }
        }
    }
}
//package com.mygdx.game;
//
//import com.badlogic.gdx.Gdx;
//
///**
// * Created by Cian on 20/03/2015.
// */
//public class Secrets {
//
//
//    public static void menuSecret() {
//        boolean tempB1 = false, tempB2 = false, tempB3 = false, tempB4 = false, tempB5 = false, tempB6 = false, tempB7 = false, tempB8 = false, tempB9 = false, tempB10 = false, tempB11 = false, tempB12 = false;
//        if (inputHandler.M) {
//            tempB1 = true;
//            Gdx.app.log(MyGdxGame.title, "It's working!");
//            if (inputHandler.O || tempB2) {
//                tempB2 = true;
//                Gdx.app.log(MyGdxGame.title, "It's working! 2");
//                if (inputHandler.R || tempB3) {
//                    tempB3 = true;
//                    Gdx.app.log(MyGdxGame.title, "It's working! 3");
//                    if (inputHandler.E || tempB4) {
//                        tempB4 = true;
//                        Gdx.app.log(MyGdxGame.title, "It's working! 4");
//                        if (inputHandler.S || tempB5) {
//                            tempB5 = true;
//                            if (inputHandler.A || tempB6) {
//                                tempB6 = true;
//                                if (inputHandler.N || tempB7) {
//                                    tempB7 = true;
//                                    if (inputHandler.I || tempB8) {
//                                        tempB8 = true;
//                                        if (inputHandler.C || tempB9) {
//                                            tempB9 = true;
//                                            if (inputHandler.P || tempB10) {
//                                                tempB10 = true;
//                                                if (inputHandler.L || tempB11) {
//                                                    tempB11 = true;
//                                                    if (inputHandler.S) {
//                                                        musicPlayer.sdmPlayer();
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    } //Cian what the everliving fuck why are there like 40 fucking brackets here yo fucking retarded peasant jeasus h christ on the bike holy shit killyourself fucking hell you retard
//
//    public static void test() {
//        boolean tempB1 = false, tempB2 = false, tempB3 = false, tempB4 = false, tempB5 = false, tempB6 = false, tempB7 = false, tempB8 = false, tempB9 = false, tempB10 = false, tempB11 = false, tempB12 = false;
//        if (inputHandler.M || tempB1) {
//            tempB1 = true;
//            Gdx.app.log(MyGdxGame.title, "It's working!");
//            if (inputHandler.O || tempB2) {
//                tempB2 = true;
//                Gdx.app.log(MyGdxGame.title, "It's working! 2");
//                if (inputHandler.R || tempB3) {
//                    tempB3 = true;
//                    Gdx.app.log(MyGdxGame.title, "It's working! 3");
//                    if (inputHandler.E || tempB4) {
//                        tempB4 = true;
//                        Gdx.app.log(MyGdxGame.title, "It's working! 4");
//                        if (inputHandler.S || tempB5) {
//                            tempB5 = true;
//                            if (inputHandler.A || tempB6) {
//                                tempB6 = true;
//                                if (inputHandler.N || tempB7) {
//                                    tempB7 = true;
//                                    if (inputHandler.I || tempB8) {
//                                        tempB8 = true;
//                                        if (inputHandler.C || tempB9) {
//                                            tempB9 = true;
//                                            if (inputHandler.P || tempB10) {
//                                                tempB10 = true;
//                                                if (inputHandler.L || tempB11) {
//                                                    tempB11 = true;
//                                                    if (inputHandler.S) {
//                                                        musicPlayer.sdmPlayer();
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}