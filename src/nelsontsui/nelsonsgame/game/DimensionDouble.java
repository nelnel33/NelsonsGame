/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nelsontsui.nelsonsgame.game;

import java.io.Serializable;
public class DimensionDouble implements Serializable{
    protected double width;
    protected double height;
    public DimensionDouble(double width, double height){
        this.width = width;
        this.height = height;
    }
    public double getWidth(){
        return width;        
    }
    public double getHeight(){
        return height;
    }
    public void setWidth(double width){
        this.width = width;
    }
    public void setHeight(double height){
        this.height = height;
    }
}
