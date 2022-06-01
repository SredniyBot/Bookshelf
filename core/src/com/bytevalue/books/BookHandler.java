package com.bytevalue.books;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.bytevalue.Vibrator;

public class BookHandler extends Actor {

    private Array<Book> selectedBooks;
    private BookContainer containerOfSelectedBooks;
    private Bookshelf currentBookshelf;
    private boolean bookPressured =false;

    private BookshelfCollection bookshelfCollection;

    public BookHandler(){
        selectedBooks=new Array<>();
    }

    public void setBookshelfCollection(BookshelfCollection bookshelfCollection) {
        this.bookshelfCollection = bookshelfCollection;
    }

    public void selectBook(Book book) {
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
        if (Gdx.input.isTouched()){
            Vector2 touch = bookshelfCollection.getViewport()
                    .unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            if(bookPressured) {
                boolean intersects=false;
                for (Bookshelf bookshelf : bookshelfCollection.getBookshelves()) {
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
            Vibrator.vibrate(40);
        }else {
            Array<Book> extraBooks=currentBookshelf.insertBooks(selectedBooks);
            for (Book book:extraBooks){
                book.startReturning();
                book.setSelected(false);
            }
            containerOfSelectedBooks.returnBooks(extraBooks);

            if (currentBookshelf.isDone())
                bookshelfCollection.startBias(currentBookshelf.getY());
        }
        selectedBooks.clear();
        currentBookshelf=null;
    }


    public boolean isBookPressured() {
        return bookPressured;
    }

    public boolean isShifting() {
        return bookshelfCollection.isShifting();
    }

}
