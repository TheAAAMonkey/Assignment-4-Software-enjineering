
public class main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        // Testing dp part 1: create and open database
        Database db = new Database();
        db.doDebug();

        String[] headers = new String[]{"id", "name", "age"};

        db.create("mydatabase.csv", headers);
        db.open("mydatabase.csv");

        // System.out.println("Testing efiun iulwesn fliuweifln");

        // Testing DataTable class
        DataTable dt = new DataTable(headers);
        dt.addRecord(new String[]{"0001", "Alice", "25"});
        dt.addRecord(new String[]{"0002", "Bob", "30"});
        dt.addRecord(new String[]{"0003", "Charlie", "35"});
        dt.print();

        // Testing dp part 2: insert records
        db.clear();
        db.insert(new String[]{"0001", "Alice", "25"});
        db.insert(new String[]{"0002", "Chester", "45"});
        db.insert(new String[]{"0003", "Bob", "30"});
        db.insert(new String[]{"0004", "Ronald", "55"});
        db.insert(new String[]{"0005", "Charlie", "35"});

        String[] where;
        DataTable result;
        System.out.println();

        System.out.println("CONDITION: col1 >>2");
        where = new String[]{">>2", "--", "--"};
        result = db.select(where);
        result.print();
        System.out.println();

        System.out.println("CONDITION: col2 ==Bob");
        where = new String[]{"--", "==Bob", "--"};
        result = db.select(where);
        result.print();
        System.out.println();

        System.out.println("CONDITION: col3 >=30");
        where = new String[]{"--", "--", ">=30"};
        result = db.select(where);
        result.print();
        System.out.println();

        // Testing dp part 3: Selecting specific columns with SELECT and WHERE conditions
        int[] columns; 
        
        System.out.println("SELECT columns 1 and 2 WHERE col3 >=30");
        columns = new int[]{1, 2};
        where = new String[]{"--", "--", ">=30"};
        result = db.select(columns, where);
        result.print();
        System.out.println();

        System.out.println("SELECT column 2 WHERE col2 ==Bob");
        columns = new int[]{1};
        where = new String[]{"--", "==Bob", "--"};
        result = db.select(columns, where);
        result.print();
        System.out.println();

        System.out.println("SELECT column 1");
        columns = new int[]{0};
        result = db.select(columns);
        result.print();
        System.out.println();

        // Testing dt part 2: Sorting records with SORT BY
        System.out.println("SELECT * SORT BY column 2");
        result = db.select();
        result.sortByColumn(2);
        result.print();
        System.out.println();

        System.out.println("SELECT * SORT BY column 1 DESCENDING");
        result = db.select();
        result.sortByColumn(1);
        result.reverse();
        result.print();
        System.out.println();


    }
}