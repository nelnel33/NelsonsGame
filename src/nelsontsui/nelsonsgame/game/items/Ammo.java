/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nelsontsui.nelsonsgame.game.items;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Ammo extends UnusableItem implements Externalizable{
    
    public static final String CLASSNAME = "Ammo";
    public static final String DESCRIPTION = "Ammo for a Projectile Weapon";
    public static final String FILENAME = "ammo";
    
    private static final long serialVersionUID = 33146L;
    
    public Ammo(){
        super();
    }
    public Ammo(String name, int quantity) {
        super(name, quantity);
    }   
    
    @Override
    public String instanceDescription(){
        return "Ammo;Ammunition for a Projectile Weapon;Recommended Use: Arrows;";
    }
    
    @Override
    public String toString(){
        return super.toString();
    }
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
    }
}
