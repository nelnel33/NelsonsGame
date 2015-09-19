package nelsontsui.nelsonsgame.game;

import nelsontsui.nelsonsgame.game.entities.Player;
import nelsontsui.nelsonsgame.game.mapping.Level;
import nelsontsui.nelsonsgame.game.entities.Entity;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import file.ReadFile;
import file.WriteFile;

public class FileSelector extends JFileChooser{
    public static final String newline = "\n";
    
    private Level level;    
    private ArrayList<Entity> entities = new ArrayList<>();
    private Player player;
    
    private String fileName;
    private File file;
    private JFileChooser fileSelector;
    private FileFilter filter;
    
    private int operation;
    private int response;
    
    public static final int SAVE = 10;
    public static final int IMPORT = 20;   
    
    public static final String EXTENSION = ".nel";
    
    public static final String home = System.getProperty("user.home");
    public static final String filesep = "/";
    
    public FileSelector(int operation, Level level){ 
        this.operation = operation;
        this.level = level;
        if(level == null){
            this.entities = new ArrayList();
            this.player = null;
        }
        else{
            this.entities = level.getEntities();
            this.player = level.getPlayer();
        }
        
        init();
        respondToAction();

        setSize(new Dimension(400,400));
        setVisible(true);
    }
    private void init(){    
        fileSelector = new JFileChooser(home);     
        filter = new FileNameExtensionFilter("NELSON Files","nel");
        fileSelector.setFileFilter(filter);
        
        if(operation == SAVE){
            response = fileSelector.showSaveDialog(FileSelector.this);
        }
        else if(operation == IMPORT){
            response = fileSelector.showOpenDialog(FileSelector.this);
        }
    }
    private void respondToAction(){
        if(response == JFileChooser.APPROVE_OPTION){
            file = fileSelector.getSelectedFile();
            fileName = fileSelector.getName(file); 
            if(operation == SAVE){
                file.delete();
                WriteFile write = new WriteFile(file,level);                
                write.write();
                System.out.println(level);
            }            
            if(operation == IMPORT){
                ReadFile read = new ReadFile(file);
                read.read();
                level = read.getLevel();
                entities = read.getEntities();
                player = read.getPlayer();
            }
            System.out.println(file);
            System.out.println(fileName);
        } 
    }

    public Level getLevel() {
        return level;
    }
    public ArrayList<Entity> getEntities(){
        return entities;
    }
    public Player getPlayer(){
        return player;
    }
    public File getFile(){
        return file;
    }
    public String getFileName(){
        return fileName;
    }
    public int getResponse(){
        return response;
    }
}
