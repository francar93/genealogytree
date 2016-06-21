package Tree;


import classi.utente;
import java.util.LinkedList;

/**
 *
 * @author matteocapodicasa
 */
public class NodeList extends LinkedList<treenode>{
     /** Verifica se in una lista di nodi è presente un utente
     *
     * @param user
     * @return 
     */ 
    public boolean contains(utente user){
        for(treenode element: this){
            if(element.getUser().equals(user)) return true;
        }
        return false;
    }
}
