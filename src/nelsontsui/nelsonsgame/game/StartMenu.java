package nelsontsui.nelsonsgame.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class StartMenu extends JPanel{
    private JButton play;//functionality set in MainDisplay
    private JButton howTo;//func set in MainDisplay
    private JButton importButton;//func set in MainDisplay
    private JButton leveleditor; //func set in MainDisplay
    private JPanel buttonHolder;
    private NelsonWatermark watermark;
    
    private JPanel[] buffer = new JPanel[8];
    public StartMenu(JButton play,JButton howTo, JButton importButton, JButton leveleditor){
        this.play = play;
        this.howTo = howTo;
        this.importButton = importButton;
        this.leveleditor = leveleditor;
        setBackground(new Color(55,198,164));
        
        for(int i=0;i<buffer.length;i++){
            buffer[i] = new JPanel();
        }
        
        setButtonHolderLayout();
        addWatermark();
        
        setLayout(null);
        setPreferredSize(new Dimension(1000,610));
    }
    private void setButtonHolderLayout(){
        buttonHolder = new JPanel();
        buttonHolder.setLayout(new GridLayout(4,1));
        buttonHolder.setPreferredSize(new Dimension(100,100));
        buttonHolder.add(play); 
        buttonSize(play);
        play.setOpaque(true);
        play.setBackground(this.getBackground());
        
        buttonHolder.add(howTo); 
        buttonSize(howTo);
        howTo.setOpaque(true);
        howTo.setBackground(this.getBackground());
        
        buttonHolder.add(importButton); 
        buttonSize(importButton);
        importButton.setOpaque(true);
        importButton.setBackground(this.getBackground());
        
        buttonHolder.add(leveleditor); 
        buttonSize(leveleditor);
        leveleditor.setOpaque(true);
        leveleditor.setBackground(this.getBackground());
        
        buttonHolder.setBackground(this.getBackground());
        buttonHolder.setOpaque(true);
        
        
        buttonHolder.setBounds(400,250,200,200);
        add(buttonHolder, BorderLayout.SOUTH);
    }
    private void buttonSize(JButton b){
        b.setPreferredSize(new Dimension(25,100));
    }  
    private void addWatermark(){
        watermark = new NelsonWatermark();
        watermark.setBounds(0,570,1020,20);
        add(watermark);
    }
    
}
