package training.adv.bowling.impl.xushizhi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static final String url = "jdbc:h2:tcp://localhost/~/test";
    private static final String user = "Karaya_12";
    private static final String pwd = "980417xiao1412";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection(url, user, pwd);
            conn.setAutoCommit(false);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
