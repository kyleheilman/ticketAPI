package tickets.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLConnector {
    private Connection connector;

//creates connection to postgres with default username and password
    public SQLConnector (String jdbcConnection) {
        try {
            Class.forName("org.postgresql.Driver");
            connector = DriverManager.getConnection(jdbcConnection, "postgres", "postgres");
        }
        catch (Exception e) {
            System.out.println("unable to establish connection");
            e.printStackTrace();
        }
    }
//sends sql to postgres and returns a ResultSet object
    public ResultSet executeSql(String sql) {
        try {
            Statement statement = connector.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            return resultSet;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
