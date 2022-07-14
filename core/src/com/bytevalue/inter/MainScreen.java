package com.bytevalue.inter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.BookSorter;
import com.bytevalue.service.SkinService;
import com.bytevalue.service.SoundService;
import com.bytevalue.service.TextureService;


public class MainScreen implements Screen, ActivitySwitcher {

    private final OrthographicCamera mCamera;
    private final Viewport mViewport;
    private Stage mActiveStage;

    private final GameStage gameStage;
    private final PauseStage pauseStage;
    private final SettingsStage settingsStage;
    private final StartStage startStage;
    private final SkinStage skinStage;
    private final QuiteStage quitStage;
    private final LossStage lossStage;


    private boolean gamePreDraw;
    private boolean gamePreUpdate;

    public MainScreen() {
        SkinService.init();
        mCamera = new OrthographicCamera();
        mViewport = new FillViewport(BookSorter.SCREEN_WIDTH, BookSorter.SCREEN_HEIGHT, mCamera);
        gameStage =new GameStage(mViewport,this);
        pauseStage =new PauseStage(mViewport,this);
        settingsStage = new SettingsStage(mViewport,this);
        startStage=new StartStage(mViewport,this);
        skinStage=new SkinStage(mViewport,this);
        quitStage=new QuiteStage(mViewport,this);
        lossStage=new LossStage(mViewport,this);
        mActiveStage = startStage;
        gamePreDraw=true;
        gamePreUpdate=true;

    }


    @Override
    public void show() {
        SkinService.init();
    }

    @Override
    public void render(float delta) {
        mCamera.update();

        Gdx.gl.glClearColor(65 / 255f, 65 / 255f, 65 / 255f, 1f); // просто цвет фона
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (gamePreUpdate)
            gameStage.act();
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
        gamePreUpdate=false;
        gamePreDraw=false;
        if(gameStage.isInStartPosition()){
            if (mActiveStage==startStage){
                mActiveStage=quitStage;
                gamePreDraw=true;
            }else {
                gamePreUpdate=true;
                gamePreDraw=true;
                mActiveStage=startStage;
            }
        }else {
            if (mActiveStage==gameStage){
                gamePreDraw=true;
                mActiveStage=pauseStage;
            } else if (mActiveStage==pauseStage){
                mActiveStage=gameStage;
            }else if(mActiveStage==settingsStage){
                gamePreDraw=true;
                mActiveStage=pauseStage;
            }
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }



    @Override
    public void switchActivity(Activity activity) {
        gamePreDraw=false;
        gamePreUpdate=false;

        switch (activity){
            case GAME:
                mActiveStage=gameStage;
                break;
            case PAUSE:
                if (gameStage.isInStartPosition()){
                    gamePreDraw=true;
                    gamePreUpdate=true;
                    mActiveStage=startStage;
                }else {
                    gamePreDraw=true;
                    mActiveStage=pauseStage;
                }
                break;
            case SETTINGS:
                gamePreDraw=true;
                mActiveStage=settingsStage;
                break;
            case MAIN_MENU:
                gamePreDraw=true;
                gamePreUpdate=true;
                gameStage.renewLocket();
                mActiveStage=startStage;
                break;
            case THEMES:
                mActiveStage=skinStage;
                break;
            case LOST:
                lossStage.setScore(gameStage.getScore());
                mActiveStage=lossStage;
                break;
        }

    }

    @Override
    public void startGame() {
        gamePreDraw=false;
        gamePreUpdate=false;
        gameStage.startGame();
        mActiveStage=gameStage;
    }

    @Override
    public void dispose() {
        gameStage.dispose();
        pauseStage.dispose();
        settingsStage.dispose();
        mActiveStage.dispose();
        startStage.dispose();
        skinStage.dispose();
        quitStage.dispose();
        TextureService.dispose();
        SoundService.dispose();
    }
}
