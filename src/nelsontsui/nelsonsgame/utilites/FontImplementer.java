package nelsontsui.nelsonsgame.utilites;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alan T
 */
public class FontImplementer {
    private Font font;

    /**
     * The plain style constant.
     */
    public static final int PLAIN = 0;

    /**
     * The bold style constant.  This can be combined with the other style
     * constants (except PLAIN) for mixed styles.
     */
    public static final int BOLD = 1;

    /**
     * The italicized style constant.  This can be combined with the other
     * style constants (except PLAIN) for mixed styles.
     */
    public static final int ITALIC = 2;
        
    public FontImplementer(File file){
        InputStream fnt_stream = null;
        try {
            fnt_stream = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FontImplementer.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, fnt_stream);
        } catch (FontFormatException | IOException ex) {}
    }
    
    public FontImplementer(String internalFilepath){
        InputStream fnt_stream = getClass().getResourceAsStream(internalFilepath);
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, fnt_stream);
        } catch (FontFormatException | IOException ex) {
            System.out.println("System");
        }
    }
    
    public Font getFont(int style,int size){
        return font.deriveFont(style, size);
    }
}
