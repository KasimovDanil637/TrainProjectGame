package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.constants.Constants;

public abstract class EntityData {
    protected double speed;
    protected double hp;

    protected double maxHP;

    public EntityData(double speed, double hp) {
        this.speed = speed;
        this.hp = hp;
        this.maxHP = hp;
    }

    public EntityData() {
        this.speed = Constants.SPEED_STANDART;
        this.hp = Constants.HP_STANDART;
        this.maxHP = hp;
    }

    public boolean isAlive() {
        return hp > 0;
    }


    public double getHp() {
        return hp;
    }

    public double getMaxHp() {
        return maxHP;
    }
    public void decreaseHP(double damage) {
        hp -= damage;
    }

    public void addHP(double heal) {
        hp = (Math.min(hp + heal, maxHP));
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void kill() {
        hp = -1;
    }


}
