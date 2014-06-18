package nelsontsui.nelsonsgame.game;
public class Armor extends UnusableItem{
    private int protection;
    private boolean equipped;
    public Armor(){
        super("null",1);
        protection = 0;
    }
    public Armor(String name, int protection){
        super(name,1);
        this.protection=protection;
    }
    public int getProtection(){
        return protection;
    }    
    public void setProtection(int protection){
        this.protection = protection;
    }
    public boolean getEquipped(){
        return equipped;
    }
    public void setEquipped(boolean e){
        equipped = e;
    }
}
