package com.bytevalue.inter;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.BookSorter;
import com.bytevalue.books.Money;
import com.bytevalue.books.Score;
import com.bytevalue.service.TextureService;

public class TopBar extends Group {

    public TopBar(Viewport viewport, ActivitySwitcher activitySwitcher,Pauser pauser,Score score,Money money) {
        PauseButton pauseButton = new PauseButton(viewport,activitySwitcher,pauser);
        addActor(pauseButton);
        addActor(score);
        addActor(money);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(TextureService.getTopTexture(),0, BookSorter.SCREEN_HEIGHT-
                TextureService.getTopTexture().getRegionHeight());
        super.draw(batch, parentAlpha);
    }

}
