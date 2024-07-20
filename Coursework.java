package org.sample;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

public class Coursework {

    // fileName is final so cannot be changed during the program
    public final String fileName;

    public Coursework(String filename) {
        System.out.println(filename);
        fileName = filename;
    }

    //A main method that will take command line arguments to call the given methods.
    //args [0] = data file filename
    //args [1] = property ID
    //args [2] = city c -- as a String e.g. "Bristol"
    //args [3] = n for the most recently-sold properties
    public static void main(String[] args) {

        // create instance of coursework with specific csv file
        Coursework cw = new Coursework(args[0]);

        // most efficient methods for get property by id
        System.out.println(cw.getPropertyFindFirst(Integer.parseInt(args[1])));
        System.out.println();

        // most efficient method for get the total sales of a city
        System.out.println("Total sales for city "+args[2]+" is: £"+ cw.getTotalSalesForCityLinear(args[2]));
        System.out.println();

        // most efficient method for getting most recent properties sold in order
        String[] properties = cw.getMostRecentPropertiesSoldLineReader(Integer.parseInt(args[3]));
        for (int i = 0; i < Integer.parseInt(args[3]); i++) {
            System.out.println(properties[i]+", which is row: "+cw.getPropertyFindFirst(Integer.parseInt(properties[i])));
        }
        System.out.println();
    }

//    Your application will provide a method to return details of a property chosen via the ID
    public String getPropertyLinearSearch(int id){
        String property = ""+id;

        // using linear search
        // iterate through each row of the file until correct id number is reached
        String line = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            br.readLine(); // this will read the first line
            // in linear order loop through each line until the value of id is reached
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                // set value outside of if statement
                int idVal = Integer.parseInt(values[0]);
                if (idVal == id) {
                    // id is only found once so using string builder .append will not be as effective
                    property = "Details for ID = " + values[0] + " is: City = " + values[1] + ", Owner name = " + values[2] +
                            ", Sale price = £" + values[3] + ", Sale date = " + values[4];
                    // return property when found and also exit method
                    // because once an id is found, there should not be a duplicate
                    // so don't need to continue searching for id
                    br.close();
                    return property;
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
        return property;
    }

    public String getPropertyBinarySearch (int id) {
        String property = ""+id;
        // variables for binary search
        String line = "";
        Path path = Paths.get(fileName);

        try {
            List<String> lines = Files.readAllLines(path);

            // Code to perform a binary search
            // Taken from GeeksForGeeks, post updated 25-02-2023
            // Accessed 17-03-2023
            // https://www.geeksforgeeks.org/binary-search/
            // -
            // lowest value is 1 because first row is not table values
            int l = 1;
            int r = lines.size() - 1;
//            int m = l + (r - l) / 2;
            while (l <= r) {
                int m = l + (r - l) / 2;
                // end of referenced code

                // Check if x is present at mid
                String[] midRow = lines.get(m).split(",");
                int midValue = Integer.parseInt(midRow[0]);
                System.out.println(midValue);

                if (midValue == id) {
                    property = "Details for ID = " + midRow[0] + " is: City = " + midRow[1] + ", Owner name = " + midRow[2] +
                            ", Sale price = £" + midRow[3] + ", Sale date = " + midRow[4];
                    // return property when found and also exit method
                    // because once an id is found, there should not be a duplicate
                    // so don't need to continue searching for id
                    return property;
                }

                // Code to perform a binary search
                // Taken from GeeksForGeeks, post updated 25-02-2023
                // Accessed 17-03-2023
                // https://www.geeksforgeeks.org/binary-search/
                // -
                // If x greater, ignore left half
                if (midValue < id) {
                    l = m + 1;
                }

                // If x is smaller, ignore right half
                else {
                    r = m - 1;
                }
                // end of referenced code
            }
        } catch (NumberFormatException e) {
            System.out.println("Number format exception");
        } catch (IOException e) {
            System.out.println("IO exception");
        }

        return property;
    }

    // redo because Stream could be not allowed as extra library/api?
    public String getPropertyFindFirst(int id) {
        String property = ""+id;

        // Code to read the nth line of a file
        // Taken from Educative [No Date Given]
        // Date accessed 17-03-2023
        // https://www.educative.io/answers/reading-the-nth-line-from-a-file-in-java
        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            String row = lines.skip(id).findFirst().get();
            // end of referenced code
            String[] values = row.split(",");
            property = "Details for ID = " + values[0] + " is: City = " + values[1] + ", Owner name = " + values[2] +
                    ", Sale price = £" + values[3] + ", Sale date = " + values[4];
        }
        catch(IOException e){
            System.out.println("Io exception");
        }
        return property;
    }

//    Your application will provide a method to return the total sales for a city c
    public int getTotalSalesForCityLinear(String c){
        int total1 = 0;
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[1].contains(c)) {
                    total1 += Integer.parseInt(values[3]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Io exception");
        }
        return total1;
    }

//    public int getTotalSalesForCityLineNumReader(String c) {
//
//        // Code to use LineNumberReader
//        // Taken from Code Review Stack Exchange post by Tunaki, 08-03-2016
//        // Date accessed 17-03-2023
//        // https://codereview.stackexchange.com/questions/122263/counting-occurrences-of-a-word-in-a-file
//        int total2 = 0;
//        String line1;
//        try (LineNumberReader r = new LineNumberReader(new FileReader(fileName))){
//            while ((line1 = r.readLine()) != null) {
//                for (String element : line1.split(",")) {
//                    // end of referenced code
//                    if (element.contains(c)) {
//                        String[] values = line1.split(",");
//                        total2 = total2 + Integer.parseInt(values[3]);
//                    }
//                }
//            }
//            r.close();
//            return total2;
//        } catch (FileNotFoundException e) {
//            System.out.println("File not found");
//        } catch (IOException e) {
//            System.out.println("IO exception");
//        }
//        return total2;
//    }

    public int getTotalSalesForCityLineNumReaderSimpl (String c) {
        int total2 = 0;

        // Code to use LineNumberReader
        // Taken from Code Review Stack Exchange post by Tunaki, 08-03-2016
        // Date accessed 17-03-2023
        // https://codereview.stackexchange.com/questions/122263/counting-occurrences-of-a-word-in-a-file
        String line1;
        try (LineNumberReader r = new LineNumberReader(new FileReader(fileName))){
            while ((line1 = r.readLine()) != null) {
                // end of referenced code
                String[] values = line1.split(",");
                if (values[1].contains(c)) {
                    total2 += Integer.parseInt(values[3]);
                }
            }
            r.close();
            return total2;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
        return total2;
    }

    public int getTotalSalesForCitySortCity (String c) {
        int total = 0;
        ArrayList<String> cityList = new ArrayList<>();
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            br.readLine(); // this will read the first line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                // adds all cities into array list
                cityList.add(values[1] + "," + values[3]);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // sort cities
        Collections.sort(cityList);

        // linear search to add up values
        for (int i=0; i<cityList.size(); i++) {
            String valueAtIndex = cityList.get(i);
            String[] values = valueAtIndex.split(",");
            if (values[0].contains(c)) {
                total += Integer.parseInt(values[1]);
            }

        }

        return total;
    }

//    Your application will provide a method to return the n properties that were most recently sold
    public String[] getMostRecentPropertiesSoldLinear(int n){
        String[] properties = new String[n];

        // variables
        String line = "";
        ArrayList<String> dateList = new ArrayList<>();

        // add all date items to large array list
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            br.readLine(); // this will read the first line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                // add id value with each date to make it easy to print most recent properties sold

                // Code to change date format to YYYYMMDD so it can be sorted correctly
                // Taken from Stack Overflow post by Thomas 26-04-2014
                // Date accessed 17-03-2023
                // https://stackoverflow.com/questions/25502308/sort-objects-in-list-by-string-dd-mm-yyyy
                dateList.add(values[4].replaceAll("(\\d+)/(\\d+)/(\\d+)","$3$2$1") + "," + values[0]);
                // end of referenced code
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }

        // sort array list by date
        Collections.sort(dateList);

        // add n most recent values into string array properties
        for (int i = 0; i < n; i++) {
            // get id from most recent sold
            String[] whichId = dateList.get(dateList.size() - (i+1)).split(",");
            // add id to array
            properties[i] = whichId[1];
        }

        return properties;
    }

    public String[] getMostRecentPropertiesSoldLinearReversed(int n){
        String[] properties2 = new String[n];

        // variables
//        String path = "data.csv";
        String line = "";
        ArrayList<String> dateList = new ArrayList<>();

        // add all date items to large array list
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            br.readLine(); // this will read the first line
//            String line1=null;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                // add id value with each date to make it easy to print most recent properties sold

                // Code to change date format to YYYYMMDD so it can be sorted correctly
                // Taken from Stack Overflow post by Thomas 26-04-2014
                // Date accessed 17-03-2023
                // https://stackoverflow.com/questions/25502308/sort-objects-in-list-by-string-dd-mm-yyyy
                dateList.add(values[4].replaceAll("(\\d+)/(\\d+)/(\\d+)","$3$2$1") + "," + values[0]);
                // end of referenced code
            }

            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }

        // sort array list by date in reverse order
        dateList.sort(Collections.reverseOrder());

        // add n most recent values into string array properties
        for (int i = 0; i < n; i++) {
            // get id from most recent sold
            String[] whichId = dateList.get(i).split(",");
            // add id to array
            properties2[i] = whichId[1];
        }

        return properties2;
    }

    public String[] getMostRecentPropertiesSoldLineReader (int n) {
        String[] properties3 = new String[n];
        Path path = Paths.get(fileName);
        List<String> columns;
        ArrayList<String> dateList1 = new ArrayList<>();


        try {
            List<String> lines = Files.readAllLines(path);
            // starting from line 1 instead of 0 because first line is header of columns
            int i = 1;
            // initialise list size outside of loop
            int listSize = lines.size();
            while (i < listSize) {

                columns = Arrays.asList((lines.get(i).split(",")));
                // add id value with each date to make it easy to print most recent properties sold

                // Code to change date format to YYYYMMDD so it can be sorted correctly
                // Taken from Stack Overflow post by Thomas 26-04-2014
                // Date accessed 17-03-2023
                // https://stackoverflow.com/questions/25502308/sort-objects-in-list-by-string-dd-mm-yyyy
                dateList1.add(columns.get(4).replaceAll("(\\d+)/(\\d+)/(\\d+)", "$3$2$1") + "," + columns.get(0));
                // end of referenced code
                i++;
            }
        } catch (IOException e) {
            System.out.println("IO Exception");
        }

        dateList1.sort(Collections.reverseOrder());

        for (int i = 0; i < n; i++) {
            // get id from most recent sold
            String[] whichId = dateList1.get(i).split(",");
            // add id to array
            properties3[i] = whichId[1];
        }

        return properties3;
    }
}
