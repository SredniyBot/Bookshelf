package com.bytevalue.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

public class TextureService { //TODO

    public static List<TextureRegion> booksTextureRegions;

    public static Texture bookshelfTexture;
    public static int w=35,h=160;

    static {
        Texture booksTexture =new Texture(Gdx.files.internal("books13.0.png"));
        booksTextureRegions=booksTextureRegionsInit(booksTexture,w,h,5,35,17);

        bookshelfTexture =new Texture(Gdx.files.internal("shelf4.png"));



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
        id=id%34;                               //TODO
//        return new TextureRegion(texture,40*id+5,5,w,h);        //books2.0.png
//        return new TextureRegion(booksTexture,35*id,5,w,h);      //books5.0.png
        return booksTextureRegions.get(id);
    }

    public static TextureRegion getTextureByStr(String name){
        Texture texture =new Texture(Gdx.files.internal("books2.png"));
        switch (name) {
            case "":return new TextureRegion(texture,0,0,0,0);
            default:return new TextureRegion(texture,1,1,1,1);
        }
    }

    public static TextureRegion getBackgroundTexture(){
        return new TextureRegion(bookshelfTexture,0,80,1280,1200);
    }
    public static TextureRegion getTopTexture(){
        return new TextureRegion(new Texture(Gdx.files.internal("top.png")),0,0,1280,80);
    }
}
