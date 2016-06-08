package servlet;

import classi.utente;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
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
     * Caricamento delle pagina per la gestione delle richieste
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  
        try {

            HttpSession session = request.getSession(false);

            if (session != null) {
                // Recupero dell'utente loggato
                utente user_logged = (utente) session.getAttribute("user_logged");

                String msg = request.getParameter("msg");
                // Se non ci sono messaggi da mostrare
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

        } catch (Exception ex) {
            response.sendRedirect("error");
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

            // Recupero dei due utenti coinvolti (richiedente e richiesto)
            utente user_sender = utente.getUserById(request.getParameter("user_sender"));
            utente user_receiver = utente.getUserById(request.getParameter("user_receiver"));
            // Recupero del grado di parentela 
            String relationship = request.getParameter("relationship");

            try {

                // Se l'utente che viene aggiunto è un profilo base o un profilo fittizio, non sarà necessario inviare la richiesta
                if (user_receiver.getPassword() == null) {
                    user_sender.setRelative(user_receiver, relationship);
                    message = new Message("basic_add", false);
                } else {

                    // Alrtimenti invia richiesta di parentela
                    user_sender.sendRequest(user_receiver, relationship);
                    message = new Message("snd", false); // Server error
                }

            } catch (SQLException ex) {
                message = new Message("srv", true); // Server error
            } catch (NotAllowedException ex) {
                message = new Message(ex.getMessage(), true); // Not allowed
            }

            response.sendRedirect("profilo?msg=" + URLEncoder.encode(message.getCode(), "UTF-8"));

        } else {
            // Vai alla pagina di login e mostra messaggio di errore
            response.sendRedirect("login?msg=" + URLEncoder.encode("log", "UTF-8"));
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet per effettuare l'aggiunta di parenti post ricerca";
    }

}
