package nelsontsui.nelsonsgame.game;
public class PortalGate extends Entity{
    private boolean canNpcUse;
    private boolean isHead;
    private boolean isTwoWay;
    private int linker;//corresponds with tail of the same linker
    public PortalGate(
                  double x, 
                  double y, 
                  double width, 
                  double height
                  ) {
        super(x, y, width, height);
        canNpcUse = false;   
        isHead = false;
    }
    public boolean canNpcUse(){
        return canNpcUse;
    }
    public void setNpcUse(boolean canNpcUse){
        this.canNpcUse = canNpcUse;
    }
    public void setLinker(int linker){
        this.linker = linker;
    }
    public int getLinker(){
        return linker;
    }
    public void setIsHead(boolean isHead){
        this.isHead = isHead;
    }
    public boolean getIsHead(){
        return isHead;
    }
    public void setIsTwoWay(boolean t){
        isTwoWay = t;
    }
    public boolean getIsTwoWay(){
        return isTwoWay;
    }
    public void teleport(Entity e){
        if(isTwoWay){
            if(e.getHitbox().isTouching(this.getHitbox())){
                if(this.getX()<GameDisplay.edgeX/2){
                    e.setX(this.getX()+this.getWidth()+Portal.offsetDistance);
                }
                else{
                    e.setX(this.getX()-this.getWidth()-Portal.offsetDistance);
                }
            }
        }
        else{
            if(isHead){
                if(e.getHitbox().isTouching(this.getHitbox())){
                    if(this.getX()<GameDisplay.edgeX/2){
                        e.setX(this.getX()+this.getWidth()+Portal.offsetDistance);
                    }
                    else{
                        e.setX(this.getX()-this.getWidth()-Portal.offsetDistance);
                    }
                }
            }
        }
    }
    
}
