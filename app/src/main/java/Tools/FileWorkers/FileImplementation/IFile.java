package Tools.FileWorkers.FileImplementation;

import org.w3c.dom.Document;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by java_monkey on 2/21/2016.
 */
public interface IFile {

    /**
     * start point of path
     */
    public final String HOME_PATH = "\\sdcard\\";

    /**
     * path to temporary files
     */
    public final String TEMP_PATH = "\\sdcard\\temp\\";

    /**
     * file encoding
     */
    public final String CHARSET = "utf-8";

    /**
     * Create empty file
     */
    public abstract boolean createFile() throws IOException;

    /**
     * Read content from file
     *
     * @return
     */
    public abstract boolean readFile() throws IOException;

    /**
     * Update file content
     *
     * @param obj
     */
    public abstract boolean updateFile(Object obj) throws IOException;

    /**
     * Check if file already exist
     *
     * @return
     */
    public abstract boolean existFile();

    /**
     *
     * @return
     */
    public abstract boolean delete() throws Exception;

    /**
     *
     * @return
     */
    public abstract String getPathToFile();

    /**
     * 
     * @return
     */
    public abstract Document getContent();
}
