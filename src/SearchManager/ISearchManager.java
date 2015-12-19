package SearchManager;

import WorkSession.Session;

public interface ISearchManager {
	
	public abstract void loadCommonlyUsed();
	
	public abstract void saveTranslateSession(Session session);
}
