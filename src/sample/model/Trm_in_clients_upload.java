package sample.model;

import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class Trm_in_clients_upload {
    private Integer global_id;
    private String id;
    private String classifclient;
    private String sur_name;
    private String name;
    private String patronymic;
    private String birthday;
    private String inn;
    private String passport;
    private String pricetype;
    private Integer type;
    private Integer allow_paycash;
    private Integer active;
    private Integer version;
    private Integer deleted;

        public static Charset toCharset = Charset.forName("utf8");
        public static Charset fromCharset = Charset.forName("cp866");

    public Trm_in_clients_upload(Integer global_id, String id, String classifclient, String sur_name, String name, String patronymic, String birthday, String inn, String passport, String pricetype, Integer type, Integer allow_paycash, Integer active, Integer version, Integer deleted) {
        this.global_id = global_id;
        this.id = id;
        this.classifclient = classifclient;
        this.sur_name = sur_name;
        this.name = name;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.inn = inn;
        this.passport = passport;
        this.pricetype = pricetype;
        this.type = type;
        this.allow_paycash = allow_paycash;
        this.active = active;
        this.version = version;
        this.deleted = deleted;
    }



    public static String getClasifByClient(Statement stmt, String idClient){
        String result="";
        ResultSet resultSet = null;
        try {
            String queryStr = "SELECT * FROM trm_in_clients WHERE id = '"+idClient+"'";
      //      System.out.println(queryStr);
            resultSet =stmt.executeQuery(queryStr);
            resultSet.next();

            result = resultSet.getString("classifclient");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public static Boolean creatClient(String sur_name,String classifclient, String uuid, String birthday, Statement stmt){
        boolean result;
        String sql_create_clients =
                "replace into trm_in_clients (sur_name,classifclient,birthday,allow_paycash,active,id) values ('"
                        +sur_name+"','"
                        + classifclient+"','"
                        +birthday
                        +"','1','1','"
                        + uuid + "')";

    //    toCharset.decode(toCharset.encode(sql_create_clients)).toString();
    //    System.out.println(sql_create_clients);
        try {
            stmt.executeUpdate(sql_create_clients);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }

    public static void changeGroup(String id, String classifclient, Statement stmt){
        String sql_change_group = "UPDATE trm_in_clients SET classifclient = "+ classifclient + " WHERE id = '"+id+"'";
    //    System.out.println(sql_change_group);
        try {
            stmt.executeUpdate(sql_change_group);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    public static ArrayList<Trm_in_clients_upload> getAll(Statement stmt){
        ArrayList<Trm_in_clients_upload> resault = new ArrayList<Trm_in_clients_upload>();

        Map<String,Charset> map = Charset.availableCharsets();
        Set<String> set = map.keySet();
        String[] strCharset = new String[2];// = set.toArray(new String[set.size()]);
        strCharset[1] = "UTF-8";
        strCharset[0] = "cp866";
      /*  for (int i = 0; i < strCharset.length; i++) {
            System.out.println(strCharset[i]);

        }*/
        /*String[] strCharset;
        for (Map.Entry<String, Charset> entry:
                map.entrySet()
             ) {
            strCharset.
            System.out.println(entry.getKey());

        }*/
        //  System.out.println(Charset.availableCharsets());
        //System.out.println(Charset.defaultCharset());
        try {
            ResultSet query = stmt.executeQuery("SELECT * FROM trm_in_clients");

            while (query.next()){

                //         System.out.println(HelperLexForUpload.charset(query.getString("name"),strCharset));

                toCharset = Charset.forName("UTF-8");
                fromCharset = Charset.forName(HelperLexForUpload.charset(query.getString("name"),strCharset));
                //  System.out.println(fromCharset);
                resault.add(new Trm_in_clients_upload(
                        Integer.parseInt(query.getString("global_id")),
                        query.getString("id"),
                        query.getString("classifclient"),
                        toCharset.decode(fromCharset.encode(query.getString("sur_name"))).toString(),
                        toCharset.decode(fromCharset.encode(query.getString("name"))).toString(),
                        query.getString("patronymic"),
                        query.getString("birthday"),
                        query.getString("inn"),
                        query.getString("passport"),
                        query.getString("pricetype"),
                        Integer.parseInt(query.getString("type")),
                        Integer.parseInt(query.getString("allow_paycash")),
                        Integer.parseInt(query.getString("active")),
                        Integer.parseInt(query.getString("version")),
                        Integer.parseInt(query.getString("deleted"))
                )
                );


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resault;
    }







    public Integer getGlobal_id() {
        return global_id;
    }

    public void setGlobal_id(Integer global_id) {
        this.global_id = global_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassifclient() {
        return classifclient;
    }

    public void setClassifclient(String classifclient) {
        this.classifclient = classifclient;
    }

    public String getSur_name() {
        return sur_name;
    }

    public void setSur_name(String sur_name) {
        this.sur_name = sur_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getPricetype() {
        return pricetype;
    }

    public void setPricetype(String pricetype) {
        this.pricetype = pricetype;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAllow_paycash() {
        return allow_paycash;
    }

    public void setAllow_paycash(Integer allow_paycash) {
        this.allow_paycash = allow_paycash;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
