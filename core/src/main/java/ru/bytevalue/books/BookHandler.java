package ru.bytevalue.books;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.*;

import ru.bytevalue.service.*;

public class BookHandler extends Actor {


    private final Array<Book> selectedBooks;
    private BookContainer containerOfSelectedBooks;
    private Bookshelf currentBookshelf;
    private boolean bookPressured = false;

    private boolean isWaiting = false;
    private final Viewport viewport;
    private Locket locket;

    private final Money money;

    public BookHandler(Viewport viewport, Money money) {
        this.money = money;
        this.viewport = viewport;
        selectedBooks = new Array<>();
    }

    public void setBookshelfCollection(Locket locket) {
        this.locket = locket;
    }

    public void selectBook(Book book) {
        VibrationService.vibrate(40);
        selectedBooks.clear();
        selectedBooks.add(book);
        selectedBooks.addAll(book.getGoodNeighbours());
        bookPressured = true;
        for (Book selected : selectedBooks) {
            if (selected.shakeOutBookmark())
                money.spawnPenny(selected.getRealX() + 1, selected.getRealY() + 140);
            selected.setSelected(true);
            selected.setZIndex(100000);
        }
        containerOfSelectedBooks = book.getBookContainer();
        containerOfSelectedBooks.alignBooks();
    }

    @Override
    public void act(float delta) {
        if (!isWaiting)
            if (Gdx.input.isTouched()) {
                Vector2 touch = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
                if (bookPressured) {
                    boolean intersects = false;
                    for (Bookshelf bookshelf : locket.getBookshelves()) {
                        if (bookshelf.checkCollisionAndAct(touch, selectedBooks.size) != -1) {//bookshelf is not full
                            currentBookshelf = bookshelf;
                            intersects = true;
                        }
                    }
                    if (!intersects) currentBookshelf = null;
                }
            }
    }

    public void release() {
        bookPressured = false;
        Vector2 noteFromBook = null;

        if (currentBookshelf == null) {
            for (Book book : selectedBooks) {
                book.startReturning();
                book.setSelected(false);
            }
            containerOfSelectedBooks.returnBooks(selectedBooks);
            VibrationService.vibrate(40);
        } else {
            for (Book book : selectedBooks) {
                Vector2 v = book.shakeOutNote();
                if (v != null) noteFromBook = v;
            }
            Array<Book> extraBooks = currentBookshelf.insertBooks(selectedBooks);
            for (Book book : extraBooks) {
                book.startReturning();
                book.setSelected(false);
            }
            containerOfSelectedBooks.returnBooks(extraBooks);

            if (currentBookshelf.isDone()) {
                locket.complete(currentBookshelf);
                noteFromBook = null;
            }
        }
        selectedBooks.clear();
        currentBookshelf = null;

        SoundService.playStandSound();

        if (noteFromBook != null && !locket.isStartMenu()) {
            locket.spawnNewNote(noteFromBook);
        }
    }

    public HashMap<Integer, Integer> getBookshelfStatistics() {
        HashMap<Integer, Integer> map = locket.getBookStatisticsStartsFromId(0);
        for (Integer key : getBooksIds().keySet()) {
            if (map.containsKey(key)) {
                map.put(key, map.get(key) + getBooksIds().get(key));
            } else {
                map.put(key, getBooksIds().get(key));
            }
        }
        return map;
    }

    private HashMap<Integer, Integer> getBooksIds() {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (Book book : selectedBooks) {
            if (map.containsKey(book.getId())) {
                map.put(book.getId(), map.get(book.getId()) + 1);
            } else {
                map.put(book.getId(), 1);
            }
        }
        return map;
    }

    public boolean isBookPressured() {
        return bookPressured;
    }

    public boolean isShifting() {
        return locket.isShifting();
    }

    public void setWaiting(boolean waiting) {
        isWaiting = waiting;
    }

    public Array<Book> getTwoRandomBooks() {
        Array<Book> books = new Array<>();
        Array<Book> res = new Array<>();
        for (Bookshelf bookshelf : locket.getBookshelves()) books.addAll(bookshelf.getBooks());
        Iterator<Book> bookIterator = books.iterator();
        books.shuffle();
        int count = 0;
        while (bookIterator.hasNext()) {
            Book book = bookIterator.next();
            if (!(book.isDisappearing() || book.isVanishBook() || book.isBoomBook())) {
                count++;
                res.add(book);
                if (count == 2) break;
            }
        }

        return res;
    }

    public void addActor(Book book) {
        locket.addActor(book);
    }

    public void checkLose() {
        for (Integer in : getBookshelfStatistics().values()) {
            if (in == 12) return;
        }
        locket.lose();
    }

    public void renew() {
        for (Book book : selectedBooks) book.remove();
        selectedBooks.clear();
        bookPressured = false;
    }
}

