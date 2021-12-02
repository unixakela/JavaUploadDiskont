package sample.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Trm_in_cards_upload {
    private Integer global_id;
    private String id;
    private String start_card_code;
    private String stop_card_code;
    private Date date_from;
    private Date date_till;
    private Integer active;
    private Integer version;
    private Integer deleted;



    public static boolean CardIsExist(Statement stmt, String card){
        ResultSet resultSet = null;
        boolean result = false;
        try {
            resultSet =stmt.executeQuery("SELECT * FROM trm_in_cards WHERE start_card_code ="+card);
            result = resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public static String IdByStart_card_code(Statement stmt, String card){
        String result="";
        ResultSet resultSet = null;
        try {
            resultSet =stmt.executeQuery("SELECT id FROM trm_in_cards WHERE start_card_code ="+card);
            resultSet.next();
            result = resultSet.getString(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public  static String getMaxId(Statement stmt){
        String result ="";
        ResultSet resultSet = null;
        try {
            resultSet =stmt.executeQuery("SELECT max(id) FROM trm_in_cards");
            resultSet.next();
            result = resultSet.getString(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public static  boolean creatCard(String start_card_code, String date_from, String id, Statement stmt){
        boolean result;
        String sql_create_card =
                "replace into trm_in_cards (start_card_code,active,date_from,id) values ('"+
                        start_card_code +"','1','"
                        +date_from+"','"
                        + id +"')";

    //   System.out.println(sql_create_card);
        try {
            stmt.executeUpdate(sql_create_card);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }


}
