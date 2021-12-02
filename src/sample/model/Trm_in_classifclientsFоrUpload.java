package sample.model;


import sample.model.HelperLexForUpload;


import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;




public class Trm_in_classifclientsFоrUpload {


    int global_id;
    public String id;
    public String name;
    String owner;
    String pricetype;
    int version;
    int deleted;

    public Trm_in_classifclientsFоrUpload(String global_id, String id, String owner, String name, String pricetype, String version, String deleted) {
        this.global_id = Integer.parseInt(global_id);
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.pricetype = pricetype;
        this.version = Integer.parseInt(version);
        this.deleted = Integer.parseInt(deleted);

    }

    public static ArrayList<Trm_in_classifclientsFоrUpload> getAll(Statement stmt){
        ArrayList<Trm_in_classifclientsFоrUpload> resault = new ArrayList<Trm_in_classifclientsFоrUpload>();
        Charset toCharset = Charset.forName("utf8");
        Charset fromCharset = Charset.forName("cp866");
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
            ResultSet query = stmt.executeQuery("SELECT * FROM trm_in_classifclients WHERE deleted = 0");

            while (query.next()){

         //         System.out.println(HelperLexForUpload.charset(query.getString("name"),strCharset));

                toCharset = Charset.forName("UTF-8");
                fromCharset = Charset.forName(HelperLexForUpload.charset(query.getString("name"),strCharset));
              //  System.out.println(fromCharset);
                resault.add(new Trm_in_classifclientsFоrUpload(query.getString("global_id"),
                        query.getString("id"),
                        query.getString("owner"),
                        toCharset.decode(fromCharset.encode(query.getString("name"))).toString(),
                        //query.getString("name"),
                        query.getString("pricetype"),
                        query.getString("version"),
                        query.getString("deleted")));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resault;
    }
    public String toString() {
        return name;
    }

}