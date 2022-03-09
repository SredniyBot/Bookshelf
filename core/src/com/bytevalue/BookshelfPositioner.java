package com.bytevalue;

import com.badlogic.gdx.utils.viewport.Viewport;

public interface BookshelfPositioner {
    int[] getPositions();
    int getBookshelfSize();
    Viewport getViewport();
    float getBookWidth();
    float getBookHeight();
}
