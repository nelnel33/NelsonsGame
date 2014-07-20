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
