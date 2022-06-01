package com.bytevalue.books;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Random;

public class BookGenerator {

    int shiftId=0;
    int bookshelfCount=6;
    private Array<Integer> existingBooks;        //ID  -  NUMBER


    BookGenerator(){

    }



    public void generate(int numberOfBookshelf){
        int numberOfBooks=0;
        for (int i=0;i<existingBooks.size;i++){
            numberOfBooks+=existingBooks.get(i);
        }
        Array<Integer> bookToCreate=setShiftAndRevert(existingBooks);
        HashMap<Integer,Integer> creation=new HashMap<>();

        int books=49+new Random().nextInt(11)-numberOfBooks;
        int min =getMin(bookToCreate);
        if (min == bookToCreate.get(0)) {
            creation.put(0,min);
            books-=min;
            for (int i=1;i<bookToCreate.size;i++){
                int def=Math.min(new Random()
                        .nextInt(bookToCreate.get(i))+books/(bookshelfCount-1)
                        ,bookToCreate.get(i));
                books-=def;
                creation.put(i,def);
            }
        }else {
            int j=bookToCreate.indexOf(min,true);
            creation.put(j,min);
            for (int i=0;i<bookToCreate.size;i++){
                if(i!=j) {
                    int def = Math.min(new Random()
                                    .nextInt(bookToCreate.get(i)) + books / (bookshelfCount - 1)
                            , bookToCreate.get(i));
                    books -= def;
                    creation.put(i, def);
                }
            }
        }

    }


    public Array<Array<Integer>> getBookshelves(HashMap<Integer,Integer> input,int number){
        Array<Integer> bank = new Array<>();
        Array<Array<Integer>> result = new Array<>();
        for (int i=0;i<number;i++)result.add(new Array<Integer>());
        for (int i:input.keySet()){
            for (int j=0;j<input.get(i);j++){
                bank.add(i+shiftId);
            }
        }
        bank.shuffle();
        for (int i:bank){
            int random = new Random().nextInt(number);
            if(result.get(random).size!=12){
                result.get(random).add(i);
            }else {
                int o=0;
                while (result.get(o).size==12&&o<result.size)o++;
                if(o<result.size)result.get(o).add(i);
                else break;
            }
        }
        result.shuffle();
        return result;
    }



    private int getMin(Array<Integer> books){
        int min=100000;
        for (int i:books){
            if(i!=0&&i<min)min=i;
        }
        return min;
    }

    public Array<Integer> setShiftAndRevert(Array<Integer> existingBooks){
        Iterator<Integer> iterator=existingBooks.iterator();
        int bookshelves=0;
        while (existingBooks.get(0)==0&&bookshelves<6){
            existingBooks.removeIndex(0);
            existingBooks.add(0);
            shiftId++;
            bookshelves++;
        }
        for (int i=0;i<existingBooks.size;i++){
            existingBooks.set(i,12-existingBooks.get(i));
        }
        return existingBooks;
    }
}
