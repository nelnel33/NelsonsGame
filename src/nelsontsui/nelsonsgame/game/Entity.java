/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nelsontsui.nelsonsgame.game;

public class Entity{
    protected double x;
    protected double y;    
    protected double width;
    protected double height;
    protected int direction;
    
    protected static final int UP = 1;
    protected static final int DOWN = 2;
    protected static final int LEFT = 3;
    protected static final int RIGHT = 4;
    
    public Entity(double x, double y, double width, double height){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.direction = UP;
        
    }
    public void setX(double x){
        this.x=x;
    }
    public void setY(double y){
        this.y=y;
    }
    public void setDirection(int direction){
        this.direction = direction;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public int getDirection(){
        return direction;
    }
    public double getWidth(){
        return width;
    }
    public double getHeight(){
        return height;
    }
    public Hitbox getHitbox(){
        return new Hitbox(new Point(this.x,this.y), new Point(this.x+this.width,this.y+this.height));
    }
}
