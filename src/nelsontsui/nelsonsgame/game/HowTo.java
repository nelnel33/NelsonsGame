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
        setLocationRelativeTo(owner);
    }
    private void showInstructions(){
        howto = new JTextArea(
                        "||Controls||" +DialogBox.newline+
                        DialogBox.bp+"Arrow Keys - Up,Down,Left,Right" +DialogBox.newline+
                        DialogBox.bp+"A - attack"+DialogBox.newline+
                        DialogBox.bp+"S - shoot"+DialogBox.newline+
                        DialogBox.bp+"D - defend"+DialogBox.newline+
                        DialogBox.bp+"H - heal"+DialogBox.newline+
                        DialogBox.bp+"Q - inventory selector[backwards]"+DialogBox.newline+
                        DialogBox.bp+"W - inventory selector[forwards]"+DialogBox.newline+
                        DialogBox.bp+"E/Mouse 1[Left Click] - use"+DialogBox.newline+
                        DialogBox.bp+"R/Mouse 2[Right Click - drop"+DialogBox.newline+
                        DialogBox.newline+
                        "||Color Identification||"+DialogBox.newline+
                        DialogBox.bp+"Warrior - red"+DialogBox.newline+
                        DialogBox.bp+"Archer - light green"+DialogBox.newline+
                        DialogBox.bp+"Tank - dark green"+DialogBox.newline+
                        DialogBox.bp+"Sub-Boss - purple"+DialogBox.newline+
                        DialogBox.bp+"Boss - black"+DialogBox.newline+
                        DialogBox.bp+"Dark Grey - walls"+DialogBox.newline+
                        DialogBox.bp+"Grey - doors[damagable walls]"+DialogBox.newline+
                        DialogBox.bp+"Pink - items"+DialogBox.newline+
                        DialogBox.bp+"Orange/Blue - main/sub - portals"+DialogBox.newline+
                        DialogBox.newline+
                        "||Objective||"+DialogBox.newline+
                        DialogBox.bp+"Make it to the black circle and you win!" );
        howto.setLineWrap(true);
        howto.setWrapStyleWord(true);  
        howto.setEditable(false);
        howto.setBackground(new Color(250,250,210));
        instruct = new JScrollPane(howto);   
        instruct.setBackground(new Color(250,250,210));
    }    
}
