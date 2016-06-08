package classi;

import java.util.LinkedHashSet;

/**
 *
 * @author matteocapodicasa
 */
public class listautenticonnumero extends LinkedHashSet<utenteconnumero> {
     
    @Override
    public boolean add(utenteconnumero user){
        if(user != null){
            return super.add(user);
        }
        return false;
    }
    
}
