package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.resources.objectUserData;

public class MyContactListener implements ContactListener {

    public void beginContact(Contact c) {

        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();

        if (fa.getBody().getUserData() != null && fa.getBody().getUserData() != null && fb.getBody().getUserData() instanceof objectUserData && fa.getBody().getUserData() instanceof objectUserData) {

            if (((objectUserData) fa.getBody().getUserData()).getId() == "fireBall") {
                if (((objectUserData) fa.getBody().getUserData()).getId() != null && ((objectUserData) fb.getBody().getUserData()).getId() == "fireBall") {
                } else {

                    if (((objectUserData) fb.getBody().getUserData()).getId() == "enemy") {
                        ((objectUserData)fb.getBody().getUserData()).setId("dead");
                        Filter filter = fa.getFilterData();
                        filter.categoryBits = 0x0004;
                        fa.setFilterData(filter);

                    } else if (((objectUserData) fb.getBody().getUserData()).getId() == "player" && ((objectUserData) fa.getBody().getUserData()).isEnemy()) {
                        worldHandler.currentHealth--;
                        Filter filter = fa.getFilterData();
                        filter.categoryBits = 0x0004;
                        fa.setFilterData(filter);

                    } else if (!((objectUserData) fa.getBody().getUserData()).isEnemy() && ((objectUserData) fb.getBody().getUserData()).getId() != "player") {
                        Filter filter = fa.getFilterData();
                        filter.categoryBits = 0x0004;
                        fa.setFilterData(filter);

                    } else if (((objectUserData) fb.getBody().getUserData()).getId() != "player") {
                        Filter filter = fa.getFilterData();
                        filter.categoryBits = 0x0004;
                        fa.setFilterData(filter);

                    }
                }
            }

            if (((objectUserData) fb.getBody().getUserData()).getId() == "fireBall") {
                if (((objectUserData) fa.getBody().getUserData()).getId() != null && ((objectUserData) fa.getBody().getUserData()).getId() == "fireBall") {
                } else {

                    if (((objectUserData) fa.getBody().getUserData()).getId() == "enemy") {
                        ((objectUserData)fa.getBody().getUserData()).setId("dead");
                        Filter filter = fb.getFilterData();
                        filter.categoryBits = 0x0004;
                        fb.setFilterData(filter);

                    } else if (((objectUserData) fa.getBody().getUserData()).getId() == "player" && ((objectUserData) fb.getBody().getUserData()).isEnemy()) {
                        worldHandler.currentHealth--;
                        Filter filter = fb.getFilterData();
                        filter.categoryBits = 0x0004;
                        fb.setFilterData(filter);

                    } else if (!((objectUserData) fb.getBody().getUserData()).isEnemy() && ((objectUserData) fa.getBody().getUserData()).getId() != "player") {
                        Filter filter = fb.getFilterData();
                        filter.categoryBits = 0x0004;
                        fb.setFilterData(filter);

                    } else if (((objectUserData) fa.getBody().getUserData()).getId() != "player") {
                        Filter filter = fb.getFilterData();
                        filter.categoryBits = 0x0004;
                        fb.setFilterData(filter);

                    }
                }
            }

            if (((objectUserData) fa.getBody().getUserData()).getId() == "health" && ((objectUserData) fb.getBody().getUserData()).getId() == "player") {
                worldHandler.currentHealth++;
                Filter filter = fa.getFilterData();
                filter.categoryBits = 0x0004;
                fb.setFilterData(filter);
            }

            if (((objectUserData) fb.getBody().getUserData()).getId() == "health" && ((objectUserData) fa.getBody().getUserData()).getId() == "player") {
                worldHandler.currentHealth++;
                Filter filter = fb.getFilterData();
                filter.categoryBits = 0x0004;
                fb.setFilterData(filter);

            }
        }
    }


    public void endContact(Contact c) {
        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();

    }

    public void preSolve(Contact c, Manifold m) {
    }

    public void postSolve(Contact c, ContactImpulse ci) {
    }
}
