package mkqq.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBSingleton {
    private static DBSingleton instance;


    private static final String URL = "jdbc:mysql://localhost:3306/libraryfinal";
    private static final String USER = "root";
    private static final String PASSWORD = "123";


    private static Connection connection;


    private DBSingleton() {
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception, or log it as needed
        }
    }


    public static DBSingleton getInstance() {
        if (instance == null) {
            instance = new DBSingleton();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            this.connection.close();
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
