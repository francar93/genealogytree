/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import Tree.genetree;
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
import utilita.Message;

/**
 *
 * @author matteocapodicasa
 */
public class ricercanolog extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
  

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
        //processRequest(request, response);
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
        //processRequest(request, response);
        
        listautenti results = new listautenti();
        Map<String, Object> data = new HashMap<>();
        Map<String, String> input_filter = new HashMap<>();
        //Gestione sessione
        HttpSession session=request.getSession(false);
        Message check = new Message(null, false);
        
        //Se è stato effettuato il login...
        if(session!=null) { 
            // Verifica se l'albero genealogico nella cache è aggiornato
            utente user_logged = (utente)session.getAttribute("user_logged");
            user_logged.checkFamilyTreeCache(session);
            data.put("family_tree", (genetree)session.getAttribute("family_tree"));
            data.put("user_logged", (utente)session.getAttribute("user_logged"));
        } 
        
        
            
            /* Ricerca dalla search bar */
            input_filter.put("name", "");
            input_filter.put("surname", "");
            input_filter.put("birthplace", "");
            input_filter.put("birthdate", "");
             
            String input = request.getParameter("search-bar-input");
            if(!input.equals("")){
                // Se la stringa da cercare è alfanumerica
                if(!DataUtil.isAlphanumeric(input, true)){
                    check = new Message("alp", false);
                }else{
                    
                    
                    try {
                        // Cerca la stringa
                        results = Database.search(input);
                    } catch (SQLException ex) {
                        Logger.getLogger(ricercanolog.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                }
                data.put("searching", input);
            }else{
                response.sendRedirect("search");
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
