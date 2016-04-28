/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import classi.controlli;
import classi.utente;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Date;
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
public class profilopre extends HttpServlet {
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        HttpSession session = request.getSession(false);
        Map<String, Object> data = new HashMap<>();
        
        
        //Se è stata generata la sessione
        if(session != null){
            utente user_logged = (utente)session.getAttribute("user_logged");
            data.put("user_logged", user_logged);
                   
            FreeMarker.process("addparent.html", data, response, getServletContext());
        }else {
            response.sendRedirect("login?msg=" + URLEncoder.encode("log", "UTF-8"));
        }
        
    
    }
     /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @return
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session != null){
            
            String nome            = DataUtil.spaceTrim(request.getParameter("nome"));
            String cognome         = DataUtil.spaceTrim(request.getParameter("cognome"));
            String data_nascita    = request.getParameter("datanascita").trim();
            String citta           = DataUtil.spaceTrim(request.getParameter("citta").trim());
            String email           = request.getParameter("email").trim();
            String sesso           = request.getParameter("sesso");

            Message check = null;

            if(email.equals("") || nome.equals("") || cognome.equals("") || sesso.equals("vuoto") || data_nascita.equals("")  || citta.equals("")){
                check = new Message("fld", true);
            }else{

                try {
                    // Controllo dell'email
                    check = controlli.controlloemail(email);
                } catch (SQLException ex) {
                    Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(!check.isError()) {

                        // Controllo di dati
                        check = controlli.controllodati(nome, cognome, sesso, data_nascita, citta);

            }}



            if(!check.isError()){


                Map<String, Object> data = new HashMap<>();


                //** Generatore utente

                // Genera l'id dell'utente
                String user_id = utente.createUniqueUserId(10);


                data.put("nome", nome);
                data.put("cognome", cognome);
                data.put("id",user_id);
                data.put("citta", citta);
                data.put("sesso", sesso);
                data.put("email", email);
                data.put("info", "");


                Date sqlDate = null;
                    try {
                        sqlDate = DataUtil.stringToDate(data_nascita, "dd/MM/yyyy");
                        data.put("datanascita", DataUtil.dateToString(sqlDate));
                    } catch (ParseException ex) { }



                try {
                    Database.insertRecord("user", data);
                } catch (SQLException ex) {
                    Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
                }
                /*
                HttpSession session = request.getSession(true);

                utente loggato= utente.getUserByEmail(email);

                String id = loggato.getId();

                session.setAttribute("id",id);
                */

                //utente loggato= utente.getUserByEmail(email);
                //loggato.initSession2(request.getSession());
                   
                // da scrivere l'inserivmento come parente mio 
                
                response.sendRedirect("profilo");

            }else{

                response.sendRedirect("profilopre?msg=" + URLEncoder.encode(check.getCode(), "UTF-8"));

            }
        }else {
            response.sendRedirect("login?msg=" + URLEncoder.encode("log", "UTF-8"));
        }    
    }
}
