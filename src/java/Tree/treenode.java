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
 * @author carus
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
                || (((label.contains("Pronipote") || label.contains("Pronipote")) && (relationship.equals("Figlio") || relationship.equals("Figlia"))))) {

            // Ritorna la label originale aggiungendo solamente un altro "great-"
            return label.replace("great-", "great-great-");
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

        switch (relationship) {

            case "Padre":

                switch (label) {

                    case "Moglie":
                    case "Marito":
                        new_label = "PadreAcquisito";
                        break;

                    case "Padre":
                        new_label = "Nonno paterno";
                        break;
                    case "Madre":
                        new_label = "Nonno materno";
                        break;

                    case "Nonno paterno":
                    case "Nonna paterna":
                        new_label = " Paterno Bisnonno";
                        break;

                    case "Nonno materno":
                    case "Nonna materna":
                        new_label = "Materno Bisnonno";
                        break;
                }

                break;

            case "Madre":

                switch (label) {

                    case "Moglie":
                    case "Marito":
                        new_label = "MadreAcquisito";
                        break;

                    case "Padre":
                        new_label = "Nonna paterna";
                        break;
                    case "Madre":
                        new_label = "Nonna materna";
                        break;

                    case "Nonno paterno":
                    case "Nonna paterna":
                        new_label = "Paterno Bisnonna";
                        break;

                    case "Nonno materno":
                    case "Nonna materna":
                        new_label = "Materno Bisnonna";
                        break;
                }

                break;

            case "Moglie":

                switch (label) {
                    case "Figlio":
                        new_label = "FigliaAcquisito";
                        break;
                    case "Fratello":
                        new_label = "SorellaAcquisito";
                        break;
                    case "Paterno Zio":
                        new_label = "Paterno Zia Acquisito";
                        break;
                    case "Materno Zio":
                        new_label = "Materno Zia Acquisito";
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
                        new_label = "Figlio Acquisito";
                        break;
                    case "Sorella":
                        new_label = "Fratello Acquisito";
                        break;
                    case "Materno Zia":
                        new_label = "Materno Zio Acquisito";
                        break;
                    case "Paterno Zia":
                        new_label = "Paterno Zio Acquisito";
                        break;

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
                        new_label = "Fratello Acquisito";
                        break;

                    case "Madre":
                        new_label = "Materno Zio";
                        break;
                    case "Nonno materno":
                        new_label = "Materno Prozio";
                        break;

                    case "Padre":
                        new_label = "Paterno Zio";
                        break;
                    case "Nonno paterno":
                        new_label = "Paterno Prozio";
                        break;

                }

                break;

            case "Sorella":

                switch (label) {

                    case "Marito":                         // cognato
                    case "Moglie":
                        new_label = "SorellaAcquisito";
                        break;

                    case "Madre":
                        new_label = "Materno Zia";
                        break;
                    case "Nonno materno":
                        new_label = "Materno Prozia";
                        break;

                    case "Padre":
                        new_label = "Paterno Zia";
                        break;
                    case "Nonno paterno":
                        new_label = "Paterno Prozia";
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

                    case "Materno Zia":
                    case "Paterno Zia":
                    case "Materno Zio":
                    case "Paterno Zio":
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
                        new_label = "Pronipote m";
                        break;

                    case "Pronipote f":
                    case "Pronipote m": 
                        new_label = "Prp-Pronipote m";
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
                    case "Fratello ":
                        new_label = "Nipote f";
                        break;

                    case "Nipote f":
                    case "Nipote m":
                        new_label = "Pronipote f";
                        break;

                    case "Materno Zia":
                    case "Paterno Zia":
                    case "Materno Zio":
                    case "Paterno Zio":
                        new_label = "cugino";
                        break;

                    case "Madre":
                    case "Padre":
                    case "Matrigna":
                    case "Patrigno":
                        new_label = "Sorellastra";
                        break;

                    case "Figlio":
                    case "Figlia":
                        new_label = "Pronipote";
                        break;

                    case "Pronipote":
                    new_label = "Pro-Pronipote";
                        break;
                }

                break;
        }

        // Se è un parente acquisito, aggiungi la dicitura "Acquisito"
        if (in_law && !new_label.equals("") && !new_label.contains("Acquisito")) {
            new_label = new_label + "Acquisito";
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
