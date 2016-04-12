/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import classi.controlli;
import static classi.controlli.campivuoti;
import static classi.controlli.emaildb;
import static classi.controlli.emailvalida;
import classi.utente;
import static freemarker.template.utility.NullArgumentException.check;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.ParseException;
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
import org.apache.commons.validator.EmailValidator;
import utilita.DataUtil;
import utilita.Database;
import utilita.Message;
import utilita.Messaggi;

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
        String data_nascita    = request.getParameter("data_nascita");
        String citta           = request.getParameter("citta");
        String sesso           = request.getParameter("sesso");
        String email           = request.getParameter("email");
        String password        = request.getParameter("password");
        String info            = request.getParameter("info");
        
        
        Message check;
       
        // Se non sono stati compilati tutti i campi
        if(email.equals("") || password.equals("") || nome.equals("") || cognome.equals("") || sesso == null || data_nascita.equals("")  || citta.equals("")){
            check = new Message("fld", true);
        }else{

            // Controllo dell'email
            check = controlli.controlloemail(email);
            if(!check.isError()) {

                // Controllo della password
                check = controlli.controllopassword(password);
                if(!check.isError()) {

                    // Controllo di dati
                    check = controlli.controllodati(nome, cognome, sesso, data_nascita, citta);

        }}}

        // Se è stato riscontrato un errore, 
        if(!check.isError()){
            
            Map<String, Object> data = new HashMap<>();

        //** Generatore utente
        
        // Genera l'id dell'utente
        String user_id = utente.createUniqueUserId(10); 
        data.put("id",user_id);
        
        data.put("nome", nome);
        data.put("cognome", cognome);
        data.put("citta", citta);
        data.put("sesso", sesso);
        data.put("email",email);
        data.put("password",password);
        data.put("info",info);
        
        
        Date data_nascita1 = null;
        try {
            data_nascita1 = DataUtil.stringToDate(data_nascita, "dd/MM/yyyy");
            data.put("datanascita", data_nascita1);
        } catch (ParseException ex) {
            Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
        
        }     
                                
        try {
            Database.insertRecord("user", data);
            // creo un nuovo oggetto
            utente new_user = new utente(user_id, nome, cognome, email, password, sesso, data_nascita1, citta, info);
            // Prepara l'utente ad essere loggato (gestione della variabili di sessione)
            HttpSession s = request.getSession(true);
            s.setAttribute("utente", new_user);
            
        } catch (SQLException ex) {}
        
            // Reindirizzamento alla pagina del profilo dell'utente se va a buon fine
            response.sendRedirect("profilo");      
        }else{
            // Messaggio errore
        
            response.sendRedirect("signup?msg=" + URLEncoder.encode(check.getCode(), "UTF-8"));
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
