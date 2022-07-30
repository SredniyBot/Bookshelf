package com.bytevalue.books;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.bytevalue.service.FontService;

public class Score extends Actor {

    private Integer score=0;
    private Integer increasePart=0;
    private long time=0;
    private final GlyphLayout layout;
    private int y;

    public Score(int y){
        this.y=y;
        layout = new GlyphLayout();
        layout.setText(FontService.getFont(),String.valueOf(score), FontService.getScoreColor(),1280, Align.center,true);
        setZIndex(500);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        FontService.getFont().draw(batch,layout,0,y);//400
    }

    @Override
    public void act(float delta) {
        if(increasePart>0){
            if(System.currentTimeMillis()-time>80){
                time=System.currentTimeMillis();
                score++;
                increasePart--;
                layout.setText(FontService.getFont(),String.valueOf(score), FontService.getScoreColor(),1280, Align.center,true);
            }
        }
    }

    public void increaseScore(int increasePart){
        this.increasePart+=increasePart;
    }

    public void toZero() {
        score=0;
        layout.setText(FontService.getFont(),String.valueOf(score), FontService.getScoreColor(),1280, Align.center,true);
    }

    public int getScore() {
        return score;
    }
}
