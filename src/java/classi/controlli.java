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

/**
 *
 * @author carus
 */
public class controlli {
    
    
    public static void campivuoti (String email, String password, String nome, String cognome, String sesso, String data_nascita, String citta ){
        
        if(email.equals("") || password.equals("") || nome.equals("") || cognome.equals("") || sesso == null || data_nascita.equals("")  || citta.equals("")){
            // stampa messaggio errore 
            }else{
                    utente new_utente = utente.getUserByEmail(email);
                    if(new_utente != null){
                        try {
                            if(new_utente.getPassword() != null){
                                // stampa messaggio utente registro gia
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        }
                    }        
    }
            
            
    
    public static void controlloemail(String email){
        boolean error = true;
        if(!(EmailValidator.getInstance().isValid(email))){
                        // Se l'email non è valida
        }else{
            utente utente1 = utente.getUserByEmail(email);
            if(utente1 != null){
                try {
                    if(utente1.getPassword() != null){
                         // User already exist
                    }else{
                        Database.deleteRecord("user", "id = '" + utente1.getId() + "'");
                        Database.deleteRecord("request", "user_id = '" + utente1.getId() + "' OR relative_id = '" + utente1.getId() + "'");
                    error = false;
                    }
                } catch (SQLException ex) {
                    }
            }else{
                error = false;
    }
        }
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
    
    
}