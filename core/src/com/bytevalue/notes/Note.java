package com.bytevalue.notes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.bytevalue.service.FontService;
import com.bytevalue.service.SoundService;
import com.bytevalue.service.TextureService;

public class Note extends Actor {

    private final GlyphLayout noteLayout;
    private final GlyphLayout authorLayout;
    private final GlyphLayout nameLayout;
    private final GlyphLayout numberLayout;

    private final String note;
    private final String noteEN;
    private final String author;
    private final String authorEN;
    private final String name;
    private final String nameEN;
    private final String number;

    private float x,y,a=1,sx=0.1f,sy=0.1f,yScale,h;
    private boolean found,ru;

    private boolean startAnim=false;

    public Note(String input,boolean ru) {
        this.ru=ru;
        input=input.substring(input.indexOf("='")+2);
        this.number = input.substring(0,input.indexOf("'"));
        input=input.substring(input.indexOf("='")+2);
        this.note = input.substring(0,input.indexOf("'"));
        input=input.substring(input.indexOf("='")+2);
        this.noteEN = input.substring(0,input.indexOf("'"));
        input=input.substring(input.indexOf("='")+2);
        this.name = input.substring(0,input.indexOf("'"));
        input=input.substring(input.indexOf("='")+2);
        this.nameEN = input.substring(0,input.indexOf("'"));
        input=input.substring(input.indexOf("='")+2);
        this.author = input.substring(0,input.indexOf("'"));
        input=input.substring(input.indexOf("='")+2);
        this.authorEN = input.substring(0,input.indexOf("'"));


        noteLayout =new GlyphLayout();
        authorLayout =new GlyphLayout();
        nameLayout =new GlyphLayout();
        numberLayout =new GlyphLayout();
        numberLayout.setText(FontService.getFont(),"#"+number +"  ",
                FontService.getNotesColor(),350, Align.center,false);

        noteLayout.setText(FontService.getMiddleFont(),getNote(),
                FontService.getNotesColor(),350, Align.center,true);
        authorLayout.setText(FontService.getSmallFont(),getAuthor(),
                FontService.getNotesColor(),350, Align.center,false);
        nameLayout.setText(FontService.getSmallFont(),getName(),
                FontService.getNotesColor(),350, Align.right,false);

        h=(100+authorLayout.height*8+noteLayout.height+nameLayout.height+numberLayout.height*2);
        yScale=h/400;

        setOrigin(TextureService.getNoteTexture().getRegionWidth()/2f,
                TextureService.getNoteTexture().getRegionHeight()/2f);
        setScale(sx);
        Preferences preferences = Gdx.app.getPreferences("notes");
        this.found=preferences.getBoolean(number);

    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(TextureService.getNoteTexture(),x,y,getOriginX(),getOriginY(),
                TextureService.getNoteTexture().getRegionWidth(),
                TextureService.getNoteTexture().getRegionHeight(),
                getScaleX(),getScaleY(),getRotation());
        if(!startAnim){
            FontService.getFont().draw(batch,numberLayout,467,590+h/2);
            FontService.getSmallFont().draw(batch,authorLayout,467,590-numberLayout.height*2+h/2);
            FontService.getMiddleFont().draw(batch,noteLayout,467,590+h/2-numberLayout.height*2-authorLayout.height*4);
            FontService.getSmallFont().draw(batch,nameLayout,467,590+h/2-numberLayout.height*2-authorLayout.height*8-noteLayout.height);
        }
    }

    public void startAnim(float x,float y){
        this.x=x-190;
        this.y=y-200;
        a=1;
        sx=0.1f;
        sy=0.1f;
        startAnim=true;
        setScale(sx);
        SoundService.playPaperSound();
    }

    @Override
    public void act(float delta) {
        if(!startAnim)return;
        x+=delta*(450-x)*4;
        y+=delta*(640-200-y)*4;
        sx+=(1-sx)*delta*5;
        sy+=(yScale-sy)*delta*5;
        a+=(180-a)*delta*6;
        setRotation(a);
        setScaleX(sx);
        setScaleY(sy);
        if (Math.abs(450 - x) < 1&&Math.abs(440 - y) < 1&&Math.abs(180-a)<1) {
            startAnim=false;
            setRotation(0);
            setScaleX(1);
            setScaleY(yScale);
            x=450;
            y=640-200;
        }
    }

    public String getNote() {
        if (ru) return note;
        else return noteEN;
    }

    public String getAuthor() {
        if(ru) return author;
        else return authorEN;
    }

    public String getName(){
        if (ru)return name;
        else return nameEN;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found){
        this.found=found;
        Preferences preferences = Gdx.app.getPreferences("notes");
        preferences.putBoolean(number,found);
        preferences.flush();
    }

    public void setRu(boolean ru) {
        this.ru = ru;
        noteLayout.setText(FontService.getMiddleFont(),getNote(),
                FontService.getNotesColor(),350, Align.center,true);
        authorLayout.setText(FontService.getSmallFont(),getAuthor(),
                FontService.getNotesColor(),350, Align.center,false);
        nameLayout.setText(FontService.getSmallFont(),getName(),
                FontService.getNotesColor(),350, Align.right,false);
        numberLayout.setText(FontService.getFont(),"#"+number+ "  ",
                FontService.getNotesColor(),350, Align.center,false);

        h=(100+authorLayout.height*8+noteLayout.height+nameLayout.height+numberLayout.height*2);
        yScale=h/400;
    }

    public boolean isAnim(){
        return startAnim;
    }
}
