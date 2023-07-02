package com.mygdx.game.scenes.ProgressBar;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.mygdx.game.entity.player.PlayerData;

/**
 * Progress bar which reassembles the behaviour of the loading bar (with left and right borders).
 *
 * @author serhiy
 */
public class Stamina extends ProgressBar {
    PlayerData playerData;

    public Stamina(int width, int height, PlayerData playerData) {
        super(0, 100, 1, false, new ProgressBarStyle());
        this.playerData = playerData;
        getStyle().background = Utils.getColoredDrawable(width, height, Color.RED);
        getStyle().knob = Utils.getColoredDrawable(0, height, Color.RED);
        getStyle().knobBefore = Utils.getColoredDrawable(width, height, Color.YELLOW);
        setWidth(width);
        setHeight(height);

        setValue(playerData.getStamina());

        setAnimateDuration(1);
    }
}