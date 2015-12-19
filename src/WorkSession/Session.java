package WorkSession;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.TreeSet;

import com.example.testproject.MainActivity;

import SearchTranslatCore.source.SearchWord;
import WorkSession.SessionModule.DictionaryManager;


public class Session implements ISession{
	
	private TreeSet<SearchWord> userWords;
	
	private DictionaryManager dictionaryManager;
	
	public Session(){
		this.dictionaryManager = new DictionaryManager();
		this.userWords = new TreeSet<>();
	}

	@Override
	public void saveSession() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadPreviousSession() {
		String n = null;
		try {
			n = this.dictionaryManager.readFrom("c_dictionary.dic");
			System.out.println(n);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean checkDictionary() {
		String[] files = this.getListOfFile(TABS_FOLDER);
		
		if(files.length > 0){
			return true;
		}
		
		if(this.checkUpdateFromFile()){
			MainActivity.toastManager("Dictionary update!");
			return true;
		}

		return false; 
	}

	@Override
	public boolean checkUpdateFromFile() {
		String[] files = this.getListOfFile(ASSETS_FOLDER);
		
		for(String f : files){ 
			if(f.equals(NON_CONVERT_DIC)){
				this.dictionaryManager.createConvertDictionaryTabs();
				return true;
			}
		}
		
		MainActivity.toastManager("No Dictionary loaded!");
		
		return false;
	}
	
	public TreeSet<SearchWord> getUserWords(){
		return this.userWords;
	}
	
	private String[] getListOfFile(String path){
		String[] files = null;
		try {
			files = MainActivity.assetManager.list(path);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return files;
	}
}
