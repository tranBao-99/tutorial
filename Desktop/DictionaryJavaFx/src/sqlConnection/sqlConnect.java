package sqlConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class sqlConnect {
    Connection connection = null;

    public Connection getConnection(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dictionary", "root","244466666");
        } catch (ClassNotFoundException | SQLException e) {
            return null;
        }

        return connection;
    }

}
