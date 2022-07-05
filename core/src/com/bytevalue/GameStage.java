package com.bytevalue;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.books.ActorAdder;
import com.bytevalue.books.BookHandler;
import com.bytevalue.books.Locket;

public class GameStage extends Stage implements ActorAdder {

    Locket locket;
    Viewport viewport;
    ActivitySwitcher activitySwitcher;


    public GameStage(Viewport viewport,ActivitySwitcher activitySwitcher) {
        this.viewport=viewport;
        this.activitySwitcher=activitySwitcher;
        setViewport(viewport);

        BookHandler bookHandler = new BookHandler();
        addActor(bookHandler);
        locket =new Locket(viewport,this,bookHandler);
        addActor(locket);
        locket.generateBookshelves();

    }

    public void pause(){
        activitySwitcher.switchActivity();
    }

}
