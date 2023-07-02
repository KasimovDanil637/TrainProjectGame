package com.mygdx.game.entity.player;

import com.mygdx.game.entity.EntityData;

public class PlayerData extends EntityData {

    private float maxStamina = 100;
    private float stamina = 100;
    private double jumpHeight;


    public PlayerData() {
        super() ;
    }

    public PlayerData(double speed, double hp, float maxStamina, float stamina) {
        super(speed, hp);
        this.maxHP = hp;
        this.maxStamina = maxStamina;
        this.stamina = stamina;
    }
    public float getStamina(){
        return stamina;
    }
    public void addStamina(float stamina){
        if (this.stamina != maxStamina){
            this.stamina = (this.stamina + stamina > maxStamina ? maxStamina : this.stamina + stamina );

        }
    }

    public void decreaseStamina(float stamina){
        if (this.stamina > 0){
            this.stamina -= stamina;

        }
    }


    public void addJumpHeight(double height){
        jumpHeight += height;
    }

    public void decreaseJumpHeight(double height){
        jumpHeight -= height;

    }
}
