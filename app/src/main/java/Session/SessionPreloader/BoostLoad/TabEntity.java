package Session.SessionPreloader.BoostLoad;

import android.annotation.SuppressLint;
import java.util.HashMap;
import java.util.Map;

public class TabEntity {

	private char underTab;

	private Map<Character, StringBuilder> underTabList;

	public TabEntity() {
		this.underTabList = new HashMap<Character, StringBuilder>();
	}

	public TabEntity(char underTab, String word) {
		this.underTabList = new HashMap<Character, StringBuilder>();
		this.underTab = underTab;

		this.underTabList.put(underTab, new StringBuilder(word).append("\n"));
	}

	private void addTabContent(String word) {
		this.underTabList.get(word.charAt(1)).append(word).append("\n");
	}

	private void createTab(String word) {
		this.underTabList.put(word.charAt(1), new StringBuilder().append(word)
				.append("\n"));
	}

	public char getUnderTab() {
		return underTab;
	}

	public void setUnderTab(char underTab) {
		this.underTab = underTab;
	}

	@SuppressLint("DefaultLocale")
	public void setWordsList(String wordsList) {
		wordsList = wordsList.toLowerCase();

		if (this.underTabList.isEmpty()) {
			this.createTab(wordsList);
		} else {
			this.ifTabExist(wordsList);
		}
	}

	private void ifTabExist(String ward) {
		char currentChar = ward.charAt(1);
		if (this.underTabList.containsKey(currentChar)) {
			this.addTabContent(ward);
		} else {
			this.createTab(ward);
		}
	}

	public Map<Character, StringBuilder> getUnderTabList() {
		return underTabList;
	}
}
