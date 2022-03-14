package com.bytevalue.books;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.ActorAdditor;
import com.bytevalue.BookshelfPositioner;
import java.util.HashSet;
import java.util.Set;

public class BookshelfCollection extends Actor implements BookshelfPositioner,BookHandler {

    private final int size=12;
    private final float w=36,h=163;
    private final int [] xPositions;
    private final Viewport viewport;
    private float backgroundY;
    private float bias;
    private final Texture texture;
    private final ActorAdditor actorAdditor;

    private final Array<Bookshelf> bookshelves;

    private final Array<Book> selectedBooks;
    private BookContainer selectedBooksContainer;
    private Bookshelf currentBookshelf;

    private boolean bookPressured =false;
    private boolean shifting =false;


    public BookshelfCollection(Viewport viewport, ActorAdditor actorAdditor){
        this.actorAdditor=actorAdditor;
        this.viewport=viewport;
        selectedBooks=new Array<>();
        texture=new Texture(Gdx.files.internal("shelf3.png"));
        backgroundY=0;
        xPositions=getXPositions();
        bookshelves=new Array<>();
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

        if(shifting){
            float velocity= (bias * delta *5);
            backgroundY-=velocity;
            bias-=velocity;
            if (backgroundY<=-texture.getHeight())backgroundY+=texture.getHeight();
            for (Bookshelf bookshelf:bookshelves){
                bookshelf.moveDown(velocity);
                if(bookshelf.getY()+150<0)reorganizeBookshelf(bookshelf);
            }
            if(bias<=1){
                backgroundY-=bias;
                bias=0;
                for (Bookshelf bookshelf:bookshelves){
                    bookshelf.moveDown(bias);
                    if(bookshelf.getY()+150<0)reorganizeBookshelf(bookshelf);
                }
                shifting=false;
                bookPressured=false;
            }
            return;
        }
        if (Gdx.input.isTouched()){
            Vector2 touch = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            if(bookPressured) {
                boolean findIntersection=false;
                for (Bookshelf bookshelf : bookshelves) {
                    if(bookshelf.checkCollisionAndAct(touch, selectedBooks.size)!=-1){
                        currentBookshelf=bookshelf;
                        findIntersection=true;
                    }
                }
                if (!findIntersection)currentBookshelf=null;
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture,0,backgroundY);
        batch.draw(texture,0,backgroundY+texture.getHeight());
    }

    @Override
    public void selectBook(Book book) {
        selectedBooks.clear();
        selectedBooks.add(book);
        selectedBooks.addAll(book.getGoodNeighbours());
        bookPressured=true;
        for (Book selected:selectedBooks){
            selected.setSelected(true);
            selected.setZIndex(1000);
        }
        selectedBooksContainer=book.getBookContainer();
        selectedBooksContainer.collectBooks();
    }


    @Override
    public boolean isBookPressured() {
        return bookPressured;
    }

    @Override
    public boolean isShifting() {
        return shifting;
    }


    @Override
    public void release() {
        bookPressured=false;
        if(currentBookshelf==null){
            for (Book book:selectedBooks){
                book.startReturning();
                book.setSelected(false);
            }
            selectedBooksContainer.returnBooks(selectedBooks);
            Gdx.input.vibrate(40);
        }else {
            Array<Book> notfited=currentBookshelf.insertBooks(selectedBooks);
            for (Book book:notfited){
                book.startReturning();
                book.setSelected(false);
            }
            selectedBooksContainer.returnBooks(notfited);

            if (currentBookshelf.isDone())startBias(currentBookshelf.getStartPosition(0).y);
        }
        selectedBooks.clear();
    }

    public void startBias(float y){
        shifting=true;
        bookPressured=true;
        bias=y+213-20;
    }

    private void reorganizeBookshelf(Bookshelf bookshelf){
        Array<Book> newBooks=bookshelf.renew(bookshelf.getY()+213*7-1);
        for (Book book:newBooks)actorAdditor.addActor(book);
    }



}
