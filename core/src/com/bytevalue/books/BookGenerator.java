package com.bytevalue.books;

import com.badlogic.gdx.utils.Array;
import java.util.HashMap;
import java.util.Random;

public class BookGenerator {

    private int shiftId=0;
    private final int bookshelfCount=6;
    private final int numberOfSkins=19;
    private final int shelfCapacity=12;

    public Array<Array<Integer>> generateNew(int numberOfBookshelf){
        HashMap<Integer,Integer> n =new HashMap<>();
        for (int i=0;i<numberOfBookshelf;i++)n.put(i,0);
        return generate(n,numberOfBookshelf);
    }

    public Array<Array<Integer>> generate(HashMap<Integer,Integer> existingBooks,int numberOfBookshelf){
        HashMap<Integer,Integer> creation=new HashMap<>();
        int numberOfBooks =Math.min(numberOfBookshelf*shelfCapacity,getNumberOfBooks(existingBooks));

        Array<Integer> booksAbleToCreate=setShiftAndRevert(existingBooks);

        int minNumberOfBooks = getMinBooks(booksAbleToCreate);

        if (minNumberOfBooks == booksAbleToCreate.get(0)) {
            creation.put(0,minNumberOfBooks);
            numberOfBooks-=minNumberOfBooks;
            for (int i=1;i<booksAbleToCreate.size;i++){
                if(booksAbleToCreate.get(i)!=0) {
                    int number = Math.min(numberOfBooks,Math.min(new Random()
                                    .nextInt(booksAbleToCreate.get(i))
                                    + numberOfBooks / (bookshelfCount - 1)
                            , booksAbleToCreate.get(i)));
                    numberOfBooks -= number;
                    creation.put(i, number);
                }
            }
        }else {
            int minBooksId=booksAbleToCreate.indexOf(minNumberOfBooks,true);
            creation.put(minBooksId,minNumberOfBooks);
            numberOfBooks-=minNumberOfBooks;
            for (int i=0;i<booksAbleToCreate.size;i++){
                if(i!=minBooksId) {
                    if(booksAbleToCreate.get(i)!=0) {
                        int number = Math.min(numberOfBooks,Math.min(new Random()
                                        .nextInt(booksAbleToCreate.get(i))
                                        + numberOfBooks / (bookshelfCount - 1)
                                , booksAbleToCreate.get(i)));
                        numberOfBooks -= number;
                        creation.put(i, number);
                    }
                }
            }
        }
        return getBookshelves(creation,numberOfBookshelf);

    }

    private int getNumberOfBooks(HashMap<Integer,Integer> existingBooks){
        int numberOfBooks=0;
        for (Integer i:existingBooks.values()){
            numberOfBooks+=i;
        }
        return 49+new Random().nextInt(11)-numberOfBooks;
    }

    public Array<Array<Integer>> getBookshelves(HashMap<Integer,Integer> input,int number){
        Array<Integer> bank = new Array<>();
        for (int i:input.keySet()){
            for (int j=0;j<input.get(i);j++){
                bank.add(i+shiftId);
            }
        }
        Array<Array<Integer>> result;
        do {
            result = new Array<>();
            for (int i=0;i<number;i++)result.add(new Array<Integer>());
            bank.shuffle();
            for (int i:bank){
                int random = new Random().nextInt(number);
                if(result.get(random).size!=shelfCapacity){
                    result.get(random).add(i);
                }else {
                    int o=0;
                    while (o<result.size&&result.get(o).size==shelfCapacity)o++;
                    if(o<result.size)result.get(o).add(i);
                    else break;
                }
            }
        }while (containsFull(result));
        result.shuffle();
        return result;
    }

    private boolean containsFull(Array<Array<Integer>> res){
        for (Array<Integer> r:res){
            if (r.size!=shelfCapacity)continue;
            boolean hasAnother=false;
            for (int i=1;i<shelfCapacity;i++){
                if(!r.get(i).equals(r.get(0))){
                    hasAnother=true;
                    break;
                }
            }
            if (!hasAnother)return true;
        }
        return false;
    }


    private int getMinBooks(Array<Integer> books){
        int min=100000;
        for (int i:books){
            if(i!=0&&i<min)min=i;
        }
        return min;
    }

    public Array<Integer> setShiftAndRevert(HashMap<Integer,Integer> existingBooks){
        Array<Integer> result= new Array<>();
        for (int i=0;i<6;i++)result.add(0);
        int min=1000000000;//TODO
        for(Integer integer :existingBooks.keySet()){
            if(integer<min)min=integer;
        }
        if (min==1000000000) {
            min=0;
            shiftId+=2;

        }else {
            if(min>=shiftId)shiftId += Math.min(2, min - shiftId);
            else if (min==0)shiftId=0;
        }
        shiftId = shiftId % numberOfSkins;
        for(Integer integer :existingBooks.keySet()){
            result.set(integer-min,existingBooks.get(integer));
        }

        for (int i=0;i<result.size;i++){
            result.set(i,shelfCapacity-result.get(i));
        }
        return result;
    }

}
