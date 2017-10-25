package com.sujathay.dao;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class LoginDao {
    public static boolean validate(String name, String pass) throws ClassNotFoundException, IOException {        
        boolean status = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        Properties props = new Properties();
        
        FileInputStream in = new FileInputStream("D:\\db.properties");
        //LoginDao lDao = new LoginDao();
        props.load(in);
        in.close();

        String driver = props.getProperty("jdbc.driver");
        if (driver != null) {
            Class.forName(driver) ;
        }

        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        try {
        	Connection con = DriverManager.getConnection(url, username, password);

            pst = con
                    .prepareStatement("select * from data where user=? and password=?");
            pst.setString(1, name);
            pst.setString(2, pass);

            rs = pst.executeQuery();
            status = rs.next();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return status;
    }
    
    private InputStream getProps() {
    	return getClass().getClassLoader().getResourceAsStream("db.properties");
    }
}
