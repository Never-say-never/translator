package Session;

import AndroidFilesType.FilesType.SDCardFile;
import Session.SessionCommons.Session;
import Session.SessionPreloader.SessionPreloader;

public class SessionProcessManager {

	private Session session;

	public SessionProcessManager() {
		this.session = new Session();
	}

	public void startSession() {
		// just set source for working with start load application
		this.session.setSource(new SessionPreloader());

		// Initialize session, load configure file, check if its first
		// start of application - create 'configure.dic', and start
		// boostDictionaryLoad() - to create base of words;
		// if not - load old file with application configure.
		this.session.initSession(new SDCardFile());

		// load user dictionary if it is exist, if not
		// create default with first marked line - #empty_dictionary
		this.session.initUserDictionary(new SDCardFile());

	}

	public Session getSource() {
		return this.session;
	}
}
