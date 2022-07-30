package com.bytevalue.service;

public enum Skin {

    USUAL(18,"usual","#c0a588","#453125",0),

    HALLOWEEN(6,"halloween","#a89dcf","#383445",1),

    NEON(6,"neon","#dab7c8","#222323",2);

    private final int bookCount,number;
    private final String  folder, scoreColor,notesColor;

    Skin(int bookCount,String folder,String scoreColor,String notesColor,int number){
        this.number=number;
        this.folder=folder;
        this.bookCount = bookCount;

        this.scoreColor=scoreColor;
        this.notesColor=notesColor;
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

    public String getScoreColor() {
        return scoreColor;
    }

    public String getNotesColor() {
        return notesColor;
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

    public String getQuitMenuFile(boolean ru) {
        if (ru)
            return "themes/"+folder+"/quitRU.png";
        else
            return "themes/"+folder+"/quitEN.png";
    }

    public String getLossMenuFile(boolean ru) {
        if (ru)
            return "themes/"+folder+"/lossRU.png";
        else
            return "themes/"+folder+"/lossEN.png";
    }

    public String getNotesMenuFile(boolean ru) {
        if (ru)
            return "themes/"+folder+"/notesRU.png";
        else
            return "themes/"+folder+"/notesEN.png";
    }


    public String getStand1Sound(){
        return "sounds/"+folder+"/stand1.wav";
    }
    public String getStand2Sound(){
        return "sounds/"+folder+"/stand2.wav";
    }
    public String getStand3Sound(){
        return "sounds/"+folder+"/stand3.wav";
    }
    public String getMoveSound(){
        return "sounds/"+folder+"/move.wav";
    }
    public String getShelfSound(){
        return "sounds/"+folder+"/shelf.wav";
    }
    public String getMenuSound(){
        return "sounds/"+folder+"/menu.wav";
    }


}
