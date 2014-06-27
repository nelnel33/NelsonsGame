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
    private int numberOfRepeats;
    
    public TalkableGate(
            double x, 
            double y, 
            double width, 
            double height, 
            String speech,            
            int numberOfRepeats) {
        super(x, y, width, height, MapGate.REACH_GATE);
        this.speech = speech;
        this.numberOfRepeats = numberOfRepeats;
    }
    public String getSpeech(){
        return speech;
    }
    public int getNumberOfRepeats(){
        return numberOfRepeats;
    }
    public void setSpeech(String speech){
        this.speech = speech;
    }       
    public void setNumberOfRepeats(int n){
        this.numberOfRepeats = n;
    }
    public void decrementRepeats(){
        if(this.numberOfRepeats>0){
            this.numberOfRepeats--;
        }
    }
    public void incrementRepeats(){
        this.numberOfRepeats++;
    }
    @Override
    public boolean canOperate(Entity e){
        if((e.getHitbox().isTouching(this.getHitbox()))&&(numberOfRepeats>0)){
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
