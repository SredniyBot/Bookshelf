package com.bytevalue.books;

import com.badlogic.gdx.utils.viewport.Viewport;

public class BookDisposer {


    private final int bookshelfSize =12;
    private final float bookW =35, bookH =160;
    private final int distanceBetweenShelves =200,offsetFromBottomShelf=10;
    private final int [] xPositions;

    private float bias;

    private final Viewport viewport;


    BookDisposer(Viewport viewport){
        this.viewport = viewport;
        xPositions=calculateXPositions();
    }

    private int[] calculateXPositions() {
        int [] pos = new int[bookshelfSize];
        for (int i = 0; i< bookshelfSize; i++){
            pos[i]= (int) (1280/2-420/2+ bookW *i);
        }
        return pos;
    }


    public int[] getXPositions() {
        return xPositions;
    }

    public int getBookshelfSize() {
        return bookshelfSize;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public float getBookWidth() {
        return bookW;
    }

    public float getBookHeight() {
        return bookH;
    }

    public float getBookW() {
        return bookW;
    }

    public float getBookH() {
        return bookH;
    }

    public float getBias() {
        return bias;
    }

    public void increaseBias(float bias){
        this.bias-=bias;
    }

    public int getYPosition(int yIndex){
        return offsetFromBottomShelf+yIndex*distanceBetweenShelves;
    }

    public void roundBias() {
        bias=Math.round(bias);
    }
}
