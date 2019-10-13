package training.adv.bowling.impl.zhangxinyi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

//    private static final String url = "jdbc:h2:file:C:/CitiIntern/Bowling";
    private static final String url = "jdbc:h2:mem:/Bowling";
    private static final String user = "admin";
    private static final String pwd = "admin";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(url, user, pwd);
            connection.setAutoCommit(false);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
