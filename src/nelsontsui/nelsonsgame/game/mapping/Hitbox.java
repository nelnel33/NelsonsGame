package nelsontsui.nelsonsgame.game.mapping;

import nelsontsui.nelsonsgame.game.mapping.Point;


public class Hitbox{
    public Point close;//top left corner; closer to origin.
    public Point far;//bottom right corner; farther from origin
    
    public static final int RANGE = 3;//buffer for collision detection.
    
    public static final int ABOVE=1;
    public static final int BELOW=2;
    public static final int LEFT=3;
    public static final int RIGHT=4;
    public static final int UNDETERMINED=0;
    public Hitbox(Point close, Point far){
        this.close = close;
        this.far = far;
    }
    public Point getClose(){
        return close;
    }
    public Point getFar(){
        return far;
    }
    public void setClose(Point close){
        this.close = close;
    }
    public void setFar(Point far){
        this.far = far;
    }  
    public boolean isAccuratelyAbove(Hitbox o){
        return !isBelow(o);
    }
    public boolean isAccuratelyBelow(Hitbox o){
        return !isAbove(o);
    }
    public boolean isAccuratelyRight(Hitbox o){
        return !isLeft(o);
    }
    public boolean isAccuratelyLeft(Hitbox o){
        return !isRight(o);
    }
    public boolean isStrictlyAbove(Hitbox o){
        //returns true <-> your hitbox's bottom is less than other hitbox's top
        return (this.far.y<=o.close.y)||(this.far.y<=o.close.y+1)||
            isBetween(this.far.y, o.close.y, o.close.y-RANGE);
    }
    public boolean isStrictlyBelow(Hitbox o){
        //return true <-> your hitbox's top is greater than other hitbox's bottom
        return (this.close.y>=o.far.y)||(this.close.y>=o.far.y-1)||
            isBetween(this.close.y, o.far.y, o.far.y+RANGE);
    }
    public boolean isStrictlyLeft(Hitbox o){
        //return true <-> your hitbox's right is less than other hitbox's left
        return (this.far.x<=o.close.x)||(this.far.x<=o.close.x-1)||
            isBetween(this.far.x, o.close.x, o.close.x-RANGE);
    }
    public boolean isStrictlyRight(Hitbox o){
        //return true <-> your hitbox's left is greater than other hitbox's right
        return (this.close.x>=o.far.x)||(this.close.x>=o.far.x+1)||
            isBetween(this.close.x, o.far.x, o.far.x+RANGE);
    }    
    public boolean isBetween(double num, double min, double max){
        return num>=min && num <=max;
    }
    public boolean isAbove(Hitbox o){
        //return true <-> your hitbox's bottom is less than other hitbox's bottom
        return this.far.y<=o.far.y;
    }
    public boolean isBelow(Hitbox o){
        //return true <-> your hitbox's top is greater than other hitbox's top
        return this.close.y>=o.close.y;
    }
    public boolean isRight(Hitbox o){
        //return true <-> your hitbox's left is greater than other hitbox's left
        return this.far.x<=o.far.x;
    }
    public boolean isLeft(Hitbox o){
        //return true <-> your hitbox's right is less than other hitbox's right
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
    
    public boolean equals(Hitbox h){
        return close.equals(h.close) && far.equals(h.far);
    }
    
    @Override
    public String toString(){
        return "Close: ("+this.close.x+","+this.close.y+") Far: ("+this.far.x+","+this.far.y+"); ";
    }

        
}