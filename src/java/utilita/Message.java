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
public class Message {
    private final String code;
    private final Boolean error;
    private final static Map<String,String> map;
    private final String msg;
     
    public String getMsg(){
        return this.msg;
    }
    
    public String getCode(){
        return this.code;
    }
    
    public Boolean isError(){
        return this.error;
    }
    
    public Message(String code,boolean error){
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
        map.put("err", "Si è rilevato un errore");
        //errore User
        
        map.put("usr_0", "Inserisci tutti i campi");
        map.put("usr_1", "Utente già esistente");
        map.put("usr_2", "Utente non esistente");
        map.put("usr_3", "Utente non trovato");
        map.put("usr_4", "Utente loggato");
        //errore Nome
        map.put("name_1", "Il nome deve essere alfanumerico");
        map.put("name_2", "Il nome è corto");
        map.put("name_3", "Il nome è lungo");
        //errore Cognome
        map.put("surname_1", "Il nome deve essere alfanumerico");
        map.put("surname_2", "Il nome è corto");
        map.put("surname_3", "Il nome è lungo");
        //errore Data di Nascita
        map.put("date_1", "Formato non valido");
        map.put("date_2", "Data non valida");
        map.put("dt_ok", "Data modificata");
        //errore Città
        map.put("plc", "La città deve essere alfanumerica");
        //errore Sesso
        map.put("gnd", "Puoi scegliere solo maschio o femmina");
        //errore Email
        map.put("eml_1", "L'e-mail corrente non è valida");
        map.put("eml_2", "L'e-mail confermata non è valida");
        map.put("eml_3", "L'e-mail non è valida");
        map.put("eml_ok", "L'e-mail è stata modificata");
        //errore Password
        map.put("psw", "Password errata");
        map.put("psd_1", "La password non è valida");
        map.put("psd_2", "La password confermata non è valida");
        map.put("psd_3", "La password deve contenere almeno 6 caratteri");
        map.put("psd_4", "La password deve essere alfanumerica");
        map.put("psd_ok", "La password è stata modificata");
        map.put("psd_5","inserisci la password");
        /* FOTO */
        map.put("pho_slt", "Please, select a photo");
        map.put("pho_ok", "Photo Uploaded Successfully");
        map.put("pho_err", "Photo Uploaded Failed");
        /* REQUEST */
        map.put("snd", "Request sent");
        map.put("acc", "Request accepted");
        map.put("dec", "Request declined");
        /* OTHER */
        map.put("srv", "An error occurred, please retry");
        map.put("tmp", "Tampered data");
        map.put("alp", "Please, insert alphanumeric characters only");
        map.put("log", "Please log in to see this page");
        map.put("fld", "All fields are required");
        map.put("inv", "User invited");
        map.put("basic_add", "Relative added to your tree");
        /* NotAllowedException */
        map.put("yourself",     "Not allowed: you can't add yourself as relative");
        /* SPOUSE */
        map.put("sp_alr",       "Not allowed: you already have a spouse");
        map.put("sp_gen",       "Not allowed: you can't have the same gender of your spouse");
        map.put("sp_your",      "Not allowed: this user already is your spouse");
        map.put("sp_sib",       "Not allowed: your spouse can't be your sibling");
        map.put("sp_anc",       "Not allowed: your spouse can't be your anchestor");
        map.put("sp_off",       "Not allowed: your spouse can't be your offspring");
        /* FATHER */
        map.put("sp_alr",       "Not allowed: you already have a spouse");
        map.put("sp_gen",       "Not allowed: you can't have the same gender of your spouse");
        map.put("sp_your",      "Not allowed: this user already is your spouse");
        map.put("sp_sib",       "Not allowed: your spouse can't be your sibling");
        map.put("sp_anc",       "Not allowed: your spouse can't be your anchestor");
        map.put("sp_off",       "Not allowed: your spouse can't be your offspring");
        /* MOTHER */
        map.put("mot_alr",      "Not allowed: you already have a mother");
        map.put("mot_your",     "Not allowed: this user already is your mother");
        map.put("mot_sib",      "Not allowed: your mother can't be your sibling");
        map.put("mot_anc",      "Not allowed: your mother can't be your anchestor");
        map.put("mot_off",      "Not allowed: your mother can't be your offspring");
        /* FATHER */
        map.put("fat_alr",      "Not allowed: you already have a father");
        map.put("fat_your",     "Not allowed: this user already is your father");
        map.put("fat_sib",      "Not allowed: your father can't be your sibling");
        map.put("fat_anc",      "Not allowed: your father can't be your anchestor");
        map.put("fat_off",      "Not allowed: your father can't be your offspring");
        /* CHILD */
        map.put("ch_mot_alr",   "Not allowed: the user already have a mother");
        map.put("ch_mot_your",  "Not allowed: this user already is your child");
        map.put("ch_mot_sib",   "Not allowed: your child can't be your sibling");
        map.put("ch_mot_anc",   "Not allowed: your child can't be your offsping");
        map.put("ch_mot_off",   "Not allowed: your child can't be your anchestor");
        map.put("ch_fat_alr",   "Not allowed: the user already have a father");
        map.put("ch_fat_your",  "Not allowed: this user already is your child");
        map.put("ch_fat_sib",   "Not allowed: your child can't be your sibling");
        map.put("ch_fat_anc",   "Not allowed: your child can't be your offspring");
        map.put("ch_fat_off",   "Not allowed: your child can't be your anchestor");
        /* SIBLING */
        map.put("sib_your",     "Not allowed: this user already is your sibling");
        map.put("sib_1",        "Not allowed: this user can't be add as sibling, because this relationship is not verificable");
        map.put("sib_2",        "Not allowed: this user can't be your sibling, because you have different parents");
        map.put("sib_3",        "Not allowed: this user can't be your sibling, beacause you can't have the same parents");
    
                }
    
        
        
    
}
