package com.bytevalue;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.books.ActorAdder;
import com.bytevalue.books.BookHandler;
import com.bytevalue.books.Locket;
import com.bytevalue.pause.PauseButton;

public class GameStage extends Stage implements ActorAdder {


    public GameStage(Viewport viewport,ActivitySwitcher activitySwitcher) {
        setViewport(viewport);

        BookHandler bookHandler = new BookHandler();
        addActor(bookHandler);
        Locket locket =new Locket(viewport,this,bookHandler);
        addActor(locket);
        locket.generateBookshelves();
        PauseButton pauseButton = new PauseButton(viewport,activitySwitcher);
        addActor(pauseButton);
    }

}
