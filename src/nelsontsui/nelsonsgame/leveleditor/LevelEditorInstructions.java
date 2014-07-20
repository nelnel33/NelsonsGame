package nelsontsui.nelsonsgame.leveleditor;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import nelsontsui.nelsonsgame.game.DialogBox;

/**
 *
 * @author Nelnel33
 */
public class LevelEditorInstructions extends JDialog{
    private JTextArea howto;
    private JScrollPane instruct;
    public LevelEditorInstructions(JDialog owner){
        super(owner,"Nelson's Game: Level Editor Instructions");     
        showInstructions();
        
        getContentPane().setBackground(new Color(250,250,210));
        
        add(instruct);
        
        setPreferredSize(new Dimension(600,400));
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);        
        pack();
        setLocationRelativeTo(owner);
    }
    private void showInstructions(){
        howto = new JTextArea(
                        "||Controls||" +DialogBox.newline+
                        DialogBox.bp+"Left Mouse - Place Entity" +DialogBox.newline+
                        DialogBox.bp+"Right Mouse - Remove Entity" +DialogBox.newline+
                        DialogBox.bp+"Arrow Keys - Moves the scrollable panel on the bottom right" +DialogBox.newline+
                        DialogBox.newline+        
                        "||Other Info||" +DialogBox.newline+
                        DialogBox.bp+"To set the attributes of certain entities click the 'Custom' button on the bottom right/below 'Configure'" +DialogBox.newline+
                        DialogBox.bp+"To reset all the current attributes to their default settings click the 'Default' button on the bottom right/below 'Configure'" +DialogBox.newline+
                        DialogBox.bp+"Please remember to set the desired attributes of an entity before placing it." +DialogBox.newline+
                        DialogBox.bp+"Please hover over the top-left corner of any placed entity to get the statistics of that entity." +DialogBox.newline+
                        DialogBox.bp+"If you are still confused on each of the entities and their configurations, please hover over the the corresponding entities' tile." +DialogBox.newline+
                        DialogBox.newline+
                        DialogBox.bp+"Please note: MapGates currently only work for your own computer. " +DialogBox.newline+
                        "If you were to send the level containing the MapGate to another computer the file path would not match up and it would not work. " +DialogBox.newline+
                        "This will be fixed eventually -- probably with some sort of level chaining feature"
                         );
        howto.setLineWrap(true);
        howto.setWrapStyleWord(true);  
        howto.setEditable(false);
        howto.setBackground(new Color(250,250,210));
        instruct = new JScrollPane(howto);   
        instruct.setBackground(new Color(250,250,210));
    } 
    
}
