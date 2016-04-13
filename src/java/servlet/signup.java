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

/**
 *
 * @author matteocapodicasa
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   
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
        String nome            = DataUtil.spaceTrim(request.getParameter("nome"));
        String cognome         = DataUtil.spaceTrim(request.getParameter("cognome"));
        String data_nascita    = request.getParameter("datanascita").trim();
        String citta           = DataUtil.spaceTrim(request.getParameter("citta").trim());
        String email           = request.getParameter("email").trim();
        String sesso           = request.getParameter("sesso").trim();
        String password        = request.getParameter("password").trim();
       
        
        
      
        Map<String, Object> data = new HashMap<>();

        //** Generatore utente
        
        // Genera l'id dell'utente
        String user_id = utente.createUniqueUserId(10);
        
     
        
        data.put("nome", nome);
        data.put("cognome", cognome);
        //data.put("datanascita", data_nascita);
        data.put("id",user_id);
        data.put("citta", citta);
        data.put("sesso", sesso);
        data.put("email", email);
        data.put("password", password);
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
        
       
        
        
        
       response.sendRedirect("profilo");
        
                /*
        try {
            Database.insertRecord("user", data);
        } catch (SQLException ex) {
            Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        String query="INSERT INTO user (nome, cognome, datanascita, id, citta, sesso, email, password, info, idmadre, idpadre, idpartner) VALUE"+"'"+nome+"'"+"'"+cognome+"'"+data_nascita+"'"+"'"+user_id+"'"+"'"+citta+"'"+"'"+sesso+"'"+"'"+email+"'"+"'"+password+"'"+info+"'"+idPadre+"'"+idMadre+"'"+idPartner+"'";

        
        
        
        String query="INSER INTO user (nome, cognome, id, citta, sesso, email, password) VALUE"+"'"+nome+"'"+"'"+cognome+"'"+"'"+user_id+"'"+"'"+citta+"'"+"'"+sesso+"'"+"'"+email+"'"+"'"+password+"'";
        try {
            Database.inserimento(query);
        } catch (SQLException ex) {
            Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Reindirizzamento alla pagina del profilo dell'utente
            
        
        //response.sendRedirect("logged");
                */
        
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
