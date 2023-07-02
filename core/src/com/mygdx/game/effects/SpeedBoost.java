package com.mygdx.game.effects;

import com.mygdx.game.entity.player.PlayerData;
import com.mygdx.game.entity.player.PlayerProvider;
public class SpeedBoost extends  Effect{

    public SpeedBoost(float amount, double duration, PlayerData playerData)
    {
        super(amount,duration, playerData);
    }
    @Override
    public void ApplayEffect() {
        this.playerData.setSpeed(playerData.getSpeed() + amount);
    }

    @Override
    public void EndEffect() {
        this.playerData.setSpeed(playerData.getSpeed() - amount);
    }

    @Override
    protected void effectOnUpdate(double dt) {

    }
}
