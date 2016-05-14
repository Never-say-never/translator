package Tools.FileWorkers.AsyncFileWorkerImplementation;

import com.example.translateok.DebugActivity;

import java.io.IOException;

/**
 * Created by java_monkey on 3/12/2016.
 */
public class AsyncFileWorker extends AsyncFileAbstract implements IFileAsync{

    public AsyncFileWorker(String pathToFile) {
        super(pathToFile);

        this.setMaxProgressValue(DEFAULT_MAX);
    }

    @Override
    public void run() {
        int count = 3;
        while(count > 0){
            this.startProcessing();
            count--;
        }

        DebugActivity.logger.flushMessage(
                Thread.currentThread().getName() + " thread finish ...");
    }

    @Override
    public boolean isFinish() {
        return this.isFinished;
    }

    /**
     * processing file, parsing and creating
     * vocabulary tabs
     */
    @Override
    public void startProcessing() {
        try {
            this.readFile();
        } catch (IOException e) {
            DebugActivity.logger.flushMessage(e.getMessage() + e.getStackTrace());
        }
        DebugActivity.logger.flushMessage(
                Thread.currentThread().getName() + " thread processing ...");
    }

    @Override
    public int getProgress() {
        return this.currentProgress;
    }

    @Override
    public void setMaxProgressValue(int max) {
        this.maxProgress = max;
    }
}
