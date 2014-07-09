package nelsontsui.nelsonsgame.game;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class OpaqueEntity extends Entity implements Externalizable{   
    
    private static final long serialVersionUID = 332L;
    
    public OpaqueEntity(){
        super();
    }
    public OpaqueEntity(double x, double y, double width, double height) {
        super(x, y, width, height);
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
