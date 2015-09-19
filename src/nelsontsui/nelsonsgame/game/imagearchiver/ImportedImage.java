/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nelsontsui.nelsonsgame.game.imagearchiver;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;

/**
 *
 * @author Nelnel33
 */
public class ImportedImage {
    private BufferedImage loadedImage;
    
    private BufferedImage desiredImage;
    private int x, y, width, height;
    
    public static final String PATH = "/nelsontsui/nelsonsgame/game/resources/";
    public static final String PNG = ".png";
    
    public static final String ITEMS_DIR = "items";
    public static final String ENTITIES_DIR = "entities";    
    
    private String resource;
    
    public ImportedImage(String resource, String directory){             
        String fullPath, name;
        if(directory == null){
            fullPath = PATH+resource+PNG;
            name = resource+PNG;
        }
        else{
            fullPath = PATH+directory+"/"+resource+PNG;
            name = resource+PNG;
        }
        
        try {
            this.resource = resource;
            loadedImage = ImageIO.read(getClass().getResource(fullPath));
        } catch (IOException ex) {
            System.err.println(fullPath+" could not be loaded." +" "+name+ " will be null.");
        }
    }
    
    public String getResource(){
        return resource;
    }
    
    public BufferedImage getDesiredImage(){
        if(desiredImage == null){
            System.err.println("No desired image has been created");
        }
        return desiredImage;
    }
    
    public BufferedImage createDesiredImage(){
        return createDesiredImage(0,0,loadedImage.getWidth(), loadedImage.getHeight());
    }
    
    public BufferedImage createDesiredImage(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        desiredImage = loadedImage.getSubimage(x, y, width, height);
        
        return desiredImage;
    }    
    
    public void drawDesiredImage(Graphics2D g, int x, int y){
        g.drawImage(loadedImage, x, y, width, height, null);
    }
    
    public void drawDesiredImage(Graphics2D g, int x, int y, int width, int height){
        g.drawImage(loadedImage, x, y, width, height, null);
    }
    
    public void drawTiledImage(Graphics2D g, int x, int y, int width, int height, int tileSize){
        int rw = width; //width of the tiledImaged
        int intrw = rw/tileSize; //how many rows u need to draw of the desiredImage
        int diffw = rw%tileSize;
                
        int rh = height; //height of the tiledImage
        int intrh = rh/tileSize; //how many col you need to draw the desiredImage 
        int diffh = rh%tileSize;
        
        for(int xx = x;xx<width-tileSize; xx+=tileSize){
            for(int yy = y; yy<height-tileSize;yy+=tileSize){
                g.drawImage(desiredImage, xx, yy, tileSize, tileSize, null);
            }
        }
        
        if(diffw!=0){
            //draw the excess of rows
            BufferedImage subDesiredImage = desiredImage.getSubimage(0, 0, 
                    (int)proportionToImage(diffw, desiredImage.getWidth(), tileSize), 
                    desiredImage.getHeight());
            
            for(int yy = y; yy<height-tileSize;yy+=tileSize){
                g.drawImage(subDesiredImage, x+width-diffw, yy, diffw, tileSize, null);
            }            
        }
        
        if(diffh!=0){
            //draw the excess of cols
            BufferedImage subDesiredImage = desiredImage.getSubimage(0, 0, 
                    desiredImage.getWidth(), 
                    (int)proportionToImage(diffh, desiredImage.getHeight(), tileSize));

            
            for(int xx = x; xx<width-tileSize;xx+=tileSize){
                g.drawImage(subDesiredImage, xx, y+height-diffh, tileSize, diffh, null);
                //g.drawImage(subDesiredImage, x, y+height-diffh, tileSize, 35, null);
            }
        }
        
        if(diffh!=0 && diffw!=0){
            //draw puny square on bottom right corner
            BufferedImage subDesiredImage = desiredImage.getSubimage(0,0, 
                    (int)proportionToImage(diffw, desiredImage.getWidth(), tileSize),
                    (int)proportionToImage(diffh, desiredImage.getHeight(), tileSize));
            
            
            g.drawImage(subDesiredImage,x+width-diffw,y+height-diffh, diffw, diffh, null);
        }
    }
    
    private double proportionToImage(double n, double image, double tileSize){
        double percent = n/tileSize;
        return image*percent;
    }
    
    public static void main(String[] args) throws IOException{
        JFrame frame = new JFrame("Test");
      
        JPanel panel = new JPanel(){
            
            ImportedImage ii = new ImportedImage("entity", null);
            @Override
            public void paintComponent(Graphics g){
                ii.createDesiredImage();
                
                //ii.drawDesiredImage((Graphics2D)g, 30,30);
                ii.drawTiledImage((Graphics2D)g, 0,0, 176,59,64);
            }
        };
        
        frame.setLayout(new GridLayout(1,1));
        frame.add(panel);
        
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000,610));        
        frame.pack();
        frame.setLocationRelativeTo(null);
    }
    
}
