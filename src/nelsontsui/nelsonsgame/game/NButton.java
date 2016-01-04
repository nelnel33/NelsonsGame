package nelsontsui.nelsonsgame.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Alan T
 */
public class NButton extends JButton implements MouseListener{
    private BufferedImage buttonImage;
    private BufferedImage pushButtonImage;
    
    private boolean hovering;
    
    public NButton(String text){
        super(text);
        try {
            buttonImage = ImageIO.read(getClass().getResource("/nelsontsui/nelsonsgame/game/resources/startmenu/medieval_button.png"));
            pushButtonImage = ImageIO.read(getClass().getResource("/nelsontsui/nelsonsgame/game/resources/startmenu/medieval_button_pushed.png"));
        } catch (IOException ex) {
            Logger.getLogger(NButton.class.getName()).log(Level.SEVERE, null, ex);
        }
        setFont(MainDisplay.MEDIEVAL_FONT.getFont(0, 16));
        setForeground(Color.LIGHT_GRAY);
        setOpaque(true);
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setHorizontalTextPosition(JButton.CENTER);
        setVerticalTextPosition(JButton.CENTER);
        
        addMouseListener(this);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D gd = (Graphics2D)g;
        
        if(hovering){
            gd.drawImage(pushButtonImage, 0, 0, null);
        }
        else{
            gd.drawImage(buttonImage, 0, 0, null);
        }
        super.paintComponent(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        hovering = true;
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        hovering = false;
        repaint();
    }
}
