package Tools.FileWorkers;

import android.annotation.TargetApi;
import android.os.Build;

import com.example.translateok.DebugActivity;

import java.io.IOException;

import Tools.FileWorkers.FileImplementation.IFile;

/**
 * Created by java_monkey on 2/21/2016.
 */
public class FileManager implements IDataSourceDriver{

    private IFile source;

    public FileManager(){
        DebugActivity.logger.flushMessage(this.getClass().getName());
    }

    @Override
    public void setSource(IFile source){
        this.source = source;
    }

    /**
     * Return file with loaded content
     *
     * @return
     */
    @Override
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public IFile read(){
        if(!this.source.existFile()){
            DebugActivity.logger.flushMessage(
                    "CREATE NEW FILE:" + this.source.getPathToFile());

            this.create();
        }else{
            DebugActivity.logger.flushMessage("FILE EXIST :"
                    + this.source.getPathToFile());
        }

        try {
            this.source.readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.source;
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean save(Object obj){
        if(obj == null){
            DebugActivity.logger.flushMessage(this.getClass().getName()
                    + " empty fields!");

            return false;
        }

        try {
            return this.source.updateFile(obj);
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     *
     * @return
     */
    @Override
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public boolean create(){
        try {
            return this.source.createFile();
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     *
     * @return
     */
    @Override
    public boolean removeFile(){
        if(this.source.existFile()){
            try {
                return this.source.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * Delete any existing file
     *
     * @param file
     * @return
     */
    @Override
    public boolean removeFile(IFile file){
        if(file.existFile()){
            try {
                return file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

}
