package com.bytevalue;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.books.Book;
import com.bytevalue.books.Bookshelf;
import com.bytevalue.books.BookshelfCollection;

import java.util.Collection;

public class GameStage extends Stage {

    BookshelfCollection bookshelfCollection;
    Viewport viewport;
    public GameStage(Viewport viewport) {
        this.viewport=viewport;
        setViewport(viewport);


        bookshelfCollection =new BookshelfCollection(viewport);
        addActor(bookshelfCollection);

        Bookshelf bookshelf2= bookshelfCollection.generateBookshelf(228);

        Array<Book> books2 =bookshelf2.getBooks();

        for (Book book:books2){
            addActor(book);
        }

    }


}
