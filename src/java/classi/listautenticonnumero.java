/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
