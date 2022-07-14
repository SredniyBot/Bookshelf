package com.bytevalue.inter;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.books.ActorAdder;
import com.bytevalue.books.BookHandler;
import com.bytevalue.books.Locket;

public class GameStage extends Stage implements ActorAdder,Pauser {

    private Locket locket;

    public GameStage(Viewport viewport, ActivitySwitcher activitySwitcher) {
        setViewport(viewport);

        BookHandler bookHandler = new BookHandler(viewport);
        addActor(bookHandler);

        TopBar topBar = new TopBar(viewport,activitySwitcher,this);

        locket =new Locket(viewport,bookHandler,topBar.getScore(),activitySwitcher);
        locket.setModeStart();
        addActor(locket);
        addActor(topBar);
    }


    public void renewLocket() {
        locket.setModeStart();
    }

    public void startGame() {
        locket.complete();
    }

    public boolean isInStartPosition(){
        return locket.isStartMenu();
    }
}
