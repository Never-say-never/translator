package Session.SessionCommons;

import java.util.List;

import AndroidFilesType.FilesType.AssetsFile;
import Search.ProcessSearch.SearchEntetis.SearchEnteti;
import Session.ConfigEntity;

public interface ISessionLoader extends ISessionLoaderPoore{

	public final String UNCONVERT_DICTIONARY = /*"Mueller24.koi";*/"dictionary.txt";

	public final String TABS_FOLDER = "sdcard\\tabs\\";

	public final String ASSETS_FOLDER = "";

	public abstract ConfigEntity parsAppConfig(ConfigEntity configEntity);

	public abstract void boostLoadExternalDictionary(
			AssetsFile externalDictionary);

	public abstract List<SearchEnteti> parsUserDictionary();
}
