package com.bytevalue;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class BookSorter extends Game implements Stoppable {

	public static final float SCREEN_WIDTH = 1280f;
	public static final float SCREEN_HEIGHT = 1280f;
	public static float VIEWPORT_LEFT;
	public static float VIEWPORT_RIGHT;

	private MainScreen mMainScreen;

	@Override
	public void create () {
		Gdx.input.setCatchKey(Input.Keys.BACK, true);
		Gdx.gl.glEnable(Gdx.gl20.GL_BLEND);
		Gdx.input.setInputProcessor(new MyInputProcessor(this));
		mMainScreen = new MainScreen();
		setScreen(mMainScreen);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		float aspectRatio = (float) width / height;
		float viewportWidth = SCREEN_HEIGHT * aspectRatio;
		VIEWPORT_LEFT = (SCREEN_WIDTH - viewportWidth) / 2;
		VIEWPORT_RIGHT = VIEWPORT_LEFT + viewportWidth;
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}