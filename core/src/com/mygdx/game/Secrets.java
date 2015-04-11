package com.mygdx.game;

import com.badlogic.gdx.Gdx;

/**
 * Created by Cian on 20/03/2015.
 */
public class Secrets {



    public static void menuSecret() {
        boolean tempB1 = false, tempB2 = false, tempB3 = false, tempB4 = false, tempB5 = false, tempB6 = false, tempB7 = false, tempB8 = false, tempB9 = false, tempB10 = false, tempB11 = false, tempB12 = false;
        if (Input.M){
            tempB1 = true;
            Gdx.app.log(MyGdxGame.title, "It's working!");
            if (Input.O || tempB2){
                tempB2 = true;
                Gdx.app.log(MyGdxGame.title, "It's working! 2");
                if (Input.R || tempB3){
                    tempB3 = true;
                    Gdx.app.log(MyGdxGame.title, "It's working! 3");
                    if (Input.E || tempB4){
                        tempB4 = true;
                        Gdx.app.log(MyGdxGame.title, "It's working! 4");
                        if (Input.S || tempB5){
                            tempB5 = true;
                            if (Input.A || tempB6){
                                tempB6 = true;
                                if (Input.N || tempB7){
                                    tempB7 = true;
                                    if (Input.I || tempB8){
                                        tempB8 = true;
                                        if (Input.C || tempB9){
                                            tempB9 = true;
                                            if (Input.P || tempB10){
                                                tempB10 = true;
                                                if (Input.L || tempB11){
                                                    tempB11 = true;
                                                    if (Input.S){
                                                        musicPlayer.sdmPlayer();

                                                    }
                                                }
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


    }

    public static void test(){
        boolean tempB1 = false, tempB2 = false, tempB3 = false, tempB4 = false, tempB5 = false, tempB6 = false, tempB7 = false, tempB8 = false, tempB9 = false, tempB10 = false, tempB11 = false, tempB12 = false;
        if (Input.M || tempB1){
            tempB1 = true;
            Gdx.app.log(MyGdxGame.title, "It's working!");
            if (Input.O || tempB2){
                tempB2 = true;
                Gdx.app.log(MyGdxGame.title, "It's working! 2");
                if (Input.R || tempB3){
                    tempB3 = true;
                    Gdx.app.log(MyGdxGame.title, "It's working! 3");
                    if (Input.E || tempB4){
                        tempB4 = true;
                        Gdx.app.log(MyGdxGame.title, "It's working! 4");
                        if (Input.S || tempB5){
                            tempB5 = true;
                            if (Input.A || tempB6){
                                tempB6 = true;
                                if (Input.N || tempB7){
                                    tempB7 = true;
                                    if (Input.I || tempB8){
                                        tempB8 = true;
                                        if (Input.C || tempB9){
                                            tempB9 = true;
                                            if (Input.P || tempB10){
                                                tempB10 = true;
                                                if (Input.L || tempB11){
                                                    tempB11 = true;
                                                    if (Input.S){
                                                        musicPlayer.sdmPlayer();

                                                    }
                                                }
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

}
