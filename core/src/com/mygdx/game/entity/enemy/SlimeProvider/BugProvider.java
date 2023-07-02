package com.mygdx.game.entity.enemy.SlimeProvider;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.constants.Constants;
import com.mygdx.game.constants.Masks;
import com.mygdx.game.entity.EntityData;
import com.mygdx.game.entity.EntityProvider;
import com.mygdx.game.entity.bullet.BulletData;
import com.mygdx.game.entity.bullet.BulletProvider;
import com.mygdx.game.entity.enemy.EnemyData;
import com.mygdx.game.entity.enemy.EnemyProvider;
import com.mygdx.game.entity.player.PlayerProvider;
import com.mygdx.game.screen.PlayScreen;

import java.util.List;
import java.util.Random;

public class BugProvider extends SlimeProvider {

    public BugProvider(PlayScreen screen, EnemyData enemyData, Vector2 position, Vector2 textureSize, Vector2 box2d, boolean orientation) {
        super(screen, enemyData, position, textureSize, box2d, orientation);
        stateTimer = 0;
        setToDestroy = false;
        destroyed = false;

        setBounds(position.x, position.y, textureSize.x / Constants.PPM, textureSize.y / Constants.PPM);
    }




    @Override
    public void damage(EntityData entityData) {}

    @Override
    protected void attack() {
        Vector2 pp = b2body.getPosition();
        double attackRange = ((EnemyData) entityData).getAwakeRange();
        int accessible = this.isPlayerIsHorizontalyGettable(b2body.getPosition());
        if(playerProvider != null && playerProvider.getEntityData().isAlive() && accessible != 0) {
            this.stateTimer = 0;
            isAttacking = true;
            currentState = State.ATTACK;

            Vector2 dir = new Vector2(accessible, 0);

            Vector2 loc = new Vector2(pp.x, pp.y);


            BulletProvider bullet = new BulletProvider(world, loc, new BulletData(new Vector2(2,1), 1.4, 0.2, 1.0, 1.0), dir.nor(), Masks.PLAYER_BIT);
            screen.addToUpdate(bullet);
        }
    }
}
