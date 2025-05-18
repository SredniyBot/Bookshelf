package ru.bytevalue.books;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.Align;
import ru.bytevalue.service.*;

public class Money extends Group {

    private Integer count;
    private final GlyphLayout layout;

    public Money(){
        Preferences preferences =Gdx.app.getPreferences("money");
        count=preferences.getInteger("count",0);
        layout=new GlyphLayout();
        layout.setText(FontService.getFont(),String.valueOf(count), FontService.getMoneyColor(),200,Align.left,false);
        setZIndex(500);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        FontService.getFont().draw(batch,layout,430,1250);
        int xc = 390;
        int yc = 1216;
        batch.draw(TextureService.getBookmarkTexture(), xc, yc);
        super.draw(batch,parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void increaseMoney(){
        count++;
        layout.setText(FontService.getFont(),String.valueOf(count), FontService.getMoneyColor(),200,Align.left,false);
        Preferences preferences = Gdx.app.getPreferences("money");
        preferences.putInteger("count",count);
        preferences.flush();
    }

    public void spawnPenny(float x,float y){
        Penny penny=new Penny(x,y);
        addActor(penny);
    }


    private class Penny extends Actor{
        private final float x;
        private float y;
        private float a=1,b=30;
        private final float startY;

        Penny(float x,float y){
            this.x=x;
            this.y=y;
            startY=y;
            setOrigin(TextureService.getBookmarkTexture().getRegionWidth()/2f,
                    TextureService.getBookmarkTexture().getRegionHeight()/2f);
            SoundService.playPennySound();
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            batch.draw(TextureService.getBookmarkTexture(),x,y,getOriginX(),getOriginY(),
                    TextureService.getBookmarkTexture().getRegionWidth(),
                    TextureService.getBookmarkTexture().getRegionHeight(),
                    getScaleX(),getScaleY(),getRotation());
        }

        @Override
        public void act(float delta) {
            if(y>startY+30){

                if(30-a<1){
                    if(b<1){
                        increaseMoney();
                        remove();
                    }else {
                        b-=delta*b* 30;
                        setRotation(b);
                    }
                }else {
                    a+=delta*a* 10;
                    setRotation(a);
                }
            }else {
                y-=delta*((startY-30)-y)*3;
            }
        }
    }
}
