/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nelsontsui.nelsonsgame.leveleditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import nelsontsui.nelsonsgame.game.Character;
import nelsontsui.nelsonsgame.game.Entity;
import nelsontsui.nelsonsgame.game.FileSelector;

public class WriteFile {
    private String formalFileName;
    private ArrayList<Entity> npcs;
    private Character player;
    private File file;
    
    private File currentFile;
    
    ObjectOutputStream objectWriter;
    
    private int numberOfObjects;
    
    public static String EXTENSION = FileSelector.EXTENSION;
    public WriteFile(File file, ArrayList<Entity> npcs, Character player){
        this.npcs = npcs;
        this.player = player;     
        this.file = file;
        formalFileName = getFileName(file.getPath())+EXTENSION;     
        
        currentFile = new File(formalFileName);
    }
    public void write(){
        try{            
            objectWriter = new ObjectOutputStream(new FileOutputStream(formalFileName));
            
            numberOfObjects = npcs.size()+1;
            objectWriter.writeInt(numberOfObjects);//amount of objects in the nel file
            System.out.println("Wrote "+numberOfObjects+" as Integer");
            
            objectWriter.writeObject(player);
            objectWriter.flush();
            if(!npcs.isEmpty()){
                for(int i=0;i<npcs.size();i++){
                    objectWriter.writeObject(npcs.get(i));
                    objectWriter.flush();
                    //System.out.println("Wrote "+npcs.size()+" Objects");
                }
            }
            objectWriter.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Unable to write file:"+formalFileName);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
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
