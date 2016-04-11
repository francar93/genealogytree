/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilita;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author moira
 */
public class Messaggi {
    private String code;
    private Boolean error;
    private static Map<String,String> map;
    private String msg;
    
    public String getMsg(){
        return this.msg;
    }
    
    public String getCode(){
        return this.code;
    }
    
    public Boolean getError(){
        return this.error;
    }
    
    public Messaggi(String code,boolean error){
        this.code=code;
        this.error=error;
        this.msg=map.get(this.code);   
    }
    
    public String stringa(){
        String error_string = this.error ? "true": "false";       
        return "{\"message\":\"" + this.msg + "\", \"error\":\"" + error_string + "\"}";
    }
   
    static{
        
        map= new HashMap<>();
        //errore generico
        map.put("error", "Si è rilevato un errore");
        //errore User
        map.put("user_alr", "Utente già esistente");
        map.put("user2", "Utente non esistente");
        map.put("user3", "Utente non trovato");
        map.put("user4", "Utente loggato");
        //errore Nome
        map.put("nome1", "Il nome deve essere alfanumerico");
        map.put("nome2", "Il nome è corto");
        map.put("nome3", "Il nome è lungo");
        //errore Cognome
        map.put("nome1", "Il nome deve essere alfanumerico");
        map.put("nome2", "Il nome è corto");
        map.put("nome3", "Il nome è lungo");
        //errore Data di Nascita
        map.put("data1", "Formato non valido");
        map.put("data2", "Data non valida");
        map.put("data3", "Data modificata");
        //errore Città
        map.put("città1", "La città deve essere alfanumerica");
        //errore Sesso
        map.put("sesso", "Puoi scegliere solo maschio o femmina");
        //errore Email
        map.put("email1", "L'e-mail corrente non è valida");
        map.put("email2", "L'e-mail confermata non è valida");
        map.put("email_mod", "L'e-mail è stata modificata");
        //errore Password
        map.put("pass1" , "Password non corretta");
        map.put("pass2", "Password corrente non valida");
        map.put("pass3", "Password confermata non valida");
        map.put("pass4", "La password deve contenere almeno 6 caratteri");
        map.put("pass5", "La password deve contenere caratteri alfanumerici");
        map.put("pass_mod", "Password modificata");
        //errore idMadre
        map.put("error_IdMadre1", "Hai già inserito tua madre nel tuo albero");
        map.put("error_IdMadre_alr", "Questo utente è già tua madre");
        map.put("error_IdMadreF", "Tua madre non può essere tuo fratello");
        map.put("error_IdMadreS", "Tua madre non può essere tua sorella");
        map.put("error_IdMadreNo", "Tua madre non può essere tuo nonno");
        map.put("error_IdMadreNa", "Tua madre non può essere tua nonna");
        // errore idPadre
        map.put("error_IdPadre1", "Hai già inserito tuo padre nel tuo albero");
        map.put("error_IdPadre_alr", "Questo utente è già tuo padre");
        map.put("error_IdPadreM", "Tuo padre non può essere tua madre");
        map.put("error_IdPadreF", "Tuo padre non può essere tuo fratello");
        map.put("error_IdPadreS", "Tuo padre non può essere tua sorella");
        map.put("error_IdPadreNo", "Tuo padre non può essere tuo nonno");
        map.put("error_IdPadreNa","Tuo padre non può essere tua nonna");
        // errore Figlio
        map.put("error_FiglioM", "Non hai ancora inserito la madre");
        map.put("error_FiglioP", "Non ha un padre");
        map.put("error_Figlio_alr", "Questo utente è già tuo figlio");
        map.put("error_FiglioF", "Tuo figlio non può essere tuo fratello");
        map.put("error_FiglioS", "Tuo figlio non può essere tua sorella");
        map.put("error_FiglioNo", "Tuo figlio non può essere tuo nonno");
        map.put("error_FiglioNa", "Tuo figlio non può essere tua nonna");
        //errore Fratello
        map.put("error_FratelloM", "Non hai inserito tua madre");
        map.put("error_FratelloP", "Non hai inserito tuo padre");
        map.put("error_Fratello_alr", "E' già tuo fratello");
        map.put("error_FratelloNo", "Tuo fratello non può essere tuo nonno");
        map.put("error_FratelloNa", "Tuo fratello non può essere tua nonna");
        map.put("error_FratelloS", "Tuo fratello non può essere tua sorella");
        map.put("error_FratelloC", "Tuo fratello non può essere tuo figlio");
         //errore Sorella   
        map.put("error_SorellaM", "Non hai inserito tua madre");
        map.put("error_SorellaP", "Non hai inserito tuo padre");
        map.put("error_Sorella_alr", "E' già tua sorella");
        map.put("error_SorellaNo", "Tua sorella non può essere tuo nonno");
        map.put("error_SorellaNa", "Tua sorella non può essere tua nonna");
        map.put("error_SorellaS", "Tua sorella non può essere tuo fratello");
        map.put("error_SorellaC", "Tua sorella non può essere tuo figlio");
        
              
        
        
    }
    
}
