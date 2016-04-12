/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classi;

import static java.lang.System.out;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.validator.EmailValidator;
import servlet.signup;
import utilita.DataUtil;
import utilita.Database;
import static utilita.Database.emailIn;
import utilita.Message;

/**
 *
 * @author carus
 */
public class controlli {
    
    
    public static boolean campivuoti (String email, String password, String nome, String cognome, String sesso, String data_nascita, String citta ){
        
        if(email.equals("") || password.equals("") || nome.equals("") || cognome.equals("") || sesso == null || data_nascita.equals("")  || citta.equals("")){
            return true;
            }else{
            return false;
    }
    }
 
        
            
    public static boolean emailvalida(String email) throws SQLException{
         if((EmailValidator.getInstance().isValid(email))){
            return true;            // Se l'email non è valida
        }else{            
            return false;
        }
    }
    
    public static boolean emaildb(String email) throws SQLException{
        return (emailIn(email));
    }
    
    
    
    public static void controllinome (String nome){
        if(nome.length() < 2 ){
            // nome lungo                  
            
        }else if(nome.length() > 50 ){
            // nome corto
            
        }
        }

    public static void checkpassword(String password){
    
        boolean error = true;
        
            // Se la password è lunga meno di sei caratteri
        if(password.length() <6){
            // The password must be 6 characters at least
        
            // Se la password non è alfanumerica
        }else if(!DataUtil.isAlphanumeric(password, false)){
             // The password must be alphanumeric
          
        }else{
            error = false;
        }

        
    }
    public static void checksesso (String sesso){
    
        
        boolean error = false;

        //Controllo su maschio e femmina
        
        if(!sesso.equals("male") && !sesso.equals("female") ){
        // solo un sesso 
            error = true;
        }
        
        
    }
    public static void checkcitta(String citta){
    
        
        boolean error = false;

        //Check solo caratteri
        if(!(DataUtil.isAlphanumeric(citta, true))){
        // The birthplace must be alphanumeric
            error = true;
        }

    }
    
    public static void checkdatanascita(String data_nascita){
        
        
        boolean error = true;
        
        if(!DataUtil.validateDateFormat(data_nascita)){
            // The date isn't in the right format
        }else{
            // Converti la data da String a Date
            Date date;
            try {
                date = DataUtil.stringToDate(data_nascita, "dd/MM/yyyy");
                if(DataUtil.validateDateRange(date)){
                    error = false;
                }
            } catch (ParseException ex) {
                 // The date in not valid
            }
            // Se la data non rientra nel range valido
            
        }
    }
    
    
    
    
     public static Message checkData(String name, String surname, String gender, String birthdate, String birthplace){
       
            Message check = new Message("dt_ok", false);
            
            if(name.equals("") || surname.equals("") || gender == null || birthdate.equals("")  || birthplace.equals("")){
                check = new Message("fld", true); // All fields required

                // Se la data di nascita non è valida
                }else {

                    // Controllo del nome
                    check = checkName(name, "name");
                    if(!check.isError()) {

                        // Controllo del cognome
                        check = checkName(surname, "surname");
                        if(!check.isError()) {

                            // Controllo del sesso
                            check = checkGender(gender);
                            if(!check.isError()) {

                                // Controllo della città di nascita
                                check = checkBirthplace(birthplace);
                                if(!check.isError()) {

                                    // Controllo della data di nascita
                                    check = checkBirthdate(birthdate);
            }}}}}
            
            return check; 
            
    }
    public static Message checkEmail(String email){
        
        String msg = null;
        boolean error = true;

        // Se l'email non è valida
        if(!(EmailValidator.getInstance().isValid(email))){
            msg = "eml_3"; // Email is not valid
            
        // Se l'utente è già registrato
        }else{
            utente user = utente.getUserByEmail(email);
            if(user != null){
                try {
                    if(user.getPassword() != null){
                        msg = "usr_2"; // User already exist
                    }else{
                        Database.deleteRecord("user", "id = '" + user.getId() + "'");
                        Database.deleteRecord("request", "user_id = '" + user.getId() + "' OR relative_id = '" + user.getId() + "'");
       
                        error = false;
                    }
                } catch (SQLException ex) {
                    msg = "srv";
                }
            }else{
                error = false;
            }
        }
        return new Message(msg, error);

    } 

    public static Message checkPassword(String toCheck){
    
        String msg = null;
        boolean error = true;
        
        // Se la password è lunga meno di sei caratteri
        if(toCheck.length() <6){
            msg = "psd_3"; // The password must be 6 characters at least
        
        // Se la password non è alfanumerica
        }else if(!DataUtil.isAlphanumeric(toCheck, false)){
            msg = "psd_4"; // The password must be alphanumeric
          
        }else{
            error = false;
        }

        return new Message(msg, error);
    }
    
    public static Message checkName(String toCheck, String field){

        String msg = null;
        boolean error = true;

        //Check alphanumeric
        if(!DataUtil.isAlphanumeric(toCheck,true)){
            msg = field + "_1"; // The <field> must be alphanumeric

        //Check lunghezza anomala
        //Nome 'anomalo': meno di 2 caratteri, piu di 50
        }else if(toCheck.length() < 2 ){
            msg = field + "_2"; // The <field> is too short

        }else if(toCheck.length() > 50 ){
            msg = field + "_3"; // The <field> is too long
        }else{
            error = false;
        }
        
        return new Message(msg, error);

    }
        
    public static Message checkGender(String toCheck){
    
        String msg = null;
        boolean error = false;

        //Controllo su 'male' oppure 'female'
        if(!toCheck.equals("male") && !toCheck.equals("female") ){
            msg = "gnd"; // You can be only male or female
            error = true;
        }
        
        return new Message(msg, error);
      
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