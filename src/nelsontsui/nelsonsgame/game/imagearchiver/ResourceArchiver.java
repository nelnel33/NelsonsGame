package nelsontsui.nelsonsgame.game.imagearchiver;

import java.util.LinkedList;

/**
 *
 * @author Nelnel33
 */
public abstract class ResourceArchiver {
    LinkedList<ImportedImage> archive;    
    
    public ResourceArchiver(){
        archive = new LinkedList();
    }
    
    public ImportedImage getBasedOnFilename(String filename){
        for(ImportedImage i:archive){
            if(i.getResource() != null && i.getResource().equals(filename)){
                return i;
            }
        }
        System.out.println("ImportedImage "+filename+" does not exist.");
        return null;
    }
    
    public void initImportedImage(ImportedImage image, String filename, String dir){
        image = new ImportedImage(filename, dir);
        if(image.getDesiredImage() != null){
            archive.add(image);
        }
    }
}
