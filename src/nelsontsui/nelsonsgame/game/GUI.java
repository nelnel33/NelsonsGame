package nelsontsui.nelsonsgame.game;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
public class GUI extends JFrame{
    private JLabel label;
    private JTextField item1,item2,item3;
    private JPasswordField password;
    public GUI(){
        super("Nelson's Game");
        setLayout(new FlowLayout());
        label = new JLabel("this is a sentence");
        label.setToolTipText("This is going to hover over");
        add(label);
        
        item1 = new JTextField(10);
        add(item1);
        
        item2 = new JTextField("Enter Text Here");
        add(item2);
        
        item3 = new JTextField("uneditable", 20);
        item3.setEditable(false);
        
        password = new JPasswordField("Password: ");
        add(password);
        
        Listener listener = new Listener();
        item1.addActionListener(listener);
        item2.addActionListener(listener);
        item3.addActionListener(listener);               
    }  
    private class Listener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent event) {
            String string = "";
            if(event.getSource()==item1){
                string=String.format("field 1: %s", event.getActionCommand());
            }
            else if(event.getSource()==item2){
                string=String.format("field 2: %s", event.getActionCommand());
            }
            else if(event.getSource()==item3){
                string=String.format("field 3: %s", event.getActionCommand());
            }
            else if(event.getSource()==password){
                string=String.format("password is: %s", event.getActionCommand());
            }
            
            JOptionPane.showMessageDialog(null,string);
        }
        
    }
}
