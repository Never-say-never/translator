package Tools.FileWorkers;

import android.annotation.TargetApi;
import android.os.Build;

import com.example.translateok.DebugActivity;

import java.io.IOException;

import Tools.FileWorkers.AsyncFileWorkerImplementation.AsyncFileWorker;
import Tools.FileWorkers.AsyncFileWorkerImplementation.IFileAsync;
import Tools.FileWorkers.FileImplementation.IFile;

/**
 * Created by java_monkey on 3/12/2016.
 */
public class AsyncFileManager extends FileManager{

    private IFileAsync source;

    public AsyncFileManager(){
        DebugActivity.logger.flushMessage(this.getClass().getName());
    }

    @Override
    public void setSource(IFile source){
        DebugActivity.logger.flushMessage(this.getClass().getName() + " SEAT SOURCE");
        this.source = (IFileAsync) source;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public IFile read(){
        DebugActivity.logger.flushMessage(this.getClass().getName() + " READ");

        Thread t = new Thread(this.source);
        t.start();

        //this.source.startProcessing();

        return (IFile) this.source;
    }

}
