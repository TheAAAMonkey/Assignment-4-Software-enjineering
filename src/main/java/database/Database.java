package database;

import java.io.File;
import java.io.FileNotFoundException;                  // Import the File class
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

public class Database {
    // This class holds all the methods to deal with a csv file as a database
    private File dbfile;
    private String[] headers;
    private int records = 0;
    private Scanner reader;
    private boolean debug = false; 
    private boolean open = false;
    
    // Construcor for Database class
    // Default constructor
    public Database() {
    }

    ///////////////////////////////////////////////////////////////////////////////
    /// CREATE, OPEN AND CLEAR METHODS
    ///////////////////////////////////////////////////////////////////////////////
    // Create new database file
    public void create(String dbfileString, String[] headers) {
        // Implementation for creating a new database file

        // First check given name ends in .csv or .txt, if not output error and return
        if (!dbfileString.endsWith(".csv") && !dbfileString.endsWith(".txt")) {
            System.out.println("error: Invalid file name. File name must end in .csv or .txt");
            return;
        }


        try {
            File dbFile = new File(dbfileString); // Create File object
            if (dbFile.createNewFile()) {           // Try to create the file
                log("pass: File created: " + dbFile.getName());
                // Write headers to the file
                try (FileWriter writer = new FileWriter(dbFile)) {
                    String headerLine = String.join(",", headers);
                    writer.write(headerLine); // Write headers to the file
                    log("pass: New file created with headers: " + headerLine);
                } catch (IOException e) {
                    System.out.println("error: Error writing headers to the file.");
                }
            } else {
                System.out.println("error: File already exists. Cancelled creating file.");
            }


        } 
        catch (IOException e) {
            System.out.println("error: An error occurred creating the file.");
        }

    }

    // Open database file
    public void open(String dbfileString) {
        // log("Opening database file: " + dbfileString);
        try {
            this.dbfile = new File(dbfileString);

            this.reader = new Scanner(this.dbfile);
            log("pass: File opened successfully.");

            this.headers = this.reader.nextLine().split(",");
            log("pass: Headers read successfully: " + String.join(", ", this.headers));

            // Get number of records in file
            while (this.reader.hasNextLine()) {
                this.reader.nextLine();
                records++;
            }
            log("pass: Number of records: " + records);

            open = true;

        } 
        catch (FileNotFoundException e) {
            System.out.println("error: File not found. Failed to find file " + dbfileString);
            System.out.println("       Please check the file exists and the file name is correct. Include the file extension .csv or .txt if it has one.");
        }
        // log("Finished opening database file.");
    }

    // Method to clear data in database but not headers
    public void clear() {
        try (FileWriter writer = new FileWriter(this.dbfile, false)) {
            String headerLine = String.join(",", this.headers);
            writer.write(headerLine); // Write headers to the file
            log("pass: Database cleared successfully. Headers retained: " + headerLine);
            records = 0; // Reset record count
        } catch (IOException e) {
            System.out.println("error: Failed to clear database.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    /// PRIVATE METHODS FOR SCANNER AND FILEWRITER
    ///////////////////////////////////////////////////////////////////////////////
    
    // Method to send scanner to first line of file
    private void resetScanner() {
        try {
            this.reader = new Scanner(this.dbfile);
            log("pass: Scanner reset successfully.");
        } 
        catch (FileNotFoundException e) {
            System.out.println("error: File not found. Failed to find file " + dbfile.getName());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    /// INSERT, SELECT, WHERE, DELETE METHODS
    ///////////////////////////////////////////////////////////////////////////////

    // Insert method
    public void insert(String[] values) {
        // Implementation for inserting a new record into the database file
        try (FileWriter writer = new FileWriter(this.dbfile, true);) {
            String recordLine = String.join(",", values);
            writer.write("\n" + recordLine); // Write new record to the file
            log("pass: New record inserted: " + recordLine);
            records++; // Increment record count
        } catch (IOException e) {
            System.out.println("error: Failed to insert new record.");
        }
    }

    // Select method
    // Usage: select(String[] where) where where is an array of conditions for each column in the format "operator,value"
    //        operator can be ==, >>, <<, !=, --, >=, <=
    //        uses two char for each to reduce parsing complexity
    public DataTable select(String[] where) {
        // Select the where conditions to filter the records
        // List of where conidition formats:
        //   "==x" for equality
        //   ">>x" for greater than
        //   "<<x" for less than
        //   "!=x" for not equal to
        //   "--x" for no condition, just return the column value without checking it against anything, useful for when you want to select a column but not filter on it
        //   ">=x" for greater than or equal to x
        //   "<=x" for less than or equal to x
        // Conditions can be split on , to get the operator and value 
        // and column is the index of the where array

        // begin scanning the file
        resetScanner();
        // Skip headers
        this.reader.nextLine();
        // Create a DataTable to hold the selected records
        DataTable selectedRecords = new DataTable(this.headers);
        // Loop through the records and apply the where conditions
        while (this.reader.hasNextLine()) {
            // Get the next record and split it into values
            String[] recordValues = this.reader.nextLine().split(",");

            // Check if the record matches the where conditions
            boolean matches = true;
            // Loop through each value in the where array and check the condition
            for (int i = 0; i < where.length; i++) {
                // get the operator and value from the where condition
                // using first two char as operator and the rest as value
                String condition = where[i];
                String operator = condition.substring(0, 2);
                String value = condition.substring(2);

                // If the operator is --, there is no condition, just go the next ocnditino
                if (operator.equals("--")) {
                    continue;
                }

                // Check the condition based on the operator
                matches = checkCondition(operator, recordValues[i], value);

            }

            // If matches is still true after checking all conditions, 
            // add the record to selectedRecords
            if (matches) {
                selectedRecords.addRecord(recordValues);
            }
        }
        return selectedRecords;
    }

    // Select only certain columns after a where has been applied
    public DataTable select(int[] selectedColumns, String[] where) {
        // Select the columsn where conditions are met

        // begin scanning the file
        resetScanner();
        // Skip headers
        this.reader.nextLine();

        // System to make sure headers on the new table are correct
        // Create new headers based on what headers are selected
        String[] selectedHeaders = new String[selectedColumns.length];

        for (int i = 0 ; i < selectedColumns.length ; i++) {
            selectedHeaders[i] = headers[selectedColumns[i]];
        }
        
        // Create a DataTable to hold the selected records
        DataTable selectedRecords = new DataTable(selectedHeaders);

        // Loop through the records and apply the where conditions
        while (this.reader.hasNextLine()) {
            // Get the next record and split it into values
            String[] recordValues = this.reader.nextLine().split(",");
            // Create the record to add to the output table to be filled later
            String[] appendRecord = new String[selectedColumns.length];

            // Check if the record matches the where conditions
            boolean matches = true;
            // Loop through each value in the where array and check the condition
            for (int i = 0; i < where.length; i++) {
                // get the operator and value from the where condition
                // using first two char as operator and the rest as value
                String condition = where[i];
                String operator = condition.substring(0, 2);
                String value = condition.substring(2);

                // construct new record to append to table
                for (int j = 0 ; j < selectedColumns.length ; j++) {
                    if (i == selectedColumns[j]) {
                        appendRecord[j] = recordValues[i];
                    }
                }

                // If the operator is --, there is no condition, just go the next ocnditino
                if (operator.equals("--")) {
                    continue;
                }

                // Check the condition based on the operator
                matches = checkCondition(operator, recordValues[i], value);
            }

            // If matches is still true after checking all conditions, 
            // add the record to selectedRecords
            if (matches) {
                selectedRecords.addRecord(appendRecord);
            }
        }

        return selectedRecords;
    }

    // Select only certain columns after a where has been applied
    public DataTable select(int[] selectedColumns) {
        // Select the columsn where conditions are met

        // begin scanning the file
        resetScanner();
        // Skip headers
        this.reader.nextLine();

        // System to make sure headers on the new table are correct
        // Create new headers based on what headers are selected
        String[] selectedHeaders = new String[selectedColumns.length];

        for (int i = 0 ; i < selectedColumns.length ; i++) {
            selectedHeaders[i] = headers[selectedColumns[i]];
        }
        
        // Create a DataTable to hold the selected records
        DataTable selectedRecords = new DataTable(selectedHeaders);

        // Loop through the records and apply the where conditions
        while (this.reader.hasNextLine()) {
            // Get the next record and split it into values
            String[] recordValues = this.reader.nextLine().split(",");
            // Create the record to add to the output table to be filled later
            String[] appendRecord = new String[selectedColumns.length];

            // construct new record to append to table
            for (int i = 0 ; i < selectedColumns.length ; i++) {
                appendRecord[i] = recordValues[selectedColumns[i]];
            }
            // add the record to selectedRecords
            selectedRecords.addRecord(appendRecord);

        }

        return selectedRecords;
    }

    // Select method that returns whole records without any where conditions
    public DataTable select() {
        // begin scanning the file
        resetScanner();
        // Skip headers
        this.reader.nextLine();
        // Create a DataTable to hold the selected records
        DataTable selectedRecords = new DataTable(this.headers);
        // Loop through the records and add them to selectedRecords
        while (this.reader.hasNextLine()) {
            // Get the next record and split it into values
            String[] recordValues = this.reader.nextLine().split(",");
            selectedRecords.addRecord(recordValues);
        }
        return selectedRecords;
    }

    // Extracted segment of where clause to make code more consise
    private static boolean checkCondition(String operator, String recordValue, String compareValue) {
        boolean matches = true;

        switch (operator) {
            case "==" -> {
                if (!recordValue.equals(compareValue)) {
                    matches = false;
                }
            }
            case ">>" -> {
                if (Integer.parseInt(recordValue) <= Integer.parseInt(compareValue)) {
                    matches = false;
                }
            }
            case "<<" -> {
                if (Integer.parseInt(recordValue) >= Integer.parseInt(compareValue)) {
                    matches = false;
                }
            }
            case "!=" -> {
                if (recordValue.equals(compareValue)) {
                    matches = false;
                }
            }
            case ">=" -> {
                if (Integer.parseInt(recordValue) < Integer.parseInt(compareValue)) {
                    matches = false;
                }
            }
            case "<=" -> {
                if (Integer.parseInt(recordValue) > Integer.parseInt(compareValue)) {
                    matches = false;
                }
            }
            default -> {
                System.out.println("error: Unknown operator in where clause.");
                matches = false;
            }
        }

        return matches;
    }

    // edit records in place where
    public void edit(String[] where, String[] newRecord) {
        // check both String[] are same length as headers
        if (where.length != headers.length || newRecord.length != headers.length ) {
            System.out.println("error: Invalid inpput for edit record. Arrays must have the same number of elements as there are headers for the database.");
        }

        // There's unfortunatly no way in java I could find to overwrite a specific line in a file, so this method is hard hitting on the RAM
        DataTable whereResults = this.select(where);
        
        // Check there are any results at all
        if (whereResults.isEmpty()) {
            System.out.println("error: Where clause returned no results.");
            return;
        }

        try {
            // Get all lines
            List<String> lines = Files.readAllLines(dbfile.toPath()); 

            // Iterate through lines
            for (int i = 1 ; i < lines.size() ; i++) {
                // Iterate through where results
                for (int j = 0 ; j < whereResults.getRecordCount() ; j++) {
                    // Get where result as a string
                    String checkRecord = String.join(",", whereResults.getRecord(j));
                    // if line amtches where result then:
                    if (lines.get(i).equals(checkRecord)) {
                        // Set line ot new record
                        lines.set(i, String.join(",", newRecord));
                        whereResults.removeRecord(j);
                    }
                }
            }

            // Write new lines to file
            Files.write(dbfile.toPath(), lines);



        } catch (Exception e) {
            System.out.println("error: Failed to read file");
        }

    }

    ///////////////////////////////////////////////////////////////////////////////
    /// MISCELLANEOUS METHODS
    ///////////////////////////////////////////////////////////////////////////////
    // Checks if debugging is enabled and prints message if it is, 
    // mainly for pass/fail messages but not error messages
    private void log(String message) {
        if (debug) {
            System.out.println(message);
        }
    }

    // debug controls
    public void doDebug() {
        debug = true;
    }
    public void noDebug() {
        debug = false;
    }

    // Setters and getters
    public String[] getHeaders() {
        return this.headers;
    }

    public String getDbfile() {
        return dbfile.getName();
    }

    public boolean isOpen() {
        return open;
    }

    public int getRecords() {
        return records;
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




}
