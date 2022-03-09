package com.bytevalue;

import com.badlogic.gdx.Game;

public class BookSorter extends Game {

	public static final float SCREEN_WIDTH = 1280f;
	public static final float SCREEN_HEIGHT = 1280f;
	public static float VIEWPORT_LEFT;
	public static float VIEWPORT_RIGHT;

	private GameScreen mGameScreen;

	@Override
	public void create () {
		mGameScreen = new GameScreen();
		setScreen(mGameScreen);
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
	public void dispose() {
		super.dispose();
		mGameScreen.dispose();
	}
}