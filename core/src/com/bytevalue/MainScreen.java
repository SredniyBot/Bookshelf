package com.bytevalue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.pause.PauseStage;
import com.bytevalue.settings.SettingsStage;


public class MainScreen implements Screen, ActivitySwitcher{

    private final OrthographicCamera mCamera;
    private final Viewport mViewport;
    private Stage mActiveStage;

    private final GameStage gameStage;
    private final PauseStage pauseStage;
    private final SettingsStage settingsStage;
    private boolean gamePreDraw=false;

    public MainScreen() {
        mCamera = new OrthographicCamera();
        mViewport = new FillViewport(BookSorter.SCREEN_WIDTH, BookSorter.SCREEN_HEIGHT, mCamera);
        gameStage =new GameStage(mViewport,this);
        pauseStage =new PauseStage(mViewport,this);
        settingsStage = new SettingsStage(mViewport,this);
        mActiveStage = gameStage;
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        mCamera.update();

        Gdx.gl.glClearColor(65 / 255f, 65 / 255f, 65 / 255f, 1f); // просто цвет фона
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mActiveStage.act(delta);
        if(gamePreDraw)
            gameStage.draw();
        mActiveStage.draw();

    }

    @Override
    public void resize(int width, int height) {
        mViewport.update(width, height, true);
        mViewport.setScreenWidth(mViewport.getScreenWidth()*height/mViewport.getScreenHeight());
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
        mActiveStage.dispose();
    }

    @Override
    public void switchActivity(Activity activity) {
        switch (activity){
            case GAME:
                gamePreDraw=false;
                mActiveStage=gameStage;
                break;
            case PAUSE:
                gamePreDraw=true;
                mActiveStage=pauseStage;
                break;
            case SETTINGS:
                gamePreDraw=true;
                mActiveStage=settingsStage;
                break;
        }

    }
}
