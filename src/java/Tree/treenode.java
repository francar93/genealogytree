/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tree;

import classi.utente;
import java.util.Objects;

/**
 *
 * @author caruso
 */

public final class treenode {
private final String label;
    private final utente user;

    public treenode(utente user, String label) {
        this.label = label;
        this.user = user;
    }

    public String getLabel() {
        return this.label;
    }

    public utente getUser() {
        return this.user;
    }

    public static String getNewLabel(String label, String relationship) {
        if (label.contains("Tu")) {
            return relationship;
        }

        // Se l'utente non ha una label, allora non l'avranno tutti gli utenti che aggiungerà all'albero
        if (label.equals("")) {
            return "";
        }

        // Se bisogna aggiungere un genitore di un nonno/a o un Figlio di un nipote,
        if (((label.contains("Bisnonna") || label.contains("Bisnonno")) && (relationship.equals("Madre") || relationship.equals("Padre")))
                || (((label.contains("Pronipote m") || label.contains("Pronipote f")) && (relationship.equals("Figlio") || relationship.equals("Figlia"))))) {

            // Ritorna la label originale sostituendo bis a tris
            return label.replace("Bisnonno", "Trisnonno");
            
        }

        String new_label = "";
        boolean in_law = false;

        // Se è un parente acquisito
        if (label.contains("Acquisito")) {
            // Elimina la dicitura "Acquisito"
            label = label.replace("Acquisito", "");
            // Segnala che è stata eliminata la dicitura "Acquisito" cosi da poterla riaggiungere in seguito
            in_law = true;
        }
        
        if (label.contains("Acquisita")) {
            // Elimina la dicitura "Acquisita"
            label = label.replace("Acquisita", "");
            // Segnala che è stata eliminata la dicitura "Acquisita" cosi da poterla riaggiungere in seguito
            in_law = true;
        }
        

        switch (relationship) {

            case "Padre":

                switch (label) {

                    case "Moglie":
                    case "Marito":
                        new_label = "Suocero";
                        break;

                    case "Padre":
                        new_label = "Nonno paterno";
                        break;

                    case "Madre":
                        new_label = "Nonno materno";
                        break;

                    case "Nonno paterno":
                    case "Nonna paterna":
                        new_label = "Bisnonno Paterno";
                        break;

                    case "Nonno materno":
                    case "Nonna materna":
                        new_label = "Bisnonno Materno";
                        break;
                }

                break;

            case "Madre":

                switch (label) {

                    case "Moglie":
                    case "Marito":
                        new_label = "Suocera";
                        break;

                    case "Padre":
                        new_label = "Nonna paterna";
                        break;

                    case "Madre":
                        new_label = "Nonna materna";
                        break;

                    case "Nonno paterno":
                    case "Nonna paterna":
                        new_label = "Bisnonna Paterna";
                        break;

                    case "Nonno materno":
                    case "Nonna materna":
                        new_label = "Bisnonna Materna";
                        break;
                }

                break;

            case "Moglie":

                switch (label) {

                    case "Figlio":
                        new_label = "Nuora";
                        break;

                    case "Fratello":
                        new_label = "Cognata";
                        break;

                    case "Zio Paterno":
                        new_label = "Zia Paterna Acquisita";
                        break;

                    case "Zio Materno":
                        new_label = "Zia Materna Acquisita";
                    case "Cognato":
                        new_label = "Cognata Acquisita";
                        break;

                    /*  
                        Se un padre deve inserire la popria moglie, 
                        allora suo Figlio non ha inserito la moglie del padre
                        ciò vuol dire che quest'ultima non è la madre naturale del Figlio
                     */
                    case "Padre":
                        new_label = "Matrigna";
                        break;
                }

                break;

            case "Marito":

                switch (label) {

                    case "Figlia":
                        new_label = "Genero";
                        break;

                    case "Sorella":
                        new_label = "Cognato";
                        break;

                    case "Zia Materna":
                        new_label = "Zio Materno Acquisito";
                        break;

                    case "Zia Paterna":
                        new_label = "Zio Paterno Acquisito";
                        break;

                    case "Cognata":
                        new_label = "Cognato Acquisito";

                    /*  
                        Se una madre deve inserire il proprio marito, 
                        allora suo Figlio non ha inserito il marito della madre
                        ciò vuol dire che quest'ultimo non è il padre naturale del Figlio
                     */
                    case "Madre":
                        new_label = "Patrigno";
                        break;
                }

                break;

            case "Fratello":

                switch (label) {

                    case "Marito":
                    case "Moglie":
                        new_label = "Cognato";
                        break;

                    case "Madre":
                        new_label = "Zio Materno";
                        break;

                    case "Nonno materno":
                        new_label = "Prozio Materno";
                        break;

                    case "Padre":
                        new_label = "Zio Paterno";
                        break;

                    case "Nonno paterno":
                        new_label = "Prozio Paterno";
                        break;

                }

                break;

            case "Sorella":

                switch (label) {

                    case "Marito":
                    case "Moglie":
                        new_label = "Cognata";
                        break;

                    case "Madre":
                        new_label = "Zia Materna";
                        break;

                    case "Nonno materno":
                        new_label = "Prozia Materna";
                        break;

                    case "Padre":
                        new_label = "Zia Paterna";
                        break;

                    case "Nonno paterno":
                        new_label = "Prozia Paterna";
                        break;

                }

                break;

            case "Figlio":
                
                switch (label) {
                    /*  
                        Se una utente deve inserire il Figlio del coniuge,
                        allora l'utente stesso non è stato inserito dal proprio Figlio
                        quindi l'utente che sta inserendo è il suo figliastro
                     */
                    case "Moglie":
                    case "Marito":
                        new_label = "Figliastro";
                        break;

                    case "Sorella":
                    case "Fratello":
                        new_label = "Nipote m";
                        break;

                    case "Nipote f":
                    case "Nipote m":
                        new_label = "Pronipote m";
                        break;

                    case "Zia Materna":
                    case "Zia Paterna":
                    case "Zio Materno":
                    case "Zio Paterno":
                        new_label = "cugino";
                        break;

                    case "Madre":
                    case "Padre":
                    case "Matrigna":
                    case "Patrigno":
                        new_label = "Fratellastro";
                        break;

                    case "Figlio":
                    case "Figlia":
                        new_label = "Nipote m";
                        break;

                    case "Pronipote f":
                    case "Pronipote m":
                        new_label = "Pro-Pronipote m";

                    case "Cognato":
                    case "Cognata":
                    case "Cognato Aquisito":
                    case "Cognata Aquisita":
                        new_label = "Nipote m Aquisito";
                        break;
                }

                break;

            case "Figlia":

                switch (label) {
                    /*  
                        Se un'utente deve inserire la figlia del coniuge,
                        allora l'utente stesso non è stato inserito dalla propria figlia
                        quindi l'utente che sta inserendo è la sua figliastra
                     */
                    case "Moglie":
                    case "Marito":
                        new_label = "Figliastra";
                        break;

                    case "Sorella":
                    case "Fratello":
                        new_label = "Nipote f";
                        break;

                    case "Nipote f":
                    case "Nipote m":
                        new_label = "Pronipote f";
                        break;

                    case "Zia Materna":
                    case "Zia Paterna":
                    case "Zio Materno":
                    case "Zio Paterno":
                        new_label = "cugina";
                        break;

                    case "Madre":
                    case "Padre":
                    case "Matrigna":
                    case "Patrigno":
                        new_label = "Sorellastra";
                        break;

                    case "Figlio":
                    case "Figlia":
                        new_label = "Nipote f";
                        break;

                    case "Pronipote m":
                        new_label = "Pro-Pronipote f";

                    case "Cognato":
                    case "Cognata":
                    case "Cognato Aquisito":
                    case "Cognata Aquisita":
                        new_label = "Nipote f Aquisita";
                        break;
                }

                break;
        }

        // Se è un parente acquisito, aggiungi la dicitura "Acquisito"
        if (in_law && !new_label.equals("") && !new_label.contains("Acquisito")) {
            new_label = new_label + " Acquisito";
        }
        
        if (in_law && !new_label.equals("") && !new_label.contains("Acquisita")) {
            new_label = new_label + " Acquisita";
        }

        return new_label;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.user);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final treenode other = (treenode) obj;

        return Objects.equals(this.user, other.user);
    }
}
