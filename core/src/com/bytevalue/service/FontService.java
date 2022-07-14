package com.bytevalue.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontService {

    private static BitmapFont scoreFont;

    public static void init(String color){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 35;
        Color c = Color.valueOf(color);
        parameter.color= c;
        parameter.shadowColor=c;
        parameter.borderColor=c;

        scoreFont= generator.generateFont(parameter);
        generator.dispose();
    }

    public static BitmapFont getScoreFont() {
        return scoreFont;
    }
}
