package com.mygdx.game;

import com.badlogic.gdx.InputProcessor;

public class Input implements InputProcessor {
    public static boolean Up;
    public static boolean Down;
    public static boolean Left;
    public static boolean Right;
    public static boolean Space;
    public static boolean Escape;
    public static boolean M;
    public static boolean O;
    public static boolean R;
    public static boolean E;
    public static boolean S;
    public static boolean A;
    public static boolean N;
    public static boolean I;
    public static boolean C;
    public static boolean P;
    public static boolean L;
    public static boolean W;
    public static boolean D;


    public Input() {
    }

    public boolean keyDown(int keycode) {
        switch(keycode) {
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
            case 62:
                Space = true;
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
            case 131:
                Escape = true;
                break;
            case 51:
                W = true;
                break;
            case 32:
                D = true;





        }

        return true;
    }

    public boolean keyUp(int keycode) {
        switch(keycode) {
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
            case 62:
                Space = false;
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
            case 131:
                Escape = false;
                break;
            case 51:
                W = false;
                break;
            case 32:
                D = false;




        }

        return true;
    }

    public boolean keyTyped(char character) {
        return false;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    public boolean scrolled(int amount) {
        return false;
    }
} 