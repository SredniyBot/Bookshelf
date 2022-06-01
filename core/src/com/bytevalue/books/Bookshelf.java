package com.bytevalue.books;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class Bookshelf implements BookContainer {

    private final Rectangle bookshelfRectangle;

    private final BookDisposer bookDisposer;
    private final BookHandler bookHandler;
    private boolean induced=false;
    private int shift=1000;

    private Array<BookPosition> bookPositions;

    private Array<Book> books;

    Bookshelf(float y, BookDisposer bookDisposer, BookHandler bookHandler){
        this.bookDisposer = bookDisposer;
        this.bookHandler=bookHandler;
        bookshelfRectangle =new Rectangle();
        bookPositions=createBookPositions();
        resetPositions(y);


        fillWithRandomBooks(littleRandom());            //TODO rewrite creation of books
    }

    public Array<Book> renew(float y){
        deleteBooks();
        resetPositions(y);
        fillWithRandomBooks(littleRandom());
        return getBooks();
    }

    private void deleteBooks(){
        for (Book book:books){
            book.remove();
        }
        books.clear();
    }

    private int[] littleRandom(){
        int s=new Random().nextInt(6)+2;
        int [] r=new int[s];
        for (int i=0;i<s;i++){
            r[i]=new Random().nextInt(4)+1;
        }
        return r;
    }

    private void fillWithRandomBooks(int...numberOfBooks){
        books=new Array<>();
        int i=0;
        int id=0;
        for (int num:numberOfBooks){
            if (i>= bookDisposer.getBookshelfSize())break;
            for (int q=0;q<num;q++){
                if (i>= bookDisposer.getBookshelfSize())break;
                Book book=new Book(id,i,bookHandler,this);
                book.setViewport(bookDisposer.getViewport());
                book.setVisible(true);
                books.add(book);
                i++;
            }
            id++;
        }
        shuffle();
    }

    private Array<BookPosition> createBookPositions(){
        Array<BookPosition> bookPositions =new Array<>();
        for(int i = 0; i< bookDisposer.getBookshelfSize(); i++){
            bookPositions.add(new BookPosition(bookDisposer.getPositions()[i],0,
                    (int) bookDisposer.getBookWidth(),(int) bookDisposer.getBookHeight()));
        }
        return bookPositions;
    }

    public void resetPositions(float y){
        bookshelfRectangle.set(bookDisposer.getPositions()[0],y,
                bookDisposer.getBookWidth()* bookDisposer.getBookshelfSize(),
                bookDisposer.getBookHeight());
        for (int i=0;i<bookPositions.size;i++){
            bookPositions.get(i).set(bookDisposer.getPositions()[i],
                    y,
                    bookDisposer.getBookWidth(),
                    bookDisposer.getBookHeight());
        }
    }

    public void shuffle(){
        Array<Integer> positions=new Array<>();
        for (int i = 0; i< bookDisposer.getBookshelfSize(); i++){
            positions.add(i);
        }
        positions.shuffle();
        for (int i=0;i<books.size;i++){
            books.get(i).replacePosition(positions.get(i));
        }
        collectBooks();
    }

    @Override
    public Vector2 getStartPosition(int positionNumber) {
        return bookPositions.get(positionNumber).getStartPosition();
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
                if(shift!=pos){
                    shift=pos;
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
                if(shift!=pos){
                    shift=pos;
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
            Gdx.input.vibrate(10);
            for (Book book:books){
                if(book.getPositionNumber()>=pos)
                book.replacePosition(book.getPositionNumber()+size);
            }
        }

    }

    public Array<Book> insertBooks(Array<Book> selectedBooks) {
        collectBooks();
        shift=Math.min(shift,books.size);
        int bookBias=Math.min(selectedBooks.size, bookDisposer.getBookshelfSize()-books.size);
        for (Book book:books){
            if (book.getPositionNumber()>=shift){
                book.replacePosition(book.getPositionNumber()+bookBias);
            }
        }
        for (int i=bookBias-1;i>=0;i--){
            selectedBooks.get(i).setBookContainer(this);
            selectedBooks.get(i).replacePosition(shift+i);
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

    public void moveDown(float deltaY){
        resetPositions(bookshelfRectangle.y-deltaY);
        for (Book book:books)book.getPositionNumber();          //TODO ???
    }

    public float getY(){
        return bookshelfRectangle.y;
    }
}