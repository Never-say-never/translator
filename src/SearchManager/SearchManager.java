package SearchManager;

import java.util.TreeSet;

import SearchTranslatCore.ISearchEntity;
import WorkSession.Session;

public class SearchManager implements ISearchManager {

	private TreeSet<ISearchEntity> HistorySearchMap;
	
	public SearchManager(){
		this.HistorySearchMap = new TreeSet<ISearchEntity>();
	}
	
	@Override
	public void loadCommonlyUsed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveTranslateSession(Session session) {
		// TODO Auto-generated method stub
		
	}
	
	public TreeSet<ISearchEntity> getHistory(){
		return this.HistorySearchMap;
	}

}
