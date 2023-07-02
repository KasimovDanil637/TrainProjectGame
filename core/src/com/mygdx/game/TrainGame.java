package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.screen.PlayScreen;

public class TrainGame extends Game {
	public SpriteBatch batch;
	private Vector2 config;
	public TrainGame(Vector2 config) {
		this.config = config;
	}
	public Vector2 getConfig() {
		return config;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();

		setScreen(new PlayScreen(this));
	}


	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
	}

	@Override
	public void render () {


		super.render();
	}
}
