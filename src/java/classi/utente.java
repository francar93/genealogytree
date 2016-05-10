/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classi;

import Tree.NodeList;
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
import utilita.NotAllowedException;

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

            ResultSet record = Database.selectRecord("user", "id = '" + this.id + "'");
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
        //aka getNumRelatives
        public int getParentele() throws SQLException {

            /* Il numero di parenti può essere modificato anche da altri utenti, per cui è necessario prelevare il valore ogni volta dal database*/
            ResultSet record = Database.selectRecord("user", "id = '" + this.id + "'");
            if(record.next()){
                return record.getInt("parentele");
            }

            return 0;
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
        
        /**
         * Aggiorna il numero di parenti collegati
         * @throws java.sql.SQLException
         */
        
        //aka setNumRelatives
        public void setParentele() throws SQLException {
            // Recupero i parenti dell'utente corrente
            NodeList family_tree = this.getFamilyTree().getFamily_tree();

            // Calcola il numero di parenti (-1 per non considerare l'utente stesso)
            int tree_size = family_tree.size() - 1;

            Map<String, Object> data = new HashMap<>();
            data.put("parentele", tree_size);

            // Generazione della condizione: bisogna aggiornare i numeri di parenti ad ogni membro dell'albero genealogico
            String condition = "";
            for(treenode user: family_tree){
                condition = condition + "id = '" + user.getUser().getId() + "' OR ";
            }
            condition = condition.substring(0, condition.length()-4);
            // Aggoirna il numero di parenti
            Database.updateRecord("user", data, condition);
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="metodi per il reperimento dell'utenza"> 
     /**
     * Recupera un utente attraverso il suo id
     * @param user_id   
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
        public boolean checkFamilyTreeCache(HttpSession session){
            try {
                ResultSet record = Database.selectRecord("user", "id='" + this.id + "'");
                if(record.next()){
                    if(record.getInt("refresh") != 0){
                        this.initSession2(session);
                        return true;
                    }
                }
            } catch (SQLException ex) { }
            
            return false;
        }
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
            Database.updateRecord("user", data, condition);

        }
       
    //</editor-fold>
      
        
        
        
        
        //<editor-fold defaultstate="collapsed" desc="metodi aggiunta parentela">
    
        /**
         * Aggiungi un parente
         * Nota:    prima di aggiungere un parente vengono effettuati prima i dovuti controlli
         * @param relative          parente da aggiunre
         * @param relationship      grado di parentela
         * @throws SQLException
         * @throws NotAllowedException
         */
        public void setRelative(utente relative, String relationship) throws SQLException, NotAllowedException{
            // Verifica se l'aggiunta può essere fatta
            this.canAddLike(relative, relationship);

            switch(relationship){

                case "parent": this.setParent(relative);     break;

                case "child": this.setChild(relative);       break;

                case "sibling": this.setSibling(relative);   break;

                case "spouse": this.setSpouse(relative);     break;

                default: throw new NotAllowedException("tmp");
            }
            
            
            this.sendRefreshAck();

        }
        /**
         * Aggiungi il padre o la madre
         * @param user  genitore da aggiungere
         * @throws java.sql.SQLException
         * @throws it.collaborative_genealogy.exception.NotAllowedException
         */
        private void setParent(utente user) throws SQLException, NotAllowedException{

            if(user.getSesso().equals("femmina")){
                this.updateAttribute("idpadre", user.getId());
            }else{
                this.updateAttribute("idmadre", user.getId());
            }

            // Aggiorna numero parenti
            //-------da aggiungere per agg il num di parenti-----this.setNumRelatives();

        }
        
        /**
         * Inserisci il coniuge
         * @param spouse
         * @throws it.collaborative_genealogy.exception.NotAllowedException
         * @throws java.sql.SQLException
         */
        private void setSpouse(utente spouse) throws NotAllowedException, SQLException{

            // Aggiungi il coniuge
            this.updateAttribute("idpartner", spouse.getId());

            // Se non è già stato fatto, cambia anche il coniuge dell'utente appena aggiunto
            
            if(spouse.getPartner() == null) {
                //spouse.setSpouse(this);
                spouse.updateAttribute("idpartner", this.getId());
            }  
            

            // Aggiorna numeri parenti
            //-------da aggiungere per agg il num di parenti-----this.setNumRelatives();

        }
        
        /**
         * Inserisci un figlio
         * @param user  figlio da inserire
         * @throws it.collaborative_genealogy.exception.NotAllowedException
         * @throws java.sql.SQLException
         */
        private void setChild(utente user) throws NotAllowedException, SQLException{
            // Imposta l'utente corrente come genitore
            user.setParent(this);
        }
        
        /**
         * Aggiungi un fratello o una sorella
         * @param sibling  utente da aggiungere
         * @throws java.sql.SQLException
         * @throws it.collaborative_genealogy.exception.NotAllowedException
         */
        private void setSibling(utente sibling) throws SQLException, NotAllowedException {

            utente u1 = this; //te stesso
            utente u2 = sibling; //chi ti ha aggiunto
            listautenti u1_parents;
            listautenti u2_parents;
            utente u1_parent = null;
            utente u2_parent = null;

            do{
                // Recupera i genitori dei due utenti
                u1_parents = u1.getGenitori();
                u2_parents = u2.getGenitori();
                // Recupera il numero di genitori dei due utenti
                int u1_size = u1_parents.size();
                int u2_size = u2_parents.size();
                // Se {u1} non ha parenti
                if(u1_size == 0){
                    for(utente parent: u2_parents){
                        u1.setParent(parent);
                    }
                }

                if(u1_size == 1 && u2_size == 2){
                    // Recupera il genitore di {u2} che non ha {u1}
                    utente other_parent;
                    if(u1.getByParentela("madre") != null){
                        other_parent = u2.getByParentela("madre");
                    }else{
                        other_parent = u2.getByParentela("padre");
                    }

                    u1.setParent(other_parent);
                }

                /* 
                    Le due condizioni vanno considerate anche con gli utenti a parti invertite, 
                    perciò si fa lo swap dei due utenti
                    Alla termine di questa operazione, i due utenti avranno gli stessi genitori
                */

                if(u1.equals(sibling)) break;

                // Swappa utenti
                u1 = sibling;
                u2 = this;

            }while(true);

        }

    //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Controlli per le aggiunte di parentela">
        
        /**
         * Verifica se un dato utente può essere aggiunto come parente
         * @param user      user da aggiungere
         * @param relationship    grado di parentela
         * @throws java.sql.SQLException
         * @throws it.collaborative_genealogy.exception.NotAllowedException
         */
        private void canAddLike(utente user, String relationship) throws SQLException, NotAllowedException {
            
            // Se l'utente prova ad aggiungere se stesso
            if(this.equals(user)) throw new NotAllowedException("yourself");
            
            switch(relationship){

                case "parent": this.canAddLikeParent(user);     break;

                case "child": this.canAddLikeChild(user);       break;

                case "sibling": this.canAddLikeSibling(user);   break;

                case "spouse": this.canAddLikeSpouse(user);     break;

                default: throw new NotAllowedException("tmp");
            }

        }
   
        //<editor-fold defaultstate="collapsed" desc="Metodi ausiliari. NON utilizzare al di fuori di canAddLike">
            /**
         * Verifica se un dato utente può essere aggiunto come genitore
         * @param user      utente da aggiungere
         * @param gender    sesso del genitore per definire se si vuole aggiungere un padre o una madre
         * @return          true se l'utente è stato aggiunto come coniuge, false altrimenti
         */
            private void canAddLikeParent(utente user) throws SQLException, NotAllowedException {
                try{
                    // Se {user} è già genitore dell'utente
                    utente parent = this.getGenitore(user.getSesso());
                    if(parent != null && parent.equals(user)) throw new NotAllowedException("your");
                    
                    // Se l'utente hà gia un genitore dello stesso sesso
                    if(this.getGenitore(user.getSesso()) != null) throw new NotAllowedException("alr");

                    String user_gender = user.getSesso();
                    // Se {user} è tra i fratelli/sorelle    
                    if(this.getFratelliSorelle().contains(user)) throw new NotAllowedException("sib");

                    // Se {user} è un discendente
                    listautenti offsprings = this.getDiscendenti(user_gender);        
                    if(offsprings.contains(user)) throw new NotAllowedException("off");
                    
                    // Se {user} è un antenato
                    listautenti ancestors = this.getAntenati(user_gender);
                    if(ancestors.contains(user)) throw new NotAllowedException("anc");

                        
                }catch(NotAllowedException ex){
                    if(user.getSesso().equals("femmina")){
                        throw new NotAllowedException("mot_" + ex.getMessage());
                    }else{
                        throw new NotAllowedException("fat_" + ex.getMessage());
                    }
                }
            }
            /**
             * Verifica se un dato utente può essere aggiunto come coniuge
             * @return  true se l'utente è stato aggiunto come coniuge, false altrimenti
             */
            private void canAddLikeSpouse(utente user) throws SQLException, NotAllowedException {

                /* 
                    Più in generale, si verifica se due utenti possono avere (o aver avuto) una relazione sentimentale di qualsiasi tipo (e di conseguenza se possono avere figli comuni)
                    Per ipotesi non sono accettate relazioni sentimentali tra:
                        1. fratelli;
                        2. utente con i suoi antenati, e di conseguenza anche con i suoi discendenti; 
                       
                */

                // Se l'utente corrente e/o {user} hanno già un coniuge
                if(this.getPartner() != null || user.getPartner() != null) throw new NotAllowedException("sp_alr");

                // Se {user} ha lo stesso sesso
                if(this.sesso.equals(user.getSesso())) throw new NotAllowedException("sp_gen");

                // Se {user} è già il coniuge
                if(this.getPartner() != null && this.getPartner().equals(user)) throw new NotAllowedException("sp_your");

                // Se {user} è tra i fratelli/sorelle
                listautenti siblings = this.getFratelliSorelle();        
                if(siblings.contains(user)) throw new NotAllowedException("sp_sib");

                // Se {user} è un antenato
                listautenti anchestors = this.getAntenati();        
                if(anchestors.contains(user)) throw new NotAllowedException("sp_anc");

                // Se {user} è un discendente
                listautenti offsprings = this.getDiscendenti();    
                if(!offsprings.contains(user)) throw new NotAllowedException("sp_off");
            }
            /**
             * Verifica se un dato utente può essere aggiunto come fratello
             * Nota:    due utenti sono fratelli se hanno entrambi i genitori in comune, 
             *          per cui due utenti possono diventare fratelli sono se entrambi posso avere gli stessi genitori
             *          e solo se dopo l'aggiunta risultano avere sia il padre che la madre
             * @return  true se l'utente è stato aggiunto con successo, false altrimenti
             */
            private void canAddLikeSibling(utente user) throws SQLException, NotAllowedException {

                // Se i due utenti sono già fratelli
                if(this.getFratelliSorelle().contains(user)) throw new NotAllowedException("sib_alt");

                utente u1 = this;
                utente u2 = user;

                // Recupera i genitori dei due utenti
                listautenti u1_parents = u1.getGenitori();
                listautenti u2_parents = u2.getGenitori();

                // Recupera il numero di genitori dei due utenti
                int u1_size = u1_parents.size();
                int u2_size = u2_parents.size();

                utente u1_parent, u2_parent;
                
                

                // Se entrambi gli utenti non hanno nessun genitore, non è possibile verificare la parentela
                if(u2_size == 0 && u1_size == 0) throw new NotAllowedException("sib_1");

                // Se i due utenti hanno già entrambi i genitori, non è possibile che i due utenti siano fratelli
                if(u2_size == 2 && u1_size == 2) throw new NotAllowedException("sib_2");

                // Se entrambi gli utenti hanno un solo genitore
                if(u2_size == 1 && u1_size == 1){
                    // Recupera l'unico genitore di {u1}
                    u1_parent = (utente) u1_parents.iterator().next();
                    // Reucpera l'unico genitore di {u2}
                    u2_parent = (utente) u2_parents.iterator().next();
                    
                    // Se i due genitori identificano lo sesso utente, non è possibile verificare se i due utenti sono fratelli
                    if(u1_parent.equals(u2_parents)) throw new NotAllowedException("sib_1");
                    // Se il genitore di {u1} e il genitore di {u2} sono dello stesso sesso, non è possibile che i due utenti siano fratelli
                    if(!u1_parent.getSesso().equals(u2_parent.getSesso())) throw new NotAllowedException("sib_3");
                    
                    try{
                        // Verifica se i due genitori possono essere coniugi
                        u2_parent.canAddLike(u1_parent, "spouse");
                    }catch(NotAllowedException ex){
                        // i due utenti non possono essere fratelli
                        throw new NotAllowedException("sib_3");
                    }
                    

                }

                do{
                    // Se {u2} non ha genitori e {u1} ne ha solo uno
                    if(u2_size == 0 && u1_size == 1) throw new NotAllowedException("sib_1");

                    // Se {u2} non ha genitori
                    if(u2_size == 0){

                        for(utente parent: u1_parents){
                            try{
                                // Verifica se {u2} può avere entrambi i genitori di {u1}
                                u2.canAddLike(parent, "parent");
                            }catch(NotAllowedException ex){
                                // i due utenti non possono essere fratelli
                                throw new NotAllowedException("sib_3");
                            }
                        }

                    }

                    // Se {u2} ha un solo genitore e {u1} gli ha entrmabi
                    if(u2_size == 1 && u1_size == 2){
                        // Recupera l'unico genitore di {u2}
                        u2_parent = u2_parents.iterator().next();

                        // Se il genitore di {u2} non è anche genitore di {u1}
                        if(!u1_parents.contains(u2_parent)) throw new NotAllowedException("sib_3");

                        utente other_parent;
                        // Se {u2} ha solo la madre
                        if(u2.getByParentela("madre") != null){
                            // Recupera il padre di {u1}
                            other_parent = u1.getByParentela("padre");
                        }else{
                            // Altrimenti recupara la madre di {u1}
                            other_parent = u1.getByParentela("madre");
                        }
                        try{
                            // Verifica se l'altro genitore di {u1} può essere aggiunto come genitore di {u2}
                            u2.canAddLike(other_parent, "parent");
                        }catch(NotAllowedException ex){
                            // i due utenti non possono essere fratelli
                            throw new NotAllowedException("sib_3");
                        }
                    }

                    /* 
                        Le ultime due condizioni vanno considerate anche con gli utenti a parti invertite, 
                        perciò si fa lo swap dei due utenti
                    */

                    // Se gli utenti sono già stati swappati, esci dal ciclo
                    if(u1.equals(user)) break;

                    // Swappa utenti
                    u1 = user;
                    u2 = this;

                    u1_parents = u1.getGenitori();
                    u2_parents = u2.getGenitori();

                    u1_size = u1_parents.size();
                    u2_size = u2_parents.size();

                }while(true);

            }
            /**
             * Verifica se un dato utente può essere aggiunto come figlio
             * @return  true se l'utente è stato aggiunto con successo, false altrimenti
             */
            private void canAddLikeChild(utente user) throws SQLException, NotAllowedException { 
                try{
                    // Verifica se {user} può aggiungere l'utente corrente come genitore
                    user.canAddLike(this, "parent");
                }catch(NotAllowedException ex){
                    throw new NotAllowedException("ch_" + ex.getMessage());
                }
                
            }
        //</editor-fold>
    
    //</editor-fold>
            
        //<editor-fold defaultstate="collapsed" desc="Recupero e gestione richieste di parentela">
    
        /**
         * Recupera le richieste di parentela ricevute
         * @return
         * @throws SQLException
         */
        public ResultSet getRequests() throws SQLException{   
            return Database.selectRecord("richieste", "idreciver = '" + this.id + "'");
        }
        /**
         * Invia una richiesta di parentela
         * @param relative      utente che riceve la richiesta
         * @param relationship  grado di parentela (parent, spouse, child, sibling)
         * @throws NotAllowedException
         * @throws SQLException
         */
        public void sendRequest(utente relative, String relationship) throws NotAllowedException, SQLException {
            // Elimina un'eventuale richiesta in sospeso tra i due utenti
            Database.deleteRecord("richieste", "idsender = '" + this.id + "' AND idreciver='" + relative.getId() + "'");
            // Verifica se l'utente corrente può aggiungere {relative} come parente
            this.canAddLike(relative, relationship);
            // Invia richiesta
            this.send_handler(relative, relationship);
        }
        private void send_handler(utente relative, String relationship) throws NotAllowedException, SQLException {
            if(!(relative.getPassword()==null)){
                //Se non è un profilo base manda la richiesta
                Map<String, Object> data = new HashMap<>();
                data.put("idsender", this.id);
                data.put("idreciver", relative.getId());
                data.put("relazione", relationship);

                Database.insertRecord("richieste", data); 
            } else {
                //Altrimenti aggiungi direttamente l'utente tra i parenti
                this.setRelative(relative, relationship);
            }

            /*
                INVIARE EMAIL DI RICHIESTA  
            */

        }

    //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Gestione rifiuto/accettazione di richieste di parentela">
    
        /**
         * Accetta richiesta di parentela
         * @param relative  parente che invia la richiesta
         * @throws java.sql.SQLException
         * @throws utilita.NotAllowedException
         */
        public void acceptRequest(utente relative) throws SQLException, NotAllowedException {
        
            ResultSet request = Database.selectRecord("richieste", "idsender = '" + relative.getId() + "' AND idreciver = '" + this.id + "'");
            String relationship = "";

            while(request.next()){
                relationship = request.getString("relazione");
            }
            
            try {
                // Effettua il collegamento tra i due parenti
                relative.setRelative(this, relationship);
                
            } finally {
                // Rimuovi la richiesta dal database
                Database.deleteRecord("richieste", "idsender = '" + relative.getId() + "' AND idreciver = '" + this.id + "'");
            }
            
            
            
        }
        /**
         * Declina richiesta di parentela
         * @param relative  parente a cui si è fatta la richiesta
         * @throws java.sql.SQLException
         */
        public void declineRequest(utente relative) throws SQLException{
            Database.deleteRecord("richieste", "idsender = '" + relative.getId() + "' AND idreciver = '" + this.id + "'");
        }
    
    //</editor-fold>
} 


