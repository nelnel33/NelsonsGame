package nelsontsui.nelsonsgame.game.items;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import nelsontsui.nelsonsgame.game.entities.Character;
import nelsontsui.nelsonsgame.leveleditor.StringWrapper;

public class Item implements Externalizable{
    protected String name;
    protected int quantity;
    
    protected String abbreviation;
    
    private static final long serialVersionUID = 33141L;
    
    public Item(){
    }
    public Item(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
        init();
        createAbbreviation();
    }
    private void init(){
        if(quantity<1){
            quantity = 1;
        }
    }
    private void createAbbreviation(){
        if(name.length()<=8){
            abbreviation = name;
        }
        else{
            abbreviation = StringWrapper.createFormalAbbr(name,8);
        }
    }
    public String getName(){
        return name;
    }
    public int getQuantity(){
        return quantity;
    }
    public String getAbbreviation(){
        return abbreviation;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public void setAbbreviation(String abbreviation){
        this.abbreviation = abbreviation;
    }
    public void addQuantity(int quantity){
        this.quantity+=quantity;
    }
    public void useItem(Character e){
        if(quantity>0){
            quantity--;
        }
        ability(e);
    }
    public boolean itemDepleted(){
        if(quantity<=0){
            return true;
        }
        else{
            return false;
        }
    }
    public void ability(Character e){}//override if needed
    
    public String instanceDescription(){
        return "Item;A generic item;Recommened Use: Decoy item";
    }
    
    @Override
    public String toString(){
        return "Name: "+name+"; Type:"+this.getClass()+"; Quantity: "+quantity+";";
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeInt(quantity);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String)in.readObject();
        quantity = in.readInt();
        
        init();
        createAbbreviation();
    }
}
