package nelsontsui.nelsonsgame.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Nelnel33, Alan T
 */
public class StartMenu extends JPanel{
    private NButton play;//functionality set in MainDisplay
    private NButton howTo;//func set in MainDisplay
    private NButton importButton;//func set in MainDisplay
    private NButton leveleditor; //func set in MainDisplay
    private JPanel buttonHolder;
    
    private JLabel torch1;
    private JLabel torch2;
    
    private NelsonWatermark watermark;
    private NelsonTitle title;
    
    private BufferedImage buttonImage;
    
    private JPanel[] buffer = new JPanel[8];
    
    public StartMenu(NButton play, NButton howTo, NButton importButton, NButton leveleditor){
        this.play = play;
        this.howTo = howTo;
        this.importButton = importButton;
        this.leveleditor = leveleditor;
                
//        setBackground(new Color(55,198,164));
        setBackground(Color.LIGHT_GRAY);
        
        for(int i=0;i<buffer.length;i++){
            buffer[i] = new JPanel();
        }
        
        setLayout(null);
        setPreferredSize(new Dimension(1000,610));
        
        initTorches();
        setButtonHolderLayout();
        addTitle();
        addWatermark();
        
    }
    
    private void initTorches(){
        Image image = new ImageIcon(getClass().getResource("/nelsontsui/nelsonsgame/game/resources/startmenu/100x100 torch.gif")).getImage();
        ImageIcon icon = new ImageIcon(image);
        
        torch1 = new JLabel(icon);
        torch1.setPreferredSize(new Dimension(100,100));
        torch1.setBounds(250,300,100,100);
//        torch1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(torch1);
        
        torch2 = new JLabel(icon);
        torch2.setPreferredSize(new Dimension(100,100));
        torch2.setBounds(650,300,100,100);
//        torch2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(torch2);
    }
    
    private void setButtonHolderLayout(){
        buttonHolder = new JPanel();
        buttonHolder.setLayout(null);
        buttonHolder.setPreferredSize(new Dimension(100,100));
        
        play.setPreferredSize(new Dimension(215,50));
        play.setBounds(1,0,215,50);
        buttonHolder.add(play);
        
        howTo.setPreferredSize(new Dimension(215,50));
        howTo.setBounds(1,55,215,50);
        buttonHolder.add(howTo);
        
        importButton.setPreferredSize(new Dimension(215,50));
        importButton.setBounds(1,110,215,50);
        buttonHolder.add(importButton);
        
        leveleditor.setPreferredSize(new Dimension(215,50));
        leveleditor.setBounds(1,165,215,50);
        buttonHolder.add(leveleditor);
        
        buttonHolder.setBackground(this.getBackground());
        buttonHolder.setOpaque(true);
        
        buttonHolder.setBounds(400,250,215,215);
        add(buttonHolder, BorderLayout.SOUTH);
        
    }
    
    private void addWatermark(){
        watermark = new NelsonWatermark();
        watermark.setBounds(0,570,1020,20);
        add(watermark);
    }
    
    private void addTitle(){
        title = new NelsonTitle();
        title.setBounds(200,40,800,400);
        add(title);
    }
    
}
