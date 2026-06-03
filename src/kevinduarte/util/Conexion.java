package kevinduarte.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    private static final String URL = "jdbc:oracle:thin:@192.168.254.215:1521:orcl";
    private static final String USER = "admin";
    private static final String PASS = "admin";

    public static Connection getConexion() {
        Connection con = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            System.out.println("Error conexion: " + e.getMessage());
        }
        return con;
    }
}