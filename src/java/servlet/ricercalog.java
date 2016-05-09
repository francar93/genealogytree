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
import java.sql.SQLException;
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

    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        
        Map<String, String> data = new HashMap<>();
        Map<String, Object> ricerca = new HashMap<>();
        
        //Gestione sessione
        HttpSession session=request.getSession(false);
        
        //Se è stato effettuato il login
        
        if(session!=null) { 
            
            // Verifica se l'albero genealogico nella cache è aggiornato
            
            utente user_logged = (utente)session.getAttribute("user_logged");
            user_logged.checkFamilyTreeCache(session);
            
            /* ricerca nella barra da loggato */
            
            String nome            = request.getParameter("nome-login").trim();
            String cognome         = request.getParameter("cognome-login").trim();
            String data_nascita    = request.getParameter("datanascita-login").trim();
            String citta           = request.getParameter("luogonascita-login").trim();
            
            data.put("nome",nome);
            data.put("cognome",cognome);
            data.put("data_nascita",data_nascita);
            data.put("citta",citta);
            
            
            Message check = null;
            Boolean flag = false;

            // effettuo i controlli sui campi
            // Controllo di dati
            check = controlli.controllodatishort(nome, cognome, data_nascita, citta);
                
                if(!(check.isError())){
                    try {
                        flag = Database.shortIn(nome, cognome, data_nascita, citta);
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
        
    
    
    
    
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
