package ru.bytevalue.books;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Array;

import ru.bytevalue.service.TextureService;

public class Book extends BookLocation implements Comparable<Book> {

    private final int id;
    private boolean canBeLifted = true;
    private boolean needsStoneTexture = false;
    private boolean startBook = false;

    private boolean isDisappearing = false;
    private int phaseOfDisappear = 0;

    private boolean bookmarked = false;
    private boolean notebook = false;


    Book(int id, int positionNumber, BookHandler bookHandler, BookContainer bookContainer) {
        super(bookHandler,positionNumber);
        this.id = id;
        if (new RandomXS128().nextInt(35) == 0) bookmarked = true;
        if (new RandomXS128().nextInt(300) == 0) notebook = true;
        setBookContainerF(bookContainer);
        setZIndex(100);
        setSize(bookContainer.getBookWidth(), bookContainer.getBookHeight());
    }

    @Override
    public void act(float delta) {
        if (isDisappearing)
            animateDisappearing(delta);
        super.act(delta);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(TextureService.getBookTextureById(id), getRealX(), getRealY(),
            getOriginX(), getOriginY(),
            getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        if (needsStoneTexture) {
            batch.draw(TextureService.getStoneTexture(), getRealX(), getRealY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }

    @Override
    public int compareTo(Book book) {
        return Integer.compare(getPositionNumber(), book.getPositionNumber());
    }


    public int getId() {
        return id;
    }

    public Array<Book> getGoodNeighbours() {
        return getBookContainer().getSimilarNeighbours(this);
    }

    public void replacePosition(int positionNumber) {
        Vector2 c = getPosition();
        setPositionNumber(positionNumber);
        setPosition(c);
        startReturning();
    }

    public void animateDisappearing(float f) {
        if (phaseOfDisappear == 0)
            setScaleY(getScaleY() - f * 4);
        else if (phaseOfDisappear == 1) {
            setScaleY(getScaleY() + f * 5);
            setScaleX(getScaleX() - f * 5);
            setRealX(getRealX() + f * 90);
        } else if (phaseOfDisappear == 2) {
            setScaleY(0);
            setScaleX(0);
            remove();
            getBookContainer().commitRemove(this);
        }
        if (getScaleY() <= 0.3) phaseOfDisappear = 1;
        if (getScaleX() <= 0) phaseOfDisappear = 2;
    }

    public void disappear() {
        isDisappearing = true;
        canBeLifted = false;
    }

    public boolean shakeOutBookmark() {
        if (bookmarked) {
            bookmarked = false;
            return true;
        }
        return false;
    }

    public Vector2 shakeOutNote() {
        if (notebook) {
            notebook = false;
            return new Vector2(getRealX(), getRealY());
        }
        return null;
    }

    public void setCanBeLifted(boolean canBeLifted, boolean startBook, boolean needsStoneTexture) {
        this.canBeLifted = canBeLifted;
        this.startBook = startBook;
        this.needsStoneTexture = needsStoneTexture;
    }

    @Override
    public boolean canBeLifted() {
        return canBeLifted;
    }

    public boolean isStartBook() {
        return startBook;
    }

    public Array<Book> getTwoNeighbours() {
        return getBookContainer().getTwoNeighbours(this);
    }

    public void startVanish() {
        getBookContainer().changeBookType(this);
        remove();
    }

    public boolean isStone() {
        return needsStoneTexture;
    }

    public boolean isDisappearing() {
        return isDisappearing;
    }

    public boolean isBoomBook() {
        return false;
    }

    public boolean isVanishBook() {
        return false;
    }
}
