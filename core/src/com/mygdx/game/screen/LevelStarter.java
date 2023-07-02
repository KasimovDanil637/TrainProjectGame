package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.TrainGame;

public class LevelStarter implements Screen {

    private TrainGame game;
    private Sprite spriteText;
    private Sprite spriteMonitor;

    private PlayScreen playScreen;

    private float scale;

    public LevelStarter(TrainGame game, String pathToImage, String pathToMonitor, PlayScreen playScreen){
        this.game = game;
        this.playScreen = playScreen;

        spriteText = new Sprite(new Texture(pathToImage));
        spriteMonitor = new Sprite(new Texture(pathToMonitor));

        scale = 3.0F;
    }

    public LevelStarter(TrainGame game, String pathToImage, PlayScreen playScreen){
        this.game = game;
        this.playScreen = playScreen;

        spriteText = new Sprite(new Texture(pathToImage));

        scale = 3.0F;
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()) {
            playScreen.createPreviousLevel();
            game.setScreen(playScreen);
            dispose();

            return;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        if(spriteMonitor != null)
        game.batch.draw(spriteMonitor.getTexture(), (game.getConfig().x - spriteMonitor.getTexture().getWidth() * scale) / 2, (game.getConfig().y - spriteMonitor.getTexture().getHeight() * scale) / 2, spriteMonitor.getTexture().getWidth() * scale, spriteMonitor.getTexture().getHeight() * scale);

        game.batch.draw(spriteText.getTexture(), (game.getConfig().x - spriteText.getTexture().getWidth() * scale) / 2, (game.getConfig().y - spriteText.getTexture().getHeight() * scale) / 2, spriteText.getTexture().getWidth() * scale, spriteText.getTexture().getHeight() * scale);
        game.batch.end();
    }


    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}

