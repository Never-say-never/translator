package Session.SessionCommons;

import AndroidFilesType.FilesType.AndroidFile;

public interface ISession {

	public abstract boolean readFile(AndroidFile file);

	public abstract void writeFile(AndroidFile file, String data);

}
