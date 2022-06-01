package com.bytevalue.books;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public interface BookContainer {

    Vector2 getStartPosition(int positionNumber);

    Array<Book> getSimilarNeighbours(Book book);


    void collectBooks();
    void returnBooks(Array<Book> books);
    int getBookWidth();
    int getBookHeight();
}
