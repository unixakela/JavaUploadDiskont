package sample.model;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HelperLexForUpload {

       public static String addCharLeft(String str,String than,int howMany){
            String resault ="";
            for (int i = 0; i < howMany-str.length() ; i++) {
                resault = resault + than;
            }
            resault = resault + str;
            return resault;
        }

        public static String convert(String value, String fromEncoding, String toEncoding){

            try {
                return new String(value.getBytes(fromEncoding), toEncoding);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "";

        }

        public static String charset(String value, String charsets[]){
            String probe = StandardCharsets.UTF_8.name();
            for(String c : charsets) {
                Charset charset = Charset.forName(c);
                if(charset != null) {
                    if(value.equals(convert(convert(value, charset.name(), probe), probe, charset.name()))) {
                        return c;
                    }
                }
            }
            return StandardCharsets.UTF_8.name();
        }

        //public  static String retur
    }


