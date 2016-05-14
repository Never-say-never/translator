package AsyncTaskManager;

import com.example.translateok.DebugActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by java_monkey on 3/12/2016.
 */
public class TaskManager{

    private List<ITask> taskQueue;

    public TaskManager(){
        this.taskQueue = new ArrayList<ITask>();
        DebugActivity.logger.flushMessage("Task manager creating");
    }

    public TaskManager setTask(ITask task){
        this.taskQueue.add(task);
        return this;
    }

    public void setTaskQueue(List<ITask> taskQueue){
        this.taskQueue = taskQueue;
    }

    public TaskManager processTasks(){
        DebugActivity.logger.flushMessage("START PROCESSING VOCABULARY: " + this.taskQueue.size());
        for (ITask task: this.taskQueue) {
            task.process();
        }

        return this;
    }

    public void waitingVocabularyParserFinished(){
        for(ITask task : this.taskQueue){
            for(Thread t : task.getThread()){
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
