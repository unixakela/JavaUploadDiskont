package sample.model;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MySQLConForUpload {

        private static Connection conn;

        public static Statement connectionMySQL(String host, String dbname, String user, String password) {
            Statement result = null;

            try{
       /*         Driver driver = new com.mysql.cj.jdbc.Driver();
                DriverManager.registerDriver(driver);

                Class.forName("com.mysql.cj.jdbc.Driver");*/
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                String url = "jdbc:mysql://" + host + "/" + dbname + "?useUnicode=true&characterEncoding=utf8&encoding=utf8&characterSetResults=utf8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&amp&serverTimezone=UTC";
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Connection to Store DB succesfull!");
                result = conn.createStatement();
                result.execute("set character set utf8");
                result.execute("set names utf8");
            }
            catch(Exception ex){
                System.out.println("Connection failed...");
                System.out.println(ex);
                return result;

            }
            return result;
        }
    }


