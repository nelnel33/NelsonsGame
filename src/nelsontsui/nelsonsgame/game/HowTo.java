package nelsontsui.nelsonsgame.game;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HowTo extends JDialog{
    private JTextArea howto;
    private JScrollPane instruct;
    public HowTo(JFrame owner){
        super(owner,"Nelson's Game: Instructions");     
        showInstructions();
        
        getContentPane().setBackground(new Color(250,250,210));
        
        add(instruct);
        
        setPreferredSize(new Dimension(300,500));
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);        
        pack();
    }
    private void showInstructions(){
        howto = new JTextArea(
                        "||Controls||" +DialogBox.newline+
                        "Arrow Keys - Up,Down,Left,Right" +DialogBox.newline+
                        "A - attack"+DialogBox.newline+
                        "S - shoot"+DialogBox.newline+
                        "D - defend"+DialogBox.newline+
                        "H - heal"+DialogBox.newline+
                        "Q - inventory selector[backwards]"+DialogBox.newline+
                        "W - inventory selector[forwards]"+DialogBox.newline+
                        "E/Mouse 1[Left Click] - use"+DialogBox.newline+
                        "R/Mouse 2[Right Click - drop"+DialogBox.newline+
                        DialogBox.newline+
                        "||Color Identification||"+DialogBox.newline+
                        "Warrior - red"+DialogBox.newline+
                        "Archer - light green"+DialogBox.newline+
                        "Tank - dark green"+DialogBox.newline+
                        "Sub-Boss - purple"+DialogBox.newline+
                        "Boss - black"+DialogBox.newline+
                        "Dark Grey - walls"+DialogBox.newline+
                        "Grey - doors[damagable walls]"+DialogBox.newline+
                        "Pink - items"+DialogBox.newline+
                        "Orange/Blue - main/sub - portals"+DialogBox.newline+
                        DialogBox.newline+
                        "||Objective||"+DialogBox.newline+
                        "Make it to the black circle and you win!" );
        howto.setLineWrap(true);
        howto.setWrapStyleWord(true);  
        howto.setEditable(false);
        howto.setBackground(new Color(250,250,210));
        instruct = new JScrollPane(howto);   
        instruct.setBackground(new Color(250,250,210));
    }    
}
