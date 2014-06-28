/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nelsontsui.nelsonsgame.leveleditor;

import java.awt.Point;
import java.util.ArrayList;

public class test {
    public static void main(String[] args){
        nelsontsui.nelsonsgame.game.Point p = decodeCoords("0.0,0.0");
        System.out.println(p.getX()+""+p.getY());
    }
    
    public static nelsontsui.nelsonsgame.game.Point decodeCoords(String s){
        double x;
        double y;
        String sx="";
        String sy="";
        int h=0;
        for(int i=0;i<s.length();i++){
            if(!(s.charAt(i)==',')){
                sx=sx+s.charAt(i);
            }
            else if(s.charAt(i)==','){
                h=i;
                break;
            }
        }
        for(int j=h+1;j<s.length();j++){
            sy = sy+s.charAt(j);
        }
        
        if(!(sx.equals("")||sy.equals(""))){
            x = Double.parseDouble(sx);
            y = Double.parseDouble(sy);
            return new nelsontsui.nelsonsgame.game.Point(x,y);
        }
        else{return null;}
    }
}
