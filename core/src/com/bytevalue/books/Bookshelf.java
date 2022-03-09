package com.bytevalue.books;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bytevalue.BookshelfPositioner;

public class Bookshelf implements BookContainer {

    private final Rectangle rectangle;
    private final BookshelfPositioner bookPositioner;
    private final BookHandler bookHandler;

    private Array<BookPosition> bookPositions;

    private Array<Book> books;

    Bookshelf(int y, BookshelfPositioner bookPositioner,BookHandler bookHandler){
        this.bookPositioner = bookPositioner;
        this.bookHandler=bookHandler;
        rectangle=new Rectangle();
        bookPositions=createBookPositions();
        resetPositions(y);
        fillWithRandomBooks(1,2,3,4);
    }

    private Array<BookPosition> createBookPositions(){
        Array<BookPosition> bookPositions =new Array<>();
        for(int i=0;i<bookPositioner.getBookshelfSize();i++){
            bookPositions.add(new BookPosition(bookPositioner.getPositions()[i],0,
                    (int)bookPositioner.getBookWidth(),(int)bookPositioner.getBookHeight()));
        }
        return bookPositions;
    }

    public void resetPositions(int y){
        rectangle.set(bookPositioner.getPositions()[0],y,
                bookPositioner.getBookWidth()*bookPositioner.getBookshelfSize(),
                bookPositioner.getBookHeight());
        for (int i=0;i<bookPositions.size;i++){
            bookPositions.get(i).set(bookPositioner.getPositions()[i],
                    y,
                    bookPositioner.getBookWidth(),
                    bookPositioner.getBookHeight());
        }
    }

    private void fillWithRandomBooks(int...numberOfBooks){
        books=new Array<>();
        int i=0;
        int id=0;
        for (int num:numberOfBooks){
            if (i>= bookPositioner.getBookshelfSize())break;
            for (int q=0;q<num;q++){
                if (i>= bookPositioner.getBookshelfSize())break;
                Book book=new Book(id,i,bookHandler,this);
                book.setViewport(bookPositioner.getViewport());
                book.setVisible(true);
                books.add(book);
                i++;
            }
            id++;
        }
    }


    @Override
    public Vector2 getStartPosition(int positionNumber) {
        return bookPositions.get(positionNumber).getStartPosition();
    }

    @Override
    public int getBookWidth() {
        return (int)bookPositioner.getBookWidth();
    }

    @Override
    public int getBookHeight() {
        return (int)bookPositioner.getBookHeight();
    }

    public Array<Book> getBooks() {
        return books;
    }

    public boolean checkCollisionAndAct(Vector2 touch){
        if(rectangle.contains(touch)){
            for (BookPosition bookPosition:bookPositions){
                if(bookPosition.contains(touch)){
                    return true;//TODO обработка наведения на полки
                }
            }

            return true;
        }
        return false;
    }

    public void move(){
        books.sort();
        for (int i=0;i<books.size;i++){
            books.get(i).setPositionNumber(i);
        }
    }

}