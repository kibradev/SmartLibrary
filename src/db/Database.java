package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:sqlite:smartlibrary.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void init() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            String createBooks = "CREATE TABLE IF NOT EXISTS books(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title TEXT," +
                    "author TEXT," +
                    "year INTEGER" +
                    ")";

            String createStudents = "CREATE TABLE IF NOT EXISTS students(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "department TEXT" +
                    ")";

            String createLoans = "CREATE TABLE IF NOT EXISTS loans(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "bookId INTEGER," +
                    "studentId INTEGER," +
                    "dateBorrowed TEXT," +
                    "dateReturned TEXT" +
                    ")";

            stmt.execute(createBooks);
            stmt.execute(createStudents);
            stmt.execute(createLoans);

            System.out.println("Database tabloları olusturuldu");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
