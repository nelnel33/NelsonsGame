package nelsontsui.nelsonsgame.game;


public class Hitbox{
    protected Point close;//top left corner; closer to origin.
    protected Point far;//bottom right corner; farther from origin
    protected static final int ABOVE=1;
    protected static final int BELOW=2;
    protected static final int LEFT=3;
    protected static final int RIGHT=4;
    protected static final int UNDETERMINED=0;
    public Hitbox(Point close, Point far){
        this.close = close;
        this.far = far;
    }
    public Point close(){
        return close;
    }
    public Point far(){
        return far;
    }
    public void close(Point close){
        this.close = close;
    }
    public void far(Point far){
        this.far = far;
    }  
    public boolean isAbove(Hitbox o){
        return this.far.y<=o.far.y;
    }
    public boolean isBelow(Hitbox o){
        return this.close.y>=o.close.y;
    }
    public boolean isRight(Hitbox o){
        return this.far.x<=o.far.x;
    }
    public boolean isLeft(Hitbox o){
        return this.close.x>=o.close.x;
    }
    public boolean isTouching(Hitbox o){
        if(this.close.y>o.far.y){//if thisy greater than othery then this is below o.            
            return false;
        }
        if(this.far.y<o.close.y){//if thisy less than othery then this is above o.
            return false;
        }
       
        if(this.close.x>o.far.x){//if thisx greater than otherx then this is toright o
            return false;
        }
        if(this.far.x<o.close.x){//if thisx less than otherx then this is toleft o.
            return false;
        }
        else{
            return true;
        }
    }

        
}