package SearchTranslatCore.source.PrimalSearch;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import WorkSession.SessionModule.DictionaryManager;

public class PrimalSerch implements IprimalSearch {
	
	private String findeTarget;
	
	
	public PrimalSerch(){

	}
	
	protected PrimalSerch target(String findeTarget){
		if(this.findeTarget == null || this.findeTarget.length() == 0){
			System.out.println("-----------eror::target is empty");
		}else{
			System.out.println("-----------alert::target is " + this.findeTarget);
		}
		
		this.findeTarget = findeTarget;
		
		return this;
	}
	
	public String primalSearch(){
		String searchResult = "apple €блоко";
		try {
			searchResult = this.openDictionary();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println(searchResult);
		
		return searchResult;
	}
	
	
	private String openDictionary() throws FileNotFoundException{
		DictionaryManager dictionary = new DictionaryManager();
		String tabName = this.getTabName();
		if(tabName != null){
			try {
				return dictionary.readFrom(tabName);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	private String getTabName(){
		StringBuilder tab_name = new StringBuilder();
		char first = this.findeTarget.charAt(0);
		if(this.charIsValid(first)){
			return tab_name.append(first).append(TAB_SUFFIX).toString();
		}
		
		return null;
	}
	
	private boolean charIsValid(char firstTargetChar){
		return (int)firstTargetChar > 60 & (int)firstTargetChar < 123;
	}
	
	private String search(String nextLine){
		String[] peare = nextLine.split(" ");
		System.out.println(":" + peare[0] + "--" + this.findeTarget + ";");
		peare[0] = peare[0].replace(" ", "");
		if(this.findeTarget.equals(peare[0])){
			System.out.println("success!!!!");
			return peare[0] + " - " + peare[1];
		}
			
		return null;
	}
}
