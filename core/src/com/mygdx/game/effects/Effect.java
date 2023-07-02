package com.mygdx.game.effects;

import com.mygdx.game.entity.player.PlayerData;

public abstract class Effect {
    protected double stateTimer;
    protected double duration;
    protected PlayerData playerData;
    protected float amount;

    public Effect(float amount, double duration, PlayerData playerData)
    {
        this.amount = amount;
        this.duration = duration;
        this.playerData = playerData;
    }
    public abstract void ApplayEffect();
    public abstract void EndEffect();
    void update(double dt) {
     stateTimer+=dt;
     if(stateTimer >= duration) EndEffect();
     if(stateTimer < duration) effectOnUpdate(dt);
    }

    protected abstract void effectOnUpdate(double dt);
    protected double GetDuration()
    {
        return duration;
    }
    protected double GetRemainingDuration(){
        return duration - stateTimer;
    }
    protected boolean IsActive(){ return GetRemainingDuration()>0;}
}
