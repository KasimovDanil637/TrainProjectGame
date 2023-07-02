package com.mygdx.game.tools;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.constants.Masks;
import com.mygdx.game.entity.bullet.BulletProvider;
import com.mygdx.game.entity.enemy.EnemyProvider;
import com.mygdx.game.entity.items.Potion;
import com.mygdx.game.entity.player.PlayerProvider;
import com.mygdx.game.screen.PlayScreen;

public class WorldContactListener implements ContactListener {
    private PlayScreen playScreen;

    public void setPlayScreen(PlayScreen playScreen) {
        this.playScreen = playScreen;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case Masks.BULLET_BIT | Masks.PLAYER_BIT:
                if(fixA.getFilterData().categoryBits == Masks.BULLET_BIT) {
                    ((BulletProvider)fixA.getUserData()).damage(((PlayerProvider) fixB.getUserData()).getEntityData());
                    System.out.println(((PlayerProvider) fixB.getUserData()).getEntityData().getHp());
                }
                else {
                    ((BulletProvider)fixB.getUserData()).damage(((PlayerProvider) fixA.getUserData()).getEntityData());
                    System.out.println(((PlayerProvider) fixA.getUserData()).getEntityData().getHp());
                }
                break;
            case Masks.ENEMY_BIT | Masks.PLAYER_BIT:
                if(fixA.getFilterData().categoryBits == Masks.ENEMY_BIT) {
                    ((EnemyProvider)fixA.getUserData()).damage(((PlayerProvider) fixB.getUserData()).getEntityData());
                }
                else {
                    ((EnemyProvider)fixB.getUserData()).damage(((PlayerProvider) fixA.getUserData()).getEntityData());
                }
                break;
            case Masks.GROUND_BIT | Masks.BULLET_BIT:
                if(fixA.getFilterData().categoryBits == Masks.BULLET_BIT) {
                    ((BulletProvider)fixA.getUserData()).damage();
                }
                else {
                    ((BulletProvider)fixB.getUserData()).damage();
                }
                break;
            case Masks.ENEMY_BIT | Masks.BULLET_BIT:
                if(fixA.getFilterData().categoryBits == Masks.BULLET_BIT) {
                    ((BulletProvider)fixA.getUserData()).damage(((EnemyProvider) fixB.getUserData()).getEntityData());
                }
                else {
                    ((BulletProvider)fixB.getUserData()).damage(((EnemyProvider) fixA.getUserData()).getEntityData());
                }
                break;
            case Masks.PLAYER_BIT | Masks.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == Masks.OBJECT_BIT)
                    ((Potion)fixA.getUserData()).collision((PlayerProvider) fixB.getUserData());
                else
                    ((Potion)fixB.getUserData()).collision(((PlayerProvider) fixA.getUserData()));
                break;
            case Masks.PLAYER_BIT | Masks.GAME_ENDER_BIT:
                if(fixA.getFilterData().categoryBits == Masks.PLAYER_BIT)
                    playScreen.moveToNextLeve();
                else
                    playScreen.moveToNextLeve();
                break;
            case Masks.ENEMY_BIT | Masks.OBJECT_BIT:
            case Masks.ENEMY_BIT | Masks.GROUND_BIT:
                if(fixA.getFilterData().categoryBits == Masks.ENEMY_BIT)
                    ((EnemyProvider)fixA.getUserData()).collisionWithoutDamage();
                else
                    ((EnemyProvider)fixB.getUserData()).collisionWithoutDamage();
                break;

        }
    }


    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
