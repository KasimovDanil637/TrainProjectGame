package com.mygdx.game.entity.enemy;

import com.mygdx.game.entity.EntityData;

public class EnemyData extends EntityData {
    private double attackRange;
    private double awakeRange;
    private double damage;

    public EnemyData(double hp, double speed, double attackRange, double awakeRange, double damage){
        this.attackRange = attackRange;
        this.awakeRange = awakeRange;
        this.damage = damage;
        this.hp = hp;
        this.speed = speed;
    }

    public double getDamage() {
        return damage;
    }

    public double getAttackRange() {
        return attackRange;
    }

    public double getAwakeRange() {
        return awakeRange;
    }
}
