package sample.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TrmInCardClient {
    private Integer global_id;
    private String card;
    private String client;
    private Integer version;
    private Integer deleted;

    public TrmInCardClient(Integer global_id, String card, String client, Integer version, Integer deleted) {
        this.global_id = global_id;
        this.card = card;
        this.client = client;
        this.version = version;
        this.deleted = deleted;
    }

    public static String getClientByCard(Statement stmt, String card){
        String result="";
        ResultSet resultSet = null;
        try {
            resultSet =stmt.executeQuery("SELECT client FROM trm_in_card_client WHERE card ="+card);
            resultSet.next();
            result = resultSet.getString(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public static boolean creatCardClient(String card, String client, Statement stmt){
        boolean result;
        String sql_creat_card_clients =
                "replace into trm_in_card_client (card,client) values ('"+
                card+"','"+
                client + "')";

     //   System.out.println(sql_creat_card_clients);
        try {
            stmt.executeUpdate(sql_creat_card_clients);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }


    }

}

