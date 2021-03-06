package utilita;

import Tree.NodeList;
import classi.listautenti;
import classi.listautenticonnumero;
import classi.utente;
import classi.utenteconnumero;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 *
 * @author matteocapodicasa
 */
public class Database { 
    private static java.sql.Connection db;
    
    /**
     * Connessione al database
     * @throws javax.naming.NamingException
     * @throws java.sql.SQLException
     */
    public static void connect() throws NamingException, SQLException{
        InitialContext ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:comp/env/genealogytree");
        Database.db  = ds.getConnection();
    }
    
    /**
     * Chiusura connessione al database
     * @throws java.sql.SQLException
     */
    public static void close() throws SQLException{
        Database.db.close();
    }
    
    
    public static ResultSet selectRecord1(String table) throws SQLException {
        // Generazione query
        String query = "SELECT * FROM " + table;
        
        Statement s1 = Database.db.createStatement();
        ResultSet records = s1.executeQuery(query);
        // Esecuzione query
        return records;
    }
    
    public static boolean emailIn(String email) throws SQLException{
        String query = "SELECT * FROM user WHERE email='"+ email +"'";
        ResultSet in = Database.executeQuery(query);
        return(in.next());
     }
    public static boolean nameIn(String name) throws SQLException{
        String query = "SELECT * FROM user WHERE nome='"+ name +"'";
        ResultSet in = Database.executeQuery(query);
        return(in.next());
     }
    
    public static boolean surnameIn(String cognome) throws SQLException{
        String query = "SELECT * FROM user WHERE cognome='"+ cognome +"'";
        ResultSet in = Database.executeQuery(query);
        return(in.next());
     }
    
    public static boolean shortIn(String name,String cognome, String data_nascita, String citta) throws SQLException{
        String query = "SELECT * FROM user WHERE nome='"+ name + "'" + "OR cognome=" +  "'" + cognome +  "'" + "OR datanascita=" + "'" + data_nascita +  "'" + "OR citta=" +  "'" + citta +  "'";
        ResultSet in = Database.executeQuery(query);
        return(in.next());
     }
    
    public static boolean controlloEmailPassword(String email, String password) throws SQLException{
        String query = "SELECT * FROM  user WHERE email=" + "'" + email + "'" + "AND password=" +  "'" + password +  "'" ;
        ResultSet in = Database.executeQuery(query);
        return(in.next());
    }
    
     public static ResultSet controlloProfilo(String email) throws SQLException{
        String query = "SELECT * FROM  user WHERE email=" + "'" + email + "'";
        return Database.executeQuery(query);
    }
     
    
     
      public static boolean controlloPassword(String email) throws SQLException{
        String query = "SELECT password FROM user WHERE email='"+ email +"'";
        ResultSet in = Database.executeQuery(query);
        
        String password = null;
        
        while(in.next()) {
     
        password = in.getString(1);

        if(in.wasNull()) {
          password = "";
        }
      
        }
        return(password.equals(""));
        
     
    }
     
     
    
    
    /**
     * Select record con condizione
     * @param table         tabella da cui prelevare i dati
     * @param condition     condizione per il filtro dei dati
     * @return              dati prelevati
     * @throws java.sql.SQLException
     */
    public static ResultSet selectRecord(String table, String condition) throws SQLException {
        // Generazione query
        String query = "SELECT * FROM " + table + " WHERE " + condition;
        // Esecuzione query
        return Database.executeQuery(query);
    }
    /**
     * Select record con condizione e ordinamento
     * @param table         tabella da cui prelevare i dati
     * @param condition     condizione per il filtro dei dati
     * @param order         ordinamento dei dati
     * @return              dati prelevati
     * @throws java.sql.SQLException
     */
    public static ResultSet selectRecord(String table, String condition, String order) throws SQLException{
        // Generazione query
        String query = "SELECT * FROM " + table + " WHERE " + condition + " ORDER BY " + order;
        // Esecuzione query
        return Database.executeQuery(query);
    }
    
    /**
     * Select record con join tra due tabelle
     * @param table_1           nome della prima tabella
     * @param table_2           nome della seconda tabella
     * @param join_condition    condizione del join tra la tabelle
     * @param where_condition   condizione per il filtro dei dati
     * @return                  dati prelevati
     * @throws java.sql.SQLException
     */
    public static ResultSet selectJoin(String table_1, String table_2, String join_condition, String where_condition) throws SQLException{
        // Generazione query
        String query = "SELECT * FROM " + table_1 + " JOIN " + table_2 + " ON " + join_condition + " WHERE " + where_condition;
        // Esecuzione query
        return Database.executeQuery(query);
    }
    
    /**
     * Select record con join tra due tabelle e ordinamento
     * @param table_1           nome della prima tabella
     * @param table_2           nome della seconda tabella
     * @param join_condition    condizione del join tra la tabelle
     * @param where_condition   condizione per il filtro dei dati
     * @param order             ordinamento dei dati
     * @return                  dati prelevati
     * @throws java.sql.SQLException
     */
    public static ResultSet selectJoin(String table_1, String table_2, String join_condition, String where_condition, String order) throws SQLException{
        // Generazione query
        String query = "SELECT * FROM " + table_1 + " JOIN " + table_2 + " ON " + join_condition + " WHERE " + where_condition + "ORDER BY" + order;
        // Esecuzione query
        return Database.executeQuery(query);
    }
    
    /**
     * Insert record
     * @param table     tabella in cui inserire i dati
     * @param data      dati da inserire
     * @return dati     prelevati
     * @throws java.sql.SQLException
     */
    public static boolean insertRecord(String table, Map<String, Object> data) throws SQLException{
        // Generazione query
        String query = "INSERT INTO " + table + " SET ";
        Object value;
        String attr;
        
        for(Map.Entry<String,Object> e:data.entrySet()){
            attr = e.getKey();
            value = e.getValue();
            if(value instanceof Integer){
                query = query + attr + " = " + value + ", ";
            }else{
                value = value.toString().replace("\'", "\\'");
                query = query + attr + " = '" + value + "', ";
            }
        }
        query = query.substring(0, query.length() - 2);
        // Esecuzione query
        return Database.updateQuery(query);
    }
    
    /**
     * Update record
     * @param table         tabella in cui aggiornare i dati
     * @param data          dati da inserire
     * @param condition     condizione per il filtro dei dati
     * @return              true se l'inserimento è andato a buon fine, false altrimenti
     * @throws java.sql.SQLException
     */
    public static boolean updateRecord(String table, Map<String,Object> data, String condition) throws SQLException{
        // Generazione query
        String query = "UPDATE " + table + " SET ";
        Object value;
        String attr;
        
        for(Map.Entry<String,Object> e:data.entrySet()){
            attr = e.getKey();
            value = e.getValue();
            if(value instanceof String){
                value = value.toString().replace("\'", "\\'");
                query = query + attr + " = '" + value + "', ";
            }else{
                query = query + attr + " = " + value + ", ";
            }
            
            
        }
        query = query.substring(0, query.length()-2) + " WHERE " + condition;
        
        // Esecuzione query
        return Database.updateQuery(query);
    }
    
    /**
     * Delete record
     * @param table         tabella in cui eliminare i dati
     * @param condition     condizione per il filtro dei dati
     * @return              true se l'eliminazione è andata a buon fine, false altrimenti
     * @throws java.sql.SQLException
     */
    public static boolean deleteRecord(String table, String condition) throws SQLException{
        // Generazione query
        String query = "DELETE FROM " + table + " WHERE " + condition;
        // Esecuzione query
        return Database.updateQuery(query);
    }
    
    /**
     * Count record
     * @param table         tabella in cui contare i dati
     * @param condition     condizione per il filtro dei dati
     * @return              numero dei record se la query è stata eseguita on successo, -1 altrimenti
     * @throws java.sql.SQLException
     */
    public static int countRecord(String table, String condition) throws SQLException{

        // Generazione query
        String query = "SELECT COUNT(*) FROM " + table + " WHERE " + condition;
        // Esecuzione query
        ResultSet record = Database.executeQuery(query);
        record.next();
        // Restituzione del risultato
        return record.getInt(1);

    }
    
    /**
     * Imposta a NULL un attributo di una tabella  
     * @param table         tabella in cui è presente l'attributo
     * @param attribute     attributo da impostare a NULL
     * @param condition     condizione
     * @return
     * @throws java.sql.SQLException
     */
    public static boolean resetAttribute(String table, String attribute, String condition) throws SQLException{
        String query = "UPDATE " + table + " SET " + attribute + " = NULL WHERE " + condition;
        return Database.updateQuery(query); 
    }

    public static boolean deleteUtente(String id) throws SQLException{
        // Generazione query
        String query = "DELETE FROM user WHERE id='" + id +"'";
        // Esecuzione query
        return Database.updateQuery(query);
    }
    // <editor-fold defaultstate="collapsed" desc="Metodi ausiliari.">
    
    /**
     * executeQuery personalizzata
     * @param query query da eseguire
     */
    private static ResultSet executeQuery(String query) throws SQLException{
        
        Statement s1 = Database.db.createStatement();
        ResultSet records = s1.executeQuery(query);

        return records; 
            
    }
    
    /**
     * updateQuery personalizzata
     * @param query query da eseguire
     */
    private static boolean updateQuery(String query) throws SQLException{
        
        Statement s1;
        
        s1 = Database.db.createStatement();
        s1.executeUpdate(query); 
        s1.close();
        return true; 

    }
    
    
    public static listautenti search(String input) throws SQLException{
        listautenti result = new listautenti();
        String condition = "nome= '"+input+"'OR cognome='"+input+"'";
        
            ResultSet record = Database.selectRecord("user", condition);
            while(record.next()){
                result.add(new utente(record));  
            }
        
        
        return result;
    }
    
    
    public static listautenti search1(String nome) throws SQLException{
        listautenti result = new listautenti();
        String condition = "datanascita= '"+nome+"'";
        
            ResultSet record = Database.selectRecord("user", condition);
            while(record.next()){
                result.add(new utente(record));  
            }
        
        
        return result;
    }
    
    
    public static listautenticonnumero searchnolog(String input) throws SQLException{
        listautenticonnumero result = new listautenticonnumero();
        
       String condition2 = "nome= '"+input+"'OR cognome='"+input+"' OR CONCAT(nome, ' ', cognome)='"+input+"'OR CONCAT(cognome, ' ', nome)='"+input+"'";
        
            ResultSet record = Database.selectRecord("user", condition2);
            while(record.next()){
                
                utente attuale= new utente(record);
                
                NodeList family_tree = attuale.getFamilyTree().getFamily_tree();

                // Calcola il numero di parenti (-1 per non considerare l'utente stesso)
                int tree_size = family_tree.size() - 1;

                int num = 3;
                result.add(new utenteconnumero(attuale,tree_size)); 
                
                
            }
        
        
        return result;
    }
   // </editor-fold>
 
    public static listautenti search2(Map<String, String> input) throws SQLException{
        listautenti result = new listautenti();
        
        String condition_string = "";
        for(Map.Entry<String, String> entry : input.entrySet()){
            
            
                if(!entry.getValue().equals("")){
                    if(!condition_string.equals("")){
                        condition_string += " AND ";
                    }
                    
                        condition_string += entry.getKey() + "='" + entry.getValue() + "'";
                   
                }     
        }
            ResultSet record = Database.selectRecord("user", condition_string);
            while(record.next()){
                result.add(new utente(record));  
            }
        return result;
    }
}
