package WorkSession;

public interface ISession {

	public final String NON_CONVERT_DIC = "dictionary.txt";
	
	public final String TABS_FOLDER = "./tabs/";
	
	public final String ASSETS_FOLDER = "";
	/*
	 * save all new wards from user search or buffer
	 */
	public abstract void saveSession();
	
	/*
	 * load all user words from my_dictionary.dic
	 * to create ListWiev
	 */
	public abstract void loadPreviousSession();
	
	/*
	 * check folder ./assets/tabs/ if there are convert
	 * letters tabs is.
	 */
	public abstract boolean checkDictionary();
	
	/*
	 * if folder ./assets/tabs/ is empty or not exist
	 * Session check for non convert file into ./assets/
	 */
	public abstract boolean checkUpdateFromFile();
}
