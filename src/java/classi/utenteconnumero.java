/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classi;

/**
 *
 * @author matteocapodicasa
 */
public class utenteconnumero {
   public utente relativo;
   public int parenti;
    
    public utenteconnumero(utente rel, int par){
        this.relativo=rel;
        this.parenti=par;
    }
    
    public int getNparent() {
        return this.parenti;
}
    public utente getrelat() {
        return this.relativo;
    }
    
}
