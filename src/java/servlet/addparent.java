package servlet;

import Tree.genetree;
import Tree.treenode;
import classi.controlli;
import classi.utente;
import java.io.IOException;
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
import utilita.NotAllowedException;

/**
 *
 * @author caruso
 */

public class addparent extends HttpServlet {
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
  
        HttpSession session = request.getSession(false);

        if (session != null) {

            Map<String, Object> data = new HashMap<>();

            utente user_logged = (utente) session.getAttribute("user_logged");

            // Recupero dell'utente corrente
            utente user_current;
            treenode user_current_node;

            if (request.getParameter("id") != null) {
                user_current_node = ((genetree) session.getAttribute("family_tree")).getUserById((String) request.getParameter("id"));
                user_current = user_current_node.getUser();
                data.put("relative_grade", user_current_node.getLabel());

            } else {
                user_current = user_logged;
            }

            data.put("user_logged", user_logged);
            data.put("user_current", user_current);
            
            //Codifica del messaggio di errore sulla base del codice inviato
            data.put("message", new Message(request.getParameter("msg"), true));
            FreeMarker.process("addparent.html", data, response, getServletContext());

        } else {
            response.sendRedirect("login?msg=log");
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
                Message check = new Message("dt_ok", false);
                // Recupera l'azione da svolgere
                //non viene utilizzata
                String action = request.getRequestURI().substring(request.getContextPath().length() + 1); // create o invite

                HttpSession session = request.getSession(false);
            
                // Se è attiva una sessiona
                if (session != null) {
                    
                            
                                   
                        String nome            = DataUtil.spaceTrim(request.getParameter("nome"));
                        String cognome         = DataUtil.spaceTrim(request.getParameter("cognome"));
                        String data_nascita    = request.getParameter("datanascita").trim();
                        String citta           = DataUtil.spaceTrim(request.getParameter("citta").trim());
                        String email           = request.getParameter("email").trim();
                        String sesso           = request.getParameter("sesso");
                        String relazione        = request.getParameter("relationship");
                        
                        utente user_logged = (utente) session.getAttribute("user_logged");
                        treenode user_current_node = ((genetree) session.getAttribute("family_tree")).getUserById(user_logged.getId());
                        utente user_current = user_current_node.getUser();
                        utente user_current1 = (utente) session.getAttribute("user_logged");
                     

                        if(email.equals("")|| nome.equals("") || cognome.equals("") || sesso.equals("vuoto") || data_nascita.equals("")  || citta.equals("")){
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

                        
                                }
                            
                        }

                        if(!check.isError()){

                            Map<String, Object> data = new HashMap<>();

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
                                
                                utente user_added = utente.getUserById(user_id);
                                user_current1.setRelative(user_added, relazione);
               
                            } catch (SQLException ex) {
                                check = new Message("srv", true);
                            } catch (NotAllowedException ex) { 
                                check = new Message(ex.getMessage(), true);
                            }

                            if(!check.isError()){
                                // Torna alla pagina del profilo
                                response.sendRedirect("profilo"+"?msg="+"basic_add");
   
                            }else{
                                try {
                                    //cancella l'utente
                                    Database.deleteUtente(user_id);
                                } catch (SQLException ex) {
                                    Logger.getLogger(addparent.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                // Mostra messaggio di errore
                            response.sendRedirect(action + "?msg=" + check.getCode());
                                
                            }

        
                        }else{
                            // Mostra messaggio di errore
                            response.sendRedirect(action + "?msg=" + check.getCode());
                        }
        
                }else {
                            response.sendRedirect("login?msg=log");
                        }
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet per l'aggiunta di un parente in possesso di indirizzo email";
    }  
}