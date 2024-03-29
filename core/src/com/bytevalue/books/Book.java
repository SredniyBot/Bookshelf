package com.bytevalue.books;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bytevalue.service.TextureService;

public class Book extends BookLocation implements Comparable<Book>{

    private final int id;
    private final BookHandler bookHandler;
    private int positionNumber;
    private boolean selected;

    private final Vector2 destination;

    private boolean canBeLifted =true;
    private boolean needsStoneTexture =false;
    private boolean startBook =false;

    private boolean isDisappearing=false;
    private int phaseOfDisappear = 0;

    private boolean bookmarked=false;
    private boolean notebook=false;



    Book(int id,int positionNumber,BookHandler bookHandler,BookContainer bookContainer){
        this.id=id;
        this.positionNumber=positionNumber;
        this.bookHandler = bookHandler;
        if (new RandomXS128().nextInt(35)==0)bookmarked=true;
        if (new RandomXS128().nextInt(300)==0)notebook=true;
        destination=new Vector2(0,0);
        setBookContainerF(bookContainer);
        setZIndex(100);
        setSize(bookContainer.getBookWidth(),bookContainer.getBookHeight());
    }

    @Override
    public void act(float delta) {
        if (isDisappearing)
            animateDisappearing(delta);
        if (bookHandler.isShifting()){
            moveToHome(delta);
            return;
        }
        super.act(delta);
    }


    @Override
    public void onRelease() {
        if (selected) ///TODO
        bookHandler.release();
    }

    @Override
    public void onTouch() {
        if(canBeLifted)
        if(!bookHandler.isBookPressured()) {
            bookHandler.selectBook(this);
        }
    }

    @Override
    public void onScreenTouch(float delta,Vector2 touch) {
        if (selected){
            destination.set(touch.x - getWidth() / 2, touch.y - getHeight() / 2);
            moveTo(delta,destination);
        }
    }


    @Override
    public void isPressed(Vector2 touch) {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(TextureService.getBookTextureById(id), getRealX(), getRealY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        if(needsStoneTexture){
            batch.draw(TextureService.getStoneTexture(), getRealX(), getRealY(),
                    getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }

    @Override
    public int compareTo(Book book) {
        return Integer.compare(getPositionNumber(),book.getPositionNumber());
    }

    public int getPositionNumber(){
        return positionNumber;
    }

    public void setPositionNumber(int positionNumber) {
        this.positionNumber = positionNumber;
    }

    public int getId() {
        return id;
    }

    public Array<Book> getGoodNeighbours(){
        return getBookContainer().getSimilarNeighbours(this);
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void replacePosition(int positionNumber){
        Vector2 c=getPosition();
        setPositionNumber(positionNumber);
        setPosition(c);
        startReturning();
    }

    public void animateDisappearing(float f){
        if (phaseOfDisappear ==0)
            setScaleY(getScaleY()-f*4);
        else if (phaseOfDisappear ==1){
            setScaleY(getScaleY()+f*5);
            setScaleX(getScaleX()-f*5);
            setRealX(getRealX()+f*90);
        } else if(phaseOfDisappear ==2){
            setScaleY(0);
            setScaleX(0);
            remove();
            getBookContainer().commitRemove(this);
        }
        if (getScaleY()<=0.3) phaseOfDisappear =1;
        if (getScaleX()<=0) phaseOfDisappear =2;

    }

    public void disappear(){
        isDisappearing=true;
        canBeLifted=false;
    }

    public boolean shakeOutBookmark() {
        if (bookmarked){
            bookmarked=false;
            return true;
        }
        return false;
    }

    public Vector2 shakeOutNote() {
        if (notebook){
            notebook=false;
            return new Vector2(getRealX(),getRealY());
        }
        return null;
    }

    public void setCanBeLifted(boolean canBeLifted,boolean startBook,boolean needsStoneTexture) {
        this.canBeLifted = canBeLifted;
        this.startBook = startBook;
        this.needsStoneTexture=needsStoneTexture;
    }

    public boolean canBeLifted() {
        return canBeLifted;
    }

    public boolean isStartBook() {
        return startBook;
    }

    public Array<Book> getTwoNeighbours(){
        return getBookContainer().getTwoNeighbours(this);
    }

    public void startVanish(){
        getBookContainer().changeBookType(this);
        remove();
    }

    public BookHandler getBookHandler() {
        return bookHandler;
    }

    public boolean isStone() {
        return needsStoneTexture;
    }

    public boolean isDisappearing() {
        return isDisappearing;
    }

    public  boolean isBoomBook(){
        return false;
    }
    public  boolean isVanishBook(){
        return false;
    }
}
