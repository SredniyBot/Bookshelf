package com.bytevalue.books;

import com.badlogic.gdx.utils.viewport.Viewport;

public interface BookDisposer {
    int[] getPositions();
    int getBookshelfSize();
    Viewport getViewport();
    float getBookWidth();
    float getBookHeight();
}
