package ru.bytevalue.service;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.*;

public class TextureService {

    public static Array<TextureRegion> booksTextureRegions;
    public static Array<TextureRegion> boomTextureRegions;
    public static Array<TextureRegion> timerTextureRegions;
    public static Map<String ,TextureRegion> textureRegionMap;

    public static Texture bookshelfTexture;
    public static Texture prefLogoTexture;
    public static Texture pauseMenuTexture;
    public static Texture settingsTexture;
    public static Texture booksTexture;
    public static Texture startTexture;
    public static Texture skinsTexture;
    public static Texture quitMenuTexture;
    public static Texture lossTexture;
    public static Texture utilitiesTexture;
    public static Texture notesMenuTexture;
    public static Texture timerTexture;

    public static int w=35,h=160,count;

    public static void init(Skin skin){
        dispose();
        count=skin.getBookCount();

        Preferences preferences=Gdx.app.getPreferences("main");
        boolean ru=preferences.getBoolean("ru",false);

        booksTexture =new Texture(Gdx.files.internal(skin.getBookFile()));
        prefLogoTexture = new Texture(Gdx.files.internal(skin.getIconsFile()));
        bookshelfTexture =new Texture(Gdx.files.internal(skin.getShelfBackgroundFile()));
        pauseMenuTexture=new Texture(Gdx.files.internal(skin.getPauseFile(ru)));
        settingsTexture=new Texture(Gdx.files.internal(skin.getSettingsMenuFile(ru)));
        startTexture=new Texture(Gdx.files.internal(skin.getStartFile(ru)));
        skinsTexture=new Texture(Gdx.files.internal(skin.getSkinFile(ru)));
        quitMenuTexture=new Texture(Gdx.files.internal(skin.getQuitMenuFile(ru)));
        lossTexture =new Texture(Gdx.files.internal(skin.getLossMenuFile(ru)));
        notesMenuTexture =new Texture(Gdx.files.internal(skin.getNotesMenuFile(ru)));
        utilitiesTexture =new Texture(Gdx.files.internal("utilities/utilities2.png"));
        timerTexture =new Texture(Gdx.files.internal("utilities/timer.png"));

        booksTextureRegions=textureRegionsInit(booksTexture,0,w,h,count);
        boomTextureRegions=textureRegionsInit(utilitiesTexture,1,w,h,5);
        timerTextureRegions=textureRegionsInit(timerTexture,6,w,h,160);
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
        res.put("quit",new TextureRegion(quitMenuTexture,0,0,1280,1280));
        res.put("loss",new TextureRegion(lossTexture,0,0,1280,1200));
        res.put("notesMenu",new TextureRegion(notesMenuTexture,0,0,1280,1280));
        res.put("top",new TextureRegion(bookshelfTexture,0,0,1280,80));
        res.put("bookmark",new TextureRegion(utilitiesTexture,0,164,32,42));
        res.put("stone",new TextureRegion(utilitiesTexture,0,0,w,h));
        res.put("note",new TextureRegion(utilitiesTexture,35,160,380,400));
        return res;
    }


    public static void changeLanguage(boolean ru){
        Skin s=SkinService.getCurrentSkin();
        pauseMenuTexture=new Texture(Gdx.files.internal(s.getPauseFile(ru)));
        settingsTexture=new Texture(Gdx.files.internal(s.getSettingsMenuFile(ru)));
        startTexture=new Texture(Gdx.files.internal(s.getStartFile(ru)));
        skinsTexture=new Texture(Gdx.files.internal(s.getSkinFile(ru)));
        quitMenuTexture=new Texture(Gdx.files.internal(s.getQuitMenuFile(ru)));
        lossTexture =new Texture(Gdx.files.internal(s.getLossMenuFile(ru)));
        notesMenuTexture =new Texture(Gdx.files.internal(s.getNotesMenuFile(ru)));
        textureRegionMap=fillTextureRegionMap();
    }

    private static Array<TextureRegion> textureRegionsInit(Texture booksTexture, int start,int w,int h,int count){
        Array<TextureRegion> textures = new Array<>();
        for (int i=start;i<count+start;i++){
            textures.add(new TextureRegion(booksTexture,35*i,0,w,h));
        }
        return textures;
    }

    public static TextureRegion getBookTextureById(int id){
        id=id%count;
        return booksTextureRegions.get(id);
    }

    public static TextureRegion getBoomTextureById(int id){
        return boomTextureRegions.get(id);
    }

    public static TextureRegion getTimerTextureById(int id){
        return timerTextureRegions.get(id);
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

    public static TextureRegion getQuitMenuTexture() {
        return textureRegionMap.get("quit");
    }

    public static TextureRegion getLossTexture() {
        return textureRegionMap.get("loss");
    }

    public static TextureRegion getBookmarkTexture() {
        return textureRegionMap.get("bookmark");
    }

    public static TextureRegion getStoneTexture() {
        return textureRegionMap.get("stone");
    }

    public static TextureRegion getNotesMenuTexture() {
        return textureRegionMap.get("notesMenu");
    }
    public static TextureRegion getNoteTexture() {
        return textureRegionMap.get("note");
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
        if (quitMenuTexture!=null)
            quitMenuTexture.dispose();
        if (lossTexture !=null)
            lossTexture.dispose();
        if (utilitiesTexture!=null)
            utilitiesTexture.dispose();
        if(notesMenuTexture!=null)
            notesMenuTexture.dispose();
        if (timerTexture!=null)
            timerTexture.dispose();
    }
}
