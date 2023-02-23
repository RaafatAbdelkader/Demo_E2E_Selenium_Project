package test;

import testBase.TestBase;

import java.util.*;

public class Test extends TestBase {

//    public static List<Integer> getSortedList( List<Integer> list){
//        List<Integer> sortedlist=list;
//        Collections.sort(list,Collections.reverseOrder());
//        return list;
//    }


    public static void main(String[] args) {

        List<Integer>numbers= new ArrayList<>();

        Random random= new Random();
        for (int i = 0; i < 100; i++) {
            numbers.add(random.nextInt(100));
        }

        System.out.println(numbers);

        Collections.sort(numbers);

        System.out.println(numbers);

    }



}
