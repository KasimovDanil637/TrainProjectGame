package com.mygdx.game.effects;

import com.mygdx.game.constants.Constants;
import com.mygdx.game.entity.player.PlayerData;
import com.mygdx.game.entity.player.PlayerProvider;

public class JumpHeightBoost extends Effect{
    public JumpHeightBoost(float amount, double duration, PlayerData playerData)
    {
        super(amount,duration, playerData);
    }
    @Override
    public void ApplayEffect() {
        playerData.addJumpHeight(amount / Constants.PPM);
    }

    @Override
    public void EndEffect() {
        playerData.decreaseJumpHeight(this.amount/Constants.PPM);
    }

    @Override
    protected void effectOnUpdate(double dt) {

    }
}
