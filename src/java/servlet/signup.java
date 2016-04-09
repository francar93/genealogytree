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
import java.util.Date;
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

/**
 *
 * @author carus
 */
public class signup extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   

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
         request.setAttribute("action", "signup");
        request.getRequestDispatcher("login").forward(request, response);
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
        String nome            = request.getParameter("nome");
        String cognome         = request.getParameter("cognome");
        String data_nascita    = DataUtil.spaceTrim(request.getParameter("data_nascita"));
        String citta           = request.getParameter("citta");
        String sesso           = request.getParameter("sesso");
        String email           = request.getParameter("email");
        String password        = request.getParameter("password");
        String info            = request.getParameter("info");
        String idPadre         = request.getParameter("idPadre");
        String idMadre         = request.getParameter("idMadre");
        String idPartner         = request.getParameter("idPartner");
        
        String telefono        = request.getParameter("telefono");
        String cell            = request.getParameter("cell");
        
        if(email.equals("") || password.equals("") || nome.equals("") || cognome.equals("") || sesso == null || data_nascita.equals("")  || citta.equals("")){
            // stampa messaggio errore 
        }else{
            //altri controlli che farò domattina :D 
        }
              
        
        Map<String, Object> data = new HashMap<>();

        //** Generatore utente
        
        // Genera l'id dell'utente
        String user_id = utente.createUniqueUserId(10); //manca il metodo 
        
        data.put("id",user_id);
        
        data.put("nome", nome);
        data.put("cognome", cognome);
        data.put("data_nascita", data_nascita);
        data.put("citta", citta);
        data.put("sesso", sesso);
        data.put("email",email);
        data.put("password",password);
        data.put("info",info);
        data.put("idPadre",idPadre);
        data.put("idMadre",idMadre);
        data.put("idPartner",idPartner);
        
        data.put("telefono", telefono);
        data.put("cell", cell);
        
        
                
        try {
            Database.insertRecord("utente", data);
        } catch (SQLException ex) {
            Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
       // da aggiungere chiamata al metodo string to date
        
        utente new_user = new utente(user_id, nome, cognome, email, password, sesso, data_nascita, citta, info);
        // Prepara l'utente ad essere loggato (gestione della variabili si sessione)
        HttpSession s = request.getSession(true);
        s.setAttribute("utente", user_id);

        
        // Reindirizzamento alla pagina del profilo dell'utente
            
        
        response.sendRedirect("logged");
        
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
