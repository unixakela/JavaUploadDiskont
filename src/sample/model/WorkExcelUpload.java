package sample.model;

/*import com.worklex.UploadDiscountClients.FileExtention;
import com.worklex.UploadDiscountClients.HelperLex;
import com.worklex.UploadDiscountClients.TableOfValues;*/

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sample.UploadClientDiscont;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class WorkExcelUpload {
    public static Object getExcelCell(Cell cell) {

        switch (cell.getCellType()) {
            case STRING:
                return  cell.getStringCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)){
                    return cell.getDateCellValue();
                }
                else {
                    return  Long.toString((long)cell.getNumericCellValue());
                }
            default:
                System.out.println("неизвестный тип ячейки" + cell.getCellType());
                return  cell;
        }

    }

    public static ArrayList<TableOfClients> getAll(File file,String prefix,int len) {
        Set<String> setCard = new TreeSet<String>();
        Set<String> setPhone = new TreeSet<String>();
        Workbook workbook = null;
        Sheet sheet;
        Row row;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ArrayList<TableOfClients> table = new ArrayList<TableOfClients>();

        try {
            FileInputStream inputStream = new FileInputStream(file);

            switch (FileExtention.getFileExtension(file)){
                case "xls": workbook = new HSSFWorkbook(inputStream);
                    break;

                case "xlsx":workbook = new XSSFWorkbook(inputStream);
                    break;

                default:
                    System.out.println("передан не эксель файл");
            }
            int list =0;

            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            while (sheetIterator.hasNext()){
                list = list +1;
                int roww=0;
                int error=0;
                sheet = sheetIterator.next();
                Iterator<Row> rowIterator = sheet.rowIterator();
                while (rowIterator.hasNext()){
                    roww = roww + 1;
                    System.out.println(list + " " + roww);
                    row = rowIterator.next();
                    if (row==null){
                        System.out.println("supper");
                    }
                    String name ="";
                    try {
                        name = String.valueOf(getExcelCell(row.getCell(1)));
                    }
                    catch (Exception e)
                    {
                        error=error+1;
                        continue;
                    }

                    if (name.trim().length()==0) {
                    //    System.out.println(error);
                        if (error == 4){
                            break;
                        }
                            error =error+1;
                            continue;
                    }
             //       System.out.println(name);
                    if (name.length()==0){}
                    String phone = makeNicePhone(String.valueOf(getExcelCell(row.getCell(2))));
                    if (phone.trim().length()==0){
                        //System.out.println("на листе " + list + " в строке " + roww + " номер телефона "
//                                + String.valueOf(getExcelCell(row.getCell(2))) + " несоотвествует стандарту" );
                        UploadClientDiscont.logEr = UploadClientDiscont.logEr + "В файле "
                                 + file.getAbsolutePath().trim() +
                                " на листе " + list + " в строке " + roww + " номер телефона "
                                + String.valueOf(getExcelCell(row.getCell(2))) + " несоотвествует стандарту" + "\n";

                   //     continue;
                        phone="";
                    }
                 //   System.out.println(phone);







                    String card = makeNiceCard(String.valueOf(getExcelCell(row.getCell(3))));
                    if (card.length()>0)
                    {
                        card = prefix + HelperLexForUpload.addCharLeft(card,"0",len-prefix.length());
                    }

                //    System.out.println(card);
                    String dateBirthday = "1970-01-01 00:00:00";
                    try {
                        dateBirthday = sdf.format(getExcelCell(row.getCell(4)));
                    }
                    catch (Exception e){

                    }
                    if (!setCard.add(card))
                    {
                        UploadClientDiscont.logEr = UploadClientDiscont.logEr + "В файле "
                                + file.getAbsolutePath().trim() +
                                " на листе " + list + " в строке " + roww + " предовращена попытка повторно внести карту "
                                + card+ "в одной загрузке"  + "\n";
                     continue;
                    }
                    if (!setPhone.add(phone)&&phone.length()>0)
                    {
                        UploadClientDiscont.logEr = UploadClientDiscont.logEr + "В файле "
                                + file.getAbsolutePath().trim() +
                                " на листе " + list + " в строке " + roww + " предовращена попытка повторно внести телефон "
                                + card +  "в одной загрузке" + "\n";
                        continue;
                    }

                 //   System.out.println(dateBirthday);

                    //System.out.println(name + " " + phone +" "+ card + " " +dateBirthday);
                    table.add(new TableOfClients(name,phone,card,dateBirthday));
                }
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return table;
    }

    public static String makeNicePhone(String phone){
        String result ="";
        if (phone.trim().length()>12||phone.trim().length()<10) {
          return "";

        }
        if (phone.trim().length()==11&&phone.trim().substring(0,1).equals("7")){
            return "8"+phone.trim().substring(1);
        }
        if (phone.trim().length()==11&&!phone.trim().substring(0,1).equals("8")){
            return "";
        }
        if (phone.trim().length()==12&&phone.trim().indexOf("+")!=0){
            return "";
        }
        if (phone.trim().length()==10&&phone.trim().indexOf("9")!=0){
            return "";
        }
        if (phone.trim().length()==12){
            return makeNicePhone(phone.trim().substring(1));
        }
        if (phone.trim().length()==10) {
            return makeNicePhone("8"+phone.trim());
        }
        if (phone.trim().length()==11&&phone.trim().substring(0,1).equals("8")){
            return phone.trim();
        }

        return result;
    }

    public static String makeNiceCard(String card){
        String result="";
        result = card.replace("o","0");
        result = card.replace("O","0");
        if (!isNumeric(result)) {
            return  "";
        }
        return result;

    }
    public static boolean isNumeric(String strNum) {
        return strNum.matches("-?\\d+(\\.\\d+)?");
    }

}
