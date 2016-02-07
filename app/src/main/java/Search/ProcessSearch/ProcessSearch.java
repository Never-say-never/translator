package Search.ProcessSearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import AndroidFilesType.FilesType.SDCardFile;
import AppliacationManager.TranslateProcessManager;
import Search.ProcessSearch.SearchEntetis.SearchEntity;

public class ProcessSearch {
	
	private SearchEntity source;
	
	private List<String> expList;
	
	/* 
	 * have hear single word or expression for example
	 * SearchWord       - 'apple'
	 * SearchExpression - 'i am eat apple'
	 * */
	public void search(SearchEntity typeIs) {
		if(!this.validateWord(typeIs.getExp())){
			TranslateProcessManager.appMessage("search() :: return");
			return;
		}
		
		this.source = typeIs;

		this.searchStart();
	}
	
	public boolean validateWord(String word){
		if(word == null || word.length() == 0)
			return false;
		
		return true;
	}

	private String initLocation(String exp){
		StringBuilder location = new StringBuilder();
		
		char tab = exp.charAt(0);
		location.append(tab).append("_tab\\");
		
		if(this.source.getExp().length() >= 2){
			char underTab = exp.charAt(1);
			location.append(underTab);
			location.append("\\");
		}
		
		location.append("_common.dic");
		
		return location.toString();
	}
	
	private List<String> cleareExpressionAtom(String[] primal){
		List<String> expList = new ArrayList<String>();
		for(String exp : primal){
			if(!primal.equals(" ")){
				expList.add(exp);
			}
		}
		
		return expList;
	}
	
	private void searchStart(){
		TranslateProcessManager.appMessage("searchStart() ::");
		
		String[] expAtom = this.source.getAtomOfExp();
		this.expList = this.cleareExpressionAtom(expAtom);
		
		if(expList.isEmpty()){
			TranslateProcessManager.appMessage("searchStart() :: return"); 
			return;
		}
		
		for(int ix = 0; ix < expList.size(); ++ix){
			String location = this.initLocation(expList.get(ix));
			SDCardFile file = new SDCardFile(location);
			try {
				if(file.initInputStream()){
					this.finde(file.read(), ix);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				file = null;
			}
		}
	}

	public void finde(List<String> list, int index) {
		for(String line : list){
			line = this.delateSpaces(line);
			String[] variants = line.split(" ");
			if(variants[0].equals(expList.get(index))){
				TranslateProcessManager.appMessage(variants[1]);
				this.source.setTranslate(variants[1]);
			}
		}
		
	}
	
	public SearchEntity getSearchEntity(){
		return this.source;
	}
	
	/*
	 * delete all 'spaces' at end of expressions
	 * and all 'spaces' from begin;
	 * */
	public String delateSpaces(String line){
		return this.deleteLeft(this.deleteRight(line));
	}
	
	private String deleteRight(String line){
		String rightCliare = line;
		
		if((line.length() - 1) < 0)
			return line; 
		
		if(line.charAt(line.length() - 1) == ' '){
			line = line.substring(0, line.length() - 1);
			rightCliare = this.deleteRight(line);
		}
		
		return rightCliare;
	}
	
	private String deleteLeft(String line){
		String leftCliare = line;
		
		if(line.length() == 0)
			return line; 
		
		if(line.charAt(0) == ' '){
			line = line.substring(1);
			leftCliare = this.deleteLeft(line);
		}
		
		return leftCliare;
	}

}
