package nelsontsui.nelsonsgame.game;
public class Potion extends Item{
    private double strength;
    public Potion(String name,
                  int quantity,
                  double strength){
        super(name,quantity);
        this.strength = strength;
    }
    public double getStrength(){
        return strength;
    }
    public void setStrength(double strength){
        this.strength = strength;
    }

    
}
