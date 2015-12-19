package Session.SessionPreloader.BoostLoad;

import AppliacationManager.TranslateProcessManager;
import android.annotation.SuppressLint;
import java.util.HashMap;
import java.util.Map;

@SuppressLint("DefaultLocale")
public class DictionaryParser implements Runnable {

	/*
	 * Array of lines for current thread, its part of dictionary.txt in assets
	 * witch load at first application start time, to create dictionary
	 * structure into sdcard application space.
	 */
	private String[] strLines;

	/* manager of threads, control resources when tabs creating */
	private ThreadManager tManager;

	/*
	 * <CHAR 'A', TABENTITY <CHAR 'A', STRING
	 * "word1 translate\n word2 translate\n ...">> first char is main tab for
	 * example "apple" - 'a' is main tab, second tab(under tab) is 'p', its mean
	 * that in folder "a_tab" locate file "p_dictionary.dic" with all words
	 * witch start with "[a][p]".
	 */
	private Map<Character, TabEntity> generalMap;

	public DictionaryParser(String[] lines, ThreadManager tManager) {
		this.generalMap = new HashMap<Character, TabEntity>();
		this.strLines = lines;
		this.tManager = tManager;
	}

	/*
	 * check if symbols are English 97 = 'a', 122 = 'z'
	 */
	private boolean isValidChar(String word) {
		char currentChar = word.charAt(1);
		return currentChar < 97 || currentChar > 122;
	}

	/*
	 * Each thread take resource from ThreadManager balance method which divide
	 * primal dictionary into parts. Scan lines of resources and create new
	 * EntetyTab oar add to exist.
	 */
	@Override
	public void run() {
		for (String dictionaryWord : this.strLines) {
			dictionaryWord = dictionaryWord.toLowerCase();
			char currentChar = dictionaryWord.charAt(0);
			TranslateProcessManager.progress.incrementProgressBy(1);
			if (this.isValidChar(dictionaryWord)) {
				TranslateProcessManager.cProgress++;
				continue;
			}

			if (this.generalMap.isEmpty()) {
				this.addNewTab(dictionaryWord);
				TranslateProcessManager.cProgress++;
				continue;
			}

			if (this.generalMap.containsKey(currentChar)) {
				this.generalMap.get(currentChar).setWordsList(dictionaryWord);
			} else {
				this.addNewTab(dictionaryWord);
			}

			TranslateProcessManager.cProgress++;

		}

		this.saveTabs();
	}

	private void addNewTab(String str) {
		char currentChar = str.charAt(0);
		char underTub = str.charAt(1);
		this.generalMap.put(currentChar, new TabEntity(underTub, str));
	}

	private void saveTabs() {
		this.tManager.getAccessToFile(generalMap);
	}
}
