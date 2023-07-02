package com.mygdx.game.effects;

import com.mygdx.game.constants.Constants;
import com.mygdx.game.entity.player.PlayerData;

public class HealingEffect extends Effect {
    private double healInSecond;
    public HealingEffect(float amount, double duration, PlayerData playerData)
    {
        super(amount,duration, playerData);
        healInSecond = amount / duration;
    }
    @Override
    public void ApplayEffect() {
    }

    @Override
    public void EndEffect() {
    }

    @Override
    protected void effectOnUpdate(double dt) {
        playerData.addHP(healInSecond * dt);
    }
}
