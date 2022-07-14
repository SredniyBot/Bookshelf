package com.bytevalue.books;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.service.SoundService;
import com.bytevalue.service.VibrationService;

public class BookHandler extends Actor {


    private final Array<Book> selectedBooks;
    private BookContainer containerOfSelectedBooks;
    private Bookshelf currentBookshelf;
    private boolean bookPressured =false;

    private boolean isWaiting=false;
    private Viewport viewport;
    private Locket locket;

    public BookHandler(Viewport viewport){
        this.viewport=viewport;
        selectedBooks=new Array<>();
    }

    public void setBookshelfCollection(Locket locket) {
        this.locket = locket;
    }

    public void selectBook(Book book) {
        VibrationService.vibrate(40);
        selectedBooks.clear();
        selectedBooks.add(book);
        selectedBooks.addAll(book.getGoodNeighbours());
        bookPressured=true;
        for (Book selected:selectedBooks){
            selected.setSelected(true);
            selected.setZIndex(100000);
        }
        containerOfSelectedBooks =book.getBookContainer();
        containerOfSelectedBooks.collectBooks();
    }

    @Override
    public void act(float delta) {
        if (!isWaiting)
        if (Gdx.input.isTouched()){
            Vector2 touch = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            if(bookPressured) {
                boolean intersects=false;
                for (Bookshelf bookshelf : locket.getBookshelves()) {
                    if(bookshelf.checkCollisionAndAct(touch, selectedBooks.size)!=-1){//bookshelf is not full
                        currentBookshelf=bookshelf;
                        intersects=true;
                    }
                }
                if (!intersects)currentBookshelf=null;
            }
        }
    }

    public void release() {
        bookPressured=false;
        if(currentBookshelf==null){
            for (Book book:selectedBooks){
                book.startReturning();
                book.setSelected(false);
            }
            containerOfSelectedBooks.returnBooks(selectedBooks);
            VibrationService.vibrate(40);
        }else {
            Array<Book> extraBooks=currentBookshelf.insertBooks(selectedBooks);
            for (Book book:extraBooks){
                book.startReturning();
                book.setSelected(false);
            }
            containerOfSelectedBooks.returnBooks(extraBooks);

            if (currentBookshelf.isDone())
                locket.complete(currentBookshelf);
        }
        selectedBooks.clear();
        currentBookshelf=null;

        SoundService.playStandSound();
    }


    public boolean isBookPressured() {
        return bookPressured;
    }

    public boolean isShifting() {
        return locket.isShifting();
    }

    public void setWaiting(boolean waiting) {
        isWaiting = waiting;
    }
}

