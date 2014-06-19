package nelsontsui.nelsonsgame.game;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JViewport;

public class DialogBox extends JPanel{
    private ArrayList<String> text;
    private JScrollPane pane;
    private JViewport view;
    public DialogBox(){
        this.view = new JViewport();
        this.view.setPreferredSize(new Dimension(this.getPreferredSize().width,this.getPreferredSize().height));
        pane = new JScrollPane(view,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }
    
    public static void main(String[] args){
        JFrame frame = new JFrame("test");
        frame.setLayout(null);
        DialogBox dialogbox = new DialogBox();
        dialogbox.setBackground(Color.red);
        dialogbox.setPreferredSize(new Dimension(200,200));
        dialogbox.setBounds(0,0,200,200);
        frame.setSize(new Dimension(500,500));
        frame.add(dialogbox);
        
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        
    }
    
    
}
