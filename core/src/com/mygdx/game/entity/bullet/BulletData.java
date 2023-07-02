package com.mygdx.game.entity.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.constants.Constants;
import com.mygdx.game.entity.EntityData;

public class BulletData extends EntityData {
    private double damage;
    private double radius;
    private Vector2 size;

    private double deathTimer;

    public BulletData(Vector2 sizeOfBullet, double radius, double speed, double hp, double damage){
        super(speed, hp);
        this.damage = damage;

        this.size = sizeOfBullet;
        this.radius = radius;
        deathTimer = Constants.BULLET_DEATH_TIMER;
    }

    public BulletData(){
        damage = 1;
        speed = 1000;
        deathTimer = Constants.BULLET_DEATH_TIMER;

        size = new Vector2(8, 8);
    }
    public double damage(){
        return damage;
    }

    public Vector2 getSize() {
        return size;
    }

    public double getRadius() {
        return radius;
    }

    public double getDeathTimer() {
        return deathTimer;
    }
}
