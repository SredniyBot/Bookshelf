package com.bytevalue.books;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Score extends Actor {
    private BitmapFont font;
    private Integer score=0;
    private String print;
    private Integer increasePart=0;
    private long time=0;

    public Score(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 35;
        Color color = Color.valueOf("#c0a588");
        parameter.color= color;
        parameter.borderColor=color;
        parameter.shadowColor=color;
        print=String.valueOf(score);
        font = generator.generateFont(parameter);
        generator.dispose();
        setZIndex(500);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.draw(batch,print,400,1250);
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
}
