package database;

import java.util.ArrayList;

public class DataTable {
    // A class to store the output of a query, which can be printed in a nice format

    private ArrayList<String[]> data;
    private String[] headers;

    public DataTable(String[] headers) {
        this.headers = headers;
        this.data = new ArrayList<String[]>();
        System.out.println("pass: DataTable created with " + headers.length + " columns.");
    }

    public DataTable(String[] headers, ArrayList<String[]> data ) {
        this.data = data;
        this.headers = headers;
        System.out.println("pass: DataTable created with " + data.size() + " records and " + headers.length + " columns.");
    }

    // methods to get data and headers
    public ArrayList<String[]> getData() {
        return data;
    }

    public String[] getHeaders() {
        return headers;
    }

    // Method to get the number of records
    public int getRecordCount() {
        return data.size();
    }

    // Method to get specific record by index
    public String[] getRecord(int index) {
        if (index < 0 || index >= data.size()) {
            System.out.println("error: Record index out of bounds.");
            return null;
        }
        return data.get(index);
    }

    // Method to remove record from the TABLE ONLY for for loops complexity optimisation
    public void removeRecord(int index) {
        data.remove(index);
    }

    // Methdo to check if empty
    public boolean isEmpty() {
        return data.isEmpty();
    }

    // Method to get a column index by name
    public int getColumnIndex(String columnName) {
        for (int i = 0; i < headers.length; i++) {
            if (headers[i].equals(columnName)) {
                return i;
            }
        }

        // if not found, return -1 and print error
        System.out.println("error: Column name not found: " + columnName);
        return -1;
    }

    // Method to get a column by index
    public String[] getColumn(int index) {
        // Check index is valid
        if (index < 0 || index >= headers.length) {
            System.out.println("error: Column index out of bounds.");
            return null;
        }

        // Column output array
        String[] column = new String[data.size()];

        // get column data
        for (int i = 0; i < data.size(); i++) {
            column[i] = data.get(i)[index];
        }

        return column;
    }

    // Method to get a column by name
    public String[] getColumn(String columnName) {
        int index = getColumnIndex(columnName);
        if (index == -1) {
            System.out.println("error: Column name not found: " + columnName);
            return null;
        }
        return getColumn(index);
    }

    // print data
    public void print() {
        // Print headers
        System.out.println(String.join(" | ", headers));
        System.out.println("-".repeat(headers.length * 10)); // Separator line

        // Print data rows
        for (String[] row : data) {
            System.out.println(String.join(" | ", row));
        }
    }

    // setters 
    public void setData(ArrayList<String[]> data) {
        this.data = data;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

    // Method to add a record to the DataTable
    public void addRecord(String[] record) {
        // Check record length matches headers length
        if (record.length != headers.length) {
            System.out.println("error: Record length does not match number of columns.");
            return;
        }

        this.data.add(record);
    }

    // Method to sort records using values in a specific column
    public void sortByColumn(int columnIndex) {
        // Check column index is valid
        if (columnIndex < 0 || columnIndex >= headers.length) {
            System.out.println("error: Column index out of bounds.");
            return;
        }

        // Sort data using quicksort algorithm
        quickSort(data, columnIndex, 0, data.size() - 1);
    }

    // Method to sort records using values in a specific column by name
    // Based on code from https://www.geeksforgeeks.org/quick-sort/
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // partition function
    static int partition(ArrayList<String[]> data, int columnIndex, int low, int high) {
        // pivot is the last element in the current sub-array
        String pivot = data.get(high)[columnIndex];
        // the right position of pivot found so far
        int i = low - 1;

        // traverse arr[low..high] and move all smaller
        // elements to the left side. Elements from low to
        // i are smaller after every iteration
        for (int j = low; j < high; j++) {
            if (data.get(j)[columnIndex].compareTo(pivot) <= 0) {
                i++;
                String[] temp = data.get(i);
                data.set(i, data.get(j));
                data.set(j, temp);
            }
        }

        // swap the pivot element with the element at i + 1
        String[] temp = data.get(i + 1);
        data.set(i + 1, data.get(high));
        data.set(high, temp);

        // return the index of the pivot element
        return i + 1;
    }

    // the QuickSort function implementation
    static void quickSort(ArrayList<String[]> data, int columnIndex, int low, int high) {
        if (low < high) {
            
            // pi is the partition return index of pivot
            int pi = partition(data, columnIndex, low, high);

            // recursion calls for smaller elements
            // and greater or equals elements
            quickSort(data, columnIndex, low, pi - 1);
            quickSort(data, columnIndex, pi + 1, high);
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    
    // Method to reverse orders of records in place
    public void reverse() {
        int left = 0;
        int right = data.size() - 1;

        while (left < right) {
            String[] temp = data.get(left);
            data.set(left, data.get(right));
            data.set(right, temp);
            left++;
            right--;
        }
    }





    
}
