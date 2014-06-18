/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nelsontsui.nelsonsgame.game;

/**
 *
 * @author Nelnel33
 */
public class StrengthPotion extends Potion{
    public StrengthPotion(String name, int quantity, double strength) {
        super(name, quantity, strength);
    }
    @Override
    public void ability(Character e){
        e.setDamage(e.getDamage()+super.getStrength());
    }
    
}
