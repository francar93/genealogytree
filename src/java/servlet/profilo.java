/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;


import Tree.genetree;
import Tree.treenode;
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
        if(session != null){
                genetree family_tree = (genetree)session.getAttribute("family_tree");
                // Recupero dell'utente loggato
                utente user_logged = (utente)session.getAttribute("user_logged");
                
                
                
                 boolean refresh = user_logged.checkFamilyTreeCache(session);
                if(!refresh){
                    
                    
                    utente user_current;
                    treenode user_current_node;
                    String relative_grade = null;
                    if (request.getParameter("id") != null){
                        user_current_node = family_tree.getUserById((String)request.getParameter("id"));
                        user_current = user_current_node.getUser();
                        relative_grade = user_current_node.getLabel();
                    } else {
                        user_current = user_logged;
                        relative_grade = "You";
                    }



                    /* Recupero dei parenti dell'utente corrente */

                    // Recupero del padre
                    //treenode father = null;
                    
                    treenode father = null;
                    try {
                        father = family_tree.getUser(user_current.getByParentela("padre"));
                    } catch (SQLException ex) {
                        Logger.getLogger(profilo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                   // utente prova = family_tree.prova();
                    
                    
                    /*
                    String forse= "il cazzo";
                    if(prova == null){
                        forse = "l'utente non c'è";
                    }else{
                        forse= "l'utente c'è";
                    }
                   */
                          
                    /*
                    treenode father1 = null;
                    try {
                        father1 = family_tree.getUserById(user_logged.getIdPadre());
                    } catch (SQLException ex) {
                        Logger.getLogger(profilo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    */
                    treenode io = null;

                    io = family_tree.getUser(user_logged);


                    
                    // Recupero della madre
                    treenode mother = null;
                    try {
                        mother = family_tree.getUser(user_current.getByParentela("madre"));
                    } catch (SQLException ex) { }
                    /*
                    // Recupero del coniuge
                    TreeNode spouse = null;
                    try {
                        spouse = family_tree.getUser(user_current.getRelative("spouse"));
                    } catch (SQLException ex) { }

                    // Recupero dei fratelli
                    NodeList siblings = null;
                    try {
                        siblings = family_tree.getUsers(user_current.getSiblings());
                    } catch (SQLException ex) { }

                    // Recupero dei figli
                    NodeList children = null;
                    try {
                        children = family_tree.getUsers(user_current.getChildren());
                    } catch (SQLException ex) { }
                    */

                    /* Inserimento dei parenti nel data-model */
                    
                    /*
                    data.put("user_logged", user_logged);
                    data.put("user_current", user_current);
                    data.put("relative_grade", relative_grade);

                    data.put("siblings", siblings);
                    data.put("children", children);

                    data.put("spouse", spouse);
                    
                    data.put("mother", mother);
                    */
                
                    //utente padre = father.getuser();
                   // data.put("forse", forse);
                    data.put("io", io);
                    data.put("father", father);
                    data.put("mother", mother);

                
            
            
            
            
                    FreeMarker.process("provastampadb.html", data, response, getServletContext());
                }
            
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
    }
}