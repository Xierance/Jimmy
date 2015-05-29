package com.mygdx.game;

import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.Visuals.ui;
import com.mygdx.game.resources.changeMap;
import com.mygdx.game.resources.projectiles;
import com.mygdx.game.screens.TestClass;

public class inputHandler implements InputProcessor {

    public static boolean Up;
    public static boolean Down;
    public static boolean Left;
    public static boolean Right;
    public static boolean Space;
    public static boolean Escape;
    public static boolean Alt_left;
    public static boolean Alt_right;
    public static boolean Tab;
    public static boolean Num_1;
    public static boolean Num_2;
    public static boolean Num_3;
    public static boolean Num_4;
    public static boolean Num_5;
    public static boolean Num_6;
    public static boolean Num_7;
    public static boolean Num_8;
    public static boolean Num_9;
    public static boolean Num_0;
    public static boolean F1;
    public static boolean F2;
    public static boolean F3;
    public static boolean F4;
    public static boolean F5;
    public static boolean F6;
    public static boolean F7;
    public static boolean F8;
    public static boolean F9;
    public static boolean F10;
    public static boolean F11;
    public static boolean F12;
    public static boolean A;
    public static boolean B;
    public static boolean C;
    public static boolean D;
    public static boolean E;
    public static boolean F;
    public static boolean G;
    public static boolean H;
    public static boolean I;
    public static boolean J;
    public static boolean K;
    public static boolean L;
    public static boolean M;
    public static boolean N;
    public static boolean O;
    public static boolean P;
    public static boolean Q;
    public static boolean R;
    public static boolean S;
    public static boolean T;
    public static boolean U;
    public static boolean V;
    public static boolean W;
    public static boolean X;
    public static boolean Y;
    public static boolean Z;
    public static int currentKey;
    public static int currentScrollValue;
    public static boolean Ctrl_left;
    public static boolean Ctrl_right;
    public static long timeElapsed;
    public static int actualScroll = 0;
    public static boolean mouse;

    public static boolean betterScrolled(int scrollValue){

       /* long millis = System.currentTimeMillis();


        if (actualScroll < scrollValue){
            return true;
        }
        else*/
        return false;
    }

    public boolean keyDown(int keycode) {
        currentKey = keycode;
        switch (keycode) {
            case 19:
                Up = true;
                break;
            case 20:
                Down = true;
                break;
            case 21:
                Left = true;
                break;
            case 22:
                Right = true;
                break;
            case 57:
                Alt_left = true;
                break;
            case 58:
                Alt_right = true;
                break;
            case 62:
                Space = true;
                break;
            case 131:
                Escape = true;
                break;
            case 41:
                M = true;
                break;
            case 43:
                O = true;
                break;
            case 46:
                R = true;
                break;
            case 33:
                E = true;
                break;
            case 47:
                S = true;
                break;
            case 29:
                A = true;
                break;
            case 42:
                N = true;
                break;
            case 37:
                I = true;
                break;
            case 31:
                C = true;
                break;
            case 44:
                P = true;
                break;
            case 40:
                L = true;
                break;
            case 51:
                W = true;
                break;
            case 32:
                D = true;
                break;
            case 30:
                B = true;
                break;
            case 34:
                F = true;
                break;
            case 35:
                G = true;
                break;
            case 36:
                H = true;
                break;
            case 38:
                J = true;
                break;
            case 39:
                K = true;
                break;
            case 45:
                Q = true;
                break;
            case 48:
                T = true;
                break;
            case 49:
                U = true;
                break;
            case 50:
                V = true;
                break;
            case 52:
                X = true;
                break;
            case 53:
                Y = true;
                break;
            case 54:
                Z = true;
                break;
            case 7:
                Num_0 = true;
                break;
            case 8:
                Num_1 = true;
                break;
            case 9:
                Num_2 = true;
                break;
            case 10:
                Num_3 = true;
                break;
            case 11:
                Num_4 = true;
                break;
            case 12:
                Num_5 = true;
                break;
            case 13:
                Num_6 = true;
                break;
            case 14:
                Num_7 = true;
                break;
            case 15:
                Num_8 = true;
                break;
            case 16:
                Num_9 = true;
                break;
            case 61:
                Tab = true;
            case 129:
                Ctrl_left = true;
                break;
            case 130:
                Ctrl_right = true;
                break;


        }

        return true;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case 19:
                Up = false;
                break;
            case 20:
                Down = false;
                break;
            case 21:
                Left = false;
                break;
            case 22:
                Right = false;
                break;
            case 57:
                Alt_left = false;
                break;
            case 58:
                Alt_right = false;
                break;
            case 62:
                Space = false;
                break;
            case 131:
                Escape = false;
                break;
            case 41:
                M = false;
                break;
            case 43:
                O = false;
                break;
            case 46:
                R = false;
                break;
            case 33:
                E = false;
                break;
            case 47:
                S = false;
                break;
            case 29:
                A = false;
                break;
            case 42:
                N = false;
                break;
            case 37:
                I = false;
                break;
            case 31:
                C = false;
                break;
            case 44:
                P = false;
                break;
            case 40:
                L = false;
                break;
            case 51:
                W = false;
                break;
            case 32:
                D = false;
                break;
            case 30:
                B = false;
                break;
            case 34:
                F = false;
                break;
            case 35:
                G = false;
                break;
            case 36:
                H = false;
                break;
            case 38:
                J = false;
                break;
            case 39:
                K = false;
                break;
            case 45:
                Q = false;
                break;
            case 48:
                T = false;
                break;
            case 49:
                U = false;
                break;
            case 50:
                V = false;
                break;
            case 52:
                X = false;
                break;
            case 53:
                Y = false;
                break;
            case 54:
                Z = false;
                break;
            case 7:
                Num_0 = false;
                break;
            case 8:
                Num_1 = false;
                break;
            case 9:
                Num_2 = false;
                break;
            case 10:
                Num_3 = false;
                break;
            case 11:
                Num_4 = false;
                break;
            case 12:
                Num_5 = false;
                break;
            case 13:
                Num_6 = false;
                break;
            case 14:
                Num_7 = false;
                break;
            case 15:
                Num_8 = false;
                break;
            case 16:
                Num_9 = false;
            case 61:
                Tab = false;
            case 129:
                Ctrl_left = false;
                break;
            case 130:
                Ctrl_right = false;
                break;



        }

        return true;
    }

    public boolean keyTyped(char character) {
        return false;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(TestClass.orthographicCamera != null)TestClass.orthographicCamera.unproject(TestClass.tmp.set(screenX, screenY, 0));

            if (!Ctrl_right)

                switch(ui.scrollLocation) {
                    case 1:if (TestClass.player.getPlayerBody() != null)
                        projectiles.shootDick(TestClass.player.getPlayerBody().getPosition(),projectiles.angle2(TestClass.player.getPlayerBody().getPosition(),TestClass.getmouseCoords()),TestClass.world,false);
                        break;
                    case 2:if (TestClass.player.getPlayerBody() != null)
                        projectiles.shootFire(TestClass.player.getPlayerBody().getPosition(), projectiles.angle2(TestClass.player.getPlayerBody().getPosition(), TestClass.getmouseCoords()), TestClass.world,1);
                        break;
                    case 3:
                        worldHandler.currentHealth--;
                        break;
                    case 4:
                        //changeMap.clearMap(TestClass.world);
                        projectiles.airStrike(TestClass.getmouseCoords(),5,TestClass.world);
                        break;
                    case 5:
                        mouse = true;
                        break;
                    case 6:
                        projectiles.shootScone(TestClass.player.getPlayerBody().getPosition(),projectiles.angle2(TestClass.player.getPlayerBody().getPosition(),TestClass.getmouseCoords()),TestClass.world);
                        break;


                }
        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        mouse = false;
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    public boolean scrolled(int amount) {
        currentScrollValue = amount;


       /* while (currentScrollValue == 1 || currentScrollValue == -1){
           // timeElapsed = System.currentTimeMillis() - millis;
            actualScroll++;
            currentScrollValue = 0;
            System.out.println(String.valueOf(actualScroll));
        }*/
        return false;
    }

} 