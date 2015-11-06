package ru.mail.track.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.postgresql.ds.PGPoolingDataSource;

import ru.mail.track.message.User;

/**
 *
 */
public class DBConnection {
    public static void main(String[] args) throws Exception {

        Class.forName("org.postgresql.Driver");

        PGPoolingDataSource source = new PGPoolingDataSource();
        source.setDataSourceName("My DB");
        source.setServerName("178.62.140.149");
        source.setDatabaseName("arhangeldim");
        source.setUser("senthil");
        source.setPassword("ubuntu");
        source.setMaxConnections(10);

        Connection c = source.getConnection();

        Statement stmt = c.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM users;");
        while (rs.next()) {
            System.out.println(rs.getInt(1));
            System.out.println(rs.getString(2));
        }

        rs.close();
        stmt.close();

        /**
         * Получение сгенерированного базой ключа
         */
        stmt = c.createStatement();
        int result = stmt.executeUpdate("INSERT INTO \"users\" (name) values ('Anna');", Statement.RETURN_GENERATED_KEYS);
        System.out.println("result: " + result);

        rs = stmt.getGeneratedKeys();
        while (rs.next()) {
            int genId = rs.getInt(1);
            System.out.println("genID: " + genId);
        }


        /**
         * Использование executor для запроса в базу
         */
        QueryExecutor exec = new QueryExecutor();
        List<User> users = exec.execQuery(c, "SELECT * FROM users;", (r) -> {
            System.out.println("handle:");
            List<User> data = new ArrayList<>();
            while (r.next()) {
                User u = new User(r.getString(2), null);
                data.add(u);
            }
            return data;
        });

        System.out.println(users.toString());


        /**
         * Использование prepared executor для запроса в базу
         */
        Map<Integer, Object> prepared = new HashMap<>();
        prepared.put(1, "John");

        users = exec.execQuery(c, "SELECT * FROM users WHERE name = ?;", prepared, (r) -> {
            System.out.println("handle:");
            List<User> data = new ArrayList<>();
            while (r.next()) {
                User u = new User(r.getString(2), null);
                data.add(u);
            }
            return data;
        });

        System.out.println(users.toString());

    }
}
