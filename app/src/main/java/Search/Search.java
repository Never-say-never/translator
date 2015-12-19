package Search;

import Search.ProcessSearch.ProcessSearch;
import Search.ProcessSearch.SearchEntetis.SearchEnteti;
import Search.ProcessSearch.SearchEntetis.SearchWord;
import AppliacationManager.TranslateProcessManager;

public class Search implements ISearch {
	
	private ProcessSearch pSearch;
	
	public Search() {
		this.pSearch = new ProcessSearch();
	}
	
	/* first entry of search process */
	public SearchEnteti searchProcess(String word) {
		/* for debug */
		TranslateProcessManager.appMessage(word);
		
		/* Prepare line */
		String clieareExp = this.delateSpaces(word);
		
		if(clieareExp.length() < 0){
			return null;
		}
		
		this.pSearch.search(this.typeIs(clieareExp));
		
		if(pSearch.getSearchEntity() != null)
			return pSearch.getSearchEntity();
		
		return null;
	}
	
	private SearchEnteti typeIs(String entety){
		/* choose the type of expression now only one type */
		return new SearchWord(entety);
	}
	
	/*
	 * delete all 'spaces' at end of expressions
	 * and all 'spaces' from begin;
	 * */
	private String delateSpaces(String line){
		return this.pSearch.delateSpaces(line);
	}

}
