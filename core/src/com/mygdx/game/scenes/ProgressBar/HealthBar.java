package com.mygdx.game.scenes.ProgressBar;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.mygdx.game.entity.player.PlayerData;

/**
 * The progress bar which reassembles the behaviour of the health bar.
 *
 * @author serhiy
 */
public class HealthBar extends ProgressBar {
    PlayerData playerData;
    /**
     * @param width  of the health bar
     * @param height of the health bar
     */
    public HealthBar(int width, int height, PlayerData playerData) {
        super(0, (float) playerData.getMaxHp(), 1, false, new ProgressBarStyle());
        this.playerData = playerData;
        getStyle().background = Utils.getColoredDrawable(width, height, Color.RED);
        getStyle().knob = Utils.getColoredDrawable(0, height, Color.RED);
        getStyle().knobBefore = Utils.getColoredDrawable(width, height, Color.GREEN);
        setWidth(width);
        setHeight(height);

        setValue((int) playerData.getHp());

        setAnimateDuration(1);
    }
}
