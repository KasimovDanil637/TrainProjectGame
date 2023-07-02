package com.mygdx.game.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.TrainGame;

import com.mygdx.game.interfaces.IUpdatable;
import jdk.internal.net.http.common.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayScreen implements Screen {
    private List<String> levels;
    private List<String> gameScreens;

    private Level level;

    private int state;

    private TrainGame game;
    private int nowLevel;

    private boolean moveToNext;

    public PlayScreen(TrainGame trainGame){
        game = trainGame;

        levels = new ArrayList<>();
        levels.add("TutorialTemple.tmx");
        levels.add("level02.tmx");
        level = new Level(this, levels.get(0), game);
        gameScreens = Arrays.asList("pngs/monitors/texts/LEVEL01.png", "pngs/monitors/texts/LEVEL02.png");
        moveToNext = true;
        nowLevel = 1;
    }


    @Override
    public void show() {

    }


    public void addToUpdate(IUpdatable newEntity) {
        level.addToUpdate(newEntity);
    }
    @Override
    public void render(float delta) {
        if (moveToNext) {
            moveToNext = false;
            level.dispose();
            if(nowLevel + 1 < gameScreens.size()) {
                nowLevel ++;
                game.setScreen(new LevelStarter(game, gameScreens.get(nowLevel) ,"pngs/monitors/monitor.png", this));
                level = null;
            } else {
                game.setScreen(new LevelStarter(game, "pngs/monitors/main menu.png" ,this));
                nowLevel = 0;
                level = null;
            }
        }

        if(level == null) return;

        state = level.render(delta);

        if(state == 1) {
            level.dispose();
            level = null;
        } else if (state == -1) {
            game.setScreen(new LevelStarter(game, "pngs/monitors/texts/GAME OVER TEXT.png" ,"pngs/monitors/monitor.png", this));

            level.dispose();
            level = null;
        }
    }
    public void moveToNextLeve() {
        moveToNext = true;
    }
    @Override
    public void resize(int width, int height) {
        //updated our game viewport
        level.resize(width,height);
    }

    public void createPreviousLevel() {
        level = new Level(this, levels.get(nowLevel), game);
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

    public TiledMap getMap(){
        return level.getMap();
    }
    public World getWorld(){
        return level.getWorld();
    }
}
