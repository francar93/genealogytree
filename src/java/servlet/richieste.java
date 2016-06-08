package servlet;

import classi.request;
import classi.utente;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utilita.FreeMarker;
import utilita.Message;
import utilita.NotAllowedException;

/**
 *
 * @author matteocapodicasa
 */
public class richieste extends HttpServlet {

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

        if (session != null) {
            // Recupero dell'utente loggato
            utente user_logged = (utente) session.getAttribute("user_logged");

            // Recupero la lista delle richieste
            List<request> requests = new LinkedList<>();
            try {
                ResultSet record = user_logged.getRequests();
                while (record.next()) {
                    requests.add(new request(record));
                }
            } catch (SQLException ex) {

            }
            String msg = request.getParameter("msg");
            // Se ci sono richieste da mostrare
            if (!requests.isEmpty()) {

                Map<String, Object> data = new HashMap<>();
                data.put("user_logged", user_logged);
                data.put("message", new Message(msg, true));
                data.put("requests", requests);

                FreeMarker.process("richieste.html", data, response, getServletContext());
            } else // Se non ci sono messaggi da mostrare
            if (msg == null) {
                // Vai alla pagina del profilo senza mostrare messaggi
                response.sendRedirect("profilo");
            } else {

                // Altrimenti vai alla pagina del profilo mostrando il messaggio
                response.sendRedirect("profilo?msg=" + msg);
            }

        } else {
            // Vai alla pagina di login e mostra messaggio di errore
            response.sendRedirect("login?msn=" + URLEncoder.encode("log", "UTF-8"));
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
        HttpSession session = request.getSession(false);

        if (session != null) {
            Message message;

            // Recupero dell'utente loggato
            utente user_logged = (utente) session.getAttribute("user_logged");

            String accept = request.getParameter("accept");
            String decline = request.getParameter("decline");

            // ACCEPT
            // Se si deve accettare la richiesta di parantela
            if (accept != null) {

                // Recupero dell'utente che ha inviato la richeista
                utente sender = utente.getUserById(accept);
                try {
                    user_logged.acceptRequest(sender);
                    message = new Message("acc", false); // Request accepted
                } catch (NotAllowedException ex) {
                    message = new Message(ex.getMessage(), true); // Not allowed
                } catch (SQLException ex) {
                    message = new Message("srv", true); // Server error
                }

                // Rifiuta
                // Se si deve rifiutare la richiesta di parantela
            } else if (decline != null) {
                // Recupero dell'utente che ha inviato la richeista
                utente sender = utente.getUserById(decline);
                try {
                    user_logged.declineRequest(sender);
                    message = new Message("dec", false); // Request declined
                } catch (SQLException ex) {
                    message = new Message("srv", true); // Server error
                }
            } else {
                // Dati corrotti
                message = new Message("tmp", true); // Tampered data
            }

            // Torna all apagina delle richieste
            response.sendRedirect("richieste?msg=" + URLEncoder.encode(message.getCode(), "UTF-8"));

        } else {
            // Vai alla pagina di login e mostra messaggio di errore
            response.sendRedirect("login?msn=" + URLEncoder.encode("log", "UTF-8"));
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet per la gestione delle richieste di parentela";
    }

}
