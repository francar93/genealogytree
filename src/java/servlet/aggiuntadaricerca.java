/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import classi.utente;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utilita.Message;
import utilita.NotAllowedException;

/**
 *
 * @author matteocapodicasa
 */
public class aggiuntadaricerca extends HttpServlet {

  
   

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
       
        HttpSession session = request.getSession(false);

        if(session!=null){
            
            Message message;
            
            
                    String page_redirect = "profile";

                    // Recupero dei due utenti coinvolti (richiedente e richiesto)
                    utente user_sender = utente.getUserById(request.getParameter("user_sender"));
                    utente user_receiver = utente.getUserById(request.getParameter("user_receiver"));
                    // Recupero del grado di parentela 
                    String relationship = request.getParameter("relationship");

                    try{
                        
                        // Se l'utente che viene aggiunto è un profilo base, non sarà necessario inviare la richiesta
                        if(user_receiver.getPassword()==null){
                            user_sender.setRelative(user_receiver, relationship);
                            message = new Message("basic_add", false);
                        } else {
                        
                            // Alrtimenti invia richiesta di parentela
                            user_sender.sendRequest(user_receiver, relationship);
                            message = new Message("snd", false); // Server error
                        }
                        
                    } catch(SQLException ex){
                        message = new Message("srv", true); // Server error
                    } catch(NotAllowedException ex){
                        message = new Message(ex.getMessage(), true); // Not allowed
                    }
            
            
            
            
            
            
            
            
            
            
            
            
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
    }

}
