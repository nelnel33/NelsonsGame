package nelsontsui.nelsonsgame.game;
public class Weapon extends UnusableItem {
    private int damage;
    private boolean equipped = false;
    public Weapon(){
        super("null",1);
        damage = 0;
    }
    public Weapon(String name, int damage){
        super(name,1);
        this.damage = damage;
    } 
    public int getDamage(){
        return damage;
    }
    public void setDamage(int damage){
        this.damage = damage;
    }
    public boolean getEquipped(){
        return equipped;
    }
    public void setEquipped(boolean e){
        equipped = e;
    }
}
