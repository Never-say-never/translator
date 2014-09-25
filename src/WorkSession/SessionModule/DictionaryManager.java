package WorkSession.SessionModule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

import com.example.testproject.MainActivity;

public class DictionaryManager implements IDictionaryManager {

	private static final int MODE_APPEND = 0x00008000;
	
	private final String UNCONVERT_DICTIONARY = "dictionary.txt";
	
	private final String PATH_TO_COMPLITE 	  = "sdcard\\tabs\\";
	
	private static char currentChar = ' ';
	
	private RandomAccessFile binFile;
	
	private FileOutputStream out;
	
	@Override
	public void deleteWord() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addWords() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTabs() {
		
		
	}
	
	public void createConvertDictionaryTabs(){
		StringBuilder line = new StringBuilder();
        InputStream stream = null;
		try {
			stream = MainActivity.assetManager.open(UNCONVERT_DICTIONARY);
			int check;
			while((check = stream.read()) > -1){
				if(check == 10){
					this.checkNewLattaer(line.toString());
					line.delete(0, line.length());
					
					continue;
				}
				
				line.append((char) check);
            }

		    stream.close();
		} catch (IOException ex){
            System.out.println(ex);
        }
    }
	
	private void checkNewLattaer(String line){
		
        if(currentChar == ' '){
            currentChar = line.charAt(0);
            this.createNewBinTab(line);
            return;
        }else if(currentChar != line.charAt(0)){
            currentChar = line.charAt(0);
        }
        
        if(currentChar == line.charAt(0)){
            this.createNewBinTab(line);
        }
    }
	
	public void createNewBinTab(String line){
        File tab = null;
        
        String comliteLine = line + (char) 0xa;
        
        try {
        	
        	if(line.charAt(0) == ' '){
            	String name = PATH_TO_COMPLITE + "_losted_dictionary.dic";
            	out = MainActivity.context.openFileOutput(name, MODE_APPEND);
            }else{
            	String name = PATH_TO_COMPLITE + currentChar +  "_dictionary.dic";
            	out = MainActivity.context.openFileOutput(name, MODE_APPEND);
            }
        	
        	out.write(incode(comliteLine.getBytes()));
        	out.flush();
        	out.close();
        } catch (FileNotFoundException ex ) {
            System.out.println("Write Error " + ex);
        } catch (IOException ex){
            System.out.println("Write Error " + ex);
        } 
    }
	
	private byte[] incode(byte[] primal){
        for(int ix = 0; ix < primal.length; ++ix){
            primal[ix] = (byte) (~primal[ix] &0xff);
        }
        
        return primal;
    }
	
	private byte[] decode(byte[] bytes){
        for(int ix = 0; ix < bytes.length - 1; ++ix){
            bytes[ix] = (byte) (~bytes[ix] &0xff);
        }
        
        return bytes;
    }

	public String readFrom(String tab) throws UnsupportedEncodingException{
        System.out.println("Reading... " + tab);
        tab = PATH_TO_COMPLITE + tab.toLowerCase();
        FileInputStream tmp_s = null;
        try {
        	tmp_s = MainActivity.context.openFileInput(tab);
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        }
        
        InputStreamReader inputStreamReader = new InputStreamReader(tmp_s);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        
        StringBuilder sb = new StringBuilder();
        String line;
        try {
        	while ((line = bufferedReader.readLine()) != null) {
        		sb.append(line).append((char) 10);
        	}
        	tmp_s.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
        
        byte[] bytes = sb.toString().getBytes();
        
        return new String(this.decode(bytes), "UTF-8");
    }
}
