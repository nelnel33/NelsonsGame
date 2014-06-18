package nelsontsui.nelsonsgame.game;
public class RandomDecision {

    public static final int RANGE = 100;
    public static final int MELEE = 200;
    public static final int DEFEND = 300;
    public static final int MOVE = 400;
    
    public RandomDecision(){}
    
    public static int makeDecision(){
        double rand = (int)(Math.random()*100);
        if(rand<=30){
            return MOVE;
        }
        else if(rand<=50){
            return RANGE;
        }
        else if(rand<=70){
            return DEFEND;
        }
        else{
            return MELEE;            
        }        
    }
    
    
}
