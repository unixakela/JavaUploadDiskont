package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import sample.UploadClientDiscont;

import java.util.ArrayList;

public class ProgressCreatTable extends Task<ObservableList<TableWithClients> > {
    private String idClassifClient;
    private ArrayList<TableOfClients> tableOfClients  = new ArrayList<TableOfClients>();
    private ObservableList<TableWithClients> tableWithClients = FXCollections.observableArrayList();
    //File file = null;


    public ProgressCreatTable(String idClassifClient,ArrayList<TableOfClients> tableOfClients){
        this.idClassifClient = idClassifClient;
        this.tableOfClients = tableOfClients;
    }

    public ObservableList<TableWithClients> getTableWithClients() {
        return tableWithClients;
    }

    public void setTableWithClients(ObservableList<TableWithClients> tableWithClients) {
        this.tableWithClients = tableWithClients;
    }

    @Override
    protected ObservableList<TableWithClients> call() throws Exception {
    //    String idClassifClient = classifClientsBox.getValue().id;
        String idClassif = "";
        String nameClassif="";
        Boolean teleport = false;
        String comment = "";
      //  ArrayList<TableOfClients> tableOfClients  = new ArrayList<TableOfClients>();

        //tableOfClients =  WorkExcelUpload.getAll(file);

        int countBar=0;
        for (TableOfClients tableStr:
                tableOfClients) {

            countBar = countBar + 1;
            updateProgress(countBar,tableOfClients.size());
            updateMessage("");
            idClassif="";
            nameClassif="";
            comment = "";
            //  System.out.println(tableStr.name +" "+tableStr.card+" "+tableStr.phone +" "+ tableStr.dateBirthday);
            if (tableStr.card.trim().length()>0){
                if (Trm_in_cards_upload.CardIsExist(UploadClientDiscont.stmt,tableStr.card)) {
                    String idCards = Trm_in_cards_upload.IdByStart_card_code(UploadClientDiscont.stmt, tableStr.card);
                    String idClient = TrmInCardClient.getClientByCard(UploadClientDiscont.stmt, idCards);
                    idClassif = Trm_in_clients_upload.getClasifByClient(UploadClientDiscont.stmt, idClient);
             /*   System.out.println("card id = " +idCards);
                System.out.println("client for card = " + idClient);
                System.out.println("classif fo client card = " + idClassif);*/


                }
            }
            if (tableStr.phone.trim().length()>0){
            if (Trm_in_cards_upload.CardIsExist(UploadClientDiscont.stmt,tableStr.phone)) {
                String idCards = Trm_in_cards_upload.IdByStart_card_code(UploadClientDiscont.stmt,tableStr.phone);
                String idClient = TrmInCardClient.getClientByCard(UploadClientDiscont.stmt,idCards);
                //UploadClientDiscont.listClient.
                idClassif = Trm_in_clients_upload.getClasifByClient(UploadClientDiscont.stmt,idClient);
           /*     System.out.println("phone id = " +idCards);
                System.out.println("client for phone = " + idClient);
                System.out.println("classif fo client phone = " + idClassif);*/

            }
            }


            if (!idClassifClient.equals(idClassif)){teleport = true;}else {teleport = false;}

            //      System.out.println(UploadClientDiscont.listClassif.stream());
            for (Trm_in_classifclientsFоrUpload classif:
                    UploadClientDiscont.listClassif) {
                if (classif.id.equals(idClassif)){
                    nameClassif = classif.name;
                    break;
                }

            }
            Boolean cardExist = false;
            if (tableStr.card.trim().length()>0){
                cardExist =Trm_in_cards_upload.CardIsExist(UploadClientDiscont.stmt,tableStr.card);
            }
            Boolean phoneExist = false;
            if (tableStr.phone.trim().length()>0){
                 phoneExist = Trm_in_cards_upload.CardIsExist(UploadClientDiscont.stmt,tableStr.phone);
            }


            if (!cardExist&&!phoneExist){
                comment = comment + "Создадим новго клиента";
            }
            if (!cardExist&&phoneExist||cardExist&&!phoneExist){
                comment = comment + "Создадим новую карту существующего клиента" + "\n";

            }
            if ((cardExist||phoneExist)&&teleport){
                comment = comment + "Перенесем клиента в новую группу" + "\n";

            }



        /*    System.out.println(nameClassif);
            System.out.println(tableStr.name);*/

            tableWithClients.add(new TableWithClients(
                    true,
                    tableStr.name,
                    tableStr.card,
                    cardExist,
                    tableStr.phone,
                    phoneExist,
                    comment,
                    tableStr.dateBirthday,
                    teleport,
                    idClassif,
                    nameClassif

            ));

        }


        return tableWithClients;
    }



}
