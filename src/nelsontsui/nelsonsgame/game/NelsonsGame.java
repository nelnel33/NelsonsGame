/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nelsontsui.nelsonsgame.game;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Nelnel33
 */
public class NelsonsGame {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
        //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        /*
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
            UIManager.setLookAndFeel(info.getClassName());
            break;
                }
            }
        } catch (Exception e) {
            System.out.println("Cannot set desired UI. Default UI was set.");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        */
        MainDisplay main = new MainDisplay();
    }
}
