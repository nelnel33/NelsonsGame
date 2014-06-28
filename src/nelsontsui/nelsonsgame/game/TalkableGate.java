/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nelsontsui.nelsonsgame.game;

import java.util.ArrayList;
import static nelsontsui.nelsonsgame.game.MapGate.KILL_ALL;

/**
 *
 * @author Nelnel33
 */
public class TalkableGate extends MapGate {
    private String speech;
    private boolean flag = true;
    public TalkableGate(
            double x, 
            double y, 
            double width, 
            double height, 
            String speech         
            ){
        super(x, y, width, height, MapGate.REACH_GATE);
        this.speech = speech;
    }
    public String getSpeech(){
        return speech;
    }
  
    public void setSpeech(String speech){
        this.speech = speech;
    }       
    @Override
    public boolean canOperate(Entity e){
        if((e.getHitbox().isTouching(this.getHitbox()))&&flag){
            flag = false;
            return true;            
        }
        else{
            return false;
        }
    }
    @Override
    public void setOperation(Entity e){
        //does nothing
    }
    
}
