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
import classi.listautenti;

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
    
    
        //<editor-fold defaultstate="collapsed" desc="metodi get">    
    
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
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="metodi set">
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
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="metodi per il reperimento dell'utenza"> 
     /**
     * Recupera un utente attraverso il suo id
     * @param user_id   email utente
     * @return          
     */
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
    }
        
    /**
     * Recupera un utente attraverso la sua e-mail
     * @param user_email   email utente
     * @return          
     */
    public static utente getUserByEmail(String user_email){
        
        try {
            if(user_email != null){
                try (ResultSet record = Database.selectRecord("user", "email = '" + user_email + "'")) {
                    if(record.next()){
                        return new utente(record);
                    }
                }
            
            }
        } catch (SQLException ex) { }
        
        return null;
    }
    //</editor-fold>
    
        //<editor-fold defaultstate="collapsed" desc="metodi reperimento parentela">
    
    /*
     *qui sono state create tutte le funzioni che servono per il reperimento dei parenti
     *all'interno del db, gli elementi di ritorno delle funzioni possono essere:
     *       - singoli oggetti della classe utente.
     *       - liste di oggetti della classe utente, racchiusi nel formato listautenti
     */
    
    /**
        * Recupera il padre o la madre
        * @return
        * @throws java.sql.SQLException
        */
    private utente getGenitore(String sesso) throws SQLException{
           String genitore;
           // Se bisogna restituire il genitore femmina
           if(sesso.equals("femmina")){
               // Restituire la madre
               return utente.getUserById(this.getIdMadre());
           }else{
               // Altrimenti, restituire il padre
               return utente.getUserById(this.getIdPadre());
           }        
       }
    
        /**
        * Recupera il partner
        * @return
        * @throws java.sql.SQLException
        */
    
        private utente getPartner() throws SQLException{
        
        return  utente.getUserById(this.getIdPartner());
          
       }
    
        /**
        * Recupera il padre e la madre
        * @return
        * @throws java.sql.SQLException
        */
        public listautenti getGenitori() throws SQLException{
           listautenti genitori = new listautenti();
           // Aggiunta della madre
           genitori.add(this.getGenitore("maschio"));
           // Aggiunta del padre
           genitori.add(this.getGenitore("femmina"));
           return genitori;
       }
        
        /**
         * Recupera i figli
         * @return  lista con tutti i figli di un utente
         * @throws java.sql.SQLException
         */
        public listautenti getFigli() throws SQLException{
            listautenti figli = new listautenti();

            ResultSet record = Database.selectRecord("user", "idpadre = '" + this.id + "' OR idmadre = '" + this.id + "'");
            // Aggiungo ogni figlio trovato alla lista
            while(record.next()){    
                figli.add(new utente(record));
            }
            record.close();

            return figli;    
        }
        
        /**
        * Recupera un utente
        * @param parentela      grado di parentela
        * @return
        * @throws SQLException
        */
        public utente getByParentela(String parentela) throws SQLException{
        utente effettivo = null;
        
        switch(parentela){

            case "madre":       effettivo = this.getGenitore("maschio");    break;    
            case "padre":       effettivo = this.getGenitore("femmina");      break;
            
            case "compagno":
            case "marito": 
            case "moglie":      effettivo = this.getPartner();            break;
                
        }
        
        return effettivo;
        
    }
        
        /**
         * Recupera fratelli e sorelle di sangue
         * @return
         * @throws java.sql.SQLException
         */
        public listautenti getFratelloSorella() throws SQLException {

            listautenti fratellanza = new listautenti();
            
            utente papa = this.getByParentela("padre");
            utente mamma = this.getByParentela("madre");

            // Se l'utente ha entrambi i genitori
            if(papa != null && mamma != null) {
                // Recupera figli del padre
                listautenti figli_papa = papa.getFigli();
                // Recupera figli della madre
                listautenti figli_mamma = mamma.getFigli();
                // Per ogni figlio del padre
                for (utente figlio_papa : figli_papa) {
                    // Se è anche figlio della madre ed è diverso dall'utente corrente
                    if(figli_mamma.contains(figlio_papa) && !figlio_papa.equals(this)){
                        // Aggiungilo tra i fratelli di sangue
                        fratellanza.add(figlio_papa);
                    }
                }
            } 
                  
            // Ritorna figli recuperati
            return fratellanza;
        }
        
        
        /**
         * Recupera gli antenati
         * @return
         * @throws java.sql.SQLException
         */
        public listautenti getAntenati() throws SQLException{
            listautenti antenati = new listautenti();
            listautenti genitori = this.getGenitori();

            // Per ogni genitore
            for (utente genitore : genitori) { 
                // Aggiungilo alla lista
                antenati.add(genitore);
                // Ricerca ricorsivamente i genitori alla lista
                antenati.addAll(genitore.getAntenati());
            }

            return antenati;
        }
        
        
        /**
         * Recupera antenati con filtro sul sesso
         * @param sesso    Sesso degli antenati
         * @return
         * @throws java.sql.SQLException
         */
        public listautenti getAntenati(String sesso) throws SQLException{
            listautenti antenati = new listautenti();
            listautenti genitori = this.getGenitori();
            // Per ogni genitore
            for (utente genitore : genitori) { 
                if(genitore.getSesso().equals(sesso)){
                    // Aggiungilo alla lista
                    antenati.add(genitore);
                }

                // Ricerca ricorsivamente i genitori alla lista
                antenati.addAll(genitore.getAntenati(sesso));
            }
            return antenati;
        }
        
        /**
         * Recupera i discendenti
         * @return
         * @throws java.sql.SQLException
         */
        private listautenti getDiscendenti() throws SQLException {
            listautenti discendenti = new listautenti();
            listautenti figli = this.getFigli();
            // Per ogni figlio
            for (utente figlio : figli) {  
                // Aggiungilo alla lista
                discendenti.add(figlio);
                // Aggiungi ricrosivamente i figli alla lista
                discendenti.addAll(figlio.getDiscendenti());
            }
            return discendenti;
        }
        
        
        /**
         * Recupero dei discendenti con filtro sul sesso
         * @param sesso    Sesso dei discendenti
         * @return
         * @throws java.sql.SQLException
         */
        public listautenti getDiscendenti(String sesso) throws SQLException {
            listautenti discendenti = new listautenti();
            listautenti figli = this.getFigli();
            // Per ogni figlio
            for (utente figlio : figli) {  
                if(figlio.getSesso().equals(sesso)){
                    // Aggiungilo alla lista
                    discendenti.add(figlio);
                }
                // Aggiungi ricrosivamente i figli alla lista
                discendenti.addAll(figlio.getDiscendenti(sesso));
            }
            return discendenti;
        }
        
        
        
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="metodi vari">
        private void updateAttribute(String attribute, Object value) throws SQLException{
        Map<String, Object> data = new HashMap();
        data.put(attribute, value);
        Database.updateRecord("utente", data, "id = '" + this.id + "'");
    }
        
        public void initSession(HttpSession session){
        // Inserisci l'utente corrente nella variabile di sessione
        session.setAttribute("utente_loggato", this.id);
    }
        
        
        public static String createUniqueUserId(int length){
        String user_id;
        do{
            user_id = DataUtil.generateCode(length);
        // Cicla fino a quando non esiste un utente con id uguale a quello appena generato
        }while(utente.getUserById(user_id) != null);
        
        return user_id;
        
    } 
        //</editor-fold>

} 


