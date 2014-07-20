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

public class ProjectileWeapon extends Weapon implements Externalizable{
    
    private static final long serialVersionUID = 33145L;
    
    public ProjectileWeapon(){
        super();
    }
    public ProjectileWeapon(String name, int damage){
        super(name,damage);
    }
    public boolean isCompatible(Ammo ammo){
        return ammo instanceof Ammo;
    }
    @Override
    public String instanceDescription(){
        return "ProjectileWeapon;A weapon for firing projectiles;Recommened Use: Gun/Bow";
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
