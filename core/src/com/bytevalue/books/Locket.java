package com.bytevalue.books;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bytevalue.inter.Activity;
import com.bytevalue.inter.ActivitySwitcher;

import java.util.Comparator;
import java.util.HashMap;

public class Locket extends Group {

    private final Array<Bookshelf> bookshelves;

    private final Background background;
    private Score score;

    private float bias;
    private boolean shifting =false;

    private final BookGenerator bookGenerator;      //creates new books, balances game
    private final BookDisposer bookDisposer;        //constants of book positions
    private final BookHandler bookHandler;          //handle books while they are taken

    private ActivitySwitcher activitySwitcher;
    private boolean startMenu=true;
    private int numberOfShelves=6;

    public Locket(Viewport viewport, BookHandler bookHandler, Score score, ActivitySwitcher activitySwitcher){
        this.bookHandler=bookHandler;
        this.activitySwitcher=activitySwitcher;
        this.score=score;
        bookHandler.setBookshelfCollection(this);
        bookGenerator =new BookGenerator();
        bookshelves=new Array<>();
        bookDisposer = new BookDisposer(viewport);
        background = new Background(bookDisposer);
        background.setModeStartMenu(startMenu);
        addActor(background);
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
                if (startMenu){
                    deactivateStartMenu();
                }
            }
            for (Bookshelf bookshelf:bookshelves) {
                bookshelf.resetPositions();
                if (bookshelf.getY() + bookDisposer.getBookH() < 0) deleteBookshelf(bookshelf);
            }
        }
        super.act(delta);
    }

    private void deleteBookshelf(Bookshelf bookshelf) {
        bookshelves.removeValue(bookshelf,false);
        bookshelf.dispose();
    }



    public void complete(){
        bookshelves.sort(new Comparator<Bookshelf>() {
            @Override
            public int compare(Bookshelf b1, Bookshelf b2) {
                return Integer.compare((int) b1.getY(),(int) b2.getY());
            }
        });
        complete(bookshelves.get(bookshelves.size-1));
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
        score.increaseScore(s+1);
        float y=bookshelf.getY();
        for (int i=0;i<=s;i++){
            Bookshelf newBookshelf=new Bookshelf(numberOfShelves++,bookDisposer,bookHandler,books.get(i));
            bookshelves.add(newBookshelf);
            for (Book book:newBookshelf.getBooks()) addActor(book);
        }
        startBias(y);
    }

    private void startBias(float y){
        shifting=true;
        bias=y+200-10;
    }


    public void generateBookshelves(){
        Array<Array<Integer>> newBooks =bookGenerator.generateNew(6);
        for (int i=0;i<6;i++){
            Bookshelf bookshelf=new Bookshelf(i,bookDisposer,bookHandler,newBooks.get(i));
            bookshelves.add(bookshelf);
            for (Book book:bookshelf.getBooks())    addActor(book);
        }
    }

    public void generateStartBookshelves(){
        Array<Integer> newBooks1 = new Array<>();
        Array<Integer> newBooks2 = new Array<>();
        for (int i=0;i<11;i++)newBooks1.add(0);
        newBooks2.add(1);
        newBooks2.add(0);
        Bookshelf bookshelf=new Bookshelf(5,bookDisposer,bookHandler,newBooks1);
        Bookshelf bookshelf2=new Bookshelf(0,bookDisposer,bookHandler,newBooks2);
        bookshelves.add(bookshelf);
        bookshelves.add(bookshelf2);
        bookshelves.add(new Bookshelf(-3,bookDisposer,bookHandler,new Array<Integer>()));
        bookshelves.add(new Bookshelf(-3,bookDisposer,bookHandler,new Array<Integer>()));
        bookshelves.add(new Bookshelf(-3,bookDisposer,bookHandler,new Array<Integer>()));
        bookshelves.add(new Bookshelf(-3,bookDisposer,bookHandler,new Array<Integer>()));
        for (Book book:bookshelf.getBooks()) {
            addActor(book);
            book.setCanBeLifted(false);
        }
        for (Book book:bookshelf2.getBooks()) addActor(book);
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


    public void setModeStart() {
        bookHandler.setWaiting(true);
        for (int i=bookshelves.size-1;i>=0;i--)deleteBookshelf(bookshelves.get(i));
        startMenu=true;
        background.setModeStartMenu(true);
        numberOfShelves=6;
        score.toZero();
        bookDisposer.toZero();
        bookGenerator.toZero();
        bias=0;
        shifting=false;
        generateStartBookshelves();
        bookHandler.setWaiting(false);
    }

    public void deactivateStartMenu(){
        startMenu=false;
        background.setModeStartMenu(false);
        activitySwitcher.switchActivity(Activity.GAME);
    }

    public boolean isStartMenu() {
        return startMenu;
    }
}
