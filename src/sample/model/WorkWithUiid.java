package sample.model;

import org.apache.commons.codec.digest.DigestUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WorkWithUiid {

    private static boolean uniqueUiid(Statement stmt, String uiid){
        ResultSet resultSet = null;
        boolean result = false;
        //System.out.println("SELECT * FROM trm_in_clients WHERE id ="+uiid+";");
        try {
            //    System.out.println("SELECT * FROM trm_in_clients WHERE id ="+uiid);
            resultSet =stmt.executeQuery("SELECT * FROM trm_in_clients WHERE id ='"+uiid+"'");
            result = resultSet.next();
        } catch (SQLException throwables) {
            System.out.println("ошибка проверки уникальности");
            throwables.printStackTrace();
        }

        return result;

    }

    public static String getNewUiid(Statement stmt){
        String resultUiid ="";
        boolean unikUiid = true;
        while (unikUiid){
            resultUiid = generate_guid().replace("{","").replace("}","");
            unikUiid = uniqueUiid(stmt,resultUiid);
        }

        return resultUiid;
    }

    private static String generate_guid(){
        String generateUiid = "";
        long randint = (long) (double) (Math.random()*10000*System.currentTimeMillis());
        String rndstr = Long.toString(randint);
        //    System.out.println(rndstr);
        String md5Hex = DigestUtils.md5Hex(rndstr).toUpperCase();
        //    System.out.println(md5Hex);
        //return md5Hex;
        String skobkaOpen = Character.toString((char) 123);
        String skobkaClose = Character.toString((char) 125);
        String sepearator = Character.toString((char) 45);
        generateUiid = skobkaOpen+
                md5Hex.substring(0,8) + sepearator +
                md5Hex.substring(8,12) + sepearator +
                md5Hex.substring(12,16) + sepearator +
                md5Hex.substring(16,20) + sepearator +
                md5Hex.substring(20,32) +
                skobkaClose;


        return generateUiid;
    }



}
