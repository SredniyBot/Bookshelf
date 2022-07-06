package com.bytevalue.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

public class TextureService { //TODO

    public static List<TextureRegion> booksTextureRegions;

    public static Texture bookshelfTexture;
    public static Texture prefLogoTexture;
    public static Texture pauseMenuTexture;
    public static Texture settingsTexture;
    public static int w=35,h=160;

    static {
        Texture booksTexture =new Texture(Gdx.files.internal("books/books13.0.png"));
        booksTextureRegions=booksTextureRegionsInit(booksTexture,w,h,5,35,17);
        prefLogoTexture = new Texture(Gdx.files.internal("prefs.png"));
        bookshelfTexture =new Texture(Gdx.files.internal("shelf4.png"));
        pauseMenuTexture=new Texture(Gdx.files.internal("pause.png"));
        settingsTexture=new Texture(Gdx.files.internal("settings.png"));
    }

    private static List<TextureRegion> booksTextureRegionsInit(Texture booksTexture,
            int w,int h,int paddingTop,int paddingLeft,int count){
        List<TextureRegion> textures = new ArrayList<>();
        for (int i=0;i<count;i++){
            textures.add(new TextureRegion(booksTexture,paddingLeft*i,paddingTop,w,h));
        }
        return textures;
    }


    public static TextureRegion getBookTextureById(int id){
        id=id%34;                                                                                   //TODO
        return booksTextureRegions.get(id);
    }

    public static TextureRegion getPauseTexture(){
        return new TextureRegion(prefLogoTexture,390,16,32,44);
    }

    public static TextureRegion getPauseMenuTexture(){
        return new TextureRegion(pauseMenuTexture,0,0,1280,1200);
    }

    public static TextureRegion getSettingsTexture(){
        return new TextureRegion(settingsTexture,0,0,1280,1200);
    }

    public static TextureRegion getVibrateTexture(boolean vibrate){
        if (vibrate)
            return new TextureRegion(prefLogoTexture,0,0,78,78);
        else
            return new TextureRegion(prefLogoTexture,156,0,78,78);
    }

    public static TextureRegion getSoundTexture(boolean vibrate){
        if (vibrate)
            return new TextureRegion(prefLogoTexture,78,0,78,78);
        else
            return new TextureRegion(prefLogoTexture,234,0,78,78);
    }

    public static TextureRegion getBackgroundTexture(){
        return new TextureRegion(bookshelfTexture,0,80,1280,1200);
    }

    public static TextureRegion getTopTexture(){
        return new TextureRegion(new Texture(Gdx.files.internal("top.png")),0,0,1280,80);
    }
}
