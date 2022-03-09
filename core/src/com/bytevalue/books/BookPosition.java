package com.bytevalue.books;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BookPosition extends Rectangle {


    BookPosition(int x,int y,int w,int h){
        set(x,y,w,h);
    }

    public Vector2 getStartPosition(){
        return new Vector2(getX(),getY());
    }

}
