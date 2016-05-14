package AsyncTaskManager.Tasks;

import AsyncTaskManager.ITask;
import Models.IModel;
import Tools.FileWorkers.AsyncFileWorkerImplementation.IFileAsync;
import Tools.FileWorkers.IDataSourceDriver;
import Tools.SpeedParsers.IParser;

/**
 * Created by java_monkey on 3/12/2016.
 */
public class AsyncTask implements ITask {

    private Thread[] thread;

    public AsyncTask(){

    }

    @Override
    public void process() {
        this.thread[0] = new Thread();
        thread[0].start();
    }

    @Override
    public boolean isTaskFinished() {
        return false;
    }

    @Override
    public int getProgress() {
        return 0;
    }

    public Thread[] getThread() {
        return thread;
    }
}
