package com.bytevalue.books;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Comparator;
import java.util.HashMap;

public class Locket extends Actor {

    private final Array<Bookshelf> bookshelves;

    Background background;

    private float bias;
    private boolean shifting =false;

    private final ActorAdder actorAdder;            //adds new books to stage
    private final BookGenerator bookGenerator;      //creates new books, balances game
    private final BookDisposer bookDisposer;        //constants of book positions
    private final BookHandler bookHandler;          //handle books while they are taken

    private int numberOfShelves=6;
    public Locket(Viewport viewport, ActorAdder actorAdder, BookHandler bookHandler){
        this.actorAdder = actorAdder;
        this.bookHandler=bookHandler;
        bookHandler.setBookshelfCollection(this);
        bookGenerator =new BookGenerator();
        bookshelves=new Array<>();
        bookDisposer = new BookDisposer(viewport);
        background = new Background(bookDisposer);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        background.draw(batch);
    }

    @Override
    public void act(float delta) {

        if(isShifting()){
            if(bias>1){
                float velocity= (bias * delta *5);
                bookDisposer.increaseBias(velocity);
                bias-=velocity;
            }else {
                bookDisposer.increaseBias(bias);
                bookDisposer.roundBias();
                bias=0;
                shifting=false;
            }
            for (Bookshelf bookshelf:bookshelves) {
                bookshelf.resetPositions();
                if (bookshelf.getY() + bookDisposer.getBookH() < 0) deleteBookshelf(bookshelf);
            }

        }
    }

    private void deleteBookshelf(Bookshelf bookshelf) {
        bookshelves.removeValue(bookshelf,false);
        bookshelf.dispose();
    }




    public void complete(Bookshelf bookshelf){
        bookshelves.sort(new Comparator<Bookshelf>() {
            @Override
            public int compare(Bookshelf b1, Bookshelf b2) {
                return Integer.compare((int) b1.getY(),(int) b2.getY());
            }
        });
        int s = bookshelves.indexOf(bookshelf,false);
        HashMap<Integer,Integer> map= new HashMap<>();
        for (int i=s+1;i<6;i++){
            for (Integer key:bookshelves.get(i).getBooksIds().keySet()){
                if (map.containsKey(key)){
                    map.put(key,map.get(key)+bookshelves.get(i).getBooksIds().get(key));
                }else {
                    map.put(key,bookshelves.get(i).getBooksIds().get(key));
                }
            }
        }

        Array<Array<Integer>> books = bookGenerator.generate(map,s+1);

        float y=bookshelf.getY();
        for (int i=0;i<=s;i++){
            Bookshelf newBookshelf=new Bookshelf(numberOfShelves++,bookDisposer,bookHandler,books.get(i));
            bookshelves.add(newBookshelf);
            for (Book book:newBookshelf.getBooks()) actorAdder.addActor(book);
        }
        startBias(y);
    }

    private void startBias(float y){
        shifting=true;
        bias=y+200-10;//TODO rewrite
    }


    public void generateBookshelves(){
        Array<Array<Integer>> newBooks =bookGenerator.generateNew(6);
        for (int i=0;i<6;i++){
            Bookshelf bookshelf=new Bookshelf(i,bookDisposer,bookHandler,newBooks.get(i));
            bookshelves.add(bookshelf);
            for (Book book:bookshelf.getBooks())actorAdder.addActor(book);
        }
    }




    public Array<Bookshelf> getBookshelves() {
        return bookshelves;
    }

    public boolean isShifting() {
        return shifting;
    }


    public BookDisposer getBookDisposer() {
        return bookDisposer;
    }
}
