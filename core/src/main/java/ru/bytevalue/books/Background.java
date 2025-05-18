package ru.bytevalue.books;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ru.bytevalue.service.TextureService;

public class Background extends Actor {

    private final BookDisposer bookDisposer;
    private boolean startMenu;

    Background(BookDisposer bookDisposer) {
        this.bookDisposer = bookDisposer;
        setZIndex(1000000);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (startMenu) {
            batch.draw(TextureService.getStartMenuTexture(), 0, bookDisposer.getBias());
        } else {
            batch.draw(TextureService.getBackgroundTexture(), 0, bookDisposer.getBias() % getH());
        }
        batch.draw(TextureService.getBackgroundTexture(), 0, bookDisposer.getBias() % getH() +
            TextureService.getBackgroundTexture().getRegionHeight());
    }

    public int getH() {
        return TextureService.getBackgroundTexture().getRegionHeight();
    }


    public void setModeStartMenu(boolean startMenu) {
        this.startMenu = startMenu;
    }
}
