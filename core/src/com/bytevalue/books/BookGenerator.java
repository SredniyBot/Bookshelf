package com.bytevalue.books;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class BookGenerator {

    private int shiftId=0;
    private final int shelfCapacity=12;

    public Array<Array<Integer>> generateNew(int numberOfBookshelf){
        HashMap<Integer,Integer> n =new HashMap<>();
        for (int i=0;i<numberOfBookshelf;i++)n.put(i,0);
        return generate(n,numberOfBookshelf);
    }

    public Array<Array<Integer>> generate(HashMap<Integer,Integer> existingBooks, int numberOfBookshelf){
        Array<Integer> canBeCreated = new Array<>();
        Array<Integer> willBeCreated = new Array<>();
        int numberOfBooksToCreate = Math.min(numberOfBookshelf*shelfCapacity, getNumberOfBooksToCreate(existingBooks));
        int numberOfVoidToCreate = numberOfBookshelf*shelfCapacity-numberOfBooksToCreate;

        if(isNotEmpty(existingBooks)) {                 // fill books that can be created by id

            shiftId = Collections.min(existingBooks.keySet());
            while (existingBooks.size()<6){
                int additionalId=1;
                while (existingBooks.containsKey(additionalId+shiftId))additionalId++;
                existingBooks.put(additionalId+shiftId,0);
            }
            for (Integer index : existingBooks.keySet())
                for (int i=0;i<shelfCapacity-existingBooks.get(index);i++)
                    canBeCreated.add(index - shiftId);
        }else {
            for (int i=0;i<6;i++)
                for (int ii=0;ii<shelfCapacity;ii++)
                    canBeCreated.add(i);
                shiftId+=2;
        }


        if(!(countMeaning(canBeCreated,0)==0            // field have no ability to continue game
                ||countMeaning(canBeCreated,1)==0
                ||countMeaning(canBeCreated,2)==0
                ||countMeaning(canBeCreated,3)==0
                ||countMeaning(canBeCreated,4)==0
                ||countMeaning(canBeCreated,5)==0)){
            int min = min(canBeCreated);
            while (canBeCreated.contains(min,true)){
                canBeCreated.removeValue(min,true);
                willBeCreated.add(min);
                numberOfBooksToCreate--;
            }
        }
        canBeCreated.shuffle();
        for (int i=0;i<numberOfBooksToCreate;i++){                      // fill with other random books
            willBeCreated.add(canBeCreated.pop());
        }

        for (int i = 0;i<numberOfVoidToCreate;i++)willBeCreated.add(-1);

        willBeCreated.shuffle();
        int wrong=getWrongShelf(willBeCreated,numberOfBookshelf);
        while (wrong!=-1){
            if(numberOfBookshelf==1){
                willBeCreated.set(new Random().nextInt(shelfCapacity),-1);
            }else {
                if(wrong>0){
                    willBeCreated.swap(new Random().nextInt(shelfCapacity)+wrong*shelfCapacity,new Random().nextInt(shelfCapacity));
                }else {
                    willBeCreated.swap(new Random().nextInt(shelfCapacity),new Random().nextInt(shelfCapacity)+shelfCapacity);
                }
            }
            wrong=getWrongShelf(willBeCreated,numberOfBookshelf);
        }
        return transformToResult(willBeCreated,numberOfBookshelf);
    }

    private int min(Array<Integer> array){
        ArrayList<Integer> res = new ArrayList<>();
        for (int i=0;i<6;i++)res.add(0);
        for (Integer i :array){
            res.set(i,res.get(i)+1);
        }
        return res.indexOf(Collections.min(res));
    }

    private int getWrongShelf(Array<Integer> array,int bookshelfCount){
        for (int i=0;i<bookshelfCount;i++){
            boolean prev=true;
            for (int ii=1;ii<shelfCapacity;ii++){
                if((!array.get(i * shelfCapacity + ii).equals(array.get(i * shelfCapacity)))||array.get(i * shelfCapacity + ii)<0){
                    prev=false;
                    break;
                }
            }
            if (prev)
            return i;
        }
        return -1;
    }

    private Array<Array<Integer>> transformToResult(Array<Integer> array,int bookshelfCount){
        Array<Array<Integer>> resultArray = new Array<>();
        for (int i=0;i<bookshelfCount;i++){
            Array<Integer> shelf = new Array<>();
            for(int ii=0;ii<shelfCapacity;ii++)if(array.get(i*shelfCapacity+ii)>=0)shelf.add(array.get(i*shelfCapacity+ii)+shiftId);
            resultArray.add(shelf);
        }
        return resultArray;
    }

    private boolean isNotEmpty(HashMap<Integer,Integer> existingBooks){
        if(existingBooks.isEmpty())return false;
        for (Integer i:existingBooks.keySet()){
            if (existingBooks.get(i)!=0)return true;
        }
        return false;
    }

    private int countMeaning(Array<Integer> array,int meaning){
        int count=0;
        for(Integer i:array)if(i==meaning)count++;
        return count;
    }


    private int getNumberOfBooksToCreate(HashMap<Integer,Integer> existingBooks){
        int numberOfBooks=0;
        for (Integer i:existingBooks.values()){
            numberOfBooks+=i;
        }
        return getNumberOfBooksToCreateFromZero()-numberOfBooks;
    }

    private int getNumberOfBooksToCreateFromZero(){
        return 49+new Random().nextInt(11);
    }


}
