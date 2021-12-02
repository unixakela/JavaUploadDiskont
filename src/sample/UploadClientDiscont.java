package sample;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import sample.model.MySQLConForUpload;
import sample.model.Trm_in_classifclientsFоrUpload;
import sample.model.Trm_in_clients_upload;

import java.io.File;
import java.sql.Statement;
import java.util.ArrayList;

public class UploadClientDiscont extends Application {
    //static public String DBASENAME = "ukmserver_out";

//    static public String HOST = "127.0.0.1";
    /*static public String USER = "root";
    static public String PASSWORD = "";*/
    static public String DBASENAME = "ukmserver";

    static public String HOST = "192.168.2.241";
    static public String USER = "ukm_web";
    //static public String USER = "root";
    static public String PASSWORD = "CtHDbCGK.C";
    public static String logEr;

    /*static final public String DBASENAME = "ukmserver";
    static final public String HOST = "192.168.2.241";
    static final public String USER = "wishuser";
    static final public String PASSWORD = "wishpass";*/
    public  static Statement stmt;
   // public static Properties properties = new Properties();
    public static ArrayList<Trm_in_classifclientsFоrUpload> listClassif = new ArrayList<Trm_in_classifclientsFоrUpload>();
    public static ArrayList<Trm_in_clients_upload> listClient = new ArrayList<Trm_in_clients_upload>();
    public  static  File fileProperties = new File(System.getProperty("user.home")+System.getProperty("file.separator")+"UploadClientDiscont"+
            System.getProperty("file.separator")+"main.properties");
    public static   File dirProperties = new File(System.getProperty("user.home")+System.getProperty("file.separator")+"UploadClientDiscont");

    public static void main(String[] args) {
        stmt = MySQLConForUpload.connectionMySQL(HOST,DBASENAME,USER,PASSWORD);
        System.out.println(stmt);
        if (stmt==null){
            System.out.println("нет связи с базой данных, работа программы не возможна");
            return;
        }
        listClassif = Trm_in_classifclientsFоrUpload.getAll(stmt);
        System.out.println();
   launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Read file fxml and draw interface.
            ObservableList<Trm_in_classifclientsFоrUpload> observableArrayList = FXCollections.observableArrayList(UploadClientDiscont.listClassif);
            ComboBox<Trm_in_classifclientsFоrUpload> classifClientsBox= new ComboBox<Trm_in_classifclientsFоrUpload>(observableArrayList);

            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/rootWindow.fxml"));

            primaryStage.setTitle("Загрузка клиентов в УКМ 4.0");
            primaryStage.setScene(new Scene(root));
            System.out.println(primaryStage);
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}
