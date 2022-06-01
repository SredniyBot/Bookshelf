package com.bytevalue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureIds { //TODO

    public static Texture texture =new Texture(Gdx.files.internal("books3.0.png"));
    public static Texture texture1 =new Texture(Gdx.files.internal("shelf3.png"));
    public static int w=35,h=160;

    public static TextureRegion getBookTextureById(int id){
        int bias=40;
        switch (id){
            case 1:return new TextureRegion(texture,5,5,w,h);
            case 2:return new TextureRegion(texture,bias+5,5,w,h);
            case 3:return new TextureRegion(texture,bias*2+5,5,w,h);
            case 4:return new TextureRegion(texture,bias*3+5,5,w,h);
            case 5:return new TextureRegion(texture,bias*4+5,5,w,h);
            case 6:return new TextureRegion(texture,bias*5+5,5,w,h);
            case 7:return new TextureRegion(texture,bias*6+5,5,w,h);
            case 8:return new TextureRegion(texture,bias*7+5,5,w,h);
            case 9:return new TextureRegion(texture,bias*8+5,5,w,h);
            case 10:return new TextureRegion(texture,bias*9+5,5,w,h);
            case 11:return new TextureRegion(texture,bias*10+5,5,w,h);
            default:return new TextureRegion(texture,bias*11+5,5,w,h);
        }
    }

    public static TextureRegion getTextureByStr(String name){
        Texture texture =new Texture(Gdx.files.internal("books2.png"));
        switch (name) {
            case "":return new TextureRegion(texture,0,0,0,0);
            default:return new TextureRegion(texture,1,1,1,1);
        }
    }

    public static TextureRegion getBackgroundTexture(){
        return new TextureRegion(texture1,0,80,1280,1200);
    }
    public static TextureRegion getTopTexture(){
        return new TextureRegion(new Texture(Gdx.files.internal("top.png")),0,0,1280,80);
    }
}
