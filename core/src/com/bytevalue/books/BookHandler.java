package com.bytevalue.books;


public interface BookHandler {

    void selectBook(Book book);
    boolean isBookPressured();
    boolean isShifting();
    void release();


}
