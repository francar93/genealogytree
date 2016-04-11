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
import java.net.URLEncoder;
import java.sql.ResultSet;
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
import static utilita.Database.emailIn;
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
        if(session == null){
        
            Map<String, Object> data = new HashMap<>();
      
            String action = (String) request.getAttribute("action");
            // Se l'azione non è stata definita o non è valida, impostala come l'azione di login
            if(action == null || (action.equals("login") && action.equals("signup"))) action = "login";
            // Inserisci l'azione nel data-model
            data.put("action", action);
            
            data.put("message", new Message(request.getParameter("msg"), true));

            data.put("script", "login");

            FreeMarker.process("login.html", data, response, getServletContext());
        }else{
            
                //vai alla pagina del profilo(DA IMPLEMENTARE)

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
       String email    = request.getParameter("email");
       String password = request.getParameter("password");
      /*
       Prova che ho fatto io (Matteo)
       Map<String, Object> data = new HashMap<>();
       
       data.put("email", email);
       data.put("password", password);
       
       FreeMarker.process("stampa.html", data, response, getServletContext());
       */
      
       //controllo l'email e la passwore sul db: 
       
       //Controllo. Si tratta di una richiesta AJAX?
        //boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
        //boolean quick_signup = false;
        String msg = null;
        boolean error = true;
       
      
       if(email.equals("") || password.equals("")){
           
           msg ="usr_1";
           
               // Nel caso in cui i due campi sono vuoti, oppure è vuota la email
               //aggiungere un helper d'errore, ma comunque si rimane
               //nella pagina di login
               
       }    
       
             
       try {
       if(password.equals("") && emailIn(email)){
                   //Controllare se è stato registrato un utente da terzi con quella email,
                   //perchè se così fosse bisogna permettere all'utente di effettuare una
                   //vera e propria registrazione.
                   //Possibile soluzione è quella di creare una form precompilata con i dati esistenti e far
                   //registrare l'utente. la funzione emailIn si trova nel file Database e l'ho fatta io(Matteo)
                   
       }
       } catch (SQLException ex) {
               Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
           }
       
            //qui ora bisogna controllare user e password sul db per vedere se ci sono oppure fare le effettive modifiche
             // da continuare mi sono fermato qui....
            // FRANCESCO 
            try {
                // da creare un metodo nella classa database
                ResultSet in = Database.controlloEmailPassword(email,password);
           
                if(in.next() == false){
                    if(email.equals("") || password.equals("")){
                        msg="usr_1";
                    }else{
                    
                    msg ="usr_3";
                    }
                    //response.sendRedirect("login?msg=" + URLEncoder.encode(msg, "UTF-8"));
                    //response.sendRedirect("login");      
                    // se non fa match con il db torna a login, con messaggio di errore da aggiugnere
           
                }else{
                    error = false;
                    msg ="usr_4";
                    //response.sendRedirect("login?msg=" + URLEncoder.encode(msg, "UTF-8"));
                    
                    
                    // qui per ora va a profilo che non esiste 
           }
        } catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
            // FRANCESCO 
           
       }
            if(error){
                response.sendRedirect("login?msg=" + URLEncoder.encode(msg, "UTF-8"));
            }else{
                response.sendRedirect("login?msg=" + URLEncoder.encode(msg, "UTF-8"));
            }
            
       
}
            
//}

 
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