package Session.SessionPreloader;

import java.util.ArrayList;
import java.util.List;

import AndroidFilesType.FilesType.AssetsFile;
import Search.ProcessSearch.SearchEntetis.SearchEnteti;
import Search.ProcessSearch.SearchEntetis.SearchWord;
import Session.ConfigEntity;
import Session.SessionCommons.ISessionLoader;
import Session.SessionPreloader.BoostLoad.ThreadManager;

public class SessionPreloader implements ISessionLoader {

	private StringBuilder compliteLoad;

	public SessionPreloader() {
		this.compliteLoad = new StringBuilder();
	}

	@Override
	public void nextLine(String line) {
		if (!this.isValidLine(line))
			return;

		if (line != null) {
			this.compliteLoad.append(line);
			this.compliteLoad.append((char) 0xa);
		}
	}

	@Override
	public String getContent() {
		System.out.println(compliteLoad.toString());
		return compliteLoad.toString();
	}

	private boolean isValidLine(String line) {
		if (line.contains("="))
			return false;

		return true;
	}

	@Override
	public ConfigEntity parsAppConfig(ConfigEntity configEntity) {
		if (this.contentIsValid()) {
			return configEntity;
		}

		String[] config = this.getContent().split("\n");

		return this.setConfigEntity(config, configEntity);
	}

	private boolean contentIsValid() {
		if (this.getContent() == null && this.getContent().length() <= 0) {
			return false;
		}

		return true;
	}

	private ConfigEntity setConfigEntity(String[] config, ConfigEntity configEntity) {
		configEntity.setVersion(config[0]);
		configEntity.setCountAppStart(Integer.parseInt(config[1]));
		configEntity.setCountUserBook(Integer.parseInt(config[2]));
		configEntity.setCountUserWords(Integer.parseInt(config[3]));
		configEntity.setCountUnknownWords(Integer.parseInt(config[4]));

		return configEntity;
	}

	@Override
	public void boostLoadExternalDictionary(AssetsFile externalDictionary) {
		ThreadManager tManager = new ThreadManager(this.compliteLoad.toString());
		tManager.balanceResource();
		tManager = null;
	}

	@Override
	public void freeContent() {
		if(this.compliteLoad == null | this.compliteLoad.length() == 0){
			return;
		}
		
		int lenght = this.compliteLoad.length() - 1;
		this.compliteLoad.delete(0, lenght).trimToSize();
	}

	@Override
	public List<SearchEnteti> parsUserDictionary() {
		if (!this.contentIsValid()) {
			List<SearchEnteti> tmp = new ArrayList<SearchEnteti>();
			tmp.add(new SearchWord(null));
			return tmp;
		}

		String[] list = this.compliteLoad.toString().split("\n");

		System.out.println("list_l::" + list.length);
		return this.cleareList(list);
	}

	private List<SearchEnteti> cleareList(String[] arr) {
		List<SearchEnteti> tmpList = new ArrayList<SearchEnteti>();
		for (String el : arr) {
			if (el != null | el.length() != 0) {
				tmpList.add(new SearchWord(el));
			}
		}
		
		tmpList.remove(0);
		System.out.println("list_s::" + tmpList.size());
		return tmpList;
	}
}
