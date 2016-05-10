/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import Tree.genetree;
import classi.controlli;
import classi.listautenti;
import classi.utente;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utilita.DataUtil;
import utilita.Database;
import utilita.FreeMarker;
import utilita.Message;

/**
 *
 * @author carus
 */
public class ricercalog extends HttpServlet {

    

   
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*Francesco
        Map<String, String> data = new HashMap<>();
        Map<String, Object> ricerca = new HashMap<>();
        
        
            // Inserisci l'azione nel data-model
            
        //Gestione sessione
        HttpSession session=request.getSession(false);
        
        //Se è stato effettuato il login
        
        if(session!=null) { 
            
            // Verifica se l'albero genealogico nella cache è aggiornato
            
            utente user_logged = (utente)session.getAttribute("user_logged");
            
            ricerca.put("user_logged",user_logged);
            user_logged.checkFamilyTreeCache(session);
            
            //ricerca nella barra da loggato 
            
            String nome            = request.getParameter("nome-login").trim();
            String cognome         = request.getParameter("cognome-login").trim();
            String datanascita    = request.getParameter("datanascita-login").trim();
            String citta           = request.getParameter("luogonascita-login").trim();
            
            
            
            
            Message check = null;
            Boolean flag = false;

            // effettuo i controlli sui campi
            // Controllo di dati
            check = controlli.controllodatishort(nome, cognome, datanascita, citta);
                
                if(!(check.isError())){
                    try {
                        flag = Database.shortIn(nome, cognome, datanascita, citta);
                    } catch (SQLException ex) {
                        Logger.getLogger(ricercalog.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    // c'e un errore in shortIn
                }
            // se non ci sono errori ti compilazione campi entro nel primo if
            if(!check.isError()){
                // se trovo sul db i campi inseriti entro nel secondo if 
                if (flag) {
                    // query db
                    data.put("nome",nome);
                    data.put("cognome",cognome);
                    data.put("data_nascita",datanascita);
                    data.put("citta",citta);
                    
                    listautenti results = Database.search2(data); 
                    ricerca.put("risultati", results);
                    
                    FreeMarker.process("ricercalog.html", ricerca, response, getServletContext());
                }else{
                    // se entro qui vuoldire che non ho trovato nessuna persona con quelle caratteristiche
                    response.sendRedirect("notfound");
                }
            }else{
                // se entro qui vuol dire che ho sbagliato a mettere qualche campo nella form
            }
        }else{
                // utente non loggato 
        }
        
    */
        listautenti results = new listautenti();
         
        Map<String, Object> data = new HashMap<>();
        Map<String, String> input_filter = new HashMap<>();
        //Gestione sessione
        HttpSession session=request.getSession(false);
        Message check = new Message(null,false);
        
        //Se è stato effettuato il login...
        if(session!=null) { 
            // Verifica se l'albero genealogico nella cache è aggiornato
            utente user_logged = (utente)session.getAttribute("user_logged");
            user_logged.checkFamilyTreeCache(session);
            data.put("family_tree", (genetree)session.getAttribute("family_tree"));
            data.put("user_logged", (utente)session.getAttribute("user_logged"));
        } 
        
        
            // Recupero del nome
            String nome = DataUtil.spaceTrim(request.getParameter("nome"));
            //String nome = request.getParameter("nome").trim(); 
            // Recupero del cognome
            String cognome = DataUtil.spaceTrim(request.getParameter("cognome"));
            input_filter.put("nome", nome);
            input_filter.put("cognome", cognome);
            
            // Inizializzazione della data e luogo di nascita
            String citta = "";
            String datanascita = "";
            // Se c'è una sessiona attiva
            if(session != null){
                // Recupera la data e il luogo di nascita
                citta = DataUtil.spaceTrim(request.getParameter("citta"));
                datanascita = request.getParameter("datanascita").trim();
                // Se è stata inserita una data di nascita
                if(!datanascita.equals("")){
                    /*
                    try {
                        // Prova a convertire la data di nascita in Date e inserisci il risultato del data-model
                        input_filter.put("datanascita", DataUtil.stringToDate(datanascita, "dd/MM/yyyy").toString());
                    } catch (ParseException ex) {}
                    */
                    Date sqlDate = null;
                    try {
                        sqlDate = DataUtil.stringToDate(datanascita, "dd/MM/yyyy");
                        input_filter.put("datanascita", DataUtil.dateToString(sqlDate));
                    } catch (ParseException ex) { }

                    }else{
                        input_filter.put("datanascita", "");
                }     
                // Inserisci il luogo di nascita nel data-model
                input_filter.put("citta", citta);
                
                String relative = request.getParameter("relative");
                if(relative != null) data.put("selected_relative", relative); 
            }
            
            // Controllo del nome
            if(!DataUtil.isAlphanumeric(nome, true)) {
                check = new Message("name_1", true); // The name must be alphanumeric

            // Controllo del cognome
            }else if(!DataUtil.isAlphanumeric(cognome, true)) {
                check = new Message("surname_1", true); // The surname must be alphanumeric

            // Se c'è una sessione attiva
            }else if(session != null){
                // Controllo della città di nascita
                check = DataUtil.checkBirthplace(citta);
                if(!check.isError()) {
                    // Se è stata inserita una data di nascita
                    if(!datanascita.equals("")){
                        // Controllo della data di nascita
                        check = DataUtil.checkBirthdate(datanascita);
                    }
                }  
            }
           
            
            // Se non sono stati trovati errori
            if(!check.isError()){
            
                // Esegui la ricerca
                //results = Database.search2(input_filter); 
                //results = Database.search(nome);
                //nome= request.getParameter("nome").trim();
                String data1="";
               Date sqlDate = null;
                    try {
                        sqlDate = DataUtil.stringToDate(datanascita, "dd/MM/yyyy");
                        data1= DataUtil.dateToString(sqlDate);
                    } catch (ParseException ex) { }
           
                
            try {
                results = Database.search2(input_filter);
            } catch (SQLException ex) {
                Logger.getLogger(ricercalog.class.getName()).log(Level.SEVERE, null, ex);
            }
            
                
           
            }
            
            // Se c'è una sessione attiva
            if(session != null){
                // Inserirsci la data di nascita del data-model
                //input_filter.put("birthdate", request.getParameter("datanascita").trim());
            }
            
            /*aggiunta parenti
           
            if(request.getParameter("add_to")!=null){
                String add_to_id = request.getParameter("add_to");
            
                User add_to = User.getUserById(add_to_id);
            
                data.put("add_to", add_to);
            }
            */
        
        
        // Se è stato riscontrato qualche errore
        if(check.isError()){
            // Messaggio di errore
            data.put("message", check); 
        }else{
            // Se non è stato trovato qualche utente
            if(results.isEmpty()){
                data.put("message", new Message("null", true)); // No users found
            }else{
                // Mostra risultati
                data.put("risultati", results); 
            }
        }
        
        // Inserisci i campi compilati nel data-model
        data.put("values", input_filter);
        
        // Genera il data-model
        FreeMarker.process("ricercalog.html",data, response, getServletContext());
    
    
    
    }
    

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
