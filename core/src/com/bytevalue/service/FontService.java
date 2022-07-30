package com.bytevalue.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontService {

    private static BitmapFont font;
    private static BitmapFont smallFont;
    private static BitmapFont middleFont;
    public static final String FONT_CHARACTERS = "№йцукенгшщзхъфывапролджэячсмитьбюЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";
    private static Color scoreColor;
    private static Color moneyColor;
    private static Color notesColor;

    public static void init(String scoreColor,String notesColor){
        moneyColor = Color.valueOf("#dfba67");
        FontService.scoreColor = Color.valueOf(scoreColor);
        FontService.notesColor = Color.valueOf(notesColor);


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 35;
        parameter.characters=FONT_CHARACTERS;
        font= generator.generateFont(parameter);

        parameter.size = 20;
        smallFont= generator.generateFont(parameter);

        parameter.size = 24;
        middleFont= generator.generateFont(parameter);
        generator.dispose();

    }

    public static BitmapFont getFont() {
        return font;
    }

    public static BitmapFont getSmallFont() {
        return smallFont;
    }

    public static BitmapFont getMiddleFont() {
        return middleFont;
    }

    public static Color getScoreColor() {
        return scoreColor;
    }

    public static Color getMoneyColor() {
        return moneyColor;
    }

    public static Color getNotesColor() {
        return notesColor;
    }
}
