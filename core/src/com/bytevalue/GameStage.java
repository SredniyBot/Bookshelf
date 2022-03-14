package com.bytevalue;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.books.Book;
import com.bytevalue.books.Bookshelf;
import com.bytevalue.books.BookshelfCollection;

import java.util.Collection;

public class GameStage extends Stage implements ActorAdditor{

    BookshelfCollection bookshelfCollection;
    Viewport viewport;
    ActivitySwitcher activitySwitcher;
    public GameStage(Viewport viewport,ActivitySwitcher activitySwitcher) {
        this.viewport=viewport;
        this.activitySwitcher=activitySwitcher;
        setViewport(viewport);


        bookshelfCollection =new BookshelfCollection(viewport,this);
        addActor(bookshelfCollection);

        Bookshelf bookshelf0= bookshelfCollection.generateBookshelf(15);
        Bookshelf bookshelf1= bookshelfCollection.generateBookshelf(229);
        Bookshelf bookshelf2= bookshelfCollection.generateBookshelf(442);
        Bookshelf bookshelf3= bookshelfCollection.generateBookshelf(656);
        Bookshelf bookshelf4= bookshelfCollection.generateBookshelf(869);
        Bookshelf bookshelf5= bookshelfCollection.generateBookshelf(1082);
        Bookshelf bookshelf6= bookshelfCollection.generateBookshelf(1295);

        Array<Book> books =new Array<>();
        books.addAll(bookshelf0.getBooks());
        books.addAll(bookshelf1.getBooks());
        books.addAll(bookshelf2.getBooks());
        books.addAll(bookshelf3.getBooks());
        books.addAll(bookshelf4.getBooks());
        books.addAll(bookshelf5.getBooks());
        books.addAll(bookshelf6.getBooks());

        for (Book book:books){
            addActor(book);
        }
    }


}
