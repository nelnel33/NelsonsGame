package nelsontsui.nelsonsgame.game.items;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class UnusableItem extends Item{
    
    private static final long serialVersionUID = 33142L;
    
    public UnusableItem(){
        super();
    }    
    public UnusableItem(String name, int quantity) {
        super(name, quantity);
    }
    @Override
    public String toString(){
        return super.toString();
    }
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
    }
    
}
