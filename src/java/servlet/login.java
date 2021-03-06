package servlet;

import classi.utente;
import java.io.IOException;
import java.net.URLEncoder;
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
public class login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);

        HttpSession session = request.getSession(false);
        // Se non è stata generata la sessione
        if (session == null) {

            Map<String, Object> data = new HashMap<>();

            String action = (String) request.getAttribute("action");
            // Se l'azione non è stata definita o non è valida, impostala come l'azione di login
            if (action == null || (action.equals("login") && action.equals("signup"))) {
                action = "login";
            }
            // Inserisci l'azione nel data-model
            data.put("action", action);

            data.put("message", new Message(request.getParameter("msg"), true));

            data.put("script", "login");

            FreeMarker.process("login.html", data, response, getServletContext());
        } else {

            response.sendRedirect("profilo");
            
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
        // processRequest(request, response);

        //recupero dell'utente e della password
        String email = request.getParameter("email");
        String password = request.getParameter("password").trim();

        String msg = null;
        boolean error = true;
        int flag = 0;
        if ((email.equals("") && password.equals("")) || email.equals("")) {

            msg = "usr_0";

        } else {

            try {
                if (password.equals("")) {

                    if (Database.emailIn(email)) {

                        if (Database.controlloPassword(email)) {

                            //deve andare a profilo precompilato
                            flag = 1;

                        } else {

                            msg = "psd_5";

                            //l'utente deve inserire la password
                        }
                    } else {

                        //l'email non esiste
                        msg = "usr_2";

                    }
                } else if (Database.controlloEmailPassword(email, password)) {
                    flag = 2;//login normale

                } else {
                    msg = "usr_3";
                    //user e password errate
                }
            } catch (SQLException ex) {
                Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        switch (flag) {

            case 1:
                response.sendRedirect("profilopre"+"?email1="+email);
                break;

            case 2:
                utente loggato = utente.getUserByEmail(email);
                loggato.initSession2(request.getSession());
                response.sendRedirect("profilo");
                break;

            default:
                response.sendRedirect("login?msg=" + URLEncoder.encode(msg, "UTF-8"));

        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Sevlet per la gestione della login";
    }// </editor-fold>

}
