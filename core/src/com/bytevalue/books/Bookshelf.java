package com.bytevalue.books;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bytevalue.service.SoundService;
import com.bytevalue.service.VibrationService;

import java.util.HashMap;

public class Bookshelf implements BookContainer {

    private final Rectangle bookshelfRectangle;
    private final BookDisposer bookDisposer;
    private final BookHandler bookHandler;
    private boolean induced=false;
    private int bookShift =1000;
    private final int yIndex;

    private final Array<Rectangle> bookPositions;

    private Array<Book> books;


    Bookshelf(int yIndex, BookDisposer bookDisposer, BookHandler bookHandler,Array<Integer> newBooks){
        this.bookDisposer = bookDisposer;
        this.bookHandler=bookHandler;
        this.yIndex=yIndex;
        bookshelfRectangle =new Rectangle();
        bookPositions=createBookPositions();
        resetPositions();
        fillWithBooks(newBooks);
    }

    private void deleteBooks(){
        for (Book book:books){
            book.remove();
        }
        books.clear();
    }


    private void fillWithBooks(Array<Integer> input){
        books=new Array<>();
        int i=0;
        for (int num:input){
            Book book=new Book(num,i,bookHandler,this);
            book.setZIndex(1);
            book.setViewport(bookDisposer.getViewport());
            book.setVisible(true);
            books.add(book);
            i++;
        }
    }

    private Array<Rectangle> createBookPositions(){
        Array<Rectangle> bookPositions =new Array<>();
        for(int i = 0; i< bookDisposer.getBookshelfSize(); i++){
            bookPositions.add(new Rectangle(bookDisposer.getXPositions()[i],0,
                    (int) bookDisposer.getBookWidth(),(int) bookDisposer.getBookHeight()));
        }
        return bookPositions;
    }

    public void resetPositions(){
        bookshelfRectangle.set(bookDisposer.getXPositions()[0],bookDisposer.getYPosition(yIndex)+bookDisposer.getBias(),
                bookDisposer.getBookWidth()* bookDisposer.getBookshelfSize(),
                bookDisposer.getBookHeight());
        for (int i=0;i<bookPositions.size;i++){
            bookPositions.get(i).set(bookDisposer.getXPositions()[i],
                    bookDisposer.getYPosition(yIndex)+bookDisposer.getBias(),
                    bookDisposer.getBookWidth(),
                    bookDisposer.getBookHeight());
        }
    }

    @Override
    public Vector2 getStartPosition(int positionNumber) {
        return new Vector2(bookPositions.get(positionNumber).getX(),
                bookPositions.get(positionNumber).getY());
    }

    @Override
    public Array<Book> getSimilarNeighbours(Book book) {
        Array<Book> goodNeighbours=new Array<>();
        int pos= book.getPositionNumber()-1;
        boolean next=true;
        while (pos>=0&&next){
            if(books.get(pos).getId()==book.getId())goodNeighbours.add(books.get(pos));
            else next=false;
            pos--;
        }
        next=true;
        pos=book.getPositionNumber()+1;
        while (pos<books.size&&next){
            if(books.get(pos).getId()==book.getId())goodNeighbours.add(books.get(pos));
            else next=false;
            pos++;
        }
        return goodNeighbours;
    }

    @Override
    public int getBookWidth() {
        return (int) bookDisposer.getBookWidth();
    }

    @Override
    public int getBookHeight() {
        return (int) bookDisposer.getBookHeight();
    }

    @Override
    public void collectBooks(){
        Array<Book> b= new Array<>();
        for (Book book:books){
            if (!book.isSelected())b.add(book);
        }
        b.sort();
        for (int i=0;i<b.size;i++){
            if (b.get(i).getPositionNumber()!=i)b.get(i).replacePosition(i);
        }
        books.clear();
        books.addAll(b);
    }

    @Override
    public void returnBooks(Array<Book> books) {
        if (books.size==0)return;
        books.sort();

        int minPos=books.get(0).getPositionNumber();
        for (int i=0;i<this.books.size;i++){
            if(this.books.get(i).getPositionNumber()>=minPos){
                this.books.get(i).replacePosition(i+books.size);
            }
        }
        for (int i=0;i<books.size;i++){
            books.get(i).replacePosition(i+minPos);
        }
        this.books.addAll(books);
        collectBooks();
    }

    public Array<Book> getBooks() {
        return books;
    }

    public int checkCollisionAndAct(Vector2 touch,int size){
        if(bookshelfRectangle.contains(touch)){
            if(size+books.size<= bookDisposer.getBookshelfSize()){
                int pos=getPositionCollision(touch);
                if(pos==-1)return -1;
                if(bookShift !=pos){
                    bookShift =pos;
                    induced=false;
                    collectBooks();
                }
                if(!induced){
                    induced=true;
                    moveBooks(pos,size);
                }
                return pos;
            }else if(books.size!= bookDisposer.getBookshelfSize()){
                int pos=getPositionCollision(touch);
                if(pos==-1)return -1;
                if(bookShift !=pos){
                    bookShift =pos;
                    induced=false;
                    collectBooks();
                }
                if(!induced){
                    induced=true;
                    moveBooks(pos, bookDisposer.getBookshelfSize()-books.size);
                }
                return pos;
            }
        }else {
            if (induced) {
                collectBooks();
                induced = false;
            }
        }
        return -1;
    }

    private int getPositionCollision(Vector2 touch) {
        for (int pos = 0; pos < bookPositions.size; pos++) {
            if (bookPositions.get(pos).contains(touch)) {
                return pos;
            }
        }
        return -1;
    }

    private void moveBooks(int pos,int size){
        if (pos<books.size){
            VibrationService.vibrate(10);
            SoundService.playMoveSound();
            for (Book book:books){
                if(book.getPositionNumber()>=pos)
                book.replacePosition(book.getPositionNumber()+size);
            }
        }
    }

    public Array<Book> insertBooks(Array<Book> selectedBooks) {
        collectBooks();
        bookShift =Math.min(bookShift,books.size);
        int bookBias=Math.min(selectedBooks.size, bookDisposer.getBookshelfSize()-books.size);
        for (Book book:books){
            if (book.getPositionNumber()>= bookShift){
                book.replacePosition(book.getPositionNumber()+bookBias);
            }
        }
        for (int i=bookBias-1;i>=0;i--){
            selectedBooks.get(i).setBookContainer(this);
            selectedBooks.get(i).replacePosition(bookShift +i);
            selectedBooks.get(i).setSelected(false);
            books.add(selectedBooks.get(i));
            selectedBooks.removeIndex(i);
        }
        collectBooks();
        return selectedBooks;
    }

    public boolean isDone(){
        int count=0;
        for (int i=0;i<books.size;i++){
            if(books.get(i).getId()==books.get(0).getId())count++;
        }
        return count == bookDisposer.getBookshelfSize();
    }

    public float getY(){
        return bookDisposer.getYPosition(yIndex)+bookDisposer.getBias();
    }

    public HashMap<Integer,Integer> getBooksIds(){
        HashMap<Integer,Integer> map = new HashMap<>();
        for (Book book:books){
            if(map.containsKey(book.getId())){
                map.put(book.getId(),map.get(book.getId())+1);
            }else {
                map.put(book.getId(),1);
            }
        }
        return map;
    }

    public void dispose() {
        deleteBooks();
    }
}