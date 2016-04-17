/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utilita.FreeMarker;
import classi.utente;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author matteocapodicasa
 */
public class profilo extends HttpServlet {


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

        HttpSession session = request.getSession(false);

        //Se è stata generata la sessione
        if (session != null) {
            String id_utente = (String) session.getAttribute("id");

            Map<String, Object> data = new HashMap<>();

            //recupero utente loggato
            utente loggato = utente.getUserById(id_utente);

            //recupero del padre
            utente padre = null;

            try {
                padre = loggato.getGenitore("maschio");
            } catch (SQLException ex) {
                Logger.getLogger(profilo.class.getName()).log(Level.SEVERE, null, ex);
            }

            data.put("id", loggato);
            data.put("padre", padre);

            FreeMarker.process("provastampadb.html", data, response, getServletContext());

        } else {
            response.sendRedirect("login?msg=" + URLEncoder.encode("log", "UTF-8"));
        }

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