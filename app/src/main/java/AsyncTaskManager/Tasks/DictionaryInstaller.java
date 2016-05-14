package AsyncTaskManager.Tasks;

import com.example.translateok.DebugActivity;
import com.example.translateok.MainActivity;

import AsyncTaskManager.ITask;
import Tools.FileWorkers.AsyncFileManager;
import Tools.FileWorkers.AsyncFileWorkerImplementation.AsyncFileWorker;
import Tools.SpeedParsers.IParser;

/**
 * Created by java_monkey on 3/12/2016.
 */
public class DictionaryInstaller implements ITask {

    private IParser dictionaryParser;

    private AsyncFileManager driver;

    private String getDictionaryName;

    private Thread[] threadPool;

    public DictionaryInstaller(IParser dictionaryParser, AsyncFileManager driver){
        this.dictionaryParser = dictionaryParser;
        this.driver = driver;

        // temporary
        this.getDictionaryName = "dictionary1.txt";

        AsyncFileWorker fileWorker = new AsyncFileWorker(this.getDictionaryName);
        fileWorker.setContext(MainActivity.context);
        this.driver.setSource(fileWorker);

        this.threadPool = new Thread[2];
    }

    @Override
    public void process() {
        DebugActivity.logger.flushMessage("PROCESSING...");
        for(int ix = 0; ix < 2; ++ix){
            this.threadPool[ix] = new Thread(new Runnable(){

                @Override
                public void run() {
                    DebugActivity.logger.flushMessage("RUNNING...");
                    driver.read();
                }
            });

            this.threadPool[ix].run();
        }

    }

    @Override
    public boolean isTaskFinished() {
        return false;
    }

    @Override
    public int getProgress() {
        return 0;
    }

    @Override
    public Thread[] getThread() {
        return this.threadPool;
    }
}
