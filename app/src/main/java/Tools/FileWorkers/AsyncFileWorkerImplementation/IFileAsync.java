package Tools.FileWorkers.AsyncFileWorkerImplementation;

/**
 * Created by java_monkey on 3/12/2016.
 */
public interface IFileAsync extends Runnable{

    public final int DEFAULT_MAX = 100;

    public abstract boolean isFinish();

    public abstract void startProcessing();

    public abstract int getProgress();

    public abstract void setMaxProgressValue(int max);

}
