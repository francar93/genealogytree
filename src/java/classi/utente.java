/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classi;

import Tree.genetree;
import Tree.treenode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import utilita.DataUtil;
import utilita.Database;
import java.util.Objects;

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
                ResultSet record = Database.selectRecord("user", "id = '" + user_id + "'");
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
        * @param sesso
        */
    public utente getGenitore(String sesso) throws SQLException{
           //String genitore;
           // Se bisogna restituire la madre
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

            case "madre":       effettivo = this.getGenitore("femmina");    break;    
            case "padre":       effettivo = this.getGenitore("maschio");      break;
            
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
        public listautenti getFratelliSorelle() throws SQLException {

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
        Database.updateRecord("user", data, "id = '" + this.id + "'");
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
        
        /**
     * Imposta le variabili di sessione necessarie
     * @param session
     */
    public void initSession2(HttpSession session){
        utente user_refresh = utente.getUserById(this.id);
        // Inserisci l'utente corrente nella variabile di sessione
        session.setAttribute("user_logged", user_refresh);
        // Inizializza la breadcrumb
        //session.setAttribute("breadcrumb", new NodeList());
        
        try {
            // Appena un utente fa il login non ha bisogno di fare il refresh dell'albero nella cache
            this.updateAttribute("refresh", 0);
        } catch (SQLException ex) { 
            
        }
        
        try {
            session.setAttribute("family_tree", user_refresh.getFamilyTree());
        } catch (SQLException ex) {
            session.setAttribute("family_tree", null);
        }
    }
    
    public boolean checkFamilyTreeCache(HttpSession session){
            try {
                ResultSet record = Database.selectRecord("user", "id='" + this.id + "'");
                if(record.next()){
                    if(record.getInt("refresh") != 0){
                        this.initSession(session);
                        return true;
                    }
                }
            } catch (SQLException ex) { }
            
            return false;
        }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        final utente other = (utente) obj;
        
        return Objects.equals(this.id, other.id);
    }
        
        
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Gestione albero genealogico">

        /**
         * Verifica se l'utente è parente ad un altro
         * @param user  parente da cercare
         * @return      true se l'user è tra i parenti, false altrimenti
         * @throws java.sql.SQLException
         */
        public boolean isRelative(utente user) throws SQLException {

            listautenti family_tree_final = new listautenti();
            // Aggiungi l'utente corrente al proprio albero genealogico
            family_tree_final.add(this);

            int number_relatives;

            do{

                // Inizializza albero temporaneo
                listautenti family_tree_temp = new listautenti();
                // Calcola numero di parenti inseriti
                number_relatives = family_tree_final.size();
                // Per ogni parente già inserito nell'albero
                for(utente relative: family_tree_final){
                    // Aggiungi all'abero temporaneo gli antenati del parente
                    listautenti ancestors = relative.getAntenati();
                    // Se l'utente da trovare è tra gli antenati, ritorna true
                    if(ancestors.contains(user)) return true;

                    // Se l'utente non ha antenati, inserisci l'utente stesso tra gli antenati, cosi da poter cercare i suoi discendenti
                    if(ancestors.isEmpty()) ancestors.add(relative);
                    // Aggiungi gli antenati all'albero genealogico
                    family_tree_temp.addAll(ancestors);

                    // Per ogni antenato trovato
                    for(utente ancestor: ancestors){
                        // Recupera i discendenti 
                        listautenti offsprings = ancestor.getDiscendenti();
                        // Se l'utente da trovare è tra i discententi, ritorna true
                        if(offsprings.contains(user)) return true;
                        // Aggiungi all'albero temporaneo tutti i suoi discendenti (quindi anche i discendenti dell'parente stesso)
                        family_tree_temp.addAll(offsprings);
                    }
                    
                    utente spouse = relative.getPartner();
                    if(spouse != null){
                        // Se l'utente da trovare è il coniuge
                        if(spouse.equals(user)) return true;
                        // Aggiungi all'albero temporaneo il coniuge del parente
                        family_tree_temp.add(spouse);
                    }

                }

                // Aggiungi l'albero temporaneo a quello finale
                family_tree_final.addAll(family_tree_temp);

            // Cicla fino a quando non sono stati aggiungi nuovi utente nell'albero finale
            }while(number_relatives != family_tree_final.size());

            return false;
        }
        
        
      
        
        
        /**
         * Recupera i componenti dell'alabero genealogico dell'utente con etichette
         * @return  lista di nodi dell'albero, a cui ad ogni utente è associata un'etichetta 
         *              che corrisponde al tipo di parentela rispetto all'utente corrente
         * @throws java.sql.SQLException
         */
        public genetree getFamilyTree() throws SQLException{
                
            genetree tree = new genetree(this);
            tree.getFamilyTree();
            return tree;
        }
        /**
         * Recupera i componenti del nucleo familiare
         * @return  lista digli utenti che compongono il nucleo familiare dell'utente
         * @throws java.sql.SQLException
         */
        public listautenti getFamilyCore() throws SQLException{
          
            listautenti family_core = new listautenti();
            // Aggiungi i genitori
            family_core.addAll(this.getGenitori());
            // Aggiungi i figli
            family_core.addAll(this.getFigli());
            // Aggiungi i fratelli
            family_core.addAll(this.getFratelliSorelle());
            // Aggiungi il coniuge
            family_core.add(this.getByParentela("spouse"));
            return family_core;
        }
    
    //</editor-fold>
       
        //<editor-fold defaultstate="collapsed" desc="Gestione albero genealogico nella cache">
        
        /**
        *   PROBLEMA: durante la sessione di un utente, è possibile che venga aggiunto/rimosso qualche parente nel suo albero
        *       In questo caso, l'albero presente nella cache dell'utente loggato risulterebbe non aggiornata
        *       Per cui quando si aggiunge/rimuove un utente bisogna segnalare a tutti gli utenti nell'albero loggati 
        *           in quel momento, di fare il refresh di quest'ultimo cosi da avere i PARENTI e le relative LABEL aggiornate.
        *       Per segnalare a un utente che il proprio albero è da aggiornare, è stato aggiunto l'attributo "refresh" nel db
        *           che, se posto a 1, indica che l'albero è da aggiornare. Il controllo su questo attributo deve essere fatto 
        *           in ogni pagina in cui è necessario avere un albero aggiornato, ovvero la pagina del profilo
        *           e la pagina di ricerca
        *       Appena un utente effettua il login, pone a 0 l'attributo "refresh" in quanto non ha bisogno di 
        *           aggiornare l'albero perchè è stato appena generato. 
        * 
        *       NOTA: la segnalazione sopra descritta, deve essere fatta DOPO l'aggiunta di un utente e PRIMA di una rimozione
        *       
        *       NOTA 2: Prima e dopo l'aggiornamento dell'albero è probabile che gli utenti presenti nell'albero non siano cambiati. 
        *               Ciò avviene quando:
        *                   1. un parente aggiunto da un altro già era presente nell'albero di quest'ultimo;
        *                   2. un parente rimosso da un altro continua a essere presente nel suo albero
        *               Alla luce di ciò, è comunque necessario aggiornare l'albero degli utenti loggati in quando è probabile
        *                   che delle label abbiano subito delle modifiche
        */      
        
        /**
         * Manda una segnalazione a tutti i parenti loggati dell'utente corrente, per aggiornare l'albero genealogico presente in cache
         * @throws SQLException
         */
        public void sendRefreshAck() throws SQLException{
            // Recupero i parenti dell'utente corrente
            genetree family_tree = this.getFamilyTree();

            Map<String, Object> data = new HashMap<>();
            data.put("refresh", 1);

            // Generazione della condizione: bisogna aggiornare l'albero genealogico di ogni parente
            String condition = "";
            for(treenode user: family_tree.getFamily_tree()){
                condition = condition + "id = '" + user.getUser().getId() + "' OR ";
            }
            condition = condition.substring(0, condition.length()-4);
            // Aggoirna il numero di parenti
            //Database.updateRecord("user", data, condition);

        }
       
    //</editor-fold>
} 


