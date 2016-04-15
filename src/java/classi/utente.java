/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import utilita.DataUtil;
import utilita.Database;

/**
 *
 * @author matteocapodicasa
 */
public class utente {

    
    
    public String nome;
    public String cognome;
    public Date dataNascita;
    private final String id;
    public String citta;
    public String sesso;
    public String email;
    public String password;
    public String info;
    public String idPadre;
    public String idMadre;
    public String idPartner;
    
     /**
     * Metodo costruttore
     * @param utente      contiene i dati personali dell'utente
     * @throws java.sql.SQLException
     */
    public utente(ResultSet utente) throws SQLException{

        this.id             = utente.getString("id");
        this.nome           = utente.getString("nome");
        this.cognome        = utente.getString("cognome");
        this.email          = utente.getString("email");
        this.password       = utente.getString("password");
        this.sesso          = utente.getString("sesso");
        this.dataNascita    = utente.getDate("datanascita");
        this.citta          = utente.getString("citta");
        this.info           = utente.getString("info");
        this.idMadre        = utente.getString("idmadre");
        this.idPadre        = utente.getString("idpadre");
        this.idPartner      = utente.getString("idpartner");
    }
    
    public utente(String id, String nome, String cognome, String email, String password, String sesso, Date dataNascita, String citta, String info ){

        this.id             = id;
        this.nome           = nome;
        this.cognome        = cognome;
        this.email          = email;
        this.password       = password;
        this.sesso          = sesso;
        this.dataNascita    = dataNascita;
        this.citta          = citta;
        this.info           = info;
        this.idMadre        = null;
        this.idPadre        = null;
        this.idPartner      = null;
    }
    
    
//Metodi Get    
    
        public String getId(){
            return this.id;
        }

        public String getNome(){
            return this.nome;
        }

        public String getCognome(){
            return this.cognome;
        }

        public String getEmail(){
            return this.email;
        }
        
        public String getPassword() throws SQLException{

            ResultSet record = Database.selectRecord("utente", "id = '" + this.id + "'");
            if(record.next()){
                return record.getString("password"); 
            }
            return null;
        }
        
        public String getSesso(){
            return this.sesso;
        }
        
        public Date getDataNascita(){
            return this.dataNascita;
        }


        public String getCitta(){
            return this.citta;
        }


        public String getInfo() {
            return this.info;
        }

        public String getIdMadre() throws SQLException {
            return this.idMadre;
        }

        public String getIdPadre() throws SQLException {
            return this.idPadre;
        }

        public String getIdPartner() throws SQLException {
            return this.idPartner;
        }
        
//Metodi Set
        public void setDati(String nome, String cognome, String sesso, String dataNascita, String citta, String info) throws SQLException, ParseException{
           
            Map<String, Object> data = new HashMap<>();
            data.put("nome", nome);
            data.put("cognome", cognome);
            data.put("sesso", sesso);
            java.sql.Date sqlDate = DataUtil.stringToDate(dataNascita, "dd/MM/yyyy");
            data.put("datanascita", DataUtil.dateToString(sqlDate));
            data.put("citta", citta);
            data.put("info", info);
            /*
            boolean remove_tree = false;
            String old_gender = this.gender;
            if(!gender.equals(old_gender)){
                remove_tree = true;
            }
            */
            
            Database.updateRecord("user", data, "id = '" + this.getId() + "'");
            /*
            if(remove_tree){
                // Rimuovi i filgi
                UserList children = this.getChildren();
                for(User child: children){
                    child.removeParent(old_gender);
                }
                // Rimuovi il padre
                this.removeParent("male");
                // Rimuovi la madre
                this.removeParent("female");
                // Rimuovi il coniuge
                this.removeSpouse();
                
                this.sendRefreshAck();
            }
            */
            
            this.nome = nome;
            this.cognome = cognome;
            this.sesso = sesso;
            this.dataNascita = DataUtil.stringToDate(dataNascita, "dd/MM/yyyy");
            this.citta = citta;
            this.info = info;
            

        }

        public void setEmail(String email) throws SQLException {
            this.updateAttribute("email", email);
            this.email = email;
        }

        public void setPassword(String password) throws SQLException {
            this.updateAttribute("password", DataUtil.crypt(password));
        }
        
        private void updateAttribute(String attribute, Object value) throws SQLException{
        Map<String, Object> data = new HashMap();
        data.put(attribute, value);
        Database.updateRecord("utente", data, "id = '" + this.id + "'");
    }
        
        public static utente getUserById(String user_id){
        utente user = null;
        try {
            if(user_id != null){
                ResultSet record = Database.selectRecord("utente", "id = '" + user_id + "'");
                if(record.next()){
                    user =  new utente(record);
                }
            }
        } catch (SQLException ex) {}
        
        return user;
    } //FC
        
        public static String createUniqueUserId(int length){
        String user_id;
        do{
            user_id = DataUtil.generateCode(length);
        // Cicla fino a quando non esiste un utente con id uguale a quello appena generato
        }while(utente.getUserById(user_id) != null);
        
        return user_id;
        
    } //FC
        /**
     * Recupera un utente attraverso la sua e-mail
     * @param user_email   email utente
     * @return          
     */
    public static utente getUserByEmail(String user_email){
        
        try {
            if(user_email != null){
                try (ResultSet record = Database.selectRecord("utente", "email = '" + user_email + "'")) {
                    if(record.next()){
                        return new utente(record);
                    }
                }
            
            }
        } catch (SQLException ex) { }
        
        return null;
    }
    
    
    public void initSession(HttpSession session){
       
        utente id_utente = utente.getUserById(this.id);
        
        // Inserisci l'utente corrente nella variabile di sessione
        session.setAttribute("utente_loggato", id_utente);
    }

} 


