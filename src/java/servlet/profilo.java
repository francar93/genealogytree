package servlet;

import Tree.NodeList;
import Tree.genetree;
import Tree.treenode;
import java.io.IOException;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilita.Message;

/**
 *
 * @author matteocapodicasa
 */
public class profilo extends HttpServlet {

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
        Map<String, Object> data = new HashMap<>();

        HttpSession session = request.getSession(false);

        //Se è stata generata la sessione
        if (session != null) {
            genetree family_tree = (genetree) session.getAttribute("family_tree");

            // Recupero dell'utente loggato
            utente user_logged = (utente) session.getAttribute("user_logged");

            ///ultima messa prova
            NodeList family_tree1;
            int tree_size = 0;
            try {
                family_tree1 = user_logged.getFamilyTree().getFamily_tree();
                tree_size = family_tree1.size() - 1;
            } catch (SQLException ex) {
                Logger.getLogger(profilo.class.getName()).log(Level.SEVERE, null, ex);
            }

            boolean refresh = user_logged.checkFamilyTreeCache(session);
            if (!refresh) {

                utente user_current;
                treenode user_current_node;
                String relative_grade = null;
                if (request.getParameter("id") != null) {
                    //data.put();
                    user_current_node = family_tree.getUserById((String) request.getParameter("id"));
                    user_current = user_current_node.getUser();
                    relative_grade = user_current_node.getLabel();
                } else {
                    user_current = user_logged;
                    relative_grade = "Tu";
                }

                /* Recupero dei parenti dell'utente corrente */
                // Recupero del padre
                treenode father = null;
                try {
                    father = family_tree.getUser(user_current.getByParentela("padre"));
                } catch (SQLException ex) {
                    Logger.getLogger(profilo.class.getName()).log(Level.SEVERE, null, ex);
                }

                treenode io = null;

                io = family_tree.getUser(user_logged);

                // Recupero della madre
                treenode mother = null;
                try {
                    mother = family_tree.getUser(user_current.getByParentela("madre"));
                } catch (SQLException ex) {
                }

                // Recupero del coniuge
                treenode spouse = null;
                try {
                    spouse = family_tree.getUser(user_current.getByParentela("compagno"));
                } catch (SQLException ex) {
                }

                // Recupero dei fratelli
                NodeList siblings = null;
                try {
                    siblings = family_tree.getUsers(user_current.getFratelliSorelle());
                } catch (SQLException ex) {
                }

                // Recupero dei figli
                NodeList children = null;
                try {
                    children = family_tree.getUsers(user_current.getFigli());
                } catch (SQLException ex) {
                }
                
                //data-model
                
                String filename = ("template/img/profilo/");

                data.put("foto", filename);
                data.put("user_logged", user_logged);
                data.put("user_current", user_current);
                data.put("relative_grade", relative_grade);
                data.put("siblings", siblings);
                data.put("children", children);
                data.put("spouse", spouse);
                data.put("mother", mother);
                data.put("io", io);
                data.put("father", father);
                data.put("mother", mother);

                
                /* Gestione breadcrumb */

                    // Recupero del breadcrumb
                    NodeList breadcrumb = (NodeList)session.getAttribute("breadcrumb");
                    if(user_current.equals(user_logged)){
                        breadcrumb.clear();

                    }else{

                        Iterator iter = breadcrumb.iterator();
                        boolean remove = false;
                        while(iter.hasNext()){
                            treenode node = (treenode)iter.next();
                            if(!remove){
                                // Se l'utente corrente è uguale a quello nella lista
                                if(node.getUser().getId().equals(user_current.getId())){
                                    // Elimina tutti gli utenti successivi
                                    iter.remove();
                                    remove = true;
                                }
                            }else{
                                iter.remove();
                            }
                        }
                    }


                    breadcrumb.add(family_tree.getUser(user_current));


                    // Inserimento del nuovo breadcrumb nella variabile di sessione
                    session.setAttribute("breadcrumb", breadcrumb);
                    // Inserimento del breadcrumb nel data-model
                    data.put("breadcrumb", breadcrumb);
                
                
                
                //controllo dei messaggi
                Message message = new Message(request.getParameter("msg"), false);
                data.put("message", message);

                // Controllo richieste in arrivo
                int request_count = 0;
                try {
                    ResultSet record = user_logged.getRequests();
                    while (record.next()) {
                        request_count++;
                    }
                } catch (SQLException ex) {
                    request_count = 0;
                } finally {
                    data.put("request", request_count);
                }

                FreeMarker.process("profile.html", data, response, getServletContext());
            } else {

                StringBuffer requestURL = request.getRequestURL();
                if (request.getQueryString() != null) {
                    requestURL.append("?").append(request.getQueryString());
                }
                String completeURL = requestURL.toString();
                // Vai alla pagina di login e mostra messaggio di errore
                response.sendRedirect(completeURL);

            }

        } else {
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
        return "Servlet per la gestione del Profilo";
    }
}
