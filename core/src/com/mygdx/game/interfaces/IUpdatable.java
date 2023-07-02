package com.mygdx.game.interfaces;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface IUpdatable {
    void update(double time);
    void draw(Batch batch);
}
