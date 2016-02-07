package Session.SessionCommons;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Search.ProcessSearch.SearchEntetis.SearchEntity;
import Session.ConfigEntity;

import AndroidFilesType.FilesType.AndroidFile;
import AndroidFilesType.FilesType.AssetsFile;
import AppliacationManager.TranslateProcessManager;

public class Session implements ISession {

	private ISessionLoader source;

	private ConfigEntity configAppFile;

	private List<SearchEntity> usrWordList;

	public Session() {
		this.usrWordList   = new ArrayList<SearchEntity>();
		this.configAppFile = new ConfigEntity();
	}

	public void setSource(ISessionLoader source) {
		this.source = source;
	}

	public ISessionLoader getSource() {
		return this.source;
	}

	@Override
	public boolean readFile(AndroidFile file) {
		try {
			if (file.initInputStream()) {
				file.read(this.source);
			} else {
				return false;
			}
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	public void initSession(AndroidFile file) {
		if (!this.readFile(file)) {
			TranslateProcessManager.firstBoot = true;
			
			/* if file note found create new */
			this.createConfigFile(file);

			/* load dictionary from assets */
			this.loadExternalDictionary();
		} else {
			/* set progress out of boostLoader */
			TranslateProcessManager.gProgress = 100;

			/* load application configuration files */
			this.loadAppConfig();
		}
		/* free content of file read manager */
		this.source.freeContent();
	}

	private void loadAppConfig() {
		this.configAppFile = this.source.parsAppConfig(this.configAppFile);

		/* for ui - progress bar */
		TranslateProcessManager.cProgress = 99;
		TranslateProcessManager.progress.incrementProgressBy(100);
	}

	private void createConfigFile(AndroidFile file) {
		String jason = this.configAppFile.getJson();
		this.writeFile(file, jason);
	}

	private void loadExternalDictionary() {
		AssetsFile externalDictionary = new AssetsFile();
		this.readFile(externalDictionary);
		this.source.boostLoadExternalDictionary(externalDictionary);
	}

	@Override
	public void writeFile(AndroidFile file, String data) {
		try {
			file.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initUserDictionary(AndroidFile file) {
		file.setFileName(configAppFile.DEFAULT);
		if (!this.readFile(file)) {
			System.out.println("create new Default.dic");
			this.writeFile(file, "");
		} else {
			/* debug - show where function is calling */
			TranslateProcessManager.appMessage("this.loadUserDictionary()");

			/* load user search words from default or top book */
			this.loadUserDictionary();
		}

		this.source.freeContent();
	}

	private void loadUserDictionary() {
		this.usrWordList = this.source.parsUserDictionary();
	}

	public List<SearchEntity> getList() {
		return this.usrWordList;
	}

	public String defaultCurrentBook() {
		if (this.configAppFile.getUserBookList().size() > 0)
			return this.configAppFile.getUserBookList().get(0);

		return this.configAppFile.DEFAULT;
	}

}
