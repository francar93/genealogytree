package classi;

import java.util.LinkedHashSet;

/**
 *
 * @author matteocapodicasa
 */
public class listautenti extends LinkedHashSet<utente>{
    /**
     * Aggiungi un utente ad una lista
     * @param user  utente da aggiungere
     * @return      true se l'utente è stato aggiungo alla lista, false altrimenti
     */
    @Override
    public boolean add(utente user){
        if(user != null){
            return super.add(user);
        }
        return false;
    }
    
}
