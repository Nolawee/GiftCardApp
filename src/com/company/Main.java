package com.company;

import java.io.*;
import java.util.*;


public class Main {
    public static void main(String[] args) throws IOException{

        // Check amount of arguments are correct
        if (args.length != 2) {
            System.out.println("Incorrect arguments");
            return;
        }

         var fileName = args[0];

        if(isInteger(args[1]) == false){
            System.out.println("Second argument is not an integer");
            return;
        }

        var giftCardBalance = Integer.parseInt(args[1]);


        var itemHashStore = MakeStore(fileName);

        var itemsCollection = itemHashStore.keySet();
        var pricesCollection = itemHashStore.values();

        // add the two smallest values and if thats greater than the giftcard ballance then exit out of the progarm
        if (!AbleToBuyTwoItems(giftCardBalance, pricesCollection)) {
            System.out.println("Not Possible");
            return;
        }



        ArrayList<String> items = new ArrayList(itemsCollection);
        ArrayList<Integer> prices = new ArrayList(pricesCollection);

        String result = findPair(giftCardBalance, items, prices);

        System.out.println(result);
    }

    private static HashMap sortByValues(HashMap map) {
        List list = new LinkedList(map.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, (Comparator) (o1, o2) -> ((Comparable) ((Map.Entry) (o1)).getValue())
                .compareTo(((Map.Entry) (o2)).getValue()));

        // Here I am copying the sorted list in HashMap
        // using LinkedHashMap to preserve the insertion order
        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
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

            HashMap<String, Integer> storeMapUnsorted = new HashMap<String, Integer>();

            while(currentLine != null) {
                var something = currentLine.split(",");
                storeMapUnsorted.put(something[0], Integer.parseInt(something[1].trim()));
                currentLine = reader.readLine();
            }

            var storeMap = sortByValues(storeMapUnsorted);

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

    public static String findPair(int giftCardBalance, ArrayList<String> items, ArrayList<Integer> prices) {
        int min = 0;
        int max = prices.size() - 1;
        boolean found = false;
        int pricesMin = 0;
        int pricesMax = 0;

        int itemMinPrice = 0;
        int itemMaxPrice = 0;
        String itemNameMin = "";
        String itemNameMax = "";

        while(min < max) {
            pricesMin = prices.get(min);
            pricesMax = prices.get(max);
            int currentPrice = pricesMax + pricesMin;
            if(currentPrice == giftCardBalance) {
                found = true;
                itemNameMin = items.get(min);
                itemNameMax = items.get(max);
                itemMinPrice = pricesMin;
                itemMaxPrice = pricesMax;

            }
            else if (currentPrice > giftCardBalance) {
                max--;
            }
            else if (currentPrice < giftCardBalance) {
                if(giftCardBalance - itemMinPrice - itemMaxPrice >= giftCardBalance - pricesMin - pricesMax) {
                    itemNameMin = items.get(min);
                    itemNameMax = items.get(max);
                    itemMinPrice = pricesMin;
                    itemMaxPrice = pricesMax;
                }
                min++;
            }
            if(found)
                break;
        }
        String output = String.format("%s %d, %s %d",
                itemNameMin, itemMinPrice,
                itemNameMax, itemMaxPrice
        );
        return output;
    }


}
