package com.bytevalue.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class TextureService { //TODO

    public static Array<TextureRegion> booksTextureRegions;
    public static Map<String ,TextureRegion> textureRegionMap;

    public static Texture bookshelfTexture;
    public static Texture prefLogoTexture;
    public static Texture pauseMenuTexture;
    public static Texture settingsTexture;
    public static Texture booksTexture;
    public static Texture startTexture;
    public static Texture skinsTexture;

    private static boolean ru;

    public static int w=35,h=160,count;

    public static void init(Skin skin){
        dispose();
        count=skin.getBookCount();

        Preferences preferences=Gdx.app.getPreferences("main");
        ru=preferences.getBoolean("ru",false);

        booksTexture =new Texture(Gdx.files.internal(skin.getBookFile()));
        prefLogoTexture = new Texture(Gdx.files.internal(skin.getIconsFile()));
        bookshelfTexture =new Texture(Gdx.files.internal(skin.getShelfBackgroundFile()));
        pauseMenuTexture=new Texture(Gdx.files.internal(skin.getPauseFile(ru)));
        settingsTexture=new Texture(Gdx.files.internal(skin.getSettingsMenuFile(ru)));
        startTexture=new Texture(Gdx.files.internal(skin.getStartFile(ru)));
        skinsTexture=new Texture(Gdx.files.internal(skin.getSkinFile(ru)));

        booksTextureRegions=booksTextureRegionsInit(booksTexture,w,h,count);
        textureRegionMap=fillTextureRegionMap();
    }

    public static Map<String ,TextureRegion> fillTextureRegionMap(){
        Map<String,TextureRegion> res=new HashMap<>();
        res.put("pauseMenu",new TextureRegion(pauseMenuTexture,0,0,1280,1200));
        res.put("pause",new TextureRegion(prefLogoTexture,390,16,32,44));
        res.put("settings",new TextureRegion(settingsTexture,0,0,1280,1201));
        res.put("vibrateTrue",new TextureRegion(prefLogoTexture,0,0,78,78));
        res.put("vibrateFalse",new TextureRegion(prefLogoTexture,156,0,78,78));
        res.put("soundTrue",new TextureRegion(prefLogoTexture,78,0,78,78));
        res.put("soundFalse",new TextureRegion(prefLogoTexture,234,0,78,78));
        res.put("checkMark",new TextureRegion(prefLogoTexture,329,22,45,34));
        res.put("background",new TextureRegion(bookshelfTexture,0,80,1280,1200));
        res.put("startMenu",new TextureRegion(startTexture,0,80,1280,1200));
        res.put("skins",new TextureRegion(skinsTexture,0,0,1280,1280));
        res.put("top",new TextureRegion(bookshelfTexture,0,0,1280,80));

        return res;
    }


    public static void changeLanguage(boolean ru){
        TextureService.ru=ru;
        Preferences preferences=Gdx.app.getPreferences("main");
        preferences.putBoolean("ru",ru);
        Skin s=SkinService.getCurrentSkin();
        pauseMenuTexture=new Texture(Gdx.files.internal(s.getPauseFile(ru)));
        settingsTexture=new Texture(Gdx.files.internal(s.getSettingsMenuFile(ru)));
        startTexture=new Texture(Gdx.files.internal(s.getStartFile(ru)));
        skinsTexture=new Texture(Gdx.files.internal(s.getSkinFile(ru)));
        textureRegionMap=fillTextureRegionMap();
    }

    private static Array<TextureRegion> booksTextureRegionsInit(Texture booksTexture, int w,int h,int count){
        Array<TextureRegion> textures = new Array<>();
        for (int i=0;i<count;i++){
            textures.add(new TextureRegion(booksTexture,35*i,0,w,h));
        }
        return textures;
    }

    public static TextureRegion getBookTextureById(int id){
        id=id%count;
        return booksTextureRegions.get(id);
    }


    public static TextureRegion getPauseTexture(){
        return textureRegionMap.get("pause");
    }

    public static TextureRegion getPauseMenuTexture(){
        return textureRegionMap.get("pauseMenu");
    }

    public static TextureRegion getSettingsTexture(){
        return textureRegionMap.get("settings");
    }

    public static TextureRegion getVibrateTexture(boolean vibrate){
        if (vibrate)
            return textureRegionMap.get("vibrateTrue");
        else
            return textureRegionMap.get("vibrateFalse");
    }

    public static TextureRegion getSoundTexture(boolean vibrate){
        if (vibrate)
            return textureRegionMap.get("soundTrue");
        else
            return textureRegionMap.get("soundFalse");
    }

    public static TextureRegion getBackgroundTexture(){
        return textureRegionMap.get("background");
    }

    public static TextureRegion getTopTexture(){
        return textureRegionMap.get("top");
    }

    public static TextureRegion getSkinsTexture(){
        return textureRegionMap.get("skins");
    }

    public static TextureRegion getCheckMarkTexture(){
        return textureRegionMap.get("checkMark");
    }

    public static TextureRegion getStartMenuTexture() {
        return textureRegionMap.get("startMenu");
    }

    public static void dispose() {
        if (booksTexture!=null)
        bookshelfTexture.dispose();
        if (prefLogoTexture!=null)
        prefLogoTexture.dispose();
        if (pauseMenuTexture!=null)
        pauseMenuTexture.dispose();
        if (settingsTexture!=null)
        settingsTexture.dispose();
        if (booksTexture!=null)
        booksTexture.dispose();
        if (startTexture!=null)
            startTexture.dispose();
        if (skinsTexture!=null)
            skinsTexture.dispose();
    }


}
