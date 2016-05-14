package Tools.FileWorkers.FileImplementation;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by java_monkey on 2/23/2016.
 */
public abstract class FileAbstract implements IFile{

    protected Context context;

    protected FileInputStream inputStream;

    protected OutputStream outStream;

    public IFile setContext(Context context){
        this.context = context;
        return this;
    }

    /**
     * Create input buffered reader
     *
     * @return
     * @throws FileNotFoundException
     */
    protected BufferedReader getBufferedReader() throws FileNotFoundException {
        InputStreamReader streamReader = null;

        try {
            streamReader = new InputStreamReader(
                    this.context.openFileInput(this.getPathToFile()), CHARSET);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return new BufferedReader(streamReader);
    }

    /**
     *
     * @return
     */
    protected OutputStream getOutStream() throws FileNotFoundException {
        return this.context.openFileOutput(this.getPathToFile(), Context.MODE_PRIVATE );
    }

    /**
     *
     * @return
     * @throws FileNotFoundException
     */
    protected FileInputStream getFileInputStream() throws FileNotFoundException {
        return this.context.openFileInput(this.getPathToFile());
    }

    /**
     *
     * @return
     */
    public boolean existFile() {
        try {
            this.getBufferedReader();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     *
     * @param path
     * @return
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    protected boolean validateFilePath(String path){
        if(path == null || path.isEmpty()){
            return false;
        }

        if(path.indexOf(".") >= 0){
            return false;
        }

        return true;
    }
}
