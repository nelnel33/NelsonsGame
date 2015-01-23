package nelsontsui.nelsonsgame.game;

import javax.swing.Timer;

public class WinLose{
    public WinLose(){}
    public boolean hasWon(){
        //unknown -- checks for MapGate Operation in ActionPanel.
        //implemented in MapGate Class
        throw new UnsupportedOperationException("No operation currently -- unknown if method will be implemented");        
    }
    public boolean hasLost(Character p){
        if(p.getHitpoints()<=0){
            System.out.println("hp<=0; lost");
            return true;
        }
        else{
            return false;
        }
    }
    public void operate(Character p, Timer t, DialogBox d){
        if(hasLost(p)){
            System.out.println("lose confirm.");
            d.message(MainDisplay.loseMessageCruel);
            d.message(MainDisplay.genMessage);
            t.stop();            
        }
    }
}
