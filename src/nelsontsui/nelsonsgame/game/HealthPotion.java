/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nelsontsui.nelsonsgame.game;
public class HealthPotion extends Potion{
    public HealthPotion(String name, int quantity, double strength) {
        super(name, quantity, strength);
    }
    @Override
    public void ability(Character e){
    if(e.getHitpoints()+super.getStrength()<=e.getinitHitpoints()){
        e.heal(super.getStrength());
        }
    }
}
