package school;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SchoolAccess {
    private Connection conn;
    private static final String teacherTable = "teacher";

    public SchoolAccess()
            throws SQLException, ClassNotFoundException {

        // Class.forName("org.hsqldb.jdbc.JDBCDriver" );

        //STEP 2: Check if JDBC driver is available
        Class.forName( "com.mysql.cj.jdbc.Driver");
        //STEP 3: Open a connection
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cr06_johannes_wahl_school" +
                        "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC" ,
                "root",
                "");

        // we will use this connection to write to a file
        conn.setAutoCommit(true);
        conn.setReadOnly( false);
    }

    public void closeDb() throws SQLException {
        conn.close();
    }

    public List<Teacher> getAllRows()   throws SQLException {

        String sql = "SELECT * FROM " + teacherTable + " ORDER BY lastName" ;
        PreparedStatement pstmnt = conn.prepareStatement(sql);
        ResultSet rs = pstmnt.executeQuery();
        List<Teacher> list = new ArrayList<>();

        while  (rs.next()) {
            int i = rs.getInt("teacherID" );
            String firstName = rs.getString("firstName" );
            String lastName = rs.getString("lastName" );
            String email = rs.getString("email");
            list.add( new Teacher(i, firstName, lastName, email));

        }

        pstmnt.close(); // also closes related result set
        return list;
    }

    public  boolean nameExists(Teacher teacher) throws SQLException {

        String sql = "SELECT COUNT(teacherID) FROM " + teacherTable + " WHERE lastName = ? AND teacherID <> ?" ;
        PreparedStatement pstmnt = conn.prepareStatement(sql);
        pstmnt.setString( 1 , teacher.getLastName());
        pstmnt.setInt( 2 , teacher.getTeacherID());
        ResultSet rs = pstmnt.executeQuery();
        rs.next();
        int  count = rs.getInt( 1 );
        pstmnt.close();

        if  (count > 0 ) {

            return   true ;
        }

        return   false ;
    }

    public   int  insertRow(Teacher teacher)
            throws  SQLException {

        String dml = "INSERT INTO "  + teacherTable + " VALUES (DEFAULT, ?, ?)" ;
        PreparedStatement pstmnt = conn.prepareStatement(dml,
                PreparedStatement.RETURN_GENERATED_KEYS);
        pstmnt.setString( 1 , teacher.getLastName());
        pstmnt.setString( 2 , teacher.getEmail());
        pstmnt.executeUpdate(); // returns insert count

        // get identity column value
        ResultSet rs = pstmnt.getGeneratedKeys();
        rs.next();
        int  id = rs.getInt( 1 );

        pstmnt.close();
        return  id;
    }

    public   void  updateRow(Teacher teacher)
            throws  SQLException {

        String dml = "UPDATE "  + teacherTable +
                " SET firstName = ?, lastName = ?, email = ? "  + " WHERE teacherID = ?" ;
        PreparedStatement pstmnt = conn.prepareStatement(dml);
        pstmnt.setString( 1 , teacher.getFirstName());
        pstmnt.setString( 2 , teacher.getLastName());
        pstmnt.setString(3, teacher.getEmail());
        pstmnt.setInt( 4 , teacher.getTeacherID());
        pstmnt.executeUpdate(); // returns update count
        pstmnt.close();
    }

    public   void  deleteRow(Teacher teacher)
            throws  SQLException {

        String dml = "DELETE FROM "  + teacherTable + " WHERE teacherID = ?" ;
        PreparedStatement pstmnt = conn.prepareStatement(dml);
        pstmnt.setInt( 1 , teacher.getTeacherID());
        pstmnt.executeUpdate(); // returns delete count (0 for none)
        pstmnt.close();
    }
}
