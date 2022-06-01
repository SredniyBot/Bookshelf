package com.bytevalue.books;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.BookSorter;
import com.bytevalue.GameStage;
import com.bytevalue.TextureIds;

public class BookshelfCollection extends Actor implements BookDisposer {

    private final Array<Bookshelf> bookshelves;
    private final int bookshelfSize =12;
    private final float bookW =35, bookH =160;
    private final int [] xPositions;
    private final Viewport viewport;
    private float backgroundY;
    private float bias;
    private final TextureRegion backgroundTexture;
    private final TextureRegion topTexture;


    private final ActorAdder actorAdder;
    private final BookHandler bookHandler;
    private boolean shifting =false;


    public BookshelfCollection(Viewport viewport, ActorAdder actorAdder,BookHandler bookHandler){
        this.actorAdder = actorAdder;
        this.viewport=viewport;
        this.bookHandler=bookHandler;
        bookHandler.setBookshelfCollection(this);

        bookshelves=new Array<>();

        backgroundTexture = TextureIds.getBackgroundTexture();
        topTexture=TextureIds.getTopTexture();
        backgroundY=0;                                                           //TODO
        xPositions=getXPositions();
    }



    private int[] getXPositions() {
        int [] pos = new int[bookshelfSize];
        for (int i = 0; i< bookshelfSize; i++){
            pos[i]= (int) (backgroundTexture.getRegionWidth()/2-420/2+ bookW *i);
        }
        return pos;
    }

    @Override
    public int[] getPositions() {
        return xPositions;
    }

    @Override
    public int getBookshelfSize() {
        return bookshelfSize;
    }

    @Override
    public Viewport getViewport() {
        return viewport;
    }

    @Override
    public float getBookWidth() {
        return bookW;
    }

    @Override
    public float getBookHeight() {
        return bookH;
    }


    @Override
    public void act(float delta) {
        if(shifting){
            float velocity= (bias * delta *5);
            backgroundY-=velocity;
            bias-=velocity;
            if (backgroundY<=-backgroundTexture.getRegionHeight())backgroundY+= backgroundTexture.getRegionHeight();
            for (Bookshelf bookshelf:bookshelves){
                bookshelf.moveDown(velocity);
                if(bookshelf.getY()+ bookH <0)reorganizeBookshelf(bookshelf);
            }
            if(bias<=1){
                backgroundY-=bias;
                for (Bookshelf bookshelf:bookshelves){
                    bookshelf.moveDown(bias);
                    if(bookshelf.getY()+150<0)reorganizeBookshelf(bookshelf);
                }
                bias=0;
                shifting=false;
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(backgroundTexture,0,backgroundY);
        batch.draw(backgroundTexture,0,backgroundY+ backgroundTexture.getRegionHeight());
        batch.draw(topTexture,0, BookSorter.SCREEN_HEIGHT-topTexture.getRegionHeight());
    }


    public void startBias(float y){
        shifting=true;
        bias=y+200-10;//TODO rewrite
    }
    public Bookshelf generateBookshelf(int y){
        Bookshelf bookshelf=new Bookshelf(y,this,bookHandler);
        bookshelves.add(bookshelf);
        return bookshelf;
    }
    private void reorganizeBookshelf(Bookshelf bookshelf){      //TODO refactor
        Array<Book> newBooks=bookshelf.renew(bookshelf.getY()+200*6);
        for (Book book:newBooks) actorAdder.addActor(book);
    }

    public Array<Bookshelf> getBookshelves() {
        return bookshelves;
    }

    public boolean isShifting() {
        return shifting;
    }
}
