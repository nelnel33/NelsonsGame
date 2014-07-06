/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nelsontsui.nelsonsgame.leveleditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import nelsontsui.nelsonsgame.game.Entity;
import nelsontsui.nelsonsgame.game.Character;
import nelsontsui.nelsonsgame.game.FileSelector;
import nelsontsui.nelsonsgame.game.NonPlayerCharacter;

public class ReadFile {
    private String formalFileName;
    private ArrayList<Entity> npcs = new ArrayList<>();
    private nelsontsui.nelsonsgame.game.Character player;
    private File file;
    
    ObjectInputStream objectReader;
    
    private int numberOfObjects;
    
    public static String EXTENSION = FileSelector.EXTENSION;
    public ReadFile(File file){
        this.file = file;
        formalFileName = getFileName(file.getPath())+EXTENSION;
    }
    public void read(){
        try{
        objectReader = new ObjectInputStream(new FileInputStream(formalFileName));
        numberOfObjects = objectReader.readInt();
        for(int i=0;i<numberOfObjects;i++){
            Entity e = (Entity)objectReader.readObject();
            if(!(e instanceof NonPlayerCharacter)
                    &&(e instanceof Character)){
                player = (Character)e;
                }
            else{
                npcs.add(e);
                }
            }
        }
        catch(ClassNotFoundException | IOException e){
            System.out.println(e.getMessage());
        }
    }
    public ArrayList<Entity> getNpcs(){
        return npcs;
    }
    public Character getPlayer(){
        return player;
    }
    private String getFileName(String s){
        String non="";//part that isn't extension
        String ext="";//extension
        int h = 0;
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)!='.'){
                non=non+s.charAt(i);
            }
            else if(s.charAt(i)=='.'){
                h=i;
                break;
            }
        }
        for(int j=h;j<s.length();j++){
            ext=ext+s.charAt(j);
        }
        
        return non;
    }
    
    
}
