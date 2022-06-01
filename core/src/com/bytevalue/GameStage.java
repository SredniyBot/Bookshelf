package com.bytevalue;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.books.ActorAdder;
import com.bytevalue.books.Book;
import com.bytevalue.books.BookHandler;
import com.bytevalue.books.Bookshelf;
import com.bytevalue.books.BookshelfCollection;

public class GameStage extends Stage implements ActorAdder {

    BookshelfCollection bookshelfCollection;
    Viewport viewport;
    ActivitySwitcher activitySwitcher;


    public GameStage(Viewport viewport,ActivitySwitcher activitySwitcher) {
        this.viewport=viewport;
        this.activitySwitcher=activitySwitcher;
        setViewport(viewport);

        BookHandler bookHandler = new BookHandler();
        addActor(bookHandler);
        bookshelfCollection =new BookshelfCollection(viewport,this,bookHandler);
        addActor(bookshelfCollection);

        Bookshelf bookshelf0= bookshelfCollection.generateBookshelf(10);
        Bookshelf bookshelf1= bookshelfCollection.generateBookshelf(210);
        Bookshelf bookshelf2= bookshelfCollection.generateBookshelf(410);
        Bookshelf bookshelf3= bookshelfCollection.generateBookshelf(610);
        Bookshelf bookshelf4= bookshelfCollection.generateBookshelf(810);
        Bookshelf bookshelf5= bookshelfCollection.generateBookshelf(1010);


        Array<Book> books =new Array<>();
        books.addAll(bookshelf0.getBooks());
        books.addAll(bookshelf1.getBooks());
        books.addAll(bookshelf2.getBooks());
        books.addAll(bookshelf3.getBooks());
        books.addAll(bookshelf4.getBooks());
        books.addAll(bookshelf5.getBooks());


        for (Book book:books){
            addActor(book);
        }
    }

    public void pause(){
        activitySwitcher.switchActivity();
    }

}
