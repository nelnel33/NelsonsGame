/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nelsontsui.nelsonsgame.game;
public class Projectile extends Entity{
    private double speed;
    private int direction;
    private boolean hasBeenFired;
    public Projectile(double x, double y, double width, double height, double speed, int direction){
        super(x,y,width,height);
        this.speed=speed;
        this.direction = direction;
        hasBeenFired = false;
    }
    public void setSpeed(double speed){
        this.speed=speed;
    }
    public double getSpeed(){
        return speed;
    }    
    public void fire(){
        if(direction==Entity.UP){
            y-=speed;
        }
        if(direction==Entity.DOWN){
            y+=speed;
        }
        if(direction==Entity.LEFT){
            x-=speed;
        }
        if(direction==Entity.RIGHT){
            x+=speed;
        }
        hasBeenFired = true;
    }
    public boolean hasBeenFired(){
        return hasBeenFired;
    }
}
