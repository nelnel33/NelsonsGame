package nelsontsui.nelsonsgame.leveleditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import nelsontsui.nelsonsgame.game.Character;
import nelsontsui.nelsonsgame.game.Entity;
import nelsontsui.nelsonsgame.game.FileSelector;
import nelsontsui.nelsonsgame.game.Level;
import nelsontsui.nelsonsgame.game.NonPlayerCharacter;
import nelsontsui.nelsonsgame.game.Player;

public class ReadFile {
    private String formalFileName;
    private ArrayList<Entity> entities;
    private Player player;
    private Level level;
    private File file;
    
    ObjectInputStream objectReader;
    
    private int numberOfObjects;
    
    public static String EXTENSION = FileSelector.EXTENSION;
    public ReadFile(File file){
        this.file = file;
        formalFileName = getFileName(file.getPath())+EXTENSION;
        entities = new ArrayList<>();
    }
    public void read(){
        try{
            objectReader = new ObjectInputStream(new FileInputStream(formalFileName));
            numberOfObjects = objectReader.readInt();
            System.out.println("Read "+numberOfObjects+" As Integer");
        
            for(int i=0;i<numberOfObjects;i++){
                Entity e = (Entity)objectReader.readObject();
                if((e instanceof Player)){
                    player = (Player)e;
                    }
                else{
                    entities.add(e);
                    }
                }
            System.out.println("Read "+(numberOfObjects)+" Objects");
            level = new Level(player,entities); 
        }
        catch(ClassNotFoundException e){
            System.out.println("Classnotfound"+e.getMessage());
        }
        catch(IOException e){
            System.out.println("IOEXCEPTION"+e.getMessage());
        }
    }
    public ArrayList<Entity> getEntities(){
        return entities;
    }
    public Player getPlayer(){
        return player;
    }
    public Level getLevel(){
        return level;
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
