package servlet;

import classi.listautenti;
import classi.listautenticonnumero;
import java.io.IOException;
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
import utilita.Database;
import utilita.FreeMarker;
import utilita.Message;

/**
 *
 * @author matteocapodicasa
 */
public class ricercanolog extends HttpServlet {

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

        listautenticonnumero a = new listautenticonnumero();

        listautenti results = new listautenti();
        Map<String, Object> data = new HashMap<>();
        String action = (String) request.getAttribute("action");
        // Se l'azione non è stata definita o non è valida, impostala come l'azione di login
        if (action == null || (action.equals("login") && action.equals("signup"))) {
            action = "login";
        }
        // Inserisci l'azione nel data-model
        data.put("action", action);
        //Gestione sessione
        HttpSession session = request.getSession(false);
        Message check = new Message(null, false);

        String input = request.getParameter("cerca-nologin");

        if (!input.equals("")) {

            try {
                // Cerca la stringa
                results = Database.search(input);
            } catch (SQLException ex) {
                Logger.getLogger(ricercanolog.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                // Cerca la stringa
                a = Database.searchnolog(input);
            } catch (SQLException ex) {
                Logger.getLogger(ricercanolog.class.getName()).log(Level.SEVERE, null, ex);
            }

            //}
            data.put("utente", a);
            FreeMarker.process("ricercanolog1.html", data, response, getServletContext());
        } else {
            response.sendRedirect("login");
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet per la gestione della ricerca non loggata";
    }// </editor-fold>

}
