/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nelsontsui.nelsonsgame.game;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import nelsontsui.nelsonsgame.leveleditor.ReadFile;
import nelsontsui.nelsonsgame.leveleditor.WriteFile;

public class FileSelector extends JFileChooser{
    public static final String newline = "\n";
    
    ArrayList<Entity> npcs = new ArrayList<>();
    Character player;
    
    private String fileName;
    private File file;
    private JFileChooser fileSelector;
    private FileFilter filter;
    
    private int operation;
    private int response;
    
    public static final int SAVE = 10;
    public static final int IMPORT = 20;
    public static final String EXTENSION = ".nel";
    public FileSelector(int operation, ArrayList<Entity> npcs, Character player){ 
        this.operation = operation;
        this.npcs = npcs;
        this.player = player;
        
        init();
        respondToAction();

        setSize(new Dimension(400,400));
        setVisible(true);
    }
    private void init(){    
        fileSelector = new JFileChooser();     
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
                WriteFile write = new WriteFile(file,npcs,player);                
                write.write();
            }            
            if(operation == IMPORT){
                ReadFile read = new ReadFile(file);
                read.read();
                npcs = read.getNpcs();
                player = read.getPlayer();
            }
            System.out.println(file);
            System.out.println(fileName);
        } 
    }
    public ArrayList<Entity> getNpcs(){
        return npcs;
    }
    public Character getPlayer(){
        return player;
    }
    public File getFile(){
        return file;
    }
    public String getFileName(){
        return fileName;
    }
}
