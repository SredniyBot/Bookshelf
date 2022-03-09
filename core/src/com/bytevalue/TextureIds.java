package com.bytevalue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureIds {
    public static TextureRegion getTextureById(int id){
        Texture texture =new Texture(Gdx.files.internal("books2.png"));
        switch (id){
            case 1:return new TextureRegion(texture,0,0,36,163);
            case 2:return new TextureRegion(texture,36,0,36,163);
            case 3:return new TextureRegion(texture,36*2,0,36,163);
            case 4:return new TextureRegion(texture,36*3,0,36,163);
            case 5:return new TextureRegion(texture,36*4,0,36,163);
            case 6:return new TextureRegion(texture,36*5,0,36,163);
            case 7:return new TextureRegion(texture,36*6,0,36,163);
            case 8:return new TextureRegion(texture,36*7,0,36,163);
            case 9:return new TextureRegion(texture,36*8,0,36,163);
            case 10:return new TextureRegion(texture,36*9,0,36,163);
            case 11:return new TextureRegion(texture,36*10,0,36,163);
            default:return new TextureRegion(texture,36*11,0,36,163);
        }
    }

}
