package sample.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Date;

/**
 *
 */
public class TableWithClients {
 /*   <TableColumn prefWidth="55.0" text="Грузить" />
          <TableColumn prefWidth="95.0" text="Клиент" />
            <TableColumn prefWidth="75.0" text="Номер карты" />
            <TableColumn prefWidth="101.0" text="н/к существует" />
            <TableColumn prefWidth="75.0" text="Номер телефона" />
            <TableColumn prefWidth="75.0" text="н/т существует" />
            <TableColumn prefWidth="136.0" text="Комментарий" />*/
    SimpleBooleanProperty upload;
    public String client;
    String card;
    SimpleBooleanProperty cardExist;
    String phone;
    SimpleBooleanProperty phoneExist;
    String dateBirthday;
    String comment;
    String oldGroupId;
    String oldGroupName;
    SimpleBooleanProperty teleport;


    public String getOldGroupName() {
        return oldGroupName;
    }

    public void setOldGroupName(String oldGroupName) {
        this.oldGroupName = oldGroupName;
    }

    public String getOldGroupId() {
        return oldGroupId;
    }

    public void setOldGroupId(String oldGroupId) {
        this.oldGroupId = oldGroupId;
    }


    public boolean isTeleport() {
        return teleport.get();
    }

    public SimpleBooleanProperty teleportProperty() {
        return teleport;
    }

    public void setTeleport(boolean teleport) {
        this.teleport.set(teleport);
    }

    public TableWithClients(Boolean upload, String client, String card, Boolean cardExist, String phone, Boolean phoneExist, String comment, String dateBirthday, boolean teleport, String oldGroupId, String oldGroupName) {
      this.upload = new SimpleBooleanProperty(upload);
      this.client = client;
      this.card = card;
      this.cardExist = new SimpleBooleanProperty(cardExist);
      this.phone = phone;
      this.phoneExist = new SimpleBooleanProperty(phoneExist);
      this.dateBirthday = dateBirthday;
      this.comment = comment;
      this.teleport = new SimpleBooleanProperty(teleport);
      this.oldGroupId = oldGroupId;
      this.oldGroupName = oldGroupName;

   }

    public BooleanProperty uploadProperty() {
        return this.upload;
    }
    public Boolean isUpload() {
        return this.uploadProperty().get();
    }

    public void setUpload(Boolean upload) {
        this.uploadProperty().set(upload);
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BooleanProperty phoneExistProperty() {
        return this.phoneExist;
    }

    public Boolean isPhoneExist() {
        return this.phoneExistProperty().get();
    }

    public void setPhoneExist(Boolean phoneExist) {
        this.phoneExistProperty().set(phoneExist);
    }

    public String getDateBirthday() {
        return dateBirthday;
    }

    public void setDateBirthday(String dateBirthday) {
        this.dateBirthday = dateBirthday;
    }

    public String getComment() {
        return comment;
    }

    public BooleanProperty cardExistProperty() {
        return this.cardExist;
    }

    public Boolean isCardExist() {
        return this.cardExistProperty().get();
    }

    public void setCardExist(Boolean cardExist) {
        this.cardExistProperty().set(cardExist);
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

   // public

}
