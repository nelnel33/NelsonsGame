package nelsontsui.nelsonsgame.leveleditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import nelsontsui.nelsonsgame.game.entities.Character;
import nelsontsui.nelsonsgame.game.entities.Entity;
import nelsontsui.nelsonsgame.game.FileSelector;
import nelsontsui.nelsonsgame.game.mapping.Level;
import nelsontsui.nelsonsgame.game.entities.Player;

public class WriteFile {
    private String formalFileName;
    private Level level;
    private ArrayList<Entity> entities;
    private Player player;
    private File file;
    
    private File currentFile;
    
    ObjectOutputStream objectWriter;
    
    private int numberOfObjects;
    
    public static String EXTENSION = FileSelector.EXTENSION;
    public WriteFile(File file, Level level){
        this.level = level;
        this.entities = level.getEntities();
        this.player = level.getPlayer();     
        this.file = file;
        formalFileName = getFileName(file.getPath())+EXTENSION;     
        
        currentFile = new File(formalFileName);
    }
    public void write(){
        try{            
            objectWriter = new ObjectOutputStream(new FileOutputStream(formalFileName));
            
            numberOfObjects = entities.size()+1;
            objectWriter.writeInt(numberOfObjects);//amount of objects in the nel file
            System.out.println("Wrote "+numberOfObjects+" as Integer");
            
            objectWriter.writeObject(player);
            objectWriter.flush();
            if(!entities.isEmpty()){
                for(int i=0;i<entities.size();i++){
                    objectWriter.writeObject(entities.get(i));
                    objectWriter.flush();
                }
                System.out.println("Wrote "+(entities.size()+1)+" Objects");
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
