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
import nelsontsui.nelsonsgame.game.entities.Character;

public class HealthPotion extends Potion implements Externalizable{
    
    private static final long serialVersionUID = 33149L;
    
    public HealthPotion(){
        super();
    }
    public HealthPotion(String name, int quantity, double strength) {
        super(name, quantity, strength);
    }
    @Override
    public void ability(Character e){
        if(e.getHitpoints()+super.getStrength()<=e.getinitHitpoints()+e.getArmorHitpoints()){
            e.heal(super.getStrength());
        }
        else{
            if(super.getStrength()>=e.getinitHitpoints()+e.getArmorHitpoints()-e.getHitpoints())
                e.heal((e.getinitHitpoints()+e.getArmorHitpoints())-e.getHitpoints());
            }
    }
    
    @Override
    public String instanceDescription(){
        return "HealthPotion;Used to heal the player;Recommened Use: Food/Potions;";
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
