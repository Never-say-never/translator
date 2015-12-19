package Session.SessionPreloader.BoostLoad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import AndroidFilesType.FilesType.SDCardFile;
import AppliacationManager.TranslateProcessManager;

public class ThreadManager {

	private String[] content;

	private final int T_OCCUPAY = 10000;

	private int generalProgress;

	private List<Thread> threadPool;

	public ThreadManager(String content) {
		this.threadPool = new ArrayList<Thread>();
		this.content 	= content.split("\n");
	}

	public void balanceResource() {
		/* allocating resources between threads */
		this.shareResources();
		this.startAllThreads();

		/* wait for end of all threads */
		this.alignThreadWork();
	}

	private void shareResources() {
		int residue = this.getResidueLineCount();
		int size 	= this.threadCount(residue);

		for (int index = 0; index < size; ++index) {
			String[] linesToCopy = null;
			if (index != size - 1) {
				linesToCopy = new String[T_OCCUPAY];
			} else {
				linesToCopy = new String[residue];
			}

			this.generalProgress += linesToCopy.length;

			this.setThreadResources(index, linesToCopy);
			linesToCopy = null;
		}

		TranslateProcessManager.gProgress = this.generalProgress;
		System.out.println(" load of count general progress :: "
				+ TranslateProcessManager.gProgress);
	}

	private void startAllThreads() {
		for (Thread parth : this.threadPool) {
			parth.start();
		}
	}

	private void alignThreadWork() {
		for (Thread thread : this.threadPool) {
			try {
				thread.join();
			} catch (InterruptedException ex) {
				System.out.println(ex);
			}
		}
	}

	private int getResidueLineCount() {
		return this.content.length % T_OCCUPAY;
	}

	private int threadCount(int isOneMore) {
		int fullCircl = this.content.length / T_OCCUPAY;

		if (isOneMore > 0)
			fullCircl++;

		return fullCircl;
	}

	private void setThreadResources(int index, String[] linesToCopy) {
		int deep = index * T_OCCUPAY;
		System.arraycopy(this.content, deep, linesToCopy, 0, linesToCopy.length);
		this.threadPool.add(new Thread(new DictionaryParser(linesToCopy, this)));
	}

	public void getAccessToFile(Map<Character, TabEntity> tabs) {
		synchronized (this) {
			for (Map.Entry<Character, TabEntity> entry : tabs.entrySet()) {
				TabEntity tmp = (TabEntity) entry.getValue();
				char key 	  = (Character) entry.getKey();
				
				// Save all under tabs of one main char, current thread
				// which find words of one dictionary tab, save it on
				// file in correct folder 'sd_card/my_app/tabs/a/b/_common.dic'.
				// looks like - Map <'A',TabEntity { <'b',"abyss, about, ..."> } >
				this.saveTabs(tmp, key);
			}
		}
	}

	private void saveTabs(TabEntity tabEntity, char key){
		Map<Character, StringBuilder> underTab = tabEntity.getUnderTabList();
		for (Map.Entry<Character, StringBuilder> entryIn : underTab.entrySet()) {
			StringBuilder tmpN = (StringBuilder) entryIn.getValue();
			char keyCharacter  = (Character) entryIn.getKey();
			SDCardFile fileSD  = null;
			try {
				fileSD = new SDCardFile(this.tabName(key, keyCharacter));
				fileSD.write(tmpN.toString());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// free reference on file
				fileSD = null; 
			}
		}
	}
	
	private String tabName(char key, char keyU) {
		StringBuilder name = new StringBuilder();

		key  = this.emptyChar(key);
		keyU = this.emptyChar(keyU);

		name.append(key).append("_tab");
		name.append("\\").append(keyU);
		name.append("\\_common.dic");

		return name.toString();
	}

	private char emptyChar(char c) {
		return (c == ' ') ? c = '_' : c;
	}
}
