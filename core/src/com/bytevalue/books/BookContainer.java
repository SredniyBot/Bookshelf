package com.bytevalue.books;

import com.badlogic.gdx.math.Vector2;

public interface BookContainer {

    Vector2 getStartPosition(int positionNumber);

    int getBookWidth();
    int getBookHeight();
}
