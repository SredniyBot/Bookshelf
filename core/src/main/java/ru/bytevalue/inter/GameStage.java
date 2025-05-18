package ru.bytevalue.inter;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import ru.bytevalue.books.*;

public class GameStage extends Stage implements ActorAdder,Pauser {

    private final Locket locket;
    private final Score score;

    public GameStage(Viewport viewport, ActivitySwitcher activitySwitcher) {
        setViewport(viewport);
        score = new Score(1250);
        Money money = new Money();

        BookHandler bookHandler = new BookHandler(viewport, money);
        addActor(bookHandler);

        TopBar topBar = new TopBar(viewport,activitySwitcher,this,score, money);

        locket =new Locket(viewport,bookHandler,score,activitySwitcher);
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

    public int getScore() {
        return score.getScore();
    }
}
