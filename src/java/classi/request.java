/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classi;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author matteocapodicasa
 */

public final class request {
    private final utente sender;
    private final utente receiver;
    private final String relationship;
    
    public request(utente sender, utente receiver, String relationship){
        this.sender = sender;
        this.receiver = receiver;
        this.relationship = relationship;
    }
    
    public request(String sender_id, String receiver_id, String relationship){
        this.sender = utente.getUserById(sender_id);
        this.receiver = utente.getUserById(receiver_id);
        this.relationship = relationship;
    }
    
    public request(ResultSet request) throws SQLException{
        this.sender = utente.getUserById(request.getString("idreciver"));
        this.receiver = utente.getUserById(request.getString("idsender"));
        this.relationship = request.getString("relazione");
    }
    
    public utente getSender(){
        return this.sender;
    }
    
    public utente getReceiver(){
        return this.receiver;
    }
    
    public String getRelationship(){
        return this.relationship;
    }
}