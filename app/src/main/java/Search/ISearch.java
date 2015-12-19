package Search;

import Search.ProcessSearch.SearchEntetis.SearchEnteti;

public interface ISearch {
	
	public final String WRONG_TYPE   = "Incorrect expression!";
	
	public final String EMPTY_REZULT = "Not found";
	
	public abstract SearchEnteti searchProcess(String word);
	
}
