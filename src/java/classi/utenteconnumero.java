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
