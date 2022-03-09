package com.bytevalue.books;

import com.badlogic.gdx.math.Vector2;

public interface BookHandler {

    void selectBook(Book book);

    boolean isBookPressured();
    void setBookPressured(boolean bookPressured);
    void release();

    Vector2 getMainBookPosition();

    void setMainBook(Book book);

}
