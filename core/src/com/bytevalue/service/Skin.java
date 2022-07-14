package com.bytevalue.service;

public enum Skin {

    USUAL(18,"usual","1","#c0a588",0),

    HALLOWEEN(6,"halloween","1","#c0a588",1),

    NEON(6,"neon","1","#c0a588",2);

    private final int bookCount,number;
    private final String  folder,soundIndex, fontColor;

    Skin(int bookCount,String folder, String soundIndex,String fontColor,int number){
        this.number=number;
        this.folder=folder;
        this.bookCount = bookCount;
        this.soundIndex=soundIndex;
        this.fontColor=fontColor;
    }

    public int getBookCount(){
        return bookCount;
    }

    public String getBookFile() {
        return "themes/"+folder+"/books.png";
    }

    public String getPauseFile(boolean ru) {
        if (ru)
            return "themes/"+folder+"/pauseRU.png";
        else
            return "themes/"+folder+"/pauseEN.png";
    }

    public String getIconsFile() {
        return "themes/"+folder+"/icons.png";
    }

    public String getSettingsMenuFile(boolean ru) {
        if (ru)
            return "themes/"+folder+"/settingsRU.png";
        else
            return "themes/"+folder+"/settingsEN.png";

    }

    public String getShelfBackgroundFile() {
        return "themes/"+folder+"/shelf.png";
    }

    public String getSoundIndex() {
        return soundIndex;
    }

    public String getFontColor() {
        return fontColor;
    }

    public int getNumber(){
        return number;
    }

    public static Skin getSkinById(int num){
        switch (num){
            case 1:return HALLOWEEN;
            case 2:return NEON;
            default:return USUAL;
        }
    }

    public String getStartFile(boolean ru) {
        if (ru)
            return "themes/"+folder+"/startRU.png";
        else
            return "themes/"+folder+"/startEN.png";
    }

    public String getSkinFile(boolean ru) {
        if (ru)
            return "themes/"+folder+"/skinsRU.png";
        else
            return "themes/"+folder+"/skinsEN.png";
    }
}
