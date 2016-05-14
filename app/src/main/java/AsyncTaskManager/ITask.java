package AsyncTaskManager;

/**
 * Created by java_monkey on 3/12/2016.
 */
public interface ITask {

    public abstract void process();

    public abstract boolean isTaskFinished();

    public abstract int getProgress();

    public abstract Thread[] getThread();
}
