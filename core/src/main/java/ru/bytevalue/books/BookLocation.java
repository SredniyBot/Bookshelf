package ru.bytevalue.books;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class BookLocation extends Actor {

    private BookContainer bookContainer;
    private final Vector2 bias;
    private final BookHandler bookHandler;
    private int positionNumber;
    private boolean selected;
    private final Vector2 destination;

    private Viewport viewport;
    private boolean isTouchedBefore = false;

    private boolean isReturning = false;

    private final Rectangle rectangle;

    BookLocation(BookHandler bookHandler, int positionNumber) {
        this.bookHandler = bookHandler;
        this.positionNumber = positionNumber;
        bias = new Vector2(0, 0);
        destination = new Vector2(0, 0);
        rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());
    }


    public boolean moveTo(float delta, Vector2 endOffset) {
        float yDelta = endOffset.y - bias.y - getStartPosition().y;
        float xDelta = endOffset.x - bias.x - getStartPosition().x;
        if (Math.sqrt(xDelta * xDelta + yDelta * yDelta) >= 5) {
            bias.y += (yDelta * delta * 20);
            bias.x += (xDelta * delta * 20);
            resetRectangle();
            return false;
        } else {
            bias.y = endOffset.y - getStartPosition().y;
            bias.x = endOffset.x - getStartPosition().x;
            resetRectangle();
            return true;
        }
    }

    public boolean moveToHome(float delta) {
        if (Math.sqrt(bias.y * bias.y + bias.x * bias.x) >= 5) {
            bias.y -= (bias.y * delta * 20);
            bias.x -= (bias.x * delta * 20);
            resetRectangle();
            return false;
        } else {
            bias.y = 0;
            bias.x = 0;
            resetRectangle();
            return true;
        }
    }

    @Override
    public void act(float delta) {
        if (bookHandler.isShifting()) {
            moveToHome(delta);
            return;
        }
        if (isReturning) {
            isReturning = !moveToHome(delta);
            return;
        }
        if (Gdx.input.isTouched()) {
            Vector2 touch = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

            onScreenTouch(delta, touch);
            if (isTouchedBefore || contains(touch)) {
                if (!isTouchedBefore) onTouch();
                isTouchedBefore = true;
            } else {
                if (isTouchedBefore) {
                    onRelease();
                    isTouchedBefore = false;
                }
            }
        } else {
            if (isTouchedBefore) {
                onRelease();
                isTouchedBefore = false;
            }
        }
    }

    @Override
    @Deprecated
    public void setX(float x) {
        super.setX(x);
    }

    @Override
    @Deprecated
    public void setY(float y) {
        super.setY(y);

    }

    @Override
    public void setPosition(float x, float y) {
        bias.set(x - getStartPosition().x, y - getStartPosition().y);
        resetRectangle();
    }

    public void setPosition(Vector2 vector2) {
        bias.set(vector2.x - getStartPosition().x, vector2.y - getStartPosition().y);
        resetRectangle();
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width);
        resetRectangle();
    }

    @Override
    public void setHeight(float height) {
        super.setHeight(height);
        resetRectangle();
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        resetRectangle();
    }

    public void setBookContainer(BookContainer bookContainer) {
        bias.set(getRealX() - bookContainer.getStartPosition(getPositionNumber()).x,
            getRealY() - bookContainer.getStartPosition(getPositionNumber()).y);
        this.bookContainer = bookContainer;

    }

    public void setBookContainerF(BookContainer bookContainer) {
        this.bookContainer = bookContainer;

    }

    public void setRealX(float x) {
        rectangle.x = x;
    }

    public void onRelease() {
        if (selected) ///TODO
            bookHandler.release();
    }

    public void onTouch() {
        if (canBeLifted())
            if (!bookHandler.isBookPressured()) {
                bookHandler.selectBook((Book) this);
            }
    }

    public void onScreenTouch(float delta, Vector2 touch) {
        if (selected) {
            destination.set(touch.x - getWidth() / 2, touch.y - getHeight() / 2);
            moveTo(delta, destination);
        }
    }

    public int getPositionNumber() {
        return positionNumber;
    }

    public void setPositionNumber(int positionNumber) {
        this.positionNumber = positionNumber;
    }

    public Vector2 getStartPosition() {
        return bookContainer.getStartPosition(getPositionNumber());
    }

    public float getRealX() {
        return rectangle.x;
    }

    public float getRealY() {
        return rectangle.y;
    }

    public Vector2 getPosition() {
        return new Vector2(getStartPosition().x + bias.x, getStartPosition().y + bias.y);
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public boolean contains(Vector2 vector2) {
        return rectangle.contains(vector2);
    }

    public void startReturning() {
        isReturning = true;
    }

    private void resetRectangle() {
        rectangle.set(getStartPosition().x + bias.x, getStartPosition().y + bias.y,
            getWidth(), getHeight());
    }

    public BookContainer getBookContainer() {
        return bookContainer;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }


    public BookHandler getBookHandler() {
        return bookHandler;
    }
    public abstract boolean canBeLifted();

}
