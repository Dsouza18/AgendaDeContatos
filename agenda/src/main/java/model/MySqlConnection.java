package Model;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnection implements ConnectionStrategy{
    private final String driver = "com.mysql.cj.jdbc.Driver";
    private final String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
    private final String user = "root";
    private final String password = "123456";
    @Override
    
    public Connection connect() {

        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
     }
    }

