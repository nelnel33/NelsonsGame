/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nelsontsui.nelsonsgame.game;

import java.util.ArrayList;

public class MapGate extends Entity{
    private int useCondition;
    private boolean canUse;
    
    public static final int KILL_ALL = 0;
    public static final int REACH_GATE = 1;
    public MapGate(double x, double y, double width, double height, int useCondition) {
        super(x, y, width, height);
        this.useCondition = useCondition;
        assignCanUse();
    }
    private void assignCanUse(){
        if(useCondition==REACH_GATE){
            canUse = true;
        }
        else{
            canUse = false;
        }
    }
    public void checkIfCanUse(ArrayList<Entity> e){
        if(useCondition==KILL_ALL){
            for(int i=0;i<e.size();i++){
                if(e.get(i) instanceof NonPlayerCharacter){
                    canUse = false;
                }
            }
        }
    }
    public boolean canOperate(Entity e){
        if((e.getHitbox().isTouching(this.getHitbox()))&&canUse){
            return true;
        }
        else{
            return false;
        }
    }
    public void operate(Entity e){
        if((e.getHitbox().isTouching(this.getHitbox()))&&canUse){
            setOperation(e);
        }
    }
    public void setOperation(Entity e){

    }
    
}
