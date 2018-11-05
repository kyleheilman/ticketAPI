package tickets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@SpringBootApplication
public class TicketAPI {
    public static void main(String[] args) {
        //Starts Spring Application on port 8443
        SpringApplication.run(TicketAPI.class, args);
    }
}
