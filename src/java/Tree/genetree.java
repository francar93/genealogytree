/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tree;

import java.sql.SQLException;
import classi.listautenti;
import classi.utente;

/**
 *
 * @author matteocapodicasa
 */
public class genetree {
    
    // Contiene l'albero genealogico
    private final NodeList family_tree;

    public NodeList getFamily_tree() {
        return family_tree;
    }
    
    public genetree(utente user){
        this.family_tree = new NodeList();
        this.family_tree.add(new treenode(user, "You")); 
    }
    
    /**
     * Restiruisci l'intero albero genealogico con utenti etichettati
     * @return
     * @throws java.sql.SQLException
     */
    public NodeList getFamilyTree() throws SQLException {
        
        // Crea albero genealogico
        this.createTree(0);
        
        /* Successivamene non ci sarà bisogno di creare tutto l'albero già dall'inizo,
            ma si creerà inizialmente solo il nucleo familiare dell'utente, 
            che poi verrà ampliato in base al parente scelto
        */
        
        return this.family_tree;
    }
    
    private void createTree(int index) throws SQLException {
        
        /** 
        *   ORDINE DI INSERIMENTO:
        *   - Genitori
        *   - Figli
        *   - Fratelli/sorelle
        *   - Coniuge
        
            N.B.: l'ordine con cui vengono inseriti i parenti è importante
            Esempio: dopo aver aggiunto i propri figli, 
                    i figli che aggiungerà il coniuge saranno sicuamente figliastri,
                    per cui è importante aggiungere prima i figli di un'utente e poi il coniuge
        */

        // Se non ci sono più utenti da analizzare
        if(index >= this.family_tree.size()) return;
        
        // Recupera il prossimo user da analizzare con relativa etichetta
        utente user = this.family_tree.get(index).getUser();
        String label = this.family_tree.get(index).getLabel();
        
        // Aggiungi la madre
        this.add(user.getByParentela("madre"), label, "mother");
        // Aggiungi il padre
        //da non mettere this.family_tree.add(new treenode(user.getRelative("father"), "father"));
        
        this.add(user.getByParentela("padre"), label, "father");
        
        // Aggiungi i figlie/figlie
        for(utente child: user.getFigli()){
            if(child.getSesso().equals("M")){
                // Aggiungi i figli
                this.add(child, label, "son");
            }else{
                // Aggiungi le figlie
                this.add(child, label, "daughter");
            }
        }
        
        // Aggiungi i fratelli/sorelle
        for(utente sibling: user.getFratelliSorelle()){
            if(sibling.getSesso().equals("M")){
                // Aggiungi i figli
                this.add(sibling, label, "brother");
            }else{
                // Aggiungi le figlie
                this.add(sibling, label, "sister");
            }
        }

        if(user.getSesso().equals("F")){
            // Aggiungi la moglie
            this.add(user.getByParentela("moglie"), label, "husband");
        }else{
            // Aggiungi il marito
            this.add(user.getByParentela("marito"), label, "wife");
        }
        
        // Valuta il prossimo utente
        createTree(++index);
        
    }
    private void add(utente new_relative, String label, String degree){
        if(new_relative != null){
            // Se l'utente non è stato ancora valutato
            if(!this.family_tree.contains(new_relative)){
                String new_label = treenode.getNewLabel(label, degree);
                this.family_tree.add(new treenode(new_relative, new_label));
            }
        }
    }
    private void addAll(listautenti list, String label, String degree){
        for (utente element : list) {
            add(element, label, degree);
        }
    }
    
    /**
     * Restiruisci solo il nucleo familiare di un utente presente nell'albero genalogico
     * @param user
     * @return
     */
    public NodeList getFamily(utente user) throws SQLException {
        
        NodeList family = new NodeList();
        
        // Recupero i membri del nucleo familiare
        listautenti relatives = new listautenti();
        relatives.addAll(user.getGenitori());
        relatives.addAll(user.getFratelliSorelle());
        relatives.addAll(user.getFigli());
        relatives.add(user.getByParentela("partner"));
        
        // Per ogni membro del nucleo familiare, recupero il nodo dell'albero corrispodente
        for(utente relative: relatives){
            for(treenode node: this.family_tree){
                if(relative.getId().equals(node.getUser().getId())){
                    family.add(node);
                }
            }
        }
        // Restituisco il nucleo familiare con utenti etichettati
        return family;
        
        
        
    }   
    
    public utente prova(){
        if(this.family_tree.get(1).getUser() != null){
            return this.family_tree.get(1).getUser();
        }
        return null;
    }
    
    public treenode getUser(utente user){
        for(treenode element: this.family_tree){
            if(element.getUser().equals(user)) return element;
        }
        return null;
    }
    
    public treenode getUserById(String id){
        for(treenode element: this.family_tree){
            if(element.getUser().getId().equals(id)) return element;
        }
        return null;
    }
    
    public NodeList getUsers(listautenti users){
        NodeList relatives = new NodeList();
        for(utente relative: users){
            for(treenode element: this.family_tree){
                if(element.getUser().equals(relative)) relatives.add(element);
            }
        }
        
        return relatives;
    }

}
