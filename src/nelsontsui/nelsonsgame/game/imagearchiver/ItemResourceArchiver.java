package nelsontsui.nelsonsgame.game.imagearchiver;

import java.util.LinkedList;
import nelsontsui.nelsonsgame.game.items.Ammo;
import nelsontsui.nelsonsgame.game.items.Armor;
import nelsontsui.nelsonsgame.game.items.HealthPotion;
import nelsontsui.nelsonsgame.game.items.Item;
import nelsontsui.nelsonsgame.game.items.Potion;
import nelsontsui.nelsonsgame.game.items.ProjectileWeapon;
import nelsontsui.nelsonsgame.game.items.StrengthPotion;
import nelsontsui.nelsonsgame.game.items.UnusableItem;
import nelsontsui.nelsonsgame.game.items.Weapon;

/**
 *
 * @author Nelnel33
 */
public class ItemResourceArchiver extends ResourceArchiver{    
    public static String DIR = "items/";
    
    //DEFAULTS
    public ImportedImage ITEM;    
    public ImportedImage POTION;
    public ImportedImage UNUSABLE_ITEM;
    
    //WILL BE USED
    public ImportedImage STRENGTH_POTION;
    public ImportedImage HEALTH_POTION;
    
    public ImportedImage ARMOR;
    public ImportedImage WEAPON;
    public ImportedImage PROJECTILE_WEAPON;
    public ImportedImage AMMO;
    
    public ItemResourceArchiver(){
        //DEFAULTS
        initImportedImage(ITEM, Item.FILENAME, ImportedImage.ITEMS_DIR);
        initImportedImage(POTION, Potion.FILENAME, ImportedImage.ITEMS_DIR);
        initImportedImage(UNUSABLE_ITEM, UnusableItem.FILENAME, ImportedImage.ITEMS_DIR);

        //WILL BE USED
        initImportedImage(STRENGTH_POTION, StrengthPotion.FILENAME, ImportedImage.ITEMS_DIR);
        initImportedImage(HEALTH_POTION, HealthPotion.FILENAME, ImportedImage.ITEMS_DIR);

        initImportedImage(ARMOR, Armor.FILENAME, ImportedImage.ITEMS_DIR);
        initImportedImage(WEAPON, Weapon.FILENAME, ImportedImage.ITEMS_DIR);
        initImportedImage(PROJECTILE_WEAPON, ProjectileWeapon.FILENAME, ImportedImage.ITEMS_DIR);
        initImportedImage(AMMO, Ammo.FILENAME, ImportedImage.ITEMS_DIR); 
    }
}
