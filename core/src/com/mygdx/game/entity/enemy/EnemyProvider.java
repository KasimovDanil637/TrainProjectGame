package com.mygdx.game.entity.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.constants.Masks;
import com.mygdx.game.entity.EntityData;
import com.mygdx.game.entity.EntityProvider;
import com.mygdx.game.entity.player.PlayerProvider;
import com.mygdx.game.screen.PlayScreen;

import java.util.ArrayList;
import java.util.List;

public abstract class EnemyProvider extends EntityProvider {
    protected PlayScreen screen;
    protected Vector2 velocity;
    protected List<EntityProvider> enemies;
    protected PlayerProvider playerProvider;

    private boolean isPlayerAccesable;


    public EnemyProvider(PlayScreen screen, EntityData enemyData, Vector2 position, Vector2 box2d){
        super(screen);

        this.world = screen.getWorld();
        this.entityData = enemyData;
        this.screen = screen;


        setPosition(position.x, position.y);

        defineEnemy(position, box2d);
        System.out.println(this.getPosition());
        velocity = new Vector2(-0.1f, 0);
    }

    public void setEnemies(List<EntityProvider> enemies) {

        this.enemies = enemies;
        for(int i = 0; i < enemies.size(); i++) {
            if(enemies.get(i) instanceof PlayerProvider) {
                playerProvider = (PlayerProvider) enemies.get(i);
            }
        }
    }

    public void addEnemy(EntityProvider enemyProvider) {
        if(enemies == null) enemies = new ArrayList<>();
        enemies.add(enemyProvider);
        if(playerProvider != null) return;
        if(enemyProvider instanceof PlayerProvider) {
            playerProvider = (PlayerProvider) enemyProvider;
        }
    }

    protected abstract void defineEnemy(Vector2 location, Vector2 size);
    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x = -velocity.x;
        if(y)
            velocity.y = -velocity.y;
    }

    protected int isPlayerIsHorizontalyGettable(Vector2 fromPoint) {
        isPlayerAccesable = true;
        if(playerProvider.getPosition().dst2(b2body.getPosition()) > ((EnemyData)entityData).getAwakeRange() * ((EnemyData)entityData).getAwakeRange()) isPlayerAccesable = false;
        if(!isPlayerAccesable) return 0;

        RayCastCallback callback = new RayCastCallback() {

            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {

                if(normal.x < 0) {
                    if ((playerProvider.getPosition().x > point.x) && (fixture.getFilterData().categoryBits == Masks.GROUND_BIT || fixture.getFilterData().categoryBits == Masks.OBJECT_BIT)) {
                        isPlayerAccesable = false;
                        return -1;
                    }
                } else {
                    if ((playerProvider.getPosition().x < point.x)  && (fixture.getFilterData().categoryBits == Masks.GROUND_BIT || fixture.getFilterData().categoryBits == Masks.OBJECT_BIT)) {
                        isPlayerAccesable = false;
                        return -1;
                    }
                }
                return 1;
            }
        };

        if(playerProvider.getPosition().x > fromPoint.x) {
            world.rayCast(callback, fromPoint, new Vector2((float) (fromPoint.x + ((EnemyData)entityData).getAwakeRange()), fromPoint.y));
            return isPlayerAccesable ? 1 : 0;
        } else {
            world.rayCast(callback, fromPoint, new Vector2((float) (fromPoint.x - ((EnemyData)entityData).getAwakeRange()), fromPoint.y));
            return isPlayerAccesable ? -1 : 0;

        }

    }

    public abstract void damage(EntityData entityData);

    public abstract void collisionWithoutDamage();
}
