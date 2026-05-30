import java.util.ArrayList;
public class Query {
    // Method to turn sql query into list of databse functions to execute
    public static DataTable query(Database db, String query) {
        // Check db is open
        if (!db.isOpen()) {
            System.out.println("error: Database object must be open to query!");
            return new DataTable(new String[]{""});
        }

        // Check different sections
        boolean isSelect = query.contains("SELECT");
        boolean isWhere = query.contains("WHERE");
        boolean isOrder = query.contains("ORDER BY");

        String selectquery;
        String wherequery;

        int[] select;

        // Cases
        if (isSelect && !isWhere && !isOrder) {
            selectquery = query.substring(query.indexOf("SELECT"));
            select = parseSelect(db, selectquery);
        }


    }

    // Method to process the SELECT section of the querry
    private static int[] parseSelect(Database db, String selectquery) {

        //
        if (selectquery.contains("*")) {
            int[] select = new int[db.getHeaders().length];
            for (int i = 0 ; i < db.getHeaders().length ; i++) {
                select[i] = i;
            }
        }

        ArrayList<Integer> select = new ArrayList<>();
        String[] selectsplit = selectquery.split(" ");
        for (String arg : selectsplit) {
            // Skip if it is an && operator
            if (arg.contains("&&") || arg.contains("SELECT") ) {
                continue;
            }

            // Else trim and assume as header
            arg.trim();

            // Get column index
            int columnIndex = getColumnIndex(db, arg);
            if(columnIndex > -1) { // Check it exists
                select.add(Integer.parseInt(arg));
            } else { // Error if not
                System.out.println("error: parsing SQL query did not find a header with name: " + arg);
                continue;
            }

            
        }

        // Convert to int[]
        select.toArray();
        int[] newSelect = new int[select.size()];

        for (int i = 0 ; i < select.size() ; i++ ) {
            newSelect[i] = select.get(i);
        }

        // return int[]
        return newSelect;
    }

    // Method to get a column index by name
    private static int getColumnIndex(Database db, String columnName) {
        for (int i = 0; i < db.getHeaders().length; i++) {
            if (db.getHeaders()[i].equals(columnName)) {
                return i;
            }
        }

        // if not found, return -1 and print error
        System.out.println("error: Column name not found: " + columnName);
        return -1;
    }

    // Method to get a column by index
    private static String getColumnHeader(Database db, int index) {
        // Check index is valid
        if (index < 0 || index >= db.getHeaders().length) {
            System.out.println("error: Column index out of bounds.");
            return null;
        }

        return db.getHeaders()[index];
    }
}
