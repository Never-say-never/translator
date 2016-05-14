package Tools.FileWorkers.AsyncFileWorkerImplementation;

import com.example.translateok.DebugActivity;

import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import Tools.FileWorkers.FileImplementation.FileWorkerTxt;

/**
 * Created by java_monkey on 3/12/2016.
 */
public class AsyncFileAbstract extends FileWorkerTxt{

    protected int currentProgress;

    protected int maxProgress;

    protected volatile boolean isFinished;

    protected volatile boolean isStarted;

    public AsyncFileAbstract(String pathToFile) {
        super(pathToFile);
    }

    @Override
    protected BufferedReader getBufferedReader() throws FileNotFoundException {
        InputStreamReader streamReader = null;
        DebugActivity.logger.flushMessage(" >>>> CREATE CORRECT CONTEXT <<<<");
        try {
            streamReader = new InputStreamReader(
                    this.context.getAssets().open(this.getPathToFile()), CHARSET);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new BufferedReader(streamReader);
    }
}
