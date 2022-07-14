package com.bytevalue.books;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bytevalue.service.FontService;

public class Score extends Actor {

    private Integer score=0;
    private String print;
    private Integer increasePart=0;
    private long time=0;

    public Score(){
        print=String.valueOf(score);
        setZIndex(500);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        FontService.getScoreFont().draw(batch,print,400,1250);
    }

    @Override
    public void act(float delta) {
        if(increasePart>0){
            if(System.currentTimeMillis()-time>80){
                time=System.currentTimeMillis();
                score++;
                increasePart--;
                print=String.valueOf(score);
            }
        }
    }

    public void increaseScore(int increasePart){
        this.increasePart+=increasePart;
    }

    public void toZero() {
        score=0;
        print=String.valueOf(score);
    }

    public int getScore() {
        return score;
    }
}
