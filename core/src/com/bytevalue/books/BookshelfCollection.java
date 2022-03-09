package com.bytevalue.books;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.BookshelfPositioner;
import java.util.HashSet;
import java.util.Set;

public class BookshelfCollection extends Actor implements BookshelfPositioner,BookHandler {

    private final int size=12;
    private final float w=36,h=163;
    private final int [] xPositions;
    private final Viewport viewport;
    private int backgroundY;
    private final Texture texture;

    private final Set<Bookshelf> bookshelves;

    private final Array<Book> selectedBooks;
    private Book mainBook;

    private boolean bookPressured =false;


    public BookshelfCollection(Viewport viewport){
        this.viewport=viewport;
        selectedBooks=new Array<>();
        texture=new Texture(Gdx.files.internal("bookshelf.png"));
        backgroundY=0;
        xPositions=getXPositions();
        bookshelves=new HashSet<>();
    }

    public Bookshelf generateBookshelf(int y){
        Bookshelf bookshelf=new Bookshelf(y,this,this);
        bookshelves.add(bookshelf);
        return bookshelf;
    }

    private int[] getXPositions() {
        int [] pos = new int[size];
        for (int i=0;i<size;i++){
            pos[i]= (int) (texture.getWidth()/2-432/2+w*i);
        }
        return pos;
    }

    @Override
    public int[] getPositions() {
        return xPositions;
    }

    @Override
    public int getBookshelfSize() {
        return size;
    }

    @Override
    public Viewport getViewport() {
        return viewport;
    }

    @Override
    public float getBookWidth() {
        return w;
    }

    @Override
    public float getBookHeight() {
        return h;
    }


    @Override
    public void act(float delta) {
        if (Gdx.input.isTouched()){
//            if(Math.abs(Gdx.input.getDeltaX())<Math.abs(Gdx.input.getDeltaY())){
//                backgroundY-=Gdx.input.getDeltaY();
//
//                for (Bookshelf bookshelf:bookshelves)bookshelf.updateY(-Gdx.input.getDeltaY());
//                if (backgroundY>=0)
//                    backgroundY-=texture.getHeight();
//
//                if (backgroundY<=1280-texture.getHeight()*2)
//                    backgroundY+=texture.getHeight();
//            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture,0,backgroundY);
        batch.draw(texture,0,backgroundY+texture.getHeight());
    }

    @Override
    public void selectBook(Book book) {
        if(book.isSelected()){
            book.setZIndex(100);
            boolean canBeRaised=false;
            for (Book selected:selectedBooks){
                if(book.getPositionNumber()-1==selected.getPositionNumber()||
                        book.getPositionNumber()+1==selected.getPositionNumber()){
                    canBeRaised=true;
                    break;
                }
            }
            if (!canBeRaised) {
                for (Book selected : selectedBooks) {
                    selected.changeSelection();
                }
                selectedBooks.clear();
            }
            selectedBooks.add(book);
        }else {
            book.setZIndex(2);
            int more=0;
            int less=0;
            for (Book selected : selectedBooks){
                if (selected.getPositionNumber()>book.getPositionNumber())more++;
                if (selected.getPositionNumber()<book.getPositionNumber())less++;
            }
            selectedBooks.removeValue(book,false);
            if (more>less){
                for (int i=selectedBooks.size-1;i>=0;i--){
                    if(selectedBooks.get(i).getPositionNumber()<book.getPositionNumber()){
                        selectedBooks.get(i).changeSelection();
                        selectedBooks.removeIndex(i);
                    }
                }
            }else {
                for (int i=selectedBooks.size-1;i>=0;i--){
                    if(selectedBooks.get(i).getPositionNumber()>book.getPositionNumber()){
                        selectedBooks.get(i).changeSelection();
                        selectedBooks.removeIndex(i);
                    }
                }
            }
        }
    }


    @Override
    public boolean isBookPressured() {
        return false;
    }


    @Override
    public void setBookPressured(boolean bookPressured) {
        this.bookPressured=bookPressured;
    }

    @Override
    public void release() {
        bookPressured=false;
        for (Book book:selectedBooks)book.startReturning();
    }

    @Override
    public Vector2 getMainBookPosition() {
        return mainBook.getPosition();
    }

    @Override
    public void setMainBook(Book book) {
        mainBook = book;
    }


}
