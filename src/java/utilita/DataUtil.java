package utilita;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import org.apache.commons.validator.DateValidator;


/**
 *
 * @author caruso
 */
public class DataUtil {
    
    
    /**
     * Controllo su String. Contiene solo caratteri alfanumerici?
     * @param toCheck   stringa sul quale effettuare il controllo
     * @param space     se true accetta anche gli spazi.
     * @return          true se la stringa è alfanumerica, false altrimenti.
     */
    public static boolean isAlphanumeric(String toCheck, boolean space){
        if(toCheck.equals("")) return true;
        
        if(space){
            return toCheck.matches("[a-zA-Z' ]+");
        }else{
            return toCheck.matches("[a-zA-Z']+");
        }
        
    }
    
    /**
     * Eliminazione degli spazi esterni e dei doppi spazi interni
     * @param toTrim    stringa da elaborare
     * @return          stringa "pulita"
     */
    public static String spaceTrim(String toTrim){
        return toTrim.trim().replaceAll("\\s+", " ");
    }

    /**
     * Converto una data da String a Date
     * @param date      data da convertire
     * @param format    formato di date
     * @return          data nell formato yyyy-MM-dd
     * @throws java.text.ParseException
     */
    public static Date stringToDate(String date, String format) throws ParseException{
        DateFormat formatter = new SimpleDateFormat(format);
        java.util.Date myDate;

        myDate = formatter.parse(date);
        Date sqlDate = new Date(myDate.getTime());
        return sqlDate;
        
    }
    
    /**
     * Controlla il formato della data (dd/MM/yyyy)
     * @param date  data da verificare
     * @return      true se la data è nel formato corretto, false altrimenti
     */
    public static boolean validateDateFormat(String date){
        return DateValidator.getInstance().isValid(date, "dd/MM/yyyy", false);
    }
    
    /**
     * Controlla se la data rientra nel range stabilito (01/01/1900 - oggi)
     * @param date  data da verificare
     * @return      true se la data è corretta, false altrimenti
     */
    public static boolean validateDateRange(Date date){
        
        // Calcola la data corrente
        java.util.Date current_date = new java.util.Date();
        // Calcola la data minima 01/01/1900)
        Calendar cal = new GregorianCalendar(1900, 01, 01);
        java.util.Date min_date = cal.getTime();
        
        // Calcola i secondi della data da validare
        long date_second = date.getTime();
        // Calcola i secondi della data attuale
        long current_second = current_date.getTime();
        // Calcola i secondi della data minima
        long min_second = min_date.getTime();

        return !(date_second >= current_second || date_second <= min_second);
    }
    
    /**
     * Converte una data da Date a String
     * @param date  data da convertire
     * @return      stringa nel formato yyyy-MM-dd
     */
    public static String dateToString(Date date){
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
    
    /**
     * Cripta una stringa
     * @param string    stringa da criptare
     * @return          stringa criptata
     */
    public static String crypt(String string){
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            byte[] passBytes = string.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<digested.length;i++){
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }

    }
    
    /**
     * Verifica se una stringa criptata è stata generata da un'altra stringa
     * @param string_crypted    stringa criptata
     * @param to_check          stringa da verificare
     * @return                  true se la password è stata verificata, false altrimenti
     */
    public static boolean decrypt(String string_crypted, String to_check){
        if(to_check == null || string_crypted == null) return false;
        return string_crypted.equals(crypt(to_check));
    }
    
    /**
     * Genera un codice alfanumerico
     * @param length    numero di caratteri del codice
     * @return          codice generato
     */
    public static String generateCode(int length){
        // Definisci caratteri ammessi
        char[] chars = "ABCDEFGHILMNOPQRTUVZ1234567890".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) sb.append(chars[random.nextInt(chars.length)]);
        return  sb.toString();
    }
    
    public static Message checkBirthplace(String toCheck){
    
        String msg = null;
        boolean error = false;

        //Check solo caratteri
        if(!(DataUtil.isAlphanumeric(toCheck, true))){
            msg = "plc"; // The birthplace must be alphanumeric
            error = true;
        }

        return new Message(msg, error);
    }
    
    public static Message checkBirthdate(String toCheck){
        
        String msg = null;
        boolean error = true;
        
        if(!DataUtil.validateDateFormat(toCheck)){
            msg = "date_1"; // The date isn't in the right format
        }else{
            // Converti la data da String a Date
            Date date;
            try {
                date = DataUtil.stringToDate(toCheck, "dd/MM/yyyy");
                if(DataUtil.validateDateRange(date)){
                    error = false;
                }
            } catch (ParseException ex) {
                msg = "date_2"; // The date in not valid
            }
            // Se la data non rientra nel range valido
            
        }
            
        return new Message(msg, error);
    }
   
}
