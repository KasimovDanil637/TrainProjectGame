package com.mygdx.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.constants.Constants;
import com.mygdx.game.entity.player.PlayerData;
import com.mygdx.game.scenes.ProgressBar.Stamina;
import com.mygdx.game.scenes.ProgressBar.HealthBar;


public class Hud implements Disposable {

    //Scene2D.ui Stage and its own Viewport for HUD
    public Stage stage;
    private Viewport viewport;
    private PlayerData playerData;

    private boolean timeUp; // true when the world timer reaches 0

    //Scene2D widgets
    private HealthBar healthBar;
    private Stamina endurance;


    public Hud(SpriteBatch sb, PlayerData playerData) {
        //define our tracking variables;
        this.playerData = playerData;


        //setup the HUD viewport using a new camera seperate from our gamecam
        //define our stage using that viewport and our games spritebatch
        viewport = new FitViewport(Constants.WIDTH_SCREEN_STANDART, Constants.HEIGHT_SCREEN_STANDART, new OrthographicCamera());
        stage = new Stage(viewport, sb);

    }


    public void createHeathBar(){
        stage = new Stage();
        healthBar = new HealthBar(120, 10, playerData);
        healthBar.setPosition(10, Gdx.graphics.getHeight() - 20);
        healthBar.setValue((int) playerData.getHp());
        stage.addActor(healthBar);
    }
    public void createStamina(){
        endurance  = new Stamina(120, 10, playerData);
        endurance.setPosition(10, Gdx.graphics.getHeight() - 40);
        endurance.setValue(endurance.getValue() - 1);
        stage.addActor(endurance);
    }
    public void update(){
        createHeathBar();
        createStamina();
        stage.draw();
        stage.act();
    }


    @Override
    public void dispose() { stage.dispose(); }

    public boolean isTimeUp() { return timeUp; }

}
