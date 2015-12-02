package ru.mail.track.jdbc;

        import java.sql.*;

/**
 * Created by a.borodin on 23.11.2015.
 */
public class TestBD {
    public static void main(String[] argv) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");

        Connection c = DriverManager.getConnection("jdbc:postgresql://178.62.140.149:5432/virtuosmipt",
                "senthil", "ubuntu");

        Statement stmt;
        String sql;
        sql = "INSERT INTO User VALUES (2, 'Paul', 'paul')";

        stmt = c.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
        c.commit();
        c.close();
    }
}
