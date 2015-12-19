package AndroidFilesType;

import java.io.IOException;

import Session.SessionCommons.ISessionLoader;

public interface IFile {

	public final String APP_CONF 		= "configApp.cnf";

	public final String FILE_TYPE_TABS 	= ".tab";

	public final String D_ASSETS 		= "dictionary.txt";

	public final String D_SDCARD 		= "\\sdcard\\tabs\\";

	public final String D_APPCONF 		= "\\sdcard\\";

	public final String D_ASSETS_RESOURCE_FOLDER = "";

	public final int MODE_APPEND 		= 0x00008000;

	public abstract void read(ISessionLoader sessionLoader) throws IOException;

	public abstract void write(String writeString) throws IOException;

}
