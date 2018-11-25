package com.company;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.server.ExportException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException{
        // Check amount of arguments are correct
        if (args.length != 2) {
            return;
        }

        var fileName = args[0];

        if(isInteger(args[0]) == false){
            return;
        }

        var giftCardBalance = Integer.parseInt(args[1]);

        var store = MakeStore(fileName);

        var prices = store.values();

        // add the two smallest values and if thats greater than the giftcard ballance then exit out of the progarm
        if (AbleToBuyTwoItems(giftCardBalance, prices)) {
            System.out.println("Need a bigger gift card balance");
            return;
        }





    }

    public static boolean isInteger(String s) throws NullPointerException {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }

        return true;
    }

    public static HashMap<String, Integer> MakeStore(String fileName) throws IOException {
        try {

            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String currentLine = reader.readLine();

            HashMap<String, Integer> storeMap = new HashMap<String, Integer>();

            while(currentLine != null) {
                var something = currentLine.split(",");
                storeMap.put(something[0], Integer.parseInt(something[1].trim()));
                currentLine = reader.readLine();
            }

            return storeMap;

        } catch (IOException err) {
            throw err;
        }
    }

    public static boolean AbleToBuyTwoItems (int giftCardBalance, Collection storeValues) {

        Object[] array = storeValues.toArray();

        int[] ints = Arrays.stream(array).mapToInt(o -> (int)o).toArray();

        Arrays.sort(ints);

        var minimumBalanceToBuyTwoItems = ints[0] + ints[1];

        if(giftCardBalance < minimumBalanceToBuyTwoItems) {
            return false;
        }

        return true;
    }

}
