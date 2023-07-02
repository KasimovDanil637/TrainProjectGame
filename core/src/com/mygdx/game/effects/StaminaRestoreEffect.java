package com.mygdx.game.effects;

import com.mygdx.game.entity.player.PlayerData;

public class StaminaRestoreEffect extends Effect{
    private double staminaInSecond;
    public StaminaRestoreEffect(float amount, double duration, PlayerData playerData)
    {
        super(amount,duration, playerData);
        staminaInSecond = amount / duration;
    }
    @Override
    public void ApplayEffect() {
    }

    @Override
    public void EndEffect() {
    }

    @Override
    protected void effectOnUpdate(double dt) {
        playerData.addStamina((float) (staminaInSecond * dt));
    }
}
