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
       
       //controllo l'email e la passwore sul db: 
       
       if((email.equals("") && password.equals(""))||(email.equals(""))){
           
           try {
               /* Nel caso in cui i due campi sono vuoti, oppure è vuota la email
               * aggiungere un helper d'errore, ma comunque si rimane
               * nella pagina di login
               */
               //out.print("errore");
               if(password.equals("") && emailIn(email)){
                   /** Controllare se è stato registrato un utente da terzi con quella email,
                    *  perchè se così fosse bisogna permettere all'utente di effettuare una
                    *  vera e propria registrazione.
                    *  Possibile soluzione è quella di creare una form precompilata con i dati esistenti e far
                    *  registrare l'utente. la funzione emailIn si trova nel file Database e l'ho fatta io(Matteo)
                    **/
               }
           } catch (SQLException ex) {
               Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
           }
       }else{
            //qui ora bisogna controllare user e password sul db per vedere se ci sono oppure fare le effettive modifiche
             // da continuare mi sono fermato qui....
            // FRANCESCO 
            try {
                // da creare un metodo nella classa database
                ResultSet in;
           
           
                String tab = "utente";
           
                String query = "email=" + "'" + email + "'" + "AND password=" +  "'" + password +  "'" ; // aggiunta query FC
           
                in = Database.selectRecord(tab,query);
           
           
                if(in.next() == false){
               
                    response.sendRedirect("login");      
                    // se non fa match con il db torna a login, con messaggio di errore da aggiugnere
           
                }else{
               
                    response.sendRedirect("profilo");    
                    // qui per ora va a profilo che non esiste 
           }
        } catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
            // FRANCESCO 
           
       }
    }
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