package ru.bytevalue.books;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.*;

import ru.bytevalue.inter.*;
import ru.bytevalue.notes.NotesCollection;
import ru.bytevalue.service.SoundService;

public class Locket extends Group {

    private final Array<Bookshelf> bookshelves;

    private final Background background;
    private final Score score;

    private float bias;
    private boolean shifting = false;

    private final BookGenerator bookGenerator;      //creates new books, balances game
    private final BookDisposer bookDisposer;        //constants of book positions
    private final BookHandler bookHandler;          //handle books while they are taken

    private final ActivitySwitcher activitySwitcher;
    private boolean startMenu = true;
    private int numberOfShelves = 6;

    private boolean hasStone = false;

    public Locket(Viewport viewport, BookHandler bookHandler, Score score, ActivitySwitcher activitySwitcher) {
        this.bookHandler = bookHandler;
        this.activitySwitcher = activitySwitcher;
        this.score = score;
        bookHandler.setBookshelfCollection(this);
        bookGenerator = new BookGenerator(score);
        bookshelves = new Array<>();
        bookDisposer = new BookDisposer(viewport);
        background = new Background(bookDisposer);
        background.setModeStartMenu(startMenu);
        addActor(background);
    }


    @Override
    public void act(float delta) {
        if (isShifting()) {
            if (bias > 1) {
                float velocity = (bias * delta * 5);
                bookDisposer.increaseBias(velocity);
                bias -= velocity;
            } else {
                bookDisposer.increaseBias(bias);
                bookDisposer.roundBias();
                bias = 0;
                shifting = false;
                if (startMenu) {
                    deactivateStartMenu();
                }
            }
            for (Bookshelf bookshelf : bookshelves) {
                bookshelf.resetPositions();
                if (bookshelf.getY() + bookDisposer.getBookH() < 0) deleteBookshelf(bookshelf);
            }
        }
        super.act(delta);
    }

    private void deleteBookshelf(Bookshelf bookshelf) {
        if (bookshelf.containsStone()) hasStone = false;
        bookshelves.removeValue(bookshelf, false);
        bookshelf.dispose();
    }

    public void complete() {
        bookshelves.sort(new Comparator<Bookshelf>() {
            @Override
            public int compare(Bookshelf b1, Bookshelf b2) {
                return Integer.compare((int) b1.getY(), (int) b2.getY());
            }
        });
        complete(bookshelves.get(bookshelves.size - 1));
    }

    public void complete(Bookshelf bookshelf) {
        bookshelves.sort(new Comparator<Bookshelf>() {
            @Override
            public int compare(Bookshelf b1, Bookshelf b2) {
                return Integer.compare((int) b1.getY(), (int) b2.getY());
            }
        });
        int s = bookshelves.indexOf(bookshelf, false);

        HashMap<Integer, Integer> map = getBookStatisticsStartsFromId(s + 1);

        Array<Array<Integer>> books = bookGenerator.generate(map, s + 1);
        score.increaseScore(s + 1);
        float y = bookshelf.getY();


        Array<Bookshelf> newBookshelves = new Array<>();
        for (int i = 0; i <= s; i++) {
            Bookshelf newBookshelf = new Bookshelf(numberOfShelves++, bookDisposer, bookHandler, books.get(i), generateVanish(), generateBoom());
            newBookshelves.add(newBookshelf);
            for (Book book : newBookshelf.getBooks()) addActor(book);
        }
        addStone(newBookshelves);
        bookshelves.addAll(newBookshelves);
        startBias(y);
    }

    private void startBias(float y) {
        shifting = true;
        bias = y + 200 - 10;
        SoundService.playShelfMoveSound();
    }


    public void generateStartBookshelves() {
        Array<Integer> newBooks1 = new Array<>();
        Array<Integer> newBooks2 = new Array<>();
        for (int i = 0; i < 11; i++) newBooks1.add(0);
        newBooks2.add(1);
        newBooks2.add(0);
        Bookshelf bookshelf = new Bookshelf(5, bookDisposer, bookHandler, newBooks1, false, 0);
        Bookshelf bookshelf2 = new Bookshelf(0, bookDisposer, bookHandler, newBooks2, false, 0);
        bookshelves.add(bookshelf);
        bookshelves.add(bookshelf2);
        bookshelves.add(new Bookshelf(-3, bookDisposer, bookHandler, new Array<Integer>(), false, 0));
        bookshelves.add(new Bookshelf(-3, bookDisposer, bookHandler, new Array<Integer>(), false, 0));
        bookshelves.add(new Bookshelf(-3, bookDisposer, bookHandler, new Array<Integer>(), false, 0));
        bookshelves.add(new Bookshelf(-3, bookDisposer, bookHandler, new Array<Integer>(), false, 0));
        for (Book book : bookshelf.getBooks()) {
            addActor(book);
            book.setCanBeLifted(false, true, false);
        }
        for (Book book : bookshelf2.getBooks()) addActor(book);
    }


    public Array<Bookshelf> getBookshelves() {
        return bookshelves;
    }

    public boolean isShifting() {
        return shifting;
    }


    public void setModeStart() {
        bookHandler.setWaiting(true);
        bookHandler.renew();
        for (int i = bookshelves.size - 1; i >= 0; i--) deleteBookshelf(bookshelves.get(i));
        startMenu = true;
        background.setModeStartMenu(true);
        numberOfShelves = 6;
        score.toZero();
        bookDisposer.toZero();
        bookGenerator.toZero();
        bias = 0;
        shifting = false;
        generateStartBookshelves();
        bookHandler.setWaiting(false);
    }


    public HashMap<Integer, Integer> getBookStatisticsStartsFromId(int startId) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = startId; i < 6; i++) {
            for (Integer key : bookshelves.get(i).getBooksIds().keySet()) {
                if (map.containsKey(key)) {
                    map.put(key, map.get(key) + bookshelves.get(i).getBooksIds().get(key));
                } else {
                    map.put(key, bookshelves.get(i).getBooksIds().get(key));
                }
            }
        }
        return map;
    }


    public void deactivateStartMenu() {
        startMenu = false;
        background.setModeStartMenu(false);
        activitySwitcher.switchActivity(Activity.GAME);
    }

    public boolean isStartMenu() {
        return startMenu;
    }


    public void addStone(Array<Bookshelf> bookshelves) {
        if (hasStone) return;
        if (score.getScore() / 50 < new Random().nextInt(10)) return;
        Bookshelf b = bookshelves.random();
        if (b.getBooks().size == 0) return;
        b.getBooks().random().setCanBeLifted(false, false, true);
        hasStone = true;
    }

    public void spawnNewNote(Vector2 noteFromBook) {
        activitySwitcher.openNote(NotesCollection.newRandomNote(), noteFromBook.x, noteFromBook.y);
    }

    public void lose() {

        activitySwitcher.loseGame(score);
        setModeStart();
    }

    public boolean generateVanish() {
        return (Math.min(score.getScore() / 100 + 1, 10) > new Random().nextInt(20));
    }

    public int generateBoom() {
        int count = 0;
        if (score.getScore() < 80) {
            if (new Random().nextInt(20) == 0) count = 1;
        } else if (score.getScore() < 200) {
            if (new Random().nextInt(15) == 0) count = 1;
            if (new Random().nextInt(30) == 0) count = 2;
        } else if (score.getScore() < 350) {
            if (new Random().nextInt(10) == 0) count = 1;
            if (new Random().nextInt(20) == 0) count = 2;
            if (new Random().nextInt(50) == 0) count = 3;
        } else if (score.getScore() < 600) {
            if (new Random().nextInt(8) == 0) count = 1;
            if (new Random().nextInt(15) == 0) count = 2;
            if (new Random().nextInt(30) == 0) count = 3;
            if (new Random().nextInt(50) == 0) count = 4;
        } else count = new Random().nextInt(5);
        return count;
    }

}
