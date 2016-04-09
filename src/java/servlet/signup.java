/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import classi.utente;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
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
        String data_nascita    = request.getParameter("data_nascita");
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
        
     /*   if(email.equals("") || password.equals("") || nome.equals("") || cognome.equals("") || sesso == null || data_nascita.equals("")  || citta.equals("")){
            // stampa messaggio errore 
            String messaggio = "errore campo vuoto";
            out.println(messaggio);
            
        }else{
                boolean error = true;

        // Se l'email non è valida
                if(!(EmailValidator.getInstance().isValid(email))){
                    String messaggio = "errore email";
                    out.println(messaggio);
            
        // Se l'utente è già registrato
                }else{
                    utente new_utente = utente.getUserByEmail(email);
                    if(new_utente != null){
                        try {
                            if(new_utente.getPassword() != null){
                                String messaggio = "errore gia registrato";
                                out.println(messaggio);
                                // User already exist
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        }
                    }
                } 
        }
        
    /**
     *
     * @param nome
     */
    /*public void controllinome (String nome){
        if(nome.length() < 2 ){
            String messaggio = "errore troppo corto";
            out.println(messaggio);
                              
            
        }else if(nome.length() > 50 ){
            String messaggio = "errore troppo lungo";
            out.println(messaggio);                                
        }
        }

    */
        
        
        Map<String, Object> data = new HashMap<>();

        //** Generatore utente
        
        // Genera l'id dell'utente
        String user_id = utente.createUniqueUserId(10); 
        
        data.put("id",user_id);
        
        data.put("nome", nome);
        data.put("cognome", cognome);
        data.put("datanascita", data_nascita);
        data.put("citta", citta);
        data.put("sesso", sesso);
        data.put("email",email);
        data.put("password",password);
        data.put("info",info);
        data.put("idPadre",idPadre);
        data.put("idMadre",idMadre);
        data.put("idPartner",idPartner);
        
        
        
                        
        
        
                
        try {
            Database.insertRecord("utente", data);
        } catch (SQLException ex) {
            Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        Date data_nascita1 = null;
        try {
            data_nascita1 = DataUtil.stringToDate(data_nascita, "dd/MM/yyyy");
        } catch (ParseException ex) {
            Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        utente new_user = new utente(user_id, nome, cognome, email, password, sesso, data_nascita1, citta, info);
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
