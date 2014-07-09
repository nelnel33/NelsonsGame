package nelsontsui.nelsonsgame.leveleditor;


public class StringWrapper {
    
    private int charsPerLine;//number of Characters per line
    
    public static String newline = "\n";
    
    public StringWrapper(int charsPerLine){
        this.charsPerLine = charsPerLine;
    }
    public int getCharsPerLine(){
        return charsPerLine;
    }
    public void setCharsPerLine(int c){
        charsPerLine = c;
    }
    private static boolean checkCharsPerLine(int m){
        if(m<1){
            System.out.println("Characters per line cannot be a value less than 1");
            return false;
        }
        else{
            return true;
        }
    }
    public String wrap(String s){
        if(!checkCharsPerLine(charsPerLine)){
            return s;
        }
        int lines = s.length()/charsPerLine;
        int extra = s.length()%charsPerLine;
        int chars = charsPerLine;
        String newString = "";
        for(int i=0;i<lines;i++){
            for(int j=(chars*i);j<chars+(chars*i);j++){
                newString = newString+s.charAt(j);
            }
            //if(lines>1){
            newString = newString+newline;
            //}
            if(i==lines-1){
                for(int z=(s.length()-extra);z<s.length();z++){
                    newString = newString+s.charAt(z);
                }
            }
        }
        if(lines==0){
            for(int z=(s.length()-extra);z<s.length();z++){
                    newString = newString+s.charAt(z);
            }            
        }
        return newString;
    }
    public String wrap(String s, int m){
        if(!checkCharsPerLine(m)){
            return s;
        }
        int lines = s.length()/m;
        int extra = s.length()%m;
        int chars = m;
        String newString = "";
        for(int i=0;i<lines;i++){
            for(int j=(chars*i);j<chars+(chars*i);j++){
                newString = newString+s.charAt(j);
            }
            //if(lines>1){
            newString = newString+newline;
            //}
            if(i==lines-1){
                for(int z=(s.length()-extra);z<s.length();z++){
                    newString = newString+s.charAt(z);
                }
            }
        }
        if(lines==0){
            for(int z=(s.length()-extra);z<s.length();z++){
                    newString = newString+s.charAt(z);
            }            
        }
        return newString;
    }
    public String wrapHTML(String s){
        if(!checkCharsPerLine(charsPerLine)){
            return s;
        }
        int lines = s.length()/charsPerLine;
        int extra = s.length()%charsPerLine;
        int chars = charsPerLine;
        String newString = "<html>";
        for(int i=0;i<lines;i++){
            for(int j=(chars*i);j<chars+(chars*i);j++){
                newString = newString+s.charAt(j);
            }
            //if(lines>1){
            newString = newString+"<br>";
            //}
            if(i==lines-1){
                for(int z=(s.length()-extra);z<s.length();z++){
                    newString = newString+s.charAt(z);
                }
            }
        }
        newString = newString+"<html>";
        if(lines==0){
            newString = "";
            for(int z=(s.length()-extra);z<s.length();z++){
                    newString = newString+s.charAt(z);
            }                        
        }
        return newString;
    }
    public static String wrapHTML(String s, int m){
        if(!checkCharsPerLine(m)){
            return s;
        }
        int lines = s.length()/m;
        int extra = s.length()%m;
        int chars = m;
        String newString = "<html>";
        for(int i=0;i<lines;i++){
            for(int j=(chars*i);j<chars+(chars*i);j++){
                newString = newString+s.charAt(j);
            }
            //if(lines>1){
            newString = newString+"<br>";
            //}
            if(i==lines-1){
                for(int z=(s.length()-extra);z<s.length();z++){
                    newString = newString+s.charAt(z);
                }
            }
        }
        newString = newString+"<html>";
        if(lines==0){
            newString = "";
            for(int z=(s.length()-extra);z<s.length();z++){
                    newString = newString+s.charAt(z);
            }                        
        }
        return newString;
    }
    public String createAbbr(String s){
        if(!checkCharsPerLine(charsPerLine)){
            return s;
        }
        String newString = "";
        int z;
        if(s.length()<charsPerLine){
            z=s.length();
        }
        else{
            z=charsPerLine;
        }
        for(int i=0;i<z;i++){
            newString = newString+s.charAt(i);
        }
        return newString;
    }  
    public static String createAbbr(String s, int m){
        if(!checkCharsPerLine(m)){
            return s;
        }
        String newString = "";
        int z;
        if(s.length()<m){
            z=s.length();
        }
        else{
            z=m;
        }
        for(int i=0;i<z;i++){
            newString = newString+s.charAt(i);
        }
        return newString;
    }
    public String createFormalAbbr(String s){
        if(!checkCharsPerLine(charsPerLine)){
            return s;
        }
        String newString = "";
        int marker=-1;
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)==' '){
                marker = i;
            }   
            if(i==marker+1){
                newString = newString+s.charAt(i)+".";
            }
            else{
                
            }
        }
        return createAbbr(newString);
    }
    public static String createFormalAbbr(String s, int m){
        if(!checkCharsPerLine(m)){
            return s;
        }
        String newString = "";
        int marker=-1;
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)==' '){
                marker = i;
            }   
            if(i==marker+1){
                newString = newString+s.charAt(i)+".";
            }
            else{
                
            }
        }
        return createAbbr(newString,m);
    }
    public static String wrapOnCharHTML(String s, char c){
        String newString = "<html>";
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)==c){
                newString = newString+s.charAt(i)+"<br>";
            }
            else{
                newString = newString+s.charAt(i);
            }
        }
        newString = newString + "<html>";
        return newString;
    }
    public static String wrapOnChar(String s, char c){
        String newString = "";
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)==c){
                newString = newString+s.charAt(i)+newline;
            }
            else{
                newString = newString+s.charAt(i);
            }
        }
        return newString;
    }
    public static String wrapOnSemiColonHTML(String s){
        String newString = "<html>";
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)==';'){
                newString = newString+s.charAt(i)+"<br>";
            }
            else{
                newString = newString+s.charAt(i);
            }
        }
        newString = newString + "<html>";
        return newString;
    }
    public static String wrapOnSemiColon(String s){
        String newString = "";
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)==';'){
                newString = newString+s.charAt(i)+newline;
            }
            else{
                newString = newString+s.charAt(i);
            }
        }
        return newString;
    }
}
